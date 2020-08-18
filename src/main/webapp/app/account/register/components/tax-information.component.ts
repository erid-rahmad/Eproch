import Vue from 'vue'
import Component from 'vue-class-component'
import TaxInformationUpdate from './tax-information-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const TaxInformationProps = Vue.extend({
    props: {
        eventBus: {
            type: Object,
            default: () => { }
        }
    }
})

@Component({
    components: {
        TaxInformationUpdate
    }
})
export default class TaxInformation extends TaxInformationProps {
    applying = false;
    columnSpacing = 32;
    editDialogVisible = false;
    errors = {
        tax: null,
        type: "info"
    };
    private taxesWatcherTimestamp: number = Date.now();

    get eInvoice() {
        return registrationStore.eInvoice;
    }

    set eInvoice(eInvoice) {
        registrationStore.setEInvoice(eInvoice);
    }

    get taxableEmployers() {
        return registrationStore.taxableEmployers;
    }

    set taxableEmployers(taxableEmployers) {
        registrationStore.setTaxableEmployers(taxableEmployers);
    }

    get taxes() {
        return this.taxesWatcherTimestamp && registrationStore.taxes;
    }

    mounted() {
        // Pre-populate the contact person table with the one who was specified in the login details.
        this.eventBus.$on('validate-form', this.validate);
    }

    beforeDestroy() {
        this.eventBus.$off('validate-form', this.validate);
        (<Vue>this.$refs.taxSelectionList)?.$destroy();
    }

    showTaxList() {
        this.editDialogVisible = true;
    }

    deleteTax(row: any) {
        registrationStore.removeTax(row);
    }

    public onEInvoiceChanged(eInvoice: boolean) {
        registrationStore.setEInvoice(eInvoice);
    }

    public onTaxableEmployersChanged(taxableEmployers: boolean) {
        registrationStore.setTaxableEmployers(taxableEmployers);
    }

    public async onTaxesApplied() {
        this.applying = true;
        const taxes = (<any>this.$refs.taxSelectionList).selectedTaxes;

        taxes.forEach((tax: any) => {
                tax.eInvoice = this.eInvoice;
                tax.taxableEmployers = this.taxableEmployers;
                tax.taxId = tax.id;
            });

        await registrationStore.setTaxes(taxes);
        this.taxesWatcherTimestamp = Date.now();
        this.editDialogVisible = false;
        this.applying = false;
    }

    validate(formIndex: number) {
        if (formIndex === 6) {
            let passed = true;
            if (! this.taxes.length) {
                passed = false;
                this.errors.tax = `Your company must have at least one tax rate`;
                this.errors.type = 'error';
            } else {
                this.errors.tax = null;
                this.errors.type = 'info';
            }

            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }

}
