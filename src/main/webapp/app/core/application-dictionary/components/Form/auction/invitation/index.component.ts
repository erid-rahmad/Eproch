import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AuctionInvitationDetail from './auction-invitation-detail.vue';

const enum AuctionPage {
  MAIN = 'main',
  REVIEW = 'review'
}

const baseApiAuctionInvitation = 'api/m-auction-invitations';
const baseApiDocType = 'api/c-document-types';
const baseApiReferenceList = 'api/ad-reference-lists';

@Component({
  components: {
    AuctionInvitationDetail,
  }
})
export default class AuctionInvitation extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  confirmationVisible: boolean = false;
  deleteConfirmationVisible: boolean = false;
  loading: boolean = false;
  setupTabName: string = 'INF';

  gridData: any[] = [];

  schedule: any = {};
  selectedRow: any = {};
  evaluationList: any[] = [];

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
      id: this.selectedRow.auctionId,
      name: this.selectedRow.auctionName,
      documentNo: this.selectedRow.documentNo,
      documentStatus: this.selectedRow.documentStatus,
      currencyId: this.selectedRow.auctionCurrencyId,
      contentId: this.selectedRow.auctionContentId,
      prerequisiteId: this.selectedRow.auctionPrerequisiteId,
      ruleId: this.selectedRow.auctionRuleId,
    }
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

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get mainPage() {
    return this.section === AuctionPage.MAIN;
  }

  get reviewPage() {
    return this.section === AuctionPage.REVIEW;
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

  onDeleteClicked() {
    this.deleteConfirmationVisible = true;
  }

  onAcceptClicked() {
    this.documentAction = 'ACC';
    this.confirmationVisible = true;
  }

  onDeclineClicked() {
    this.documentAction = 'DCL';
    this.confirmationVisible = true;
  }

  created() {
    this.retrieveDocumentType('Auction Invitation');
    this.retrieveDocStatuses();
    this.transition();
  }

  acceptInvitation() {
    (<any>this.$refs.reviewPage).accept();
  }

  declineInvitation() {
    (<any>this.$refs.reviewPage).decline();
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

}