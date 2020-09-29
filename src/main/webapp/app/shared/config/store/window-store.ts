import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import store from '@/shared/config/store'
import { IADField } from '@/shared/model/ad-field.model';
import { IADTab } from '@/shared/model/ad-tab.model';

export interface IWindowState {
  /**
   * Each item in the map is identified by window URL fullPath.
   */
  windows: Map<string, Map<string, object>>;
}

export interface IRegisterTabParameter {
  /**
   * The unique window full path.
   */
  path: string;

  /**
   * The tab identifier within the window.
   */
  tabId: string;

  /**
   * The record within the tab.
   */
  data: Record<string, any>;
}

export interface IConditionalTabParameter {
  /**
   * The unique window full path.
   */
  path: string;

  /**
   * The tab identifier within the window.
   */
  tabId: string;

  /**
   * Object representing the IADTab interface.
   */
  tab: IADTab;
}

export interface IMandatoryFieldParameter {
  /**
   * The unique window full path.
   */
  path: string;

  /**
   * The tab identifier within the window.
   */
  tabId: string;

  /**
   * Fields that is conditionally displayed and/or readonly.
   */
  fields: Record<string, IADField>;
}

@Module({ dynamic: true, store, name: 'windowStore', namespaced: true })
class WindowStore extends VuexModule implements IWindowState {
  
  windows: Map<string, Map<string, object>> = new Map();
  conditionalTabs: Map<string, Map<string, IADTab>> = new Map();
  mandatoryFields: Map<string, Map<string, Record<string, IADField>>> = new Map();

  public get tabs() {
    return (key: string): Map<string, object> => this.windows.get(key);
  }

  public get tab() {
    return (path: string, tabId: string): object => {
      if (this.tabs(path)) {
        return this.tabs(path).get(tabId);
      }

      return null;
    }
  }

  public get logicallyMandatoryFields() {
    return (path: string, tabId: string) => {
      if (this.mandatoryFields.has(path)) {
        return this.mandatoryFields.get(path).get(tabId);
      }

      return null;
    }
  }

  public get value() {
    return (path: string, tabId: string, field: string): any => {
      const tab = this.tab(path, tabId);
      return tab ? tab[field] : null;
    }
  }

  @Mutation
  private SET_TAB({path, tabId, data}: IRegisterTabParameter) {
    let tabs = this.windows.get(path);
    if (!tabs) {
      tabs = new Map();
      this.windows.set(path, tabs);
    }

    // Entry with the same tabId will be over written.
    tabs.set(tabId, data);
  }

  @Mutation
  private SET_CONDITIONAL_TAB({path, tabId, tab}: IConditionalTabParameter) {
    let tabs = this.conditionalTabs.get(path);
    if (!tabs) {
      tabs = new Map();
      this.conditionalTabs.set(path, tabs);
    }

    tabs.set(tabId, tab);
  }

  @Mutation
  private SET_MANDATORY_FIELDS({path, tabId, fields}: IMandatoryFieldParameter) {
    let tabs = this.mandatoryFields.get(path);
    if (!tabs) {
      tabs = new Map();
      this.mandatoryFields.set(path, tabs);
    }

    tabs.set(tabId, fields);
  }

  @Mutation
  private REMOVE_WINDOW(path: string) {
    this.mandatoryFields.delete(path);
    this.conditionalTabs.delete(path);
    this.windows.delete(path);
  }

  @Action
  public async registerTab(options: IRegisterTabParameter) {
    this.SET_TAB(options);
  }

  @Action
  public async addConditionalTab(options: IConditionalTabParameter) {
    this.SET_CONDITIONAL_TAB(options);
  }

  @Action
  public async addMandatoryFields(options: IMandatoryFieldParameter) {
    this.SET_MANDATORY_FIELDS(options);
  }

  @Action
  public async removeWindow(path: string) {
    this.REMOVE_WINDOW(path);
  }
}

export const WindowStoreModule = getModule(WindowStore);
