import Vue from 'vue';
import Component from 'vue-class-component';
import PrequalificationInformation from './components/prequalification-information.vue';
import PrequalificationInvitation from './components/prequalification-invitation.vue';
//import PrequalificationEvent from './components/vendor-invitation.vue';
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
  INVITATION,
  EVENT
}

@Component({
  components: {
    PrequalificationInformation,
    PrequalificationInvitation,
    //PrequalificationEvent
  }
})
export default class StepsForm extends StepsFormProps {

  private direction = 0;

  dataChanged = false;
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

  preq: Record<string, any> = {
    
  }

  get nextButton() {
    return this.active < 2 ? 'Next' : 'Save';
  }

  @Watch('stepIndex')
  onStepIndexChanged(stepIndex: number) {
    this.active = stepIndex;
  }

  onSkipSave() {
    this.dataChanged = false;
    this.showSaveDialog = false;
    this.$emit('change', false);

    if (this.editMode) {
      if (this.direction > 0) {
        ++this.active;
      } else {
        --this.active;
      }
    }
  }

  onStepChanged() {
    this.dataChanged = true;
    this.$emit('change');
  }

  onStepError() {
    this.showSaveDialog = false;
  }

  onStepSaved({ data, changeStep }) {
    this.showSaveDialog = false;
    this.dataChanged = false;
    this.$emit('change', false);

    if (! this.editMode && data.id) {
      this.$emit('update:editMode', true);
    }

    let active = this.active;
    if (changeStep) {
      active = this.direction > 0 ? this.active++ : this.active--;
    }

    if (active <= 2) {
      this.preq = data;
    }
  }

  created() {
    this.onStepIndexChanged(this.stepIndex);
    
    if (this.editMode && this.data) {
      this.preq = {...this.data};
    }
  }

  previous() {
    if (this.active === 0) {
      return;
    }

    this.direction = -1;
    if (this.editMode) {
      if (this.dataChanged) {
        this.showSaveDialog = true;
      } else {
        --this.active;
      }
    } else {
      --this.active;
    }
  }

  next() {
    if (this.active === 2) {
      return;
    }

    this.direction = 1;
    if (this.editMode) {
      if (this.dataChanged) {
        this.showSaveDialog = true;
      } else {
        ++this.active;
      }
    } else {
      this.showSaveDialog = true;
    }
  }

  saveStep(changeStep: boolean) {
    const currentStep = this.$refs[`step-${this.active}`] as any;
    currentStep.save(changeStep);
  }
}
