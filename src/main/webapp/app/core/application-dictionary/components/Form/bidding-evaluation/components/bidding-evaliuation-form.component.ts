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
import {ifError} from "assert";
import buildCriteriaQueryString from "@/shared/filter/filters";
import {AccountStoreModule as accountStore} from "@/shared/config/store/account-store";
import {log} from "util";


const baseApiVendorScoring = 'api/m-vendor-scorings';
const baseApiEvaluationMethodLine = 'api/c-evaluation-method-lines';
const baseApiVendorScoringLine ='api/m-vendor-scoring-lines';
const baseApiEvalResultLine='api/m-bidding-eval-result-lines';
const baseApiEvalResults ='api/m-bidding-eval-results';
const baseApiNegotiation ='api/m-bidding-negotiations';
const baseApiBiddingSchedule='api/m-bidding-schedules';


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
  private baseQuery: string = '';
  dialogSubmitEvaluation:boolean=false;
  dialogApproveEvaluation:boolean=false;
  dialogRejectEvaluation:boolean=false;
  title='';


  mainForm: any = {};
  private readOnly:boolean=false;
  private readOnlyApp:boolean=false;
  button =0;

  created(){
    console.log("read only",this.readOnly)
    this.evaluationResult=this.data.evaluationResult;
    this.evaluationFormProp.biddingSubmission=this.data.pickrow;
    this.evaluationFormProp.scheduleId=this.data.scheduleId;
    this.evaluation=this.data.pickrow;
    this.handleButton();
    this.retrieveVendorScoring(this.evaluation.biddingId);

  }

  async setEvaluation(data: any) {
    this.title = data.evaluation;
    this.SelectVendorScoringLine = data;
    this.vendorId = this.evaluation.vendorId;
    this.evaluationFormProp.vendorId = this.evaluation.vendorId;
    this.evaluationFormProp.SelectVendorScoringLine = this.SelectVendorScoringLine;
    this.evaluationFormProp.evaluationMethodLineId = this.SelectVendorScoringLine.evaluationMethodLineId;
    this.evaluationFormProp.biddingEvalResultId = this.evaluationResult.id;
    if (this.SelectVendorScoringLine.evaluationMethodLineEvaluation ==="P"){
              this.FormMenu=1;
            }
            else {
              this.FormMenu=2;
            }
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
        let average=0,loop=0,status,evaluationStatus;
        AllEvalResultLine.forEach(data=>{
            average=average+data.score*data.evaluationMethodLineWeight/100;
            ++loop;
          if(data.status==="Fail"){
            status="Fail";
          }{status="Pass"}
          if(data.documentStatus===null){
            evaluationStatus="DRF";
          }{evaluationStatus="SMT"}
        })

        this.evaluationResult.status=status;
        this.evaluationResult.score=average;
        this.evaluationResult.evaluationStatus=evaluationStatus;
        this.updateEvalResult();
        console.log("bidding evaluation")
        });
  }

  updateEvalResult(){
    console.log("this evalresult",this.evaluationResult)
    this.commonService(baseApiEvalResults)

      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
      })
      .catch(_err => {

      });
  }

   submitEvaluation() {
     (<any>this.$refs.evaluationFormDetail).saveSubmit();
     this.retrieveAllEvalResultLine(this.evaluationResult.id);

    this.dialogSubmitEvaluation = false;
  }

  async save() {
    await (<any>this.$refs.evaluationFormDetail).save();
    await this.retrieveAllEvalResultLine(this.evaluationResult.id);
  }

  handler(params) {
    this.readOnly=params;
  }

  approve(x){
    this.readOnlyApp=x;
  }

   approveEvaluation() {
     (<any>this.$refs.evaluationFormDetail).approveEvaluation();
     this.retrieveAllEvalResultLine(this.evaluationResult.id);
    this.dialogSubmitEvaluation = false;

  }

   rejectEvaluation() {
     (<any>this.$refs.evaluationFormDetail).rejectEvaluation();
     this.retrieveAllEvalResultLine(this.evaluationResult.id);
    this.dialogSubmitEvaluation = false;

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
    let filterQuery = [];
    filterQuery.push( 'active.equals=true');
    filterQuery.push( `vendorScoringId.equals=${vendorScoringId}`);
    if (this.data.formType) {
      filterQuery.push( `formType.equals=${this.data.formType}`);
    }
    if (this.data.fromGrid.formType=='S2'){
      filterQuery.push( `formType.equals=S2`);
    }
    if (this.data.fromGrid.formType=='S3'){
      filterQuery.push( `formType.equals=S3`);
    }
    this.baseQuery = buildCriteriaQueryString(filterQuery);

    this.commonService(baseApiVendorScoringLine)
      .retrieve({
        criteriaQuery: this.baseQuery,
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
