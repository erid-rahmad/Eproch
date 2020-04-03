import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICurrency } from '@/shared/model/currency.model';
import CurrencyService from './currency.service';

@Component
export default class CurrencyDetails extends Vue {
  @Inject('currencyService') private currencyService: () => CurrencyService;
  public currency: ICurrency = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.currencyId) {
        vm.retrieveCurrency(to.params.currencyId);
      }
    });
  }

  public retrieveCurrency(currencyId) {
    this.currencyService()
      .find(currencyId)
      .then(res => {
        this.currency = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
