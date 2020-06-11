import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADReferenceList } from '@/shared/model/ad-reference-list.model';
import ADReferenceListService from './ad-reference-list.service';

@Component
export default class ADReferenceListDetails extends Vue {
  @Inject('aDReferenceListService') private aDReferenceListService: () => ADReferenceListService;
  public aDReferenceList: IADReferenceList = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDReferenceListId) {
        vm.retrieveADReferenceList(to.params.aDReferenceListId);
      }
    });
  }

  public retrieveADReferenceList(aDReferenceListId) {
    this.aDReferenceListService()
      .find(aDReferenceListId)
      .then(res => {
        this.aDReferenceList = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
