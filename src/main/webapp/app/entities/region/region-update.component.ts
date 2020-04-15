import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CityService from '../city/city.service';
import { ICity } from '@/shared/model/city.model';

import CountryService from '../country/country.service';
import { ICountry } from '@/shared/model/country.model';

import AlertService from '@/shared/alert/alert.service';
import { IRegion, Region } from '@/shared/model/region.model';
import RegionService from './region.service';

const validations: any = {
  region: {
    name: {
      required
    },
    countryId: {
      required
    }
  }
};

@Component({
  validations
})
export default class RegionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('regionService') private regionService: () => RegionService;
  public region: IRegion = new Region();

  @Inject('cityService') private cityService: () => CityService;

  public cities: ICity[] = [];

  @Inject('countryService') private countryService: () => CountryService;

  public countries: ICountry[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regionId) {
        vm.retrieveRegion(to.params.regionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.region.id) {
      this.regionService()
        .update(this.region)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.region.updated', { param: param.id });
          //this.alertService().showAlert(message, 'info');
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    } else {
      this.regionService()
        .create(this.region)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.region.created', { param: param.id });
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

  public retrieveRegion(regionId): void {
    this.regionService()
      .find(regionId)
      .then(res => {
        this.region = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cityService()
      .retrieve()
      .then(res => {
        this.cities = res.data;
      });
    this.countryService()
      .retrieve()
      .then(res => {
        this.countries = res.data;
      });
  }
}
