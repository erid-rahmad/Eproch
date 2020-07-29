import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject, Watch } from 'vue-property-decorator'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const TaxInformationUpdateProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => {}
        },
        
    }
})

@Component
export default class TaxInformationUpdate extends TaxInformationUpdateProps {

    @Inject('dynamicWindowService')
    private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    public tax:any = [];
    
    mounted() {
        this.retrieveTax();
        this.eventBus.$on('reset-tax-form', this.reset);
    }

    beforeDestroy() {
        this.eventBus.$off('reset-tax-form', this.reset);
    }

    private reset() {
        this.loading = false;
    }
    
    private retrieveTax() {
        
        this.dynamicWindowService('/api/c-tax-rates')
            .retrieve()
            .then(res=>{
                this.tax = res.data;
                //console.log(this.tax);
            });
    }

    private getTax(scope: any){
        this.loading = true;

        setTimeout(() => {
            const data = { ...scope };
            this.eventBus.$emit('push-tax', data);
            this.reset();
        }, 1000);
    }

}
