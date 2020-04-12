import Vue from 'vue'
import Component from 'vue-class-component'
import SupportingDocumentsForm from './supporting-documents-form.vue';

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
        SupportingDocumentsForm
    }
})
export default class SupportingDocuments extends DocumentProps {
    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    document = {};
    editingForm = null;

    mounted() {
        this.eventBus.$on('push-document', (document) => {
            this[this.editingForm].push(document);
            this.loading = false;
            this.editDialogVisible = false;
        });
    }

    addDocument(target: string) {
        this.editingForm = target;
        this.editDialogVisible = true;
    }

    saveDocument() {
        this.loading = true;
        this.eventBus.$emit('save-document');
    }

    private handleDialogOpen() {
        this.eventBus.$emit('document-entry-open');
    }
}