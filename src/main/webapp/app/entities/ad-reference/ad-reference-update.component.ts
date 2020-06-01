import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ADReferenceListService from '../ad-reference-list/ad-reference-list.service';
import { IADReferenceList } from '@/shared/model/ad-reference-list.model';

import ADOrganizationService from '../ad-organization/ad-organization.service';
import { IADOrganization } from '@/shared/model/ad-organization.model';

import AlertService from '@/shared/alert/alert.service';
import { IADReference, ADReference } from '@/shared/model/ad-reference.model';
import ADReferenceService from './ad-reference.service';

const validations: any = {
  aDReference: {
    name: {
      required
    },
    value: {
      required
    },
    description: {},
    referenceType: {},
    active: {},
    adOrganizationId: {
      required
    }
  }
};

@Component({
  validations
})
export default class ADReferenceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('aDReferenceService') private aDReferenceService: () => ADReferenceService;
  public aDReference: IADReference = new ADReference();

  @Inject('aDReferenceListService') private aDReferenceListService: () => ADReferenceListService;

  public aDReferenceLists: IADReferenceList[] = [];

  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;

  public aDOrganizations: IADOrganization[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aDReferenceId) {
        vm.retrieveADReference(to.params.aDReferenceId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.aDReference.id) {
      this.aDReferenceService()
        .update(this.aDReference)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDReference.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.aDReferenceService()
        .create(this.aDReference)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.aDReference.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveADReference(aDReferenceId): void {
    this.aDReferenceService()
      .find(aDReferenceId)
      .then(res => {
        this.aDReference = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.aDReferenceListService()
      .retrieve()
      .then(res => {
        this.aDReferenceLists = res.data;
      });
    this.aDOrganizationService()
      .retrieve()
      .then(res => {
        this.aDOrganizations = res.data;
      });
  }
}
