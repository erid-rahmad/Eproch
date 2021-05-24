import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import {
  Component
} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

import ProductInformation from './bidding-evaluation.vue';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import EvaluationResult from './bidding-evaliuation-result.vue';

@Component({
  components: {
    ProductInformation,
    EvaluationResult

  }
})
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ContextVariableAccessor) {

  private bidding: any= { };
  private pickRow:any={};

  index=0;

  created(){
    this.moreInformationData()
  }

  private moreInformationData() {
    this.dynamicWindowService("/api/m-biddings")
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['documentNo']
        }
      })
      .then(res => {
        this.bidding = res.data;
      });
  }

  evaluate(row){
    this.index=1;
    this.pickRow=row;
  }
  result(row){
    this.index=2;
    this.pickRow=row;
  }

  close() {
    this.index = 0;
  }



}
