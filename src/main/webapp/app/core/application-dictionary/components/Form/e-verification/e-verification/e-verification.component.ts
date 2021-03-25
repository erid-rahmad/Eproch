import WatchListMixin from '@/core/application-dictionary/mixins/WatchListMixin';
import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { ElTable } from 'element-ui/types/table';
import { mixins } from 'vue-class-component';
import { Component, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import EVerificationUpdate from './e-verification-update.vue';

@Component({
  components: {
    EVerificationUpdate
  }
})
export default class EVerification extends mixins(WatchListMixin) {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

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

  public exportingData = false;
  public exportWizardVisible = false;
  public exportParameter = {
    windowId: 0,
    currentRowOnly: false,
    recordId: 0,
    includeLines: false,
    includedSubTabs: [],
    parameterMapping: {}
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get taxInvoicePattern() {
    return settings.taxNoPattern16digits;
  }

  created() {
    this.baseQuery = `vendorId.equals=${accountStore.userDetails.cVendorId}`;
    this.initDocumentStatusOptions();
    this.retrieveWindowMeta();
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
    this.exportParameter.recordId = row.id;
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

  public checkCurrentRow(currentRowOnly: boolean) {
    if (currentRowOnly && !this.selectedRow) {
      this.$message.error('Please select at least one row');
      this.$set(this.exportParameter, 'currentRowOnly', false);
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

  public exportData() {
    this.exportingData = true;
    const link = document.createElement('a');
    link.setAttribute('download', 'e-Verification.csv');
    link.className = 'download-anchor';
    document.body.appendChild(link);

    this.lookupQuery.forEach(query => {
      const queryPairs = query.split('=');
      this.exportParameter.parameterMapping[queryPairs[0]] = queryPairs[1];
    });

    this.dynamicWindowService(null)
      .export(this.exportParameter)
      .then(res => {
        const buffer = new Buffer(res.data);
        const url = window.URL.createObjectURL(new Blob([buffer], {
          type: 'text/csv'
        }));

        link.href = url;
        link.click();
      })
      .catch(err => {
        console.log('Failed to export document.', this.$t(err.message));
        this.$message.error('There is a problem when exporting the document');
      })
      .finally(() => {
        document.body.removeChild(link);
        this.exportingData = false;
        this.exportWizardVisible = false;
      });
  }

  private retrieveWindowMeta() {
    this.dynamicWindowService('/api/ad-windows')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          'name.equals=Invoice Verification'
        ]
      })
      .then(res => {
        if (res.data.length) {
          this.exportParameter.windowId = res.data[0].id
          this.retrieveSubTabMeta(this.exportParameter.windowId);
        }
      });
  }

  private retrieveSubTabMeta(windowId: number) {
    this.dynamicWindowService('/api/ad-tabs')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          'parentTabId.specified=true',
          `adWindowId.equals=${windowId}`
        ]
      })
      .then(res => {
        this.exportParameter.includedSubTabs = res.data.map(tab => tab.id);
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
    this.exportParameter.recordId = 0;
    this.exportParameter.currentRowOnly = false;
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
      this.lookupQuery.push(`taxInvoice.equals=${form.taxInvoiceNo.replace(/[-.]+/g, '')}`);
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
