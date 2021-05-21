import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import {Component, Inject, Vue} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import {element} from "protractor";
import AccountService from "@/account/account.service";

const EventAnnouncementProps = Vue.extend({
  props: {
    pickRow: {
      type: Object,
      default: () => {}
    }
  }
});

@Component({
  components: {
    HtmlEditor
  }
})
export default class EventAnnouncement extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ContextVariableAccessor,EventAnnouncementProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;

  private formData :any={};
  private action: string = "/api/c-attachments/upload"
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  private file: any = {};
  attachmentFormVisible = false;

  private biddingEvalResult:any={};
  loading: boolean = false;
  private emailList:any={};
  private selectedRecipients:any={};
  private schedule:any={};


  index: boolean = true;
  pages: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;
  dialogTableVisible11 = false;
  recipientListVisible=false;


  created() {
    console.log("this row",this.pickRow);
    this.retrieveBiddingEvalResult();
    this.retrieveapiAnnouncementResults(this.pickRow.id);
    this.retrieveBiddingSchedule()
  }

  private retrieveBiddingEvalResult() {
    this.commonService("api/m-bidding-eval-results")
      .retrieve({
        criteriaQuery: this.updateCriteria([
          // `biddingId.equals=${this.pickRow.id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        let biddingEvent:any=[];
        res.data.forEach(result => {
          // console.log("this result before",result)
          if (result.biddingId===this.pickRow.id){
            // console.log(result.biddingId,this.pickRow.id)
            biddingEvent.push(result);
          }
        });
        this.biddingEvalResult = biddingEvent;
        // console.log("this biddingEvent",biddingEvent)
        // console.log("this biddingEvalResult",this.biddingEvalResult)
      });
  }

  private retrieveBiddingSchedule() {
    const a : String="RS";
    this.commonService("api/m-bidding-schedules")
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `biddingId.equals=${this.pickRow.id}`,
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach(result => {
          console.log("this result",result)
          if (result.formType==="RS"){
            this.schedule=result;
          }
        });
        console.log("this schedule ",this.schedule);
      });
  }

  onRecipientSelectionChanged(val) {
    this.selectedRecipients = val;
  }

  retrieveEmailList() {
    const vendorQuery = this.biddingEvalResult.map(vendor => `cVendorId.in=${vendor.vendorId}`);
    this.commonService("api/ad-users")
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          'vendor.equals=true',
          ...vendorQuery
        ])
      })
      .then(res => {
        this.emailList = res.data;
      })
      .catch(err => this.$message.error('Failed to get the email recipients'))
  }

  private retrieveapiAnnouncementResults(biddingId: number) {

    this.commonService("api/c-announcement-results")
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `biddingId.equals=${biddingId}`,
        ])
      })
      .then(res => {
        const data = res.data as any[];
        if (data.length) {
          this.formData = {...this.formData, ...data[0]};
        }
      })
      .catch(err => this.$message.error('Failed to get bidding announcement'))
  }

  //upload
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

  handlePreview() {
    window.open(this.formData.attachmentUrl, '_blank');
  }

  cancelAttachment() {
    this.formData.attachmentId = null;
    this.formData.attachmentName = null;
    this.formData.attachmentUrl = null;
    this.formData.attachment = null;
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

  onUploadSuccess(response, file, fileList) {
    this.formData.attachment = response.attachment;
    this.formData.attachmentId = response.attachment.id;
    this.formData.attachmentName = response.attachment.name;
    this.formData.attachmentUrl = response.attachment.downloadUrl;
    this.file = file;
  }

  handleRemove(file, fileList) {
    console.log(file, fileList);

  }
  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  get uploadHeaders() {
    if (this.accountService().hasToken) {
      return {
        'Authorization': `Bearer ${this.accountService().token}`
      }
    }
    return {};
  }

  printFileName(attachment: any) {
    return attachment?.fileName;
  }

  saveAttachment() {
    this.formData.attachmentId = this.formData.attachment.id;
    this.formData.attachmentName = this.formData.attachment.fileName;
    this.formData.attachmentUrl = this.formData.attachment.downloadUrl;
    this.attachmentFormVisible = false;
  }

  get attachmentName() {
    return this.formData.attachmentName;
  }

  get hasAttachment() {
    return !!this.formData.attachmentId;
  }
  //upload end

  saveDraft(){
    console.log()
    this.formData.adOrganizationId=1;
    this.formData.biddingId=this.pickRow.id;
    this.formData.biddingScheduleId=this.schedule.id;
    console.log(this.formData)

    this.loading = false
      this.commonService("api/c-announcement-results")
        .create(this.formData)
        .then(res => {
          this.formData = res;
          this.$message.success('Announcement has been saved successfully');
        })
        .catch(_err => this.$message.error('Failed to save the announcement'))
        .finally(() => this.loading = false);
    }

  publish() {
      const data :any ={};
      data.cAnnouncementResultDTO=this.formData;
      data.mBiddingEvalResult=this.biddingEvalResult;
      data.users=this.selectedRecipients;

      // this.commonService(`${baseApiAnnouncement}/publish/${data.announcement.id}`)
      this.commonService(`api/m-bidding-results/publish`)
        .create(data)
        .then(() => {
          this.$message.success('Announcement has been published successfully');
          this.recipientListVisible = false;
        })
        .catch(() => this.$message.error('Failed to publish bidding announcement'))
        .finally(() => this.loading = false);

  }



  back() {
    this.pages = 1;
  }

  backtomain() {
    this.$emit("back")
      ;
  }



}
