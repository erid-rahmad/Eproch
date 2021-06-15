import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from './AccessLevelMixin';

const baseApiSchedule = 'api/m-bidding-schedules';

const ScheduleEventMixinProps = Vue.extend({
  props: {
    scheduleId: Number
  }
})

@Component
export default class ScheduleEventMixin extends Mixins(AccessLevelMixin, ScheduleEventMixinProps) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  protected biddingScheduleId: number = null;

  loading: boolean = false;
  mainForm: any = {};

  /**
   * Optional method that can be overriden by classes that uses this mixin.
   * @param mainForm the resulted MBiddingSchedule data.
   */
  protected onMainFormUpdated(mainForm: any) {
    // No default behavior.
  }
  protected onMainFormUpdatedtes(mainForm: any) {
    // No default behavior.
  }
  protected onMainFormUpdatedevaluation(mainForm: any) {
    // No default behavior.
  }
  created() {
    const query = this.$route.query;
    let scheduleId = query.biddingScheduleId ? parseInt(query.biddingScheduleId as string) : this.scheduleId;

    if (scheduleId) {
      this.biddingScheduleId = scheduleId;
      this.retrieveBiddingSchedule(scheduleId);
    }
  }

  private retrieveBiddingSchedule(id: number) {
    this.loading = true;
    this.commonService(baseApiSchedule)
    .find(id)
    .then(res => {
      this.mainForm = res;
      this.$emit('data-loaded', {...this.mainForm});
      this.onMainFormUpdated(this.mainForm);
      this.onMainFormUpdatedtes(this.mainForm);
      this.onMainFormUpdatedevaluation(this.mainForm)

    })
    .catch(err => {
      console.error('Failed to get bidding schedule. %O', err);
      this.$message.error('Failed to get schedule details');
    })
    .finally(() => {
      this.loading = false;
    });
  }
}
