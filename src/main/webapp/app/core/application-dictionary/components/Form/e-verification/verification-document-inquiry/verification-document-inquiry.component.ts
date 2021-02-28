import WatchListMixin from '@/core/application-dictionary/mixins/WatchListMixin';
import settings from '@/settings';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { ElTable } from 'element-ui/types/table';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import DetailVerificationDocument from './detail-verification-document.vue';
import UpdateVoucher from './update-voucher.vue';

@Component({
  components: {
    UpdateVoucher,
    DetailVerificationDocument
  }
})
export default class EVerification extends mixins(ContextVariableAccessor, WatchListMixin) {
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
  public docStatus: string = "docStatus";
  public paymentStatus: string = "paymentStatus";

  private baseQuery: string = '';
  private lookupQuery: string[] = [];
  private processing = false;

  private dialogTitle = "";
  private dialogMessage = "";
  private dialogButton = "";
  private dialogValue = "";
  private dialogType = "";

  public gridData: Array<any> = [];

  selectedRow: any = {};
  public vendorOptions: any[] = [];
  public documentStatusOptions: any[] = [];
  public paymentStatusOptions: any[] = [];

  public dialogConfirmationVisible: boolean = false;
  public filter: any = {};

  public radioSelection: number = null;
  public confirmReopen: boolean = false;

  get canReopen() {
    return this.selectedRow?.apReversed && this.selectedRow?.documentStatus !== 'ROP';
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.retrieveReferenceLists('docStatus');
    this.retrieveReferenceLists('paymentStatus');
    this.retrieveVendorOptions();
    this.retrieveAllRecords();
  }

  closeDetailVerification() {
    this.index = true;
    this.dialogConfirmationVisible = false;
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
  }

  public showDialogConfirmation(key: string) {
    if (!this.selectedRow) {
      this.$message({
        message: 'Please select a row',
        type: 'warning'
      });
      return
    }

    if (key == "detail") {
      this.index = false;
    } else if (key == "update") {
      this.dialogConfirmationVisible = true;
    } else if (key == "print") {
      this.buttonPrint("invoice-verification");
    } else if (key == "printSummary") {
      this.buttonPrint("summary-invoice-verification");
    } else if (key == "printVerificationReceipt") {
      this.buttonPrint("invoice-verification-receipt");
    }
  }

  public buttonPrint(key): void {
    const data = { ...this.selectedRow };
    window.open(`/api/m-verifications/report/${data.id}/${data.documentNo}/${key}`, '_blank');
  }

  public reopenDocument() {
    const data = { ...this.selectedRow };
    data.documentStatus = 'ROP';

    this.dynamicWindowService(this.baseApiUrl)
      .update(data)
      .then(() => {
        const message = 'Invoice verification has been reopened'

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
        this.confirmReopen = false;
      });
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
      });
  }

  private clearSelection() {
    (<ElTable>this.$refs.mainTable)?.setCurrentRow();
    this.radioSelection = null;
    this.selectedRow = null;
  }

  public retrieveVendorOptions(): void {
    this.processing = true;

    this.dynamicWindowService('/api/c-vendors')
      .retrieve({
        criteriaQuery: 'active.equals=true',
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.vendorOptions = res.data.map(item => ({
          key: item.id,
          value: item.name
        }));
      })
      .catch(err => {
        console.error('Failed getting vendor options. %O', err);
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

  private retrieveReferenceLists(code: string) {
    this.dynamicWindowService('/api/ad-reference-lists')
    .retrieve({
      criteriaQuery: [
        `active.equals=true`,
        `adReferenceValue.equals=${code}`
      ],
      paginationQuery: {
        page: 0,
        size: 100,
        sort: ['name']
      }
    })
    .then(res => {
      const prop = code === 'docStatus' ? 'documentStatusOptions' : 'paymentStatusOptions';
      this[prop] = res.data.map((item: any) => 
        ({
            key: item.value,
            value: item.name
        })
      );
    });
  }

  public verificationFilter() {
    const form = this.filter;
    this.lookupQuery = [];

    if (!!this.filter.documentNo) {
      this.lookupQuery.push(`documentNo.equals=${form.documentNo}`);
    }
    if (!!this.filter.invoiceNo) {
      this.lookupQuery.push(`invoiceNo.equals=${form.invoiceNo}`);
    }
    if (!!this.filter.taxInvoiceNo) {
      this.lookupQuery.push(`taxInvoiceNo.equals=${form.taxInvoiceNo}`);
    }
    if (!!this.filter.vendorName) {
      this.lookupQuery.push(`vendorId.equals=${form.vendorName}`);
    }
    if (!!this.filter.documentStatus) {
      this.lookupQuery.push(`documentStatus.equals=${form.documentStatus}`);
    }
    if (!!this.filter.verificationDateFrom) {
      this.lookupQuery.push(`dateTrx.greaterOrEqualThan=${form.verificationDateFrom}`);
    }
    if (!!this.filter.verificationDateTo) {
      this.lookupQuery.push(`dateTrx.lessOrEqualThan=${form.verificationDateTo}`);
    }
    if (!!this.filter.invoiceDateFrom) {
      this.lookupQuery.push(`invoiceDate.greaterOrEqualThan=${form.invoiceDateFrom}`);
    }
    if (!!this.filter.invoiceDateTo) {
      this.lookupQuery.push(`invoiceDate.lessOrEqualThan=${form.invoiceDateTo}`);
    }
    if (!!this.filter.taxInvoiceDateFrom) {
      this.lookupQuery.push(`taxInvoiceDate.greaterOrEqualThan=${form.taxInvoiceDateFrom}`);
    }
    if (!!this.filter.taxInvoiceDateTo) {
      this.lookupQuery.push(`taxInvoiceDate.lessOrEqualThan=${form.taxInvoiceDateTo}`);
    }
    if (!!this.filter.payStatus) {
      this.lookupQuery.push(`payStatus.equals=${form.payStatus}`);
    }

    this.retrieveAllRecords();
  }

  formatDocumentStatus(value: string) {
    return this.documentStatusOptions.find(status => status.key === value)?.value || value;
  }

  formatPaymentStatus(value: string) {
    return this.paymentStatusOptions.find(status => status.key === value)?.value || value;
  }

}
