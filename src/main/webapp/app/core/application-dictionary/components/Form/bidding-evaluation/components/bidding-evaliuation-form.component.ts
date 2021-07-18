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
  dialogSubmitEvaluation:boolean=false;
  dialogApproveEvaluation:boolean=false;
  dialogRejectEvaluation:boolean=false;
  title='';


  mainForm: any = {};
  private readOnly:boolean=false;
  button =0;

  created(){
    this.evaluationResult=this.data.evaluationResult;
    this.evaluationFormProp.biddingSubmission=this.data.pickrow;
    this.evaluation=this.data.pickrow;
    // console.log("this data pickrow",this.data)
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

    // await this.retrieveEvalResultLine(this.SelectVendorScoringLine.evaluationMethodLineId, this.evaluationResult.id);


  }

  changeCode(code :String){
    if(code==="T"){
      // this.title="Technic";
      return "Technic"
    }
    if(code==="A"){
      // this.title="Administrasi";
      return "Administrasi"
    }
    if(code==="P"){
      // this.title="Price";
      return "Price"
    }
  }

  handleButton(){

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
    this.commonService(baseApiEvalResults)
      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
      })
      .catch(_err => {

      });
  }

  async submitEvaluation() {
    (<any>this.$refs.evaluationFormDetail).saveSubmit();
    this.dialogSubmitEvaluation = false;
  }

  async save() {
    await (<any>this.$refs.evaluationFormDetail).save();
    await this.retrieveAllEvalResultLine(this.evaluationResult.id);
  }

  handler(params) {
    console.log("masuk")
    this.readOnly=params
  }

  approveEvaluation(){
    this.evaluationResult.evaluationStatus="APP";
    this.commonService(baseApiEvalResults)
      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
        this.button=3;
        this.dialogApproveEvaluation=false;
        // this.createTableNegotiation();
      })
      .catch(_err => this.$message.error('fail create records evalResult'))
      .finally(()=>{});
  }



  rejectEvaluation(){
    this.evaluationResult.evaluationStatus="RJC";
    this.commonService(baseApiEvalResults)
      .create( this.evaluationResult)
      .then(res => {
        let evaluationResult = res.data;
        this.button=2;
        this.dialogRejectEvaluation=false;
      })
      .catch(_err => this.$message.error('fail create record'))
      .finally(()=>{});
  }



  // createTableEvalResultLine(){
  //   const data={
  //     biddingEvalResultId:this.evaluationResult.id,
  //     evaluationMethodLineId:this.SelectVendorScoringLine.evaluationMethodLineId,
  //     adOrganizationId:1,
  //     active:true,
  //   }
  //   this.commonService(baseApiEvalResultLine)
  //     .create(data)
  //     .then(res => {
  //       this.evaluationFormProp.evaluationResultLine = res;
  //
  //       if (this.SelectVendorScoringLine.evaluationMethodLineEvaluation ==="P"){
  //         this.FormMenu=1;
  //       }
  //       else {
  //         this.FormMenu=2;
  //       }
  //
  //       // this.$message.success('create ResultLine ');
  //     })
  //     .catch(_err => this.$message.error('fail create record'));
  // }

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
          `vendorScoringId.equals=${vendorScoringId}`,
          `formType.equals=${this.data.formType}`
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
