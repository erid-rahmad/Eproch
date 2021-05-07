import { Component, Inject, Mixins, Vue } from 'vue-property-decorator';
import DynamicWindowService from '../components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from './AccessLevelMixin';

const baseApiSchedule = 'api/m-bidding-schedules';
const baseApiDateSet = 'api/m-prequalification-date-sets';

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

  protected onMainFormUpdated(mainForm: any) {}

  created() {
    console.log('scheduleEventMixin created');
    const query = this.$route.query;
    let scheduleId = query.biddingScheduleId ? parseInt(query.biddingScheduleId as string) : this.scheduleId;

    if (scheduleId) {
      this.biddingScheduleId = scheduleId;
      this.retrieveBiddingSchedule(scheduleId);
    }
  }

  private retrieveBiddingSchedule(id: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loading = true;
      this.commonService(baseApiSchedule)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `id.equals=${id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          this.mainForm = res.data[0];
          this.retrieveDateSet(this.mainForm.id);
        }
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get bidding schedules. %O', err);
        this.$message.error('Failed to get schedule details');
        resolve(false);
      })
      .finally(() => {
        this.loading = false;
      });
    });
  }

  private retrieveDateSet(biddingScheduleId: number): Promise<boolean> {
    return new Promise(resolve => {
      this.loading = true;
      this.commonService(baseApiDateSet)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingScheduleId.equals=${biddingScheduleId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          const dateSet = res.data[0];
          this.$set(this.mainForm, 'startDate', dateSet.startDate);
          this.$set(this.mainForm, 'endDate', dateSet.endDate);
        }
        this.$emit('data-loaded', {...this.mainForm});
        this.onMainFormUpdated(this.mainForm);
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get schedule date set. %O', err);
        this.$message.error('Failed to get schedule details');
        resolve(false);
      })
      .finally(() => {
        this.loading = false;
      });
    });
  }
}