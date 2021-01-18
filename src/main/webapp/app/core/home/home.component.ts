import LoginService from '@/account/login.service';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import WatchList from "./components/watch-list.vue";

@Component({
  components: {
    WatchList
  }
})
export default class MainDashboard extends Vue {

  @Inject('loginService')
  private loginService: () => LoginService;

  get isNotVendor() {
    return !accountStore.userDetails.vendor;
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return accountStore.authenticated;
  }

  public get username(): string {
    return accountStore.userIdentity ? accountStore.userIdentity.login : '';
  }
}
