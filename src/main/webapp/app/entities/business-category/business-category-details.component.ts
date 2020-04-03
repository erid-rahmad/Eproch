import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBusinessCategory } from '@/shared/model/business-category.model';
import BusinessCategoryService from './business-category.service';

@Component
export default class BusinessCategoryDetails extends Vue {
  @Inject('businessCategoryService') private businessCategoryService: () => BusinessCategoryService;
  public businessCategory: IBusinessCategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.businessCategoryId) {
        vm.retrieveBusinessCategory(to.params.businessCategoryId);
      }
    });
  }

  public retrieveBusinessCategory(businessCategoryId) {
    this.businessCategoryService()
      .find(businessCategoryId)
      .then(res => {
        this.businessCategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
