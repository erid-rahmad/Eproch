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

  private filterQuery: string = '';
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

  public dialogConfirmationVisible: boolean = false;
  public filter: any = {};
  public radioSelection: number = null;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get isVendor() {
    return accountStore.userDetails.vendor;
  }

  created(){
    this.initStatusOptions();
    this.initVendorOptions();
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

  public singleSelection(row) {
    this.radioSelection = this.gridData.indexOf(row);
    this.selectedRow = row;
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
    
    this.filterQuery = buildCriteriaQueryString([
      'mMatchType.equals=1',
      this.filterQuery,
      watchListQuery,
      this.isVendor ? `vendorId.equals=${accountStore.userDetails.cVendorId}` : null
    ]);
    
    this.dynamicWindowService('/api/m-match-pos')
      .retrieve({
        criteriaQuery: this.filterQuery,
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
    const query = [];

    if (!!form.receiptNo) {
      query.push(`receiptNo.equals=${form.receiptNo}`);
    }
    if (!!form.receiptDateFrom) {
      query.push(`receiptDate.greaterOrEqualThan=${form.receiptDateFrom}`)
    }
    if (!!form.receiptDateTo) {
      query.push(`receiptDate.lessOrEqualThan=${form.receiptDateTo}`)
    }
    if (!!form.poNo) {
      query.push(`poNo.equals=${form.poNo}`)
    }
    if (!!form.deliveryNo) {
      query.push(`deliveryNo.equals=${form.deliveryNo}`);
    }
    if (!!form.vendorId) {
      query.push(`vendorId.equals=${form.vendorId}`);
    }
    if (!!form.invoiced) {
      query.push(`invoiced.equals=${form.invoiced}`);
    }

    this.filterQuery = buildCriteriaQueryString(query);
    this.retrieveAllRecords();
  }

  formatDocumentStatus(value: string) {
    return this.statusOptions.find(status => status.key === `${value}`)?.label;
  }

}
