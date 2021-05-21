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


  tableData = [
    {
      0: '22-22-2021',
      1: 'Pengadaan Kendaraan Operasional',
      2: '22-22-2021',
      3: 'Belum Terdaftar',
      4: 'View Schejule',
      5: 'In progres',
      re: '3',
      7: '22/22/2021',
      8: 'Admin Tender'
    },
  ];

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

  back() {
    this.index = true;
  }



}
