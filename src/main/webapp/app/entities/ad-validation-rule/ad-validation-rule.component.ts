import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAdValidationRule } from '@/shared/model/ad-validation-rule.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import AdValidationRuleService from './ad-validation-rule.service';

@Component
export default class AdValidationRule extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('adValidationRuleService') private adValidationRuleService: () => AdValidationRuleService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public adValidationRules: IAdValidationRule[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAdValidationRules();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllAdValidationRules();
  }

  public retrieveAllAdValidationRules(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.adValidationRuleService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.adValidationRules = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IAdValidationRule): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAdValidationRule(): void {
    this.adValidationRuleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.adValidationRule.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllAdValidationRules();
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
    this.retrieveAllAdValidationRules();
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
