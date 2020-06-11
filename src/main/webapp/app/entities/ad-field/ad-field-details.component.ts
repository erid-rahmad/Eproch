import { Component, Vue, Inject } from 'vue-property-decorator';

import { IADField } from '@/shared/model/ad-field.model';
import ADFieldService from './ad-field.service';

@Component
export default class ADFieldDetails extends Vue {
  @Inject('aDFieldService') private aDFieldService: () => ADFieldService;
  public aDField: IADField = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDFieldId) {
        vm.retrieveADField(to.params.aDFieldId);
      }
    });
  }

  public retrieveADField(aDFieldId) {
    this.aDFieldService()
      .find(aDFieldId)
      .then(res => {
        this.aDField = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
