import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators'
import { getSidebarStatus, getSize, setSidebarStatus, setSize } from '@/utils/cookies'
import store from '@/shared/config/store'
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store'
import { IPaDashboardItem } from '@/shared/model/pa-dashboard-item.model'

export enum DeviceType {
  Mobile,
  Desktop,
}

export interface IAppState {
  device: DeviceType;
  sidebar: {
    opened: boolean
    withoutAnimation: boolean
  };
  language: string;
  size: string;

  dashboardItems: IPaDashboardItem[];
}

@Module({ dynamic: true, store, name: 'appStore', namespaced: true })
class AppStore extends VuexModule implements IAppState {
  public sidebar = {
    opened: getSidebarStatus() !== 'closed',
    withoutAnimation: false
  };
  public device = DeviceType.Desktop;
  public language = translationStore.language;
  public size = getSize() || 'medium';
  public dashboardItems: IPaDashboardItem[] = [];

  @Mutation
  private TOGGLE_SIDEBAR(withoutAnimation: boolean) {
    this.sidebar.opened = !this.sidebar.opened
    this.sidebar.withoutAnimation = withoutAnimation
    if (this.sidebar.opened) {
      setSidebarStatus('opened')
    } else {
      setSidebarStatus('closed')
    }
  }

  @Mutation
  private CLOSE_SIDEBAR(withoutAnimation: boolean) {
    this.sidebar.opened = false
    this.sidebar.withoutAnimation = withoutAnimation
    setSidebarStatus('closed')
  }

  @Mutation
  private TOGGLE_DEVICE(device: DeviceType) {
    this.device = device
  }

  @Mutation
  private SET_LANGUAGE(language: string) {
    this.language = language
    translationStore.setLanguage(language)
  }

  @Mutation
  private SET_SIZE(size: string) {
    this.size = size
    setSize(this.size)
  }

  @Mutation
  private SET_DASHBOARD_ITEMS(items: IPaDashboardItem[]) {
    this.dashboardItems = items;
  }

  @Action
  public ToggleSideBar(withoutAnimation: boolean) {
    this.TOGGLE_SIDEBAR(withoutAnimation)
  }

  @Action
  public CloseSideBar(withoutAnimation: boolean) {
    this.CLOSE_SIDEBAR(withoutAnimation)
  }

  @Action
  public ToggleDevice(device: DeviceType) {
    this.TOGGLE_DEVICE(device)
  }

  @Action
  public SetLanguage(language: string) {
    this.SET_LANGUAGE(language)
  }

  @Action
  public SetSize(size: string) {
    this.SET_SIZE(size)
  }

  @Action
  public setDashboardItems(items: IPaDashboardItem[]) {
    this.SET_DASHBOARD_ITEMS(items);
  }
}

export const AppStoreModule = getModule(AppStore)
