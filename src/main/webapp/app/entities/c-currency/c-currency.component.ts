import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICCurrency } from '@/shared/model/c-currency.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CCurrencyService from './c-currency.service';

@Component
export default class CCurrency extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('cCurrencyService') private cCurrencyService: () => CCurrencyService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public cCurrencies: ICCurrency[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCCurrencys();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCCurrencys();
  }

  public retrieveAllCCurrencys(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.cCurrencyService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cCurrencies = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICCurrency): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCCurrency(): void {
    this.cCurrencyService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.cCurrency.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCCurrencys();
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
    this.retrieveAllCCurrencys();
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
