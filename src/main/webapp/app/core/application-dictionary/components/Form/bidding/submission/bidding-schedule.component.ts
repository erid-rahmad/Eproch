import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins } from 'vue-property-decorator';

const BiddingScheduleProp = Vue.extend({
  props: {
    biddingId: Number,
    biddingName: String,
    biddingNo: String,
    submissionId: Number
  }
});

@Component
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

  loading = false;
  dialogConfirmationVisible:boolean = false;

  biddingSchedules: any[] = [];
  selectedEvent: any = {};

  eventStatuses: any[] = [];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  created() {
    this.retrieveEventStatuses();
    this.retrieveBiddingSchedules(this.biddingId);
  }

  private retrieveBiddingSchedules(biddingId: number) {
    this.loading = true;
    this.commonService('/api/m-bidding-schedules')
    .retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `biddingId.equals=${biddingId}`,
        'status.notEquals=N',
      ]),
      paginationQuery: {
        page: 0,
        size: 100,
        sort: ['id']
      }
    })
    .then(res => {
      this.biddingSchedules = res.data.map(item => {
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
      });
    })
    .catch(err => {
      console.error('Failed to get bidding schedules. %O', err);
      this.$message.error('Failed to get event type lines');
    })
    .finally(() => {
      this.loading = false;
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
          biddingScheduleId: `${event.id}`,
          submissionId: `${this.submissionId}`
        }
      });
      this.$emit('form-open');
    }
  }

  printStatus(status: string) {
    return this.eventStatuses.find(item => status === item.value)?.name || status;
  }
}
