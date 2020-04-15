import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CountryService from '../country/country.service';
import { ICountry } from '@/shared/model/country.model';

import RegionService from '../region/region.service';
import { IRegion } from '@/shared/model/region.model';

import AlertService from '@/shared/alert/alert.service';
import { ICity, City } from '@/shared/model/city.model';
import CityService from './city.service';

const validations: any = {
  city: {
    name: {
      required
    }
  }
};

@Component({
  validations
})
export default class CityUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cityService') private cityService: () => CityService;
  public city: ICity = new City();

  @Inject('countryService') private countryService: () => CountryService;

  public countries: ICountry[] = [];

  @Inject('regionService') private regionService: () => RegionService;

  public regions: IRegion[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cityId) {
        vm.retrieveCity(to.params.cityId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.city.id) {
      this.cityService()
        .update(this.city)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.city.updated', { param: param.id });
          //this.alertService().showAlert(message, 'info');
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    } else {
      this.cityService()
        .create(this.city)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.city.created', { param: param.id });
          //this.alertService().showAlert(message, 'success');
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    }
  }

  public retrieveCity(cityId): void {
    this.cityService()
      .find(cityId)
      .then(res => {
        this.city = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.countryService()
      .retrieve()
      .then(res => {
        this.countries = res.data;
      });
    this.regionService()
      .retrieve()
      .then(res => {
        this.regions = res.data;
      });
  }
}
