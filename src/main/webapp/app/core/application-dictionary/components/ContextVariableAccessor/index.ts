import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";
import { Component, Vue, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import pluralize from 'pluralize';
import { kebabCase } from 'lodash';

@Component
export default class ContextVariableAccessor extends Vue {
  
  @Inject('dynamicWindowService')
  protected dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  static contextRegex = new RegExp(/\{((([\w\s]+)\|)|(\#|\$))?(\w+)\}/, 'g');
  static validationQueryRegex = new RegExp(/\{(select|from|where)[\s\w,:.=]+\}/, 'g');

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

  protected getContext(text: string, defaultTabId?: string): string | number | boolean {
    if (!text)
      return '';

    // Parse auth variables.
    const parsed = text.matchAll(ContextVariableAccessor.contextRegex);
    let parsingDone = false;
    while (!parsingDone) {
      const matches = parsed.next();
      parsingDone = matches.done;

      if (!parsingDone) {
        const value = this.parseMatches(matches.value, defaultTabId);
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

  /**
   * 
   * @param matches Array of matched regex groups.
   */
  private parseMatches(matches: string[], defaultTabId?: string) {
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
      return windowStore.value(this.$route.fullPath, tabId, variable);
    }

    return null;
  }

}