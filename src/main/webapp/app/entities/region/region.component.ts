import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRegion } from '@/shared/model/region.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RegionService from './region.service';
import { ElTable } from 'element-ui/types/table';

@Component
export default class Region extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('regionService') private regionService: () => RegionService;
  private showDeleteDialog: boolean = false;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'name';
  public reverse = true;
  public totalItems = 0;

  public regions: IRegion[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRegions();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRegions();
  }

  public retrieveAllRegions(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.regionService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.regions = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IRegion): void {
    this.removeId = instance.id;
    //if (<any>this.$refs.removeEntity) {
    //  (<any>this.$refs.removeEntity).show();
    //}
    this.showDeleteDialog = true;
  }

  public removeRegion(): void {
    this.regionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.region.deleted', { param: this.removeId });
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        });
        //this.alertService().showAlert(message, 'danger');
        //this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRegions();
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

  public handleSizeChange(size: number) {
    this.itemsPerPage = size;
    this.retrieveAllRegions();
  }

  public transition(): void {
    this.retrieveAllRegions();
  }

  public changeClassificationSelection(currentRow: IRegion) {
    console.log('Selected classification#%d', currentRow.id);
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    //this.reverse = !this.reverse;
    this.reverse = propOrder.order === 'ascending';
    this.transition();
  }

  public closeDialog(): void {
    //(<any>this.$refs.removeEntity).hide();
    this.showDeleteDialog = false;
  }

  public openDetails(instance: IRegion) {
    this.$router.push(`/region/${instance.id}/view`);
  }

  public add() {
    this.$router.push('/region/new');
  }

  public edit(instance: IRegion) {
    this.$router.push(`/region/${instance.id}/edit`);
  }
}
