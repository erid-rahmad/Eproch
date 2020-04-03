import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DocumentTypeService from '../document-type/document-type.service';
import { IDocumentType } from '@/shared/model/document-type.model';

import BusinessCategoryService from '../business-category/business-category.service';
import { IBusinessCategory } from '@/shared/model/business-category.model';

import AlertService from '@/shared/alert/alert.service';
import { IDocumentTypeBusinessCategory, DocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';
import DocumentTypeBusinessCategoryService from './document-type-business-category.service';

const validations: any = {
  documentTypeBusinessCategory: {
    mandatory: {},
    additional: {}
  }
};

@Component({
  validations
})
export default class DocumentTypeBusinessCategoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('documentTypeBusinessCategoryService') private documentTypeBusinessCategoryService: () => DocumentTypeBusinessCategoryService;
  public documentTypeBusinessCategory: IDocumentTypeBusinessCategory = new DocumentTypeBusinessCategory();

  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;

  public documentTypes: IDocumentType[] = [];

  @Inject('businessCategoryService') private businessCategoryService: () => BusinessCategoryService;

  public businessCategories: IBusinessCategory[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentTypeBusinessCategoryId) {
        vm.retrieveDocumentTypeBusinessCategory(to.params.documentTypeBusinessCategoryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.documentTypeBusinessCategory.id) {
      this.documentTypeBusinessCategoryService()
        .update(this.documentTypeBusinessCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.documentTypeBusinessCategory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.documentTypeBusinessCategoryService()
        .create(this.documentTypeBusinessCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.documentTypeBusinessCategory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveDocumentTypeBusinessCategory(documentTypeBusinessCategoryId): void {
    this.documentTypeBusinessCategoryService()
      .find(documentTypeBusinessCategoryId)
      .then(res => {
        this.documentTypeBusinessCategory = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentTypeService()
      .retrieve()
      .then(res => {
        this.documentTypes = res.data;
      });
    this.businessCategoryService()
      .retrieve()
      .then(res => {
        this.businessCategories = res.data;
      });
  }
}
