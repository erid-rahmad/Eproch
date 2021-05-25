import Vue from 'vue';
import {Inject, Mixins, Watch} from "vue-property-decorator";
import Component from "vue-class-component";

const DetailPriceProp = Vue.extend({
  props: {
    SelectVendorScoringLine: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
});

@Component
export default class DetailPrice extends Mixins( DetailPriceProp) {
  menuPrice:boolean=false;
  private evaluation={
    biddingNo:'BN-00008',
    biddingName:'Pengadaan Mobil Operasional',
    biddingTypeName:'Tender Goods',
    eventTypeName:'Satu Tahap',
    total:'4.3',
    evaluation:'7',
    price:'1.500.000.000.00',
    vendor:'Supplier1',
    pic:'Admin ',
    date:'2021-05-31 12:12',
    attachment:'penawaran.pdf',
    Currency:"IDR"
  }

  ///////////
  data() {
    return {
      options: [{
        value: 'Pass',
        label: 'Pass'
      }, {
        value: 'Fail',
        label: 'Fail'
      }],
      value: ''
    }
  }
    "biddingLineList"= [
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T03:30:16.503651Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-06-17T03:30:16.503651Z",
        "id": 1958866,
        "lineNo": null,
        "quantity": 10.00,
        "ceilingPrice": 150000000.00,
        "ceilingPrice1": 150000000.00,
        "totalCeilingPrice": 1500000000.00,
        "deliveryDate": "2021-06-17T03:30:16.503651Z",
        "remark": null,
        "uid": "debd79d6-2094-457e-906e-aa5aa5744e71",
        "active": true,
        "subItemId": null,
        "subItem": null,
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "costCenterId": 1956751,
        "costCenterName": "Marketing",
        "productId": 1950015,
        "productName": "BTN FIXEDASSET1",
        "uomId": 46751,
        "uomName": "Each",
        "doc": "checked",
        "delivery": "2021-06-17T03:30:16.503651Z",
        "totalpricesubmision":1500000000.00
      },

    ];
  ///////////
  created(){
  }
  close() {
    this.$emit("close");
  }
}
