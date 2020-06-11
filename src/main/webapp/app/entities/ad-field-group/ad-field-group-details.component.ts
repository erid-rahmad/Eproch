import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADFieldGroup } from '@/shared/model/ad-field-group.model';
import ADFieldGroupService from './ad-field-group.service';

@Component
export default class ADFieldGroupDetails extends Vue {
  @Inject('aDFieldGroupService') private aDFieldGroupService: () => ADFieldGroupService;
  public aDFieldGroup: IADFieldGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDFieldGroupId) {
        vm.retrieveADFieldGroup(to.params.aDFieldGroupId);
      }
    });
  }

  public retrieveADFieldGroup(aDFieldGroupId) {
    this.aDFieldGroupService()
      .find(aDFieldGroupId)
      .then(res => {
        this.aDFieldGroup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
