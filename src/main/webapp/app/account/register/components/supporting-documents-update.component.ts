import Vue from 'vue';
import Component from 'vue-class-component';
import { ElForm } from 'element-ui/types/form';
import { Inject } from 'vue-property-decorator';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import DocumentTypeService from '@/entities/document-type/document-type.service';
import { SupportingDocument } from '@/shared/model/supporting-document.model';

const SupportingDocumentsProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    mandatory: Boolean, // Whether or not the current form is mandatory supporting document.
    document: {
      type: Object,
      default: () => {
        return new SupportingDocument();
      }
    }
  }
});

@Component
export default class SupportingDocumentsUpdate extends SupportingDocumentsProps {
  @Inject('documentTypeService')
  private documentTypeService: () => DocumentTypeService;

  public rules = {};
  public hasExpirationDate = false;

  mounted() {
    this.eventBus.$on('save-document', this.save);
    this.eventBus.$on('reset-document-form', this.reset);
  }

  beforeDestroy() {
    this.eventBus.$off('save-document', this.save);
    this.eventBus.$off('reset-document-form', this.reset);
  }

  get documentTypes() {
    return this.mandatory ? registrationStore.mandatoryDocumentTypes : registrationStore.additionalDocumentTypes;
  }

  public handleDocumentTypeChange(id: number) {
    this.documentTypeService()
      .find(id)
      .then(res => {
        this.document.typeName = res.name;
        this.hasExpirationDate = res.hasExpirationDate;
      });
  }

  private reset() {
    (<ElForm>this.$refs.document).resetFields();
  }

  private save() {
    (<ElForm>this.$refs.document).validate((passed, errors) => {
      if (passed) {
        setTimeout(() => {
          const data = { ...this.document };
          this.eventBus.$emit('push-document', data);
          this.reset();
        }, 1000);
      } else {
        this.eventBus.$emit('document-validation-failed', { passed, errors });
      }
    });
  }
}
