import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADReference } from '@/shared/model/ad-reference.model';
import ADReferenceService from './ad-reference.service';

@Component
export default class ADReferenceDetails extends Vue {
  @Inject('aDReferenceService') private aDReferenceService: () => ADReferenceService;
  public aDReference: IADReference = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDReferenceId) {
        vm.retrieveADReference(to.params.aDReferenceId);
      }
    });
  }

  public retrieveADReference(aDReferenceId) {
    this.aDReferenceService()
      .find(aDReferenceId)
      .then(res => {
        this.aDReference = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
