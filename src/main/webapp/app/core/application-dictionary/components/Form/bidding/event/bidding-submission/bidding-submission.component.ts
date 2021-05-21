import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Component, Inject, Vue } from 'vue-property-decorator';
import PriceProposal from './price-proposal.vue';
import ProposalForm from './proposal-form.vue';
import SubmissionForm from './submission-form.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const baseApiVendorScoringLine = 'api/m-vendor-scoring-lines';

const enum SubmissionPage {
  SUBMISSION = 'submission',
  PROPOSAL = 'proposal'
}

@Component({
  components: {
    SubmissionForm,
    PriceProposal,
    ProposalForm
  }
})
export default class BiddingSubmissionEvent extends Vue {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  section: SubmissionPage = SubmissionPage.SUBMISSION;
  formType: string = null;

  schedule: any = {};
  evaluationList: any[] = [];
  proposals: any[] = [];
  proposalName: string = null;
  selectedProposal: number = null;
  submission: any = {};

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get submitted() {
    return this.submission.documentStatus === 'SMT';
  }

  get cannotSave() {
    if (this.isVendor) {
      return this.submitted;
    }

    return this.proposalName === 'P' && this.submitted;
  }

  get displayedProposals() {
    if (!this.proposalName) {
      return this.proposals;
    }

    return this.proposals
      .filter(proposal => proposal.evaluationMethodLineName !== this.proposalName);
  }

  get proposalComponent() {
    if (this.proposalName === 'P') {
      return 'price-proposal';
    }

    return `proposal-form`;
  }

  get submissionPage() {
    return this.section === SubmissionPage.SUBMISSION;
  }

  onSubmissionFormLoaded(data: any) {
    this.formType = data.formType;
    this.schedule = data;

    // Get the proposal buttons based on the submission's form type.
    this.retrieveVendorScoringLines(data.biddingId, data.formType);
  }

  created() {
    const submissionId = (this.$route.query.submissionId as string);
    if (submissionId) {
      this.submission = {
        id: submissionId
      };
    }
    this.retrieveEvaluationList();
  }

  closeProposalPage() {
    this.section = SubmissionPage.SUBMISSION;
    this.proposalName = null;
  }

  openProposalForm(data: any) {
    this.proposalName = data.evaluationMethodLineName;
    this.selectedProposal = data;
    this.section = SubmissionPage.PROPOSAL;
  }

  printEvaluation(value: string) {
    return this.evaluationList.find(item => item.value === value)?.name || value;
  }

  private retrieveEvaluationList() {
    this.commonService(null)
      .retrieveReferenceLists('evaluationList')
      .then(res => this.evaluationList = res)
      .catch(_err => console.warn('Failed getting the evaluation list'));
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
  }

  saveProposal() {
    (<any>this.$refs.proposalForm).save();
  }

  submitProposals() {
    (<any>this.$refs.submissionForm).submit();
  }
}