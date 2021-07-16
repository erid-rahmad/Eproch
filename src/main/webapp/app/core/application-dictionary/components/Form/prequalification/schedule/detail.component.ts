import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import DateSettingForm from '@/core/application-dictionary/components/Form/bidding/components/date-setting-form.vue';

const PreqScheduleProp = Vue.extend({
  props: {
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
export default class PrequalificationSchedule extends Mixins(AccessLevelMixin, PreqScheduleProp) {

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

  preq: Record<string, any> = {};
  selectedEvent: any = {};

  eventStatuses: any[] = [];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateTimeValueFormat;
  }

  get readOnly() {
    return this.preq.biddingStatus === 'P';
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

      const selectedIndex = this.preq.preqSchedules.indexOf(this.selectedEvent);
      this.preq.preqSchedules.splice(selectedIndex, 1, record);
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
    this.preq = {...this.data};

    this.retrieveEventStatuses();

    this.retrievePreqSchedules(this.preq.id)
      .finally(() => {
        this.recordsLoaded = true;
      });
  }

  private retrievePreqSchedules(biddingId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingSchedules = true;
      this.commonService('/api/m-prequalification-schedules')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `prequalificationId.equals=${biddingId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id']
        }
      })
      .then(async res => {

        this.$set(this.preq, 'preqSchedules', res.data.map(item => {
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

        console.log(res.data);
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
    let S:String="A";
    if(event.status){
      S=event.status;
    }
    if (S==="N"){
      let a=0
      this.preq.preqSchedules.forEach(item=>{
        if (item.status==="P"){ a=1;}
      })
      if (a===0){
        this.editScheduleVisible = true;
      }
    }
    if (S==="P"){
      this.editScheduleVisible = true;
    }
    if (S==="F"){
    }
    if(S==="A") {
      let a=0
      this.preq.preqSchedules.forEach(item=>{
        if (item.status==="P"){ a=1;}
      })
      if (a===0){
        this.editScheduleVisible = true;
      }
    }

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

  onFormSaved() {
    console.log(this.preq);
    
    this.commonService('/api/m-prequalification-informations/save-form')
    .update(this.preq)
    .then(res => {
      this.$message.success('Prequalification Schedule has been saved successfully');
      this.$emit('saved', {
        data: res
      });
    })
    .catch(err => {
      console.log('Failed to save bidding schedule. %O', err);
      this.$message.error('Failed to save bidding schedule');
    });
  }

  private saveSchedule(data: any): Promise<any> {
    return new Promise((resolve, reject) => {
      this.commonService('api/m-prequalification-schedules')
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
