import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import { log } from 'util';

const BiddingScheduleProp = Vue.extend({
  props: {
    ItemDetail: {
      type: Object,
      default: () => {}
    },
    biddingSchedule: {
      type: Object,
      default: () => {}
    },
    biddingrow: {
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
  public projectinformation: any = {};


  private baseApiUrlEventTypeLine = "/api/c-event-typelines";
  
  data() {
    return {
      projectinformation: [
        {
          no: '1',
          information: 'Bidding proposal',
          atachment: 'Bidding proposal Doc',
        }    
      ]
    }
  };

  private documentSchedule:any = {
    docEvent: "",
    vendorSubmission: "",
    vendorEvaluation: "",
    vendorSubmissionObj: "",
    vendorEvaluationObj: ""
  };

  created() {
    this.eventTypeLine(this.ItemDetail);
    this.projectinformationData();
    console.log("this item detail", this.ItemDetail);
    console.log("this item row",this.biddingrow);
    
  }

  private projectinformationData() {
    this.dynamicWindowService(`/api/m-project-informationsby-bidding/${this.biddingrow.id}`)
    // this.dynamicWindowService("/api/m-biddings/1")
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.projectinformation = res.data;
        
        console.log("this more information", this.projectinformation);
      });
  }

  downloadAttachment(row) {
    console.log(row);    
    window.open(`/api/c-attachments/download/${row.attachmentid}-${row.attachmentName}`, '_blank');
  }
  
  private eventTypeLine(eventTypeId): void {
    if ( ! this.baseApiUrlEventTypeLine) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlEventTypeLine)
      .retrieve({
        criteriaQuery: `eventTypeId.equals=${eventTypeId}&sort=sequence`
      })
      .then(res => {

        this.$set(this.biddingSchedule, 'eventSchedule', res.data.map((item: any) => {
          return item;
        }));

        this.eventScheduleOptions = this.biddingSchedule.eventSchedule;
        //console.log(this.biddingInformation);

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

    this.documentSchedule.vendorSubmissionObj = this.eventScheduleOptions.find(item => item.id === this.documentSchedule.vendorSubmission);
    this.documentSchedule.vendorEvaluationObj = this.eventScheduleOptions.find(item => item.id === this.documentSchedule.vendorEvaluation);

    //console.log(this.documentSchedule);
    //console.log(this.biddingSchedule);

    this.biddingSchedule.documentSchedule.push(this.documentSchedule);
    this.dialogConfirmationVisible = false;
    this.documentSchedule = {
      docEvent: "",
      vendorSubmission: "",
      vendorEvaluation: "",
      vendorSubmissionObj: "",
      vendorEvaluationObj: ""
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
