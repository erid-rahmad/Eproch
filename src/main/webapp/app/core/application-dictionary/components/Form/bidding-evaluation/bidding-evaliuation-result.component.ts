import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import ResultDetail from './components/result-detail.vue';

const baseApiEvalResults = 'api/m-bidding-eval-results';

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
  components: {
    ResultDetail,
  }


})
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin, AccessLevelMixin, ProductCatalogProp) {

  index = true;
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  private evaluationResult: any = [];
  private evaluationResultProp: any = {};
  private loading: boolean = false;

  created() {
    this.retriveEvaluationResult(this.pickRow.id);
  }

  getStatus(status){
    if (status==="SMT"){
      return "Submitted"
    }
    else if (status==="APP"){
      return "Approved"
    }
    else if (status==="RJC"){
      return "Rejected"
    }
    else {return "Drafted"}
  }

  retriveEvaluationResult(biddingId) {
    this.loading = true;
    this.commonService(baseApiEvalResults)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        let data_: any = []
        res.data.forEach(data => {
          if (data.biddingId === biddingId) {
            data_.push(data);
          }
        })
        this.evaluationResult = data_;

      })
      .finally(() => this.loading = false);
  }

  detailScore(row) {
    this.index = false;
    this.evaluationResultProp = row;
  }

  close() {
    this.$emit("close");
  }

  close_() {
    this.index = true;
  }
}
