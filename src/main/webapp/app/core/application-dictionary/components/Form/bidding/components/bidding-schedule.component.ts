import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import { format, parseISO } from 'date-fns';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';

const BiddingScheduleProp = Vue.extend({
  props: {
    editMode: Boolean,
    data: {
      type: Object,
      default: () => {}
    }
  }
});

@Component
export default class BiddingSchedule extends Mixins(AccessLevelMixin, BiddingScheduleProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private updated = false;
  private recordsLoaded = true;

  private events = [];

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}

  loadingSchedules = false;
  loadingDocumentSchedules = false;
  processing = false;
  dialogConfirmationVisible:boolean = false;

  public eventScheduleOptions: any[] = [];

  private documentSchedule: any = {
    docEvent: null,
    vendorSubmissionId: null,
    vendorEvaluationId: null,
    vendorSubmission: null,
    vendorEvaluation: null
  };

  bidding: Record<string, any> = {};
  selectedEvent: any = null;
  eventAttachmentVisible = false;
  tmpAttachments: any[] = [];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateTimeValueFormat;
  }

  @Watch('bidding', { deep: true })
  onBiddingChanged(_bidding: Record<string, any>) {
    if (this.recordsLoaded && ! this.updated) {
      this.updated = true;
      this.$emit('change');
    }
  }

  onAttachmentChanged(_file: any, fileList: any[]) {
    this.tmpAttachments = fileList;
  }

  onAttachmentPreviewed(file: any) {
    window.open(file.response.downloadUri, '_blank');
  }

  onAttachmentRemoved() {

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

    this.commonService(null)
      .retrieveReferenceLists('mEvent')
      .then(res => {
        this.events = res.map(item => ({ key: item.value, value: item.name }))
      });

    Promise.allSettled([
      this.retrieveBiddingSchedules(this.bidding.id),
      this.retrieveDocumentSchedules(this.bidding.id)
    ])
    .then(_results => {
      this.recordsLoaded = true;
    })
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
          sort: ['startDate', 'endDate']
        }
      })
      .then(res => {
        this.$set(this.bidding, 'biddingSchedules', res.data.map(item => {
          if (item.startDate && item.endDate) {
            item.schedule = [
              new Date(item.startDate),
              new Date(item.endDate)
            ];
          }
          return item;
        }));
        this.eventScheduleOptions = this.bidding.biddingSchedules;
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

  private retrieveDocumentSchedules(biddingId: number): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.loadingDocumentSchedules = true;
      this.commonService('/api/m-document-schedules')
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
      .then(res => {
        this.$set(this.bidding, 'documentSchedules', res.data);
        this.$set(this.bidding, 'removedDocumentSchedules', []);
        resolve(true);
      })
      .catch(err => {
        console.error('Failed to get document schedules. %O', err);
        this.$message.error('Failed to get event type lines');
        reject(false);
      })
      .finally(() => {
        this.loadingDocumentSchedules = false;
      });
    });
  }

  addDocument() {
    this.dialogConfirmationVisible = true;
  }

  editAttachments(event: any) {
    this.selectedEvent = event;
    this.eventAttachmentVisible = true;
  }

  printEventName(code: string) {
    return this.events.find(event => event.key === code)?.value || code;
  }

  printScheduleLabel(record: any) {
    const startTime = !record.startDate ? '' : format(parseISO(record.startDate), DATE_TIME_FORMAT);
    const endTime = !record.endDate ? '' : format(parseISO(record.endDate), DATE_TIME_FORMAT);
    return `${this.printEventName(record.eventTypeLineName)} (Start: ${startTime} - End: ${endTime})`;
  }

  removeDocument(row: any, index: number) {
    this.bidding.documentSchedules.splice(index, 1);
    this.bidding.removedDocumentSchedules.push(row);
  }

  saveDocument() {
    this.documentSchedule.vendorSubmission = this.eventScheduleOptions
      .find(item => item.id === this.documentSchedule.vendorSubmissionId);

    this.documentSchedule.vendorEvaluation = this.eventScheduleOptions
      .find(item => item.id === this.documentSchedule.vendorEvaluationId);

    const schedule = {...this.documentSchedule};
    schedule.vendorSubmissionStartDate = schedule.vendorSubmission.startDate;
    schedule.vendorSubmissionEndDate = schedule.vendorSubmission.endDate;
    schedule.vendorEvaluationStartDate = schedule.vendorEvaluation.startDate;
    schedule.vendorEvaluationEndDate = schedule.vendorEvaluation.endDate;

    this.bidding.documentSchedules.push(schedule);
    this.dialogConfirmationVisible = false;
    this.documentSchedule = {
      docEvent: null,
      vendorSubmissionId: null,
      vendorEvaluationId: null,
      vendorSubmission: null,
      vendorEvaluation: null
    };
  }

  /**
   * Invoked before proceeding to the next step.
   */
  save() {
    this.commonService('/api/m-biddings/save-form')
      .update(this.bidding)
      .then(res => {
        this.$emit('saved', {
          data: res
        });
      })
      .catch(err => {
        console.log('Failed to save bidding schedule. %O', err);
        this.$message.error('Failed to save bidding schedule')
      });
  }
}
