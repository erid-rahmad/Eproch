import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";
import { IADField } from '@/shared/model/ad-field.model';
import { IADTab } from '@/shared/model/ad-tab.model';
import { kebabCase } from 'lodash';
import pluralize from 'pluralize';
import { Component, Inject, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

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
]);

interface IContextParameter {
  /**
   * Tab ID to be use when there is no explicit tabId.
   */
  defaultTabId: string;

  /**
   * The string to be evaluated.
   */
  text?: string,

  /**
   * An IADTab will be passed to evaluate the tab's display logic.
   */
  tab?: IADTab;

  /**
   * A IADField will be used instead when there is a need to evaluate the
   * field's display, readonly, and mandatory logic.
   */
  field?: IADField;

  /**
   * Ask the evaluator to use this record instead of the one that's in WindowStoreModule.
   */
  record?: Record<string, any>;
}

@Component
export default class ContextVariableAccessor extends Vue {
  
  @Inject('dynamicWindowService')
  protected dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  static contextRegex = new RegExp(/\{((([\w\s]+)\|)|(\#|\$))?(\w+)\}/, 'g');
  static validationQueryRegex = new RegExp(/\{(select|from|where)[\s\w,:.=]+\}/, 'g');
  static conditionRegex = new RegExp(/([\w\d]+)\s*?(is|=|\!\=|\<|\<\=|\>|\>\=)\s*?([\w\d]+)/, 'g');

  protected async parseValidationQuery({defaultTabId, text}: IContextParameter) {
    if (! text)
      return '';

    let query = text;
    const parsed = query.matchAll(ContextVariableAccessor.validationQueryRegex);
    let parsingDone = false;

    while ( ! parsingDone) {
      const matches = parsed.next();
      parsingDone = matches.done;

      if ( ! parsingDone) {
        const value = await this.retrieveRemoteValue({
          defaultTabId,
          text: matches.value[0]
        });

        if (! value) {
          return '';
        }

        query = query.replace(matches.value[0], value);
      }
    }

    return query;
  }

  protected async retrieveRemoteValue({defaultTabId, text}: IContextParameter) {
    let params = text.substring(1, text.length - 1)
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

    const whereClause = <string>this.getContext({
      defaultTabId,
      text: config.where
    });

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

  /**
   * Get the context variable value with the given text.
   * @param param0 
   */
  protected getContext({defaultTabId, text, record}: IContextParameter): string | number | boolean {
    if ( ! text) {
      return '';
    }

    // Parse auth variables.
    const parsed = text.matchAll(ContextVariableAccessor.contextRegex);
    let parsingDone = false;

    while (!parsingDone) {
      const matches = parsed.next();
      parsingDone = matches.done;

      if (!parsingDone) {
        const value = this.parseMatches(matches.value, { defaultTabId, record });
        text = text.replace(matches.value[0], value);
      }
    }

    if (text === 'true' || text === 'false') {
      return text === 'true';
    }

    if (text && ! isNaN(text as any)) {
      return parseFloat(text);
    }

    return text;
  }

  /**
   * Evaluate display logic of a tab or field.
   * @param param0 
   */
  protected evaluateDisplayLogic({defaultTabId, tab, field, record}: IContextParameter): boolean {
    if ( ! tab && ! field) {
      return true;
    }

    let displayLogic: string;
    
    if (tab) {
      displayLogic = tab.displayLogic;
    } else {
      displayLogic = field.displayLogic || field.adColumn.displayLogic;
    }

    return this.evaluateDynamicLogic({
      defaultTabId,
      text: displayLogic,
      record
    });
  }

  /**
   * Evaluate mandatory logic of a field. Always returns true if the column is not nullable.
   * @param param0 
   */
  protected evaluateMandatoryLogic({defaultTabId, field}: IContextParameter) {
    if (field.adColumn.mandatory) {
      return true;
    }

    const mandatoryLogic = field.adColumn.mandatoryLogic;

    if ( ! mandatoryLogic) {
      return false;
    }

    return this.evaluateDynamicLogic({
      defaultTabId,
      text: mandatoryLogic
    });
  }

  /**
   * Evaluate readonly logic of a field. Always returns true if the column is not nullable.
   * @param param0 
   */
  protected evaluateReadonlyLogic({defaultTabId, field}: IContextParameter) {
    const readonlyLogic = field.readOnlyLogic || field.adColumn.readOnlyLogic;

    if ( ! readonlyLogic) {
      return false;
    }

    return this.evaluateDynamicLogic({
      defaultTabId,
      text: readonlyLogic
    });
  }

  /**
   * Evaluates a condition.
   * @param param0 
   */
  private evaluateDynamicLogic(options: IContextParameter) {
    const logic = <string> this.getContext(options);

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
  private parseMatches(matches: string[], options: IContextParameter) {
    const defaultTabId = options.defaultTabId;
    const record = options.record;

    const tabId = matches[3] || defaultTabId;
    const flag = matches[4];
    const variable = matches[5];
    const loginContext = flag === '#';
    // const accountingContext = flag === '$'
    const windowContext = ! flag;

    if (loginContext) {
      return accountStore.property(variable);
    }

    if (windowContext) {
      // Use record when evaluating each row in a table.
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