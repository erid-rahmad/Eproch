import Component, {mixins} from "vue-class-component";
import AlertMixin from "@/shared/alert/alert.mixin";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import biddingSubmission from"./bidding-submission.vue";
const baseApiUrl = 'api/m-biddings';
const baseApiSchedule = 'api/m-bidding-schedules';


@Component({
  components: {
    biddingSubmission,


  }
})
export default class BiddingSubmissionGridComponent extends mixins( AlertMixin,AccessLevelMixin) {
  index: boolean = true;
  gridData: any[] = [];
  processing = false;
  public totalItems = 0;
  public queryCount: number = null;
  selectedRows: any[] = [];
  stepIndex: number = 0;
  biddingStatuses: any[] = [];
  scheduleFromGrid:any={};

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;


  formatBiddingStatus(value: string) {
    return this.biddingStatuses.find(status => status.key === value)?.value;
  }

  created() {
    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        this.biddingStatuses = res.map(item => ({ key: item.value, value: item.name }));
      });
   this.retrieveAllRecords();
  }

  view(row){

    console.log("this row",row)

    this.commonService(baseApiSchedule)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${row.id}`
        ]),
      })
      .then(res => {
        res.data.forEach(item=>{
          if(item.formType==="S1"){
            this.scheduleFromGrid = item;
          }
        })
        console.log("this schedule",this.scheduleFromGrid);
        this.index=false;
      })
      .catch(err => {
        console.error('Failed to get bidding schedule. %O', err);
        this.$message.error('Failed to get schedule details');
      })
      .finally(() => {
      });

  }

  close(){
    this.index=true;
  }



  public retrieveAllRecords(): void {
    this.processing = true;
    this.commonService(baseApiUrl)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
      })
      .then(res => {
        this.gridData = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;


      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.processing = false;
        this.selectedRows = [];
      });
  }




}
