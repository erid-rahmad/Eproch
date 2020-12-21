import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import UpdateVoucher from './update-voucher.vue';
import DetailVerificationDocument from './detail-verification-document.vue';

const VerificationDocumentInquiryProps = Vue.extend({
  props: {

  }
})

@Component({
  components: {
    UpdateVoucher,
    DetailVerificationDocument
  }
})
export default class EVerification extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, VerificationDocumentInquiryProps) {
  private index: boolean = true;
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 420,
    height: 400
  };

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  private baseApiUrl = "/api/m-verifications";
  private baseApiUrlVendor = "/api/c-vendors";
  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  public docStatus: string = "docStatus";
  public paymentStatus: string = "paymentStatus";

  private filterQuery: string = '';
  private processing = false;

  private dialogTitle = "";
  private dialogMessage = "";
  private dialogButton = "";
  private dialogValue = "";
  private dialogType = "";

  public gridData: Array<any> = [];
  private totalAmount: number = null;
  private setVerificationNo: string = "";

  selectedRows: any = {};
  public vendorOptions: any[] = [];
  public statusOptions: any[] = [];
  public paymentStatusOptions: any[] = [];

  public dialogConfirmationVisible: boolean = false;
  public filter: any = {};

  public radioSelection: number = null;
  private voucher: any = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.retrieveGetReferences(this.docStatus);
    this.retrieveGetReferences(this.paymentStatus);
    this.retrieveAllVendorRecords();
  }

  public mounted(): void {
    this.retrieveAllRecords();
  }

  beforeDestroy() {

  }

  private closeDetailVerification(){
    this.index = true;
    this.selectedRows = {};
    this.radioSelection = null;
    //this.retrieveAllRecords();
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
    this.retrieveAllRecords();
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

  public transition(): void {
    this.retrieveAllRecords();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  public singleSelection (row) {
    this.radioSelection = this.gridData.indexOf(row);
    this.selectedRows = row;

    console.log("Single Selection %O", row);
  }

  public showDialogConfirmation(key: string) {
    if(this.radioSelection != null){

      if(key == "detail"){
        this.index = false;
      }else if(key == "update"){
        this.dialogConfirmationVisible = true;
        this.setVerificationNo = this.gridData[0].verificationNo;
      } else if(key == "printVerificationReceipt") {
        this.buttonPrint("invoice-verification-receipt");
      }

    }else{
      const message = `Please Selected row`;
      this.$notify({
        title: 'Warning',
        message: message.toString(),
        type: 'warning',
        duration: 3000
      });
    }

  }

  public buttonPrint(key): void {
    const data = { ...this.selectedRows };
    window.open(`/api/m-verifications/report/${data.id}/${data.verificationNo}/${key}`, '_blank');
  }

  public retrieveAllRecords(): void {
    if ( ! this.baseApiUrl) {
      return;
    }

    this.processing = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: this.filterQuery,
        paginationQuery
      })
      .then(res => {

        this.gridData = res.data.map((item: any) => {
          item.totalAmount = parseInt(item.totalLines) + parseInt(item.taxAmount);
          return item;
        });

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
        this.radioSelection = null;
        this.selectedRows = {};
      });
  }

  public retrieveAllVendorRecords(): void {

    this.processing = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.dynamicWindowService(this.baseApiUrlVendor)
      .retrieve({
        //criteriaQuery: this.filterQuery+"&vendorId.equals="+accountStore.userDetails.cVendorId,
        paginationQuery
      })
      .then(res => {

        let referenceList = res.data.map(item => {
          return{
              key: item.id,
              value: item.name
          };
      });

      this.vendorOptions = referenceList;

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

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
    this.retrieveAllRecords();
  }

  private retrieveGetReferences(param: string) {
    this.dynamicWindowService(this.baseApiUrlReference)
    .retrieve({
      criteriaQuery: [`value.contains=`+param]
    })
    .then(res => {
        let references = res.data.map(item => {
            return{
                id: item.id,
                value: item.value,
                name: item.name
            };
        });
        this.retrieveGetReferenceLists(references);
    });
  }

  private retrieveGetReferenceLists(param: any) {
    this.dynamicWindowService(this.baseApiUrlReferenceList)
    .retrieve({
      criteriaQuery: [`adReferenceId.equals=`+param[0].id]
    })
    .then(res => {
        let referenceList = res.data.map(item => {
            return{
                key: item.value,
                value: item.name
            };
        });

        if(param[0].value == this.docStatus){
          this.statusOptions = referenceList;
        }else if(param[0].value == this.paymentStatus){
          this.paymentStatusOptions = referenceList;
        }
    });
  }

  public verificationFilter(){

    this.filterQuery = "";

    if(!!this.filter.verificationNo){
      this.filterQuery = "verificationNo.equals="+this.filter.verificationNo;
    }
    if(!!this.filter.invoiceNo){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceNo.equals="+this.filter.invoiceNo;
    }
    if(!!this.filter.taxInvoiceNo){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceNo.equals="+this.filter.taxInvoiceNo;
    }
    if(!!this.filter.vendorName){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "vendorId.equals="+this.filter.vendorName;
    }

    if(!!this.filter.verificationDateFrom){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationDate.greaterOrEqualThan="+this.filter.verificationDateFrom;
    }
    if(!!this.filter.invoiceDateFrom){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceDate.greaterOrEqualThan="+this.filter.invoiceDateFrom;
    }
    if(!!this.filter.taxInvoiceDateFrom){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceDate.greaterOrEqualThan="+this.filter.taxInvoiceDateFrom;
    }
    if(!!this.filter.verificationStatus){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationStatus.equals="+this.filter.verificationStatus;
    }

    if(!!this.filter.verificationDateTo){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationDate.lessOrEqualThan="+this.filter.verificationDateTo;
    }
    if(!!this.filter.invoiceDateTo){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceDate.lessOrEqualThan="+this.filter.invoiceDateTo;
    }
    if(!!this.filter.taxInvoiceDateTo){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceDate.lessOrEqualThan="+this.filter.taxInvoiceDateTo;
    }
    if(!!this.filter.payStatus){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "payStatus.equals="+this.filter.payStatus;
    }

    this.retrieveAllRecords();
  }

  public dataVoucher(data?: any){
    this.voucher = data;
  }

  private onUpdateVoucherApplied(){
    console.log(this.voucher);
    //proses save
  }

  formatDocumentStatus(value: string) {
    return this.statusOptions.find(status => status.key === value)?.value || value;
  }

  formatPaymentStatus(value: string) {
    return this.paymentStatusOptions.find(status => status.key === value)?.value || value;
  }

}
