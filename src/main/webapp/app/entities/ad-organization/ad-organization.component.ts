import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IADOrganization } from '@/shared/model/ad-organization.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ADOrganizationService from './ad-organization.service';

@Component
export default class ADOrganization extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('aDOrganizationService') private aDOrganizationService: () => ADOrganizationService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public aDOrganizations: IADOrganization[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllADOrganizations();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllADOrganizations();
  }

  public retrieveAllADOrganizations(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.aDOrganizationService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.aDOrganizations = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IADOrganization): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeADOrganization(): void {
    this.aDOrganizationService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.aDOrganization.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllADOrganizations();
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
    this.retrieveAllADOrganizations();
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
