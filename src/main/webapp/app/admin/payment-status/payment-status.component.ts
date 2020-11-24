import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Inputmask from 'inputmask';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";

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

const PaymentStatusProps = Vue.extend({
  props: {

  }
})

@Component({
  components: {

  }
})
export default class PaymentStatus extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, PaymentStatusProps) {
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
  private totalAmount: number = null;

  selectedRows: any = {};
  public statusOptions: any = {};
  public paymentStatusOptions: any = {};

  public dialogConfirmationVisible: boolean = false;
  public filter: any = {};
  public vendorApprovalStatus: string = "vendorApprovalStatus";
  public paymentStatus: string = "paymentStatus";
  public radioSelection: number = null;
  private voucher: any = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.retrieveGetReferences(this.vendorApprovalStatus);
    this.retrieveGetReferences(this.paymentStatus);
  }

  public mounted(): void {
    this.retrieveAllRecords();
  }

  beforeDestroy() {

  }

  private closeDetailVerification(){
    //this.index = true;
    //this.selectedRows = {};
    //this.radioSelection = null;
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

    console.log(row);
  }

  public onSelectionChanged(value: any) {
    //this.selectedRowsLine = value;
    console.log(value);
  }

  public showDialogConfirmation(key: string) {
    if(this.radioSelection != null){

      /* if(key == "detail"){
        this.index = false;
      }else if(key == "update"){
        this.dialogConfirmationVisible = true;
        this.setVerificationNo = this.gridData[0].verificationNo;
      } */

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
          this.totalAmount = parseInt(item.totalLines) + parseInt(item.taxAmount);
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

        if(param[0].value == this.vendorApprovalStatus){
          this.statusOptions = referenceList;
        }else if(param[0].value == this.paymentStatus){
          this.paymentStatusOptions = referenceList;
        }
    });
  }

  public verificationFilter(){

    this.filterQuery = "";

    if((this.filter.verificationNo != null)&&(this.filter.verificationNo != "")){
      this.filterQuery = "verificationNo.equals="+this.filter.verificationNo;
    }
    if((this.filter.invoiceNo != null)&&(this.filter.invoiceNo != "")){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceNo.equals="+this.filter.invoiceNo;
    }
    if((this.filter.verificationStatus != null)&&(this.filter.verificationStatus != "")){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationStatus.equals="+this.filter.verificationStatus;
    }

    if((this.filter.verificationDate != null)&&(this.filter.verificationDate != "")){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationDate.greaterThan="+this.filter.verificationDate;
    }
    if((this.filter.invoiceDate != null)&&(this.filter.invoiceDate != "")){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceDate.greaterThan="+this.filter.invoiceDate;
    }
    if((this.filter.payStatus != null)&&(this.filter.payStatus != "")){
      if(this.filterQuery != ""){
        this.filterQuery += "&"
      }
      this.filterQuery += "payStatus.equals="+this.filter.payStatus;
    }

    this.retrieveAllRecords();
  }


}
