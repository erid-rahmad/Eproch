import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompanyFunctionary } from '@/shared/model/company-functionary.model';
import CompanyFunctionaryService from './company-functionary.service';

@Component
export default class CompanyFunctionaryDetails extends Vue {
  @Inject('companyFunctionaryService') private companyFunctionaryService: () => CompanyFunctionaryService;
  public companyFunctionary: ICompanyFunctionary = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyFunctionaryId) {
        vm.retrieveCompanyFunctionary(to.params.companyFunctionaryId);
      }
    });
  }

  public retrieveCompanyFunctionary(companyFunctionaryId) {
    this.companyFunctionaryService()
      .find(companyFunctionaryId)
      .then(res => {
        this.companyFunctionary = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
