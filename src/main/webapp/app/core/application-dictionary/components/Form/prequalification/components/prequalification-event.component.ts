import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { reject } from 'lodash';
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
  loadingDetails = false;
  processing = false;
  showDetail = false;
  showPic = false;

  preq: Record<string, any> = {};
  selectedEvent: any = {};

  eventOptions: any[] = [];
  methodOptions: any[] = [];

  eventSteps: any[] = [];

  evaluationMethodCriteria: any[] = [];
  members: any[] = [];

  requirements: Map<number, any> = new Map();

  positions: any[] = [
    {name:'Evaluator Administrasi', id:'E'},
    {name:'Reviewer', id:'R'},
    {name:'Approver', id:'A'}
  ];

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateTimeValueFormat;
  }

  get readOnly() {
    return this.preq.status !== 'N';
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
          'active.equals=true',
          `prequalificationId.equals=${preqId}`
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
      sort:['sequence','id']
    }
    }).then((res)=>{
      this.eventSteps = res.data;
    })
  }

  // -----------------------------------------------------------

  loadDetail(){
    this.showDetail = true;
    this.loadingDetails = true;
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
        this.evaluationMethodCriteria.forEach((method)=>{
          method.criteria.forEach((criteria) => {
            criteria.subCriteria.forEach((subCriteria) => {
              subCriteria.questions.forEach((question) => {
                question.prequalMethodCriteriaId = criteria.id
                question.prequalMethodSubCriteriaId = subCriteria.id
                question.biddingSubCriteriaLineId = question.id
                
                question.requirement = null;
                this.requirements.set(question.id, question);
              });
            });
          });
        });
        this.loadingDetails = false;
        console.log(this.evaluationMethodCriteria)
        this.loadRequirements(this.preq.id);
      });
    })
  }

  saveRequirements(){
    console.log(this.requirements);
    let save: any[] = [];
    this.requirements.forEach((value, key)=>{
      save.push({
        id: value.criteriaId,
        active: true,
        uid: value.criteriaUid,
        requirement: value.requirement,
        adOrganizationId: this.preq.adOrganizationId,
        prequalificationId: this.preq.id,
        prequalMethodCriteriaId: value.prequalMethodCriteriaId,
        prequalMethodSubCriteriaId: value.prequalMethodSubCriteriaId,
        biddingSubCriteriaLineId: value.biddingSubCriteriaLineId
      })
    })

    console.log(save);
    
    this.commonService("/api/m-prequalification-criteria/requirements").create(save)
    .then(_res => this.$message.success(`Requirements has been saved successfully`))
    .catch(err => {
      console.error('Failed to save the criteria. %O', err);
      this.$message.error(`Failed to save prequalification criteria`);
    })
  }

  loadRequirements(id: number){
    this.commonService("/api/m-prequalification-criteria").retrieve({
      criteriaQuery: [
        'active.equals=true',
        `prequalificationId.equals=${id}`
      ],
      paginationQuery: {
        page: 0,
        size: 1000,
        sort:['id']
      }
    }).then((res)=>{
      console.log(res.data);
      res.data.forEach(element => {
        let q = this.requirements.get(element.biddingSubCriteriaLineId)
        if(q) {
          q.criteriaId = element.id;
          q.criteriaUid = element.uid;
          q.requirement = element.requirement;
        }
      });
    })
  }

  // -----------------------------------------------------------

  loadPic(){
    Promise.allSettled([
      new Promise<boolean>((resolve,reject)=>{this.commonService("/api/m-bidding-evaluation-teams").retrieve({
        criteriaQuery: [
          'active.equals=true',
          `prequalificationId.equals=${this.preq.id}`
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort:['id']
        }
      }).then((res)=>{
        if(res.data.length){
          Promise.allSettled([
            new Promise<boolean>((resolve,reject)=>{
              this.commonService("/api/m-bidding-eval-team-lines").retrieve({
                criteriaQuery: [
                  'active.equals=true',
                  `evaluationTeamId.equals=${res.data[0].id}`
                ],
                paginationQuery: {
                  page: 0,
                  size: 1000,
                  sort:['id']
                }
              }).then((res)=>{
                this.members = res.data.map((item)=>{
                  item.adUserName = `${item.adUserName?item.adUserName:""} ${item.adUserLastName?item.adUserLastName:""}`
                  return item;
                });
                resolve(true);
              }).catch((err)=>{
                console.log(err);
                reject(false);
              });
            })
          ]).then((res)=>{
            resolve(true);
          })
        } else reject(false);
      }).catch((err)=>{
        console.log(err);
        reject(false);
      });
    })
    ]).then((res)=>{
      this.showPic = true;
      console.log(this.members);
    })
  }

  formatPosition(val){
    return this.positions.find(status => status.id === val)?.name;
  }
}
