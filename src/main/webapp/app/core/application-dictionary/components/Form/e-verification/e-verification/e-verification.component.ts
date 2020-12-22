import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import EVerificationUpdate from './e-verification-update.vue';

@Component({
  components: {
    EVerificationUpdate
  }
})
export default class EVerification extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  index: boolean = true;
  disabledButton: boolean = false;
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
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
  private filterQuery: string = '';
  public filter: any = {};
  processing = false;
  public gridData: Array<any> = [];
  selectedRows: any = {};
  public radioSelection: number = null;

  private dialogTitle = "";
  private dialogMessage = "";
  private dialogButton = "";
  private dialogValue = "";
  private dialogType = "";
  public dialogConfirmationVisible: boolean = false;
  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private keyReference: string = "docStatus";

  public documentStatuses: any[] = [];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.retrieveGetReferences(this.keyReference);
  }

  mounted(): void {
    this.retrieveAllRecords();
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
    this.retrieveAllRecords();
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
  // =====================================

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  public singleSelection (row) {
    this.radioSelection = this.gridData.indexOf(row);
    this.selectedRows = row;

    this.statementButtonDisabled();
    //console.log(row);
  }

  public showDialogConfirmation(key: string) {
    if (this.radioSelection != null) {

      if (key == "cancel") {
        this.dialogTitle = "Confirm VOID status verification";
        this.dialogMessage = "Update status verification to VOID ?";
        this.dialogButton = "Update";
        this.dialogValue = "CNL";
        this.dialogType = "danger";

        this.dialogConfirmationVisible = true;
      } else if (key == "submit") {
        this.dialogTitle = "Confirm SUBMIT status verification";
        this.dialogMessage = "Update status verification to SUBMIT ?";
        this.dialogButton = "Update";
        this.dialogValue = "SMT";
        this.dialogType = "primary";

        this.dialogConfirmationVisible = true;
      } else if (key == "print") {
        this.buttonPrint("invoice-verification");
      } else if(key == "printSummary") {
        this.buttonPrint("summary-invoice-verification");
      } else if (key == "update") {
        this.index = false;
      }
    } else {
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
    const data = { ...this.selectedRows };
    data.verificationStatus = this.dialogValue;

    this.dynamicWindowService(this.baseApiUrl)
      .update(data)
      .then(res => {
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
      });
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
        criteriaQuery: this.filterQuery+"&vendorId.equals="+accountStore.userDetails.cVendorId,
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data.map((item: any) => {
          return item;
        });

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        this.$emit('total-count-changed', this.queryCount);

        this.statementButtonDisabled();
        this.selectedRows = {};
        /*this.$nextTick(() => {
          console.log('taxInvoice refs: %O', this.$refs.taxInvoice);
        })*/
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

  private statementButtonDisabled(){
    if(this.selectedRows.verificationStatus == "DRF"){
      this.disabledButton = false;
    }else{
      this.disabledButton = true;
    }
  }

  public verificationFilter() {

    this.filterQuery = '';

    if (!!this.filter.verificationNo) {
      this.filterQuery = "verificationNo.equals=" + this.filter.verificationNo;
    }
    if (!!this.filter.invoiceNo) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceNo.equals=" + this.filter.invoiceNo;
    }
    if (!!this.filter.taxInvoiceNo) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoice.equals=" + this.filter.taxInvoiceNo;
    }
    if (!!this.filter.verificationStatus) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationStatus.equals=" + this.filter.verificationStatus;
    }
    if (!!this.filter.verificationDateFrom) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationDate.greaterOrEqualThan=" + this.filter.verificationDateFrom;
    }
    if (!!this.filter.invoiceDateFrom) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceDate.greaterOrEqualThan=" + this.filter.invoiceDateFrom;
    }
    if (!!this.filter.taxInvoiceDateFrom) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceDate.greaterOrEqualThan=" + this.filter.taxInvoiceDateFrom;
    }
    if (!!this.filter.verificationDateTo) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "verificationDate.lessOrEqualThan=" + this.filter.verificationDateTo;
    }
    if (!!this.filter.invoiceDateTo) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "invoiceDate.lessOrEqualThan=" + this.filter.invoiceDateTo;
    }
    if (!!this.filter.taxInvoiceDateTo) {
      if (this.filterQuery) {
        this.filterQuery += "&"
      }
      this.filterQuery += "taxInvoiceDate.lessOrEqualThan=" + this.filter.taxInvoiceDateTo;
    }

    this.retrieveAllRecords();
  }

  public addEVerification() {
    this.index = false;
    this.selectedRows = {};
  }

  formatDocumentStatus(value: string) {
    return this.documentStatuses.find(status => status.key === value)?.value;
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

        if(param[0].value == this.keyReference){
          this.documentStatuses = referenceList;
        }
    });
  }

}
