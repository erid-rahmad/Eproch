import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISupportingDocument } from '@/shared/model/supporting-document.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import SupportingDocumentService from './supporting-document.service';

@Component
export default class SupportingDocument extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('supportingDocumentService') private supportingDocumentService: () => SupportingDocumentService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public supportingDocuments: ISupportingDocument[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSupportingDocuments();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllSupportingDocuments();
  }

  public retrieveAllSupportingDocuments(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.supportingDocumentService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.supportingDocuments = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISupportingDocument): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSupportingDocument(): void {
    this.supportingDocumentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.supportingDocument.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllSupportingDocuments();
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

  public transition(): void {
    this.retrieveAllSupportingDocuments();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
