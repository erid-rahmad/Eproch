import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const NegotiationLineProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component
export default class BiddingNegotiationLineConversation extends mixins(AccessLevelMixin, NegotiationLineProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  negotiationChatApi = '/api/m-bidding-negotiation-chats';
  negotiationLineApi = '/api/m-bidding-negotiation-lines';
  negoPriceLineApi = '/api/m-bid-nego-price-lines';
  negoPriceApi = '/api/m-bid-nego-prices';
  line: any = {};
  chatForm: any = {text:"", publishToEmail:false};

  chatHistory: any[] = [];
  showChatForm = false;
  showNegoForm = false;
  submitting = false;
  isPercentage = false;

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";
  private file: any = {};

  private fileList: any[] = [];
  private fileList2: any[] = [];

  negoPrice: any = {};
  negoPriceLine: any[] = [];

  chatFormValidationSchema = {
    text: {
      required: true,
      message: 'Text is required.'
    }
  };

  get isVendor(){
    return AccountStoreModule.isVendor;
  }

  created() {
    console.log(this.data);
    this.line = {...this.data};
    this.refreshChat();
  }

  refreshChat(){
    this.commonService(this.negotiationChatApi).retrieve({
      criteriaQuery: this.updateCriteria([
        //'active.equals=true',
        `negotiationLineId.equals=${this.line.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      this.chatHistory = res.data;
    });
  }

  openChatForm(){
    this.showChatForm = true;
  }

  clearForm(){
    this.chatForm.text = "";
    this.chatForm.publishToEmail = false;
    this.showChatForm = false;
  }

  onUploadChange(file: any) {
    this.fileList = [file];
  }

  onUploadChangeN(file: any) {
    this.fileList2 = [file];
  }

  handlePreview(file) {
    console.log(file);
    window.open(file.response?file.response.downloadUri:file.url, '_blank');
  }

  handleRemove(files, fileList) {
    this.fileList = [];
    this.chatForm.attachmentId = null;
  }

  handleRemoveN(files, fileList) {
    this.fileList2 = [];
    this.negoPrice.attachmentId = null;
    this.commonService(this.negoPriceApi).update(this.negoPrice).then(
      (res)=>{
        console.log(res);
        this.$message.success("File removed to negotiation.");
      }
    ).catch((error)=>{
      this.$message.error("Failed to remove uploaded file.");
    });
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
    this.$notify({
      title: 'Error',
      message: "Failed uploading a file",
      type: 'error',
      duration: 3000
    });
  }

  onUploadSuccess(response: any, file) {
      console.log('File uploaded successfully ', response);
      this.chatForm.attachmentId = response.attachment.id;
      this.fileList = [file];
      //(this.$refs.company as ElForm).clearValidate('file');
  }

  onUploadSuccessN(response: any, file) {
    console.log('File uploaded successfully ', response);
    this.negoPrice.attachmentId = response.attachment.id;
    this.fileList2 = [file];

    this.commonService(this.negoPriceApi).update(this.negoPrice).then(
      (res)=>{
        console.log(res);
        this.$message.success("File saved to negotiation.");
      }
    ).catch((error)=>{
      this.$message.error("Failed to save uploaded file.");
    });
    //(this.$refs.company as ElForm).clearValidate('file');
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 1 file(s) are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  handleBeforeUpload(file: any) {
    // File size limitation
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      this.$notify({
          title: 'Warning',
          message: "File size must be less than 2Mb",
          type: 'warning',
          duration: 3000
      });
      return isLt2M;
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

  submitForm(){
    (<ElForm>this.$refs.chatForm).validate(passed => {
      if(passed){
        this.chatForm.biddingId = this.line.biddingId;
        this.chatForm.vendorId = this.line.vendorId;
        this.chatForm.negotiationLineId = this.line.id;
        this.chatForm.adOrganizationId = this.line.adOrganizationId;
        this.chatForm.active = true;
        if(this.isVendor) this.chatForm.vendorText = this.chatForm.text;
        else this.chatForm.buyerText = this.chatForm.text;

        this.submitting = true;

        this.commonService(this.negotiationChatApi).create(this.chatForm).then(
          (res)=>{
            console.log(res);
            this.$message.success("Conversation submitted.");
            this.clearForm();

            this.refreshChat();
          }
        ).catch((error)=>{
          this.$message.error("Failed to submit conversation.");
        }).finally(()=>{
          this.submitting = false;
        });
      }
    });
  }

  viewNegoDetail(){
    this.showNegoForm=true;
    this.commonService(this.negoPriceLineApi).retrieve({
      criteriaQuery: this.updateCriteria([
        //'active.equals=true',
        `negotiationPriceId.equals=${this.line.negoPriceId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      this.negoPriceLine=res.data;
    });
    this.commonService(this.negoPriceApi).retrieve({
      criteriaQuery: this.updateCriteria([
        //'active.equals=true',
        `negotiationPriceId.equals=${this.line.negoPriceId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      this.negoPrice=res.data[0];
      this.negoPrice.percentDiff=((this.line.proposedPrice-this.negoPrice.negotiationPrice)/this.line.proposedPrice)*100;
      this.negoPrice.percentDiff = this.truncateDecimals(this.negoPrice.percentDiff,2);

      if(this.negoPrice.attachmentId){
        this.fileList2 = [{"name":this.negoPrice.fileName, "url":this.negoPrice.downloadUrl}];
      }
    })
  }

  updateTotal(row){
    console.log(row);
    row.priceNegotiation = parseInt(row.priceNegotiation);
    if(Object.is(row.priceNegotiation,NaN)){
      row.priceNegotiation = row.proposedPrice;
    }
    row.totalNegotiationPrice = row.quantity * row.priceNegotiation;
    row.negotiationPercentage = ((row.proposedPrice-row.priceNegotiation)/row.proposedPrice)*100
    this.reCalcTotal();
  }

  updateTotalByPercentage(row){
    console.log(row);
    row.negotiationPercentage = parseFloat(row.negotiationPercentage);
    if(Object.is(row.negotiationPercentage,NaN)){
      row.negotiationPercentage = 0;
    }
    row.priceNegotiation = row.proposedPrice*(100-row.negotiationPercentage)/100;
    row.totalNegotiationPrice = row.quantity * row.priceNegotiation;
    this.reCalcTotal();
  }

  reCalcTotal(){
    this.negoPrice.negotiationPrice = this.negoPriceLine.reduce((a,b)=>{
      return (a.totalNegotiationPrice?a.totalNegotiationPrice:a)+b.totalNegotiationPrice;
    });
    this.negoPrice.percentDiff=((this.line.proposedPrice-this.negoPrice.negotiationPrice)/this.line.proposedPrice)*100;
    this.negoPrice.percentDiff = this.truncateDecimals(this.negoPrice.percentDiff,2);
  }

  declineNegotiation(){
    this.submitting=true;
    this.line.negotiationStatus = 'disagreed';

    this.commonService(`${this.negotiationLineApi}/finalize/${this.line.id}`).create(this.line).then(
      (res)=>{
        console.log(res);
        this.$message.success("Negotiation agreed.");
        this.showNegoForm=false;
        this.refreshChat();
      }
    ).catch((error)=>{
      this.$message.error("Failed to submit negotiation.");
    }).finally(()=>{
      this.submitting = false;
    });
  }

  submitNegotiation(){
    this.submitting=true;
    if(this.isVendor){
      this.line.negotiationStatus = 'agreed';

      this.commonService(`${this.negotiationLineApi}/finalize/${this.line.id}`).create(this.line).then(
        (res)=>{
          console.log(res);
          this.$message.success("Negotiation agreed.");
          this.showNegoForm=false;
          this.refreshChat();
        }
      ).catch((error)=>{
        this.$message.error("Failed to submit negotiation.");
      }).finally(()=>{
        this.submitting = false;
      });
    } else {
      this.negoPrice.line = this.negoPriceLine
      this.commonService(this.negoPriceApi).update(this.negoPrice).then(
        (res)=>{
          console.log(res);
          this.$message.success("Negotiation submitted.");
          this.showNegoForm=false;
          this.refreshChat();
        }
      ).catch((error)=>{
        this.$message.error("Failed to submit negotiation.");
      }).finally(()=>{
        this.submitting = false;
      });
    }
  }

  downloadAttachment(row){
    window.open(row.downloadUrl, '_blank');
  }

  truncateDecimals = function (number, digits) {
    var multiplier = Math.pow(10, digits),
        adjustedNum = number * multiplier,
        truncatedNum = Math[adjustedNum < 0 ? 'ceil' : 'floor'](adjustedNum);

    return truncatedNum / multiplier;
  };
}