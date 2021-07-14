import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import EvaluationForm from "./components/bidding-evaliuation-form.vue";
import EvaluationResult from "./components/result-detail.vue"
import ScheduleEventMixin from "@/core/application-dictionary/mixins/ScheduleEventMixin";

const baseApiEvalResults ='api/m-bidding-eval-results';
const baseApiBiddingSubmission='/api/m-bidding-submissions';

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
export default class BiddingEvaluationts extends mixins(Vue2Filters.mixin,ScheduleEventMixin, AlertMixin, AccessLevelMixin, BiddingEvaluationProp) {

  @Inject('dynamicWindowService')
  public commonService: (baseApiUrl: string) => DynamicWindowService;

  private biddingSubmission: any = [];
  private evaluationResult:any={};
  public loading:boolean=false;
  index=0;
  private data:any={};

  created() {
    this.getbiddingSubmission(this.pickRow.id);
  }

  onMainFormUpdateInEvaluation(mainForm: any){
    console.log("this main form",mainForm);
    this.getbiddingSubmission(mainForm.biddingId);
    this.data.formType=mainForm.formType;

  }



  // evaluate(row){
  //   this.data.pickrow=row;
  //   this.data.formType=this.pickRow;
  //   this.data.biddingSubmission=this.biddingSubmission;
  //   this.index=1;
  // }

  private getbiddingSubmission(biddingId) {
    this.loading=true;
    this.commonService(baseApiBiddingSubmission)
      .retrieve({
        criteriaQuery: this.updateCriteria([]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        let biddingEvent: any = [];
        res.data.forEach(result => {
          if (result.biddingId === biddingId) {
            biddingEvent.push(result);
          }
        });
        this.biddingSubmission = biddingEvent;
      })
      .finally(()=>this.loading=false);
  }

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

  retrieveEvaluateTable(row){
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
          this.createEvaluateTable(row.id);
        }
      })
      .catch(_err => this.$message.error('Error retrieve Evaluation Table'))
      .finally(()=>{
        this.index=1;
        this.data.pickrow=row;

        this.data.biddingSubmission=this.biddingSubmission;
        this.data.evaluationResult=this.evaluationResult;
      })
  }

  close() {
    this.$emit("close");
  }
  close_() {
    this.index=0;
  }
}
