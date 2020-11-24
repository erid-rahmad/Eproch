import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";

const DetailVerificationDocumentProps = Vue.extend({
  props: {
    detailVerification: {
      type: Object,
      default: () => {}
    },

  }
})

@Component({
  components: {

  }
})
export default class DetailVerificationDocument extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, DetailVerificationDocumentProps) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 470,
    height: 470
  };
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  private totalItems = 0;
  private methodSubmit: string = "";
  private baseApiUrlEVerification = "/api/m-verifications";
  private baseApiUrlEVerificationLine = "/api/m-verification-lines";
  private baseApiUrlCurrency = "/api/c-currencies";
  private baseApiUrlVendor = "/api/c-vendors";
  public gridData: Array<any> = [];

  public statusOptions: any = {};
  public filter: any = {};
  private processing = false;
  private fullscreenLoading: boolean = false;

  public currencyOptions: any = {};
  public vendorOptions: any = {};

  public formUpdateTotalLines: number = 0;
  public formUpdateTaxAmount: number = 0;
  public formUpdateTotalAmount: number = 0;
  public dialogMatchPoVisible: boolean = false;
  private totalAmount: number = null;

  public matchPo = {};
  public modeFilterMatchPo: any = {};
  private filterQuery: string = "";

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

  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    const {propOrder: property, reverse} = this;
    this.$emit('order-changed', { property, reverse });
    this.transition();
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    this.retrieveEVerificationLine();
  }

  public transition(): void {
    this.retrieveEVerificationLine();
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public closeDetailVerification(){
    this.$emit('close-detail-verification');
  }

  private notifWarning(message: string){
    this.$notify({
      title: 'Warning',
      message: message,
      type: 'warning',
      duration: 3000
    });
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
      })
      .then(res => {
        //console.log(res);
        this.gridData = res.data.map((item: any) => {
          this.totalAmount = parseInt(item.totalLines) + parseInt(item.taxAmount);
          return item;
        });

        console.log(this.gridData);

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








}
