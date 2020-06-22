import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICRegion } from '@/shared/model/c-region.model';
import CRegionService from './c-region.service';

@Component
export default class CRegionDetails extends Vue {
  @Inject('cRegionService') private cRegionService: () => CRegionService;
  public cRegion: ICRegion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cRegionId) {
        vm.retrieveCRegion(to.params.cRegionId);
      }
    });
  }

  public retrieveCRegion(cRegionId) {
    this.cRegionService()
      .find(cRegionId)
      .then(res => {
        this.cRegion = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
