import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject, Watch } from 'vue-property-decorator'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { required } from 'vuelidate/lib/validators'

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

    public rules = {
        //file: {
            //required
        //}
    };
    private limit: number = 1;
    private action: string = "/api/c-attachments/upload";
    private accept: string = ".jpg, .jpeg, .png, .pdf, .doc, .docx";
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

    handleRemove(files, fileList) {
        this.document.file = "";
    }

    onUploadError(err: any) {
        console.log('Failed uploading a file ', err);
    }

    onUploadSuccess(response: any) {
        console.log('File uploaded successfully ', response);
        this.document.fileId = response.attachment.id;
    }

    handleExceed(files, fileList) {
        if (fileList.length >= 1) {
            this.$notify({
                title: 'Warning',
                message: "The limit file is 1",
                type: 'warning',
                duration: 3000
            });
            return false;
        }
    }

    handleBeforeUpload(file: any) {
        // File size limitation
        const isLt5M = file.size / 1024 / 1024 < 5;
        if (!isLt5M) {
          this.$notify({
              title: 'Warning',
              message: "files with a size less than 5Mb",
              type: 'warning',
              duration: 3000
          });
          return isLt5M;
        }
    
        // File type restriction
        const name = file.name ? file.name : '';
        const ext = name
          ? name.substr(name.lastIndexOf('.') + 1, name.length)
          : true;
        const isExt = this.accept.indexOf(ext) < 0;
        if (isExt) {
          this.$notify({
            title: 'Warning',
            message: "Please upload the correct format type",
            type: 'warning',
            duration: 3000
          });
          return !isExt;
        }
    }

}