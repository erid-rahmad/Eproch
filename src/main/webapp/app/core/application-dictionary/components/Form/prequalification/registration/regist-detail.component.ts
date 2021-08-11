import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import AlertMixin from "@/shared/alert/alert.mixin";
import { mixins } from "vue-class-component";
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import { Component, Inject, Vue, Watch } from "vue-property-decorator";
import formatDuration from 'date-fns/formatDuration';
import intervalToDuration from 'date-fns/intervalToDuration';
import DynamicWindowService from "../../../DynamicWindow/dynamic-window.service";
import settings from '@/settings';

const RegistDetailProp = Vue.extend({
  props: {
    data: Object,
  }
})

@Component({

})
export default class RegistDetail extends mixins(Vue2Filters.mixin, AccessLevelMixin, AlertMixin, RegistDetailProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  mainForm: any;
  private currentDate = new Date();
  showDialog: boolean = false;
  accButton: boolean = false;

  private action: string = "/api/c-attachments/upload";
  private accept: string = ".jpg, .jpeg, .png, .pdf";

  created() {
    this.mainForm = this.data;
    this.accButton = this.data.registrationStatus !== "R" || !(this.currentDate >= new Date(this.mainForm.announcementEndDate))
  }

  uploadModels=[
    {name: 'SIUP', file: {}},
    {name: 'SPDA', file: {}}
  ]

  get timeRemaining() {
    if (!this.mainForm.announcementEndDate) {
      return '';
    }

    if (this.currentDate >= new Date(this.mainForm.announcementEndDate)) {
      return 'Event has been ended';
    }

    const duration = intervalToDuration({
      start: this.currentDate,
      end: new Date(this.mainForm.announcementEndDate)
    });

    return formatDuration(duration, {
      format: ['days', 'hours', 'minutes']
    });
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  minatAction() {
    this.showDialog = true;
  }

  onUploadChange(file: any) {
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
  }

  handleRemove(files, fileList, row) {
    row.file = {};
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

  onUploadSuccess(response: any, file, row) {
      console.log('File uploaded successfully ', response);
      row.file = response.attachment;

      let registerable = true;
      
      console.log(this.uploadModels);
      this.uploadModels.forEach((item)=>{
        registerable = registerable&&(!!(<any>item.file).id)
      });
      

      this.accButton = registerable;
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
      return false;
    }

  }

  register(){
    let data = {
      adOrganizationId: this.mainForm.adOrganizationId,
      registrationId: this.mainForm.id,
      siupDocumentId: (<any>this.uploadModels[0].file).id,
      spdaDocumentId: (<any>this.uploadModels[1].file).id
    }

    this.commonService("/api/m-preq-regist-documents").create(data).then((res)=>{
      this.$message.success("Successfully registered into the prequalification.");
      this.showDialog = false;
    }).catch((err)=>{
      console.log(err);
      this.$message.success("Error while registering.");
    })
  }
}