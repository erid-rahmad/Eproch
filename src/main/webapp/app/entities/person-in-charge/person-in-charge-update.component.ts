import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import VendorService from '../vendor/vendor.service';
import { IVendor } from '@/shared/model/vendor.model';

import AlertService from '@/shared/alert/alert.service';
import { IPersonInCharge, PersonInCharge } from '@/shared/model/person-in-charge.model';
import PersonInChargeService from './person-in-charge.service';

const validations: any = {
  personInCharge: {
    position: {
      required
    },
    phone: {
      required
    },
    userId: {
      required
    }
  }
};

@Component({
  validations
})
export default class PersonInChargeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('personInChargeService') private personInChargeService: () => PersonInChargeService;
  public personInCharge: IPersonInCharge = new PersonInCharge();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('vendorService') private vendorService: () => VendorService;

  public vendors: IVendor[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personInChargeId) {
        vm.retrievePersonInCharge(to.params.personInChargeId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.personInCharge.id) {
      this.personInChargeService()
        .update(this.personInCharge)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.personInCharge.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.personInChargeService()
        .create(this.personInCharge)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.personInCharge.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePersonInCharge(personInChargeId): void {
    this.personInChargeService()
      .find(personInChargeId)
      .then(res => {
        this.personInCharge = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.vendorService()
      .retrieve()
      .then(res => {
        this.vendors = res.data;
      });
  }
}
