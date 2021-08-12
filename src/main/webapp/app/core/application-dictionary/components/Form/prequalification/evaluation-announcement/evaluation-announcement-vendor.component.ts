import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
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

  biddingStatuses: any[] = [];

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

  menuUrl: string = '';

  formatBiddingStatus(value: string) {
    return this.biddingStatuses.find(status => status.key === value)?.value;
  }


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
    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        this.biddingStatuses = res.map(item => ({ key: item.value, value: item.name }));
      });
    this.retrieveBiddingResult();
  }

  private retrieveBiddingResult() {

    this.commonService("api/m-prequalification-results")
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['id,desc']
        }
      })
      .then(res => {
        this.biddingResults=res.data;

      })
      .catch(err => this.$message.error('Failed to get bidding announcement'))
    // .finally(() => this.loading = false);
  }

  view(row){
    this.index=false;
    this.pickRow=row;

  }

  async viewSchedule(row){
    console.log(row);

    const unspecifiedForm = () => {
      this.$message.error('No form specified for the selected event');
    }
    if(!this.menuUrl){
      const res = await this.commonService('api/ad-menus')
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `name.equals=Prequalification Schedule`
          ])
        });

      if (!res.data.length) {
        return unspecifiedForm();
      }

      // TODO Cache the URL.
      const menu = res.data[0];
      const url = await this.commonService('/api/ad-menus/full-path').find(menu.id);

      if (url) {
        this.menuUrl = url;
      }
    }
    const timestamp = Date.now();
      this.$router.push({
        path: this.menuUrl,
        query: {
          t: `${timestamp}`,
          prequalificationId: `${row.id}`
        }
      });
  }

  back() {
    this.index = true;
  }



}
