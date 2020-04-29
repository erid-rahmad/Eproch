import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADClientService from '../ad-client/ad-client.service';
import { IADClient } from '@/shared/model/ad-client.model';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

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
    writable: {},
    columnNo: {},
    columnSpan: {},
    active: {},
    adClientId: {
      required
    },
    adOrganizationId: {
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

  @Inject('aDClientService') private aDClientService: () => ADClientService;

  public aDClients: IADClient[] = [];

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];
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
    this.aDClientService()
      .retrieve()
      .then(res => {
        this.aDClients = res.data;
      });
    this.aDOrganizationService()
      .retrieve()
      .then(res => {
        this.aDOrganizations = res.data;
      });
  }
}
