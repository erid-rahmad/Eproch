import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADColumnService from '../ad-column/ad-column.service';
import { IADColumn } from '@/shared/model/ad-column.model';

import ADClientService from '../ad-client/ad-client.service';
import { IADClient } from '@/shared/model/ad-client.model';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IADTable, ADTable } from '@/shared/model/ad-table.model';
import ADTableService from './ad-table.service';

const validations: any = {
  aDTable: {
    name: {
      required
    },
    view: {},
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
export default class ADTableUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDTableService') private aDTableService: () => ADTableService;
  public aDTable: IADTable = new ADTable();

  @Inject('aDColumnService') private aDColumnService: () => ADColumnService;

  public aDColumns: IADColumn[] = [];

  @Inject('aDClientService') private aDClientService: () => ADClientService;

  public aDClients: IADClient[] = [];

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDTableId) {
        vm.retrieveADTable(to.params.aDTableId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDTable.id) {
      this.aDTableService()
        .update(this.aDTable)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDTable.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDTableService()
        .create(this.aDTable)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDTable.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADTable(aDTableId): void {
    this.aDTableService()
      .find(aDTableId)
      .then(res => {
        this.aDTable = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.aDColumnService()
      .retrieve()
      .then(res => {
        this.aDColumns = res.data;
      });
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
