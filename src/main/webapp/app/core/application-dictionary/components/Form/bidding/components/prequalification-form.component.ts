import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import Schema from 'async-validator';

const baseApiEvalMethodCriteria = 'api/c-evaluation-method-criteria';
const baseApiVendorScoringCriteria = 'api/m-vendor-scoring-criteria';

const PrequalificationFormProps = Vue.extend({
  props: {
    loading: Boolean,
    readOnly: Boolean,

    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
});

@Component
export default class PrequalificationForm extends Mixins(AccessLevelMixin, PrequalificationFormProps) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private recordsLoaded: boolean = false;

  private evaluationMethodCriteria: any = [];
  private MVendorScoringNestedDTO: any = {};
  private vendorScoringCriteria: any = {};

  private questions: Map<number, any> = new Map();

  private validationSchema = {
    requirement: {
      type: 'string',
      required: true,
      message: 'Requirement is required'
    }
  };

  get observableIdentifiers() {
    const { id, evaluationMethodLineId } = this.data;
    return { id, evaluationMethodLineId };
  }

  onRequirementChanged() {
    this.$emit('updated');
  }

  created() {
    this.loadQuestions(this.data);
  }

  @Watch('observableIdentifiers')
  private loadQuestions({ id: vendorScoringLineId, evaluationMethodLineId }) {
    this.questions.clear();
    this.retrieveEvaluationMethodCriteria(evaluationMethodLineId, vendorScoringLineId);
  }

  private retrieveEvaluationMethodCriteria(evaluationMethodLineId: number, vendorScoringLineId: number) {
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
                this.questions.set(subCriteriaLine.id, subCriteriaLine);
              });
            });
          });

          return evalMethodCriteria;
        });

        this.retrieveVendorScoringCriteria(vendorScoringLineId);
      })
  }

  /**
   * First, we need to get the predefined questions from c_evaluation_method_criteria.
   * Then, map the existing requirements from m-vendor-scoring-criteria to each question.
   * @param vendorScoringLineId 
   * @param evaluationMethodLineId
   */
   private retrieveVendorScoringCriteria(vendorScoringLineId: number) {
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
          const question = this.questions.get(criteria.biddingSubCriteriaLineId);
          question.requirement = criteria.requirement;
          question.vendorScoringLineId = criteria.vendorScoringLineId;
        });
      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
      });
  }

  save() {
    const data: any[] = [];
    
    this.questions.forEach(question => {
      console.log('question: ', question);
      const {
        id,
        name,
        score,
        adOrganizationName,
        biddingSubCriteriaId,
        biddingSubCriteriaName,
        uid,
        ...requirement
      } = question;

      requirement.vendorScoringLineId = this.data.id;
      requirement.biddingSubCriteriaLineId = question.id;
      data.push(requirement);
    });

    console.log('vendor scoring criteria:', data);

    // Validate each record.
    const validator = new Schema(this.validationSchema);
    let valid = true;

    for (const criteria of data) {
      validator.validate(criteria, (errors: any) => {
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

    this.$emit('update:loading', true);
    this.commonService(`${baseApiVendorScoringCriteria}/requirements`)
      .create(data)
      .then(_res => this.$message.success(`Requirements has been saved successfully`))
      .catch(err => {
        console.error('Failed to save the criteria. %O', err);
        this.$message.error(`Failed to save vendor scoring criteria`);
      })
      .finally(() => this.$emit('update:loading', false));
  }
}
