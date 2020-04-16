import axios from 'axios';
import { Store } from 'vuex';
import VueRouter from 'vue-router';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { TranslationStoreModule as translationStore } from '@/shared/config/store/translation-store';
import TranslationService from '@/locale/translation.service';

import TrackerService from '@/admin/tracker/tracker.service';
import { PermissionStoreModule as permissionStore } from '@/shared/config/store/permission-store';

export default class AccountService {
  constructor(
    private store: Store<any>,
    private translationService: TranslationService,
    private trackerService: TrackerService,
    private router: VueRouter
  ) {
    this.init();
  }

  public init(): void {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    if (!accountStore.userIdentity && !accountStore.logon && token) {
      this.retrieveAccount();
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

  public retrieveAccount(): void {
    accountStore.authenticate();
    axios
      .get('api/account')
      .then(response => {
        const account = response.data;
        if (account) {
          accountStore.setAuthenticated(account);
          if (translationStore.language !== account.langKey) {
            translationStore.setLanguage(account.langKey);
          }
          if (sessionStorage.getItem('requested-url')) {
            this.router.replace(sessionStorage.getItem('requested-url'));
            sessionStorage.removeItem('requested-url');
          }
          permissionStore.generateRoutes(new Set<string>(account.authorities));
          this.router.addRoutes(permissionStore.dynamicRoutes);
          this.trackerService.connect();
        } else {
          accountStore.logout();
          this.router.push('/');
          sessionStorage.removeItem('requested-url');
        }
        this.translationService.refreshTranslation(translationStore.language);
      })
      .catch(() => {
        accountStore.logout();
        this.router.push('/');
      });
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
    return authorities.some(authority => this.userAuthorities.has(authority));
  }

  public get authenticated(): boolean {
    return accountStore.authenticated;
  }

  public get userAuthorities(): Set<string> {
    return accountStore.authorities;
  }
}
