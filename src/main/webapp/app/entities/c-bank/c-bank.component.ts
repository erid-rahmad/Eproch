import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICBank } from '@/shared/model/c-bank.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CBankService from './c-bank.service';

@Component
export default class CBank extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('cBankService') private cBankService: () => CBankService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public cBanks: ICBank[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCBanks();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCBanks();
  }

  public retrieveAllCBanks(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.cBankService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cBanks = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICBank): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCBank(): void {
    this.cBankService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.cBank.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCBanks();
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
    this.retrieveAllCBanks();
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
