import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";

const DetailVerificationDocumentProps = Vue.extend({
  props: {
    detailVerification: {
      type: Object,
      default: () => {}
    },

  }
})

@Component
export default class DetailVerificationDocument extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, DetailVerificationDocumentProps) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
    height: 430
  };

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  private totalItems = 0;

  private baseApiUrlEVerificationLine = "/api/m-verification-lines";
  public gridData: Array<any> = [];

  private processing = false;
  private totalAmount: number = null;
  private filterQuery: string = "";
  selectedRows: any = {};
  public radioSelection: number = null;

  eVerification = {
    form: {},
    line: [],
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    console.log(this.detailVerification);

    if(this.detailVerification.id != null){
      this.filterQuery = "verificationId.equals="+this.detailVerification.id;
      this.retrieveEVerificationLine();
    }
  }

  public mounted(): void {

  }

  public singleSelection (row) {
    this.radioSelection = this.gridData.indexOf(row);
    this.selectedRows = row;

    console.log("Single Selection %O", row);
  }


  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    const {propOrder: property, reverse} = this;
    this.$emit('order-changed', { property, reverse });
    this.transition();
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  // =====================================
  // Pagination
  public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.page!=1){
      this.page = 0;
    }
    this.retrieveEVerificationLine();
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  @Watch('page')
  onPageChange(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveEVerificationLine();
  }
  // =====================================

  public closeDetailVerification(){
    this.$emit('close-detail-verification');
  }

  private retrieveEVerificationLine(): void {
    if ( ! this.baseApiUrlEVerificationLine) {
      return;
    }

    this.processing = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.dynamicWindowService(this.baseApiUrlEVerificationLine)
      .retrieve({
        criteriaQuery: this.filterQuery,
        paginationQuery
      })
      .then(res => {
        //console.log(res);
        this.gridData = res.data.map((item: any) => {
          item.totalAmount = parseInt(item.totalLines) + parseInt(item.taxAmount);
          return item;
        });

        console.log(this.gridData);
        this.calculateLines();

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        this.$emit('total-count-changed', this.queryCount);

      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.processing = false;
      });
  }

  private calculateLines() {
    let totalLines = 0;
    let totalAmount = 0;
    let taxAmount = 0;

    for (const row of this.gridData) {
      totalLines += row.totalLines;
      totalAmount += row.totalAmount;
      taxAmount += row.taxAmount;
    }

    this.detailVerification.totalLines = totalLines;
    this.detailVerification.taxAmount = taxAmount;
    this.detailVerification.grandTotal = totalAmount;
  }

}
