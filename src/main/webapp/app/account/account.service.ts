import TrackerService from '@/admin/tracker/tracker.service';
import AdMenuService from '@/core/application-dictionary/components/Menu/menu.service';
import TranslationService from '@/locale/translation.service';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { PermissionStoreModule as permissionStore } from '@/shared/config/store/permission-store';
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store';
import axios from 'axios';
import VueRouter from 'vue-router';
import { Store } from 'vuex';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';


export default class AccountService {
  constructor(
    private store: Store<any>,
    private windowService: (baseApiUrl: string) => DynamicWindowService,
    private translationService: TranslationService,
    private trackerService: TrackerService,
    private menuService: AdMenuService,
    private router: VueRouter
  ) {}

  public async init(): Promise<void> {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    if (!accountStore.userIdentity && !accountStore.logon && token) {
      await this.retrieveAccount();
    }
    this.retrieveProfiles();
  }

  public hasToken() {
    return localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
  }

  public retrieveProfiles(): void {
    axios.get('management/info').then(res => {
      if (res.data && res.data.activeProfiles) {
        accountStore.setRibbonOnProfiles(res.data['display-ribbon-on-profiles']);
        accountStore.setActiveProfiles(res.data['activeProfiles']);
      }
    });
  }

  public async retrieveAccount(): Promise<void> {
    accountStore.authenticate();
    
    try {
      let response = await axios.get('api/account');
      const account = response.data;

      if (account) {
        response = await this.windowService('api/ad-users').find(account.id);
        accountStore.setUserDetails(response);

        response = await axios.get('api/accesses');
        accountStore.setGrantedResources(response.data);

        const routes = await this.menuService.retrieve();
        
        await permissionStore.updateRoutes(routes.data);
        this.router.addRoutes(permissionStore.dynamicRoutes);

        if (translationStore.language !== account.langKey) {
          await translationStore.setLanguage(account.langKey);
        }

        accountStore.setAuthenticated(account);
        const requestedUrl = sessionStorage.getItem('requested-url');
        if (requestedUrl) {
          console.log('Open the last requested url');
          this.router.replace(requestedUrl);
          sessionStorage.removeItem('requested-url');
        } else {
          // console.log('Redirecting to home page');
          // this.router.replace('/redirect/account/settings');
        }
        // this.trackerService.connect();
      } else {
        this.router.replace('/redirect/');
        sessionStorage.removeItem('requested-url');
        accountStore.logout();
      }
      this.translationService.refreshTranslation(translationStore.language);
    } catch (err) {
      this.router.replace('/redirect/');
      accountStore.logout();
    };
  }

  /**
   * Checks whether a user has any authority accessing a route.
   * @param authorities Route meta authorities.
   */
  public hasAnyAuthority(authorities: any): boolean {
    if (!this.authenticated || !this.userAuthorities) {
      return false;
    }

    if (typeof authorities === 'string') {
      authorities = [authorities];
    }
    return authorities.some((authority: string) => this.userAuthorities.has(authority));
  }

  public get authenticated(): boolean {
    return accountStore.authenticated;
  }

  public get userAuthorities(): Set<string> {
    return accountStore.authorities;
  }
}
