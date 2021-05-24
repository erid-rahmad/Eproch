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
      "createdBy": "system",
      "createdDate": "2021-05-19T15:30:33.833453Z",
      "lastModifiedBy": null,
      "lastModifiedDate": null,
      "id": 3,
      "joined": null,
      "dateTrx": null,
      "documentNo": null,
      "documentAction": "e",
      "documentStatus": "S",
      "approved": true,
      "processed": true,
      "dateApprove": null,
      "dateReject": null,
      "rejectedReason": null,
      "dateSubmit": '2021-05-19T15:30:33.833453Z',
      "uid": null,
      "active": true,
      "biddingId": 3,
      "biddingName": "Pengadaan Office Equipment",
      "biddingNo": "BN-00005",
      "biddingTypeName": "Tender Goods",
      "biddingStatus": "Pass",
      "ceilingPrice": 13552500000.00,
      "currencyName": "IDR",
      "eventTypeName": "Satu Tahap",
      "vendorSelection": "DRC",
      "biddingScheduleId": 1960401,
      "biddingScheduleName": "Bidding Announcement",
      "biddingScheduleStatus": "Pass",
      "vendorId": 103401,
      "vendorName": "Sistech",
      "adOrganizationId": 2,
      "adOrganizationName": "All"
    },
    {
      "createdBy": "system",
      "createdDate": "2021-05-19T15:30:33.833453Z",
      "lastModifiedBy": null,
      "lastModifiedDate": null,
      "id": 4,
      "joined": null,
      "dateTrx": null,
      "documentNo": null,
      "documentAction": "e",
      "documentStatus": "S",
      "approved": true,
      "processed": true,
      "dateApprove": null,
      "dateReject": null,
      "rejectedReason": null,
      "dateSubmit": '2021-05-19T15:30:33.833453Z',
      "uid": null,
      "active": true,
      "biddingId": 4,
      "biddingName": "Pengadaan Office Equipment",
      "biddingNo": "BN-00005",
      "biddingTypeName": "Tender Goods",
      "biddingStatus": "Pass",
      "ceilingPrice": 13552500000.00,
      "currencyName": "IDR",
      "eventTypeName": "Satu Tahap",
      "vendorSelection": "DRC",
      "biddingScheduleId": 1960401,
      "biddingScheduleName": "Bidding Announcement",
      "biddingScheduleStatus": "Pass",
      "vendorId": 122505,
      "vendorName": "INGRAM MICRO INDONESIA",
      "adOrganizationId": 1,
      "adOrganizationName": "All"
    }
  ]

  created(){
  }

  close() {
    this.$emit("close");
  }

}
