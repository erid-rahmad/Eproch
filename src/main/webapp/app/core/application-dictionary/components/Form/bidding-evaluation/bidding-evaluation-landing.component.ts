import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import {
  Component, Inject
} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import biddingEvaluation from "@/core/application-dictionary/components/Form/bidding-evaluation/bidding-evaluation.vue";


@Component({
  components: {
    biddingEvaluation,

  }
})
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ContextVariableAccessor) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;



}
