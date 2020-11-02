import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CCityService from '../c-city/c-city.service';
import { ICCity } from '@/shared/model/c-city.model';

import CCountryService from '../c-country/c-country.service';
import { ICCountry } from '@/shared/model/c-country.model';

import AlertService from '@/shared/alert/alert.service';
import { ICRegion, CRegion } from '@/shared/model/c-region.model';
import CRegionService from './c-region.service';

const validations: any = {
  cRegion: {
    name: {
      required
    },
    active: {},
    countryId: {
      required
    }
  }
};

@Component({
  validations
})
export default class CRegionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cRegionService') private cRegionService: () => CRegionService;
  public cRegion: ICRegion = new CRegion();

  @Inject('cCityService') private cCityService: () => CCityService;

  public cCities: ICCity[] = [];

  @Inject('cCountryService') private cCountryService: () => CCountryService;

  public cCountries: ICCountry[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cRegionId) {
        vm.retrieveCRegion(to.params.cRegionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cRegion.id) {
      this.cRegionService()
        .update(this.cRegion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cRegion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cRegionService()
        .create(this.cRegion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cRegion.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCRegion(cRegionId): void {
    this.cRegionService()
      .find(cRegionId)
      .then(res => {
        this.cRegion = res;
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
    this.cCountryService()
      .retrieve()
      .then(res => {
        this.cCountries = res.data;
      });
  }
}
