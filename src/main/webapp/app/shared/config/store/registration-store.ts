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
  eInvoice: boolean;
  taxableEmployers: boolean;
  taxes: any[];
}

@Module({ dynamic: true, store, name: 'registrationStore', namespaced: true })
class RegistrationStore extends VuexModule implements IRegistrationState {
  public loginDetails = null;
  public vendorType = '';
  public businessCategories = new Set<number>();
  public mandatoryDocumentTypes = [];
  public additionalDocumentTypes = [];
  public eInvoice: boolean = false;
  public taxableEmployers: boolean = false;
  public taxes: any[] = [];

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

  @Mutation
  private SET_EINVOICE(eInvoice: boolean) {
    this.eInvoice = eInvoice;
    this.taxes.forEach(tax => {
      tax.eInvoice = eInvoice;
    });
  }

  @Mutation
  private SET_TAXABLE_EMPLOYERS(taxableEmployers: boolean) {
    this.taxableEmployers = taxableEmployers;
    this.taxes.forEach(tax => {
      tax.taxableEmployers = taxableEmployers;
    });
  }

  @Mutation
  private SET_TAXES(taxes: any) {
    console.log('Set taxes ', taxes);
    this.taxes = taxes;
  }

  @Mutation
  private REMOVE_TAX(index: number) {
    this.taxes.splice(index, 1);
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

  @Action
  public setEInvoice(eInvoice: boolean) {
    this.SET_EINVOICE(eInvoice);
  }

  @Action
  public setTaxableEmployers(taxableEmployers: boolean) {
    this.SET_TAXABLE_EMPLOYERS(taxableEmployers);
  }

  @Action
  public setTaxes(taxes: any): Promise<void> {
    return new Promise(resolve => resolve(this.SET_TAXES(taxes)));
  }

  @Action
  public removeTax(tax: any) {
    const index = this.taxes.indexOf(tax);
    index > -1 && this.REMOVE_TAX(index);
  }
}

export const RegistrationStoreModule = getModule(RegistrationStore);
