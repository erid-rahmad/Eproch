import Vue from 'vue'
import Component from 'vue-class-component'
import CompanyProfile from './components/company-profile.vue'
import LoginDetails from './components/login-details.vue'
import SupportingDocuments from './components/supporting-documents.vue'

@Component({
  components: {
    LoginDetails,
    CompanyProfile,
    SupportingDocuments
  }
})
export default class StepsForm extends Vue {
  data() {
    return {
      active: 0,
      eventBus: new Vue(),
      registration: {
        loginDetails: {
          username: 'aaryadewa',
          email: 'ananta.aryadewa@gmail.com',
          password: 'abcasd',
          passwordConfirmation: 'abcasd'
        },
        companyProfile: {
          name: 'PT. Terbaik',
          npwp: '928291918290001',
          npwpName: 'John',
          npwpAddress: 'Jl. Pemuda',
          npwpCountry: 'ID',
          npwpRegion: 'JKT',
          npwpCity: 'Jakarta Timur',
          npwpPostalCode: '',
          type: 'C',
          branch: false,
          address: 'Jl. Sudirman',
          country: 'ID',
          region: 'JKT',
          city: 'Jakarta Selatan',
          postalCode: null,
          phone: '303030',
          fax: '',
          email: 'cs@terbaik.com',
          website: ''
        },
        mainDocuments: [],
        additionalDocuments: []
      }
    }
  }

  mounted() {
    this.eventBus.$on('step-validated', this.proceedNext)
  }

  previous() {
    if (this.active >= 0) {
      --this.active
    }
  }

  next() {
    // Trigger the validation of the current form.
    this.eventBus.$emit('validate-form', this.active)
  }

  proceedNext(validationState) {
    if (validationState.passed && this.active <= 2) {
      ++this.active
    }
  }

  showAt(index) {
    return index !== this.active ? 'hide' : ''
  }
}
