import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICity } from '@/shared/model/city.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CityService from './city.service';

@Component
export default class City extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('cityService') private cityService: () => CityService;
  private showDeleteDialog: boolean = false;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public cities: ICity[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCitys();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCitys();
  }

  public retrieveAllCitys(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.cityService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.cities = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICity): void {
    this.removeId = instance.id;
    //if (<any>this.$refs.removeEntity) {
    //  (<any>this.$refs.removeEntity).show();
    //}
    this.showDeleteDialog = true;
  }

  public removeCity(): void {
    this.cityService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.city.deleted', { param: this.removeId });
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        });
        //this.alertService().showAlert(message, 'danger');
        //this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCitys();
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
    this.retrieveAllCitys();
  }

  public transition(): void {
    this.retrieveAllCitys();
  }

  public changeClassificationSelection(currentRow: ICity) {
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

  public openDetails(instance: ICity) {
    this.$router.push(`/city/${instance.id}/view`);
  }

  public add() {
    this.$router.push('/city/new');
  }

  public edit(instance: ICity) {
    this.$router.push(`/city/${instance.id}/edit`);
  }
}
