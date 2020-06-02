import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import ADReferenceService from '../ad-reference/ad-reference.service';
import { IADReference } from '@/shared/model/ad-reference.model';

import ADColumnService from '../ad-column/ad-column.service';
import { IADColumn } from '@/shared/model/ad-column.model';

import AdValidationRuleService from '../ad-validation-rule/ad-validation-rule.service';
import { IAdValidationRule } from '@/shared/model/ad-validation-rule.model';

import ADTabService from '../ad-tab/ad-tab.service';
import { IADTab } from '@/shared/model/ad-tab.model';

import AlertService from '@/shared/alert/alert.service';
import { IADField, ADField } from '@/shared/model/ad-field.model';
import ADFieldService from './ad-field.service';

const validations: any = {
  aDField: {
    name: {
      required
    },
    description: {},
    hint: {},
    staticText: {},
    staticField: {},
    labelOnly: {},
    showLabel: {},
    showInGrid: {},
    showInDetail: {},
    gridSequence: {},
    detailSequence: {},
    displayLogic: {},
    readOnlyLogic: {},
    writable: {},
    columnNo: {},
    columnSpan: {},
    updatable: {},
    alwaysUpdatable: {},
    copyable: {},
    defaultValue: {},
    formatPattern: {},
    active: {},
    adOrganizationId: {
      required
    },
    adColumnId: {
      required
    },
    adTabId: {
      required
    }
  }
};

@Component({
  validations
})
export default class ADFieldUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDFieldService') private aDFieldService: () => ADFieldService;
  public aDField: IADField = new ADField();

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];

  @Inject('aDReferenceService') private aDReferenceService: () => ADReferenceService;

  public aDReferences: IADReference[] = [];

  @Inject('aDColumnService') private aDColumnService: () => ADColumnService;

  public aDColumns: IADColumn[] = [];

  @Inject('adValidationRuleService') private adValidationRuleService: () => AdValidationRuleService;

  public adValidationRules: IAdValidationRule[] = [];

  @Inject('aDTabService') private aDTabService: () => ADTabService;

  public aDTabs: IADTab[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDFieldId) {
        vm.retrieveADField(to.params.aDFieldId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDField.id) {
      this.aDFieldService()
        .update(this.aDField)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDField.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDFieldService()
        .create(this.aDField)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDField.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADField(aDFieldId): void {
    this.aDFieldService()
      .find(aDFieldId)
      .then(res => {
        this.aDField = res;
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
    this.aDReferenceService()
      .retrieve()
      .then(res => {
        this.aDReferences = res.data;
      });
    this.aDColumnService()
      .retrieve()
      .then(res => {
        this.aDColumns = res.data;
      });
    this.adValidationRuleService()
      .retrieve()
      .then(res => {
        this.adValidationRules = res.data;
      });
    this.aDTabService()
      .retrieve()
      .then(res => {
        this.aDTabs = res.data;
      });
  }
}
