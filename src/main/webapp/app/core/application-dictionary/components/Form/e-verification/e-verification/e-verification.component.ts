import WatchListMixin from '@/core/application-dictionary/mixins/WatchListMixin';
import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import EVerificationUpdate from './e-verification-update.vue';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { ElTable } from 'element-ui/types/table';

@Component({
  components: {
    EVerificationUpdate
  }
})
export default class EVerification extends mixins(ContextVariableAccessor, WatchListMixin) {
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
  private baseQuery: string = '';
  private lookupQuery: string[] = [];
  public filter: any = {};
  processing = false;
  public gridData: Array<any> = [];
  selectedRow: any = {};
  public radioSelection: number = null;

  dialogTitle: string = null;
  dialogMessage: string = null;
  dialogButton: string = null;
  dialogType: string = null;
  private dialogValue = "";
  public confirmDocStatusUpdate: boolean = false;

  public documentStatuses: any[] = [];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created() {
    this.baseQuery = `vendorId.equals=${accountStore.userDetails.cVendorId}`;
    this.initDocumentStatusOptions();
    this.retrieveAllRecords();
  }

  closeEVerificationUpdate() {
    this.index = true;
    this.retrieveAllRecords();
  }

  changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    const {propOrder: property, reverse} = this;
    this.$emit('order-changed', { property, reverse });
    this.transition();
  }

  private sort(): Array<any> {
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

  rowClassName({row}) {
    if (row.documentStatus !== 'CNL' && row.receiptReversed) {
      return 'danger-row';
    } else if (row.apReversed) {
      return 'warning-row';
    }

    return '';
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  public singleSelection(row) {
    this.radioSelection = this.gridData.indexOf(row);
    this.selectedRow = row;
    this.toggleToolbarButtons();
  }

  public showDialogConfirmation(key: string) {
    if (!this.selectedRow) {
      this.$message({
        message: 'Please select a row',
        type: 'warning'
      });
      return;
    }

    if (key == "cancel") {
      this.dialogTitle = "Cancel Invoice Verification";
      this.dialogMessage = "Are you sure you want to cancel the document?";
      this.dialogButton = "Cancel";
      this.dialogValue = "CNL";
      this.dialogType = "danger";
      this.confirmDocStatusUpdate = true;
    } else if (key == "submit") {
      this.dialogTitle = "Submit Invoice Verification";
      this.dialogMessage = "Are you sure you want to submit the document?";
      this.dialogButton = "Submit";
      this.dialogValue = "SMT";
      this.dialogType = "primary";
      this.confirmDocStatusUpdate = true;
    } else if (key == "print") {
      this.buttonPrint("invoice-verification");
    } else if(key == "printSummary") {
      this.buttonPrint("summary-invoice-verification");
    } else if (key == "update") {
      this.index = false;
    }
  }
  
  public updateDocumentStatus(): void {
    const data = { ...this.selectedRow };
    data.documentStatus = this.dialogValue;

    this.dynamicWindowService(this.baseApiUrl)
      .update(data)
      .then(res => {
        const message = this.dialogValue === 'SMT'
          ? 'Invoice verification has been submitted'
          : 'Invoice verification has been canceled'

        this.toggleToolbarButtons();
        this.retrieveAllRecords();
        this.$message({
          message: message,
          type: 'success'
        });
      })
      .catch(err => {
        this.$message({
          message: err.response?.data?.detail || err.message,
          type: 'error'
        });
      })
      .finally(() => {
        this.processing = false;
        this.confirmDocStatusUpdate = false;
      });
  }

  public buttonPrint(key): void {
    const data = { ...this.selectedRow };
    window.open(`/api/m-verifications/report/${data.id}/${data.documentNo}/${key}`, '_blank');
  }

  public retrieveAllRecords(): void {
    this.clearSelection();
    this.processing = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    const watchListQuery = this.getWatchListQuery();

    if (watchListQuery) {
      watchListQuery.split('&').forEach(field => {
        const key = field.substring(0, field.indexOf('.'));
        const value = field.substring(field.indexOf('=') + 1);
        this.$set(this.filter, key, value);
      });
    }

    const filterQuery = buildCriteriaQueryString([
      this.baseQuery,
      watchListQuery,
      ...this.lookupQuery
    ]);
    
    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: filterQuery,
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        this.$emit('total-count-changed', this.queryCount);

        this.toggleToolbarButtons();
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

  private clearSelection() {
    (<ElTable>this.$refs.mainTable)?.setCurrentRow();
    this.radioSelection = null;
    this.selectedRow = null;
  }

  private toggleToolbarButtons() {
    const docStatus = this.selectedRow?.documentStatus;
    this.disabledButton = docStatus !== 'DRF' && docStatus !== 'RJC' && docStatus !== 'ROP';
  }

  public verificationFilter() {
    const form = this.filter;
    this.lookupQuery = [];

    if (!!form.documentNo) {
      this.lookupQuery.push(`documentNo.equals=${form.documentNo}`);
    }
    if (!!form.invoiceNo) {
      this.lookupQuery.push(`invoiceNo.equals=${form.invoiceNo}`);
    }
    if (!!form.taxInvoiceNo) {
      this.lookupQuery.push(`taxInvoiceNo.equals=${form.taxInvoiceNo}`);
    }
    if (!!form.documentStatus) {
      this.lookupQuery.push(`documentStatus.equals=${form.documentStatus}`);
    }
    if (!!form.verificationDateFrom) {
      this.lookupQuery.push(`dateTrx.greaterOrEqualThan=${form.verificationDateFrom}`);
    }
    if (!!form.verificationDateTo) {
      this.lookupQuery.push(`dateTrx.lessOrEqualThan=${form.verificationDateTo}`);
    }
    if (!!form.invoiceDateFrom) {
      this.lookupQuery.push(`invoiceDate.greaterOrEqualThan=${form.invoiceDateFrom}`);
    }
    if (!!form.invoiceDateTo) {
      this.lookupQuery.push(`invoiceDate.lessOrEqualThan=${form.invoiceDateTo}`);
    }
    if (!!form.taxInvoiceDateFrom) {
      this.lookupQuery.push(`taxInvoiceDate.greaterOrEqualThan=${form.taxInvoiceDateFrom}`);
    }
    if (!!form.taxInvoiceDateTo) {
      this.lookupQuery.push(`taxInvoiceDate.lessOrEqualThan=${form.taxInvoiceDateTo}`);
    }

    this.retrieveAllRecords();
  }

  public addEVerification() {
    this.index = false;
    this.selectedRow = {};
  }

  formatDocumentStatus(value: string) {
    return this.documentStatuses.find(status => status.key === value)?.label || value;
  }

  private initDocumentStatusOptions() {
    this.dynamicWindowService(null)
      .retrieveReferenceLists('docStatus')
      .then(res => {
        this.documentStatuses = res.map(item => 
          ({
              key: item.value,
              label: item.name
          })
        );
      });
  }

}
