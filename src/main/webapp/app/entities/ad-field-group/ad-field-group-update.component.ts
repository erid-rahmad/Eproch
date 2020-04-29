import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IADFieldGroup, ADFieldGroup } from '@/shared/model/ad-field-group.model';
import ADFieldGroupService from './ad-field-group.service';

const validations: any = {
  aDFieldGroup: {
    name: {
      required
    },
    collapsible: {},
    collapseByDefault: {},
    active: {}
  }
};

@Component({
  validations
})
export default class ADFieldGroupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDFieldGroupService') private aDFieldGroupService: () => ADFieldGroupService;
  public aDFieldGroup: IADFieldGroup = new ADFieldGroup();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDFieldGroupId) {
        vm.retrieveADFieldGroup(to.params.aDFieldGroupId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDFieldGroup.id) {
      this.aDFieldGroupService()
        .update(this.aDFieldGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDFieldGroup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDFieldGroupService()
        .create(this.aDFieldGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDFieldGroup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADFieldGroup(aDFieldGroupId): void {
    this.aDFieldGroupService()
      .find(aDFieldGroupId)
      .then(res => {
        this.aDFieldGroup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
