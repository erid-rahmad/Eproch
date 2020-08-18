import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject, Watch } from 'vue-property-decorator'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
//import DocumentTypeService from '@/entities/document-type/document-type.service'
//import { SupportingDocument } from '@/shared/model/supporting-document.model'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { IADField } from '@/shared/model/ad-field.model';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { ADColumnType } from '@/shared/model/ad-column.model';
import pluralize from 'pluralize'
import { kebabCase } from 'lodash'

const SupportingDocumentsProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => {}
        },
        mandatory: Boolean, // Whether or not the current form is mandatory supporting document.
        doc: {
            type: Object,
            default() {
                return {};
            }
        },
    }
})

@Component
export default class SupportingDocumentsUpdate extends SupportingDocumentsProps {

    @Inject('dynamicWindowService')
    private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

    public rules = {};
    public document:any = {};
    public docTypeName = null;
    public hasExpirationDate = false;

    listReleatedDocumentTypes = [];
    listAllDocumentTypes = [];
    listDocumentTypesById = [];
    
    private activeTableDirectField: any = null;
    
    @Watch('doc')
    setDocument(document){
        this.document = document;
    }

    created(){
        this.setDocument(this.doc);
    }
    
    mounted() {
        this.eventBus.$on('save-document', this.save);
        this.eventBus.$on('reset-document-form', this.reset);
    }

    beforeDestroy() {
        this.eventBus.$off('save-document', this.save);
        this.eventBus.$off('reset-document-form', this.reset);
    }

    get documentTypes() {
        
        //let docTypes = this.mandatory ? registrationStore.mandatoryDocumentTypes : registrationStore.additionalDocumentTypes;
        //console.log(this.listAllDocumentTypes);
        //console.log(this.listReleatedDocumentTypes);

        return [...[], ...this.listAllDocumentTypes, ...this.listReleatedDocumentTypes]
    }     
    
    public handleDocumentTypeChange(id: number) {
        this.dynamicWindowService('/api/c-registration-doc-types')
            .retrieve({
                criteriaQuery: `id.equals=${id}`
            })
            .then(res => {
                
                this.document.typeName = res.data[0].name;
                this.hasExpirationDate = res.data[0].hasExpirationDate;
                
            });
    }

    private reset() {
        this.listReleatedDocumentTypes = [];
        (<ElForm>this.$refs.document).resetFields();
        this.document = {};
    }

    private save() {
        (<ElForm>this.$refs.document).validate((passed, errors) => {
            if (passed) {
                const data = { ...this.document };
                
                this.eventBus.$emit('push-document', data);
                this.reset();
            } else {
                this.eventBus.$emit('document-validation-failed', {passed, errors});
            }
        });
    }

    get fileList() {        
        if ( ! this.document.file) return [];
        return [this.document.file];
    }

    onUploadChange(file: any) {
        this.document.file = file;
    }

    onUploadError(err: any) {
        console.log('Failed uploading a file ', err);
    }

    onUploadSuccess(response: any) {
        console.log('File uploaded successfully ', response);
        this.document.fileId = response.attachment.id;
    }
}