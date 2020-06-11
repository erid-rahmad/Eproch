import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADTab } from '@/shared/model/ad-tab.model';
import ADTabService from './ad-tab.service';

@Component
export default class ADTabDetails extends Vue {
  @Inject('aDTabService') private aDTabService: () => ADTabService;
  public aDTab: IADTab = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDTabId) {
        vm.retrieveADTab(to.params.aDTabId);
      }
    });
  }

  public retrieveADTab(aDTabId) {
    this.aDTabService()
      .find(aDTabId)
      .then(res => {
        this.aDTab = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
