import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICCurrency } from '@/shared/model/c-currency.model';
import CCurrencyService from './c-currency.service';

@Component
export default class CCurrencyDetails extends Vue {
  @Inject('cCurrencyService') private cCurrencyService: () => CCurrencyService;
  public cCurrency: ICCurrency = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cCurrencyId) {
        vm.retrieveCCurrency(to.params.cCurrencyId);
      }
    });
  }

  public retrieveCCurrency(cCurrencyId) {
    this.cCurrencyService()
      .find(cCurrencyId)
      .then(res => {
        this.cCurrency = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
