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
export default class EvaluationResult extends mixins(Vue2Filters.mixin,ScheduleEventMixin, AlertMixin, AccessLevelMixin, BiddingEvaluationProp) {

  @Inject('dynamicWindowService')
  public commonService: (baseApiUrl: string) => DynamicWindowService;

  private biddingSubmission: any = [];
  private evaluationResult:any={};
  public loading:boolean=false;
  public summary:string="";
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

        let count = res.data.length;
        let passc = res.data.filter((elem)=>elem.passFail=='pass').length;
        let failc = res.data.filter((elem)=>elem.passFail=='fail').length;
        
        console.log(count);
        console.log(passc);
        console.log(failc);

        this.summary = 
          `From ${count} Vendor:\n`+
          (passc?`- ${passc} Vendor lolos Prekualifikasi\n`:'')+
          (failc?`- ${failc} Vendor tidak lolos\n`:'')
      })
      .finally(()=>this.loading=false);
  }

  retrieveEvaluateTable(row){
    this.index=1;
    this.data=row;
  }

  close() {
    this.$emit("close");
  }
  close_() {
    this.index=0;
  }

  formatEvalStatus(value: string) {
    switch(value){
      case 'DRF': return 'Draft';
      case 'APP': return 'Approved';
      case 'SMT': return 'Submitted';
      case 'RJC': return 'Rejected';
      default: return value;
    }
  }
}
