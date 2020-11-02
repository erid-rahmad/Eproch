import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICLocation } from '@/shared/model/c-location.model';
import CLocationService from './c-location.service';

@Component
export default class CLocationDetails extends Vue {
  @Inject('cLocationService') private cLocationService: () => CLocationService;
  public cLocation: ICLocation = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cLocationId) {
        vm.retrieveCLocation(to.params.cLocationId);
      }
    });
  }

  public retrieveCLocation(cLocationId) {
    this.cLocationService()
      .find(cLocationId)
      .then(res => {
        this.cLocation = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
