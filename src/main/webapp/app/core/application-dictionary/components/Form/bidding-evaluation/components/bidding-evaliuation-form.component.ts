import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject, Watch} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import EvaluationFormDetail from './evaluation-form-detail.vue';
import EvaluationTeamDetailPrice from './evaluation-form-detail-price.vue'


const baseApiVendorScoring = 'api/m-vendor-scorings';
const baseApiEvaluationMethodLine = 'api/c-evaluation-method-lines';
const baseApiVendorScoringLine ='api/m-vendor-scoring-lines';
const baseApiEvalResultLine='api/m-bidding-eval-result-lines';
const baseApiEvalResults ='api/m-bidding-eval-results';


const ProductCatalogProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
})

@Component({
  components: {
    EvaluationFormDetail,
    EvaluationTeamDetailPrice,

  }
})
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ProductCatalogProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  FormMenu=0;

  private biddingSubmission:any={};
  private evaluation:any={};
  private evaluationMethodLines:any={};
  private VendorScoringLine:any={};
  private SelectVendorScoringLine:any={};
  private vendorId:number;
  private evaluationResult:any={};
  private evaluationFormProp:any={};
  mainForm: any = {};
  private readOnly:boolean=false;
  button =0;

  created(){
    this.evaluationResult=this.data.evaluationResult;
    this.evaluationFormProp.biddingSubmission=this.data.pickrow;
    this.evaluation=this.data.pickrow;
    this.handleButton();
    this.retrieveVendorScoring(this.evaluation.biddingId);

  }

  setEvaluation(data :any){
    this.SelectVendorScoringLine=data;
    this.vendorId=this.evaluation.vendorId;
    this.evaluationFormProp.vendorId=this.evaluation.vendorId;
    this.evaluationFormProp.SelectVendorScoringLine=this.SelectVendorScoringLine;
    this.retrieveEvalResultLine(this.SelectVendorScoringLine.evaluationMethodLineId,this.evaluationResult.id);
    this.retrieveAllEvalResultLine(this.evaluationResult.id);

  }

  changeCode(code :String){
    if(code==="T"){
      return "Technic"
    }
    if(code==="A"){
      return "Administrasi"
    }
    if(code==="P"){
      return "Price"
    }
  }

  handleButton(){
    if (this.evaluationResult.evaluationStatus==="SMT"){
      this.readOnly=true;
      this.button=1;
    }
    if (this.evaluationResult.evaluationStatus==="RJC" ){
      this.readOnly=true;
      this.button=2;
    }
    if (this.evaluationResult.evaluationStatus==="APP"){
      this.readOnly=true;
      this.button=3;
    }
  }

  retrieveAllEvalResultLine(biddingEvalResultId:number){
    this.commonService(baseApiEvalResultLine)
      .retrieve({
        criteriaQuery: [
          `biddingEvalResultId.equals=${biddingEvalResultId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        let AllEvalResultLine =res.data;
        let average=0,loop=0,status;
        AllEvalResultLine.forEach(data=>{
            average=average+data.score;
            ++loop;
          if(data.status==="Fail"){
            status="Fail";
          }{status="Pass"}
        })
        average=average/loop;
        this.evaluationResult.status=status;
        this.evaluationResult.score=average;
        this.updateEvalResult();
        });
  }

  updateEvalResult(){
    this.commonService(baseApiEvalResults)
      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
      })
      .catch(_err => this.$message.error('fail create record'));
  }

  submitEvaluation(){
    this.evaluationResult.evaluationStatus="SMT";
    this.commonService(baseApiEvalResults)
      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
      })
      .catch(_err => this.$message.error('fail create record'))
      .finally(()=>{ this.button=1});
  }

  approveEvaluation(){
    this.evaluationResult.evaluationStatus="APP";
    this.commonService(baseApiEvalResults)
      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
      })
      .catch(_err => this.$message.error('fail create record'))
      .finally(()=>{this.button=3});
  }

  rejectEvaluation(){
    this.evaluationResult.evaluationStatus="RJC";
    this.commonService(baseApiEvalResults)
      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
      })
      .catch(_err => this.$message.error('fail create record'))
      .finally(()=>{this.button=2});
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
      .then(res => {
        const data = res.data as any[];
        let result:any={};
        if (data.length) {
          result = {...result, ...data[0]};
          this.evaluationFormProp.evaluationResultLine=result;
          // console.log("result",result)
          // this.$message.success('open ResultLine ');
        }
        else {
          this.createTableEvalResultLine();
        }

        if (this.SelectVendorScoringLine.evaluationMethodLineEvaluation ==="P"){
          this.FormMenu=1;
        }
        else {
          this.FormMenu=2;
        }

      });
  }

  createTableEvalResultLine(){
    const data={
      biddingEvalResultId:this.evaluationResult.id,
      evaluationMethodLineId:this.SelectVendorScoringLine.evaluationMethodLineId,
      adOrganizationId:1,
      active:true,
    }
    this.commonService(baseApiEvalResultLine)
      .create(data)
      .then(res => {
        this.evaluationFormProp.evaluationResultLine = res;
        // this.$message.success('create ResultLine ');
      })
      .catch(_err => this.$message.error('fail create record'));
  }

  private retrieveVendorScoring(biddingId: number) {
    this.commonService(baseApiVendorScoring)
      .retrieve({
        criteriaQuery: [
          `biddingId.equals=${biddingId}`
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.mainForm = res.data[0];
          const evaluationMethodId = this.mainForm.evaluationMethodId;
          const vendorScoringId = this.mainForm.id;
          this.$set(this.mainForm, 'evaluationMethodId', evaluationMethodId);
          this.retrieveEvaluationMethodLines(evaluationMethodId);
          this.retrieveVendorScoringLine(vendorScoringId);
        }
      });
  }

  private retrieveEvaluationMethodLines(evaluationMethodId: number) {
    this.commonService(baseApiEvaluationMethodLine)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `evaluationMethodId.equals=${evaluationMethodId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationMethodLines = res.data;
      });
  }

  private retrieveVendorScoringLine(vendorScoringId: number) {
    this.commonService(baseApiVendorScoringLine)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `vendorScoringId.equals=${vendorScoringId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.VendorScoringLine=res.data;

      })
      .catch(err => {
        const message = 'Failed to get vendor scoring requirements';
        console.log(message, err);
        this.$message.error(message);
      });
  }
  close() {
    this.$emit("close");
  }


}
