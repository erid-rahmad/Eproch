import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { BiddingStep } from '../steps-form.component';
//import DateSettingForm from './date-setting-form.vue';

const PreqEventProp = Vue.extend({
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
    //DateSettingForm
  }
})
export default class PreqEvent extends Mixins(AccessLevelMixin, PreqEventProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private updated = false;
  private recordsLoaded = true;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };

  loadingSchedules = false;
  processing = false;
  showDetail = false;

  preq: Record<string, any> = {};
  selectedEvent: any = {};

  eventOptions: any[] = [];
  methodOptions: any[] = [];

  eventSteps: any[] = [];

  evaluationMethodCriteria: any[] = [];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateTimeValueFormat;
  }

  get readOnly() {
    return this.preq.status === 'P';
  }

  @Watch('preq', { deep: true })
  onBiddingChanged(_bidding: Record<string, any>) {
    if (this.recordsLoaded && ! this.updated) {
      this.updated = true;
      this.$emit('change');
    }
  }

  created() {
    this.recordsLoaded = false;
    this.preq = {...this.data};
    this.preq.step = BiddingStep.EVENT;

    Promise.allSettled([
      this.retrieveEvent(this.preq.id),
      this.loadPrequalificationEvents(),
      this.loadPrequalificationMethods(),
    ]).finally(() => {
      this.recordsLoaded = true;
    });
  }

  private retrieveEvent(preqId: number): Promise<boolean> {
    return new Promise((resolve,reject)=>{
      this.commonService("/api/m-prequalification-events").retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      }).then((res)=>{
        if((<any[]>res.data).length){
          this.preq.event = res.data[0];
          this.updatePrequalificationSteps();
        }
        resolve(true);
      }).catch((err)=>{
        console.error('Failed getting prequalification event data. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
        reject(false);
      })
    })
  }

  private loadPrequalificationEvents(): Promise<boolean> {
    return new Promise((resolve,reject) => {
      this.commonService("/api/c-prequalification-events")
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      })
      .then(async res => {
        this.eventOptions = res.data.map((item: any) => {
          return {
            key: item.id,
            value: item.name,
            code: item.code
          };
        });
        resolve(true);
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
        reject(false);
      });
    });
  }

  private loadPrequalificationMethods(): Promise<boolean> {
    return new Promise((resolve,reject) => {
      this.commonService("/api/c-prequalification-methods")
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      })
      .then(async res => {
        this.methodOptions = res.data.map((item: any) => {
          return {
            key: item.id,
            value: item.name,
            code: item.code
          };
        });
        resolve(true);
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
        reject(false);
      });
    });
  }


  /**
   * Invoked before proceeding to the next step.
   */
  save(changeStep: boolean) {
    this.commonService('/api/m-prequalification-informations/save-form')
      .update(this.preq)
      .then(res => {
        this.$message.success('Prequalification Event has been saved successfully');
        this.$emit('saved', {
          data: res,
          changeStep
        });
      })
      .catch(err => {
        console.log('Failed to save prequalification event. %O', err);
        this.$message.error('Failed to save prequalification event');
      });
  }

  updatePrequalificationSteps(){
    this.commonService("/api/c-prequalification-event-lines").retrieve({
    criteriaQuery: [
      'active.equals=true',
      `prequalificationEventId.equals=${this.preq.event.eventId}`
    ],
    paginationQuery: {
      page: 0,
      size: 1000,
      sort:['id','sequence']
    }
    }).then((res)=>{
      this.eventSteps = res.data;
    })
  }

  loadDetail(){
    this.commonService("/api/c-prequal-method-lines").retrieve({
      criteriaQuery: [
        'active.equals=true',
        `prequalMethodId.equals=${this.preq.event.methodId}`
      ],
      paginationQuery: {
        page: 0,
        size: 1000,
        sort:['id']
      }
    }).then((res)=>{
      this.evaluationMethodCriteria = res.data;
      Promise.allSettled(
        this.evaluationMethodCriteria.map((row)=>{
          return new Promise<boolean>((resolve,reject)=>{
            this.commonService("/api/c-prequal-method-criteria").retrieve({
              criteriaQuery: [
                'active.equals=true',
                `prequalMethodLineId.equals=${row.id}`
              ],
              paginationQuery: {
                page: 0,
                size: 1000,
                sort:['id']
              }
            }).then((res)=>{
              row.criteria = res.data;
              Promise.allSettled(row.criteria.map((row)=>{
                return new Promise<boolean>((resolve,reject)=>{
                  this.commonService("/api/c-prequal-method-sub-criteria").retrieve({
                    criteriaQuery: [
                      'active.equals=true',
                      `prequalMethodCriteriaId.equals=${row.id}`
                    ],
                    paginationQuery: {
                      page: 0,
                      size: 1000,
                      sort:['id']
                    }
                  }).then((res)=>{
                    row.subCriteria = res.data;
                    Promise.allSettled(row.subCriteria.map((row)=>{
                      return new Promise<boolean>((resolve,reject)=>{
                        this.commonService("/api/c-bidding-sub-criteria-lines").retrieve({
                          criteriaQuery: [
                            'active.equals=true',
                            `biddingSubCriteriaId.equals=${row.biddingSubCriteriaId}`
                          ],
                          paginationQuery: {
                            page: 0,
                            size: 1000,
                            sort:['id']
                          }
                        }).then((res)=>{
                          row.questions = res.data;
                          resolve(true);
                        }).catch((err)=>{
                          console.log(err);
                          reject(false);
                        })
                      })
                    })).then((res)=>{
                      resolve(true);
                    })
                  }).catch((err)=>{
                    console.log(err);
                    reject(false);
                  })
                });
              })).then((res)=>{
                resolve(true);
              });
            }).catch((err)=>{
              console.log(err);
              reject(false);
            });
          })
        })
      ).then((res)=>{
        this.showDetail = true;
        console.log(this.evaluationMethodCriteria)
      });
    })
  }
}
