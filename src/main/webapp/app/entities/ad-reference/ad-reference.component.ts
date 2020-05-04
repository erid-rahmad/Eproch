import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IADReference } from '@/shared/model/ad-reference.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ADReferenceService from './ad-reference.service';

@Component
export default class ADReference extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('aDReferenceService') private aDReferenceService: () => ADReferenceService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public aDReferences: IADReference[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllADReferences();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllADReferences();
  }

  public retrieveAllADReferences(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.aDReferenceService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.aDReferences = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IADReference): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeADReference(): void {
    this.aDReferenceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.aDReference.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllADReferences();
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
    this.retrieveAllADReferences();
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
