import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

const baseApiEvalResultLine='api/m-bidding-eval-result-lines';

const ProductCatalogProp = Vue.extend({
  props: {
    evaluationResultProp: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
})

@Component
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ProductCatalogProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private evaluationResultLine:any={};
  private loading:boolean=false;

  created(){
    console.log("this eval resupt",this.evaluationResultProp)
    this.retrieveEvalResultLine(this.evaluationResultProp.id)
  }

  retrieveEvalResultLine(biddingEvalResultId:number){
    this.loading=true;
    this.commonService(baseApiEvalResultLine)
      .retrieve({
        criteriaQuery: [
          `biddingEvalResultId.equals=${biddingEvalResultId}`,
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.evaluationResultLine=res.data;
        console.log(this.evaluationResultLine);
      })
      .finally(()=>this.loading=false);
  }

  close() {
    this.$emit("close");
  }

}
