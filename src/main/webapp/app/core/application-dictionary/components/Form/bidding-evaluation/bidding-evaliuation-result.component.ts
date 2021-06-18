import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import ResultDetail from './components/result-detail.vue';

const baseApiEvalResults = 'api/m-bidding-eval-results';
const baseApiNegotiation ='api/m-bidding-negotiations';
const baseApiBiddingSchedule='api/m-bidding-schedules';

const ProductCatalogProp = Vue.extend({
  props: {
    pickRow: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
})

@Component({
  components: {
    ResultDetail,
  }
})
export default class ProductInformation extends mixins(Vue2Filters.mixin, AlertMixin, AccessLevelMixin, ProductCatalogProp) {

  index = true;
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  private evaluationResult: any = [];
  private evaluationResultProp: any = {};
  private loading: boolean = false;
  private show: boolean = false;
  private selectedWiner = [];
  private disableSubmit:boolean=false;

  created() {
    this.retriveEvaluationResult(this.pickRow.id);
  }

  getStatus(status){
    if (status==="SMT"){
      return "Submitted"
    }
    else if (status==="APP"){
      return "Approved"
    }
    else if (status==="RJC"){
      return "Rejected"
    }
    else {return "Drafted"}
  }

  // tableRowClassName({row, rowIndex}) {
  //   console.log("row index",row)
  //   if (row.winnerStatus === true) {
  //     return 'success-row';
  //   } else {
  //     return 'warning-row';
  //   }
  // }

  retriveEvaluationResult(biddingId) {
    this.loading = true;
    this.commonService(baseApiEvalResults)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['score,desc']
        }
      })
      .then(res => {
        let data_: any = []
        let x =1;

        res.data.forEach(data => {
          if (data.biddingId === biddingId) {

            data.rank=x;
            if (data.winnerStatus===true){
              this.disableSubmit=true;
            }
            data_.push(data);
            x++;
          }
        })
        this.evaluationResult = data_;
        console.log("this ",this.evaluationResult)
        console.log("this ",this.evaluationResult.biddingId)

      })
      .finally(() => this.loading = false);
  }

  onRecipientSelectionChanged(val) {
    this.selectedWiner = val;
  }

  updateWinnerStatus(){
    this.selectedWiner.forEach(item=>{
      console.log("this item");
      item.winnerStatus=true;
      this.commonService(baseApiEvalResults)
        .create(item)
        .then(_res => {
          this.$message.success(`successfully`);
          this.retriveEvaluationResult(this.pickRow.id)
        })
        .catch(err => {
        })
    })
  }

  createTableNegotiation(){
    const bidingNego ={
      biddingEvalResultId:'',//data.id,
      biddingScheduleId:'',//biddingSchedule.id,
      adOrganizationId:'',//data.adOrganizationId,
      startDate:'',//biddingSchedule.startDate,
      endDate:'',//biddingSchedule.startDate
      active:true,
      line:[]
    }
    let max = this.selectedWiner.length;
    this.selectedWiner.forEach((data,idx,array)=>{
      let biddingSchedule:any={};
      let curIdx = idx;
      this.commonService(baseApiBiddingSchedule)
        .retrieve({
          criteriaQuery: this.updateCriteria([
            `biddingId.equals=${this.pickRow.id}`
          ])
        })
        .then(res => {
          res.data.forEach(schedule=>{
//need Change
            if (schedule.formType=="AN"){
              biddingSchedule.startDate=schedule.startDate;
              biddingSchedule.endDate=schedule.endDate;
              biddingSchedule.id=schedule.id;
            }
          });
          bidingNego.biddingEvalResultId = data.id;
          bidingNego.biddingScheduleId = biddingSchedule.id;
          bidingNego.adOrganizationId = data.adOrganizationId;
          bidingNego.startDate = biddingSchedule.startDate;
          bidingNego.endDate = biddingSchedule.endDate;
          bidingNego.line.push({active:true,
            negotiationStatus:'not started',
            adOrganizationId:data.adOrganizationId,
            biddingEvalResultId:data.id});

          if(curIdx==max-1){
            console.log(bidingNego);
            this.commonService(baseApiNegotiation)
            .create(bidingNego)
            .then(res => {
              this.$message.success('create record negotiation');
              this.updateWinnerStatus();
            })
            .catch(_err => this.$message.error('fail create record negotiation'))
            .finally(()=>{});
          }
        })
        .catch(err => this.$message.error('Failed to get baseApiBiddingSchedule'))
    })
  }

  detailScore(row) {
    this.index = false;
    this.evaluationResultProp = row;
  }

  close() {
    this.$emit("close");
  }

  close_() {
    this.index = true;
  }
}
