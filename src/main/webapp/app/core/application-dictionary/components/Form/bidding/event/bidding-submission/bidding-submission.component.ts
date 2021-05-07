import { Component, Vue } from 'vue-property-decorator';
import AdministrationProposal from './administration-proposal.vue';
import PriceProposal from './price-proposal.vue';
import SubmissionForm from './submission-form.vue';
import TechnicalProposal from './technical-proposal.vue';

const enum SubmissionPage {
  SUBMISSION = 'submission',
  ADMINISTRATION_PROPOSAL = 'administration_proposal',
  PRICE_PROPOSAL = 'price_proposal',
  TECHNICAL_PROPOSAL = 'technical_proposal'
}

@Component({
  components: {
    SubmissionForm,
    AdministrationProposal,
    TechnicalProposal,
    PriceProposal
  }
})
export default class BiddingSubmissionEvent extends Vue {

  section: SubmissionPage = SubmissionPage.SUBMISSION;
  formType: string = null;

  get submissionPage() {
    return this.section === SubmissionPage.SUBMISSION;
  }

  get administrationProposalPage() {
    return this.section === SubmissionPage.ADMINISTRATION_PROPOSAL;
  }

  get priceProposalPage() {
    return this.section === SubmissionPage.PRICE_PROPOSAL;
  }

  get technicalProposalPage() {
    return this.section === SubmissionPage.TECHNICAL_PROPOSAL;
  }

  get showAdministrationProposal() {
    return ['S1', 'S2', 'S3'].includes(this.formType);
  }

  get showTechnicalProposal() {
    return ['S1', 'S2', 'S3'].includes(this.formType);
  }

  get showPriceProposal() {
    return ['S1', 'S4'].includes(this.formType);
  }

  onSubmissionFormLoaded(data: any) {
    this.formType = data.formType;
  }

  closeProposalPage() {
    this.section = SubmissionPage.SUBMISSION;
  }

  openAdministrationProposal() {
    this.section = SubmissionPage.ADMINISTRATION_PROPOSAL;
  }

  openTechnicalProposal() {
    this.section = SubmissionPage.TECHNICAL_PROPOSAL;
  }

  openPriceProposal() {
    this.section = SubmissionPage.PRICE_PROPOSAL;
  }
}