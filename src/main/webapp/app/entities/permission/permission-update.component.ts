import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IPermission, Permission } from '@/shared/model/permission.model';
import PermissionService from './permission.service';

const validations: any = {
  permission: {
    name: {
      required
    },
    module: {
      required
    },
    canWrite: {}
  }
};

@Component({
  validations
})
export default class PermissionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('permissionService') private permissionService: () => PermissionService;
  public permission: IPermission = new Permission();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.permissionId) {
        vm.retrievePermission(to.params.permissionId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.permission.id) {
      this.permissionService()
        .update(this.permission)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.permission.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.permissionService()
        .create(this.permission)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.permission.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePermission(permissionId): void {
    this.permissionService()
      .find(permissionId)
      .then(res => {
        this.permission = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
