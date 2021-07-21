import Component, {mixins} from "vue-class-component";
import AlertMixin from "@/shared/alert/alert.mixin";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import biddingSubmission from"./bidding-submission.vue";
const baseApiUrl = 'api/m-biddings';
const baseApiSchedule = 'api/m-bidding-schedules';
const baseApiBiddingSchedule = 'api/m-bidding-schedules'
const baseApiInvitation = 'api/m-bidding-invitations';

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

  showJoinedVendors = false;
  loadingJoinedVendors = false;
  joinedVendors: any[] = [];

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
    this.commonService(baseApiSchedule)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingId.equals=${row.id}`
        ]),
      })
      .then(res => {
        res.data.forEach(item=>{
          if(item.formType===row.formType){
            this.scheduleFromGrid = item;
          }
        })
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

    this.commonService("/api/m-bidding-submissions/grid")
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(async res => {
        function compare(a, b) {
          // Use toUpperCase() to ignore character casing
          const bandA = a.id;
          const bandB = b.id;
          let comparison = 0;
          if (bandA < bandB) {
            comparison = 1;
          } else if (bandA > bandB) {
            comparison = -1;
          }
          return comparison;
        }
        await res.data.sort(compare);
        this.gridData = res.data
      })
      .finally(() => {
        this.processing = false;
        this.selectedRows = [];
      });
  }

  viewJoinVendor(biddingId: number) {
    this.showJoinedVendors = true;
    this.loadingJoinedVendors = true;
    this.commonService(baseApiInvitation)
      .retrieve({
        criteriaQuery: [
          `biddingId.equals=${biddingId}`,
          'invitationStatus.equals=R'
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['vendorName']
        }
      })
      .then(res => this.joinedVendors = res.data)
      .finally(() => this.loadingJoinedVendors = false);
  }
}
