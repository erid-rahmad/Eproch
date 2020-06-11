import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADFieldService from '../ad-field/ad-field.service';
import { IADField } from '@/shared/model/ad-field.model';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import ADTableService from '../ad-table/ad-table.service';
import { IADTable } from '@/shared/model/ad-table.model';

import ADColumnService from '../ad-column/ad-column.service';
import { IADColumn } from '@/shared/model/ad-column.model';

import ADWindowService from '../ad-window/ad-window.service';
import { IADWindow } from '@/shared/model/ad-window.model';

import AlertService from '@/shared/alert/alert.service';
import { IADTab, ADTab } from '@/shared/model/ad-tab.model';
import ADTabService from './ad-tab.service';

const validations: any = {
  aDTab: {
    name: {
      required
    },
    description: {},
    iconName: {},
    targetEndpoint: {},
    writable: {},
    displayLogic: {},
    readOnlyLogic: {},
    filterQuery: {},
    orderQuery: {},
    tabSequence: {},
    active: {},
    adOrganizationId: {
      required
    },
    adTableId: {
      required
    },
    adWindowId: {
      required
    }
  }
};

@Component({
  validations
})
export default class ADTabUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDTabService') private aDTabService: () => ADTabService;
  public aDTab: IADTab = new ADTab();

  public aDTabs: IADTab[] = [];

  @Inject('aDFieldService') private aDFieldService: () => ADFieldService;

  public aDFields: IADField[] = [];

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];

  @Inject('aDTableService') private aDTableService: () => ADTableService;

  public aDTables: IADTable[] = [];

  @Inject('aDColumnService') private aDColumnService: () => ADColumnService;

  public aDColumns: IADColumn[] = [];

  @Inject('aDWindowService') private aDWindowService: () => ADWindowService;

  public aDWindows: IADWindow[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDTabId) {
        vm.retrieveADTab(to.params.aDTabId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDTab.id) {
      this.aDTabService()
        .update(this.aDTab)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDTab.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDTabService()
        .create(this.aDTab)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDTab.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADTab(aDTabId): void {
    this.aDTabService()
      .find(aDTabId)
      .then(res => {
        this.aDTab = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.aDTabService()
      .retrieve()
      .then(res => {
        this.aDTabs = res.data;
      });
    this.aDFieldService()
      .retrieve()
      .then(res => {
        this.aDFields = res.data;
      });
    this.aDOrganizationService()
      .retrieve()
      .then(res => {
        this.aDOrganizations = res.data;
      });
    this.aDTableService()
      .retrieve()
      .then(res => {
        this.aDTables = res.data;
      });
    this.aDColumnService()
      .retrieve()
      .then(res => {
        this.aDColumns = res.data;
      });
    this.aDColumnService()
      .retrieve()
      .then(res => {
        this.aDColumns = res.data;
      });
    this.aDWindowService()
      .retrieve()
      .then(res => {
        this.aDWindows = res.data;
      });
    this.aDTabService()
      .retrieve()
      .then(res => {
        this.aDTabs = res.data;
      });
  }
}
