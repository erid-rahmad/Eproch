import Vue from 'vue'
import Component from 'vue-class-component'
import PaymentInformationUpdate from './payment-information-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { ElForm } from 'element-ui/types/form';

const PaymentInformationProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => {}
        },
        payments: {
            type: Array,
            default: () => []
        },
    }
})

@Component({
    components: {
        PaymentInformationUpdate
    }
})
export default class PaymentInformation extends PaymentInformationProps {
    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    pays = {};
    errors = {
        payment: null,
        type: 'info'
    }

    mounted() {
        // Pre-populate the contact person table with the one who was specified in the login details.
        this.eventBus.$on('validate-form', this.validate);
        this.eventBus.$on('payment-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$on('push-payment', this.savePayment);
    }

    beforeDestroy() {
        this.eventBus.$off('validate-form', this.validate);
        this.eventBus.$off('payment-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$off('push-payment', this.savePayment);
        (<Vue>this.$refs.dialogBody)?.$destroy();
    }

    get hasErrors() {
        this.errors.type = 'info';
        return this.errors.payment !== null;
    }

    addPayment() {
        this.editDialogVisible = true;
    }

    prepareRemove(row: any){
        const i = this.payments.indexOf(row);
        this.payments.splice(i, 1);
    }

    edit(payment: any, index: string){
        //console.log(payment);
        this.editDialogVisible = true;
        this.pays = {...payment}
        this.$set(this.pays, 'index', index);
    }

    hideDialog() {
        this.eventBus.$emit('reset-payment-form');
        this.editDialogVisible = false;
    }

    private hideLoadingIndicator() {
        this.loading = false;
    }

    private savePayment(payment) {
        //console.log(payment);
        payment.bankId = this.printKeyByParam(payment.bank);
        payment.name = this.printValueByParam(payment.bank);
        payment.currencyId = this.printKeyByParam(payment.currency);
        payment.accountNo = payment.account;

        if(payment.index !== undefined){
            this.payments.splice(payment.index, 1, payment);
        }else{
            this.payments.push(payment);
        }
        
        if (this.payments.length > 0) {
            this.errors.type = 'info';
            this.errors.payment = null;
        }
        this.loading = false;
        this.editDialogVisible = false;
    }

    private printValueByParam(row: any){
        if(row){
            let value, key;
            key = parseInt(row.substring( 0, row.indexOf('_')));
            value = row.substring(row.indexOf('_') + 1, row.length);
            return value;
        }
    }

    private printKeyByParam(row: any){
        if(row){
            let value, key;
            key = parseInt(row.substring( 0, row.indexOf('_')));
            return key;
        }
    }

    saveDocument() {
        this.loading = true;
        this.eventBus.$emit('save-payment');
    }
    
    validate(formIndex: number) {
        if (formIndex === 5) {
            let passed = true;

            if (this.payments.length === 0) {
                /*
                const types = registrationStore.mandatoryDocumentTypes
                    .map(item => item.name)
                    .join(', ');
                */
                passed = true;
                this.errors.payment = `Your company must have at least a payment`;
                this.errors.type = 'error';
                
            } else {
                this.errors.payment = null;
                this.errors.type = 'info';
            }
            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }

}
