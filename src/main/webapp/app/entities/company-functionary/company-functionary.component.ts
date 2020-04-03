import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICompanyFunctionary } from '@/shared/model/company-functionary.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CompanyFunctionaryService from './company-functionary.service';

@Component
export default class CompanyFunctionary extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('companyFunctionaryService') private companyFunctionaryService: () => CompanyFunctionaryService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public companyFunctionaries: ICompanyFunctionary[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCompanyFunctionarys();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCompanyFunctionarys();
  }

  public retrieveAllCompanyFunctionarys(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.companyFunctionaryService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.companyFunctionaries = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICompanyFunctionary): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCompanyFunctionary(): void {
    this.companyFunctionaryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.companyFunctionary.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCompanyFunctionarys();
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
    this.retrieveAllCompanyFunctionarys();
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
