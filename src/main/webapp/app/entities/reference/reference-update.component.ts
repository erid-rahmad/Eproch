import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ReferenceListService from '../reference-list/reference-list.service';
import { IReferenceList } from '@/shared/model/reference-list.model';

import AlertService from '@/shared/alert/alert.service';
import { IReference, Reference } from '@/shared/model/reference.model';
import ReferenceService from './reference.service';

const validations: any = {
  reference: {
    name: {
      required
    },
    description: {},
    referenceType: {}
  }
};

@Component({
  validations
})
export default class ReferenceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('referenceService') private referenceService: () => ReferenceService;
  public reference: IReference = new Reference();

  @Inject('referenceListService') private referenceListService: () => ReferenceListService;

  public referenceLists: IReferenceList[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.referenceId) {
        vm.retrieveReference(to.params.referenceId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.reference.id) {
      this.referenceService()
        .update(this.reference)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.reference.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.referenceService()
        .create(this.reference)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.reference.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveReference(referenceId): void {
    this.referenceService()
      .find(referenceId)
      .then(res => {
        this.reference = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.referenceListService()
      .retrieve()
      .then(res => {
        this.referenceLists = res.data;
      });
  }
}
