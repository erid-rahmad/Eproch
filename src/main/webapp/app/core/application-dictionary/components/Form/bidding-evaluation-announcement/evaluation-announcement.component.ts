import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import EmailGrid from './evaluation-announcement-email-grid.vue';
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";


@Component({
  components: {
    EmailGrid,
  }
})
export default class EventAnnouncement extends mixins(Vue2Filters.mixin,AccessLevelMixin, AlertMixin, ContextVariableAccessor) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private biddingGrid : any={};
  private pickRow:any={};


  created() {
    this.biddingInvitations();
  }

  ScheduleListVisible = false;
  editor = null;
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

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
  ]

  private biddingInvitations() {
    this.commonService('/api/m-biddings')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.biddingGrid = res.data;
        console.log("BiddingGrid",this.biddingGrid);
      });
  }

  index: boolean = true;
  page: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;

  viewBidding(row){
    console.log(row);
  }

  viewSchedule(row){
    console.log(row);
  }

  view(row, stepIndex: number = 0) {
    this.page = 3;
    console.log("this row",row);
    this.pickRow=row;
    this.ScheduleListVisible = false;
  }

  moreinfo() {
    this.page = 2;
  }

  back() {
    this.page = 1;

  }

}
