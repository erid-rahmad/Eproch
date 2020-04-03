import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDocumentType } from '@/shared/model/document-type.model';
import DocumentTypeService from './document-type.service';

@Component
export default class DocumentTypeDetails extends Vue {
  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;
  public documentType: IDocumentType = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.documentTypeId) {
        vm.retrieveDocumentType(to.params.documentTypeId);
      }
    });
  }

  public retrieveDocumentType(documentTypeId) {
    this.documentTypeService()
      .find(documentTypeId)
      .then(res => {
        this.documentType = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
