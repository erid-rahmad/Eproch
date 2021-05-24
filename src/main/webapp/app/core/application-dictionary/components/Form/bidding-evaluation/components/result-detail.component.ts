import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

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

@Component
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ProductCatalogProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;


  private evaluation={
    biddingNo:'BN 00001',
    biddingName:'Pengadaan Kendaraan Operasional',
    biddingTypeName:'Tender Goods',
    eventTypeName:'Ingram',
    total:'4.3',
    vendorName:'Ingram'
  }

  private biddingSubmission=[

    {
      "1": "Administrasi Proposal",
      "date": "2021-05-19T15:30:33.833453Z",
      "3": "Pass",
      "4": "",
    },    {
      "1": "Technical Proposal",
      "date": "2021-05-19T15:30:33.833453Z",
      "3": "Pass",
      "4": "5",
    },    {
      "1": "Price Proposal",
      "date": "2021-05-19T15:30:33.833453Z",
      "3": "Pass",
      "4": "4",
    },

  ]

  created(){
  }

  close() {
    this.$emit("close");
  }

}
