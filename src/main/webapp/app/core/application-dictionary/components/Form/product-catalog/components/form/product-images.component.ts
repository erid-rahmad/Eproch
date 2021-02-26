import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../../ContextVariableAccessor";
import Vue from 'vue';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

const ImgCatalogProp = Vue.extend({
  props: {
    setImgCatalog: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
})
@Component
export default class ProductImage extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, ImgCatalogProp) {
  dialogImageUrl: string = '';
  dialogVisible: boolean = false;
  disabled: boolean = false;

  private limit: number = 5;
  private action: string = "/api/c-attachments/upload";
	private baseApiUrlGallery: string = "/api/c-galleries";
	private baseApiUrlGalleryItem: string = "/api/c-gallery-items";
  private accept: string = ".jpg, .jpeg, .png";
  private downloadUri = "";
  galleryImages: any = {};
  galleryImagesItem: any = {};
  productImages: any = {};
  filterQuery: string = '';

  created() {
    //console.log(accountStore.userDetails);
    //console.log(accountStore.userIdentity);
    //console.log(this.setImgCatalog);
  }

  handlePictureCardPreview(file) {
    console.log(file);
    this.dialogImageUrl = file.url;
    this.dialogVisible = true;
  }

  handleDownload(file) {
    console.log(file);
    window.open(file.response.downloadUri, '_blank');
  }

  handlePreview(file) {
    this.dialogImageUrl = file.url;
    this.dialogVisible = true;
  }

  handleRemove(file, fileList) {
    console.log(file, fileList);
    if(file.response){
      this.retrieveGalleryItem(file.response.attachment.id, fileList.length);
    }else{
      this.retrieveGalleryItem(file.cAttachment.id, fileList.length);
    }
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  onUploadSuccess(response, file, fileList) {
      console.log('File uploaded successfully ', response);
      console.log(file);
      console.log(fileList);

      if(fileList.length==1){
        this.createGallery();
        this.galleryImagesItem.preview = true;
      }else{
        this.createGalleryItem();
        this.galleryImagesItem.preview = false;
      }
      this.galleryImagesItem.cAttachmentId = response.attachment.id;
      this.galleryImagesItem.sequence = fileList.length;
  }

  retrieveGalleryItem(id, length) {
    this.filterQuery = "cAttachmentId.equals="+id;
    this.dynamicWindowService(this.baseApiUrlGalleryItem)
      .retrieve({
        criteriaQuery: this.filterQuery,
      })
      .then(res => {
        //console.log(res);

        res.data.map((item: any) => {
          //console.log("retrieve gallery item "+item);

          if(length == 0){
            this.removeGallery(item.cGalleryId);
            this.removeGalleryItem(item.id);
          } else {
            this.removeGalleryItem(item.id);
          }

          return item;
        });
      });
  }

  createGallery(){
    this.galleryImages = {
      name: accountStore.userDetails.name,
      description: "",
      adOrganizationId: 1,
      active: true
    }

    this.dynamicWindowService(this.baseApiUrlGallery)
    .create(this.galleryImages)
    .then((response) => {

      this.galleryImagesItem.cGalleryId = response.id;
      this.$emit('galleryImage', response.id);
      this.createGalleryItem();

    }).catch(error => {
      this.$notify({
        title: 'Error',
        message: error,
        type: 'error',
      });
    });
	}

	createGalleryItem(){
    this.galleryImagesItem.type = "IMAGE";
    this.galleryImagesItem.adOrganizationId = 1;
    this.galleryImagesItem.active = true;
    if(this.galleryImagesItem.cGalleryId == null){
      this.galleryImagesItem.cGalleryId = this.setImgCatalog.id;
      console.log("= = = = = "+this.setImgCatalog.id);
      console.log(this.galleryImagesItem);
    }

    this.dynamicWindowService(this.baseApiUrlGalleryItem)
    .create(this.galleryImagesItem)
    .then((response) => {

      //console.log(response);
      //console.log(this.galleryImagesItem);

    }).catch(error => {
      this.$notify({
        title: 'Error',
        message: error,
        type: 'error',
      });
    });
  }

  removeGallery(galleryId){
    this.dynamicWindowService(this.baseApiUrlGallery)
    .delete(galleryId)
    .then(() => {
      console.log("Remove Gallery Success");
    }).catch(error => {
      this.$notify({
        title: 'Error',
        message: error,
        type: 'error',
      });
    });
	}

	removeGalleryItem(galleryItemId){
    this.dynamicWindowService(this.baseApiUrlGalleryItem)
    .delete(galleryItemId)
    .then(() => {
      console.log("Remove Gallery Success");
    }).catch(error => {
      this.$notify({
        title: 'Error',
        message: error,
        type: 'error',
      });
    });
  }

  handleExceed(files, fileList) {
    if (files.length >= 1) {
      this.$notify({
        title: 'Warning',
        message: "Up to 5 files are allowed",
        type: 'warning',
        duration: 3000
      });
      return false;
    }
  }

  get fileList() {
    if ( !this.setImgCatalog) return [];
    if ( !this.setImgCatalog.cGalleryItems) return [];

    let images = this.setImgCatalog.cGalleryItems;

    let img;
    for(var i=0; i<images.length; i++){
      img = this.setImgCatalog.cGalleryItems;
      img[i].url = `/api/c-attachments/download/${img[i].cAttachment.id}-${img[i].cAttachment.fileName}`;
    }

    return img;
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
