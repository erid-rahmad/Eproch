import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IADOrganization, ADOrganization } from '@/shared/model/ad-organization.model';
import ADOrganizationService from './ad-organization.service';

const validations: any = {
  aDOrganization: {
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
export default class ADOrganizationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;
  public aDOrganization: IADOrganization = new ADOrganization();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDOrganizationId) {
        vm.retrieveADOrganization(to.params.aDOrganizationId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDOrganization.id) {
      this.aDOrganizationService()
        .update(this.aDOrganization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDOrganization.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDOrganizationService()
        .create(this.aDOrganization)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDOrganization.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADOrganization(aDOrganizationId): void {
    this.aDOrganizationService()
      .find(aDOrganizationId)
      .then(res => {
        this.aDOrganization = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
