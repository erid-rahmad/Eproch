import WatchListMixin from '@/core/application-dictionary/mixins/WatchListMixin';
import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import buildCriteriaQueryString from '@/shared/filter/filters';


@Component
export default class PaymentStatus extends mixins(ContextVariableAccessor, WatchListMixin) {
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

  private baseQuery: string = '';
  private lookupQuery: string[] = [];
  processing = false;

  public gridData: Array<any> = [];
  private totalAmount: number = null;

  selectedRow: any = {};
  public documentStatusOptions: any[] = [];
  public paymentStatusOptions: any[] = [];

  public dialogConfirmationVisible: boolean = false;
  public filter: any = {};
  public radioSelection: number = null;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created() {
    this.baseQuery = `vendorId.equals=${accountStore.userDetails.cVendorId}`;
    this.initDocumentStatusOptions();
    this.initPaymentStatusOptions();
  }

  mounted() {
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

  public singleSelection (row) {
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
        this.radioSelection = null;
        this.selectedRow = {};
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

  private initDocumentStatusOptions() {
    this.dynamicWindowService(null)
      .retrieveReferenceLists('docStatus')
      .then(res => {
        this.documentStatusOptions = res.map(item => 
          ({
            key: item.value,
            label: item.name
          })
        );
      });
  }

  private initPaymentStatusOptions() {
    this.dynamicWindowService(null)
      .retrieveReferenceLists('paymentStatus')
      .then(res => {
        this.paymentStatusOptions = res.map(item => 
          ({
            key: item.value,
            label: item.name
          })
        );
      });
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
    if (!!form.documentStatus) {
      this.lookupQuery.push(`documentStatus.equals=${form.documentStatus}`);
    }
    if (!!form.dateTrx) {
      this.lookupQuery.push(`dateTrx.lessOrEqualThan=${form.dateTrx}`);
    }
    if (!!form.invoiceDate) {
      this.lookupQuery.push(`invoiceDate.lessOrEqualThan=${form.invoiceDate}`);
    }
    if (!!form.payStatus) {
      this.lookupQuery.push(`payStatus.equals=${form.payStatus}`);
    }

    this.retrieveAllRecords();
  }

  formatDocumentStatus(value: string) {
    return this.documentStatusOptions.find(status => status.key === value)?.label || value;
  }

  formatPaymentStatus(value: string) {
    return this.paymentStatusOptions.find(status => status.key === value)?.label || value;
  }

}
