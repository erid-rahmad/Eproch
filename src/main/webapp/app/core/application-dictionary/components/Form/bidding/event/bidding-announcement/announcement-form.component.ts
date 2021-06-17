import AccountService from '@/account/account.service';
import ScheduleEventMixin from '@/core/application-dictionary/mixins/ScheduleEventMixin';
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue, Watch } from "vue-property-decorator";

const baseApiBidding = 'api/m-biddings'
const baseApiBiddingSchedule = 'api/m-bidding-schedules'
const baseApiAnnouncement = 'api/c-announcements';
const baseApiVendorSuggestion = 'api/m-vendor-suggestions';
const baseApiUser = 'api/ad-users';

const AnnouncementFormProps = Vue.extend({
  props: {
    newRecord: Boolean
  }
})

@Component({
  components: {
    HtmlEditor
  }
})
export default class AnnouncementForm extends Mixins(ScheduleEventMixin, AnnouncementFormProps) {

  @Inject('accountService')
  private accountService: () => AccountService;

  loading: boolean = false;
  loadingVendors: boolean = false;
  loadingEmailList: boolean = false;
  publishing: boolean = false;

  attachmentFormVisible = false;
  emailPreviewVisible = false;
  recipientListVisible = false;

  public biddingData: any = {};
  public value: any = {};
  public itemname: any = {};
  public emailList: any[] = [];

  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  private action: string = "/api/c-attachments/upload"
  private limit: number = 1;
  private file: any = {};

  private vendorSuggestions: any[] = [];
  private dataForAnnouncment: any = {};

  private selectedRecipients = [];

  formData: any = {
    adOrganizationId: AccountStoreModule.organizationInfo.id,
    biddingId: null,
    description: null,
    attachment: null,
    attachmentId: null,
    emaillist: []
  };

  validationSchema = {
    biddingId: {
      required: true, message: 'Bidding is required'
    },
    description: {
      required: true, message: 'Description is required'
    },
  }

  get attachmentName() {
    return this.formData.attachmentName;
  }

  get hasAttachment() {
    return !!this.formData.attachmentId;
  }

  @Watch('dataForAnnouncment')
  onDataChang(data: any) {
    console.log('Value:', data);
    this.changedata();
  }

  onMainFormUpdated(mainForm: any) {
    if (!this.newRecord) {
      this.$set(this.formData, 'biddingId', mainForm.biddingId);
      this.$set(this.formData, 'biddingScheduleId', mainForm.id);
    }
    this.retrieveVendorSuggestions(mainForm.biddingId);
    if (!this.newRecord) {
      this.retrieveAnnouncement(mainForm.biddingId, mainForm.id);
    }
  }

  created() {
    this.retrieveBiddings();
    if(this.formData.description===null){
      this.formData.description='<p><br>Kepada Bapak/Ibu Pimpinan <br>#vendorName <br>Hal: Undangan #biddingTitle <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>';

    }
  }

  changedata() {
    this.emailList = this.dataForAnnouncment.emaillist;
    this.vendorSuggestions = this.dataForAnnouncment.vendorlist;
  }

  private retrieveBiddings() {
    this.commonService(baseApiBidding)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.biddingData = res.data;
      });
  }

  private retrieveAnnouncement(biddingId: number, scheduleId: number) {
    this.loading = true;
    this.commonService(baseApiAnnouncement)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${biddingId}`,
          `biddingScheduleId.equals=${scheduleId}`
        ])
      })
      .then(res => {
        const data = res.data as any[];
        if (data.length) {
          this.formData = {...this.formData, ...data[0]};
        }
      })
      .catch(err => this.$message.error('Failed to get bidding announcement'))
      .finally(() => this.loading = false);
  }

  retrieveVendorSuggestions(biddingId: number) {
    this.loadingVendors = true;
    this.retrieveBiddingScheduleByFormType(biddingId, 'AN');
    this.commonService(baseApiVendorSuggestion)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${biddingId}`
        ])
      })
      .then(res => {
        this.vendorSuggestions = res.data;
      })
      .catch(_err => {
        this.$message.error('Failed to get the vendor suggestions');
      })
      .finally(() => this.loadingVendors = false);
  }

  retrieveBiddingScheduleByFormType(biddingId: number, formType: string) {
    this.commonService(baseApiBiddingSchedule)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          `biddingId.equals=${biddingId}`,
          `formType.equals=${formType}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => this.formData.biddingScheduleId = res.data[0]?.id);
  }

  retrieveEmailList() {
    if (!this.vendorSuggestions.length) {
      return;
    }

    const vendorQuery = this.vendorSuggestions.map(vendor => `cVendorId.in=${vendor.vendorId}`);

    this.loadingEmailList = true;
    this.commonService(baseApiUser)
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
      .finally(() => this.loadingEmailList = false);
  }

  onRecipientSelectionChanged(val) {
    this.selectedRecipients = val;
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
      return false;
    }

  }

  handlePreview() {
    console.log(this.formData.attachmentUrl)
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
    console.log("this response",response)
    this.formData.attachment = response.attachment;
    this.formData.attachmentId = response.attachment.id;
    this.formData.attachmentName = response.attachment.name;
    this.formData.attachmentUrl = response.downloadUri;
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

  //upload

  openRecipientList() {
    this.recipientListVisible = true;
  }

  publish() {
    (<ElForm>this.$refs.mainForm).validate((passed, errors) => {
      if (passed) {
        this.loading = true;
        this.commonService(baseApiAnnouncement)
          .create(this.formData)
          .then(res => {
            this.formData = { ...this.formData, ...res };
            this.loading = true;

            if (this.selectedRecipients.length == 0) {
              this.$message.error('Please select at least one recipient');
            } else {
              const data = {
                announcement: this.formData,
                bidding: {
                  id: this.mainForm.biddingId,
                  name: this.mainForm.biddingTitle,
                  documentNo: this.mainForm.biddingNo
                },
                users: [],
                vendor: this.vendorSuggestions,

              };

              for (const recipient of this.selectedRecipients) {
                const {
                  createdBy,
                  createdDate,
                  lastModifiedBy,
                  lastModifiedDate,
                  password,
                  ...user
                } = recipient;
                data.users.push(user);
              }

              this.commonService(`${baseApiAnnouncement}/publish/${data.announcement.id}`)
                .create(data)
                .then(() => {
                  this.$message.success('Announcement has been published successfully');
                  this.recipientListVisible = false;
                })
                .catch(() => this.$message.error('Failed to publish bidding announcement'))
                .finally(() => this.loading = false);
            }


          })
          .catch(_err => this.$message.error('Failed to save the announcement'))
          .finally(() => this.loading = false);
      }
    })
  }

  saveAsDraft() {
    (<ElForm>this.$refs.mainForm).validate((passed, errors) => {
      if (passed) {
        this.loading = true;
        this.commonService(baseApiAnnouncement)
          .create(this.formData)
          .then(res => {
            this.formData = {...this.formData, ...res};
            this.$message.success('Announcement has been saved successfully');
          })
          .catch(_err => this.$message.error('Failed to save the announcement'))
          .finally(() => this.loading = false);
      }
    })
  }

  saveAttachment() {
    this.formData.attachmentId = this.formData.attachment.id;
    this.formData.attachmentName = this.formData.attachment.fileName;
    this.attachmentFormVisible = false;
  }
}
