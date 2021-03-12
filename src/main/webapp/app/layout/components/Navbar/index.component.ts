import { Component, Vue } from 'vue-property-decorator'
import { AppStoreModule as appStore } from '@/shared/config/store/app-store'
import { AccountStoreModule as accountStore} from '@/shared/config/store/account-store'
import { UserStoreModule as userStore } from '@/shared/config/store/user-store'
import Breadcrumb from '@/shared/components/Breadcrumb/index.vue'
import ErrorLog from '@/shared/components/ErrorLog/index.vue'
import Hamburger from '@/shared/components/Hamburger/index.vue'
import HeaderSearch from '@/shared/components/HeaderSearch/index.vue'
import LangSelect from '@/shared/components/LangSelect/index.vue'
import Screenfull from '@/shared/components/Screenfull/index.vue'
import SizeSelect from '@/shared/components/SizeSelect/index.vue'
import QuickCart from "@/core/application-dictionary/components/Form/marketplace/quick-cart.vue";

@Component({
  name: 'Navbar',
  components: {
    Breadcrumb,
    ErrorLog,
    Hamburger,
    HeaderSearch,
    LangSelect,
    QuickCart,
    Screenfull,
    SizeSelect
  }
})
export default class extends Vue {
  get sidebar() {
    return appStore.sidebar
  }

  get authenticated(): boolean {
    return accountStore.authenticated;
  }

  get device() {
    return appStore.device.toString()
  }

  get avatar() {
    return userStore.avatar
  }

  toggleSideBar() {
    appStore.ToggleSideBar(false);
  }

  async logout() {
    await accountStore.logout();
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$router.replace({
      path: `/redirect/login`
    }).catch(err => {
      console.warn(err);
    });
  }
}