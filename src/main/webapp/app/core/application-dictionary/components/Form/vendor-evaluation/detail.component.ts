import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import AdInputList from '@/shared/components/AdInput/ad-input-list.vue';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import { ElForm } from 'element-ui/types/form';

const baseApiContract = 'api/m-contracts';
const baseApiVendorEvaluation = 'api/m-vendor-evaluations';
const baseApiVendorEvaluationLine = 'api/m-vendor-evaluation-lines';


const VendorEvaluationDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
    fromContract: {
      type: Boolean,
      default: ()=> {return false};
    },
    loading: Boolean
  }
});

@Component({
  components: {
    AdInputList,
    AdInputLookup
  }
})
export default class VendorEvaluationDetail extends Mixins(AccessLevelMixin, VendorEvaluationDetailProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  // Mockup Aggrement data.
  aggreements = new Map([
    ['13334', {
      documentNo: '13334',
      evaluationType: 'Vendor Otomotif',
      evaluationPeriod: 'Yearly',
      evaluationDate: new Date('2021-03-31')
    }]
  ]);

  marks= {
    0: '0 %',
    50: {
      style: {
        color: '#1989FA'
      },
      label: this.$createElement('strong', '50%')
    },
    100: '100 %',
  }

  evaluation: any = {};
  lines: any[] = [];
  validationSchema = {};
  ContractFilter:any={};
  private readOnly:boolean=false;

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  retriveContractFilter(vendorId){

    this.commonService(baseApiContract)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `vendorId.equals=${vendorId}`
        ])
      })
      .then( res => {
          this.ContractFilter= res.data;
        }
      );

  }

  async onContractIdChanged(id: number) {
    if (!id) {
        this.evaluation.evaluationTypeId = null;
      this.$set(this.evaluation, 'evaluationTypeName', null);
      this.$set(this.evaluation, 'contractEvaluationPeriod', null);
      this.lines = [];
    } else {
      const contract = await this.retrieveContract(id);
      if (contract) {
        this.evaluation.evaluationTypeId = contract.vendorEvaluationId;
        this.evaluation.contractNo=contract.documentNo;
        this.$set(this.evaluation, 'evaluationTypeName', contract.vendorEvaluationName);
        this.$set(this.evaluation, 'contractEvaluationPeriod', contract.evaluationPeriod);

        this.retrieveLines(contract.vendorEvaluationId);
      }
    }
  }

  onRateChanged(score: number) {
    console.log(score);
    const totalRates = this.lines
      .map((line: any): number => line.score || 0)
      .reduce((prev, next) => prev + next, 0);

    this.$set(this.evaluation, 'score', totalRates / this.lines.length);
  }

  created() {
    this.evaluation = {...this.data};


    if (this.evaluation.id){
      this.retrieveLinesSaved(this.evaluation.id);
      if ( this.evaluation.documentStatus==='APV'){
        this.readOnly=true;
      }
    } else if(this.fromContract) {
      this.retriveContractFilter(this.evaluation.vendorId);
      this.onContractIdChanged(this.evaluation.contractId);
    }
  }

  async retrieveContract(id: number) {
    return this.commonService(baseApiContract)
      .find(id)
      .then(res => res);

  }

  retrieveLines(evaluationId: string) {
    this.commonService('/api/c-vendor-evaluation-lines')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `vendorEvaluationId.equals=${evaluationId}`
        ])
      })
      .then( res => {
        const data:any=[];
          res.data.forEach(async line=>{
             let line_=
              {
                adOrganizationId: line.adOrganizationId,
                cQuestionCategoryId: line.cQuestionCategoryId,
                cQuestionCategoryName:  line.cQuestionCategoryName,
                cVendorEvaluationId: line.cVendorEvaluationId,
                cVendorEvaluationName: line.cVendorEvaluationName,
                cquestionCategoryId: line.cquestionCategoryId,
                cquestionCategoryName: line.cquestionCategoryName,
                cvendorEvaluationId: line.cvendorEvaluationId,
                cvendorEvaluationName: line.cvendorEvaluationName,
                evaluationLineId:line.id,
                procurementWeight: line.procurementWeight,
                question: line.question,
                userWeight: line.userWeight,
                weight: line.weight,
              };
            console.log("this line",line_)
             await data.push(line_);
          })
        this.lines= data;
        }
      );
  }

  retrieveLinesSaved(evaluationId) {
    this.commonService(baseApiVendorEvaluationLine)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `vendorEvaluationId.equals=${evaluationId}`
        ])
      })
      .then(res => {
        const data1:any=[];
        res.data.forEach(async line=>{
          let line__=
            {
              adOrganizationId: line.adOrganizationId,
              cVendorEvaluationId: line.cVendorEvaluationId,
              cVendorEvaluationName: line.cVendorEvaluationName,
              cQuestionCategoryId: line.cquestionCategoryId,
              cQuestionCategoryName: line.evaluationLineName,
              cvendorEvaluationId: line.cvendorEvaluationId,
              cvendorEvaluationName: line.cvendorEvaluationName,
              procurementWeight: line.procurementWeight,
              question: line.evaluationLineQuestion,
              userWeight: line.userWeight,
              weight: line.weight,
              evaluationLineId:line.evaluationLineId,
              id:line.id,
              score:line.score,
              remark:line.remark
            };
          await data1.push(line__);
        });
        this.lines=data1;
        }
      );
  }

  submit(){
    this.$emit('update:loading', true)
    this.readOnly=true;
    this.evaluation.documentStatus='APV';
    this.commonService(baseApiVendorEvaluation)
      .update(this.evaluation)
      .then(res => {
        this.$message.success('Vendor Evaluation has been approved');
      })
      .catch(err => {
        console.error('Failed to approved', err);
        this.$message.error('Failed to save Vendor Evaluation');
      })
      .finally(() => this.$emit('update:loading', false));
  }

  save() {
    (<ElForm>this.$refs.mainForm).validate(passed => {
      if (passed) {
        const newRecord = !this.evaluation.id;
        this.$emit('update:loading', true);
        this.commonService(baseApiVendorEvaluation)
          [newRecord ? 'create' : 'update'](this.evaluation)
          .then(res => {
            this.$message.success('Vendor Evaluation has been saved successfully');
             this.evaluation = {...this.evaluation, ...res};
            this.saveLine(this.evaluation.id);
          })
          .catch(err => {
            console.error('Failed to save vendor evaluation', err);
            this.$message.error('Failed to save Vendor Evaluation');
          })
          .finally(() => this.$emit('update:loading', false));
      }
    })
  }
  saveLine(id:number){
    this.$emit('update:loading', true);
    this.lines.forEach(line=>{
      line.vendorEvaluationId=id;
      this.commonService(baseApiVendorEvaluationLine)
        .create(line)
        .then(res => {
          this.retrieveLinesSaved(this.evaluation.id);
        })
        .catch(err => {
          console.error('Failed to save vendor evaluation line', err);
          this.$message.error('Failed to save Vendor Evaluation line');
        })
        .finally(() => this.$emit('update:loading', false));
    })
  }
}
