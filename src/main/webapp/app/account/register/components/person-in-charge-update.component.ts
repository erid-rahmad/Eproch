import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject, Watch } from 'vue-property-decorator'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const PersonInChargeUpdateProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => {}
        },
        contact: Boolean, // Whether or not the current form is represents PIC.
        user: {
            type: Object,
            default() {
                return {};
            }
        }
    }
})

@Component
export default class PersonInChargeUpdate extends PersonInChargeUpdateProps {

    @Inject('dynamicWindowService')
    private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

    public rules = {
        userLogin: {
            min: 8,
            pattern: '^[_.@A-Za-z0-9-]*$'
        },
        email: {
            type: 'email'
        }
    };
    
    public businessCategories = [];
    public person = {};
    
    @Watch('user')
    setPerson(person) {
        this.person = person;
    }

    created() {
        this.setPerson(this.user);
    }

    mounted() {
        this.retrieveBusinessCategory();
        this.eventBus.$on('save-person', this.save);
        this.eventBus.$on('reset-person-form', this.reset);
    }

    beforeDestroy() {
        this.eventBus.$off('save-person', this.save);
        this.eventBus.$off('reset-person-form', this.reset);
    }

    private reset() {
        (<ElForm>this.$refs.person).resetFields();
        this.person = {};
    }

    private save() {
        (<ElForm>this.$refs.person).validate((passed, errors) => {
            if (passed) {
                const data = { ...this.person };
                this.eventBus.$emit('push-person', data);
                this.reset();
            } else {
                this.eventBus.$emit('person-validation-failed', {passed, errors});
            }
        });
    }

    private retrieveBusinessCategory() {
        const businessCategoryIds = Array.from(registrationStore.businessCategories);
        this.dynamicWindowService('/api/c-business-categories')
            .retrieve({
                criteriaQuery: [
                    'active.equals=true',
                    'sector.equals=TERTIARY'
                ].concat(businessCategoryIds.map(id => `id.in=${id}`)),
                paginationQuery: {
                    size: 1000,
                    page: 0,
                    sort: ['name']
                }
            })
            .then(res => {
                this.businessCategories = res.data;
            });
    }
}