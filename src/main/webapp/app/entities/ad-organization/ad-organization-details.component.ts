import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADOrganization } from '@/shared/model/ad-organization.model';
import ADOrganizationService from './ad-organization.service';

@Component
export default class ADOrganizationDetails extends Vue {
  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;
  public aDOrganization: IADOrganization = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDOrganizationId) {
        vm.retrieveADOrganization(to.params.aDOrganizationId);
      }
    });
  }

  public retrieveADOrganization(aDOrganizationId) {
    this.aDOrganizationService()
      .find(aDOrganizationId)
      .then(res => {
        this.aDOrganization = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
