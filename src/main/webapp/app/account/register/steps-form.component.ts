import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from '@/constants'
import { RegistrationStoreModule as registrationStore } from '@/shared/config/store/registration-store'
import Vue from 'vue'
import Component from 'vue-class-component'
import { Inject } from 'vue-property-decorator'
import BusinessCategories from './components/business-categories.vue'
import CompanyProfile from './components/company-profile.vue'
import LoginDetails from './components/login-details.vue'
import PaymentInformation from './components/payment-information.vue'
import PersonInCharge from './components/person-in-charge.vue'
import SummaryRegistration from './components/summary-registration.vue'
import SupportingDocuments from './components/supporting-documents.vue'
import TaxInformation from './components/tax-information.vue'
import RegisterService from './register.service'

@Component({
  components: {
    LoginDetails,
    CompanyProfile,
    BusinessCategories,
    SupportingDocuments,
    PersonInCharge,
    PaymentInformation,
    TaxInformation,
    SummaryRegistration
  }
})

export default class StepsForm extends Vue {

  @Inject('registerService')
  private registerService: () => RegisterService;

  agreementAccepted: boolean = false;
  doNotMatch = '';
  error = '';
  errorEmailExists = '';
  errorUserExists = '';
  success = false;

  fullscreenLoading = false;
  active = 0;
  eventBus = new Vue();
  registration = {
    loginDetails: {
      login: '',
      email: '',
      password: '',
      passwordConfirmation: ''
    },
    companyProfile: {
      name: '',
      type: '',
      branch: false,
      phone: '',
      fax: '',
      email: '',
      website: '',

      npwp: '',
      npwpName: '',
      npwpAddress: '',
      npwpCountry: '',
      npwpRegion: '',
      npwpCity: '',
      npwpPostalCode: '',
      file: '',

      address: '',
      country: '',
      region: '',
      city: '',
      postalCode: ''
    },
    businesses: [],
    businessCategories: {
      values: []
    },
    mainDocuments: [],
    additionalDocuments: [],
    contacts: [],
    functionaries: [],
    payments: [],
    taxes: [],
    taxInformations: {}
  }

  mounted() {
    this.eventBus.$on('step-validated', this.proceedNext);
  }

  back() {
    this.$router.go(-1);
  }

  previous() {
    --this.active;
  }

  next() {
    // Trigger the validation of the current form.
    this.eventBus.$emit('validate-form', this.active);
  }

  proceedNext(validationState) {
    if (validationState.passed && this.active <= 7) {
      ++this.active;
    }
  }

  showAt(index) {
    return index !== this.active ? 'hide' : '';
  }

  retrieveBusinessCategory() {
    const businessCategory = Array.from(registrationStore.businessCategories);
    this.registration.businesses = businessCategory;
  }

  submit() {
    this.retrieveBusinessCategory();
    // Set the tax details and strip out the ID of each tax.
    this.registration.taxInformations = {
      eInvoice: registrationStore.eInvoice,
      taxableEmployers: registrationStore.taxableEmployers
    }
    this.registration.taxes = registrationStore.taxes;
    this.registration.taxes.forEach(tax => { delete tax.id });
    this.fullscreenLoading = true;
    this.doNotMatch = null;
    this.error = null;
    //this.errorUserExists = null;
    //this.errorEmailExists = null;
    //this.registerAccount.langKey = translationStore.language;
    this.registerService()
      .processRegistration(this.registration)
      .then(() => {
        this.success = true;
        this.active = 0;
        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: '<strong>Registration form submitted</strong>.',
          type: 'success'
        });

        this.registration = {
          loginDetails: {
            login: '',
            email: '',
            password: '',
            passwordConfirmation: ''
          },
          companyProfile: {
            name: '',
            type: '',
            branch: false,
            phone: '',
            fax: '',
            email: '',
            website: '',

            npwp: '',
            npwpName: '',
            npwpAddress: '',
            npwpCountry: '',
            npwpRegion: '',
            npwpCity: '',
            npwpPostalCode: '',
            file: '',

            address: '',
            country: '',
            region: '',
            city: '',
            postalCode: ''
          },
          businesses: [],
          businessCategories: {
            values: []
          },
          mainDocuments: [],
          additionalDocuments: [],
          contacts: [],
          functionaries: [],
          payments: [],
          taxInformations: {},
          taxes: []
        };
        this.$router.replace('/');
      })
      .catch(error => {
        this.success = null;
        if (error.response.status === 400 && error.response.data.type === LOGIN_ALREADY_USED_TYPE) {
          this.error = '<strong>Login name already registered!</strong> Please choose another one.';
        } else if (error.response.status === 400 && error.response.data.type === EMAIL_ALREADY_USED_TYPE) {
          this.error = '<strong>Email is already in use!</strong> Please choose another one.';
        } else {
          this.error = '<strong>Registration failed!</strong> Please try again later.';
        }

        this.$notify({
          title: 'Error',
          dangerouslyUseHTMLString: true,
          message: this.error,
          type: 'error',
          duration: 3000
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

}
