import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ICCurrency, CCurrency } from '@/shared/model/c-currency.model';
import CCurrencyService from './c-currency.service';

const validations: any = {
  cCurrency: {
    code: {
      required
    },
    symbol: {
      required
    },
    name: {
      required
    },
    active: {}
  }
};

@Component({
  validations
})
export default class CCurrencyUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cCurrencyService') private cCurrencyService: () => CCurrencyService;
  public cCurrency: ICCurrency = new CCurrency();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cCurrencyId) {
        vm.retrieveCCurrency(to.params.cCurrencyId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cCurrency.id) {
      this.cCurrencyService()
        .update(this.cCurrency)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cCurrency.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cCurrencyService()
        .create(this.cCurrency)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cCurrency.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCCurrency(cCurrencyId): void {
    this.cCurrencyService()
      .find(cCurrencyId)
      .then(res => {
        this.cCurrency = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
