import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject } from 'vue-property-decorator'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DocumentTypeService from '@/entities/document-type/document-type.service'
import { SupportingDocument } from '@/shared/model/supporting-document.model'

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
})

@Component
export default class SupportingDocumentsForm extends SupportingDocumentsProps {
    @Inject('documentTypeService')
    private documentTypeService: () => DocumentTypeService;

    private documentTypes = [];
    private rules = {};
    private hasExpirationDate = false;

    mounted() {
        this.eventBus.$on('save-document', this.save);
        this.eventBus.$on('document-entry-open', () => {
            this.retrieveMandatoryDocumentTypes();
        });
    }

    save() {
        setTimeout(() => {
            const data = { ...this.document };
            this.eventBus.$emit('push-document', data);
            (<ElForm>this.$refs.document).resetFields();
        }, 1000);
    }

    public handleDocumentTypeChange(id: number) {
        this.documentTypeService()
            .find(id)
            .then(res => {
                this.hasExpirationDate = res.hasExpirationDate;
            });
    }

    private async retrieveMandatoryDocumentTypes() {
        await registrationStore.addBusinessCategory(1205);
        await registrationStore.addBusinessCategory(1206);
        const businessCategories = Array.from(registrationStore.businessCategories);
        this.documentTypeService()
            .retrieveWithFilter(
                [`mandatory=${this.mandatory}`]
                .concat(businessCategories.map(id => `businessCategoryIds=${id}`))
            )
            .then(res => {
                this.documentTypes = res.data;
            })
    }
}