import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AuctionSetup from './setup/auction-setup.vue';
import BidSubmission from './submission/bid-submission.vue';

const enum AuctionPage {
  MAIN = 'main',
  SETUP = 'setup',
  SUBMISSION = 'submission',
}

const baseApiAuction = 'api/m-auctions';
const processName = 'mAuctionProcessTrigger';

@Component({
  components: {
    AuctionSetup,
    BidSubmission
  }
})
export default class AuctionList extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  biddingScheduleVisible: boolean = false;
  deleteConfirmationVisible: boolean = false;
  loading: boolean = false;
  publishing: boolean = false;
  formType: string = null;
  setupTabName: string = 'INF';

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

  get actions() {
    let keySeq = 0;
    const list = [];

    if (this.isPublished) {
      list.push({
        id: ++keySeq,
        name: 'Start',
        tooltip: 'Start the auction',
        type: 'T',
        serviceName: processName,
        params: {
          auctionId: this.selectedRow.id,
          action: 'STR'
        }
      });
    } else if (this.isStarted) {
      list.push({
        id: ++keySeq,
        name: 'Pause',
        tooltip: 'Pause the auction',
        type: 'T',
        serviceName: processName,
        params: {
          auctionId: this.selectedRow.id,
          action: 'PAS'
        }
      },
      {
        id: ++keySeq,
        name: 'Cancel',
        tooltip: 'Cancel the auction',
        type: 'T',
        serviceName: processName,
        params: {
          auctionId: this.selectedRow.id,
          action: 'CNL'
        }
      });
    }

    list.push({
      id: keySeq + 1,
      name: 'Overtime',
      tooltip: 'Make overtime',
      type: 'P',
      popup: 'adjustScheduleForm'
    });

    return list;
  }
  
  get isCanceled() {
    return this.selectedRow.documentStatus === 'CNL';
  }

  get isDraft() {
    return this.selectedRow.documentStatus === 'DRF';
  }

  get isPaused() {
    return this.selectedRow.documentStatus === 'PAS';
  }

  get isPublished() {
    return this.selectedRow.documentStatus === 'PUB';
  }

  get isStarted() {
    return this.selectedRow.documentStatus === 'STR';
  }

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get mainPage() {
    return this.section === AuctionPage.MAIN;
  }

  get setupPage() {
    return this.section === AuctionPage.SETUP;
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

  onCloseClicked() {
    this.section = AuctionPage.MAIN;
    this.setupTabName = 'INF';
    this.transition();
  }

  onDeleteClicked() {
    this.deleteConfirmationVisible = true;
  }

  onFormSaved() {
    (<any>this.$refs.setupPage).save();
  }

  onPublishClicked() {
    this.selectedRow.documentStatus = 'PUB';

    this.publishing = true;
    this.commonService(baseApiAuction)
      .update(this.selectedRow)
      .then(res => {
        this.$message.success(`Auction #${res.documentNo} has been published successfully`);
        this.selectedRow.processed = res.processed;
      })
      .catch(err => {
        console.error('Failed to publish the auction', err);
        this.$message.error(`Failed to publish auction #${this.selectedRow.documentNo}`);
      })
      .finally(() => this.publishing = false);
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

  deleteRecord() {
    this.commonService(baseApiAuction)
      .delete(this.selectedRow.id)
      .then(() => {
        this.$message.success(`Auction ${this.selectedRow.documentNo} has been deleted successfully`);
        this.transition();
        this.deleteConfirmationVisible = false;
      })
      .catch(err => {
        console.error('Failed to delete the auction', err);
        this.$message.error(`Failed to delete auction ${this.selectedRow.documentNo}`);
      });
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
    if (command.type === 'T') {
      this.commonService(`api/ad-triggers/process/${command.serviceName}`)
        .create(command.params)
    }
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetails(row: any) {
    this.selectedRow = row;
    this.section = AuctionPage.SUBMISSION;
  }

  viewSetup(row: any) {
    if ( ! row) {
      this.selectedRow = {
        name: null,
        description: null,
        documentNo: null,
        adOrganizationId: AccountStoreModule.organizationInfo.id,
        currencyId: null,
        costCenterId: null,
        ownerUserId: null
      };
    } else {
      this.selectedRow = row;
    }

    this.section = AuctionPage.SETUP;
  }
}