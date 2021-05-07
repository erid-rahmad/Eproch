import AccountService from '@/account/account.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import HtmlEditor from '@/shared/components/HtmlEditor/index.vue';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';

const baseApiBidding = 'api/m-biddings'
const baseApiAnnouncement = 'api/c-announcements';
const baseApiVendorSuggestion = 'api/m-vendor-suggestions';
const baseApiUser = 'api/ad-users'

@Component({
  components: {
    HtmlEditor
  }
})
export default class AddAnnouncementForm extends Mixins(AccessLevelMixin) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;

  loading: boolean = false;
  loadingVendors: boolean = false;
  loadingEmailList: boolean = false;

  attachmentFormVisible = false;
  emailPreviewVisible = false;
  recipientListVisible = false;

  public emailFromChild: any = {};
  public Announcment: any = {};
  private attachmentName = '';
  private file: any = {};

  public biddingData: any = {};
  public value: any = {};
  public itemname: any = {};
  public emailList: any = {};
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  private action: string = "/api/c-attachments/upload"  
  private limit: number = 1;
  private vendorSuggestions: any[] = [];
  private dataForAnnouncment: any = {};

  multipleSelection= [];

  private content = '<p><br>Kepada Bapak/Ibu Pimpinan <br>#VendorName <br>Hal: Undangan #TenderName <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>';

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
  
  @Watch('emailFromChild')
  onDataChangeemail(data: any) {
    console.log('Value:', data);
  console.log("changeit");
  
  }

  @Watch('dataForAnnouncment')
  onDataChang(data: any) {
    console.log('Value:', data);
    this.changedata();
  }

  created() {
    console.log("mail from child", this.emailFromChild);
    this.retrieveBiddings();
  }

  changedata() {
    this.emailList = this.dataForAnnouncment.emaillist;
    this.vendorSuggestions = this.dataForAnnouncment.vendorlist;  

    console.log(this.dataForAnnouncment.emaillist);
    console.log(this.dataForAnnouncment.vendorlist);
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

  retrieveVendorSuggestions(biddingId: number) {
    this.loadingVendors = true;
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

  retrieveEmailList() {
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

  private getDataForAnnouncment() {
    this.commonService(`/api/c-announcementemaillist/${this.value}`)
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
        this.dataForAnnouncment = res.data;
        console.log("dataForAnnouncment",this.dataForAnnouncment);

      });
  }

  handleSelectionChange(val) {
    this.multipleSelection = val;
  }



  back() {
    this.$emit("back")
      ;
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

  handlePreview() {
    window.open(this.file.response.downloadUri, '_blank');
  }

  cancelAttachment() {
    this.attachmentName = null;
    this.Announcment.attachmentId = null;
    this.Announcment.attachment = null;
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
  
  openRecipientList() {
    this.recipientListVisible = true;
  }

  publish() {
    if (this.multipleSelection.length == 0) {
      this.$message.error('Please select at least one recipient');
    } else {
      console.log('Publishing emails', this.formData);
      this.recipientListVisible = false;
    }
  }

  saveAsDraft() {
    (<ElForm>this.$refs.mainForm).validate((passed, errors) => {
      if (passed) {
        this.loading = true;
        this.commonService(baseApiAnnouncement)
          .create(this.formData)
          .then(res => {
            this.formData = res;
            this.$message.success('Announcement has been saved successfully');
          })
          .catch(_err => this.$message.error('Failed to save the announcement'))
          .finally(() => this.loading = false);
      }
    })
  }

  saveAttachment() {
    this.attachmentName = this.formData.attachment.fileName;
    this.attachmentFormVisible = false;
  }
}
