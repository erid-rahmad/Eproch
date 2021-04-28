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
import ContextVariableAccessor from "../../../ContextVariableAccessor";



const ItemDetailProp = Vue.extend({
  props: {
    biddingrow: {
      type: Object,
      default: () => {}
    },
  }
})

@Component
export default class ItemDetail extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, ItemDetailProp) {
  //testing

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

  data() {
    return {
      gridData: [{
          no: '1',
          subitem: 'pc',
          subsubitem: 'pc lenovo',
          uom: 'yes',
          proposedprice: '12000',
          date: '2016-05-02',
          name: 'John Smith',
          address: 'No.1518,  Jinshajiang Road, Putuo District'
        },
        {
          no: '2',
          subitem: 'pc',
          subsubitem: 'pc acer',
          uom: 'yes',
          proposedprice: '12000',
          date: '2016-05-02',
          name: 'John Smith',
          address: 'No.1518,  Jinshajiang Road, Putuo District'
        },
        {
          no: '3',
          subitem: 'pc',
          subsubitem: 'pc hp',
          uom: 'yes',
          proposedprice: '12000',
          date: '2016-05-02',
          name: 'John Smith',
          address: 'No.1518,  Jinshajiang Road, Putuo District'
        },
        {
          no: '4',
          subitem: 'pc',
          subsubitem: 'pc rusak',
          uom: 'yes',
          proposedprice: '12000',
          date: '2016-05-02',
          name: 'John Smith',
          address: 'No.1518,  Jinshajiang Road, Putuo District'
        },
      ],
      dummy: [{
          no: '1',
          dockument: 'Proposal Teknis',
          vendorsubmision: '03/01/2021 20:22',
          vendorevaluation: 'start 03/01/2021 end 03/10/2021',
        },
        {
          no: '1',
          dockument: 'Quotation',
          vendorsubmision: '03/01/2021 20:22',
          vendorevaluation: 'start 03/01/2021 end 03/10/2021',
        },     


      ],

      value1: true,
      formLabelWidth: '120px'
    };
  }
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 150,
    height: 150
  };
  rules = {}
  pricesubmision = 23;


  // fullscreenLoading = false;
  // processing = false;
  // loading = false;
  // price = 0;
  // input: '';
  // dialogTableVisible11: false;
  // dialogFormVisible: false;

  // value2: true;

  //use
  // public moreInformation: any = {};
  public SubItem: any = {};
  public price = 0;
  public documentSchedulesData: any = {};
  public ItemDetail: any = {};

  public costCenterOptions: any = {};
  public picBiddingOptions: any = {};
  public biddingTypeOptions: any = {};
  public vendorSelectionOptions: any = {};
  public eventTypeOptions: any = {};
  public productOptions: any = {};
  public uomOptions: any = {};
  private limit: number = 1;

  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";


  private projectInformation: any = {
    information: "",
    attachment: "",
    attachmentId: ""
  };

  private productRequirement = {
    id: "",
    productName: "",
    index: ""
  };
  private gridDataProductSubItem = [];
  private gridDataProductSubSubItem = [];
  formAddSubItem = {
    biddingLine: "",
    biddingLineProductName: "",
    productId: "",
    productObj: {},
    subItemLine: [],
    edited: false,
    totalAmount: 0
  }

  formAddSubSubItem = {
    productId: "",
    productObj: "",
    quantity: 0,
    uomId: "",
    uomObj: "",
    price: 0,
    amount: 0
  }

  columnSpacing = 32;
  dialogConfirmationVisible: boolean = false;
  dialogConfirmationVisibleFormSubSubItem: boolean = false;

  dialogTitle = "";
  dialogContent = null;
  dialogContentSubItem = null;
  dialogWidth = "";
  dialogCloseOnClick = true;
  dialogTableVisible11 = false;


  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private baseApiUrlRequisition = "/api/m-requisitions";
  private baseApiUrlRequisitionLine = "/api/m-requisition-lines";

  private keyReferenceVendorSelection: string = "mVendorSelection";

  created() {
    // this.moreInformationData();
    this.documentSchedules();
    this.ItemDetail = this.biddingrow;
    console.log("this item detail",this.ItemDetail);
    
    
    // this.retrieveCostCenter();
    // this.retrieveBiddingType();
    // this.retrieveGetReferences(this.keyReferenceVendorSelection);
    // this.retrievePicBidding();
    // this.retrieveEventType();
    // this.retrieveUom();
    console.log("this row line", this.biddingrow);
 
    

    // this.moreInformation.totalpricesubmision = 123;

  }


  private moreInformationData() {
    // this.dynamicWindowService(`/api/m-biddings/${this.biddingrow.id}`)
    this.dynamicWindowService("/api/m-biddings/1958806")
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.moreInformation = res.data;
        
        console.log("this more information", this.moreInformation);
      });
  }
 
  private Subitemdata() {
    // this.dynamicWindowService(`/api/m-bidding-sub-items/${this.moreInformation.lineSubItemID}`)
    this.dynamicWindowService("http://localhost:8080/api/m-bidding-sub-items/1958806")
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.SubItem = res.data;       
        console.log("this subitem", this.SubItem);
      });
  }

  private documentSchedules() {
    this.dynamicWindowService('/api/m-document-schedules')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.documentSchedulesData = res.data;       
        console.log("this documentSchedulesData", this.documentSchedulesData);
      });
  }

  view(row) {
    console.log("this row", row);
  }
  
  viewSubItem(tes) {
    
    console.log("this row viewSubItem", tes);    
    this.$set(this.moreInformation, 'lineSubItemID', tes.id);
    // console.log("this row viewSubItem", this.moreInformation.lineSubItemID);    
    this.dialogTableVisible11 = true;
    this.Subitemdata()
  }
  onQuantityOrderedChanged(row: any, index: number, value: number) {
    console.log(row);    
    row.totalpricesubmision = row.quantity * value;
    this.tesjo();
  }
 
  tesjo() {
    var total = 0;
    this.moreInformation.biddingLineList.forEach(element => {
      total += element.totalpricesubmision
    }); 
    console.log("price", total);
    this.$set(this.moreInformation, 'totalpricesubmision', total);
  }


  // private retrieveGetReferences(param: string) {
  //   this.dynamicWindowService(this.baseApiUrlReference)
  //   .retrieve({
  //     criteriaQuery: [`value.contains=`+param]
  //   })
  //   .then(res => {
  //       let references = res.data.map(item => {
  //           return{
  //               id: item.id,
  //               value: item.value,
  //               name: item.name
  //           };
  //       });
  //       this.retrieveGetReferenceLists(references);
  //   });
  // }

  // private retrieveGetReferenceLists(param: any) {
  //   this.dynamicWindowService(this.baseApiUrlReferenceList)
  //   .retrieve({
  //     criteriaQuery: [`adReferenceId.equals=`+param[0].id]
  //   })
  //   .then(res => {
  //       let referenceList = res.data.map(item => {
  //           return{
  //               key: item.value,
  //               value: item.name
  //           };
  //       });

  //       if(param[0].value == this.keyReferenceVendorSelection){
  //         this.vendorSelectionOptions = referenceList;
  //       }
  //   });
  // }

  // private retrieveCostCenter() {
  //   this.dynamicWindowService('/api/c-cost-centers')
  //     .retrieve({
  //       paginationQuery: {
  //         page: 0,
  //         size: 10000,
  //         sort: ['name']
  //       }
  //     })
  //     .then(res => {
  //       this.costCenterOptions = res.data;
  //     });
  // }

  // private pesan() {
  //   this.dynamicWindowService('/api/c-cost-centers')
  //     .retrieve({
  //       paginationQuery: {
  //         page: 0,
  //         size: 10000,
  //         sort: ['name']
  //       }
  //     })
  //     .then(res => {
  //       this.costCenterOptions = res.data;
  //     });
  // }

  // private retrieveBiddingType() {
  //   this.dynamicWindowService('/api/c-bidding-types')
  //     .retrieve({
  //       paginationQuery: {
  //         page: 0,
  //         size: 10000,
  //         sort: ['name']
  //       }
  //     })
  //     .then(res => {
  //       this.biddingTypeOptions = res.data;
  //     });
  // }

  // private retrievePicBidding() {
  //   this.dynamicWindowService('/api/ad-users')
  //     .retrieve({
  //       paginationQuery: {
  //         page: 0,
  //         size: 10000,
  //         sort: ['user_id']
  //       },
  //       criteriaQuery: [`vendor.specified=false`]
  //     })
  //     .then(res => {
  //       this.picBiddingOptions = res.data;
  //     });
  // }



  // private retrieveEventType() {
  //   this.dynamicWindowService('/api/c-event-types')
  //     .retrieve({
  //       paginationQuery: {
  //         page: 0,
  //         size: 10000,
  //         sort: ['name']
  //       }
  //     })
  //     .then(res => {
  //       this.eventTypeOptions = res.data;
  //     });
  // }

  // private retrieveProduct(key) {
  //   this.dynamicWindowService('/api/c-products')
  //     .retrieve({
  //       paginationQuery: {
  //         page: 0,
  //         size: 10000,
  //         sort: ['name']
  //       },
  //       criteriaQuery: `name.contains=${key}`
  //     })
  //     .then(res => {
  //       this.loading = false;
  //       this.productOptions = res.data;
  //     });
  // }

  // private retrieveUom() {
  //   this.dynamicWindowService('/api/c-unit-of-measures')
  //     .retrieve({
  //       paginationQuery: {
  //         page: 0,
  //         size: 10000,
  //         sort: ['name']
  //       }
  //     })
  //     .then(res => {
  //       this.uomOptions = res.data;
  //     });
  // }

  // jsonEncode(data: any, fields: string[]) {
  //   const record: Record<string, any> = {};
  //   for (const field of fields) {
  //     record[field] = data[field];
  //   }
  //   return JSON.stringify(record);
  // }

  // private getJsonField(json: string, field: string) {
  //   if (json) {
  //     const data = JSON.parse(json);
  //     return data[field];
  //   }
  //   return null;
  // }

  // submit1() {
  //   console.log(this.biddingInformation);
  // }

  // searchReferenceNo(){
  //   console.log(this.biddingrow.referenceNo);
  //   if((this.biddingrow.referenceNo == null)||(this.biddingrow.referenceNo == "")){
  //     this.$notify({
  //       title: 'Warning',
  //       dangerouslyUseHTMLString: true,
  //       message: 'Please fill form Reference No.',
  //       type: 'warning'
  //     });

  //   }else{
  //     this.fullscreenLoading = true;
  //     this.searchRequisition();
  //   }
  // }

  // searchRequisition(): void {
  //   if ( ! this.baseApiUrlRequisition) {
  //     return;
  //   }

  //   this.dynamicWindowService(this.baseApiUrlRequisition)
  //     .retrieve({
  //       criteriaQuery: `approved.equals=true&active.equals=true&documentNo.equals=${this.biddingrow.referenceNo}`
  //     }).then(res => {

  //       if(res.data.length == 0){
  //         this.$notify({
  //           title: 'Warning',
  //           dangerouslyUseHTMLString: true,
  //           message: 'Data not found',
  //           type: 'warning'
  //         });

  //       }else{
  //         if(res.data[0].documentStatus == "APR"){
  //           res.data.map((item: any) => {
  //             this.$set(this.biddingInformation, 'referenceTypeName', item.documentTypeName);
  //             this.$set(this.biddingInformation, 'referenceTypeId', item.documentTypeId);
  //             this.$set(this.biddingInformation, 'currencyName', item.currencyName);
  //             this.$set(this.biddingInformation, 'currencyId', item.currencyId);
  //             this.$set(this.biddingInformation, 'referenceNo', item.documentNo);
  //             this.$set(this.biddingInformation, 'requisitionId', item.id);
  //             console.log(item);
  //             return item;
  //           });

  //           this.searchRequisitionLine(this.biddingrow.requisitionId);
  //         }else{
  //           this.$notify({
  //             title: 'Warning',
  //             dangerouslyUseHTMLString: true,
  //             message: 'Status Requisition is Draft',
  //             type: 'warning'
  //           });
  //         }

  //         console.log(this.biddingInformation);

  //       }

  //     }).catch(err => {
  //       console.error('Failed getting the record. %O', err);
  //       this.$message({
  //         type: 'error',
  //         message: err.detail || err.message
  //       });
  //     }).finally(() => {
  //       this.fullscreenLoading = false;
  //     });
  // }

  // private searchRequisitionLine(requisitionId): void {
  //   if ( ! this.baseApiUrlRequisitionLine) {
  //     return;
  //   }

  //   this.dynamicWindowService(this.baseApiUrlRequisitionLine)
  //     .retrieve({
  //       criteriaQuery: `requisitionId.equals=${requisitionId}`
  //     })
  //     .then(res => {

  //       //this.dialogInvoiceVerificationVisible = true;
  //       let grandTotal = 0;
  //       this.$set(this.biddingInformation, 'biddingInformationLine', res.data.map((item: any) => {
  //         grandTotal += item.requisitionAmount;

  //         return item;
  //       }));

  //       this.$set(this.biddingInformation, 'ceilingPrice', grandTotal);
  //       this.$set(this.biddingInformation, 'estimatedPrice', grandTotal);

  //       console.log(this.biddingInformation);

  //     })
  //     .catch(err => {
  //       console.error('Failed getting the record. %O', err);
  //       this.$message({
  //         type: 'error',
  //         message: err.detail || err.message
  //       });
  //     })
  //     .finally(() => {
  //       this.fullscreenLoading = false;
  //     });
  // }

  // displayAddSubItem(row, index){
  //   console.log(row);
  //   console.log(index);
  //   if(row.subItem == null){
  //     this.gridDataProductSubItem = [];
  //   }else{
  //     this.gridDataProductSubItem = row.subItem;
  //   }

  //   this.productRequirement = row;
  //   this.productRequirement.index = index;
  //   this.dialogConfirmationVisible = true;

  //   this.dialogContent = 2;
  //   this.dialogContentSubItem = 1;
  //   this.dialogTitle = `Sub Item Product ${this.productRequirement.productName}`;
  //   this.dialogWidth = "80%";
  //   this.dialogCloseOnClick = false;
  // }

  // addSubItem(){
  //   console.log("add sub item %O", this.productRequirement);

  //   this.formAddSubItem = {
  //     biddingLine: this.productRequirement.id,
  //     biddingLineProductName: this.productRequirement.productName,
  //     productId: "",
  //     productObj: {},
  //     subItemLine: [],
  //     edited: false,
  //     totalAmount: 0
  //   }

  //   this.gridDataProductSubSubItem = [];

  //   this.dialogContentSubItem = 2;
  //   this.dialogTitle = `Add Sub Item Product ${this.productRequirement.productName}`;
  // }

  // addSubSubItem(){
  //   this.dialogConfirmationVisibleFormSubSubItem = true;
  // }

  // removeSubItem(index){
  //   this.gridDataProductSubItem.splice(index, 1);
  // }

  // removeSubSubItem(index){
  //   this.gridDataProductSubSubItem.splice(index, 1);
  // }

  // editSubItem(row){
  //   console.log("edit sub item %O", row);

  //   this.dialogTitle = `Add Sub Item Product ${this.productRequirement.productName}`;
  //   this.formAddSubItem = row;
  //   this.formAddSubItem.edited = true;
  //   this.gridDataProductSubSubItem = this.formAddSubItem.subItemLine;

  //   this.dialogContentSubItem = 2;
  // }

  // saveSubItemProduct(){
  //   if(this.dialogContentSubItem == 1){

  //     var grandTotal = 0;
  //     var totalCeilingPrice = 0;

  //     for(var i=0; i<this.gridDataProductSubItem.length; i++){
  //       grandTotal += this.gridDataProductSubItem[i].totalAmount;
  //     }

  // 		var biddingInformationLine = this.biddingrow.biddingInformationLine[this.productRequirement.index];
  //     biddingInformationLine.subItem = this.gridDataProductSubItem;
  //     biddingInformationLine.unitPrice = grandTotal;
  // 		biddingInformationLine.requisitionAmount = grandTotal * biddingInformationLine.quantity;

  // 		for(var x=0; x<this.biddingrow.biddingInformationLine.length; x++){
  //       totalCeilingPrice += this.biddingrow.biddingInformationLine[x].requisitionAmount;
  //     }

  //     console.log(grandTotal);
  //     console.log(totalCeilingPrice);

  //     this.biddingrow.ceilingPrice = totalCeilingPrice;
  //     this.biddingrow.estimatedPrice = totalCeilingPrice;

  //     console.log(this.biddingInformation);
  //     console.log(this.gridDataProductSubItem);
  //     this.dialogConfirmationVisible = false;
  //   }else{
  //     this.saveSubItem();
  //   }
  // }

  // saveSubItem(){
  //   (this.$refs.formAddSubItem as ElForm).validate((passed, errors) => {
  //     if(passed){

  //       var totalAmount = 0;
  //       for(var i=0; i<this.gridDataProductSubSubItem.length; i++){
  //         totalAmount += this.gridDataProductSubSubItem[i].amount;
  //       }
  //       console.log(totalAmount);
  //       this.formAddSubItem.totalAmount = totalAmount;

  //       if(this.formAddSubItem.edited){
  //         this.backSubItem();
  //       }else{

  //         const dataExist = (this.gridDataProductSubItem.some((vLine: any) => {
  //           return vLine.productId === this.formAddSubItem.productId;
  //         }));

  //         if(!dataExist){

  //           this.formAddSubItem.subItemLine = this.gridDataProductSubSubItem;
  //           this.formAddSubItem.productObj = this.productOptions.find(item => item.id === this.formAddSubItem.productId);

  //           if(this.formAddSubItem.subItemLine.length){

  //             console.log(this.formAddSubItem);
  //             this.dialogTitle = `Sub Item Product ${this.productRequirement.productName}`;

  //             this.gridDataProductSubItem.push(this.formAddSubItem);

  //             this.dialogContentSubItem = 1;
  //             this.formAddSubItem = {
  //               biddingLine: "",
  //               biddingLineProductName: "",
  //               productId: "",
  //               productObj: "",
  //               subItemLine: [],
  //               edited: false,
  //               totalAmount: 0
  //             }
  //             this.gridDataProductSubSubItem = [];
  //           }else{
  //             this.$message({
  //               message: "Please add sub sub item product",
  //               type: 'error'
  //             });
  //           }
  //         }else{
  //           this.$message({
  //             message: "Product sub item has been added",
  //             type: 'error'
  //           });
  //         }

  //       }
  //     }else{
  //       console.log(errors);
  //     }
  //   });
  // }

  // saveSubSubItem(){
  //   (this.$refs.formAddSubSubItem as ElForm).validate((passed, errors) => {
  //     if(passed){

  //       this.formAddSubSubItem.productObj = this.productOptions.find(item => item.id === this.formAddSubSubItem.productId);
  //       this.formAddSubSubItem.uomObj = this.uomOptions.find(item => item.id === this.formAddSubSubItem.uomId);
  //       this.formAddSubSubItem.amount = this.formAddSubSubItem.quantity * this.formAddSubSubItem.price;

  //       const dataExist = (this.gridDataProductSubSubItem.some((vLine: any) => {

  //         return vLine.productId === this.formAddSubSubItem.productId;
  //       }));

  //       if(!dataExist){
  //         this.dialogConfirmationVisibleFormSubSubItem = false;
  //         this.gridDataProductSubSubItem.push(this.formAddSubSubItem);

  //         console.log(this.formAddSubSubItem);
  //         this.formAddSubSubItem = {
  //           productId: "",
  //           productObj: "",
  //           quantity: 0,
  //           uomId: "",
  //           uomObj: "",
  //           price: 0,
  //           amount: 0
  //         }
  //       }else{
  //         this.$message({
  //           message: "Product has been added",
  //           type: 'error'
  //         });
  //       }
  //     }else{
  //       console.log(errors);
  //     }

  //   });

  // }

  // backSubItem(){
  //   this.dialogTitle = `Sub Item Product ${this.productRequirement.productName}`;
  //   //this.formAddSubItem.edited = false;

  //   this.formAddSubItem = {
  //     biddingLine: this.productRequirement.id,
  //     biddingLineProductName: this.productRequirement.productName,
  //     productId: "",
  //     productObj: {},
  //     subItemLine: [],
  //     edited: false,
  //     totalAmount: 0
  //   }

  //   this.dialogContentSubItem = 1;
  //   this.dialogWidth = "80%";
  // }

  // closeSubSubItem(){
  //   this.dialogConfirmationVisibleFormSubSubItem = false;

  //   this.formAddSubSubItem = {
  //     productId: "",
  //     productObj: "",
  //     quantity: 0,
  //     uomId: "",
  //     uomObj: "",
  //     price: 0,
  //     amount: 0
  //   }
  // }

  addProject() {
    this.dialogTitle = "Add Project";
    this.dialogContent = 1;
    this.dialogConfirmationVisible = true;
    this.dialogWidth = "50%";
    this.dialogCloseOnClick = true;
  }

  remoteMethod(query) {
    if (query !== '') {
      // this.loading = true;
      // this.retrieveProduct(query);
    } else {
      this.productOptions = [];
    }
  }

  removeProject(index) {
    this.biddingrow.projectInformation.splice(index, 1);
  }

  saveProject() {
    (this.$refs.projectInformation as ElForm).validate((passed, errors) => {
      if (passed) {
        console.log(this.projectInformation);
        this.biddingrow.projectInformation.push(this.projectInformation);
        this.dialogConfirmationVisible = false;
        this.projectInformation = {
          information: "",
          attachment: ""
        };
      } else {
        console.log(errors);
      }
    });
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  downloadAttachment(row) {
    console.log(row);    
    window.open(`http://localhost:9000/api/c-attachments/download/${row.id}-${row.fileName}`, '_blank');
  }

  handleRemove(file, fileList) {
    console.log(file, fileList);

  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response, file, fileList) {
    console.log('File uploaded successfully ', response);
    console.log(file);
    console.log(fileList);
    this.projectInformation.attachment = response.attachment;
    this.projectInformation.attachmentId = response.attachment.id;
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 1 files are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  handleBeforeUpload(file: any) {
    // File size limitation
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      this.$notify({
        title: 'Warning',
        message: "files with a size less than 5Mb",
        type: 'warning',
        duration: 3000
      });
      return isLt5M;
    }

    // File type restriction
    const name = file.name ? file.name : '';
    const ext = name ?
      name.substr(name.lastIndexOf('.') + 1, name.length) :
      true;
    const isExt = this.accept.indexOf(ext) < 0;
    if (isExt) {
      this.$notify({
        title: 'Warning',
        message: "Please upload the correct format type",
        type: 'warning',
        duration: 3000
      });
      return !isExt;
    }

  }

  //=======================================================================

  validate() {
    (this.$refs.biddingInformation as ElForm).validate((passed, errors) => {
      if (passed) {
        //this.submit();
      } else {
        console.log(errors);
      }

    });
  }

  back() {
    this.$emit("back")
      ;
  }

}
