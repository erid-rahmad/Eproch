import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICCountry } from '@/shared/model/c-country.model';
import CCountryService from './c-country.service';

@Component
export default class CCountryDetails extends Vue {
  @Inject('cCountryService') private cCountryService: () => CCountryService;
  public cCountry: ICCountry = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cCountryId) {
        vm.retrieveCCountry(to.params.cCountryId);
      }
    });
  }

  public retrieveCCountry(cCountryId) {
    this.cCountryService()
      .find(cCountryId)
      .then(res => {
        this.cCountry = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
