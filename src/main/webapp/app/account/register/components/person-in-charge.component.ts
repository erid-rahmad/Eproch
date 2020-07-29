import Vue from 'vue'
import Component from 'vue-class-component'
import PersonInChargeUpdate from './person-in-charge-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import DynamicWindowService from '../../../core/application-dictionary/components/DynamicWindow/dynamic-window.service';

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
    user = {};
    errors = {
        functionaries: null,
        type: 'info'
    }

    mounted() {
        // Pre-populate the contact person table with the one who was specified in the login details.
        this.contacts.push(registrationStore.loginDetails);
        this.eventBus.$on('validate-form', this.validate);
        this.eventBus.$on('person-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$on('push-person', this.savePerson);
    }

    beforeDestroy() {
        this.eventBus.$off('validate-form', this.validate);
        this.eventBus.$off('person-validation-failed', this.hideLoadingIndicator);
        this.eventBus.$off('push-person', this.savePerson);
        (<Vue>this.$refs.dialogBody)?.$destroy();
    }

    get hasErrors() {
        this.errors.type = 'info';
        return this.errors.functionaries !== null;
    }

    addPerson(target: string) {
        this.editingForm = target;
        this.editDialogVisible = true;
    }

    prepareRemove(target: string, index: string){
        this.editingForm = target;
        this[this.editingForm].splice(index, 1);
    }

    edit(person: any, target: string, index: string){
        //console.log(person);
        this.editingForm = target;
        this.editDialogVisible = true;

        this.user = {...person}
        this.$set(this.user, 'index', index);
    }

    hideDialog() {
        this.eventBus.$emit('reset-person-form');
        this.editDialogVisible = false;
    }

    private hideLoadingIndicator() {
        this.loading = false;
    }

    private printBusinessCategory(row: any){

        if(row.businessCategories){
            let i, value, key;
            let stringArray = [];
            for (i=0; i<row.businessCategories.length; i++) {
                key = parseInt(row.businessCategories[i].substring( 0, row.businessCategories[i].indexOf('_')));
                value = row.businessCategories[i].substring(row.businessCategories[i].indexOf('_') + 1, row.businessCategories[i].length);
                stringArray.push(value);
            }
            return stringArray?.join(', ') || "";
        }
        
    }

    private savePerson(person) {
        //console.log(person.index);
        
        if(person.index !== undefined){
            this[this.editingForm].splice(person.index, 1, person);
        }else{
            this[this.editingForm].push(person);
        }
        
        if (this.functionaries.length > 0) {
            this.errors.type = 'info';
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
                /*
                const types = registrationStore.mandatoryDocumentTypes
                    .map(item => item.name)
                    .join(', ');
                */
                passed = false;
                this.errors.functionaries = 'Your company must have at least a functionary from component';
                this.errors.type = 'error';
                
            } else {
                this.errors.functionaries = null;
                this.errors.type = 'info';
            }
            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }
}
