import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IADClient, ADClient } from '@/shared/model/ad-client.model';
import ADClientService from './ad-client.service';

const validations: any = {
  aDClient: {
    name: {
      required
    },
    code: {
      required
    },
    description: {},
    active: {}
  }
};

@Component({
  validations
})
export default class ADClientUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDClientService') private aDClientService: () => ADClientService;
  public aDClient: IADClient = new ADClient();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDClientId) {
        vm.retrieveADClient(to.params.aDClientId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDClient.id) {
      this.aDClientService()
        .update(this.aDClient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDClient.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDClientService()
        .create(this.aDClient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDClient.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADClient(aDClientId): void {
    this.aDClientService()
      .find(aDClientId)
      .then(res => {
        this.aDClient = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
