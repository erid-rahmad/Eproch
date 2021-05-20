import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue, Watch } from "vue-property-decorator";
import { AccountStoreModule } from '@/shared/config/store/account-store';
import Schema from "async-validator";

const baseApiEvalMethodCriteria = 'api/c-evaluation-method-criteria';
const baseApiVendorScoringCriteria = 'api/m-vendor-scoring-criteria';
const baseApiProposalAdministration = 'api/m-proposal-administrations';
const baseApiProposalTechnical = 'api/m-proposal-technicals';

export const proposalNameMap: Map<string, string> = new Map([
  ['A', 'Administration'],
  ['P', 'Price'],
  ['T', 'Technical'],
]);

const ProposalFormProps = Vue.extend({
  props: {
    data: Object,
    submissionId: Number
  }
});

@Component
export default class ProposalForm extends Mixins(AccessLevelMixin, ProposalFormProps)  {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  evaluationMethodCriteria: any[] = [];
  requirements: Map<number, string> = new Map();

  answers: any[] = [];

  validationSchema: any = {};

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get isAdministration() {
    return this.data.evaluationMethodLineName === 'A';
  }

  get isTechnical() {
    return this.data.evaluationMethodLineName === 'T';
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.answers = [];
    this.requirements.clear();
    this.retrieveVendorScoringCriteria(data.id, data.evaluationMethodLineId);
  }

  created() {
    if (this.isVendor) {
      this.validationSchema = {
        answer: {
          type: 'string',
          required: true,
          message: 'Answer is required'
        }
      };
    }

    this.retrieveVendorScoringCriteria(this.data.id, this.data.evaluationMethodLineId);
  }

  /**
   * First, we need to get the predefined requirements from m_vendor_scoring_criteria.
   * Then, populate the form with the questions from c_evaluation_method_criteria.
   * Finally, map the existing answers from m_proposal_xxx to each question, if any.
   * @param vendorScoringLineId 
   * @param evaluationMethodLineId 
   */
  private retrieveVendorScoringCriteria(vendorScoringLineId: number, evaluationMethodLineId: number) {
    this.commonService(baseApiVendorScoringCriteria)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `vendorScoringLineId.equals=${vendorScoringLineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach((criteria: any) => {
          this.requirements.set(criteria.biddingSubCriteriaLineId, criteria.requirement);
        });

        this.retrieveEvaluationMethodCriteria(evaluationMethodLineId);
      })
  }

  private retrieveEvaluationMethodCriteria(evaluationMethodLineId: number) {
    this.commonService(baseApiEvalMethodCriteria)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `evaluationMethodLineId.equals=${evaluationMethodLineId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodCriteria = res.data.map((evalMethodCriteria: any) => {
          evalMethodCriteria.evalMethodSubCriteriaList.forEach((evalMethodSubCriteria: any) => {
            evalMethodSubCriteria.biddingSubCriteriaDTO.forEach((biddingSubCriteria: any) => {
              biddingSubCriteria.criteriaLineDTO.forEach((subCriteriaLine: any) => {
                subCriteriaLine.requirement = this.requirements.get(subCriteriaLine.id)
                subCriteriaLine.answer = null;
                subCriteriaLine.documentEvaluation = false;

                // List of answers will be submitted later.
                this.answers.push(subCriteriaLine);
              });
            });
          });

          return evalMethodCriteria;
        });

        this.retrieveProposalData(this.submissionId);
      })
  }

  private retrieveProposalData(submissionId: number) {
    const baseApiUrl = this.isAdministration ? baseApiProposalAdministration : baseApiProposalTechnical;
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `biddingSubmissionId.equals=${submissionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        for (const proposal of res.data) {
          const item = this.answers.find(ans => ans.id === proposal.biddingSubCriteriaLineId);
          item.answer = proposal.answer;
        }
      })
  }

  save() {
    this.$emit('processing', true);
    console.log('Saving proposal. data: %O', this.answers);
    const data: any[] = this.answers.map(answer => {
      const {
        id,
        name,
        score,
        adOrganizationName,
        biddingSubCriteriaId,
        biddingSubCriteriaName,
        ...proposal
      } = answer;

      proposal.biddingSubmissionId = this.submissionId;
      proposal.biddingSubCriteriaLineId = answer.id;

      return proposal;
    });

    // Validate each record.
    const validator = new Schema(this.validationSchema);
    let valid = true;

    for (const proposal of data) {

      validator.validate(proposal, (errors: any) => {
        if (errors) {
          valid = false;
          this.$message.error(errors[0].message);
        }
      });

      if (!valid) {
        break;
      }
    }
    
    if (!valid) {
      return;
    }

    const baseApiUrl = this.isAdministration ? baseApiProposalAdministration : baseApiProposalTechnical
    const evaluationName = proposalNameMap.get(this.data.evaluation);
    this.commonService(baseApiUrl + '/requirements')
      .create(data)
      .then(res => this.$message.success(`${evaluationName} proposal has been saved successfully`))
      .catch(err => {
        console.error('Failed to save the proposal. %O', err);
        this.$message.error(`Failed saving the ${evaluationName} proposal`);
      })
      .finally(() => this.$emit('processing', false));
  }
}