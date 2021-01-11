import Vue from 'vue';
import Vuex from 'vuex';
import { IAppState } from './app-store';
import { IUserState } from './user-store';
import { ITagsViewState } from './tags-view-store';
import { IErrorLogState } from './error-log-store';
import { IPermissionState } from './permission-store';
import { ISettingsState } from './settings-store';
import { IAccountState } from './account-store';
import { IAlertState } from './alert-store';
import { ITranslationState } from './translation-store';
import { IRegistrationState } from './registration-store';
import { IWindowState } from "./window-store";
import { IMarketplaceState } from "./marketplace-store";

Vue.use(Vuex);

export interface IRootState {
  translationStore: ITranslationState;
  appStore: IAppState;
  accountStore: IAccountState;
  userStore: IUserState;
  tagsViewStore: ITagsViewState;
  errorLogStore: IErrorLogState;
  permissionStore: IPermissionState;
  settingsStore: ISettingsState;
  alertStore: IAlertState;
  registrationStore: IRegistrationState;
  windowStore: IWindowState;
  marketplaceStore: IMarketplaceState;
}

// Declare empty store first, dynamically register all modules later.
export default new Vuex.Store<IRootState>({});
