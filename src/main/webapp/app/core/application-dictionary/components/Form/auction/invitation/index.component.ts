import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import SystemService from '@/shared/service/system.service';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import BidSubmission from '../submission/bid-submission.vue';
import AuctionInvitationDetail from './auction-invitation-detail.vue';

const enum AuctionPage {
  MAIN = 'main',
  REVIEW = 'review',
  LIVE = 'live'
}

const baseApiAuction = 'api/m-auctions'
const baseApiAuctionInvitation = 'api/m-auction-invitations';
const baseApiAuctionSubmission = 'api/m-auction-submissions';
const baseApiDocType = 'api/c-document-types';
const baseApiReferenceList = 'api/ad-reference-lists';

@Component({
  components: {
    AuctionInvitationDetail,
    BidSubmission
  }
})
export default class AuctionInvitation extends Mixins(AccessLevelMixin) {

  @Inject('systemService')
  private systemService: () => SystemService;

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  setupTabName: string = 'INF';

  gridData: any[] = [];

  auction: any = {};
  selectedRow: any = {};
  selectedItems: any[] = [];

  section: AuctionPage = AuctionPage.MAIN;

  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;

  docTypeId: number = null;
  docStatuses: any[] = [];

  documentAction: string = null;
  documentActions: any[] = [];

  get auctionData() {
    return {
      ...this.selectedRow,
      ...{
        documentNo: this.selectedRow.documentNo,
        documentStatus: this.selectedRow.documentStatus,
        name: this.selectedRow.auctionName,
        auctionDocumentNo: this.selectedRow.auctionDocumentNo,
        currencyId: this.selectedRow.auctionCurrencyId,
        contentId: this.selectedRow.auctionContentId,
        prerequisiteId: this.selectedRow.auctionPrerequisiteId,
        ruleId: this.selectedRow.auctionRuleId,
      }
    }
  }

  set auctionData(data: any) {
    this.selectedRow.documentAction = data.documentAction;
    this.selectedRow.documentStatus = data.documentStatus;
  }

  get hasItems() {
    return this.selectedItems.length > 0;
  }

  get isAccepted() {
    return this.selectedRow.documentStatus === 'ACC';
  }

  get isDeclined() {
    return this.selectedRow.documentStatus === 'DCL';
  }

  get isDraft() {
    return this.selectedRow.documentStatus === 'DRF';
  }

  get isStarted() {
    return this.selectedRow.documentStatus === 'STR';
  }

  get isItemSelectionTab() {
    return this.setupTabName === 'ITM';
  }

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get mainPage() {
    return this.section === AuctionPage.MAIN;
  }

  get reviewPage() {
    return this.section === AuctionPage.REVIEW;
  }

  get submissionPage() {
    return this.section === AuctionPage.LIVE;
  }

  get selectedAction() {
    return this.documentActions.find(action => action.value === this.documentAction)?.name || this.documentAction;
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

  onSelectItems() {
    this.loading = true;
    const submission = {
      adOrganizationId: this.selectedRow.adOrganizationId,
      auctionId: this.selectedRow.auctionId,
      vendorId: this.vendorInfo.id,
      submissionItems: this.selectedItems.map(item => ({
        adOrganizationId: this.selectedRow.adOrganizationId,
        auctionItemId: item.id,
      }))
    };

    this.commonService(`${baseApiAuctionSubmission}/attend`)
      .create(submission)
      .then(() => this.$message.success('You are attending the auction of the selected item(s)'))
      .catch(err => {
        console.error('Failed to process the auction attendance', err);
        this.$message.error('Failed to process the auction attendance');
      })
      .finally(() => this.loading = false);
  }

  async created() {
    this.retrieveDocumentType('Auction Invitation');
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

    this.commonService(baseApiAuctionInvitation)
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

  private retrieveDocumentActions(docTypeId: number) {
    if (AccountStoreModule.grantedDocActions.has(docTypeId)) {
      const referenceListIds = [...AccountStoreModule.grantedDocActions.get(docTypeId)];

      this.commonService(baseApiReferenceList)
      .retrieve({
        criteriaQuery: referenceListIds.map(id => `id.in=${id}`)
      })
      .then(res => {
        this.documentActions = res.data;
      });
    }
  }

  private retrieveDocumentType(name: string) {
    this.commonService(baseApiDocType)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `name.equals=${name}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.docTypeId = res.data[0].id;
          this.retrieveDocumentActions(this.docTypeId);
        }
      });
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetails(row: any) {
    this.selectedRow = row;
    this.section = AuctionPage.REVIEW;
  }

  viewSubmission(row: any) {
    this.selectedRow = row;
    this.commonService(baseApiAuction)
      .find(row.auctionId)
      .then(res => {
        this.auction = res;
        this.section = AuctionPage.LIVE;
      })
  }

}
