import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
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

  private events = [];

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };
  rules = {}

  fullscreenLoading = false;
  processing = false;
  dialogConfirmationVisible:boolean = false;

  public eventScheduleOptions: any[] = [];

  private documentSchedule: any = {
    docEvent: null,
    submissionScheduleId: null,
    evaluationScheduleId: null,
    vendorSubmission: null,
    vendorEvaluation: null
  };

  bidding: Record<string, any> = {};
  selectedEvent: any = null;
  eventAttachmentVisible = false;
  tmpAttachments: any[] = [];

  @Watch('data')
  onDataChanged(data: any) {
    console.log('bidding schedule data changed:', data);
  }

  onAttachmentChanged(_file: any, fileList: any[]) {
    this.tmpAttachments = fileList;
  }

  onAttachmentPreviewed(file: any) {
    window.open(file.response.downloadUri, '_blank');
  }

  onAttachmentRemoved() {

  }

  created() {
    this.bidding = {...this.data};
    this.bidding.step = BiddingStep.SCHEDULE;
    this.bidding.documentSchedules = [];

    if (! this.editMode) {
      this.bidding.biddingSchedules = [];
    }

    this.retrieveReferenceList('mEvent', 'events');
    this.retrieveEventTypeLines(this.bidding.eventTypeId);
  }

  private retrieveReferenceList(code: string, prop: string) {
    if (this[prop] === void 0) {
      return;
    }

    this.commonService('/api/ad-reference-lists')
    .retrieve({
      criteriaQuery: [
        `active.equals=true`,
        `adReferenceValue.equals=${code}`
      ],
      paginationQuery: {
        page: 0,
        size: 100,
        sort: ['name']
      }
    })
    .then(res => {
      this[prop] = res.data.map((item: any) =>
        ({
            key: item.value,
            value: item.name
        })
      );
    });
  }

  private retrieveEventTypeLines(eventTypeId: number): void {
    this.commonService('/api/c-event-typelines')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `eventTypeId.equals=${eventTypeId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['sequence']
        }
      })
      .then(res => {
        this.$set(this.bidding, 'biddingSchedules', res.data);
        this.eventScheduleOptions = res.data;

      })
      .catch(err => {
        console.error('Failed to get event type lines. %O', err);
        this.$message.error('Failed to get event type lines');
      })
      .finally(() => {
        this.fullscreenLoading = false;
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

  removeDocument(index) {
    this.bidding.documentSchedule.splice(index, 1);
  }

  saveDocument() {
    this.documentSchedule.vendorSubmission = this.eventScheduleOptions
      .find(item => item.id === this.documentSchedule.submissionScheduleId);

    this.documentSchedule.vendorEvaluation = this.eventScheduleOptions
      .find(item => item.id === this.documentSchedule.evaluationScheduleId);

    this.bidding.documentSchedules.push({...this.documentSchedule});
    this.dialogConfirmationVisible = false;
    this.documentSchedule = {
      docEvent: null,
      submissionScheduleId: null,
      evaluationScheduleId: null,
      vendorSubmission: null,
      vendorEvaluation: null
    };
  }

  /**
   * Invoked before proceeding to the next step.
   */
  save() {
    this.$emit('saved', {
      data: this.bidding
    });
  }
}
