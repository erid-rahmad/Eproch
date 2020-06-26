import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdValidationRule, AdValidationRule } from '@/shared/model/ad-validation-rule.model';
import AdValidationRuleService from './ad-validation-rule.service';

const validations: any = {
  adValidationRule: {
    uid: {
      required
    },
    name: {
      required
    },
    description: {},
    query: {},
    active: {},
    adOrganizationId: {
      required
    }
  }
};

@Component({
  validations
})
export default class AdValidationRuleUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('adValidationRuleService') private adValidationRuleService: () => AdValidationRuleService;
  public adValidationRule: IAdValidationRule = new AdValidationRule();

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adValidationRuleId) {
        vm.retrieveAdValidationRule(to.params.adValidationRuleId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.adValidationRule.id) {
      this.adValidationRuleService()
        .update(this.adValidationRule)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.adValidationRule.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.adValidationRuleService()
        .create(this.adValidationRule)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.adValidationRule.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdValidationRule(adValidationRuleId): void {
    this.adValidationRuleService()
      .find(adValidationRuleId)
      .then(res => {
        this.adValidationRule = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.aDOrganizationService()
      .retrieve()
      .then(res => {
        this.aDOrganizations = res.data;
      });
  }
}
