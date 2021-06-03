import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import ResultDetail from './components/result-detail.vue';

const baseApiEvalResults ='api/m-bidding-eval-results';

const ProductCatalogProp = Vue.extend({
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
  components:{
    ResultDetail,
  }


})
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ProductCatalogProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  index=true;
  private evaluationResult:any=[];
  private evaluationResultProp:any={};
  private loading:boolean=false;

  created(){
    console.log(this.pickRow);
    this.retriveEvaluationResult(this.pickRow.id);
  }

  retriveEvaluationResult(biddingId){
    this.loading=true;
    this.commonService(baseApiEvalResults)
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
          this.evaluationResult=res.data;
      })
      .finally(()=>this.loading=false);
  }

  detailScore(row){
    this.index=false;
    this.evaluationResultProp=row;
  }

  close() {
    this.$emit("close");
  }
  close_() {
    this.index=true;
  }
}
