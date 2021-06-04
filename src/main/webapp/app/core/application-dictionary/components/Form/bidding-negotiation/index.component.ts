import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import BiddingNegotiationLine from './components/negotiation-line.vue';

@Component({
  components: {
    BiddingNegotiationLine
  }
})
export default class BiddingNegotiation extends mixins(AccessLevelMixin) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  index = true;
  displayTable = true;

  showSchedule=false;

  biddingNegotiations: any[] = [];
  biddingStates: any[] = [];
  biddingSchedule: any[] = [];

  negotiationsApi = '/api/m-bidding-negotiations';
  scheduleApi = '/api/m-bidding-schedules';

  selectedRow: any = {};
  
  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  created() {
    this.commonService(this.negotiationsApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true'
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res => {
      console.log(res.data);
      this.biddingNegotiations = res.data;
    });

    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        console.log(res);
        this.biddingStates = res.map(item => ({ key: item.value, value: item.name }));
      });
  }

  formatBiddingStatus(value: string) {
    return this.biddingStates.find(status => status.key === value)?.value;
  }

  viewSchedule(row: any){
    this.showSchedule = true;
    this.commonService(this.scheduleApi).retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `biddingId.equals=${row.biddingId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res => {
      console.log(res);
      this.biddingSchedule = res.data;
    });
  }

  closeSchedule(){
    this.biddingSchedule = [];
    this.showSchedule = false;
  }

  viewDetail(row){
    this.selectedRow = row;
    this.index = false;
    this.displayTable = false;
  }

  viewSchedule2(row){
    
  }

  closeDetail(){
    this.index = true;
    this.displayTable = true;
  }
}