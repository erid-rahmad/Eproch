import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPermission } from '@/shared/model/permission.model';
import PermissionService from './permission.service';

@Component
export default class PermissionDetails extends Vue {
  @Inject('permissionService') private permissionService: () => PermissionService;
  public permission: IPermission = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.permissionId) {
        vm.retrievePermission(to.params.permissionId);
      }
    });
  }

  public retrievePermission(permissionId) {
    this.permissionService()
      .find(permissionId)
      .then(res => {
        this.permission = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
