import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
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

data() {
  return {
    
    
    gridData: [
      {
      no: '1',
      subitem: 'pc',
      subsubitem: 'pc lenovo',
      uom: 'yes',
      proposedprice:'12000',
      date: '2016-05-02',
      name: 'John Smith',
      address: 'No.1518,  Jinshajiang Road, Putuo District'
      }, 
      {
        no: '2',
        subitem: 'pc',
        subsubitem: 'pc acer',
        uom: 'yes',
        proposedprice:'12000',
        date: '2016-05-02',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District'
      },
      {
        no: '3',
        subitem: 'pc',
        subsubitem: 'pc hp',
        uom: 'yes',
        proposedprice:'12000',
        date: '2016-05-02',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District'
      },
      {
        no: '4',
        subitem: 'pc',
        subsubitem: 'pc rusak',
        uom: 'yes',
        proposedprice:'12000',
        date: '2016-05-02',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District'
      },  
    ],
    dummy: [
      {
      no: '1',
      dockument: 'suport dockument',
      vendorsubmision: 'start 03/01/2021 end 03/10/2021',
      vendorevaluation: 'start 03/01/2021 end 03/10/2021',
      },
      {
        no: '1',
        dockument: 'testing dockument',
        vendorsubmision: 'start 03/01/2021 end 03/10/2021',
        vendorevaluation: 'start 03/01/2021 end 03/10/2021',
      },
      {
        no: '1',
        dockument: 'QA dockument',
        vendorsubmision: 'start 03/01/2021 end 03/10/2021',
        vendorevaluation: 'start 03/01/2021 end 03/10/2021',
      },
      {
        no: '1',
        dockument: 'finishing dockument',
        vendorsubmision: 'start 03/01/2021 end 03/10/2021',
        vendorevaluation: 'start 03/01/2021 end 03/10/2021',
        },
      
      
    ],

    // fullscreen:true,
    form: {
      name: '',
      region: '',
      date1: '',
      date2: '',
      delivery: false,
      type: [],
      resource: '',
      desc: ''
    },
    formLabelWidth: '120px'
  };
}
  
  
  
  //testing

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 150,
    height: 150
  };
  rules = {}


  fullscreenLoading = false;
  processing = false;
  loading = false;
  price = 0;
  input: '';
  dialogTableVisible: false;
  dialogFormVisible: false;
  value1: true;
  value2: true;

  //use
  public moreInformation: any = {};
  public id = 663751;

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


  private projectInformation:any = {
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
  dialogConfirmationVisible:boolean = false;
  dialogConfirmationVisibleFormSubSubItem:boolean = false;

  dialogTitle = "";
  dialogContent = null;
  dialogContentSubItem = null;
  dialogWidth = "";
  dialogCloseOnClick = true;

  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private baseApiUrlRequisition = "/api/m-requisitions";
  private baseApiUrlRequisitionLine = "/api/m-requisition-lines";

  private keyReferenceVendorSelection: string = "mVendorSelection";

  created() {
    this.moreInformationData();
    // this.retrieveCostCenter();
    // this.retrieveBiddingType();
    // this.retrieveGetReferences(this.keyReferenceVendorSelection);
    // this.retrievePicBidding();
    // this.retrieveEventType();
    // this.retrieveUom();
    console.log("this row line",this.biddingrow);
    
  }

  private moreInformationData() {
      this.dynamicWindowService(`/api/m-biddings/${this.biddingrow.id}`)
        .retrieve({
          paginationQuery: {
            page: 0,
            size: 10000,
            sort: ['name']
          }
        })
        .then(res => {
          this.moreInformation = res.data;
          console.log("this more information",this.moreInformation);
          
        });
  }
  
  view(row) {    
    console.log("this row",row);
    
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

  addProject(){
    this.dialogTitle = "Add Project";
    this.dialogContent = 1;
    this.dialogConfirmationVisible = true;
    this.dialogWidth = "50%";
    this.dialogCloseOnClick = true;
  }

  remoteMethod(query) {
    if (query !== '') {
      this.loading = true;
      // this.retrieveProduct(query);
    } else {
      this.productOptions = [];
    }
  }

  removeProject(index){
    this.biddingrow.projectInformation.splice(index, 1);
  }

  saveProject(){
    (this.$refs.projectInformation as ElForm).validate((passed, errors) => {
      if(passed){
        console.log(this.projectInformation);
        this.biddingrow.projectInformation.push(this.projectInformation);
        this.dialogConfirmationVisible = false;
        this.projectInformation = {
          information: "",
          attachment: ""
        };
      }else{
        console.log(errors);
      }
    });
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  downloadAttachment(row){
    window.open(`http://localhost:9000/api/c-attachments/download/${row.attachment.id}-${row.attachment.fileName}`, '_blank');
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
    const ext = name
      ? name.substr(name.lastIndexOf('.') + 1, name.length)
      : true;
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
      if(passed){
        //this.submit();
      }else{
        console.log(errors);
      }

    });
  }

}
