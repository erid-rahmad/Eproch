import Vue from 'vue';
import Component from 'vue-class-component';
//import SupportingDocumentsForm from './supporting-documents-form.vue';
import SupportingDocumentsUpdate from './supporting-documents-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Inject } from 'vue-property-decorator';

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
    },
    
  }
})

@Component({
    components: {
        SupportingDocumentsUpdate
    }
})
export default class SupportingDocuments extends DocumentProps {

    @Inject('dynamicWindowService')
    private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    doc = {};
    editingForm = null;
    errors = {
        mainDocuments: null,
        type: 'info'
    }

    mounted() {
        this.eventBus.$on('validate-form', this.validate);
        this.eventBus.$on('document-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$on('push-document', this.pushDocument);
        
        this.getAllDocumentType(true, 1);
        this.getLoopingReleatedDocumentType(true, 1);
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
        this.errors.type = 'info';
        return this.errors.mainDocuments !== null;
    }

    public addDocument(target: string) {
        this.editingForm = target;
        this.editDialogVisible = true;
    }

    private getLoopingReleatedDocumentType(mandatory: boolean, run: number){
        let docTypes = mandatory ? registrationStore.mandatoryDocumentTypes : registrationStore.additionalDocumentTypes;
        for(let i in docTypes){
            this.getReleatedDocumentType(docTypes[i].documentTypeId, run);
        }
    }
    private getReleatedDocumentType(id: number, run: number){
        this.dynamicWindowService('/api/c-registration-doc-types')
            .retrieve({
                criteriaQuery: `id.equals=${id}`
            })
            .then(res => {
                if(run===1){
                    for(let i in res.data){
                        this.mainDocuments.push({
                            'typeId': res.data[i].id,
                            'typeName': res.data[i].name
                        });
                    }
                }else{
                    (<any>this.$refs.dialogBody).listReleatedDocumentTypes = res.data;
                }
            });
    }

    private getAllDocumentType(mandatory: boolean, run: number){
        let query;
        if(mandatory){
            query = `mandatoryBusinessCategories.equals=ALL`;
        }else{
            query = `additionalBusinessCategories.equals=ALL`;
        }
        this.dynamicWindowService('/api/c-registration-doc-types')
            .retrieve({
                criteriaQuery: query
            })
            .then(res => {
                if(run===1){
                    for(let i in res.data){
                        this.mainDocuments.push({
                            'typeId': res.data[i].id,
                            'typeName': res.data[i].name
                        });
                    }
                }else{
                    (<any>this.$refs.dialogBody).listAllDocumentTypes = res.data;
                }
            });
    }

    public saveDocument() {
        this.loading = true;
        this.eventBus.$emit('save-document');
    }

    prepareRemove(target: string, index: string){
        this.editingForm = target;
        this[this.editingForm].splice(index, 1);
    }

    edit(rowDocument: any, target: string, index: string){
        this.editingForm = target;
        this.editDialogVisible = true;

        this.doc = {...rowDocument}
        this.$set(this.doc, 'index', index);
    }

    //private handleDialogOpen() {
    //    this.eventBus.$emit('document-entry-open');
    //}

    public hideDialog() {
        this.eventBus.$emit('reset-document-form');
        this.editDialogVisible = false;
    }

    private hideLoadingIndicator() {
        this.loading = false;
    }

    private pushDocument(document) {
        console.log(document);
        
        if(document.index !== undefined){
            this[this.editingForm].splice(document.index, 1, document);
        }else{
            this[this.editingForm].push(document);
        }

        if (this.mainDocuments.length === registrationStore.mandatoryDocumentTypes.length) {
            this.errors.type = 'info';
            this.errors.mainDocuments = null;
        }
        
        this.loading = false;
        this.editDialogVisible = false;
    }

    private validate(formIndex: number) {
        if (formIndex === 3) {
            let passed = true;
            if (this.mainDocuments.length < registrationStore.mandatoryDocumentTypes.length) {
                /*
                const types = registrationStore.mandatoryDocumentTypes
                    .map(item => item.name)
                    .join(', ');
                */
                passed = false;
                this.errors.mainDocuments = `Document of the following document types are required: `;//${types}
                this.errors.type = 'error';
            } else {
                this.errors.mainDocuments = null;
                this.errors.type = 'info';
            }
            
            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }

}
