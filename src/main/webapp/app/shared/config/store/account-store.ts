import { resetRouter } from '@/router';
import store from '@/shared/config/store';
import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";
import { TagsViewStoreModule as tagsViewStore } from "@/shared/config/store/tags-view-store";
import { Action, getModule, Module, Mutation, VuexModule } from 'vuex-module-decorators';

export interface IAccountState {
  logon: boolean;
  userIdentity: object;
  userDetails: object;
  authenticated: boolean;
  ribbonOnProfiles: string;
  activeProfiles: Array<string>;
  authorities: Set<string>;
  grantedDocActions: Map<number, Set<number>>;
  grantedDashboards: Map<string, Set<number>>;
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
  public grantedDashboards = new Map<string, Set<number>>();
  public properties = new Map<string, any>();

  public get account() {
    return this.userIdentity;
  }

  public get isEmployee() {
    return this.userDetails.employee;
  }

  public get isVendor() {
    return this.userDetails.vendor;
  }

  public get organizationInfo() {
    return {
      id: this.userDetails.adOrganizationId,
      name: this.userDetails.adOrganizationName
    };
  }

  public get property() {
    return (key: string) => this.properties.get(key);
  }

  public get vendorInfo() {
    if (! this.isVendor) {
      return {};
    }
    
    return {
      id: this.userDetails.cVendorId,
      name: this.userDetails.cVendorName
    };
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
    this.properties.set('adOrganizationId', user.adOrganizationId);
    this.properties.set('adOrganizationName', user.adOrganizationName);
    this.properties.set('email', user.email);
    this.properties.set('isVendor', user.vendor);
    this.properties.set('userLogin', user.userLogin);
    this.properties.set('vendorId', user.cVendorId);
    this.properties.set('vendorName', user.cVendorName);
  }

  @Mutation
  private SET_GRANTED_RESOURCES(accesses: Array<any>) {
    for (const access of accesses) {
      // Grant access to document actions.
      if (access.typeName === 'DOC_ACTION') {
        let docActions = this.grantedDocActions.get(access.documentTypeId);

        if (docActions === void 0) {
          docActions = new Set<number>();
          this.grantedDocActions.set(access.documentTypeId, docActions);
        }
        docActions.add(access.referenceListId);
      }
      
      // Grant access to dashboards.
      else if (access.typeName === 'DASHBOARD') {
        const dashboardKey = `${access.paDashboardId}_${access.paDashboardName}`;
        let dashboardItems = this.grantedDashboards.get(dashboardKey);

        if (dashboardItems === void 0) {
          dashboardItems = new Set<number>();
          this.grantedDashboards.set(dashboardKey, dashboardItems);
        }
        dashboardItems.add(access.paDashboardItemId);
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
    this.grantedDashboards.clear();
    this.grantedDocActions.clear();
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
