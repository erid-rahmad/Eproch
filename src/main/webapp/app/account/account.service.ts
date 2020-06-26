import axios from 'axios';
import { Store } from 'vuex';
import VueRouter, { RouteConfig } from 'vue-router';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store';
import TranslationService from '@/locale/translation.service';

import TrackerService from '@/admin/tracker/tracker.service';
import { PermissionStoreModule as permissionStore } from '@/shared/config/store/permission-store';
import AdMenuService from '@/core/application-dictionary/components/Menu/menu.service';

export default class AccountService {
  constructor(
    private store: Store<any>,
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
      const response = await axios.get('api/account');
      const account = response.data;
      if (account) {
        accountStore.setAuthenticated(account);
        const routes = await this.menuService.retrieve({
          criteriaQuery: 'parentMenuId.specified=false'
        });
        
        await permissionStore.updateRoutes(routes.data);
        this.router.addRoutes(permissionStore.dynamicRoutes);

        if (translationStore.language !== account.langKey) {
          await translationStore.setLanguage(account.langKey);
        }

        const requestedUrl = sessionStorage.getItem('requested-url');
        if (requestedUrl) {
          this.router.replace(requestedUrl);
          sessionStorage.removeItem('requested-url');
        }
        // this.trackerService.connect();
      } else {
        accountStore.logout();
        this.router.replace('/redirect/');
        sessionStorage.removeItem('requested-url');
      }
      this.translationService.refreshTranslation(translationStore.language);
    } catch (err) {
      accountStore.logout();
      this.router.replace('/redirect/');
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
