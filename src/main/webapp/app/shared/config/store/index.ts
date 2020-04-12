import Vue from 'vue'
import Vuex from 'vuex'
import { IAppState } from '@/shared/config/store/app-store'
import { IUserState } from '@/shared/config/store/user-store'
import { ITagsViewState } from '@/shared/config/store/tags-view-store'
import { IErrorLogState } from '@/shared/config/store/error-log-store'
import { IPermissionState } from '@/shared/config/store/permission-store'
import { ISettingsState } from '@/shared/config/store/settings-store'
import { IAccountState } from '@/shared/config/store/account-store';
import { IAlertState } from '@/shared/config/store/alert-store';
import { ITranslationState } from '@/shared/config/store/translation-store';
import { IRegistrationState } from './registration-store'

Vue.use(Vuex)

export interface IRootState {
  translationStore: ITranslationState
  appStore: IAppState
  accountStore: IAccountState
  userStore: IUserState
  tagsViewStore: ITagsViewState
  errorLogStore: IErrorLogState
  permissionStore: IPermissionState
  settingsStore: ISettingsState
  alertStore: IAlertState
  registrationStore: IRegistrationState
}

// Declare empty store first, dynamically register all modules later.
export default new Vuex.Store<IRootState>({})
