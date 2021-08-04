import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import EvaluationForm from "./evaluation-form.vue";
import ScheduleEventMixin from "@/core/application-dictionary/mixins/ScheduleEventMixin";

const baseApiEvalResults ='api/m-bidding-eval-results';
const baseApiBiddingSubmission='/api/m-prequalification-submissions';

const BiddingEvaluationProp = Vue.extend({
  props: {
    pickRow: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
})

@Component({
  components: {
    EvaluationForm,
  }
})
export default class PreqEvaluationComponent extends mixins(Vue2Filters.mixin,ScheduleEventMixin, AlertMixin, AccessLevelMixin, BiddingEvaluationProp) {

  @Inject('dynamicWindowService')
  public commonService: (baseApiUrl: string) => DynamicWindowService;

  private biddingSubmission: any = [];
  private evaluationResult:any={};
  public loading:boolean=false;
  index=0;
  private data:any={};

  created() {
    this.getbiddingSubmission(this.pickRow.id);
    this.data.fromGrid=this.pickRow;
  }

  onMainFormUpdateInEvaluation(mainForm: any){

    this.getbiddingSubmission(mainForm.prequalificationId);
    this.data.formType=mainForm.formType;

  }



  // evaluate(row){
  //   this.data.pickrow=row;
  //   this.data.formType=this.pickRow;
  //   this.data.biddingSubmission=this.biddingSubmission;
  //   this.index=1;
  // }

  private getbiddingSubmission(prequalificationId) {
    this.loading=true;
    this.commonService(baseApiBiddingSubmission)
      .retrieve({
        criteriaQuery: this.updateCriteria([`prequalificationId.equals=${prequalificationId}`]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        console.log(res.data);
        this.biddingSubmission = res.data;
      })
      .finally(()=>this.loading=false);
  }

  /*
  createEvaluateTable(biddingSubmissionId){
    const data={
      biddingSubmissionId:biddingSubmissionId,
      adOrganizationId:1,
      active:true,
    }
    this.commonService(baseApiEvalResults)
      .create(data)
      .then(res => {
        this.evaluationResult = res.data;
        // this.$message.success('create new record');
      })
      .catch(_err => this.$message.error('fail create record'));
  }
  */

  retrieveEvaluateTable(row){
    //Karena hasil evaluasi gabung dengan submission, tidak perlu ambil header evaluasi terlebih dahulu
    /*
    this.commonService(baseApiEvalResults)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingSubmissionId.equals=${row.id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        const data = res.data as any[];
        if (data.length) {
          this.evaluationResult = {...this.evaluationResult, ...data[0]};
        }
        else {
          //this.createEvaluateTable(row.id);
        }
      })
      .catch(_err => this.$message.error('Error retrieve Evaluation Table'))
      .finally(()=>{
        this.index=1;

        this.data.pickrow=row;
        this.data.biddingSubmission=this.biddingSubmission;
        this.data.evaluationResult=this.evaluationResult;
      })
    */

    this.index=1;

    this.data=row;
    //this.data.biddingSubmission=this.biddingSubmission;
    //this.data.evaluationResult=this.evaluationResult;
  }

  close() {
    this.$emit("close");
  }
  close_() {
    this.index=0;
  }
}
