import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDocumentTypeBusinessCategory } from '@/shared/model/document-type-business-category.model';
import DocumentTypeBusinessCategoryService from './document-type-business-category.service';

@Component
export default class DocumentTypeBusinessCategoryDetails extends Vue {
  @Inject('documentTypeBusinessCategoryService') private documentTypeBusinessCategoryService: () => DocumentTypeBusinessCategoryService;
  public documentTypeBusinessCategory: IDocumentTypeBusinessCategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentTypeBusinessCategoryId) {
        vm.retrieveDocumentTypeBusinessCategory(to.params.documentTypeBusinessCategoryId);
      }
    });
  }

  public retrieveDocumentTypeBusinessCategory(documentTypeBusinessCategoryId) {
    this.documentTypeBusinessCategoryService()
      .find(documentTypeBusinessCategoryId)
      .then(res => {
        this.documentTypeBusinessCategory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
