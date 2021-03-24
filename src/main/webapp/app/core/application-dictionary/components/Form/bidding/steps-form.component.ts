import Vue from 'vue';
import Component from 'vue-class-component';
import BiddingInformation from './components/bidding-information.vue';
import BiddingSchedule from './components/bidding-schedule.vue';
import VendorInvitation from './components/vendor-invitation.vue';
import VendorScoring from './components/vendor-scoring.vue';
import { Watch } from 'vue-property-decorator';

const StepsFormProps = Vue.extend({
  props: {
    editMode: Boolean,
    data: Object,
    stepIndex: {
      type: Number,
      default: () => {
        return 0;
      }
    }
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

  bidding: Record<string, any> = {
    biddingLines: [],
    projectInformations: [],
    biddingSchedules: [],
    vendorInvitations: [],
    vendorSuggestions: [],
    vendorScoring: []
  }

  @Watch('stepIndex')
  onStepIndexChanged(stepIndex: number) {
    this.active = stepIndex;
  }

  get nextButton() {
    return this.active < 3 ? 'Next' : 'Save';
  }

  created() {
    this.onStepIndexChanged(this.stepIndex);
    
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
    if (this.active === 3) {
      return;
    }

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

    if (! this.editMode && data.id) {
      this.editMode = true;
    }

    if (++this.active <= 3) {
      this.bidding = data;
    }
  }
}
