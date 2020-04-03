import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPersonInCharge } from '@/shared/model/person-in-charge.model';
import PersonInChargeService from './person-in-charge.service';

@Component
export default class PersonInChargeDetails extends Vue {
  @Inject('personInChargeService') private personInChargeService: () => PersonInChargeService;
  public personInCharge: IPersonInCharge = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personInChargeId) {
        vm.retrievePersonInCharge(to.params.personInChargeId);
      }
    });
  }

  public retrievePersonInCharge(personInChargeId) {
    this.personInChargeService()
      .find(personInChargeId)
      .then(res => {
        this.personInCharge = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
