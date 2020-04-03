import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import DocumentTypeService from '../document-type/document-type.service';
import { IDocumentType } from '@/shared/model/document-type.model';

import VendorService from '../vendor/vendor.service';
import { IVendor } from '@/shared/model/vendor.model';

import AlertService from '@/shared/alert/alert.service';
import { ISupportingDocument, SupportingDocument } from '@/shared/model/supporting-document.model';
import SupportingDocumentService from './supporting-document.service';

const validations: any = {
  supportingDocument: {
    documentNo: {
      required
    },
    expirationDate: {},
    file: {},
    typeId: {
      required
    }
  }
};

@Component({
  validations
})
export default class SupportingDocumentUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('supportingDocumentService') private supportingDocumentService: () => SupportingDocumentService;
  public supportingDocument: ISupportingDocument = new SupportingDocument();

  @Inject('documentTypeService') private documentTypeService: () => DocumentTypeService;

  public documentTypes: IDocumentType[] = [];

  @Inject('vendorService') private vendorService: () => VendorService;

  public vendors: IVendor[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.supportingDocumentId) {
        vm.retrieveSupportingDocument(to.params.supportingDocumentId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.supportingDocument.id) {
      this.supportingDocumentService()
        .update(this.supportingDocument)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.supportingDocument.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.supportingDocumentService()
        .create(this.supportingDocument)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('opusWebApp.supportingDocument.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSupportingDocument(supportingDocumentId): void {
    this.supportingDocumentService()
      .find(supportingDocumentId)
      .then(res => {
        this.supportingDocument = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.documentTypeService()
      .retrieve()
      .then(res => {
        this.documentTypes = res.data;
      });
    this.vendorService()
      .retrieve()
      .then(res => {
        this.vendors = res.data;
      });
  }
}
