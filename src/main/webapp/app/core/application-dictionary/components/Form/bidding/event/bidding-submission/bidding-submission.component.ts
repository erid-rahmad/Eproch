import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Component, Inject, Vue } from 'vue-property-decorator';
import PriceProposal from './price-proposal.vue';
import ProposalForm from './proposal-form.vue';
import SubmissionForm from './submission-form.vue';

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

  proposals: any[] = [];
  proposalName: string = null;
  selectedProposal: number = null;

  get proposalComponent() {
    if (this.proposalName === 'price') {
      return 'price-proposal';
    }

    return `proposal-form`;
  }

  get submissionPage() {
    return this.section === SubmissionPage.SUBMISSION;
  }

  onSubmissionFormLoaded(data: any) {
    this.formType = data.formType;
    this.retrieveVendorScoringLines(data.biddingId, data.formType);
  }

  closeProposalPage() {
    this.section = SubmissionPage.SUBMISSION;
  }

  openProposalForm(data: any) {
    const { evaluation } = data;
    this.proposalName = evaluation.toLowerCase();
    this.selectedProposal = data;
    this.section = SubmissionPage.PROPOSAL;
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
}