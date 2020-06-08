import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import store from '@/shared/config/store'

export interface IWindowState {
  /**
   * Each item in the map is identified by window URL fullPath.
   */
  windows: Map<string, Map<any, object>>;
}

export interface IRegisterTabParameter {
  path: string;
  tabId: string;
  data: object;
}

export class RegisterTabParameter implements IRegisterTabParameter {
  path: string;
  tabId: string;
  data: object;
}

@Module({ dynamic: true, store, name: 'windowStore', namespaced: true })
class WindowStore extends VuexModule implements IWindowState {
  windows: Map<string, Map<any, object>> = new Map();

  public get tabs() {
    return (key: string): Map<any, object> => this.windows.get(key);
  }

  public get tab() {
    return (path: string, tabId: string): object => {
      if (this.tabs(path)) {
        return this.tabs(path).get(tabId);
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
  private REMOVE_WINDOW(path: string) {
    this.windows.delete(path);
  }

  @Action
  public async registerTab(options: IRegisterTabParameter) {
    this.SET_TAB(options);
  }

  @Action
  public async removeWindow(path: string) {
    this.REMOVE_WINDOW(path);
  }
}

export const WindowStoreModule = getModule(WindowStore);
