import { mixins } from 'vue-class-component';
import {Component, Inject, Vue} from 'vue-property-decorator';
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import AccountService from "@/account/account.service";
import ScheduleEventMixin from "@/core/application-dictionary/mixins/ScheduleEventMixin";

const baseApiBiddingEvalResult ="api/m-bidding-eval-results";
const baseApiAnnouncementResult ="api/c-announcement-results";
const baseApiSchedules ="api/m-bidding-schedules";
const baseApiBiddingResult ="api/m-bidding-results/publish";

const EvaluationAnnouncementProps = Vue.extend({
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
export default class EvaluationAnnouncement extends mixins(ScheduleEventMixin, EvaluationAnnouncementProps) {

  @Inject('accountService')
  private accountService: () => AccountService;

  private formData :any={};
  private action: string = "/api/c-attachments/upload"
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  private file: any = {};
  private data:any={};
  attachmentFormVisible = false;
  biddingEvalResultLoading:boolean=false;
  descriptionLoading:boolean=false;

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
    if(this.pickRow){
      this.retrieveBiddingEvalResult(this.pickRow.id);
      this.retrieveapiAnnouncementResults(this.pickRow.id);
      this.retrieveBiddingSchedules(this.pickRow.id);
      this.data.No=this.pickRow.documentNo;
      this.data.Name=this.pickRow.name;
    }
  }

  onMainFormUpdatedevaluation(mainForm: any){
    this.retrieveBiddingEvalResult(mainForm.biddingId);
    this.retrieveapiAnnouncementResults(mainForm.biddingId);
    this.retrieveBiddingSchedules(mainForm.biddingId);
    this.data.No=mainForm.biddingNo;
    this.data.Name=mainForm.biddingName;
  }


   retrieveBiddingEvalResult(biddingId) {
    this.biddingEvalResultLoading=true;
    this.commonService(baseApiBiddingEvalResult)
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
          if (result.biddingId===biddingId){
            biddingEvent.push(result);
          }
        });
        this.biddingEvalResult = biddingEvent;
        console.log("biddingEvalResult",this.biddingEvalResult);
      })
      .finally(()=>this.biddingEvalResultLoading=false);
  }

    retrieveBiddingSchedules(biddingId) {
    const a : String="RS";
    this.commonService(baseApiSchedules)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `biddingId.equals=${biddingId}`,
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        res.data.forEach(result => {
          if (result.formType==="RS"){
            this.schedule=result;
          }
        })
          .catch(err => this.$message.error('Failed to get schedule'))
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

   retrieveapiAnnouncementResults(biddingId) {
    this.descriptionLoading=true;
    this.commonService(baseApiAnnouncementResult)
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
      .finally(()=>this.descriptionLoading=false);
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
    this.formData.adOrganizationId=this.schedule.adOrganizationId;
    this.formData.biddingId=this.pickRow.id;
    this.formData.biddingScheduleId=this.schedule.id;
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
    this.formData.adOrganizationId=this.schedule.adOrganizationId;
    this.formData.biddingId=this.pickRow.id;
    this.formData.biddingScheduleId=this.schedule.id;
    this.loading = false
    this.commonService("api/c-announcement-results")
      .create(this.formData)
      .then(res => {
        this.formData = res;
        this.$message.success('Announcement has been saved successfully');

        const data :any ={};
        data.cAnnouncementResultDTO=this.formData;
        data.mBiddingEvalResult=this.biddingEvalResult;
        data.users=this.selectedRecipients;
        this.commonService(baseApiBiddingResult)
          .create(data)
          .then(() => {
            this.$message.success('Announcement has been published successfully');
            this.recipientListVisible = false;
          })
          .catch(() => this.$message.error('Failed to publish bidding announcement'))
          .finally(() => this.loading = false);
      })
      .catch(_err => this.$message.error('Failed to save the announcement'))
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
