import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICRegion } from '@/shared/model/c-region.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CRegionService from './c-region.service';

@Component
export default class CRegion extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('cRegionService') private cRegionService: () => CRegionService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public cRegions: ICRegion[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCRegions();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCRegions();
  }

  public retrieveAllCRegions(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.cRegionService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cRegions = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICRegion): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCRegion(): void {
    this.cRegionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.cRegion.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCRegions();
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
    this.retrieveAllCRegions();
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
