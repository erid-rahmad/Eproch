import { Component, Inject,Watch } from "vue-property-decorator";
import { Editor, EditorContent } from '@tiptap/vue-2'
import { defaultExtensions } from '@tiptap/starter-kit'
import MenuItem from './MenuItem.vue'
import tiptap from './Vue/index.vue'
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AccountService from '@/account/account.service';

@Component({
  components: {
    tiptap,
  }
})
export default class AddAnnouncementForm extends mixins (Vue2Filters.mixin, AlertMixin,EditorContent,AccessLevelMixin) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('dynamicWindowService')
  private pushService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('accountService')
  private accountService: () => AccountService;

  dialogTableVisible = false;
  dialogTableVisible11 = false;

  public emailFromChild: any = {};
  public Announcment: any = {};
  private attachmetName = '';
  private fileAttacment: any = {};

  public biddingData: any = {};
  public value: any = {};
  public itemname: any = {};
  public emailList: any = {};
  praSentPA = false;
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";
  projectFormVisible = false;
  private action: string = "/api/c-attachments/upload"  
  private limit: number = 1;
  private dataRekanan: any = {};
  private dataForAnnouncment: any = {};

  multipleSelection= [];

  editor = null;




  // #########################################################################SERVICE########################################################

  mounted() {
    console.log("mail from child", this.emailFromChild);
    this.retrieveBidding();
    this.value = null;
    
  }

  @Watch('value')
  onDataChanged(data: any) {
    console.log('Value:', data);
    this.getDataForAnnouncment();
  }

  @Watch('dataForAnnouncment')
  onDataChang(data: any) {
    console.log('Value:', data);
    this.changedata();
  }

  changedata() {
    this.emailList = this.dataForAnnouncment.emaillist;
    this.dataRekanan = this.dataForAnnouncment.vendorlist;  

    console.log(this.dataForAnnouncment.emaillist);
    console.log(this.dataForAnnouncment.vendorlist);
    
    
    
  }

  private retrieveBidding() {
    this.commonService('/api/m-biddings')
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
        console.log("biding data",this.biddingData);
      });
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

  private pushAnnouncement() {
    this.pushService('/api/c-announcements')
      .create(this.Announcment);
  }

    handleSelectionChange(val) {
      this.multipleSelection = val;
  }

  beforeDestroy() {
    this.editor.destroy()
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
    window.open(this.fileAttacment.response.downloadUri, '_blank');
  }

  cancelAtachment() {
    this.attachmetName = null;
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
    this.Announcment.attachment = response.attachment;
    this.Announcment.attachmentId = response.attachment.id;
    this.fileAttacment = file;
    

}

  handleRemove(file, fileList) {
    console.log(file, fileList);

  }
  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }


  get projectDocUploadHeaders() {
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
    this.attachmetName = this.Announcment.attachment.fileName;
    this.projectFormVisible = false;
  }
  
  praSent() {
    
    console.log(this.value);
    this.praSentPA = true;
    // this.viewEmailList();
    console.log("this announsment", this.Announcment);
  }

  sent() {
    if (this.multipleSelection.length == 0) {
      this.$notify.warning({
        title: 'Warning',
        message: "Email Can't Empty",
        offset: 100
      })
  }
  else {
      console.log(this.multipleSelection);
      this.Announcment.description = "<p><br>Kepada Bapak/Ibu Pimpinan <br>#VendorName <br>Hal: Undangan #TenderName <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>";
      this.Announcment.adOrganizationId = 1;
      this.Announcment.biddingId = this.value;
      this.Announcment.active = true;
      this.Announcment.emaillist = this.multipleSelection;
      this.pushAnnouncement();
      console.log("itemname",this.itemname);
      


      this.$notify.success({
        title: 'Success',
        message: 'Send Email',
        offset: 100
      });
      this.praSentPA = false;
    }
  }
}
