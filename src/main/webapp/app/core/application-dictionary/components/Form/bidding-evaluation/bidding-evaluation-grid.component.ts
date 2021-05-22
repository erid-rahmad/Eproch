import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import {
  Component
} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import CatalogGrid from './components/catalog-grid.vue';
import ProductInformation from './bidding-evaliuation.vue';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

@Component({
  components: {
    CatalogGrid,
    ProductInformation
  }
})
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ContextVariableAccessor) {

  private bidding: any= { };
  private pickRow:any={};

  index:boolean=true;

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
    this.index=false
    this.pickRow=row;
  }

  close() {
    this.index = true;
  }



}
