import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import ContractMessageBoard from '../contract-message-board.vue';
import {log} from "util";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const baseApiContract = 'api/m-contracts';

@Component({
  components: {
    ContractMessageBoard,
  }
})
export default class MessageBoardGrid extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;

  gridData: any[] = [];

  selectedRow: any = {};

  isMainPage = true;

  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;

  docStatuses: any[] = [];

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onCloseClicked() {
    this.isMainPage = true;
    this.transition();
  }

  created() {
    const query = this.$route.query;
    this.retrieveDocStatuses();
    this.transition();
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

  private loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  private transition(): void {
    this.retrieveAllRecords();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  printDocStatus(status: string) {
    return this.docStatuses.find(item => item.value === status)?.name || status;
  }

  private retrieveDocStatuses() {
    this.commonService(null).retrieveReferenceLists('docStatus')
      .then(res => this.docStatuses = res)
      .catch(err => this.$message.warning('Failed to get document statuses'));
  }

  private retrieveAllRecords(): void {
    this.loading = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.commonService(baseApiContract)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `vendorId.equals=${AccountStoreModule.vendorInfo.id}`
        ]),
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;

        if (this.gridData.length) {
          this.setRow(this.gridData[0]);
        }
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message.error(err.detail || err.message);
      })
      .finally(() => this.loading = false);
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetails(row: any) {
    this.selectedRow = row;
    this.isMainPage = false;
  }
}
