import Component, {mixins} from "vue-class-component";
import AlertMixin from "@/shared/alert/alert.mixin";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import biddingSubmission from"../../submission/registered-bidding-list.vue";
const baseApiUrl = 'api/m-biddings';


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
    this.index=false;

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
