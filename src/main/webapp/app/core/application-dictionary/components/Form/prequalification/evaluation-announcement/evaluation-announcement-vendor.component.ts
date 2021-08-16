import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import announcementDetail from "./components/details-announcement-vendor.vue"
import {Inject} from "vue-property-decorator";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import {AccountStoreModule as accountStore} from "@/shared/config/store/account-store";
import settings from '@/settings';

@Component({
  components: {
    announcementDetail,
  }
})
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin, AccessLevelMixin, ContextVariableAccessor) {

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

  loading = true;
  index: boolean = true;
  ScheduleListVisible = false;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 200,
    height: 200
  };

  menuUrl: string = '';

  formatBiddingStatus(value: string) {
    return this.biddingStatuses.find(status => status.key === value)?.value;
  }

  biddingSchedules = [

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
          'active.equals=true',
          `vendorId.equals=${accountStore.vendorInfo.id}`
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

  viewSchedule(row){
    this.ScheduleListVisible = true;
    this.loading = true;
    this.pickRow = row;
    
    this.commonService('/api/m-prequalification-schedules')
    .retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `prequalificationId.equals=${row.prequalificationId}`,
      ]),
      paginationQuery: {
        page: 0,
        size: 100,
        sort: ['id']
      }
    })
    .then(res => {
      this.biddingSchedules = res.data.map(item => {
        if (item.startDate && item.endDate) {
          item.schedule = [
            new Date(item.startDate),
            new Date(item.endDate)
          ];
        }

        if (item.actualStartDate && item.actualEndDate) {
          item.actual = [
            new Date(item.actualStartDate),
            new Date(item.actualEndDate)
          ];
        }

        return item;
      });
      console.log(this.biddingSchedules);
    })
    .catch(err => {
      console.error('Failed to get bidding schedules. %O', err);
      this.$message.error('Failed to get event type lines');
    })
    .finally(() => {
      this.loading = false;
    });
  }

  async viewEvent(event: any) {
    const unspecifiedForm = () => {
      this.$message.error('No form specified for the selected event');
    }

    if (!event.adFormId) {
      return unspecifiedForm();
    }

    const res = await this.commonService('api/ad-menus')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `adFormId.equals=${event.adFormId}`
        ])
      });

    if (!res.data.length) {
      return unspecifiedForm();
    }

    // TODO Cache the URL.
    const menu = res.data[0];
    const url = await this.commonService('/api/ad-menus/full-path').find(menu.id);

    if (url) {
      const timestamp = Date.now();
      this.ScheduleListVisible = false;
      this.$router.push({
        path: url,
        query: {
          t: `${timestamp}`,
          prequalificationId: `${this.pickRow.prequalificationId}`
        }
      });
    }
  }

  back() {
    this.index = true;
  }

  get dateDisplayFormat() {
    return settings.dateTimeDisplayFormat;
  }
}
