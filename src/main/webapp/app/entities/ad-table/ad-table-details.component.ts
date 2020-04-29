import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADTable } from '@/shared/model/ad-table.model';
import ADTableService from './ad-table.service';

@Component
export default class ADTableDetails extends Vue {
  @Inject('aDTableService') private aDTableService: () => ADTableService;
  public aDTable: IADTable = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDTableId) {
        vm.retrieveADTable(to.params.aDTableId);
      }
    });
  }

  public retrieveADTable(aDTableId) {
    this.aDTableService()
      .find(aDTableId)
      .then(res => {
        this.aDTable = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
