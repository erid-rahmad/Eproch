import Vue from 'vue';
import Component from 'vue-class-component';
import BiddingInformation from './components/bidding-information.vue';
import BiddingSchedule from './components/bidding-schedule.vue';
import VendorInvitation from './components/vendor-invitation.vue';
import VendorScoring from './components/vendor-scoring.vue';

const StepsFormProps = Vue.extend({
  props: {
    editMode: Boolean,
    data: Object
  }
});

export enum BiddingStep {
  INFO,
  SCHEDULE,
  SELECTION,
  SCORING
}

@Component({
  components: {
    BiddingInformation,
    BiddingSchedule,
    VendorInvitation,
    VendorScoring
  }
})
export default class StepsForm extends StepsFormProps {

  showSaveDialog = false;
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
    biddingLines: [],
    projectInformations: [],
    biddingSchedules: [],
    vendorInvitations: [],
    vendorSuggestions: [],
    vendorScoring: []
  }

  created() {
    console.log('editMode: %s, data: %O', this.editMode, this.data);
    if (this.editMode && this.data) {
      this.bidding = {...this.data};
    }
  }

  close() {
    this.$emit('close');
  }

  previous() {
    --this.active;
  }

  next() {
    if (this.editMode) {
      ++this.active;
    } else {
      this.showSaveDialog = true;
    }
  }

  saveStep() {
    const currentStep = this.$refs[`step-${this.active}`] as any;
    currentStep.save();
  }

  goToNextStep({ data }) {
    this.showSaveDialog = false;
    if (++this.active <= 3) {
      this.bidding = data;
    }
  }
}
