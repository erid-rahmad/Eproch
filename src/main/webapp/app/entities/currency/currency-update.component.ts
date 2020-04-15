import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ICurrency, Currency } from '@/shared/model/currency.model';
import CurrencyService from './currency.service';

/*const validations: any = {
  currency: {
    code: {
      required
    },
    symbol: {
      required
    },
    name: {
      required
    }
  }
};*/

@Component /*({
  validations
})*/
export default class CurrencyUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('currencyService') private currencyService: () => CurrencyService;
  public currency: ICurrency = new Currency();
  public isSaving = false;
  public rules = {
    code: {
      pattern: '^[A-Z]{3}$'
    }
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.currencyId) {
        vm.retrieveCurrency(to.params.currencyId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.currency.id) {
      this.currencyService()
        .update(this.currency)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.currency.updated', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    } else {
      this.currencyService()
        .create(this.currency)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.currency.created', { param: param.id });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        });
    }
  }

  public retrieveCurrency(currencyId): void {
    this.currencyService()
      .find(currencyId)
      .then(res => {
        this.currency = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
