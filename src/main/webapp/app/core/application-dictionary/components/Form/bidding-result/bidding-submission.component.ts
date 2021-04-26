import {
  ElForm
} from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import {
  mixins
} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import AdministrationProposal from './components/administration-proposal.vue';
import TechnicalProposal from './components/technical-proposal.vue';
import Priceproposal from './components/price-proposal.vue';



@Component({
  components: {
    AdministrationProposal,
    TechnicalProposal,
    Priceproposal,
  }
})
export default class BiddingSubmission extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  
  private page: number = 1;
  goadministrasi() {
    this.page = 2;
  }
  gotechnical() {
    this.page = 3;
  }
  goprice() {
    this.page = 4;
  }
  back() { 
    this.page = 1;
  }

  moreInformation = {
    "createdBy": "admin",
    "createdDate": "2021-03-29T03:30:16.500879Z",
    "lastModifiedBy": "admin",
    "lastModifiedDate": "2021-04-08T01:56:10.778Z",
    "id": 1958806,
    "name": "Pengadaan Kendaraan Operasional",
    "vendorSelection": "DRC",
    "ceilingPrice": 29570000000.00,
    "estimatedPrice": 29570000000.00,
    "biddingStatus": "N",
    "joinedVendorCount": 0,
    "dateTrx": "2021-03-29",
    "documentNo": "BN-00001",
    "documentAction": "APV",
    "documentStatus": "In Progres",
    "totalpricesubmision":29310000000,
    "approved": false,
    "processed": false,
    "dateApprove": null,
    "dateReject": null,
    "rejectedReason": null,
    "uid": "4c762479-a7c7-4cfa-89fc-ab004cc57752",
    "active": true,
    "adOrganizationId": 33851,
    "adOrganizationName": "Berca Hardayaperkasa",
    "costCenterId": 1956751,
    "costCenterName": "Marketing",
    "currencyId": 4101,
    "currencyName": "IDR",
    "documentTypeId": 1957801,
    "documentTypeName": "Bidding",
    "requisitionId": 1958504,
    "requisitionName": "PR-21030031",
    "referenceTypeId": 874951,
    "referenceTypeName": "Purchase Requisition",
    "biddingTypeId": 1953301,
    "biddingTypeName": "Tender Goods",
    "eventTypeId": 1953351,
    "eventTypeName": "Satu Tahap",
    "adUserUserId": 1954005,
    "adUserUserName": "admintender",
    "biddingLineList": [
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
        "totalpricesubmision":4160000000
      }
    ],
    "projectInformationList": [
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T03:30:16.507924Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-03-29T03:30:16.507924Z",
        "id": 1958906,
        "uid": "7091e357-d56c-4397-a57a-eb28850f431c",
        "active": true,
        "name": "Bidding Proposal",
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "attachment": {
          "createdBy": "anonymousUser",
          "createdDate": "2021-03-29T03:27:46.923768Z",
          "lastModifiedBy": "anonymousUser",
          "lastModifiedDate": "2021-03-29T03:27:46.923768Z",
          "id": 1958753,
          "type": "LOCAL",
          "fileName": "bhp132572448130081500.xlsx",
          "imageSmall": null,
          "imageMedium": null,
          "imageLarge": null,
          "mimeType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          "documentType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          "uploadDir": null,
          "uid": "effc6c2a-083a-428f-82ec-71a05f344e5f",
          "active": true,
          "adOrganizationId": 1,
          "adOrganizationName": "All"
        },
        "attachmentId": 1958753,
        "attachmentName": "bhp132572448130081500.xlsx"
      },
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T06:39:52.262102Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-03-29T06:39:52.262102Z",
        "id": 1959701,
        "uid": "44480ea2-65f8-4e28-8505-e8e6e78dfb25",
        "active": true,
        "name": "Data Sheet",
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "attachment": {
          "createdBy": "admin",
          "createdDate": "2021-03-29T06:33:04.461847Z",
          "lastModifiedBy": "admin",
          "lastModifiedDate": "2021-03-29T06:33:04.461847Z",
          "id": 1959604,
          "type": "LOCAL",
          "fileName": "1616999584452_B Data Sheet.pdf.pdf",
          "imageSmall": null,
          "imageMedium": null,
          "imageLarge": null,
          "mimeType": "application/pdf",
          "documentType": "application/pdf",
          "uploadDir": null,
          "uid": "cfc99aa7-ce50-4e73-8cdb-c316972d1173",
          "active": true,
          "adOrganizationId": 1,
          "adOrganizationName": "All"
        },
        "attachmentId": 1959604,
        "attachmentName": "1616999584452_B Data Sheet.pdf.pdf"
      },
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T06:39:52.266820Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-03-29T06:39:52.266820Z",
        "id": 1959702,
        "uid": "718c5c47-f093-4b89-acf5-9ac0d8ec20f6",
        "active": true,
        "name": "Scope of Work",
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "attachment": {
          "createdBy": "admin",
          "createdDate": "2021-03-29T06:33:24.175234Z",
          "lastModifiedBy": "admin",
          "lastModifiedDate": "2021-03-29T06:33:24.175234Z",
          "id": 1959605,
          "type": "LOCAL",
          "fileName": "1616999604171_B Scope of Work.pdf.pdf",
          "imageSmall": null,
          "imageMedium": null,
          "imageLarge": null,
          "mimeType": "application/pdf",
          "documentType": "application/pdf",
          "uploadDir": null,
          "uid": "8e0c67d7-f490-465f-93de-9b8251e8b31d",
          "active": true,
          "adOrganizationId": 1,
          "adOrganizationName": "All"
        },
        "attachmentId": 1959605,
        "attachmentName": "1616999604171_B Scope of Work.pdf.pdf"
      },
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T06:39:52.269432Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-03-29T06:39:52.269432Z",
        "id": 1959703,
        "uid": "cd0f45be-848d-4f2b-a5a9-c45537de98bc",
        "active": true,
        "name": "Terms of Reference",
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "attachment": {
          "createdBy": "admin",
          "createdDate": "2021-03-29T06:33:44.518575Z",
          "lastModifiedBy": "admin",
          "lastModifiedDate": "2021-03-29T06:33:44.518575Z",
          "id": 1959606,
          "type": "LOCAL",
          "fileName": "1616999624499_B Terms of Reference.pdf.pdf",
          "imageSmall": null,
          "imageMedium": null,
          "imageLarge": null,
          "mimeType": "application/pdf",
          "documentType": "application/pdf",
          "uploadDir": null,
          "uid": "5429572f-6f4d-4922-9f1f-bdd5e7ebbeb8",
          "active": true,
          "adOrganizationId": 1,
          "adOrganizationName": "All"
        },
        "attachmentId": 1959606,
        "attachmentName": "1616999624499_B Terms of Reference.pdf.pdf"
      },
      {
        "createdBy": "admin",
        "createdDate": "2021-03-29T06:39:52.271433Z",
        "lastModifiedBy": "admin",
        "lastModifiedDate": "2021-03-29T06:39:52.271433Z",
        "id": 1959704,
        "uid": "ef97f1ee-4dda-4b92-ac0f-009f871154a8",
        "active": true,
        "name": "Instruction to Bid",
        "biddingId": 1958806,
        "biddingName": "Pengadaan Kendaraan Operasional",
        "adOrganizationId": 33851,
        "adOrganizationName": "Berca Hardayaperkasa",
        "attachment": {
          "createdBy": "admin",
          "createdDate": "2021-03-29T06:34:05.108101Z",
          "lastModifiedBy": "admin",
          "lastModifiedDate": "2021-03-29T06:34:05.108101Z",
          "id": 1959607,
          "type": "LOCAL",
          "fileName": "1616999645104_B Instruction to Bid.pdf.pdf",
          "imageSmall": null,
          "imageMedium": null,
          "imageLarge": null,
          "mimeType": "application/pdf",
          "documentType": "application/pdf",
          "uploadDir": null,
          "uid": "af50d353-033f-43c0-be8b-fce256bdbc5e",
          "active": true,
          "adOrganizationId": 1,
          "adOrganizationName": "All"
        },
        "attachmentId": 1959607,
        "attachmentName": "1616999645104_B Instruction to Bid.pdf.pdf"
      }
    ]
  };



}
