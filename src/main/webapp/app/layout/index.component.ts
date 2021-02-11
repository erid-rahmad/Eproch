import { Component, Inject, Watch } from 'vue-property-decorator'
import { mixins } from 'vue-class-component'
import { DeviceType, AppStoreModule as appStore } from '@/shared/config/store/app-store'
import { SettingsStoreModule as settingsStore } from '@/shared/config/store/settings-store'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import RightPanel from '@/shared/components/RightPanel/index.vue'
import ResizeMixin from './mixin/resize'
import { AccountStoreModule as accountStore} from '@/shared/config/store/account-store'
import TranslationService from '../locale/translation.service'
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store'
import NotificationService from '@/core/notification/notification.service';

@Component({
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView
  }
})
export default class extends mixins(ResizeMixin) {

  @Inject('translationService') private translationService: () => TranslationService;
  private currentLanguage = translationStore.language;

  private notificationService: NotificationService;
  
  get authenticated(): boolean {
    return accountStore.authenticated;
  }

  get classObj() {
    return {
      hideSidebar: !this.sidebar.opened,
      openSidebar: this.sidebar.opened,
      withoutAnimation: this.sidebar.withoutAnimation,
      mobile: this.device === DeviceType.Mobile
    }
  }

  get showSettings() {
    return settingsStore.showSettings
  }

  get showTagsView() {
    return this.authenticated && settingsStore.showTagsView
  }

  get fixedHeader() {
    return settingsStore.fixedHeader
  }

  @Watch('authenticated')
  onAuthenticatedChanged(authenticated) {
    if (authenticated) {
      this.notificationService.connect();
      this.notificationService.subscribe(accountStore.userDetails.userLogin);
      this.notificationService.receive().subscribe(data => {
        this.$notify({
          message: data.message,
          title: data.type,
          type: data.status?.toLowerCase() || 'info'
        })
      });
    } else {
      this.notificationService.unsubscribe();
      this.notificationService.disconnect();
    }
  }

  created() {
    this.translationService().refreshTranslation(this.currentLanguage);
    this.notificationService = new NotificationService();
  }

  handleClickOutside() {
    appStore.CloseSideBar(false)
  }
}
