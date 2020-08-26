import Vue from 'vue'
import Component from 'vue-class-component'
import PersonInChargeUpdate from './person-in-charge-update.vue';
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import Schema from 'async-validator';

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
        type: {
            contacts: "info",
            functionaries: "info"
        }
    }
    private validationSchema: any = {};
    rules = {
        name: {
            required: true,
        },
        position: {
            required: true,
        },
        phone: {
            required: true,
        },
        email: {
            required: true,
        },
        valueBusinessCategories: {
            required: true,
        },
        login: {
            required: true,
        }
    };

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
        this.errors.type.functionaries = 'info';
        this.errors.type.contacts = 'info';
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

        if(row.valueBusinessCategories){
            let i, value, key;
            let stringArray = [];
            for (i=0; i<row.valueBusinessCategories.length; i++) {
                key = parseInt(row.valueBusinessCategories[i].substring( 0, row.valueBusinessCategories[i].indexOf('_')));
                value = row.valueBusinessCategories[i].substring(row.valueBusinessCategories[i].indexOf('_') + 1, row.valueBusinessCategories[i].length);
                stringArray.push(value);
            }
            return stringArray?.join(', ') || "";
        }
        
    }

    private printKeyBusinessCategory(row: any){

        if(row.valueBusinessCategories){
            let i, value, key;
            let stringArray = [];
            for (i=0; i<row.valueBusinessCategories.length; i++) {
                key = parseInt(row.valueBusinessCategories[i].substring( 0, row.valueBusinessCategories[i].indexOf('_')));
                value = row.valueBusinessCategories[i].substring(row.valueBusinessCategories[i].indexOf('_') + 1, row.valueBusinessCategories[i].length);
                stringArray.push(key);
            }
            return stringArray;
        }
        
    }

    private printKeyByParam(row: any){
        if(row){
            let value, key;
            key = parseInt(row.substring( 0, row.indexOf('_')));
            return key;
        }
    }

    private savePerson(person) {
        console.log(person);
        
        person.businessCategories = this.printKeyBusinessCategory(person);
        if(person.index !== undefined){
            this[this.editingForm].splice(person.index, 1, person);
        }else{
            this[this.editingForm].push(person);
        }
        
        if (this.functionaries.length > 0) {
            this.errors.type.functionaries = 'info';
            this.errors.functionaries = null;
        }else{
            this.errors.type.contacts = 'info';
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
            let passed;
            this.validationSchema = {};
            this.validationSchema = new Schema(this.rules);
            //for(let field=0; field<this.contacts.length; field++){
                this.validationSchema.validate(this.contacts[0], (errors, fields) => {
                    if (errors) {
                        this.$notify({
                            title: 'Error',
                            message: "Please complete contact form " + this.contacts[0].login,
                            type: 'error',
                            duration: 5000,
                        });
                        passed = false;
                        this.errors.type.contacts = 'error';
                    }else{
                        passed = true;
                        this.errors.type.contacts = 'info';

                        if (this.functionaries.length === 0) {
                            passed = false;
                            this.errors.functionaries = 'Your company must have at least a functionary from component';
                            this.errors.type.functionaries = 'error';
                        } else {
                            passed = true;
                            this.errors.functionaries = null;
                            this.errors.type.functionaries = 'info';
                        }
                    }
                })
            //}
            this.eventBus.$emit('step-validated', { passed, errors: this.errors });
        }
    }
}
