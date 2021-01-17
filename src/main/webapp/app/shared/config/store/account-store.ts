import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import store from '@/shared/config/store';
import { TagsViewStoreModule as tagsViewStore } from "@/shared/config/store/tags-view-store";
import { resetRouter } from '@/router';
import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";

export interface IAccountState {
  logon: boolean;
  userIdentity: object;
  userDetails: object;
  authenticated: boolean;
  ribbonOnProfiles: string;
  activeProfiles: Array<string>;
  authorities: Set<string>;
  grantedDocActions: Map<number, Set<number>>;
}

@Module({ dynamic: true, store, name: 'accountStore', namespaced: true })
class AccountStore extends VuexModule implements IAccountState {
  public logon = false;
  public userIdentity = null;
  public userDetails = null;
  public authenticated = false;
  public ribbonOnProfiles = '';
  public activeProfiles = [];
  public authorities = new Set<string>();
  public grantedDocActions = new Map<number, Set<number>>();

  // TODO Get organization ID from userIdentity.
  public properties = new Map<string, any>([
    ['adOrganizationId', 1]
  ]);

  public get account() {
    return this.userIdentity;
  }

  public get property() {
    return (key: string) => this.properties.get(key);
  }

  @Mutation
  private INIT_AUTHENTICATION() {
    this.logon = true;
  }

  @Mutation
  private SET_AUTHENTICATED(identity: any) {
    this.userIdentity = identity;
    this.authorities = new Set(identity.authorities);
    this.authenticated = true;
    this.logon = false;
  }

  @Mutation
  private SET_USER_DETAILS(user: any) {
    this.userDetails = user;
    this.properties.set('userLogin', user.userLogin);
  }

  @Mutation
  private SET_GRANTED_RESOURCES(accesses: Array<any>) {
    for (const access of accesses) {
      if (access.typeName === 'DOC_ACTION') {
        console.log('Granted docActions: %O', access);
        let docActions = this.grantedDocActions.get(access.documentTypeId);
        if (docActions === void 0) {
          docActions = new Set<number>();
          this.grantedDocActions.set(access.documentTypeId, docActions);
        }
        docActions.add(access.referenceListId);
      }
    }
  }

  @Mutation
  private SET_LOGOUT() {
    resetRouter();
    marketplaceStore.clearCatalog();
    tagsViewStore.delAllViews();
    this.userIdentity = null;
    this.authorities.clear();
    this.authenticated = false;
    this.logon = false;
  }

  @Mutation
  private SET_PROFILES(profiles: Array<string>) {
    this.activeProfiles = profiles;
  }

  @Mutation
  private SET_RIBBON(ribbon: string) {
    this.ribbonOnProfiles = ribbon;
  }

  @Action
  public async authenticate() {
    this.INIT_AUTHENTICATION();
  }

  @Action
  public async setAuthenticated(identity: object) {
    this.SET_AUTHENTICATED(identity);
  }

  @Action
  public async setUserDetails(user: any) {
    this.SET_USER_DETAILS(user);
  }

  @Action
  public async setGrantedResources(accesses: Array<any>) {
    this.SET_GRANTED_RESOURCES(accesses);
  }

  @Action
  public async logout() {
    this.SET_LOGOUT();
  }

  @Action
  public async setActiveProfiles(profiles: Array<string>) {
    this.SET_PROFILES(profiles);
  }

  @Action
  public async setRibbonOnProfiles(ribbon: string) {
    this.SET_RIBBON(ribbon);
  }
}

export const AccountStoreModule = getModule(AccountStore);
