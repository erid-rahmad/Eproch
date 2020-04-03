import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DocumentTypeBusinessCategoryService from './document-type-business-category.service';

@Component
export default class DocumentTypeBusinessCategory extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('documentTypeBusinessCategoryService') private documentTypeBusinessCategoryService: () => DocumentTypeBusinessCategoryService;
  private removeId: number = null;

  public documentTypeBusinessCategories: IDocumentTypeBusinessCategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentTypeBusinessCategorys();
  }

  public clear(): void {
    this.retrieveAllDocumentTypeBusinessCategorys();
  }

  public retrieveAllDocumentTypeBusinessCategorys(): void {
    this.isFetching = true;

    this.documentTypeBusinessCategoryService()
      .retrieve()
      .then(
        res => {
          this.documentTypeBusinessCategories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentTypeBusinessCategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDocumentTypeBusinessCategory(): void {
    this.documentTypeBusinessCategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.documentTypeBusinessCategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllDocumentTypeBusinessCategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
