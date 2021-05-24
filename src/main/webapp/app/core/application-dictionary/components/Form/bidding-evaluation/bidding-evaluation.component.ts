import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import EvaluationForm from "./components/bidding-evaliuation-form.vue";
import EvaluationResult from "./components/result-detail.vue"


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

  private biddingSubmission: any = {};
  index=0;
  private data:any={};


  created() {
    console.log(this.pickRow)
    this.getbiddingSubmission();

  }

  close() {
    this.$emit("close");
  }

  evaluate(row){
    this.index=1;
    this.data.pickrow=row;
    this.data.biddingSubmission=this.biddingSubmission;
  }

  result(row){
    this.index=2;
  }

  private getbiddingSubmission() {
    this.commonService('/api/m-bidding-submissions')
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
}
