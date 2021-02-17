import NotificationService from '@/core/notification/notification.service'
import RightPanel from '@/shared/components/RightPanel/index.vue'
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store'
import { AppStoreModule as appStore, DeviceType } from '@/shared/config/store/app-store'
import { SettingsStoreModule as settingsStore } from '@/shared/config/store/settings-store'
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store'
import { formatNotificationTitle } from '@/utils/string-utils'
import { mixins } from 'vue-class-component'
import { Component, Inject, Watch } from 'vue-property-decorator'
import TranslationService from '../locale/translation.service'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/resize'

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

  private notificationService: NotificationService = new NotificationService();
  
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
  onAuthenticatedChanged(authenticated: boolean) {
    this.init(authenticated);
  }

  created() {
    this.translationService().refreshTranslation(this.currentLanguage);
  }

  mounted() {
    this.init(this.authenticated);
  }

  beforeDestroy() {
    this.notificationService.unsubscribe();
    this.notificationService.disconnect();
  }

  private init(authenticated: boolean) {
    if (authenticated) {
      this.notificationService.connect();
      this.notificationService.subscribe(accountStore.userDetails.userLogin);
      this.notificationService.receive().subscribe(data => {
        this.$notify({
          duration: 0,
          message: data.message,
          showClose: true,
          title: formatNotificationTitle(data.type),
          type: data.status?.toLowerCase() || 'info'
        });
      });
    } else {
      this.notificationService.unsubscribe();
      this.notificationService.disconnect();
    }
  }

  handleClickOutside() {
    appStore.CloseSideBar(false)
  }
}
