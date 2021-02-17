import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../../ContextVariableAccessor";

const BiddingInformationProp = Vue.extend({
  props: {
    biddingInformation: {
      type: Object,
      default: () => {}
    },
  }
})

@Component
export default class BiddingInformation extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, BiddingInformationProp) {

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 150,
    height: 150
  };
  rules = {}

  fullscreenLoading = false;
  processing = false;
  public costCenterOptions: any = {};
  public picBiddingOptions: any = {};
  public biddingTypeOptions: any = {};
  public vendorSelectionOptions: any = {};
  public eventTypeOptions: any = {};

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  //private downloadUri = "";

  private projectInformation:any = {
    information: "",
    attachment: "",
    attachmentId: ""
  };

  columnSpacing = 32;
  dialogConfirmationVisible:boolean = false;

  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private baseApiUrlRequisition = "/api/m-requisitions";
  private baseApiUrlRequisitionLine = "/api/m-requisition-lines";

  private keyReferenceVendorSelection: string = "mVendorSelection";

  created() {
    this.retrieveCostCenter();
    this.retrieveBiddingType();
    this.retrieveGetReferences(this.keyReferenceVendorSelection);
    this.retrievePicBidding();
    this.retrieveEventType();
  }

  private retrieveGetReferences(param: string) {
    this.dynamicWindowService(this.baseApiUrlReference)
    .retrieve({
      criteriaQuery: [`value.contains=`+param]
    })
    .then(res => {
        let references = res.data.map(item => {
            return{
                id: item.id,
                value: item.value,
                name: item.name
            };
        });
        this.retrieveGetReferenceLists(references);
    });
  }

  private retrieveGetReferenceLists(param: any) {
    this.dynamicWindowService(this.baseApiUrlReferenceList)
    .retrieve({
      criteriaQuery: [`adReferenceId.equals=`+param[0].id]
    })
    .then(res => {
        let referenceList = res.data.map(item => {
            return{
                key: item.value,
                value: item.name
            };
        });

        if(param[0].value == this.keyReferenceVendorSelection){
          this.vendorSelectionOptions = referenceList;
        }
    });
  }

  private retrieveCostCenter() {
    this.dynamicWindowService('/api/c-cost-centers')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.costCenterOptions = res.data;
      });
  }

  private retrieveBiddingType() {
    this.dynamicWindowService('/api/c-bidding-types')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.biddingTypeOptions = res.data;
      });
  }

  private retrievePicBidding() {
    this.dynamicWindowService('/api/ad-users')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['user_id']
        },
        criteriaQuery: [`vendor.specified=false`]
      })
      .then(res => {
        this.picBiddingOptions = res.data;
      });
  }

  private retrieveEventType() {
    this.dynamicWindowService('/api/c-event-types')
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.eventTypeOptions = res.data;
      });
  }

  jsonEncode(data: any, fields: string[]) {
    const record: Record<string, any> = {};
    for (const field of fields) {
      record[field] = data[field];
    }
    return JSON.stringify(record);
  }

  private getJsonField(json: string, field: string) {
    if (json) {
      const data = JSON.parse(json);
      return data[field];
    }
    return null;
  }

  searchReferenceNo(){
    console.log(this.biddingInformation.referenceNo);
    if((this.biddingInformation.referenceNo == null)||(this.biddingInformation.referenceNo == "")){
      this.$notify({
        title: 'Warning',
        dangerouslyUseHTMLString: true,
        message: 'Please fill form Reference No.',
        type: 'warning'
      });

    }else{
      this.fullscreenLoading = true;
      this.searchRequisition();
    }
  }

  searchRequisition(): void {
    if ( ! this.baseApiUrlRequisition) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlRequisition)
      .retrieve({
        criteriaQuery: `approved.equals=true&active.equals=true&id.equals=${this.biddingInformation.referenceNo}`
      }).then(res => {

        if(res.data.length == 0){
          this.$notify({
            title: 'Warning',
            dangerouslyUseHTMLString: true,
            message: 'Data not found',
            type: 'warning'
          });

        }else{
          if(res.data[0].documentStatus == "APR"){
            res.data.map((item: any) => {
              this.$set(this.biddingInformation, 'referenceTypeName', item.documentTypeName);
              this.$set(this.biddingInformation, 'referenceTypeId', item.documentTypeId);
              this.$set(this.biddingInformation, 'currencyName', item.currencyName);
              this.$set(this.biddingInformation, 'currencyId', item.currencyId);
              this.$set(this.biddingInformation, 'referenceNo', item.id);
              console.log(item);
              return item;
            });

            //this.filterQuery = "";
            //this.filterQuery = "verificationId.equals="+this.eVerification.form.id;
            this.searchRequisitionLine(this.biddingInformation.referenceNo);
          }else{
            this.$notify({
              title: 'Warning',
              dangerouslyUseHTMLString: true,
              message: 'Status Requisition is DRF',//+this.formatDocumentStatus(res.data[0].documentStatus),
              type: 'warning'
            });
          }

          console.log(this.biddingInformation);

        }

      }).catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      }).finally(() => {
        this.fullscreenLoading = false;
      });
  }

  private searchRequisitionLine(requisitionId): void {
    if ( ! this.baseApiUrlRequisitionLine) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlRequisitionLine)
      .retrieve({
        criteriaQuery: `requisitionId.equals=${requisitionId}`
      })
      .then(res => {

        //this.dialogInvoiceVerificationVisible = true;
        let grandTotal = 0;
        this.$set(this.biddingInformation, 'biddingInformationLine', res.data.map((item: any) => {
          //this.$set(this.biddingInformation, 'requisitionAmount', item.requisitionAmount);
          grandTotal += item.requisitionAmount;

          return item;
        }));

        this.$set(this.biddingInformation, 'ceilingPrice', grandTotal);
        this.$set(this.biddingInformation, 'estimatedPrice', grandTotal);

        console.log(this.biddingInformation);

      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

  addProject(){
    this.dialogConfirmationVisible = true;
  }

  removeProject(index){
    this.biddingInformation.projectInformation.splice(index, 1);
  }

  saveProject(){
    console.log(this.projectInformation);
    this.biddingInformation.projectInformation.push(this.projectInformation);
    this.dialogConfirmationVisible = false;
    this.projectInformation = {
      information: "",
      attachment: ""
    };
  }

  handleDownload(file) {
    console.log(file);
    //window.open(file.response.downloadUri, '_blank');
  }

  handlePreview(file) {
    console.log(file)
    //this.dialogImageUrl = file.url;
    //this.dialogVisible = true;
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
    (this.$refs.productCatalog as ElForm).validate((passed, errors) => {
      if(passed){
        //this.submit();
      }else{
        console.log(errors);
      }

    });
  }

}
