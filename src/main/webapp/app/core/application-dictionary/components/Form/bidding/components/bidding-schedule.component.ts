import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';
import DateSettingForm from './date-setting-form.vue';

const BiddingScheduleProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component({
  components: {
    DateSettingForm
  }
})
export default class BiddingSchedule extends Mixins(AccessLevelMixin, BiddingScheduleProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private updated = false;
  private recordsLoaded = true;

  // Dialog visibility flags.
  editScheduleVisible = false;
  eventAttachmentVisible = false;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };

  loadingSchedules = false;
  processing = false;
  dialogConfirmationVisible:boolean = false;

  bidding: Record<string, any> = {};
  selectedEvent: any = {};

  eventStatuses: any[] = [];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateTimeValueFormat;
  }

  get readOnly() {
    return this.bidding.biddingStatus === 'P';
  }

  @Watch('bidding', { deep: true })
  onBiddingChanged(_bidding: Record<string, any>) {
    if (this.recordsLoaded && ! this.updated) {
      this.updated = true;
      this.$emit('change');
    }
  }

  async onDateSettingSaved(dateSet: any) {
    this.recordsLoaded = false;
    const newDateSet = !this.selectedEvent.dateSetId;
    const record = {
      ...this.selectedEvent,
      ...{
        actual: [
          new Date(dateSet.startDate),
          new Date(dateSet.endDate)
        ],
        status: dateSet.status
      },
      dateSetId: dateSet.id,
    };

    try {
      if (newDateSet) {
        await this.saveSchedule(record);
      }

      const selectedIndex = this.bidding.biddingSchedules.indexOf(this.selectedEvent);
      this.bidding.biddingSchedules.splice(selectedIndex, 1, record);
    } catch (err) {
      this.$message.error('Failed when applying the new date setting');
    } finally {
      console.log('date setting updated');
      this.recordsLoaded = true;
    }
  }

  onDateSettingError(error: any) {
    console.log('Failed to save the schedule. %O', error);
    this.$message.error('Failed to save the schedule');
  }

  onScheduleChanged(row: any, value: any[]) {
    if (value == null) {
      return;
    }
    row.startDate = value[0].toISOString();
    row.endDate = value[1].toISOString();
  }

  created() {
    this.recordsLoaded = false;
    this.bidding = {...this.data};
    this.bidding.step = BiddingStep.SCHEDULE;
    console.log("this bid",this.bidding.biddingSchedules);

    this.retrieveEventStatuses();

    this.retrieveBiddingSchedules(this.bidding.id)
      .finally(() => {
        this.recordsLoaded = true;
      });
  }

  private retrieveBiddingSchedules(biddingId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingSchedules = true;
      this.commonService('/api/m-bidding-schedules')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(async res => {
        this.$set(this.bidding, 'biddingSchedules', res.data.map(item => {
          if (item.startDate && item.endDate) {
            item.schedule = [
              new Date(item.startDate),
              new Date(item.endDate)
            ];
          }

          if (item.actualStartDate && item.actualEndDate) {
            item.actual = [
              new Date(item.actualStartDate),
              new Date(item.actualEndDate)
            ];
          }

          return item;
        }));
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get bidding schedules. %O', err);
        this.$message.error('Failed to get event type lines');
        reject(false);
      })
      .finally(() => {
        this.loadingSchedules = false;
      });
    });
  }

  private retrieveEventStatuses() {
    this.commonService(null)
      .retrieveReferenceLists('eventStatus')
      .then(list => {
        this.eventStatuses = list;
      })
      .catch(err => {
        console.error('Failed getting statuses. %O', err);
        this.$message.error('Failed to get event statuses');
      });
  }

  editSchedule(event: any) {
    this.selectedEvent = event;
    if (event.status==="F"){
      this.editScheduleVisible = false;
    }else { this.editScheduleVisible = true;}
  }

  async viewEvent(event: any) {
    this.selectedEvent = event;
    const unspecifiedForm = () => {
      this.$message.error('No form specified for the selected event');
    }

    if (!event.adFormId) {
      return unspecifiedForm();
    }

    const res = await this.commonService('api/ad-menus')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `adFormId.equals=${event.adFormId}`
        ])
      });

    if (!res.data.length) {
      return unspecifiedForm();
    }

    // TODO Cache the URL.
    const menu = res.data[0];
    const url = await this.commonService('/api/ad-menus/full-path').find(menu.id);

    if (url) {
      const timestamp = Date.now();
      this.$router.push({
        path: url,
        query: {
          t: `${timestamp}`,
          biddingScheduleId: `${event.id}`
        }
      });
    }
  }

  printStatus(status: string) {
    return this.eventStatuses.find(item => status === item.value)?.name || status;
  }

  /**
   * Invoked before proceeding to the next step.
   */
  save(changeStep: boolean) {
    this.commonService('/api/m-biddings/save-form')
      .update(this.bidding)
      .then(res => {
        this.$message.success('Bidding Schedule has been saved successfully');
        this.$emit('saved', {
          data: res,
          changeStep
        });
      })
      .catch(err => {
        console.log('Failed to save bidding schedule. %O', err);
        this.$message.error('Failed to save bidding schedule');
      });
  }

  private saveSchedule(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.commonService('api/m-bidding-schedules')
        .update(data)
        .then(res => {
          this.$message.success('Date settings has been updated successfully');
          resolve(res);
        })
        .catch(err => {
          this.$message.error('Failed when updating the date settings')
          reject(err);
        });
    });
  }
}
