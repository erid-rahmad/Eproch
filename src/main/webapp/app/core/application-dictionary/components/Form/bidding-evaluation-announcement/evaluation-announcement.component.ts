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

  private biddingGrid : any=[];
  private pickRow:any={};
  biddingStatuses: any[] = [];
  private loading:boolean=false;


  created() {

    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        this.biddingStatuses = res.map(item => ({ key: item.value, value: item.name }));
      });

    this.biddingInvitations();
  }

  ScheduleListVisible = false;
  editor = null;

  formatBiddingStatus(value: string) {
    return this.biddingStatuses.find(status => status.key === value)?.value;
  }


  private biddingInvitations() {
    this.loading=true;
    this.commonService('/api/m-biddings')
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
        this.biddingGrid = res.data;
      })
      .finally(()=>this.loading=false);
  }

  index: boolean = true;
  page: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;

  viewBidding(row){

  }

  viewSchedule(row){

  }

  view(row, stepIndex: number = 0) {
    this.page = 3;
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
