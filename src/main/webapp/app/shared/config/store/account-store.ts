import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import store from '@/shared/config/store';

export interface IAccountState {
  logon: boolean;
  userIdentity: object;
  authenticated: boolean;
  ribbonOnProfiles: string;
  activeProfiles: Array<string>;
  authorities: Set<string>;
}

@Module({ dynamic: true, store, name: 'accountStore', namespaced: true })
class AccountStore extends VuexModule implements IAccountState {
  public logon = false;
  public userIdentity = null;
  public authenticated = false;
  public ribbonOnProfiles = '';
  public activeProfiles = [];
  public authorities = new Set<string>();

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
  private SET_LOGOUT() {
    this.userIdentity = null;
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
  public authenticate() {
    this.INIT_AUTHENTICATION();
  }

  @Action
  public async setAuthenticated(identity: object) {
    this.SET_AUTHENTICATED(identity);
  }

  @Action
  public async logout() {
    this.SET_LOGOUT;
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
