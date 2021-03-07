import WatchListMixin from '@/core/application-dictionary/mixins/WatchListMixin';
import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import buildCriteriaQueryString from '@/shared/filter/filters';
import { ElTable } from 'element-ui/types/table';

@Component
export default class ProductReceiveInfo extends mixins(ContextVariableAccessor, WatchListMixin) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 490,
    height: 470
  };

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  private baseQuery: string = '';
  private lookupQuery: string[] = [];
  private processing = false;

  private dialogTitle = "";
  private dialogMessage = "";
  private dialogButton = "";
  private dialogValue = "";
  private dialogType = "";

  public gridData: Array<any> = [];
  private totalAmount: number = null;
  private mMatchType: string = "";

  selectedRow: any = {};
  public vendorOptions: any = [];
  public statusOptions: any = [];

  public filter: any = {};
  public radioSelection: number = null;

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

  get isVendor() {
    return accountStore.userDetails.vendor;
  }

  created() {
    this.baseQuery = buildCriteriaQueryString([
      'mMatchType.equals=1',
      this.isVendor ? `vendorId.equals=${accountStore.userDetails.cVendorId}` : null
    ]);

    this.initStatusOptions();
    this.initVendorOptions();
    this.retrieveWindowMeta();
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

  public checkCurrentRow(currentRowOnly: boolean) {
    if (currentRowOnly && !this.selectedRow) {
      this.$message.error('Please select at least one row');
      this.$set(this.exportParameter, 'currentRowOnly', false);
    }
  }
  
  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  public singleSelection(row) {
    this.radioSelection = this.gridData.indexOf(row);
    this.selectedRow = row;
    this.exportParameter.recordId = row.id;
  }

  public exportData() {
    this.exportingData = true;
    const link = document.createElement('a');
    link.setAttribute('download', 'PO_Receipt.csv');
    link.className = 'download-anchor';
    document.body.appendChild(link);

    this.lookupQuery.forEach(query => {
      const queryPairs = query.split('=');
      this.exportParameter.parameterMapping[queryPairs[0]] = queryPairs[1];
      this.exportParameter.parameterMapping['mMatchType.equals'] = 1;
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
          'name.equals=Match PO'
        ]
      })
      .then(res => {
        if (res.data.length) {
          this.exportParameter.windowId = res.data[0].id
        }
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
    
    this.dynamicWindowService('/api/m-match-pos')
      .retrieve({
        criteriaQuery: filterQuery,
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

  private clearSelection() {
    (<ElTable>this.$refs.mainTable)?.setCurrentRow();
    this.radioSelection = null;
    this.selectedRow = null;
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
    this.retrieveAllRecords();
  }

  private initStatusOptions() {
    this.dynamicWindowService(null)
      .retrieveReferenceLists('productReceiveStatus')
      .then(res => {
        this.statusOptions = res.map(item => 
          ({
              key: item.value,
              label: item.name
          })
        );
      });
  }

  public initVendorOptions(): void {
    this.dynamicWindowService('/api/c-vendors')
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ]
      })
      .then(res => {
        this.vendorOptions = res.data.map((item: any) =>
          ({
              key: item.id,
              label: item.name
          })
        );
      });
  }

  public verificationFilter() {
    const form = this.filter;
    this.lookupQuery = [];

    if (!!form.receiptNo) {
      this.lookupQuery.push(`receiptNo.equals=${form.receiptNo}`);
    }
    if (!!form.receiptDateFrom) {
      this.lookupQuery.push(`receiptDate.greaterOrEqualThan=${form.receiptDateFrom}`)
    }
    if (!!form.receiptDateTo) {
      this.lookupQuery.push(`receiptDate.lessOrEqualThan=${form.receiptDateTo}`)
    }
    if (!!form.poNo) {
      this.lookupQuery.push(`poNo.equals=${form.poNo}`)
    }
    if (!!form.deliveryNo) {
      this.lookupQuery.push(`deliveryNo.equals=${form.deliveryNo}`);
    }
    if (!!form.vendorId) {
      this.lookupQuery.push(`vendorId.equals=${form.vendorId}`);
    }
    if (!!form.invoiced) {
      this.lookupQuery.push(`invoiced.equals=${form.invoiced}`);
    }

    this.retrieveAllRecords();
  }

  formatDocumentStatus(value: string) {
    return this.statusOptions.find(status => status.key === `${value}`)?.label;
  }

}
