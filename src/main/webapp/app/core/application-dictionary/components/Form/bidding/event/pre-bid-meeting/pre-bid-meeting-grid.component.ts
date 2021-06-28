import AccountService from '@/account/account.service';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import ScheduleEventMixin from '@/core/application-dictionary/mixins/ScheduleEventMixin';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { Component, Inject, Mixins } from "vue-property-decorator";
import PreBidMeeting from './pre-bid-meeting.vue';
import ParticipantList from './participant-list.vue';

const baseApiPreBidMeeting = 'api/m-pre-bid-meetings';
const baseApiPreBidMeetingAttachment = 'api/m-pre-bid-meeting-attachments'

@Component({
  components: {
    ParticipantList, PreBidMeeting
  }
})
export default class PreBidMeetingGrid extends Mixins(ScheduleEventMixin) {
  index = true;
  selectedRow = {};

  @Inject('accountService')
  private accountService: () => AccountService;

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private preBidMeetingGrid:any={};
  biddingStatuses: any[] = [];

  created() {
    this.retrievePreBidMeeting();
    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        this.biddingStatuses = res.map(item => ({ key: item.value, value: item.name }));
      });
  }

  formatBiddingStatus(value: string) {
    return this.biddingStatuses.find(status => status.key === value)?.value;
  }

  private retrievePreBidMeeting() {
      this.loading = true;
      this.commonService(baseApiPreBidMeeting)
        .retrieve({
          criteriaQuery: this.updateCriteria([
            // 'active.equals=true',
          ]),
          paginationQuery: {
            page: 0,
            size: 100,
            sort: ['id']
          }
        })
        .then(res => {
          this.preBidMeetingGrid=res.data;
          console.log(this.preBidMeetingGrid);
        })
        .catch(err => {
          console.error('Failed to get bidding schedules. %O', err);
          this.$message.error('Failed to get schedule details');
        })
        .finally(() => {
          this.loading = false;
        });
  }
  
  openDetail(row: any){
    this.index=false;
    this.selectedRow = row;
  }

  closeDetail(){
    this.index=true;
  }
}
