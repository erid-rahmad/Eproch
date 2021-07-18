import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import {Component, Inject, Mixins, Vue, Watch} from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import Schema from 'async-validator';
import {numeric} from "vuelidate/lib/validators";

const baseApiEvalMethodCriteria = 'api/c-evaluation-method-criteria';
const baseApiVendorScoringCriteria = 'api/m-vendor-scoring-criteria';
const baseApiMTechproposal = 'api/m-tech-proposal-evaluations';
const baseApiBiddingEvaluation = 'api/m-tech-proposal-evaluations';
const baseApiEvalResultLine = 'api/m-bidding-eval-result-lines';
const baseApiTechnicalAnswer = 'api/m-proposal-technicals';
const baseApiAdministrationAnswer = 'api/m-proposal-administrations';
const baseApiProposalTechnicalFile = 'api/m-proposal-administration-files';

const EvaluationFormDetailComponentProp = Vue.extend({
  props: {
    loading: Boolean,

    title:String,
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
export default class EvaluationFormDetailComponent extends Mixins(AccessLevelMixin, EvaluationFormDetailComponentProp) {

  options = [{
    value: 'Pass',
    label: 'Pass'
  }, {
    value: 'Fail',
    label: 'Fail'
  }];
  optionsEvaluation = [{
    value: 100,
    label: 'Pass'
  }, {
    value: 0,
    label: 'Fail'
  }];

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;
  private evaluationMethodCriteria: any = [];
  private loadingAll:boolean=false;
  private evaluationType:Boolean;
  private questions: Map<number, any> = new Map();
  private evaluationResultLine:any= {};
  private attachmentHandler: Map<number, string> = new Map();
  private counting: Map<number, number> = new Map();
  private biddingSubCriteria: Map<number, string> = new Map();
  statusReadOnly:boolean=false;
  readOnly: Boolean=false;


  private validationSchema = {
    requirement: {
      type: 'string',
      required: true,
      message: 'Requirement is required'
    }
  };

  @Watch('SelectVendorScoringLine')
  loaddata() {
    this.evaluationResultLine.score=0;
    this.readOnly=false;
    this.getEvaluationtype();
    this.retrieveEvaluationMethodCriteria(this.evaluationFormProp.SelectVendorScoringLine.evaluationMethodLineId, this.evaluationFormProp.SelectVendorScoringLine.id);
    this.retrieveEvalResultLine( this.evaluationFormProp.evaluationMethodLineId, this.evaluationFormProp.biddingEvalResultId)
  }

  created() {
    this.getEvaluationtype();
    this.retrieveEvaluationMethodCriteria(this.evaluationFormProp.SelectVendorScoringLine.evaluationMethodLineId, this.evaluationFormProp.SelectVendorScoringLine.id);
    this.retrieveEvalResultLine( this.evaluationFormProp.evaluationMethodLineId, this.evaluationFormProp.biddingEvalResultId)
  }

  retrieveEvalResultLine(evaluationMethodLineId:number,biddingEvalResultId:number){
    this.commonService(baseApiEvalResultLine)
      .retrieve({
        criteriaQuery: [
          `evaluationMethodLineId.equals=${evaluationMethodLineId}`,
          `biddingEvalResultId.equals=${biddingEvalResultId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(async res => {
        const data = res.data as any[];
        let result:any={};
        if (data.length) {
          result = {...result, ...data[0]};
          this.evaluationResultLine = result;
            if (result.documentStatus === 'SMT') {
              this.readOnly = true;
              console.log("status lock")
            }
          }


      });
  }

  getEvaluationtype(){
    if (this.evaluationFormProp.SelectVendorScoringLine.evaluationMethodLineEvaluationType==="S"){
      this.evaluationType=true;
    }else { this.evaluationType=false;}
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
    const data={
      biddingEvalResultId:this.evaluationFormProp.biddingEvalResultId,
      evaluationMethodLineId:this.evaluationFormProp.evaluationMethodLineId,
      adOrganizationId:1,
      active:true,
      id:this.evaluationResultLine.id,
      score:this.evaluationResultLine.score,
      status:this.evaluationResultLine.status,
      documentStatus:this.evaluationResultLine.documentStatus,
    }

    this.commonService(baseApiEvalResultLine)
      .create(data)
      .then(res => {
        this.evaluationFormProp.SelectVendorScoringLine = res.data;
        console.log("save line")

        // this.$message.success('create ResultLine ');
      })
      .catch(_err => this.$message.error('fail create record'));

  }

  saveSubmit(){
   this.evaluationResultLine.documentStatus='SMT';
   this.save();
   this.readOnly=true;
  }

  private retrieveEvaluationMethodCriteria(evaluationMethodLineId: number, vendorScoringLineId: number) {
    this.loadingAll=true;
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
        let x =0;
        this.evaluationMethodCriteria = res.data.map((evalMethodCriteria: any) => {
          x=x+evalMethodCriteria.weight;
          let y =0;
          let p=0;
          evalMethodCriteria.evalMethodSubCriteriaList.forEach((evalMethodSubCriteria: any) => {
            y=y+evalMethodSubCriteria.weight;
            evalMethodSubCriteria.biddingSubCriteriaDTO.forEach((biddingSubCriteria: any) => {
              biddingSubCriteria.attachmentId=null;
              biddingSubCriteria.attachmentName=null;
              biddingSubCriteria.attachmentUrl=null;
              this.attachmentHandler.set(biddingSubCriteria.id,biddingSubCriteria);
              biddingSubCriteria.criteriaLineDTO.forEach((subCriteriaLine: any) => {
                subCriteriaLine.SubCriteriaWeight=evalMethodSubCriteria.weight;
                subCriteriaLine.CriteriaWeight=evalMethodCriteria.weight;
                this.questions.set(subCriteriaLine.id, subCriteriaLine);
                p++;
              });
              this.counting.set(biddingSubCriteria.id,p);
              p=0;
            });
          });
          if(y!==100 && y!==0){
            this.$message.error(`Average MethodSubCriteria = ${y}`);
          }
          return evalMethodCriteria;
        })
        if(x!==100 && x!==0){
          this.$message.error(`Average MethodCriteria = ${x}`);
        }
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
   retrieveVendorScoringCriteria(vendorScoringLineId: number) {
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
          try {
            let question = this.questions.get(criteria.biddingSubCriteriaLineId);
            question.requirement = criteria.requirement;
            question.vendorScoringLineId = criteria.vendorScoringLineId;
          }catch (e) {}
        })
        if(this.title==="T"){
          this.retrieveAnswerTechnical(this.evaluationFormProp.biddingSubmission.id);
        }
        else if (this.title==="A"){
          this.retrieveAnswerAdmin(this.evaluationFormProp.biddingSubmission.id);
        }
      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
      });
  }

   retrieveAnswerTechnical(SubmissionId: number) {
    this.commonService(baseApiTechnicalAnswer)
      .retrieve({
        criteriaQuery: [
          // 'active.equals=true',
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
        this.retrieveAttachment(this.evaluationFormProp.biddingSubmission.id);
      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
      });
  }

   retrieveAnswerAdmin(SubmissionId: number) {
    this.commonService(baseApiAdministrationAnswer)
      .retrieve({
        criteriaQuery: [
          // 'active.equals=true',
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
          }catch (e) {}
        });
        this.retrieveAttachment(this.evaluationFormProp.biddingSubmission.id);
      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
      });
  }

  retrieveAttachment(submissionId){
    this.commonService(baseApiProposalTechnicalFile)
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
          try {
            const item = this.attachmentHandler.get(proposal.biddingSubCriteriaId);
            // @ts-ignore
            item.attachmentId = proposal.cAttachmentId;
            // @ts-ignore
            item.attachmentName = proposal.cattachmentName;
            // @ts-ignore
            item.attachmentUrl = proposal.attachmentUrl;
          }catch (e) {}
        };
        // this.loadingAll=false;
        this.retrieveEvaluation(this.evaluationFormProp.SelectVendorScoringLine.biddingId);
      })
      .catch(err => {
        console.error('Failed',err);
        this.$message.error(`Failed reload attachment `);
      })

  }

   retrieveEvaluation(biddingId: number) {
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

            let question = this.questions.get(criteria.biddingSubCriteriaLineId);
              question.evaluation = criteria.evaluation;
              question.notes = criteria.notes;
              question.evaluationid = criteria.id;

          } catch (e) {}
        });
        this.loadingAll=false

      })
      .catch(err => {
        const message = 'Failed to get evaluation';
        console.log(message, err);
        this.$message.error(message);
      });
  }

  average(){
    let x=0;
    let s=0;
    this.questions.forEach(question_=>{
      let z =this.counting.get(question_.biddingSubCriteriaId);
      let y=question_.evaluation/z*question_.SubCriteriaWeight/100*question_.CriteriaWeight/100;
      x=x+y;
      s++;
    })
    this.evaluationResultLine.score =x;
    if(this.evaluationFormProp.SelectVendorScoringLine.evaluationMethodLinePassingGrade>0){
      this.statusReadOnly=true;
      if (x>this.evaluationFormProp.SelectVendorScoringLine.evaluationMethodLinePassingGrade){
        this.evaluationResultLine.status="Pass";
      }else { this.evaluationResultLine.status="Fail";}
    }
  }



  handlePreview(biddingSubCriteria){
    window.open(biddingSubCriteria.attachmentUrl, '_blank');

  }
}
