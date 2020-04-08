import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDocumentType } from '@/shared/model/document-type.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import DocumentTypeService from './document-type.service';

@Component
export default class DocumentType extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;
  private showDeleteDialog: boolean = false;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'name';
  public reverse = false;
  public totalItems = 0;

  public documentTypes: IDocumentType[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDocumentTypes();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllDocumentTypes();
  }

  public retrieveAllDocumentTypes(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.documentTypeService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.documentTypes = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IDocumentType): void {
    this.removeId = instance.id;
    this.showDeleteDialog = true;
  }

  public removeDocumentType(): void {
    this.documentTypeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.documentType.deleted', { param: this.removeId });
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        })
        this.removeId = null;
        this.retrieveAllDocumentTypes();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public handleSizeChange(size: number) {
    this.itemsPerPage = size;
    this.retrieveAllDocumentTypes();
  }

  public transition(): void {
    this.retrieveAllDocumentTypes();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    this.transition();
  }

  public closeDialog(): void {
    this.showDeleteDialog = false;
  }

  public openDetails(instance: IDocumentType) {
    this.$router.push(`/document-type/${instance.id}/view`);
  }

  public add() {
    this.$router.push('/document-type/new')
  }

  public edit(instance: IDocumentType) {
    this.$router.push(`/document-type/${instance.id}/edit`);
  }
}
