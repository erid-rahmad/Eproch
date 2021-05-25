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
    biddingNo:'BN 00001',
    biddingName:'Pengadaan Kendaraan Operasional',
    biddingTypeName:'Tender Goods',
    eventTypeName:'Goods',
    total:'4.3',
    evaluation:'7',
    price:'25.000.000',
    vendor:'Sistech',
    pic:'Admin Sistech',
    date:'2021 07 07 12:12',
    attachment:'penawaran.pdf'
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
        "lastModifiedDate": "2021-03-29T03:30:16.503651Z",
        "id": 1958866,
        "lineNo": null,
        "quantity": 50.00,
        "ceilingPrice": 238000000.00,
        "ceilingPrice1": 239000000.00,
        "totalCeilingPrice": 11900000000.00,
        "deliveryDate": "2021-03-29",
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
        "productName": "HONDA 2020",
        "uomId": 46751,
        "uomName": "Each",
        "doc": "checked",
        "delivery": "2021-03-29T03:30:16.503651Z",
        "totalpricesubmision":11950000000
      },
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T03:30:16.505336Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-03-29T03:30:16.505336Z",
        "id": 1958867,
        "lineNo": null,
        "quantity": 30.00,
        "ceilingPrice": 439000000.00,
        "ceilingPrice1": 440000000.00,
        "totalCeilingPrice": 13170000000.00,
        "deliveryDate": "2021-03-29",
        "remark": null,
        "uid": "3db4fe57-527f-4ced-90bb-742d2f93da14",
        "active": true,
        "subItemId": null,
        "subItem": null,
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "costCenterId": 1956751,
        "costCenterName": "Marketing",
        "productId": 1950014,
        "productName": "HONDA CIVIC 2017",
        "uomId": 46751,
        "uomName": "Each",
        "doc": "checked",
        "delivery": "2021-03-29T03:30:16.503651Z",
        "totalpricesubmision":13200000000
      },
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T03:30:16.506475Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-03-29T03:30:16.506475Z",
        "id": 1958868,
        "lineNo": null,
        "quantity": 100.00,
        "ceilingPrice": 45000000.00,
        "ceilingPrice1": 41600000.00,
        "totalCeilingPrice": 4500000000.00,
        "deliveryDate": "2021-03-29",
        "remark": null,
        "uid": "8e1148fa-b2d5-4323-90df-95ab8fc47a54",
        "active": true,
        "subItemId": null,
        "subItem": null,
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "costCenterId": 1956751,
        "costCenterName": "Marketing",
        "productId": 1950013,
        "productName": "HONDA 2015",
        "uomId": 46751,
        "uomName": "Each",
        "doc": "checked",
        "delivery": "2021-03-29T03:30:16.503651Z",
        "totalpricesubmision":4160000000
      }
    ];
  ///////////
  created(){
  }
  close() {
    this.$emit("close");
  }
}
