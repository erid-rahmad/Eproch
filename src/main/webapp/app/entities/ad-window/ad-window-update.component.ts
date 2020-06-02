import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADTabService from '../ad-tab/ad-tab.service';
import { IADTab } from '@/shared/model/ad-tab.model';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IADWindow, ADWindow } from '@/shared/model/ad-window.model';
import ADWindowService from './ad-window.service';

const validations: any = {
  aDWindow: {
    name: {
      required
    },
    description: {},
    titleLogic: {},
    type: {
      required
    },
    active: {},
    adOrganizationId: {
      required
    }
  }
};

@Component({
  validations
})
export default class ADWindowUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDWindowService') private aDWindowService: () => ADWindowService;
  public aDWindow: IADWindow = new ADWindow();

  @Inject('aDTabService') private aDTabService: () => ADTabService;

  public aDTabs: IADTab[] = [];

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDWindowId) {
        vm.retrieveADWindow(to.params.aDWindowId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDWindow.id) {
      this.aDWindowService()
        .update(this.aDWindow)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDWindow.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDWindowService()
        .create(this.aDWindow)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDWindow.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADWindow(aDWindowId): void {
    this.aDWindowService()
      .find(aDWindowId)
      .then(res => {
        this.aDWindow = res;
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
    this.aDOrganizationService()
      .retrieve()
      .then(res => {
        this.aDOrganizations = res.data;
      });
  }
}
