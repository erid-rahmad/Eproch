import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
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

  negotiationLineApi = '/api/m-bidding-negotiation-lines';
  line: any = {};
  chatForm: any = {text:""};

  chatHistory = "";
  showChatForm = false;
  submitting = false;

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";
  private file: any = {};

  private fileList: any[] = [];

  created() {
    console.log(this.data);
    this.line = {...this.data};
    this.chatHistory = "Tidak ada percakapan"
  }

  openChatForm(){
    this.showChatForm = true;
  }

  clearForm(){
    this.chatForm.text = "";
    this.showChatForm = false;
  }

  onUploadChange(file: any) {
    this.fileList = [file];
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
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
}