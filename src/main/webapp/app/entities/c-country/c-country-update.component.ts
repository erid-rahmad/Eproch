import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import CCurrencyService from '../c-currency/c-currency.service';
import { ICCurrency } from '@/shared/model/c-currency.model';

import CRegionService from '../c-region/c-region.service';
import { ICRegion } from '@/shared/model/c-region.model';

import CCityService from '../c-city/c-city.service';
import { ICCity } from '@/shared/model/c-city.model';

import AlertService from '@/shared/alert/alert.service';
import { ICCountry, CCountry } from '@/shared/model/c-country.model';
import CCountryService from './c-country.service';

const validations: any = {
  cCountry: {
    name: {
      required
    },
    code: {
      required
    },
    withRegion: {},
    active: {},
    currencyId: {
      required
    }
  }
};

@Component({
  validations
})
export default class CCountryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cCountryService') private cCountryService: () => CCountryService;
  public cCountry: ICCountry = new CCountry();

  @Inject('cCurrencyService') private cCurrencyService: () => CCurrencyService;

  public cCurrencies: ICCurrency[] = [];

  @Inject('cRegionService') private cRegionService: () => CRegionService;

  public cRegions: ICRegion[] = [];

  @Inject('cCityService') private cCityService: () => CCityService;

  public cCities: ICCity[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cCountryId) {
        vm.retrieveCCountry(to.params.cCountryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cCountry.id) {
      this.cCountryService()
        .update(this.cCountry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cCountry.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cCountryService()
        .create(this.cCountry)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cCountry.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCCountry(cCountryId): void {
    this.cCountryService()
      .find(cCountryId)
      .then(res => {
        this.cCountry = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cCurrencyService()
      .retrieve()
      .then(res => {
        this.cCurrencies = res.data;
      });
    this.cRegionService()
      .retrieve()
      .then(res => {
        this.cRegions = res.data;
      });
    this.cCityService()
      .retrieve()
      .then(res => {
        this.cCities = res.data;
      });
  }
}
