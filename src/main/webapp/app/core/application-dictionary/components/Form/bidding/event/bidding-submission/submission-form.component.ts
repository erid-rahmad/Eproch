import ScheduleEventMixin from '@/core/application-dictionary/mixins/ScheduleEventMixin';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { Component, Mixins, Vue } from "vue-property-decorator";
import formatDuration from 'date-fns/formatDuration';
import intervalToDuration from 'date-fns/intervalToDuration';

const baseApiBiddingSubmission = 'api/m-bidding-submissions';

const SubmissionFormProps = Vue.extend({
  props: {
    data: Object
  }
})

@Component
export default class SubmissionForm extends Mixins(ScheduleEventMixin, SubmissionFormProps) {

  private timerId;
  private intervalId;
  private currentDate = new Date();

  page = 1;
  vendorOptions: any[] = [];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get timeRemaining() {
    if (!this.mainForm.actualEndDate) {
      return '';
    }

    if (this.currentDate >= new Date(this.mainForm.actualEndDate)) {
      return 'Event has been ended';
    }

    const duration = intervalToDuration({
      start: this.currentDate,
      end: new Date(this.mainForm.actualEndDate)
    });

    return formatDuration(duration, {
      format: ['days', 'hours', 'minutes']
    });
  }

  onVendorChanged(vendorId: number) {
    if (vendorId) {
      this.commonService(baseApiBiddingSubmission)
        .retrieve({
          criteriaQuery: [
            `biddingId.equals=${this.mainForm.biddingId}`,
            `vendorId.equals=${vendorId}`
          ],
          paginationQuery: {
            page: 0,
            size: 1,
            sort: ['id']
          }
        })
        .then(res => {
          if (res.data.length) {
            this.$emit('vendor-changed', res.data[0].id);
          }
        });
      } else {
        this.$emit('vendor-changed', null);
      }
  }

  created() {
    this.timerId = setTimeout(() => {
      this.intervalId = setInterval(this.updateCurrentDate, 60000);
      this.updateCurrentDate();

      if (this.currentDate >= new Date(this.mainForm.actualEndDate)) {
        clearInterval(this.intervalId);
        clearInterval(this.timerId);
      }
    }, (60 - this.currentDate.getSeconds()) * 1000);
    
    if (!this.isVendor) {
      this.retrieveJoinedVendors(this.biddingScheduleId);
    }
  }

  beforeDestroy() {
    clearInterval(this.intervalId);
    clearInterval(this.timerId);
  }

  private retrieveJoinedVendors(scheduleId: number) {
    this.commonService(baseApiBiddingSubmission)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=1',
          `biddingScheduleId.equals=${scheduleId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(res => {
        this.vendorOptions = res.data.map(item => {
          return {
            id: item.vendorId,
            name: item.vendorName
          };
        });
      })
  }

  submit() {
    console.log('This will submit the submission');
  }

  private updateCurrentDate() {
    this.currentDate = new Date();
  }
}
