import { Vue, Component } from 'vue-property-decorator';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";

@Component
export default class ContextVariableAccessor extends Vue {

  static contextRegex = new RegExp(/\{((([\w\s]+)\|)|(\#|\$))?(\w+)\}/, 'g');

  protected getContext(text: string): string | number | boolean {
    if (!text)
      return '';

    // Parse auth variables.
    const parsed = text.matchAll(ContextVariableAccessor.contextRegex);
    let parsingDone = false;
    while (!parsingDone) {
      const matches = parsed.next();
      parsingDone = matches.done;

      if (!parsingDone) {
        const value = this.parseMatches(matches.value);
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
  private parseMatches(matches: string[]) {
    const tabId = matches[3];
    const flag = matches[4];
    const variable = matches[5];
    const loginContext = flag === '#';
    const accountingContext = flag === '$'
    const windowContext = tabId || (!tabId && !flag);

    if (loginContext) {
      return accountStore.property(variable);
    }

    if (windowContext) {
      return windowStore.value(this.$route.fullPath, tabId, variable);
    }

    return null;
  }

}