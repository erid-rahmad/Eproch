import { ElUpload } from 'element-ui/types/upload';
import { Component, Vue } from 'vue-property-decorator';
import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";

@Component
export default class BhinnekaCatalogImporter extends Vue {

  catalog: Record<string, any> = {
    file: null,
    preview: []
  };

  get uploadHeaders() {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    return {Authorization: `Bearer ${token}`};
  }

  cacheFile(file: File) {
    console.log('file type: %s', file.type);
    const reader = new FileReader();
    
    reader.onload = e => {
      const result = JSON.parse(e.target.result as string);
      const products = result.data?.filter?.result;
      
      this.$set(this.catalog, 'preview', products || []);
      
      if (products) {
        for (const product of products) {
          marketplaceStore.addProduct(product);
        }
      }
    }
    reader.readAsText(file);
    return false;
  }

  onUploadError(err: any) {
    console.log('Failed uploading a file.', err);
  }

  onUploadSuccess(response: any) {
    console.log('File has been successfully uploaded.', response);
  }

  uploadFile() {
    (<ElUpload>this.$refs.uploader).submit();
  }

}