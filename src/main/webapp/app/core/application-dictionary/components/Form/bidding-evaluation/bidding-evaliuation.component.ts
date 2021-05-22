import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";


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

@Component
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ProductCatalogProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private biddingSubmission:any={};

  created(){
    console.log(this.pickRow)
    this.getbiddingSubmission();

  }

  private getbiddingSubmission() {
    this.commonService('/api/m-bidding-submissions')
      .retrieve({
        criteriaQuery: this.updateCriteria([

        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        let biddingEvent:any=[];
        res.data.forEach(result => {
          if (result.biddingId===this.pickRow.id){
            biddingEvent.push(result);
          }
        });
        this.biddingSubmission = biddingEvent;
      });
  }

  close() {
    this.$emit("close");
  }
}
