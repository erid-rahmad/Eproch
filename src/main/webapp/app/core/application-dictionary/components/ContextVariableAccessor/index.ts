import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";
import { Component, Vue, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import pluralize from 'pluralize';
import { kebabCase } from 'lodash';
import { IADField } from '@/shared/model/ad-field.model';
import { isObject } from 'util';

const operators: Map<string, (a: any, b: any) => boolean> = new Map([
  ['=', (a: any, b: any) => a === b],
  ['!=', (a: any, b: any) => a !== b],
  ['>', (a: any, b: any) => a > b],
  ['>=', (a: any, b: any) => a >= b],
  ['<', (a: any, b: any) => a < b],
  ['<=', (a: any, b: any) => a <= b],
  ['is', (a: any, b: any) => {
    if (b === 'empty') {
      return a === 'null';
    } else if (b === 'notEmpty') {
      return a !== 'null';
    }

    return operators.get('=')(a, b);
  }]
])

@Component
export default class ContextVariableAccessor extends Vue {
  
  @Inject('dynamicWindowService')
  protected dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  static contextRegex = new RegExp(/\{((([\w\s]+)\|)|(\#|\$))?(\w+)\}/, 'g');
  static validationQueryRegex = new RegExp(/\{(select|from|where)[\s\w,:.=]+\}/, 'g');
  static conditionRegex = new RegExp(/([\w\d]+)\s*?(is|=|\!\=|\<|\<\=|\>|\>\=)\s*?([\w\d]+)/, 'g');

  protected async parseValidationQuery(query: string) {
    if (! query)
      return '';

    const parsed = query.matchAll(ContextVariableAccessor.validationQueryRegex);
    let parsingDone = false;
    while ( ! parsingDone) {
      const matches = parsed.next();
      parsingDone = matches.done;

      if ( ! parsingDone) {
        const value = await this.retrieveRemoteValue(matches.value[0]);
        if (! value) {
          return '';
        }

        query = query.replace(matches.value[0], value);
      }
    }

    return query;
  }

  protected async retrieveRemoteValue(options: string, defaultTabId?: string) {
    let params = options.substring(1, options.length - 1)
      .split(/\s*?,\s*?/)
      .map(item => {
        const param = item.trim().split(/\s*?:\s*?/);
        const result = {};
        result[param[0]] = param[1].trim();
        return result;
      });

    const config = Object.assign({}, ...params);
    const resourceName = pluralize.plural(kebabCase(config.from));
    const api = `/api/${resourceName}`;
    const whereClause = <string>this.getContext(config.where, defaultTabId);

    if (whereClause.includes('=null')) {
      return Promise.resolve(null);
    }

    const response = await this.dynamicWindowService(api)
      .retrieve({criteriaQuery: whereClause});

    if (response.data.length) {
      return Promise.resolve(response.data[0][config.select]);
    }

    return Promise.resolve(null);
  }

  protected getContext(text: string, options?: string | Record<string, any>): string | number | boolean {
    if (!text)
      return '';

    // Parse auth variables.
    const parsed = text.matchAll(ContextVariableAccessor.contextRegex);
    let parsingDone = false;
    while (!parsingDone) {
      const matches = parsed.next();
      parsingDone = matches.done;

      if (!parsingDone) {
        const value = this.parseMatches(matches.value, options);
        text = text.replace(matches.value[0], value);
      }
    }

    if (text === 'true' || text === 'false') {
      return text === 'true';
    }

    if (text && !isNaN(text as any)) {
      return parseFloat(text);
    }

    return text;
  }

  protected evaluateDisplayLogic(field: IADField, options?: string | Record<string, any>): boolean {
    const displayLogic = field.displayLogic || field.adColumn.displayLogic;
    let logic = <string> this.getContext(displayLogic, options);
    return this.evaluateDynamicLogic(logic);
  }

  protected evaluateMandatoryLogic(field: IADField, options?: string | Record<string, any>) {
    if ( ! field.adColumn.mandatoryLogic) {
      return false;
    }

    let logic = <string> this.getContext(field.adColumn.mandatoryLogic, options);
    return this.evaluateDynamicLogic(logic);
  }

  protected evaluateReadonlyLogic(field: IADField, options?: string | Record<string, any>) {
    const readonlyLogic = field.readOnlyLogic || field.adColumn.readOnlyLogic;

    if ( ! readonlyLogic) {
      return false;
    }

    let logic = <string> this.getContext(readonlyLogic, options);
    return this.evaluateDynamicLogic(logic);
  }

  private evaluateDynamicLogic(logic: string) {
    if (logic) {
      const branches = logic.split(/\s*?OR\s*/);
      let fulfilled = false;

      for (const branch of branches) {
        if (this.evaluateCondition(branch)) {
          fulfilled = true;
          break;
        }
      }

      return fulfilled;
    }

    return true;
  }

  private evaluateCondition(branch: string) {
    let fulfilled = false;
    const conditions = branch.matchAll(ContextVariableAccessor.conditionRegex);
    let parseDone = false;

    while (!parseDone) {
      const matches = conditions.next();
      parseDone = matches.done;

      if (!parseDone) {
        const operand1 = matches.value[1];
        const operator = matches.value[2];
        const operand2 = matches.value[3];

        fulfilled = operators.get(operator)(operand1, operand2);
        if (!fulfilled) {
          break;
        }
      }
    }

    return fulfilled;
  }

  /**
   * 
   * @param matches Array of matched regex groups.
   */
  private parseMatches(matches: string[], options?: string | Record<string, any>) {
    let defaultTabId: string;
    let record: Record<string, any>;

    if (typeof options === 'string') {
      defaultTabId = options;
    } else if (isObject(options)) {
      record = options;
    }

    const tabId = matches[3] || defaultTabId;
    const flag = matches[4];
    const variable = matches[5];
    const loginContext = flag === '#';
    // const accountingContext = flag === '$'
    const windowContext = tabId || !flag;

    if (loginContext) {
      return accountStore.property(variable);
    }

    if (windowContext) {
      if (record) {
        return record[variable];
      } else {
        const value = windowStore.value(this.$route.fullPath, tabId, variable);
        return value === '' ? null : value;
      }
    }

    return null;
  }

}