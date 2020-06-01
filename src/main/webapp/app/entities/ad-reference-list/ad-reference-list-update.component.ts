import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import ADReferenceService from '../ad-reference/ad-reference.service';
import { IADReference } from '@/shared/model/ad-reference.model';

import AlertService from '@/shared/alert/alert.service';
import { IADReferenceList, ADReferenceList } from '@/shared/model/ad-reference-list.model';
import ADReferenceListService from './ad-reference-list.service';

const validations: any = {
  aDReferenceList: {
    name: {
      required
    },
    value: {
      required
    },
    description: {},
    active: {},
    adOrganizationId: {
      required
    },
    adReferenceId: {
      required
    }
  }
};

@Component({
  validations
})
export default class ADReferenceListUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDReferenceListService') private aDReferenceListService: () => ADReferenceListService;
  public aDReferenceList: IADReferenceList = new ADReferenceList();

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];

  @Inject('aDReferenceService') private aDReferenceService: () => ADReferenceService;

  public aDReferences: IADReference[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDReferenceListId) {
        vm.retrieveADReferenceList(to.params.aDReferenceListId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDReferenceList.id) {
      this.aDReferenceListService()
        .update(this.aDReferenceList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDReferenceList.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDReferenceListService()
        .create(this.aDReferenceList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDReferenceList.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADReferenceList(aDReferenceListId): void {
    this.aDReferenceListService()
      .find(aDReferenceListId)
      .then(res => {
        this.aDReferenceList = res;
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
  }
}
