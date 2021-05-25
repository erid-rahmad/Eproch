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

const ProductCatalogProp = Vue.extend({
  props: {
    data: {
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

  private evaluation={
    biddingNo:'BN 00001',
    biddingName:'Pengadaan Kendaraan Operasional',
    biddingTypeName:'Tender Goods',
    eventTypeName:'Ingram',
    vendorName:'Ingram'
  }

  private biddingSubmission=[

    {
      "dateSubmit": '2021-05-25T15:30:33.833453Z',
      "vendorName": "supplier1",
      "biddingStatus": "Pass",
      "score": 6.4,
      "rank":3,
    },
    {
      "dateSubmit": '2021-05-25T15:30:33.833453Z',
      "vendorName": "supplier2",
      "biddingStatus": "Pass",
      "score": 6.4,
      "rank":2,
    },
    {
      "dateSubmit": '2021-05-25T15:30:33.833453Z',
      "vendorName": "supplier1",
      "biddingStatus": "Pass",
      "score": 6.7,
      "rank":1,
    },

  ]

  created(){
  }

  close() {
    this.$emit("close");
  }

}
