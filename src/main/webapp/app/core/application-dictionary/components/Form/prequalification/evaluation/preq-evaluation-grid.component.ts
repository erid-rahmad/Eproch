import Component, {mixins} from "vue-class-component";
import AlertMixin from "@/shared/alert/alert.mixin";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import PreqEvaluation from"./preq-evaluation.vue";
import EvaluationResult from"./evaluation-result.vue";
const baseApiUrl = 'api/m-biddings';
const baseApiSchedule = 'api/m-bidding-schedules';
const baseApiBiddingSchedule = 'api/m-bidding-schedules'
const baseApiInvitation = 'api/m-bidding-invitations';

@Component({
  components: {
    PreqEvaluation,
    EvaluationResult
  }
})
export default class PrequalificationSubmissionGridComponent extends mixins( AlertMixin,AccessLevelMixin) {
  index: number = 0;
  gridData: any[] = [];
  processing = false;
  public totalItems = 0;
  public queryCount: number = null;
  selectedRows: any[] = [];
  stepIndex: number = 0;
  biddingStatuses: any[] = [];

  selectedRow: any = {};

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
    this.selectedRow = row;
    this.index=1;
  }

  result(row){
    this.selectedRow = row;
    this.index=2;
  }

  close(){
    this.index=0;
  }

  public retrieveAllRecords(): void {
    this.processing = true;

    this.commonService("/api/m-prequalification-informations")
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(async res => {
        this.gridData = res.data
      })
      .finally(() => {
        this.processing = false;
        this.selectedRows = [];
      });
  }
}
