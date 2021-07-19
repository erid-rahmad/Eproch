import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import {
  Component, Inject
} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

import biddingEvaluation from './bidding-evaluation.vue';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import EvaluationResult from './bidding-evaliuation-result.vue';
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";

const baseApiBiddingSchedule = 'api/m-bidding-schedules'

@Component({
  components: {
    biddingEvaluation,
    EvaluationResult

  }
})
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ContextVariableAccessor) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private bidding: any= [];
  private pickRow:any={};
  private biddingStatuses:any=[];
  loading: boolean = false;
  index=0;

  created(){
    this.moreInformationData();
    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        this.biddingStatuses = res.map(item => ({ key: item.value, value: item.name }));
      });
  }
  formatBiddingStatus(value: string) {
    return this.biddingStatuses.find(status => status.key === value)?.value;
  }

  private moreInformationData() {
    this.loading=true;
    this.dynamicWindowService("/api/m-bidding-eval-results/grid")
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(async res => {
        function compare(a, b) {
          // Use toUpperCase() to ignore character casing
          const bandA = a.id;
          const bandB = b.id;
          let comparison = 0;
          if (bandA < bandB) {
            comparison = 1;
          } else if (bandA > bandB) {
            comparison = -1;
          }
          return comparison;
        }
        await res.data.sort(compare);
        this.bidding = res.data
      })
      .finally(()=> this.loading=false);
  }
  evaluate(row){
    this.index=1;
    this.pickRow=row;
    console.log("log row",row)
  }
  result(row){
    this.index=2;
    this.pickRow=row;
  }

  close() {
    this.index = 0;
  }



}
