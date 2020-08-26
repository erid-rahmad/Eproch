import Vue from 'vue';
import Component from 'vue-class-component';
import { ElForm } from 'element-ui/types/form';
import { Inject, Watch } from 'vue-property-decorator';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import { buildCascaderOptions } from '@/utils/form';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

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
        },
        businessCategory: {
            type: Object,
            default: () => {
              return null;
            }
        },
    }
})

@Component
export default class BusinessCategories extends BusinessCategoriesProps {

    @Inject('dynamicWindowService')
    private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

    public getBusinessCategory = { values: this.businessCategories };
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
    private docTypeBusinessCategory;
    
    created() {
        this.retrieveBusinessCategories();
    }

    mounted() {
        this.eventBus.$on('validate-form', this.validate);
    }

    private retrieveBusinessCategories() {
        
        this.dynamicWindowService('/api/c-business-categories')
        .retrieve()
        .then(res => {
            let categories = res.data.map(item => {
                return {
                    value: item.id,
                    label: item.name,
                    parent: item.parentCategoryId
                };
            });
    
            // Use set to uniquely identify added category.
            this.businessCategoryOptions = buildCascaderOptions(categories);
        });
    }

    private validate(formIndex: number) {
        if (formIndex === 2) {
            (<ElForm>this.$refs.businessCategoryForm)?.validate(async (passed, errors) => {

                const getValues = Array.from(registrationStore.businessCategories);
                for(let i in getValues){
                    registrationStore.deleteBusinessCategory(getValues[i]);
                };

                const values = (<any>this.$refs.businessCategories).getCheckedNodes();
                values.forEach(element => {
                    if(element.level === 3){
                        registrationStore.addBusinessCategory(element.value);
                    }
                });
                if (passed) {
                    await this.retrieveDocumentTypes(true);
                    await this.retrieveDocumentTypes(false);
                    const businessCategories = Array.from(registrationStore.businessCategories);
                    this.businessCategoryValues = businessCategories;
                    console.log(this.businessCategoryValues);
                }
                this.eventBus.$emit('step-validated', { passed, errors });
            });
        }
    }

    private async retrieveDocumentTypes(mandatory: boolean) {
        const businessCategories = Array.from(registrationStore.businessCategories);
        //console.log(businessCategories);
        try {
            const res = await this.dynamicWindowService('/api/c-reg-doc-type-business-categories')
                .retrieve({
                    criteriaQuery: [`mandatory.equals=${mandatory}`]
                    .concat(businessCategories.map(id => `businessCategoryId.in=${id}`))
                });
                
            registrationStore.setDocumentTypes({
                documentTypes: res.data,
                mandatory
            });
            //console.log(res);
        } catch(e) {
            console.log('error: %O', e);
        }
    }
}
