import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ICBank, CBank } from '@/shared/model/c-bank.model';
import CBankService from './c-bank.service';

const validations: any = {
  cBank: {
    name: {
      required
    },
    value: {
      required
    },
    shortName: {
      required
    },
    description: {},
    swiftCode: {},
    active: {}
  }
};

@Component({
  validations
})
export default class CBankUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cBankService') private cBankService: () => CBankService;
  public cBank: ICBank = new CBank();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cBankId) {
        vm.retrieveCBank(to.params.cBankId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cBank.id) {
      this.cBankService()
        .update(this.cBank)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cBank.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cBankService()
        .create(this.cBank)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.cBank.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCBank(cBankId): void {
    this.cBankService()
      .find(cBankId)
      .then(res => {
        this.cBank = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
