import AccountService from '@/account/account.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElForm } from 'element-ui/types/form';
import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';

const baseApiAttachmentUpload = 'api/c-attachments/upload';
const baseApiContractDocument = 'api/m-contract-documents';

const ContractDocumentProps = Vue.extend({
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
export default class ContractDocument extends Mixins(AccessLevelMixin, ContractDocumentProps) {

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  documentFormVisible: boolean = false;
  contractText='';
  contractTextVisible:boolean=false;

  documents: any[] = [];

   document: any = {
    adOrganizationId: null,
    name: null,
    // attachment: null,
    // attachmentId: null,
    // attachmentName: null,
    header:'',
    body:'',
    footer:'',
  };

  headerOption= [{
    value: 'Youve got to expect mistakes from anyone whos starting for the the first time -- in any profession, for that matter," Knapp told reporters. "And I m seeing very minimal same mistakes twice. So he learns quickly from his mistakes and that s impressive to see from a young guy. He a big-time student of the game',
    label: 'template 1'
  }, {
    value: 'Those of us who were so blessed to have known him, know that he would ve wanted even this moment to be a teachable one," Knapp s family said in a statement. "So this is it ... ve every day as if its your last, and love those around you like it wont last.',
    label: 'template 2'
  }, ]
  bodyOption= [{
    value: 'Knapp had been in the NFL since 1997 when he was hired by the San Francisco 49ers to be an offensive quality control coach. He was with the Niners through 2003 when he was offensive coordinator. He moved to the Atlanta Falcons to work with Michael Vick. He also worked with Matt Schaub in Houston as quarterbacks coach in 2010. He won Super Bowl 50 with the Denver Broncos in 2016.',
    label: 'template 1'
  }, {
    value: 'In total, Knapp worked for seven different teams in his career, including four as offensive coordinator. Before being hired by the Jets he was quarterbacks coach with the Falcons -- his second stint with the team -- for two seasons.',
    label: 'template 2'
  }, ]
  footerOption= [{
    value: 'He was a tremendous football coach who achieved at the highest levels of our game, but more importantly he was a wonderful person who had the love, admiration and respect of those who were blessed to work with him," Falcons owner Arthur Blank said in a statement.',
    label: 'template 1'
  }, {
    value: 'Knapp played football in college at Sacramento State and spent several years in the NFL as a practice squad and preseason player. He never played in a regular season game.',
    label: 'template 2'
  }, ]



  limit: number = 1;
  accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.data.documentStatus && this.data.documentStatus !== 'DRF';
  }

  get uploadApi() {
    return baseApiAttachmentUpload;
  }

  get uploadHeaders() {
    if (this.accountService().hasToken) {
      return {
        'Authorization': `Bearer ${this.accountService().token}`
      }
    }

    return {};
  }

  created() {
    this.retrieveDocuments(this.data.id);
  }

  private retrieveDocuments(contractId: number) {
      this.loading = true;
      this.commonService(baseApiContractDocument)
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `contractId.equals=${contractId}`
          ])
        })
        .then(res => {
          this.documents = res.data;
        })
        .catch(err => {
          console.log('Failed to get contract documents. %O', err);
          this.$message.error('Failed to get contract documents');
        })
        .finally(() => this.loading = false);
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  downloadAttachment(row: any){
    window.open(row.attachment.attachmentUrl, '_blank');
  }

  handleRemove(file, fileList) {
    console.log(file, fileList);

  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response) {
      this.document.attachment = response.attachment;
      this.document.attachmentId = response.attachment.id;
      this.document.attachmentName = response.attachment.fileName;
      this.document.attachmentUrl = response.downloadUri;
  }

  handleExceed(files) {
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
      return false;
    }
  }

  removeDocument(row) {
    this.commonService(baseApiContractDocument)
      .delete(row.id)
      .then(() => {
        this.$message.success('Document has been removed successfully');
        this.retrieveDocuments(this.data.id);
      });
  }

  addNew(){
    this.documentFormVisible = true
  }

  view(row){
    console.log("this row",row)
    this.contractText=
      '<p>'+row.header+'</p>'+
      '<p>'+row.body+'</p>'+
      '<p>'+row.footer+'</p>';
    this.contractTextVisible=true;

  }

  saveDocument() {
    // (this.$refs.documentForm as ElForm).validate(passed => {
    //   if (passed) {
    //     this.document.contractId = this.data.id;
    //     this.document.adOrganizationId = this.data.adOrganizationId;
    //
    //     this.commonService(baseApiContractDocument)
    //       .create(this.document)
    //       .then(() => {
    //         this.$message.success('Document has been created successfully');
    //         this.retrieveDocuments(this.data.id);
    //         this.documentFormVisible = false;
    //       })
    //       .catch(() => this.$message.error('Failed to create the document'));
    //   }
    // });
    // console.log("this document",document)
    this.documents.push(this.document);
    this.documentFormVisible = false;
  }
}
