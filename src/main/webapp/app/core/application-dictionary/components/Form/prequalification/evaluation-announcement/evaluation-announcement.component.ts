import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import EmailGrid from './evaluation-announcement-email-grid.vue';
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

const baseApiPrequalificationSchedule = 'api/m-prequalification-schedules'


@Component({
  components: {
    EmailGrid,
  }
})
export default class EventAnnouncement extends mixins(Vue2Filters.mixin,AccessLevelMixin, AlertMixin, ContextVariableAccessor) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private prequalGrid : any=[];
  private pickRow:any={};
  biddingStatuses: any[] = [];
  private loading:boolean=false;

  private menuUrl = '';

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
    this.commonService('/api/m-prequalification-informations')
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

        const data:any=[]
        res.data.forEach(item=>{
          this.commonService(baseApiPrequalificationSchedule)
            .retrieve({
              criteriaQuery: this.updateCriteria([
                `prequalificationId.equals=${item.id}`,
              ]),
              paginationQuery: {
                page: 0,
                size: 1,
                sort: ['id,desc']
              }
            })
            .then(res1 =>{
              const data1 = { ...res1.data[0]};
              if (data1.actualStartDate){
                data.push(item);
              }
            });
        });

        this.prequalGrid = data;
      })
      .finally(()=>this.loading=false);
  }

  index: boolean = true;
  page: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;

  viewBidding(row){

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
