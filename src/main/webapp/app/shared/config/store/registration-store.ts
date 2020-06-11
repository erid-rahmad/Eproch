import { VuexModule, Module, Mutation, Action, getModule } from 'vuex-module-decorators';
import store from '@/shared/config/store';

export interface ILoginDetails {
  username: string;
  email: string;
  password: string;
}

export interface IRegistrationState {
  loginDetails: ILoginDetails;
  vendorType: string;
  businessCategories: Set<number>;
  mandatoryDocumentTypes: Array<object>;
  additionalDocumentTypes: Array<object>;
}

@Module({ dynamic: true, store, name: 'registrationStore', namespaced: true })
class RegistrationStore extends VuexModule implements IRegistrationState {
  public loginDetails = null;
  public vendorType = '';
  public businessCategories = new Set<number>();
  public mandatoryDocumentTypes = [];
  public additionalDocumentTypes = [];

  @Mutation
  private SET_LOGIN_DETAILS(loginDetails: ILoginDetails) {
    this.loginDetails = loginDetails;
  }

  @Mutation
  private SET_VENDOR_TYPE(type: string) {
    this.vendorType = type;
  }

  @Mutation
  private ADD_BUSINESS_CATEGORY(id: number) {
    this.businessCategories.add(id);
  }

  @Mutation
  private DELETE_BUSINESS_CATEGORY(id: number) {
    this.businessCategories.delete(id);
  }

  @Mutation
  private SET_MANDATORY_DOCUMENT_TYPES(documentTypes: Array<object>) {
    this.mandatoryDocumentTypes = documentTypes;
  }

  @Mutation
  private SET_ADDITIONAL_DOCUMENT_TYPES(documentTypes: Array<object>) {
    this.additionalDocumentTypes = documentTypes;
  }

  @Action
  public setLoginDetails(loginDetails: ILoginDetails) {
    this.SET_LOGIN_DETAILS(loginDetails);
  }

  @Action
  public setVendorType(type: string) {
    this.SET_VENDOR_TYPE(type);
  }

  @Action
  public addBusinessCategory(id: number) {
    this.ADD_BUSINESS_CATEGORY(id);
  }

  @Action
  public deleteBusinessCategory(id: number) {
    this.DELETE_BUSINESS_CATEGORY(id);
  }

  @Action
  public setDocumentTypes(payload) {
    if (payload.mandatory) {
      this.SET_MANDATORY_DOCUMENT_TYPES(payload.documentTypes);
    } else {
      this.SET_ADDITIONAL_DOCUMENT_TYPES(payload.documentTypes);
    }
  }
}

export const RegistrationStoreModule = getModule(RegistrationStore);
