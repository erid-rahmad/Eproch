import { mixins } from 'vue-class-component';
import Vue from 'vue'
import { Component } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import MatchPo from './match-po.vue';
import AlertMixin from '@/shared/alert/alert.mixin';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

const EVerificationUpdateProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    formUpdate: {
      type: Object,
      default: () => {}
    },

  }
})

@Component({
  components: {
    MatchPo
  }
})
export default class EVerificationUpdate extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, EVerificationUpdateProps) {
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
  public remove: Array<any> = [];

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
    remove: []
  }

  created(){
    this.retrieveGetCurrencies();
    this.retrieveGetVendor();
    this.formUpdate.vendorId = accountStore.userDetails.cVendorId;
    this.formUpdate.vendorName = accountStore.userDetails.cVendorName;

    if(this.formUpdate.id != null){
      this.filterQuery = "verificationId.equals="+this.formUpdate.id;
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
    //this.retrieveAllRecords();
  }

  public transition(): void {
    //this.retrieveAllRecords();
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public displayMatchPo(id){
    var width, titleModal;
    if(id == 1){
      width = "90%";
      titleModal = "Receipt Order";
    }else{
      width = "40%";
      titleModal = "Add By Receipt No";
    }
    this.modeFilterMatchPo = {
      "mode": id,
      "width": width,
      "titleModal": titleModal,
      "filterByReceiptNo": ""
    };
    this.dialogMatchPoVisible = true;
  }

  public closeEVerificationUpdate(){
    this.$emit('close-e-verification-update');
  }

  private updateEVerification(){
    var message;
    if(this.gridData.length){
      if((this.formUpdate.invoiceNo == null)||(this.formUpdate.invoiceNo == "")){
        message = "Please input invoice no";
        this.notifWarning(message);
      }else if((this.formUpdate.invoiceDate == null)||(this.formUpdate.invoiceDate == "")){
        message = "Please input invoice date";
        this.notifWarning(message);
      }else if((this.formUpdate.currencyId == null)||(this.formUpdate.currencyId == "")){
        message = "Please input currency";
        this.notifWarning(message);
      }else if((this.formUpdate.taxInvoice == null)||(this.formUpdate.taxInvoice == "")){
        message = "Please input tax invoice no";
        this.notifWarning(message);
      }else if((this.formUpdate.taxDate == null)||(this.formUpdate.taxDate == "")){
        message = "Please input tax invoice date";
        this.notifWarning(message);
      }else{
        this.fullscreenLoading = true;

        setTimeout(() => {
          this.submit();
        }, 2000);

      }

    }else{
      message = "Please input receipt order";
      this.notifWarning(message);
    }


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

        //console.log(this.gridData);

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

  private retrieveGetCurrencies() {
    this.dynamicWindowService(this.baseApiUrlCurrency)
    .retrieve()
    .then(res => {
        let currencyList = res.data.map(item => {
            return{
                key: item.id,
                value: item.code
            };
        });
        this.currencyOptions = currencyList;

    });
  }

  private retrieveGetVendor() {
    this.dynamicWindowService(this.baseApiUrlVendor)
    .retrieve()
    .then(res => {
        let vendorList = res.data.map(item => {
            return{
                key: item.id,
                value: item.name
            };
        });
        this.vendorOptions = vendorList;

    });
  }

  private getMatchPo(data?: any){
    this.gridData = data;
    if(this.modeFilterMatchPo.mode == 2){
      this.onMatchPoApplied();
    }

  }

  private onMatchPoApplied() {

    this.formUpdateTotalLines = 0;
    this.formUpdateTaxAmount = 0;
    this.formUpdateTotalAmount = 0;

    for (let i = 0; i < this.gridData.length; i++) {
      this.formUpdateTotalLines += parseInt(this.gridData[i].totalLines);
      this.formUpdateTaxAmount += parseInt(this.gridData[i].taxAmount);

      this.totalAmount = parseInt(this.gridData[i].totalLines) + parseInt(this.gridData[i].taxAmount);
    }

    this.formUpdate.totalLines = this.formUpdateTotalLines;
    this.formUpdate.taxAmount = this.formUpdateTaxAmount;
    this.formUpdate.grandTotal = this.formUpdateTotalLines + this.formUpdateTaxAmount;

    this.dialogMatchPoVisible = false;
  }

  private removeRow(data){
    this.gridData.splice(data.$index, 1);
    if(this.formUpdate.id != null){
      this.remove.push(data.row);
      //console.log(this.remove);
    }

    this.formUpdate.totalLines -= parseInt(data.row.totalLines);
    this.formUpdate.taxAmount -= parseInt(data.row.taxAmount);
    this.formUpdate.grandTotal = this.formUpdate.totalLines + this.formUpdate.taxAmount;
  }

  submit() {
    this.formUpdate.picUserId = accountStore.userDetails.id;

    this.eVerification.form = this.formUpdate;
    this.eVerification.line = this.gridData;
    this.eVerification.remove = this.remove;

    console.log(this.eVerification);

    if(this.formUpdate.id != null){
      this.dynamicWindowService(this.baseApiUrlEVerification+"/submit")
        .update(this.eVerification)
        .then(() => {

          this.$notify({
            title: 'Success',
            dangerouslyUseHTMLString: true,
            message: 'E-Verification form updated.',
            type: 'success'
          });

          this.closeEVerificationUpdate();

        }).catch(error => {
          this.$notify({
            title: 'Error',
            dangerouslyUseHTMLString: true,
            message: error,
            type: 'error',
            duration: 3000
          });
        }).finally(() => {
          this.fullscreenLoading = false;
        });

    }else{
      this.dynamicWindowService(this.baseApiUrlEVerification+"/submit")
        .create(this.eVerification)
        .then(() => {

          this.$notify({
            title: 'Success',
            dangerouslyUseHTMLString: true,
            message: 'E-Verification form submitted.',
            type: 'success'
          });

          this.closeEVerificationUpdate();

        }).catch(error => {
          this.$notify({
            title: 'Error',
            dangerouslyUseHTMLString: true,
            message: error,
            type: 'error',
            duration: 3000
          });
        }).finally(() => {
          this.fullscreenLoading = false;
        });

    }
  }

}
