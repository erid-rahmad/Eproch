import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import {Component, Inject, Mixins, Vue, Watch} from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import Schema from 'async-validator';

const baseApiEvalMethodCriteria = 'api/c-evaluation-method-criteria';
const baseApiVendorScoringCriteria = 'api/m-vendor-scoring-criteria';
const baseApiMTechproposal = 'api/m-tech-proposal-evaluations';
const baseApiBiddingEvaluation = 'api/m-tech-proposal-evaluations';
const baseApiEvalResultLine = 'api/m-bidding-eval-result-lines';
const baseApiTechnicalAnswer = 'api/m-proposal-technicals';
const baseApiAdministrationAnswer = 'api/m-proposal-administrations';

const PrequalificationFormProps = Vue.extend({
  props: {
    loading: Boolean,
    readOnly: Boolean,
    evaluationFormProp: {
      type: Object,
      default: () => {
        return {};
      }
    },
    SelectVendorScoringLine: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
});

@Component
export default class PrequalificationForm extends Mixins(AccessLevelMixin, PrequalificationFormProps) {

  options = [{
    value: 'Pass',
    label: 'Pass'
  }, {
    value: 'Fail',
    label: 'Fail'
  }];
  value: ''
  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;
  private evaluationMethodCriteria: any = [];
  private questions: Map<number, any> = new Map();
  private evaluationResultLine: any = {};

  private validationSchema = {
    requirement: {
      type: 'string',
      required: true,
      message: 'Requirement is required'
    }
  };

  @Watch('SelectVendorScoringLine')
  loaddata() {
    console.log("load data")
    this.retrieveEvaluationMethodCriteria(this.evaluationFormProp.SelectVendorScoringLine.evaluationMethodLineId,
      this.evaluationFormProp.SelectVendorScoringLine.id);
    this.evaluationResultLine = this.evaluationFormProp.evaluationResultLine;

  }

  created() {
    console.log("evaluationFormProp",this.evaluationFormProp)
    this.retrieveEvaluationMethodCriteria(this.evaluationFormProp.SelectVendorScoringLine.evaluationMethodLineId,
      this.evaluationFormProp.SelectVendorScoringLine.id);
    this.evaluationResultLine = this.evaluationFormProp.evaluationResultLine;

  }

  save() {
    const data: any[] = [];
    this.questions.forEach(question => {
      const {
        id,
        name,
        notes,
        evaluation,
        averageScore,
        passFail,
        score,
        adOrganizationName,
        biddingSubCriteriaId,
        biddingSubCriteriaName,
        uid,
        vendorId,
        ...requirement
      } = question;
      requirement.id = question.evaluationid;
      requirement.biddingSubCriteriaLineId = question.id;
      requirement.notes = question.notes;
      requirement.evaluation = question.evaluation;
      requirement.biddingId = this.evaluationFormProp.SelectVendorScoringLine.biddingId;
      requirement.vendorId = this.evaluationFormProp.vendorId;
      data.push(requirement);

    });
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

    this.commonService(`${baseApiMTechproposal}/evaluation`)
      .create(data)
      .then(_res => {
        this.$message.success(`Requirements has been saved successfully`);
        this.updateEvalResultLine();
      })
      .catch(err => {
        console.error('Failed to save the criteria. %O', err);
        this.$message.error(`Failed to save vendor scoring criteria`);
      })
      .finally(() => this.$emit('update:loading', false));
  }

  updateEvalResultLine() {
    this.commonService(baseApiEvalResultLine)
      .create(this.evaluationResultLine)
      .then(res => {
        this.evaluationFormProp.SelectVendorScoringLine = res.data;
        this.$message.success('create ResultLine ');
      })
      .catch(_err => this.$message.error('fail create record'));

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
                console.log("question",this.questions);
              });
            });
          });
          return evalMethodCriteria;
        })


      })
      .finally(() => {
        this.retrieveVendorScoringCriteria(vendorScoringLineId);



      });
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
          let question = this.questions.get(criteria.biddingSubCriteriaLineId);
          question.requirement = criteria.requirement;
          question.vendorScoringLineId = criteria.vendorScoringLineId;
        })
        // this.retrieveAnswerAdmin(this.evaluationFormProp.biddingSubmission.id);
        this.retrieveAnswerTechnical(this.evaluationFormProp.biddingSubmission.id);

      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
        this.retrieveEvaluation(this.SelectVendorScoringLine.biddingId);
      });
  }

  private retrieveAnswerTechnical(SubmissionId: number) {
    this.commonService(baseApiTechnicalAnswer)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `biddingSubmissionId.equals=${SubmissionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach((criteria: any) => {
          try {
            let question = this.questions.get(criteria.biddingSubCriteriaLineId);
            question.answer = criteria.answer;
          }catch (e) {
          }
        });
        this.retrieveEvaluation(this.evaluationFormProp.SelectVendorScoringLine.biddingId);
      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
      });
  }

  private retrieveAnswerAdmin(SubmissionId: number) {
    this.commonService(baseApiAdministrationAnswer)
      .retrieve({
        criteriaQuery: [
          // 'active.equals=true',
          // `biddingSubmissionId.equals=${SubmissionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach((criteria: any) => {
          try {
            let question = this.questions.get(criteria.biddingSubCriteriaLineId);
            question.answer = criteria.answer;
          }catch (e) {
          }
        });
      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
      });
  }

  private retrieveEvaluation(biddingId: number) {

    this.commonService(baseApiBiddingEvaluation)
      .retrieve({
        criteriaQuery: [
          `biddingId.equals=${biddingId}`,
          `vendorId.equals=${this.evaluationFormProp.vendorId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach((criteria: any) => {
          try {
            const question = this.questions.get(criteria.biddingSubCriteriaLineId);
            question.evaluation = criteria.evaluation;
            question.notes = criteria.notes;
            question.evaluationid = criteria.id;
          } catch (er) {
          }

        });
      })
      .catch(err => {
        const message = 'Failed to get evaluation';
        console.log(message, err);
        this.$message.error(message);
      });
  }
}
