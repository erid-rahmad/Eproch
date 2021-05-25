import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import BidSubmission from './bid-submission.vue';

const enum AuctionPage {
  MAIN = 'main',
  SUBMISSION = 'submission',
}

const baseApiAuction = 'api/m-auctions';
const baseApiAuctionItem = 'api/m-auction-items';

@Component({
  components: {
    BidSubmission
  }
})
export default class AuctionList extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  biddingScheduleVisible: boolean = false;
  loading: boolean = false;
  formType: string = null;

  gridData: any[] = [];

  schedule: any = {};
  selectedRow: any = {};
  evaluationList: any[] = [];
  
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

  section: AuctionPage = AuctionPage.MAIN;

  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;

  biddingStatuses: any[] = [];
  docStatuses: any[] = [];

  actions: any[] = [
    {
      id: 1000,
      name: 'Pause',
      tooltip: 'Pause the timer',
      type: 'T',
      serviceName: 'pauseAuctionProcessTrigger'
    },
    {
      id: 2000,
      name: 'Stop',
      tooltip: 'Stop the timer',
      type: 'T',
      serviceName: 'stopAuctionProcessTrigger'
    },
    {
      id: 3000,
      name: 'Adjust Time',
      tooltip: 'Adjust date and time',
      type: 'P',
      popup: 'adjustScheduleForm'
    },
  ]

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get mainPage() {
    return this.section === AuctionPage.MAIN;
  }

  get submissionPage() {
    return this.section === AuctionPage.SUBMISSION;
  }

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onFormClosed() {
    this.section = AuctionPage.MAIN;
  }

  created() {
    this.retrieveAuctionStatuses();
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

  private retrieveAuctionStatuses() {
    this.commonService(null).retrieveReferenceLists('auctionStatus')
      .then(res => this.biddingStatuses = res)
      .catch(err => this.$message.warning('Failed to get bidding statuses'));
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

    this.commonService(baseApiAuction)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
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
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.loading = false;
      });
  }

  runAction(command: any) {
    console.log('Run action. command:', command);
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetails(row: any) {
    this.selectedRow = row;
    this.section = AuctionPage.SUBMISSION;
  }

}