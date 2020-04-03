import { Component, Vue, Inject } from 'vue-property-decorator';

import { IReferenceList } from '@/shared/model/reference-list.model';
import ReferenceListService from './reference-list.service';

@Component
export default class ReferenceListDetails extends Vue {
  @Inject('referenceListService') private referenceListService: () => ReferenceListService;
  public referenceList: IReferenceList = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.referenceListId) {
        vm.retrieveReferenceList(to.params.referenceListId);
      }
    });
  }

  public retrieveReferenceList(referenceListId) {
    this.referenceListService()
      .find(referenceListId)
      .then(res => {
        this.referenceList = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
