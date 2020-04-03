import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DocumentTypeBusinessCategoryService from '../document-type-business-category/document-type-business-category.service';
import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentType, DocumentType } from '@/shared/model/document-type.model';
import DocumentTypeService from './document-type.service';

const validations: any = {
  documentType: {
    name: {
      required
    },
    description: {},
    hasExpirationDate: {},
    forCompany: {},
    forProfessional: {},
    active: {}
  }
};

@Component({
  validations
})
export default class DocumentTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;
  public documentType: IDocumentType = new DocumentType();

  @Inject('documentTypeBusinessCategoryService') private documentTypeBusinessCategoryService: () => DocumentTypeBusinessCategoryService;

  public documentTypeBusinessCategories: IDocumentTypeBusinessCategory[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentTypeId) {
        vm.retrieveDocumentType(to.params.documentTypeId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.documentType.id) {
      this.documentTypeService()
        .update(this.documentType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.documentType.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentTypeService()
        .create(this.documentType)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.documentType.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocumentType(documentTypeId): void {
    this.documentTypeService()
      .find(documentTypeId)
      .then(res => {
        this.documentType = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentTypeBusinessCategoryService()
      .retrieve()
      .then(res => {
        this.documentTypeBusinessCategories = res.data;
      });
  }
}
