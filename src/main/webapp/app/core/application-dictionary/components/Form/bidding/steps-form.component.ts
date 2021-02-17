import Vue from 'vue';
import Component from 'vue-class-component';
import BiddingInformation from './components/bidding-information.vue';
import BiddingSchedule from './components/bidding-schedule.vue';
import VendorInvitation from './components/vendor-invitation.vue';
import VendorScoring from './components/vendor-scoring.vue';

@Component({
  components: {
    BiddingInformation,
    BiddingSchedule,
    VendorInvitation,
    VendorScoring
  }
})
export default class StepsForm extends Vue {

  agreementAccepted: boolean = false;
  doNotMatch = '';
  error = '';
  errorEmailExists = '';
  errorUserExists = '';
  success = false;

  fullscreenLoading = false;
  active = 0;
  eventBus = new Vue();
  bidding = {
    biddingInformation: {
      biddingInformationLine: [],
      projectInformation: [],
    },
    biddingSchedule: [],
    vendorInvitation: [],
    vendorScoring: []
  }

  mounted() {
    //this.eventBus.$on('step-validated', this.proceedNext);
  }

  back() {
    this.$emit('back');
  }

  previous() {
    --this.active;
  }

  next() {
    console.log(this.active);
    console.log(this.bidding.biddingInformation);
    ++this.active;
    // Trigger the validation of the current form.
    //this.eventBus.$emit('validate-form', this.active);
  }

  proceedNext(validationState) {
    if (validationState.passed && this.active <= 3) {
      ++this.active;
    }
  }

  showAt(index) {
    return index !== this.active ? 'hide' : '';
  }

  submit() {

  }

}
