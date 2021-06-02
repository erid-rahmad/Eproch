import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import EvaluationForm from "./components/bidding-evaliuation-form.vue";
import EvaluationResult from "./components/result-detail.vue"

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
export default class BiddingEvaluation extends mixins(Vue2Filters.mixin, AlertMixin, AccessLevelMixin, BiddingEvaluationProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private biddingSubmission: any = [];
  private evaluationResult:any={};
  index=0;
  private data:any={};

  created() {
    this.getbiddingSubmission();
  }



  evaluate(row){
    this.index=1;
    this.data.pickrow=row;
    this.data.biddingSubmission=this.biddingSubmission;
  }

  private getbiddingSubmission() {
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
          if (result.biddingId === this.pickRow.id) {
            biddingEvent.push(result);
          }
        });
        this.biddingSubmission = biddingEvent;
      });
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
