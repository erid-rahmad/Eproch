import AccountService from '@/account/account.service';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Inject } from 'vue-property-decorator';

const baseApiAttachment = 'api/c-attachments/upload';
const baseApiGallery = 'api/c-galleries';
const baseApiGalleryItem = 'api/c-gallery-items';

const ImgCatalogProp = Vue.extend({
  props: {
    galleryName: String,

    /**
     * This is the CGallery.id.
     */
    value: Number,
  }
})
@Component
export default class ProductImage extends mixins(ImgCatalogProp) {

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  dialogImageUrl: string = '';
  dialogVisible: boolean = false;
  disabled: boolean = false;

  private limit: number = 5;
  private accept: string = ".jpg, .jpeg, .png";
  private downloadUri = "";
  galleryImages: any = {};
  galleryImagesItem: any = {};
  productImages: any = {};
  filterQuery: string = '';

  uploadedFiles: any[] = [];

  get uploadUrl() {
    return baseApiAttachment;
  }

  get fileList() {
    return [];
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
    if (this.value) {
      this.retrieveGalleryItems(this.value);
    }
  }

  handlePictureCardPreview(file) {
    console.log('handlePictureCardPreview', file);
    this.dialogImageUrl = file.url;
    this.dialogVisible = true;
  }

  handleDownload(file) {
    console.log('handleDownload', file);
    window.open(file.response.downloadUri, '_blank');
  }

  handlePreview(file) {
    this.dialogImageUrl = file.url;
    this.dialogVisible = true;
  }

  handleRemove(file, fileList) {
    console.log('handleRemove file: %O, fileList: %O', file, fileList);
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file ', err);
  }

  async onUploadSuccess(response, _file, fileList) {
    let galleryId = this.value;

    if ( ! galleryId) {
      galleryId = await this.createGallery();
    }

    const attachment = response.attachment;
    
    this.createGalleryItem(galleryId, attachment.id, fileList.length)
      .then(() => this.uploadedFiles.push({
        name: attachment.fileName,
        url: response.downloadUrl
      }));
  }

  private retrieveGalleryItems(galleryId: number) {
    this.commonService(baseApiGalleryItem)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `cGalleryId.equals=${galleryId}`
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        const list = res.data as any[];
        this.uploadedFiles = list.map(item => ({
          id: item.id,
          attachmentId: item.cAttachmentId,
          name: item.cAttachmentName,
          url: item.cAttachmentUrl
        }));
      })
  }

  private createGallery(): Promise<number> {
    return new Promise((resolve, reject) => {
      const data = {
        name: this.galleryName,
        adOrganizationId: accountStore.organizationInfo.id,
        active: true
      }

      this.commonService(baseApiGallery)
      .create(data)
      .then(res => {
        this.$emit('input', res.id);
        resolve(res.id);
      })
      .catch(err => {
        this.$message.error('Failed to create a gallery');
        reject(err);
      });
    });
	}

	createGalleryItem(cGalleryId: number, cAttachmentId: number, sequence: number): Promise<number> {
    return new Promise((resolve, reject) => {
      const data = {
        active: true,
        adOrganizationId: accountStore.organizationInfo.id,
        cGalleryId,
        cAttachmentId,
        sequence,
        type: 'IMAGE',
        preview: sequence === 1
      };

      this.commonService(baseApiGalleryItem)
      .create(data)
      .then(res => {
        this.$message.success('Gallery item has been uploaded successfully');
        resolve(res.id)
      })
      .catch(err => {
        console.log('Failed to upload the gallery item', err);
        this.$message.error('Failed to upload the gallery item');
        reject(err);
      });
    });
  }

  private removeGallery(id: number) {
    this.commonService(baseApiGallery)
      .delete(id)
      .then(() => {
        console.log("Remove Gallery Success");
      })
      .catch(err => {
        console.log('Failed to remove the gallery', err);
        this.$message.warning('Failed to remove the gallery');
      })
      .finally(() => this.$emit('input', null));
	}

	private removeGalleryItem(id: number){
    this.commonService(baseApiGalleryItem)
    .delete(id)
    .then(() => {
      console.log("Remove Gallery Success");
    })
    .catch(err => {
      console.log('Failed to remove the gallery item', err);
      this.$message.error('Failed to remove the gallery item');
    });
  }

  handleExceed(files) {
    if (files.length >= 1) {
      this.$message.error(`Up to ${this.limit} files are allowed`);
      return false;
    }
  }

  onBeforeUpload(file: any) {
    // File size limitation
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      this.$message.error(`File size cannot exceed 5Mb`);
      return false;
    }

    // File type restriction
    const name = file.name ? file.name : '';
    const ext = name
      ? name.substr(name.lastIndexOf('.') + 1, name.length)
      : true;
    const isExt = this.accept.indexOf(ext) < 0;
    if (isExt) {
      this.$message.error('Please upload the correct format type');
      return false;
    }

  }

}
