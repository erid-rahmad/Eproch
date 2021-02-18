import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../../ContextVariableAccessor";

const BiddingScheduleProp = Vue.extend({
  props: {
    biddingInformation: {
      type: Object,
      default: () => {}
    },
    biddingSchedule: {
      type: Object,
      default: () => {}
    },
  }
})

@Component
export default class BiddingSchedule extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, BiddingScheduleProp) {

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

  public eventScheduleOptions: any = {};

  private baseApiUrlEventTypeLine = "/api/c-event-typelines";

  private documentSchedule:any = {
    docEvent: "",
    vendorSubmission: "",
    vendorEvaluation: "",
    vendorSub: "",
    vendorEval: ""
  };

  created() {
    this.eventTypeLine(this.biddingInformation.eventTypeId);
  }

  private eventTypeLine(eventTypeId): void {
    if ( ! this.baseApiUrlEventTypeLine) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlEventTypeLine)
      .retrieve({
        criteriaQuery: `eventTypeId.equals=${eventTypeId}`
      })
      .then(res => {

        this.$set(this.biddingSchedule, 'eventSchedule', res.data.map((item: any) => {
          return item;
        }));

        this.eventScheduleOptions = this.biddingSchedule.eventSchedule;
        console.log(this.biddingInformation);

      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

  addDocument(){
    this.dialogConfirmationVisible = true;
  }

  removeDocument(index){
    this.biddingSchedule.documentSchedule.splice(index, 1);
  }

  saveDocument(){

    //this.documentSchedule.vendorSub = this.documentSchedule.vendorSubmission.event + ' (Start: '+this.documentSchedule.vendorSubmission.startDate+' - End: '+ this.documentSchedule.vendorSubmission.endDate+ ')';
    //this.documentSchedule.vendorEval = this.documentSchedule.vendorEvaluation.event + ' (Start: '+this.documentSchedule.vendorEvaluation.startDate+' - End: '+ this.documentSchedule.vendorEvaluation.endDate+ ')';

    this.documentSchedule.vendorSub = this.eventScheduleOptions.find(item => item.id === this.documentSchedule.vendorSubmission);
    this.documentSchedule.vendorEval = this.eventScheduleOptions.find(item => item.id === this.documentSchedule.vendorEvaluation);

    console.log(this.documentSchedule);
    console.log(this.biddingSchedule);

    this.biddingSchedule.documentSchedule.push(this.documentSchedule);
    this.dialogConfirmationVisible = false;
    this.documentSchedule = {
      docEvent: "",
      vendorSubmission: "",
      vendorEvaluation: "",
      vendorSub: "",
      vendorEval: ""
    };
  }

  //=======================================================================

  validate() {
    (this.$refs.productCatalog as ElForm).validate((passed, errors) => {
      if(passed){
        //this.submit();
      }else{
        console.log(errors);
      }

    });
  }

}
