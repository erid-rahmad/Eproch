import Vue from 'vue'
import Component from 'vue-class-component'
import TaxInformationUpdate from './tax-information-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const TaxInformationProps = Vue.extend({
  props: {
    eventBus: {
        type: Object,
        default: () => {}
    },
    taxRates: {
        type: Array,
        default: () => []
    },
    taxInformations: {
        type: Object,
        default: () => {
            return null;
        }
    },
  }
})

@Component({
    components: {
        TaxInformationUpdate
    }
})
export default class TaxInformation extends TaxInformationProps {
    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    public rules = {
        efaktur: {
            required: true
        },
        pkp: {
            required: true
        }
    };
    errors = {
        tax: null,
        type: "info"
    }

    mounted() {
        // Pre-populate the contact person table with the one who was specified in the login details.
        this.eventBus.$on('validate-form', this.validate);
        this.eventBus.$on('push-tax', this.saveTax);
    }

    beforeDestroy() {
        this.eventBus.$off('validate-form', this.validate);
        this.eventBus.$off('push-tax', this.saveTax);
        (<Vue>this.$refs.dialogBody)?.$destroy();
    }

    get hasErrors() {
        this.errors.type = 'info';
        return this.errors.tax !== null;
    }

    addTax() {
        this.editDialogVisible = true;
    }

    prepareRemove(row: any){
        const i = this.taxRates.indexOf(row);
        this.taxRates.splice(i, 1);
    }

    hideDialog() {
        this.eventBus.$emit('reset-tax-form');
        this.editDialogVisible = false;
    }

    private hideLoadingIndicator() {
        this.loading = false;
    }

    private saveTax(tax) {
        this.taxRates.push(tax);

        if (this.taxRates.length > 0) {
            this.errors.type = 'info';
            this.errors.tax = null;
        }
        this.loading = false;
        this.editDialogVisible = false;
    }

    saveDocument() {
        this.loading = true;
    }
    
    validate(formIndex: number) {
        if (formIndex === 6) {
            let passed = true;
            if(this.taxRates.length === 0 || this.taxInformations.efaktur==='' || this.taxInformations.pkp==='') {
                /*
                const types = registrationStore.mandatoryDocumentTypes
                    .map(item => item.name)
                    .join(', ');
                */
                passed = false;
                this.errors.tax = `Your company must have at least a tax rate`;
                this.errors.type = 'error';
            } else {
                this.errors.tax = null;
                this.errors.type = 'info';
            }
            
            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }
    
}
