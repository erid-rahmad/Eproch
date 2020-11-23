import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Inputmask from 'inputmask';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";
import EVerificationUpdate from './e-verification-update.vue';

Vue.directive('inputmask', {
  bind: function(el, binding) {
    var inputs = el.getElementsByTagName('INPUT')
    var input = inputs[0]
    if (inputs.length > 1) {
      input = inputs[inputs.length - 1]
    }
    // new Inputmask(binding.value).mask(input)
    new Inputmask({
      autoUnmask: true,
    }).mask(input)
  },
})

const EVerificationProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },

  }
})

@Component({
  components: {
    EVerificationUpdate
  }
})
export default class EVerification extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, EVerificationProps) {
  private index: boolean = true;
  private disabledButton: boolean = false;
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
    height: 420
  };
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  private baseApiUrl = "/api/m-verifications";
  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";

  private filterQuery: string = '';
  private processing = false;

  private dialogTitle = "";
  private dialogMessage = "";
  private dialogButton = "";
  private dialogValue = "";
  private dialogType = "";

  public gridData: Array<any> = [];

  selectedRows: any = {};
  public statusOptions: any = {};

  public dialogConfirmationVisible: boolean = false;
  public filter: any = {};
  public vendorApprovalStatus: string = "vendorApprovalStatus";
  public radioSelection: number = null;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.retrieveGetReferences(this.vendorApprovalStatus);
  }

  public mounted(): void {
    this.retrieveAllRecords();
  }

  beforeDestroy() {

  }

  private closeEVerificationUpdate(){
    this.index = true;
    this.selectedRows = {};
    this.radioSelection = null;
    this.retrieveAllRecords();
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

    this.statementButtonDisabled();
    console.log(row);
  }

  public onSelectionChanged(value: any) {
    //this.selectedRowsLine = value;
    console.log(value);
  }

  public showDialogConfirmation(key: string) {
    if(this.radioSelection != null){

      if(key == "void"){
        this.dialogTitle = "Confirm VOID status verification";
        this.dialogMessage = "Update status verification to VOID ?";
        this.dialogButton = "Update";
        this.dialogValue = "Void";
        this.dialogType = "danger";

        this.dialogConfirmationVisible = true;
      }else if(key == "submit"){
        this.dialogTitle = "Confirm SUBMIT status verification";
        this.dialogMessage = "Update status verification to SUBMIT ?";
        this.dialogButton = "Update";
        this.dialogValue = "Submit";
        this.dialogType = "primary";

        this.dialogConfirmationVisible = true;
      }else if(key == "update"){
        this.index = false;
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

  public buttonDialogUpdateRecords(): void {
    var updateStatusVerification: any = {
      id: this.selectedRows.id,
      verificationDate: this.selectedRows.verificationDate,
      verificationNo: this.selectedRows.verificationNo,
      taxAmount: this.selectedRows.taxAmount,
      totalLines: this.selectedRows.totalLines,
      grandTotal: this.selectedRows.grandTotal,
      invoiceDate: this.selectedRows.invoiceDate,
      invoiceNo: this.selectedRows.invoiceNo,
      description: this.selectedRows.description,
      taxInvoice: this.selectedRows.taxInvoice,
      taxDate: this.selectedRows.taxDate,
      active: this.selectedRows.active,
      adOrganizationId: this.selectedRows.adOrganizationId,
      adOrganizationName: this.selectedRows.adOrganizationName,
      currencyId: this.selectedRows.currencyId,
      currencyName: this.selectedRows.currencyName,
      picId: this.selectedRows.picId,
      vendorId: this.selectedRows.vendorId,
      verificationStatus: this.dialogValue
    };
    this.dynamicWindowService(this.baseApiUrl)
      .update(updateStatusVerification)
      .then(res=>{

        this.statementButtonDisabled();
        this.retrieveAllRecords();
        this.radioSelection = null;

        this.dialogConfirmationVisible = false;
        this.$notify({
          title: 'Success',
          message: this.dialogMessage,
          type: 'success',
          duration: 3000
        });

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
      });;
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
        criteriaQuery: this.filterQuery+"&vendorId.equals="+accountStore.userDetails.cVendorId,
        paginationQuery
      })
      .then(res => {
        console.log(res);
        this.gridData = res.data.map((item: any) => {
          return item;
        });

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        this.$emit('total-count-changed', this.queryCount);

        this.statementButtonDisabled();
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

  private statementButtonDisabled(){
    if(this.selectedRows.verificationStatus == "Void"){
      this.disabledButton = true;
    } else if(this.selectedRows.verificationStatus == "Submit"){
      this.disabledButton = true;
    }else{
      this.disabledButton = false;
    }
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

        if(param[0].value == this.vendorApprovalStatus){
          this.statusOptions = referenceList;
        }
    });
  }

  public verificationFilter(){

    this.filterQuery = "";

    if(this.filter.verificationNo != null){
      this.filterQuery = "verificationNo.contains="+this.filter.verificationNo;
    }
    if(this.filter.invoiceNo != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceNo.contains="+this.filter.invoiceNo;
    }
    if(this.filter.taxInvoiceNo != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceNo.contains="+this.filter.taxInvoiceNo;
    }
    if((this.filter.verificationStatus != null)&&(this.filter.verificationStatus != "")){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationStatus.equals="+this.filter.verificationStatus;
    }

    if(this.filter.verificationDateFrom != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationDate.greaterThan="+this.filter.verificationDateFrom;
    }
    if(this.filter.invoiceDateFrom != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceDate.greaterThan="+this.filter.invoiceDateFrom;
    }
    if(this.filter.taxInvoiceDateFrom != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceDate.greaterThan="+this.filter.taxInvoiceDateFrom;
    }

    if(this.filter.verificationDateTo != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationDate.lessThan="+this.filter.verificationDateTo;
    }
    if(this.filter.invoiceDateTo != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceDate.lessThan="+this.filter.invoiceDateTo;
    }
    if(this.filter.taxInvoiceDateTo != null){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceDate.lessThan="+this.filter.taxInvoiceDateTo;
    }

    this.retrieveAllRecords();
  }

  public addEVerification() {
    this.index = false;
    this.selectedRows = {};
  }

}
