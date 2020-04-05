import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICity } from '@/shared/model/city.model';
import CityService from './city.service';

@Component
export default class CityDetails extends Vue {
  @Inject('cityService') private cityService: () => CityService;
  public city: ICity = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cityId) {
        vm.retrieveCity(to.params.cityId);
      }
    });
  }

  public retrieveCity(cityId) {
    this.cityService()
      .find(cityId)
      .then(res => {
        this.city = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
