import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ReferenceService from '../reference/reference.service';
import { IReference } from '@/shared/model/reference.model';

import AlertService from '@/shared/alert/alert.service';
import { IReferenceList, ReferenceList } from '@/shared/model/reference-list.model';
import ReferenceListService from './reference-list.service';

const validations: any = {
  referenceList: {
    name: {},
    description: {},
    value: {}
  }
};

@Component({
  validations
})
export default class ReferenceListUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('referenceListService') private referenceListService: () => ReferenceListService;
  public referenceList: IReferenceList = new ReferenceList();

  @Inject('referenceService') private referenceService: () => ReferenceService;

  public references: IReference[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.referenceListId) {
        vm.retrieveReferenceList(to.params.referenceListId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.referenceList.id) {
      this.referenceListService()
        .update(this.referenceList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.referenceList.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.referenceListService()
        .create(this.referenceList)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.referenceList.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveReferenceList(referenceListId): void {
    this.referenceListService()
      .find(referenceListId)
      .then(res => {
        this.referenceList = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.referenceService()
      .retrieve()
      .then(res => {
        this.references = res.data;
      });
  }
}
