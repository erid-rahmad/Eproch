import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdValidationRule } from '@/shared/model/ad-validation-rule.model';
import AdValidationRuleService from './ad-validation-rule.service';

@Component
export default class AdValidationRuleDetails extends Vue {
  @Inject('adValidationRuleService') private adValidationRuleService: () => AdValidationRuleService;
  public adValidationRule: IAdValidationRule = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adValidationRuleId) {
        vm.retrieveAdValidationRule(to.params.adValidationRuleId);
      }
    });
  }

  public retrieveAdValidationRule(adValidationRuleId) {
    this.adValidationRuleService()
      .find(adValidationRuleId)
      .then(res => {
        this.adValidationRule = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
