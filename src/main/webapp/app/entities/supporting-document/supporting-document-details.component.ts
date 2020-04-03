import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ISupportingDocument } from '@/shared/model/supporting-document.model';
import SupportingDocumentService from './supporting-document.service';

@Component
export default class SupportingDocumentDetails extends mixins(JhiDataUtils) {
  @Inject('supportingDocumentService') private supportingDocumentService: () => SupportingDocumentService;
  public supportingDocument: ISupportingDocument = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.supportingDocumentId) {
        vm.retrieveSupportingDocument(to.params.supportingDocumentId);
      }
    });
  }

  public retrieveSupportingDocument(supportingDocumentId) {
    this.supportingDocumentService()
      .find(supportingDocumentId)
      .then(res => {
        this.supportingDocument = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
