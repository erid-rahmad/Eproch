import { mixins } from 'vue-class-component';
import {Component, Inject, Vue} from 'vue-property-decorator';
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import AccountService from "@/account/account.service";
import ScheduleEventMixin from "@/core/application-dictionary/mixins/ScheduleEventMixin";
import { ElForm } from 'element-ui/types/form';

const baseApiBiddingEvalResult ="api/m-prequalification-submissions";
const baseApiAnnouncementResult ="api/m-prequal-announcement-results";
const baseApiSchedules ="api/m-prequalification-schedules";
const baseApiBiddingResult ="api/m-prequalification-results/publish";

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
  mainForm:any={};

  private biddingEvalResult:any[]=[];
  loading: boolean = false;
  private emailList:any={};
  private selectedRecipients:any={};
  private schedule:any={};

  publishing: boolean = false;

  index: boolean = true;
  pages: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;
  dialogTableVisible11 = false;
  recipientListVisible=false;

  showPass: boolean = true;
  loadingEmailList: boolean = false;
  limit = 1;
  emailPreviewVisible: boolean = false;

  validationSchema:any = {
    documentNo: {
      required: true,
      message: 'Announcement no. is required.'
    }
  }

  created() {
    console.log(this.pickRow);
    if(this.pickRow){
      Promise.allSettled([
      this.retrieveBiddingEvalResult(this.pickRow.id),
      this.retrieveapiAnnouncementResults(this.pickRow.id),
      this.retrieveBiddingSchedules(this.pickRow.id)]).then((res)=>{
        this.descriptionLoading=false;
        this.biddingEvalResultLoading=false;
        this.data.No=this.pickRow.documentNo;
        this.data.Name=this.pickRow.name;
        this.mainForm.id=this.pickRow.id;
      })
    }
  }

  onMainFormUpdatedevaluation(mainForm: any){
    console.log("this mainForm",mainForm)
    this.retrieveBiddingEvalResult(mainForm.biddingId);
    this.retrieveapiAnnouncementResults(mainForm.biddingId);
    this.retrieveBiddingSchedules(mainForm.biddingId);
    this.data.No=mainForm.biddingNo;
    this.data.Name=mainForm.biddingName;
    this.mainForm.id=mainForm.biddingId;
  }


  retrieveBiddingEvalResult(biddingId): Promise<boolean> {
    return new Promise((resolve, reject) => {
    this.biddingEvalResultLoading=true;
    this.commonService(baseApiBiddingEvalResult)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `prequalificationId.equals=${biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.biddingEvalResult = res.data;
        // FOR DEMO
        this.biddingEvalResult.forEach((item)=>{
          if((<string> item.vendorName).toLowerCase().search("buana")>1){
            item.passFail = 'pass';
          }
        })
        // FOR DEMO
        console.log("biddingEvalResult",this.biddingEvalResult);
        resolve(true);
      })
      .catch(err => {
        this.$message.error('Failed to retrieveBiddingEvalResult')
        reject(false);
      });
    });
  }

  retrieveBiddingSchedules(biddingId) {
    return new Promise((resolve, reject) => {
    const a : String="RS";
    this.commonService(baseApiSchedules)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `prequalificationId.equals=${biddingId}`,
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id,desc']
        }
      })
      .then(res => {
        console.log(res.data);
        res.data.forEach(result => {
          if (result.eventLineName.toLowerCase().search('result announcement')>-1){
            this.schedule=result;
          }
        })
        resolve(true);
      }).catch(err => {
        this.$message.error('Failed to get schedule')
        console.log("this err ",err)
        reject(false);
      });
    });
  }

  onRecipientSelectionChanged(val) {
    this.selectedRecipients = val;
  }

  retrieveEmailList() {
    this.loadingEmailList = true;
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
      .finally(()=>{this.loadingEmailList=false;})
  }

  retrieveapiAnnouncementResults(biddingId): Promise<boolean> {
    return new Promise((resolve, reject) => {this.descriptionLoading=true;
    this.commonService(baseApiAnnouncementResult)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `prequalificationId.equals=${biddingId}`,
        ])
      })
      .then(res => {
        const data = res.data as any[];
        if (data.length) {
          this.formData = {...this.formData, ...data[0]};
          console.log("this data retrieveapiAnnouncementResults ",this.formData)
        } else {
          this.formData.descriptionPass = '<p><br>Kepada Bapak/Ibu Pimpinan <br>#vendorName <br>Hal: Pengumuman Hasil Prekualifikasi <br>Dengan hormat </p><p>Sehubung dengan proses prekualifikasi sesuai judul di atas, kami memberitahukan mengenai hasil Prakualifikasi yang telah Bapak/Ibu ikutsertakan telah lolos tahap prekualifikasi. Silahkan Bapak/Ibu dapat melihat hasil terlampir. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>'
          this.formData.descriptionFail = '<p><br>Kepada Bapak/Ibu Pimpinan <br>#vendorName <br>Hal: Pengumuman Hasil Prekualifikasi <br>Dengan hormat </p><p>Sehubung dengan proses prekualifikasi sesuai judul di atas, kami memberitahukan mengenai hasil Prakualifikasi yang telah Bapak/Ibu ikutsertakan tidak lolos tahap prekualifikasi. Silahkan Bapak/Ibu dapat melihat hasil terlampir. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>'
        }
        resolve(true);
      })
      .catch(err => {
        this.$message.error('Failed to get bidding announcement');
        reject(false);
      })
    });
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
    let downloadUrl = this.formData.attachmentUrl;
    if(!downloadUrl.startsWith('https')){
      downloadUrl = downloadUrl.replace('http','https');
    }
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
    (<ElForm>this.$refs.mainForm).validate(passed=>{
      if(passed){
        this.formData.adOrganizationId=this.schedule.adOrganizationId;
        this.formData.prequalificationId=this.mainForm.id;
        this.formData.prequalificationScheduleId=this.schedule.id;
        console.log(this.formData);

        this.loading = false
        this.commonService("api/m-prequal-announcement-results")
          .create(this.formData)
          .then(res => {
            this.formData = res;
            this.$message.success('Announcement has been saved successfully');
          })
          .catch(_err => this.$message.error('Failed to save the announcement'))
          .finally(() => this.loading = false);
      }
    });
  }

  publish() {
    (<ElForm>this.$refs.mainForm).validate(passed=>{
      if(passed){
        this.formData.adOrganizationId=this.schedule.adOrganizationId;
        this.formData.biddingId=this.mainForm.id;
        this.formData.biddingScheduleId=this.schedule.id;
        this.loading = false
        this.commonService("api/m-prequal-announcement-results")
          .create(this.formData)
          .then(res => {
            this.formData = res;
            this.$message.success('Announcement has been saved successfully');

            const data :any ={};
            data.mPrequalAnnouncementResultDTO=this.formData;
            data.mPrequalificationSubmission=this.biddingEvalResult;
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
    });
  }

  back() {
    this.pages = 1;
  }

  backtomain() {
    this.$emit("back");
  }

  switchDisplay(mode: number){
    console.log("showPass=",!!mode);
    this.showPass = !!mode;
  }
}
