import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICountry } from '@/shared/model/country.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CountryService from './country.service';
import { ElTable } from 'element-ui/types/table';

@Component
export default class Country extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('countryService') private countryService: () => CountryService;
  private showDeleteDialog: boolean = false;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'name';
  public reverse = true;
  public totalItems = 0;

  public countries: ICountry[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCountries();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllCountries();
  }

  public retrieveAllCountries(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.countryService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.countries = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICountry): void {
    this.removeId = instance.id;
    this.showDeleteDialog = true;
  }

  public removeCountry(): void {
    this.countryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('opusWebApp.country.deleted', { param: this.removeId });
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        })
        this.removeId = null;
        this.retrieveAllCountries();
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
    this.retrieveAllCountries();
  }

  public transition(): void {
    this.retrieveAllCountries();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    this.transition();
  }

  public closeDialog(): void {
    this.showDeleteDialog = false;
  }

  public openDetails(instance: ICountry) {
    this.$router.push(`/country/${instance.id}/view`);
  }

  public add() {
    this.$router.push('/country/new')
  }

  public edit(instance: ICountry) {
    this.$router.push(`/country/${instance.id}/edit`);
  }
}
