//import BiddingSchedule from "@/core/application-dictionary/components/Form/bidding/submission/bidding-schedule.vue";
//import ProposalForm from '@/core/application-dictionary/components/Form/bidding/event/bidding-submission/proposal-form.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from "../../../DynamicWindow/dynamic-window.service";
import SubmissionForm from './submission-form.vue';
import SubmissionList from './submission-list.vue';
//import AdministrationProposal from '../event/bidding-submission/administration-proposal.vue';
//import PriceProposal from '../event/bidding-submission/price-proposal.vue';
//import SubmissionForm from "../event/bidding-submission/submission-form.vue";
//import TechnicalProposal from './proposal-form.vue';

const enum SubmissionPage {
  MAIN = 'main',
  SUBMISSION = 'submission',
  PROPOSAL = 'proposal',
  LIST = 'list'
}

const baseApiSubmission = 'api/m-prequalification-submissions';
const baseApiInformation = 'api/m-prequalification-informations';
const baseApiVendorScoringLine = 'api/m-vendor-scoring-lines';

@Component({
  components: {
    //BiddingSchedule,
    //AdministrationProposal,
    //TechnicalProposal,
    //PriceProposal,
    //ProposalForm
    SubmissionForm,
    SubmissionList,
  }
})
export default class RegisteredBiddingList extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  biddingScheduleVisible: boolean = false;
  loading: boolean = false;
  formType: string = null;
  readonly:boolean=false;
  display:boolean=true;

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
  selectedProposal: any = null;

  biddingStatuses: any[] = [];
  docStatuses: any[] = [];

  get displayedProposals() {
    if (!this.proposalName) {
      return this.proposals;
    }

    return this.proposals
      .filter(proposal => proposal.evaluationMethodLineName !== this.proposalName);
  }

  setReadOnly (param){
    console.log("readonly",param)
    this.readonly=param;
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

  get submissionList() {
    return this.section === SubmissionPage.LIST;
  }

  get proposalComponent() {
    if (this.proposalName === 'P') {
      return 'price-proposal';
    }

    return `proposal-form`;
  }

  get submitted() {
    return this.selectedRow.documentStatus === 'SMT';
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
    this.retrieveBiddingStatuses();
    this.retrieveDocStatuses();
    this.retrieveEvaluationList();
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

  printEvaluation(value: string) {
    return this.evaluationList.find(item => item.value === value)?.name || value;
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

  private retrieveEvaluationList() {
    this.commonService(null)
      .retrieveReferenceLists('evaluationList')
      .then(res => this.evaluationList = res)
      .catch(_err => console.warn('Failed getting the evaluation list'));
  }

  private retrieveAllRecords(): void {
    this.loading = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.commonService(this.isVendor ? baseApiSubmission : baseApiInformation)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data;
        console.log("this grid data",this.gridData)
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
    console.log("this retrive scorline",biddingId,formType)
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
    if(this.isVendor)(<any>this.$refs.submissionForm).save();
    else (<any>this.$refs.submissionList).$children[0].save();
  }

  SubmmitProposal() {
    if(this.isVendor)(<any>this.$refs.submissionForm).save('SMT');
    else (<any>this.$refs.submissionList).$children[0].save('SMT2');
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
    this.section = this.isVendor ? SubmissionPage.SUBMISSION : SubmissionPage.LIST;
  }

  toggleDisplay(){
    this.display = !this.display;
  }

  closeInner(){
    (<any>this.$refs.submissionList).$children[0].back();
  }
}
