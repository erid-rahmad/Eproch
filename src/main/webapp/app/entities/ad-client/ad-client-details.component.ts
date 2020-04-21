import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADClient } from '@/shared/model/ad-client.model';
import ADClientService from './ad-client.service';

@Component
export default class ADClientDetails extends Vue {
  @Inject('aDClientService') private aDClientService: () => ADClientService;
  public aDClient: IADClient = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDClientId) {
        vm.retrieveADClient(to.params.aDClientId);
      }
    });
  }

  public retrieveADClient(aDClientId) {
    this.aDClientService()
      .find(aDClientId)
      .then(res => {
        this.aDClient = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
