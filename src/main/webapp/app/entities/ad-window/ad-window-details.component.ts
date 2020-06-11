import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADWindow } from '@/shared/model/ad-window.model';
import ADWindowService from './ad-window.service';

@Component
export default class ADWindowDetails extends Vue {
  @Inject('aDWindowService') private aDWindowService: () => ADWindowService;
  public aDWindow: IADWindow = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDWindowId) {
        vm.retrieveADWindow(to.params.aDWindowId);
      }
    });
  }

  public retrieveADWindow(aDWindowId) {
    this.aDWindowService()
      .find(aDWindowId)
      .then(res => {
        this.aDWindow = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
