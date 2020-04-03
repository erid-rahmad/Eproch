import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import VendorService from '../vendor/vendor.service';
import { IVendor } from '@/shared/model/vendor.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompanyFunctionary, CompanyFunctionary } from '@/shared/model/company-functionary.model';
import CompanyFunctionaryService from './company-functionary.service';

const validations: any = {
  companyFunctionary: {
    name: {
      required
    },
    position: {
      required
    },
    phone: {
      required
    },
    email: {
      required
    }
  }
};

@Component({
  validations
})
export default class CompanyFunctionaryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('companyFunctionaryService') private companyFunctionaryService: () => CompanyFunctionaryService;
  public companyFunctionary: ICompanyFunctionary = new CompanyFunctionary();

  @Inject('vendorService') private vendorService: () => VendorService;

  public vendors: IVendor[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyFunctionaryId) {
        vm.retrieveCompanyFunctionary(to.params.companyFunctionaryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.companyFunctionary.id) {
      this.companyFunctionaryService()
        .update(this.companyFunctionary)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.companyFunctionary.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.companyFunctionaryService()
        .create(this.companyFunctionary)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.companyFunctionary.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompanyFunctionary(companyFunctionaryId): void {
    this.companyFunctionaryService()
      .find(companyFunctionaryId)
      .then(res => {
        this.companyFunctionary = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.vendorService()
      .retrieve()
      .then(res => {
        this.vendors = res.data;
      });
  }
}
