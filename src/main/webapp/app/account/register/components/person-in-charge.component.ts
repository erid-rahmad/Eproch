import Vue from 'vue'
import Component from 'vue-class-component'
import PersonInChargeUpdate from './person-in-charge-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'

const PICProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {}
    },
    contacts: {
      type: Array,
      default: () => []
    },
    functionaries: {
        type: Array,
        default: () => []
    }
  }
})

@Component({
    components: {
        PersonInChargeUpdate
    }
})
export default class PersonInCharge extends PICProps {
    loading = false;
    columnSpacing = 32;
    editDialogVisible = false;
    editingForm = null;
    errors = {
        functionaries: null
    }

    mounted() {
        // Pre-populate the contact person table with the one who was specified in the login details.
        this.contacts.push(registrationStore.loginDetails);
        this.eventBus.$on('validate-form', this.validate);
        this.eventBus.$on('person-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$on('push-person', this.pushPerson);
    }

    beforeDestroy() {
        this.eventBus.$off('validate-form', this.validate);
        this.eventBus.$off('document-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$off('push-person', this.pushPerson);
        (<Vue>this.$refs.dialogBody)?.$destroy();
    }

    get hasErrors() {
        return this.errors.functionaries !== null;
    }

    addPerson(target: string) {
        this.editingForm = target;
        this.editDialogVisible = true;
    }

    hideDialog() {
        this.eventBus.$emit('reset-person-form');
        this.editDialogVisible = false;
    }

    private hideLoadingIndicator() {
        this.loading = false;
    }

    private pushPerson(person) {
        this[person.contact ? 'contacts' : 'functionaries'].push(person);
        if (this.functionaries.length > 0) {
            this.errors.functionaries = null;
        }
        this.loading = false;
        this.editDialogVisible = false;
    }

    saveDocument() {
        this.loading = true;
        this.eventBus.$emit('save-person');
    }

    validate(formIndex: number) {
        if (formIndex === 4) {
            let passed = true;

            if (this.functionaries.length === 0) {
                /* const types = registrationStore.mandatoryDocumentTypes
                    .map(item => item.name)
                    .join(', ');
                passed = false;
                this.errors.functionaries = `Document of the following types are required: ${types}`; */
            } else {
                /* this.errors.functionaries = null; */
            }
            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }
}