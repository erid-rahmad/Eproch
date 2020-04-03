import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBusinessCategory } from '@/shared/model/business-category.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import BusinessCategoryService from './business-category.service';

@Component
export default class BusinessCategory extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('businessCategoryService') private businessCategoryService: () => BusinessCategoryService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public businessCategories: IBusinessCategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBusinessCategorys();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllBusinessCategorys();
  }

  public retrieveAllBusinessCategorys(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.businessCategoryService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.businessCategories = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IBusinessCategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeBusinessCategory(): void {
    this.businessCategoryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.businessCategory.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllBusinessCategorys();
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
    this.retrieveAllBusinessCategorys();
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
