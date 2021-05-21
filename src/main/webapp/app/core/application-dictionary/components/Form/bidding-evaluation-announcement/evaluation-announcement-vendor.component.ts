import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import announcementDetail from "./components/details-announcement-vendor.vue"
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import {AccountStoreModule as accountStore} from "@/shared/config/store/account-store";


@Component({
  components: {
    announcementDetail,
  }
})
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin,AccessLevelMixin, ContextVariableAccessor) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private vendorId = '';
  get isVendor() {
    return accountStore.isVendor;
  }
  public get settingsAccount(): any {
    return accountStore.account;
  }
  public get username(): string {
    return accountStore.account ? accountStore.account.login : '';
  }

  private biddingResults:any={};
  private pickRow:any={};

  index: boolean = true;
  ScheduleListVisible = false;


  BiddingSchedule = [
    {
      event: 'Bidding Announcement',
      startdate: '20-03-2021',
      finisdate:'20-03-2021',
    },
    {
      event: 'Bidding Registration',
      startdate: '21-03-2021',
      finisdate:'21-03-2021',
    },
    {
      event: 'Pre-Bid Meeting',
      startdate: '22-03-2021',
      finisdate:'22-03-2021',
    },
    {
      event: 'Bidding Submission',
      startdate: '23-03-2021',
      finisdate:'23-03-2021',
    },
    {
      event: 'Bidding Evaluation',
      startdate: '23-03-2021',
      finisdate:'23-03-2021',
    },
    {
      event: 'Bidding Evaluation Announcement',
      startdate: '23-03-2021',
      finisdate:'23-03-2021',
    },

  ]

  created() {
    this.retrieveBiddingResult();
  }

  private retrieveBiddingResult() {

    this.commonService("api/m-bidding-results")
      .retrieve({
        criteriaQuery: this.updateCriteria([
          // `vendorId.equals=${this.vendorId}`
        ])
      })
      .then(res => {
        this.biddingResults=res.data;
        console.log("this result",this.biddingResults)
      })
      .catch(err => this.$message.error('Failed to get bidding announcement'))
    // .finally(() => this.loading = false);
  }

  view(row){
    this.index=false;
    this.pickRow=row;

  }

  back() {
    this.index = true;
  }



}
