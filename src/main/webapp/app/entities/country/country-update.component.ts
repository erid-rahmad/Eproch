import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CurrencyService from '../currency/currency.service';
import { ICurrency } from '@/shared/model/currency.model';

import RegionService from '../region/region.service';
import { IRegion } from '@/shared/model/region.model';

import CityService from '../city/city.service';
import { ICity } from '@/shared/model/city.model';

import AlertService from '@/shared/alert/alert.service';
import { ICountry, Country } from '@/shared/model/country.model';
import CountryService from './country.service';

/*const validations: any = {
  country: {
    name: {
      required
    },
    code: {
      required
    }
  }
};*/

@Component
//({
//  validations
//})
export default class CountryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;

  @Inject('countryService') private countryService: () => CountryService;
  public country: ICountry = new Country();

  @Inject('currencyService') private currencyService: () => CurrencyService;
  public currencies: ICurrency[] = [];

  @Inject('regionService') private regionService: () => RegionService;
  public regions: IRegion[] = [];

  @Inject('cityService') private cityService: () => CityService;
  public cities: ICity[] = [];

  public isSaving = false;
  public rules = {
    code: {
      pattern: '^[A-Z]{2}$'
    }
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.countryId) {
        vm.retrieveCountry(to.params.countryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.country.id) {
      this.countryService()
        .update(this.country)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.country.updated', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    } else {
      this.countryService()
        .create(this.country)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.country.created', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    }
  }

  public retrieveCountry(countryId): void {
    this.countryService()
      .find(countryId)
      .then(res => {
        this.country = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.currencyService()
      .retrieve()
      .then(res => {
        this.currencies = res.data;
      });
    this.regionService()
      .retrieve()
      .then(res => {
        this.regions = res.data;
      });
    this.cityService()
      .retrieve()
      .then(res => {
        this.cities = res.data;
      });
  }
}
