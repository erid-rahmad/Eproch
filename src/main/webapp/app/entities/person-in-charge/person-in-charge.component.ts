import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPersonInCharge } from '@/shared/model/person-in-charge.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PersonInChargeService from './person-in-charge.service';

@Component
export default class PersonInCharge extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('personInChargeService') private personInChargeService: () => PersonInChargeService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public personInCharges: IPersonInCharge[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPersonInCharges();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllPersonInCharges();
  }

  public retrieveAllPersonInCharges(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.personInChargeService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.personInCharges = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPersonInCharge): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePersonInCharge(): void {
    this.personInChargeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.personInCharge.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPersonInCharges();
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
    this.retrieveAllPersonInCharges();
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
