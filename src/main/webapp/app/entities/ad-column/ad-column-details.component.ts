import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADColumn } from '@/shared/model/ad-column.model';
import ADColumnService from './ad-column.service';

@Component
export default class ADColumnDetails extends Vue {
  @Inject('aDColumnService') private aDColumnService: () => ADColumnService;
  public aDColumn: IADColumn = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDColumnId) {
        vm.retrieveADColumn(to.params.aDColumnId);
      }
    });
  }

  public retrieveADColumn(aDColumnId) {
    this.aDColumnService()
      .find(aDColumnId)
      .then(res => {
        this.aDColumn = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
