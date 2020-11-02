import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICCity } from '@/shared/model/c-city.model';
import CCityService from './c-city.service';

@Component
export default class CCityDetails extends Vue {
  @Inject('cCityService') private cCityService: () => CCityService;
  public cCity: ICCity = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cCityId) {
        vm.retrieveCCity(to.params.cCityId);
      }
    });
  }

  public retrieveCCity(cCityId) {
    this.cCityService()
      .find(cCityId)
      .then(res => {
        this.cCity = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
