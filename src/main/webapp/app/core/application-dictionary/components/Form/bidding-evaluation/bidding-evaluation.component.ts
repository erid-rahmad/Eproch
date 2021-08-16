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
const baseApiUrl = 'api/m-biddings';

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
    this.data.fromGrid=this.pickRow;
  }

  onMainFormUpdateInEvaluation(mainForm: any,scheduleId){
    this.data.scheduleId=scheduleId;
    console.log("this clue",scheduleId)
    this.getbiddingSubmission(mainForm.biddingId);
    this.data.formType=mainForm.formType;

    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `id.equals=${mainForm.biddingId}`
        ]),
      })
      .then(res => {
        let value=res.data[0]
        this.pickRow.documentNo=value.documentNo
        this.pickRow.name=value.name
        this.pickRow.biddingTypeName=value.biddingTypeName
        this.pickRow.eventTypeName=value.eventTypeName
      })
  }

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
