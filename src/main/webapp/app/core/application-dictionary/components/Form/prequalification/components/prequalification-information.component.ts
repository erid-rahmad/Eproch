import AccountService from '@/account/account.service';
import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { ElForm } from 'element-ui/types/form';
//import { random } from 'lodash';
import Vue from 'vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { Component, Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';

const PrequalificationInfoProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component({
  components: {
    DocumentActionButton
  }
})
export default class BiddingInformation extends Mixins(AccessLevelMixin, PrequalificationInfoProp) {

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private updated = false;
  private recordsLoaded = true;
  private loadingReferenceNo = false;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 150,
    height: 150
  };

  fullscreenLoading = false;
  loading = false;

  public biddingTypeOptions: any[] = [
    {id: 'O', name:"Open"},
    {id: 'C', name:"Close"},
  ];

  fileList: any[] = [];

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .doc, .docx, .xls, .xlsx, .csv, .ppt, .pptx, .pdf";

  columnSpacing = 32;

  preq: Record<string, any> = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get projectDocUploadHeaders() {
    if (this.accountService().hasToken) {
      return {
        'Authorization': `Bearer ${this.accountService().token}`
      }
    }

    return {};
  }

  get readOnly() {
    console.log(this.preq);
    console.log(this.preq.status);
    console.log(this.preq.status === 'P');
    return this.preq.status === 'P';
  }

  @Watch('preq', { deep: true })
  onBiddingChanged(_bidding: Record<string, any>) {
    if (this.editMode && this.recordsLoaded && ! this.updated) {
      this.updated = true;
      this.$emit('change');
    }
  }

  created() {
    if (this.editMode) {
      this.recordsLoaded = false;
      this.preq = {...this.data};

      if(this.preq.attachmentId){
        this.fileList.push({ "name": this.preq.fileName, "url": this.preq.downloadUrl });
      }
    } else {
      this.preq.documentAction = 'APV';
      this.preq.documentStatus = 'SMT';
      this.preq.projectInformations = [];
      this.preq.removedProjectInformations = [];
    }

    this.preq.step = BiddingStep.INFO;
    
    if (!this.preq.adOrganizationId){
      this.preq.adOrganizationId = AccountStoreModule.organizationInfo.id;
    }
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  downloadAttachment(row){
    window.open(`/api/c-attachments/download/${row.attachment.id}-${row.attachment.fileName}`, '_blank');
  }

  handleRemove(file, fileList) {
    console.log(file, fileList);

  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response, file, fileList) {
    this.preq.attachment = response.attachment;
    this.preq.attachmentId = response.attachment.id;
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
  
  printFileName(attachment: any) {
    return attachment?.fileName;
  }

  /**
   * Invoked before proceeding to the next step.
   */
  save(changeStep: boolean) {
    (this.$refs.prequalificationInformation as ElForm).validate((passed, errors) => {
      if (passed) {
        let service = this.commonService('/api/m-prequalification-informations/save-form');

        service[this.editMode ? 'update' : 'create'](this.preq)
          .then(res => {
            if (! this.editMode) {
              this.preq.id = res.id;
              this.$set(this.preq, 'documentNo', res.documentNo);
            }

            this.$message.success('Prequalification Information has been saved successfully');
            this.$emit('saved', {
              data: res,
              changeStep
            });
          })
          .catch(err => {
            console.log('Failed to save prequalification info. %O', err);
            this.$message.error('Failed to save prequalification information');
            this.$emit('error')
          });
      } else {
        this.$emit('error', errors);
      }
    });
  }

  retrieveReferencedData(referenceNo?: string) {
    if (referenceNo) {
      this.loadingReferenceNo = true;

      this.commonService('/api/m-rfqs')
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            'documentStatus.equals=APV',
            //'processed.equals=true',
            //'approved.equals=true',
            `documentNo.equals=${referenceNo}`,
            'selectionMethod.equals=T'
          ]),
          paginationQuery: {
            page: 0,
            size: 1,
            sort: ['id']
          }
        })
        .then(res => {
          const data = res.data as any[];

          if (data.length) {
            const document = data[0];
            this.$set(this.preq, 'adOrganizationId', document.adOrganizationId);
            this.$set(this.preq, 'adOrganizationName', document.adOrganizationName);
            this.$set(this.preq, 'quotationId', document.id);
          } else {
            this.$message.warning('No document found for the given reference no.');
          }
        })
        .catch(err => {
          console.error('Failed to get reference. %O', err);
          this.$message.error(err.detail || err.message);
        })
        .finally(() => {
          this.loadingReferenceNo = false;
        });
    }
  }
}
