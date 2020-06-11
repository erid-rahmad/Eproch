import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

@Component
export default class Home extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

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
