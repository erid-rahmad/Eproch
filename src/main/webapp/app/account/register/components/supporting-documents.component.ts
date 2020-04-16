import Vue from 'vue'
import Component from 'vue-class-component'
import SupportingDocumentsUpdate from './supporting-documents-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'

const DocumentProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    mainDocuments: {
      type: Array,
      default: () => []
    },
    additionalDocuments: {
        type: Array,
        default: () => []
    }
  }
})

@Component({
    components: {
        SupportingDocumentsUpdate
    }
})
export default class SupportingDocuments extends DocumentProps {
    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    editingForm = null;
    errors = {
        mainDocuments: null
    }

    mounted() {
        this.eventBus.$on('validate-form', this.validate);
        this.eventBus.$on('document-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$on('push-document', this.pushDocument);
    }

    beforeDestroy() {
        this.eventBus.$off('validate-form', this.validate);
        this.eventBus.$off('document-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$off('push-document', this.pushDocument);
        (<Vue>this.$refs.dialogBody)?.$destroy();
    }

    get shouldFillMandatoryDocuments() {
        return registrationStore.mandatoryDocumentTypes.length > 0;
    }

    get hasErrors() {
        return this.errors.mainDocuments !== null;
    }

    public addDocument(target: string) {
        this.editingForm = target;
        this.editDialogVisible = true;
    }

    public saveDocument() {
        this.loading = true;
        this.eventBus.$emit('save-document');
    }

    public hideDialog() {
        this.eventBus.$emit('reset-document-form');
        this.editDialogVisible = false;
    }

    private hideLoadingIndicator() {
        this.loading = false;
    }

    private pushDocument(document) {
        this[this.editingForm].push(document);
        if (this.mainDocuments.length === registrationStore.mandatoryDocumentTypes.length) {
            this.errors.mainDocuments = null;
        }
        this.loading = false;
        this.editDialogVisible = false;
    }

    private validate(formIndex: number) {
        if (formIndex === 3) {
            let passed = true;

            if (this.mainDocuments.length < registrationStore.mandatoryDocumentTypes.length) {
                const types = registrationStore.mandatoryDocumentTypes
                    .map(item => item.name)
                    .join(', ');
                passed = false;
                this.errors.mainDocuments = `Document of the following types are required: ${types}`;
            } else {
                this.errors.mainDocuments = null;
            }
            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }
}