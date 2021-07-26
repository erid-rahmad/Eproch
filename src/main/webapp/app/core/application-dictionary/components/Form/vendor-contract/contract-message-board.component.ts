import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const MsgBoardProp = Vue.extend({
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
export default class ContractMessageBoard extends mixins(AccessLevelMixin, MsgBoardProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  negotiationChatApi = '/api/m-contract-messages';

  contract: any = {};
  chatForm: any = {text:"", publishToEmail:false};

  chatHistory: any[] = [];
  showChatForm = false;
  submitting = false;
  
  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";
  private file: any = {};

  private fileList: any[] = [];
  
  loading = true;
  detailLoading = true;

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
    this.contract = {...this.data};
    this.refreshChat();
  }

  refreshChat(){
    this.loading = true;
    this.commonService(this.negotiationChatApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `contractId.equals=${this.contract.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then((res)=>{
      console.log(res.data);
      this.chatHistory = res.data;
    }).finally(()=>{this.loading = false});
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

  handlePreview(file) {
    console.log(file);
    window.open(file.response?file.response.downloadUri:file.url, '_blank');
  }

  handleRemove(files, fileList) {
    this.fileList = [];
    this.chatForm.attachmentId = null;
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
        this.chatForm.contractId = this.contract.id;
        this.chatForm.vendorId = this.contract.vendorId;
        this.chatForm.adOrganizationId = this.contract.adOrganizationId;
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

  downloadAttachment(row){
    window.open(row.downloadUrl, '_blank');
  }
}