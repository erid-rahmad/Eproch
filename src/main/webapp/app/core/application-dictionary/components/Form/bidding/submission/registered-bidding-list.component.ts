import BiddingSchedule from "@/core/application-dictionary/components/Form/bidding/submission/bidding-schedule.vue";
import ProposalForm from '@/core/application-dictionary/components/Form/bidding/event/bidding-submission/proposal-form.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AdministrationProposal from '../event/bidding-submission/administration-proposal.vue';
import PriceProposal from '../event/bidding-submission/price-proposal.vue';
import SubmissionForm from "../event/bidding-submission/submission-form.vue";
import TechnicalProposal from '../event/bidding-submission/technical-proposal.vue';

const enum SubmissionPage {
  MAIN = 'main',
  SUBMISSION = 'submission',
  PROPOSAL = 'proposal'
}

const baseApiSubmission = 'api/m-bidding-submissions';
const baseApiVendorScoringLine = 'api/m-vendor-scoring-lines';

@Component({
  components: {
    BiddingSchedule,
    SubmissionForm,
    AdministrationProposal,
    TechnicalProposal,
    PriceProposal,
    ProposalForm
  }
})
export default class RegisteredBiddingList extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  biddingScheduleVisible: boolean = false;
  loading: boolean = false;
  formType: string = null;

  gridData: any[] = [];

  schedule: any = {};
  selectedRow: any = {};
  
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

  section: SubmissionPage = SubmissionPage.MAIN;

  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;

  proposals: any[] = [];
  proposalName: string = null;
  selectedProposal: number = null;

  biddingStatuses: any[] = [];
  docStatuses: any[] = [];

  get displayedProposals() {
    if (!this.proposalName) {
      return this.proposals;
    }

    return this.proposals
      .filter(proposal => proposal.evaluationMethodLineName !== this.proposalName);
  }

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get mainPage() {
    return this.section === SubmissionPage.MAIN;
  }

  get submissionPage() {
    return this.section === SubmissionPage.SUBMISSION;
  }

  get proposalComponent() {
    if (this.proposalName === 'P') {
      return 'price-proposal';
    }

    return `proposal-form`;
  }

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onFormClosed() {
    this.proposals = [];
    this.section = SubmissionPage.MAIN;
  }

  onSubmissionFormLoaded(data: any) {
    this.schedule = data;
    this.formType = data.formType;

    // Get the proposal buttons based on the submission's form type.
    this.retrieveVendorScoringLines(data.biddingId, data.formType);
  }

  created() {
    if (this.isVendor) {
      this.retrieveBiddingStatuses();
      this.retrieveDocStatuses();
      this.transition();
    } else {
      this.section = SubmissionPage.SUBMISSION;
    }
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

  closeProposalPage() {
    this.section = SubmissionPage.SUBMISSION;
    this.proposalName = null;
    this.selectedProposal = null;
  }

  isStarted(data: any) {
    return data.biddingScheduleStatus && data.biddingScheduleStatus !== 'N';
  }

  openProposalForm(data: any) {
    this.proposalName = data.evaluationMethodLineName;
    this.selectedProposal = data;
    this.section = SubmissionPage.PROPOSAL;
  }

  printBiddingStatus(status: string) {
    return this.biddingStatuses.find(stat => stat.value === status)?.name || status;
  }

  printSubmissionStatus(status: string) {
    return this.docStatuses.find(stat => stat.value === status)?.name || status;
  }

  private retrieveBiddingStatuses() {
    this.commonService(null).retrieveReferenceLists('biddingStatus')
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

    this.commonService(baseApiSubmission)
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

  private retrieveVendorScoringLines(biddingId: number, formType: string) {
    this.commonService(baseApiVendorScoringLine)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `biddingId.equals=${biddingId}`,
          `formType.equals=${formType}`
        ]
      })
      .then(res => {
        this.proposals = res.data;
      })
      .catch(err => {
        console.error('Failed to load proposals. %O', err);
        this.$message.error('Failed to load proposals');
      })
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  saveProposal() {
    (<any>this.$refs.proposalForm).save();
  }

  submitProposals() {
    (<any>this.$refs.submissionForm).submit();
  }

  viewSchedule(row: any) {
    this.selectedRow = row;
    this.biddingScheduleVisible = true;
  }

  viewDetails(row: any) {
    this.selectedRow = row;
    this.section = SubmissionPage.SUBMISSION;
  }

}