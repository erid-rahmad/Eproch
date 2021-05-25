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
    biddingNo:'BN-00008',
    biddingName:'Pengadaan Mobil Operasional',
    biddingTypeName:'Tender-Goods',
    eventTypeName:'Ingram',
    total:'6.4',
    vendorName:'supplier1'
  }

  private biddingSubmission=[

    {
      "1": "Administrasi",
      "date": "2021-05-25T15:30:33.833453Z",
      "3": "Pass",
      "4": "6.5",
    },    {
      "1": "Technical ",
      "date": "2021-05-25T15:30:33.833453Z",
      "3": "Pass",
      "4": "6.2",
    },    {
      "1": "Price ",
      "date": "2021-05-25T15:30:33.833453Z",
      "3": "Pass",
      "4": "7",
    },

  ]

  created(){
  }

  close() {
    this.$emit("close");
  }

}
