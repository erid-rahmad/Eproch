import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICBank } from '@/shared/model/c-bank.model';
import CBankService from './c-bank.service';

@Component
export default class CBankDetails extends Vue {
  @Inject('cBankService') private cBankService: () => CBankService;
  public cBank: ICBank = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cBankId) {
        vm.retrieveCBank(to.params.cBankId);
      }
    });
  }

  public retrieveCBank(cBankId) {
    this.cBankService()
      .find(cBankId)
      .then(res => {
        this.cBank = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
