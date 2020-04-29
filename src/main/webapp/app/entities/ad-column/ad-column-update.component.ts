import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADClientService from '../ad-client/ad-client.service';
import { IADClient } from '@/shared/model/ad-client.model';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import ADReferenceService from '../ad-reference/ad-reference.service';
import { IADReference } from '@/shared/model/ad-reference.model';

import ADTableService from '../ad-table/ad-table.service';
import { IADTable } from '@/shared/model/ad-table.model';

import AlertService from '@/shared/alert/alert.service';
import { IADColumn, ADColumn } from '@/shared/model/ad-column.model';
import ADColumnService from './ad-column.service';

const validations: any = {
  aDColumn: {
    name: {
      required
    },
    description: {},
    key: {},
    type: {},
    mandatory: {},
    mandatoryLogic: {},
    readOnlyLogic: {},
    updatable: {},
    defaultValue: {},
    formatPattern: {},
    minValue: {},
    maxValue: {},
    active: {},
    adClientId: {
      required
    },
    adOrganizationId: {
      required
    },
    adTableId: {
      required
    }
  }
};

@Component({
  validations
})
export default class ADColumnUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDColumnService') private aDColumnService: () => ADColumnService;
  public aDColumn: IADColumn = new ADColumn();

  @Inject('aDClientService') private aDClientService: () => ADClientService;

  public aDClients: IADClient[] = [];

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];

  @Inject('aDReferenceService') private aDReferenceService: () => ADReferenceService;

  public aDReferences: IADReference[] = [];

  @Inject('aDTableService') private aDTableService: () => ADTableService;

  public aDTables: IADTable[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDColumnId) {
        vm.retrieveADColumn(to.params.aDColumnId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDColumn.id) {
      this.aDColumnService()
        .update(this.aDColumn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDColumn.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDColumnService()
        .create(this.aDColumn)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDColumn.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADColumn(aDColumnId): void {
    this.aDColumnService()
      .find(aDColumnId)
      .then(res => {
        this.aDColumn = res;
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
    this.aDReferenceService()
      .retrieve()
      .then(res => {
        this.aDReferences = res.data;
      });
    this.aDTableService()
      .retrieve()
      .then(res => {
        this.aDTables = res.data;
      });
  }
}
