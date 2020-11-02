import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CCityService from '../c-city/c-city.service';
import { ICCity } from '@/shared/model/c-city.model';

import AlertService from '@/shared/alert/alert.service';
import { ICLocation, CLocation } from '@/shared/model/c-location.model';
import CLocationService from './c-location.service';

const validations: any = {
  cLocation: {
    streetAddress: {
      required
    },
    postalCode: {},
    taxInvoiceAddress: {},
    active: {},
    cityId: {
      required
    }
  }
};

@Component({
  validations
})
export default class CLocationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cLocationService') private cLocationService: () => CLocationService;
  public cLocation: ICLocation = new CLocation();

  @Inject('cCityService') private cCityService: () => CCityService;

  public cCities: ICCity[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cLocationId) {
        vm.retrieveCLocation(to.params.cLocationId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cLocation.id) {
      this.cLocationService()
        .update(this.cLocation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cLocation.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cLocationService()
        .create(this.cLocation)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cLocation.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCLocation(cLocationId): void {
    this.cLocationService()
      .find(cLocationId)
      .then(res => {
        this.cLocation = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cCityService()
      .retrieve()
      .then(res => {
        this.cCities = res.data;
      });
  }
}
