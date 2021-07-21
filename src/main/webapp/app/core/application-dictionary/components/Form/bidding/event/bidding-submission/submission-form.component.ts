import ScheduleEventMixin from '@/core/application-dictionary/mixins/ScheduleEventMixin';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { Component, Mixins, Vue } from "vue-property-decorator";
import formatDuration from 'date-fns/formatDuration';
import intervalToDuration from 'date-fns/intervalToDuration';

const baseApiBiddingSubmission = 'api/m-bidding-submissions';

const SubmissionFormProps = Vue.extend({
  props: {
    data: Object,
    submission: Object,
    scheduleFromGrid:Object,
  }
})

@Component
export default class SubmissionForm extends Mixins(ScheduleEventMixin, SubmissionFormProps) {

  private timerId;
  private intervalId;
  private currentDate = new Date();

  submitConfirmationVisible: boolean = false;
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

  protected onMainFormUpdated(_mainForm: any) {
    if (this.isVendor) {
      this.retrieveSubmission(AccountStoreModule.vendorInfo.id);
    }

    if (!this.isVendor) {
        this.retrieveJoinedVendors(_mainForm.biddingId);

    }
  }

  onVendorChanged(vendorId: number) {
    if (vendorId) {
      this.retrieveSubmission(vendorId);
    } else {
      this.$emit('vendor-changed', null);
    }
  }

  created() {
    if(this.scheduleFromGrid){
      this.mainForm=this.scheduleFromGrid;
      console.log("this.main",this.mainForm)
    }
    this.timerId = setTimeout(() => {
      this.intervalId = setInterval(this.updateCurrentDate, 60000);
      this.updateCurrentDate();

      if (this.currentDate >= new Date(this.mainForm.actualEndDate)) {
        clearInterval(this.intervalId);
        clearInterval(this.timerId);
      }
    }, (60 - this.currentDate.getSeconds()) * 1000);

    if (!this.isVendor) {
      if (this.scheduleFromGrid.id) {
        this.retrieveJoinedVendors(this.scheduleFromGrid.biddingId)
      }
    }
  }

  beforeDestroy() {
    clearInterval(this.intervalId);
    clearInterval(this.timerId);
  }

  private retrieveSubmission(vendorId?: number) {
    const vendorCriteria = vendorId ? `vendorId.equals=${vendorId}` : null;
    this.commonService(baseApiBiddingSubmission)
      .retrieve({
        criteriaQuery: [
          `biddingId.equals=${this.mainForm.biddingId}`,
          vendorCriteria
        ],
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          const submission = res.data[0];
          this.$emit('update:submission', submission);
        }
      });
  }

  private retrieveJoinedVendors(scheduleId: number) {
    this.commonService(baseApiBiddingSubmission)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=1',
          `biddingId.equals=${scheduleId}`,
          // `biddingScheduleId.equals=${scheduleId}`,
          // 'documentStatus.equals=SMT'
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


  submit(confirm: boolean) {

    if (confirm) {
      this.submission.documentAction='SMT';
      this.commonService(baseApiBiddingSubmission)
        .applyDocAction(this.submission)
        .then(status => {
          this.submitConfirmationVisible = false;
          this.$message({
            message: `Document has been submitted successfully`,
            type: 'success'
          });
        })
        .catch(err => {
          console.error('Error updating the document status! %O', err);
          const message = `Error updating the document status`;
          this.$message({
            message: message.toString(),
            type: 'error'
          });
        });
    } else {
      this.submitConfirmationVisible = true;
    }
  }

  private updateCurrentDate() {
    this.currentDate = new Date();
  }
}
