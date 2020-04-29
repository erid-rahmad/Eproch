import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IADFieldGroup } from '@/shared/model/ad-field-group.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ADFieldGroupService from './ad-field-group.service';

@Component
export default class ADFieldGroup extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('aDFieldGroupService') private aDFieldGroupService: () => ADFieldGroupService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public aDFieldGroups: IADFieldGroup[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllADFieldGroups();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllADFieldGroups();
  }

  public retrieveAllADFieldGroups(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.aDFieldGroupService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.aDFieldGroups = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IADFieldGroup): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeADFieldGroup(): void {
    this.aDFieldGroupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.aDFieldGroup.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllADFieldGroups();
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
    this.retrieveAllADFieldGroups();
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
