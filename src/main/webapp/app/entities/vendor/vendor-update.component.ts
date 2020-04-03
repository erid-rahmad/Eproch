import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import LocationService from '../location/location.service';
import { ILocation } from '@/shared/model/location.model';

import CompanyFunctionaryService from '../company-functionary/company-functionary.service';
import { ICompanyFunctionary } from '@/shared/model/company-functionary.model';

import PersonInChargeService from '../person-in-charge/person-in-charge.service';
import { IPersonInCharge } from '@/shared/model/person-in-charge.model';

import SupportingDocumentService from '../supporting-document/supporting-document.service';
import { ISupportingDocument } from '@/shared/model/supporting-document.model';

import BusinessCategoryService from '../business-category/business-category.service';
import { IBusinessCategory } from '@/shared/model/business-category.model';

import AlertService from '@/shared/alert/alert.service';
import { IVendor, Vendor } from '@/shared/model/vendor.model';
import VendorService from './vendor.service';

const validations: any = {
  vendor: {
    code: {},
    name: {
      required
    },
    npwp: {
      required,
      numeric
    },
    branch: {},
    email: {
      required
    },
    phone: {
      required
    },
    fax: {},
    website: {},
    type: {
      required
    },
    paymentCategory: {
      required
    },
    approvalStatus: {
      required
    },
    locationId: {
      required
    }
  }
};

@Component({
  validations
})
export default class VendorUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('vendorService') private vendorService: () => VendorService;
  public vendor: IVendor = new Vendor();

  @Inject('locationService') private locationService: () => LocationService;

  public locations: ILocation[] = [];

  @Inject('companyFunctionaryService') private companyFunctionaryService: () => CompanyFunctionaryService;

  public companyFunctionaries: ICompanyFunctionary[] = [];

  @Inject('personInChargeService') private personInChargeService: () => PersonInChargeService;

  public personInCharges: IPersonInCharge[] = [];

  @Inject('supportingDocumentService') private supportingDocumentService: () => SupportingDocumentService;

  public supportingDocuments: ISupportingDocument[] = [];

  @Inject('businessCategoryService') private businessCategoryService: () => BusinessCategoryService;

  public businessCategories: IBusinessCategory[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vendorId) {
        vm.retrieveVendor(to.params.vendorId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.vendor.businessCategories = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.vendor.id) {
      this.vendorService()
        .update(this.vendor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.vendor.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.vendorService()
        .create(this.vendor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.vendor.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveVendor(vendorId): void {
    this.vendorService()
      .find(vendorId)
      .then(res => {
        this.vendor = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.locationService()
      .retrieve()
      .then(res => {
        this.locations = res.data;
      });
    this.companyFunctionaryService()
      .retrieve()
      .then(res => {
        this.companyFunctionaries = res.data;
      });
    this.personInChargeService()
      .retrieve()
      .then(res => {
        this.personInCharges = res.data;
      });
    this.supportingDocumentService()
      .retrieve()
      .then(res => {
        this.supportingDocuments = res.data;
      });
    this.businessCategoryService()
      .retrieve()
      .then(res => {
        this.businessCategories = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
