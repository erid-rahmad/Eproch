import { Component, Vue, Inject } from 'vue-property-decorator';

import { IReference } from '@/shared/model/reference.model';
import ReferenceService from './reference.service';

@Component
export default class ReferenceDetails extends Vue {
  @Inject('referenceService') private referenceService: () => ReferenceService;
  public reference: IReference = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.referenceId) {
        vm.retrieveReference(to.params.referenceId);
      }
    });
  }

  public retrieveReference(referenceId) {
    this.referenceService()
      .find(referenceId)
      .then(res => {
        this.reference = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
