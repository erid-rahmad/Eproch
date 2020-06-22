import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CCountryService from '../c-country/c-country.service';
import { ICCountry } from '@/shared/model/c-country.model';

import CRegionService from '../c-region/c-region.service';
import { ICRegion } from '@/shared/model/c-region.model';

import AlertService from '@/shared/alert/alert.service';
import { ICCity, CCity } from '@/shared/model/c-city.model';
import CCityService from './c-city.service';

const validations: any = {
  cCity: {
    name: {
      required
    },
    active: {}
  }
};

@Component({
  validations
})
export default class CCityUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cCityService') private cCityService: () => CCityService;
  public cCity: ICCity = new CCity();

  @Inject('cCountryService') private cCountryService: () => CCountryService;

  public cCountries: ICCountry[] = [];

  @Inject('cRegionService') private cRegionService: () => CRegionService;

  public cRegions: ICRegion[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cCityId) {
        vm.retrieveCCity(to.params.cCityId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cCity.id) {
      this.cCityService()
        .update(this.cCity)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cCity.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cCityService()
        .create(this.cCity)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cCity.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCCity(cCityId): void {
    this.cCityService()
      .find(cCityId)
      .then(res => {
        this.cCity = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cCountryService()
      .retrieve()
      .then(res => {
        this.cCountries = res.data;
      });
    this.cRegionService()
      .retrieve()
      .then(res => {
        this.cRegions = res.data;
      });
  }
}
