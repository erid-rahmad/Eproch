import AlertMixin from '@/shared/alert/alert.mixin';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../../ContextVariableAccessor";

const UploadVideoProp = Vue.extend({
  props: {
    productVideos: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
})
@Component
export default class ProductVideo extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, UploadVideoProp) {

  private limit: number = 1;
  private action: string = "/api/c-attachments/upload";
  private accept: string = ".mp4, .mp3";
  private downloadUri = "";

  created() {
    this.productVideos.videoId = "";
  }

  get fileList() {
    if ( ! this.productVideos.video) return [];
    return [this.productVideos.video];
  }

  onUploadChange(file: any) {
    this.productVideos.video = file;
  }

  handlePreview(file) {
    window.open(file.response.downloadUri, '_blank');
    console.log(file);
  }

  handleRemove(files, fileList) {
    this.productVideos.video = "";
    this.productVideos.videoId = "";
    console.log(files, fileList)
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response: any) {
      console.log('File uploaded successfully ', response);
      //this.downloadUri = response.downloadUri;
      this.productVideos.videoId = response.attachment.id;
      //(this.$refs.company as ElForm).clearValidate('file');
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
      return !isExt;
    }

  }

}
