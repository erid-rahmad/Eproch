import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store'
import TranslationService from '@/locale/translation.service';
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store'

@Component
export default class JhiNavbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;
  public version = VERSION ? 'v' + VERSION : '';
  private currentLanguage = translationStore.language;
  private languages: any = translationStore.languages;

  created() {
    this.translationService().refreshTranslation(this.currentLanguage);
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === translationStore.currentLanguage;
  }

  public logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    accountStore.logout().then(() => {
      this.$router.push('/');
    })
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return accountStore.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    return this.accountService().hasAnyAuthority(authorities);
  }

  public get swaggerEnabled(): boolean {
    return accountStore.activeProfiles.indexOf('swagger') > -1;
  }

  public get inProduction(): boolean {
    return accountStore.activeProfiles.indexOf('prod') > -1;
  }
}
