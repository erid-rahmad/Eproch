import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject } from 'vue-property-decorator'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DocumentTypeService from '@/entities/document-type/document-type.service'
import BusinessCategoryService from '@/entities/business-category/business-category.service'
import { buildCascaderOptions } from '@/utils/form'
import { IBusinessCategory } from '@/shared/model/business-category.model'

const BusinessCategoriesProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => {}
        },
        businessCategories: {
            type: Array,
            default: () => {
                return [];
            }
        }
    }
})

@Component
export default class BusinessCategories extends BusinessCategoriesProps {
    @Inject('businessCategoryService')
    private businessCategoryService: () => BusinessCategoryService;

    @Inject('documentTypeService')
    private documentTypeService: () => DocumentTypeService;

    public businessCategory = { values: this.businessCategories };
    public rules = {
        values: {
            type: 'array',
            required: true,
            defaultFields: {type: 'array', len: 3, required: true},
            message: 'Business Category is required'
        }
    };
    public businessCategoryValues = []; 
    public businessCategoryOptions = [];

    created() {
        this.retrieveBusinessCategories();
    }

    mounted() {
        this.eventBus.$on('validate-form', this.validate);
    }

    private retrieveBusinessCategories() {
        this.businessCategoryService().retrieve()
            .then(res => {
                let categories = (res.data as IBusinessCategory[]).map(item => {
                    return {
                        value: item.id,
                        label: item.name,
                        parent: item.parentCategoryId
                    };
                });
        
                // Use set to uniquely identify added category.
                this.businessCategoryOptions = buildCascaderOptions(categories);
            })
    }

    private validate(formIndex: number) {
        if (formIndex === 2) {
            (<ElForm>this.$refs.businessCategoryForm)?.validate(async (passed, errors) => {
                const values = (<any>this.$refs.businessCategories).getCheckedNodes();
                values.forEach(element => {
                    registrationStore.addBusinessCategory(element.value);
                });
                if (passed) {
                    await this.retrieveDocumentTypes(true);
                    await this.retrieveDocumentTypes(false);
                }
                this.eventBus.$emit('step-validated', { passed, errors })
            });
        }
    }

    private async retrieveDocumentTypes(mandatory: boolean) {
        const businessCategories = Array.from(registrationStore.businessCategories);
        try {
            const res = await this.documentTypeService()
                .retrieveWithFilter(
                    [`mandatory=${mandatory}`]
                    .concat(businessCategories.map(id => `businessCategoryIds=${id}`))
                );
            registrationStore.setDocumentTypes({
                documentTypes: res.data,
                mandatory
            });
        } catch(e) {
            console.log('error: %O', e);
        }
    }
}