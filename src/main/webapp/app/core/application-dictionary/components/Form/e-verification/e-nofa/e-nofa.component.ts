import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Inputmask from 'inputmask';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../../../../core/application-dictionary/components/ContextVariableAccessor";
import ENofaUpdate from './e-nofa-update.vue';

const ENovaProps = Vue.extend({
  props: {

  }
})

@Component({
  components: {
    ENofaUpdate,
  }
})
export default class ENova extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, ENovaProps) {
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

  private baseApiUrl = "/api/c-tax-invoices";
  private baseApiUrlVendor = "/api/c-vendors";

  private filterQuery: string = '';
  private processing = false;

  private dialogType = "";
  private dialogTitle = "";
  private dialogMessage = "";
  private dialogButton = "";
  private dialogButtonType = "";
  private dialogButtonIcon = "";

  public gridData: Array<any> = [];
  private formInputFaktur = {};

  selectedRows: any = {};
  public vendorOptions: any = {};

  public dialogConfirmationVisible: boolean = false;
  public filter: any = {};
  public radioSelection: number = null;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.retrieveAllVendorRecords();

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
    //console.log(value);
  }

  private closeEVerificationUpdate(){
    this.formInputFaktur = {};
    this.dialogConfirmationVisible = false;
    this.retrieveAllRecords();
  }

  public showDialogConfirmation(key: string) {
    if(this.radioSelection != null){
      if(key == "detail"){
        //this.index = false;
        this.dialogType = "";
        this.dialogTitle = "";
        this.dialogMessage = "";
        this.dialogButton = "";
        this.dialogButtonType = "";
        this.dialogButtonIcon = "";
      }else if(key == "remove"){
        this.dialogConfirmationVisible = true;
        this.dialogType = key;
        this.dialogTitle = "Confirm delete operation";
        this.dialogMessage = "Are you sure to delete the selected record?";
        this.dialogButton = "Remove";
        this.dialogButtonType = "danger";
        this.dialogButtonIcon = "el-icon-delete";
      }
    }else{
      if((key=="detail")||(key=="remove")){
        const message = `Please Selected row`;
        this.$notify({
          title: 'Warning',
          message: message.toString(),
          type: 'warning',
          duration: 3000
        });
      }
    }

    if(key == "add"){
      this.dialogType = key;
      this.dialogTitle = "User Revision";
      this.dialogMessage = "";
      this.dialogButton = "Save";
      this.dialogButtonType = "primary";
      this.dialogButtonIcon = "el-icon-check";

      this.dialogConfirmationVisible = true;
    }else if(key == "export"){
      this.dialogType = key;
      this.dialogTitle = "Export";
      this.dialogMessage = "";
      this.dialogButton = "Export";
      this.dialogButtonType = "primary";
      this.dialogButtonIcon = "el-icon-download";

      this.dialogConfirmationVisible = true;
    }

  }

  private dialogButtonAction(key){
    if(key == "add"){
      console.log(this.formInputFaktur);
      this.save();
    }else if(key == "remove"){
      this.remove();
    }
  }

  private save(){
    this.dynamicWindowService(this.baseApiUrl)
      .create(this.formInputFaktur)
      .then(() => {

        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: 'Success created.',
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
        //this.fullscreenLoading = false;
        console.log("proses");
      });

  }

  private remove(){
    console.log(this.selectedRows);
    this.dynamicWindowService(this.baseApiUrl)
      .delete(this.selectedRows.id)
      .then(() => {

        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: 'Success deleted.',
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
        //this.fullscreenLoading = false;
        console.log("proses");
      });
  }

  private export(){
    console.log("export");
  }

  public dataFormInputFaktur(data?: any){
    this.formInputFaktur = data;
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

    var joinFilterQuery = "";
    if(accountStore.userDetails.cVendorId){
      joinFilterQuery = "&vendorId.equals="+accountStore.userDetails.cVendorId;
    }else{
      joinFilterQuery = "";
    }

    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: this.filterQuery+joinFilterQuery,
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

  public verificationFilter(){

    this.filterQuery = "";

    if((this.filter.vendor != null)&&(this.filter.vendor != "")){
      this.filterQuery = "vendorId.equals="+this.filter.vendor;
    }

    this.retrieveAllRecords();
  }

}
