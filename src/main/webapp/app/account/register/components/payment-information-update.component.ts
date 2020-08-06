import Vue from 'vue'
import Component from 'vue-class-component'
import { ElForm } from 'element-ui/types/form'
import { Inject, Watch, Model } from 'vue-property-decorator'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store';
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
//import idb from 'idb';

const PaymentInformationUpdateProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => {}
        },
        pays: {
            type: Object,
            default() {
                return {};
            }
        },
    }
})

@Component
export default class PaymentInformationUpdate extends PaymentInformationUpdateProps {

    @Inject('dynamicWindowService')
    private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

    public rules = {};
    public banks = [];
    public currencies = [];
    public pay:any = {};
    
    @Watch('pays')
    setPayment(payment){
        this.pay = payment;
    }

    created(){
        this.setPayment(this.pays);
    }

    mounted() {
        this.retrieveBank();
        this.retrieveCurrency();
        this.eventBus.$on('save-payment', this.save);
        this.eventBus.$on('reset-payment-form', this.reset);
    }

    beforeDestroy() {
        this.eventBus.$off('save-payment', this.save);
        this.eventBus.$off('reset-payment-form', this.reset);
    }

    private reset() {
        (<ElForm>this.$refs.pay).resetFields();
        this.pay = {};
    }

    private save() {
        (<ElForm>this.$refs.pay).validate((passed, errors) => {
            if (passed) {
                setTimeout(() => {
                    const data = { ...this.pay };
                    this.eventBus.$emit('push-payment', data);
                    this.reset();
                }, 1000);
            } else {
                this.eventBus.$emit('payment-validation-failed', {passed, errors});
            }
        });
    }
    
    private retrieveBank() {        
        this.dynamicWindowService('/api/c-banks')
            .retrieve()
            .then(res=>{
                this.banks = res.data;
            });
    }

    private retrieveCurrency() {        
        this.dynamicWindowService('/api/c-currencies')
            .retrieve()
            .then(res=>{
                this.currencies = res.data;
            });
                
    }
    
    get fileList() {
        if ( ! this.pay.supportingfile) return [];
        return [this.pay.supportingfile];
    }

    getFile(file, fileList) {
        this.pay.supportingfile = file;
    }

    handleExceed(files, fileList) {
        this.$notify({
            title: 'Warning',
            message: "The limit file is 1",
            type: 'warning',
            duration: 3000
        });
    }

}
