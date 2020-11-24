import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";

const EVerificationProps = Vue.extend({
  props: {
    modeFilterMatchPo: {
      type: Object,
      default: () => {}
    },

  }
})

@Component
export default class MatchPoUpdate extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, EVerificationProps) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 412,
    height: 310
  };
  private itemsPerPage = 10;
  private queryCount: number = null;
  private page = 1;
  private previousPage = 1;
  private propOrder = 'id';
  private reverse = false;
  private totalItems = 0;
  private baseApiUrlMatchPo = "/api/m-match-pos";
  private filterQuery: string = '';
  private processing = false;

  private gridData: Array<any> = [];
  selectedRows: any[] = [];
  private statusOptions: any = {};
  private filter: any = {};
  private vendorApprovalStatus: string = "vendorApprovalStatus";
  private selectedMatchPo: any[] = [];
  private totalAmount: number = null;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    if(this.modeFilterMatchPo.mode == 1){
      this.retrieveAllRecordMatchPos();
    }
  }

  public onSelectionChanged(rows: any[]) {
    this.selectedMatchPo = rows;
    this.$emit('get-match-po', rows);
    console.log(rows);
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
    if(this.page!=1){
      this.page = 0;
    }
    this.retrieveAllRecordMatchPos();
  }

  public transition(): void {
    this.retrieveAllRecordMatchPos();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }


  private retrieveAllRecordMatchPos(): void {
    if (!this.baseApiUrlMatchPo) {
      return;
    }

    this.processing = true;
    this.filterQuery += `mMatchType.equals=1&cVendorId.equals=${accountStore.userDetails.cVendorId}`;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.dynamicWindowService(this.baseApiUrlMatchPo)
      .retrieve({
        criteriaQuery: this.filterQuery,
        paginationQuery
      })
      .then(res => {
        console.log(res);

        if (this.modeFilterMatchPo.mode == 1) {
          this.gridData = res.data.map((item: any) => {
            this.totalAmount = item.totalLines + item.taxAmount;
            return item;
          });

          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.$emit('total-count-changed', this.queryCount);

        } else {
          this.selectedMatchPo = res.data.map((item: any) => {
            this.totalAmount = item.totalLines + item.taxAmount;
            return item;
          });

          this.$emit('get-match-po', this.selectedMatchPo);

        }

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

  @Watch('page')
  onPageChange(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public matchPoFilter() {
    this.filterQuery = '';

    if (this.modeFilterMatchPo.mode == 2) {
      this.modeFilterMatchPo.filterByReceiptNo = this.filter.filterByReceiptNo;
      this.filterQuery += "receiptNo.equals=" + this.filter.filterByReceiptNo;
      this.retrieveAllRecordMatchPos();
    } else {
      if((this.filter.receiptNo != null)||(this.filter.receiptNo != "")){
        this.filterQuery = "receiptNo.equals=" + this.filter.receiptNo;
      }

      if((this.filter.receiptDateFrom != null)||(this.filter.receiptDateFrom != "")){
        if(this.filterQuery != ""){
          this.filterQuery += "&"
        }
        this.filterQuery += "receiptDate.greaterThan="+this.filter.receiptDateFrom;
      }

      if((this.filter.poNo != null)||(this.filter.poNo != "")){
        if(this.filterQuery != ""){
          this.filterQuery += "&"
        }
        this.filterQuery += "poNo.equals="+this.filter.poNo;
      }

      if((this.filter.receiptDateTo != null)||(this.filter.receiptDateTo != "")){
        if(this.filterQuery != ""){
          this.filterQuery += "&"
        }
        this.filterQuery += "receiptDate.lessThan="+this.filter.receiptDateTo;
      }

      if((this.filter.deliveryNo != null)||(this.filter.deliveryNo != "")){
        if(this.filterQuery != ""){
          this.filterQuery += "&"
        }
        this.filterQuery += "deliveryNo.equals="+this.filter.deliveryNo;
      }

      this.retrieveAllRecordMatchPos();
    }
  }



}
