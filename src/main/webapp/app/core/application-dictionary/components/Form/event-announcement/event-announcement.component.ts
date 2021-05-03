import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

import AddAnnouncementForm from './components/add-announcement.vue';
import DetailsAnnouncementForm from './components/details-announcement.vue';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';


@Component({
  components: {
    AddAnnouncementForm,
    DetailsAnnouncementForm,

  }
})
export default class EventAnnouncement extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor,AccessLevelMixin) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('dynamicWindowService')
  private pushService: (baseApiUrl: string) => DynamicWindowService;


  dialogTableVisible = false;
  dialogTableVisible11 = false;
  editor = null;
  private announcmentGridData: any = {};


  index: boolean = true;
  page: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;
  private moreinfo: any = {};

  ///##########################################################################################################

  mounted() {
    this.announcmentGrid();
 
    console.log("thislog",accountStore.userDetails.cVendorId);
    
  
    
    
  }

  private announcmentGrid() {
    this.commonService('/api/c-announcements')
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
        this.announcmentGridData = res.data;
        console.log("announcmentGridData",this.announcmentGridData);

      });
  }
  public get settingsAccount(): any {
    return accountStore.account;
  }

  public get username(): string {
    return accountStore.account ? accountStore.account.login : '';
  }

  viewBidding(row){
    console.log(row);
  }

  viewSchedule(row){
    console.log(row);
  }

  view(row, stepIndex: number = 0) {
    this.page = 3;
  }

  moreInfo(row) {
    this.page = 2;
    this.moreinfo = row;

  }

  back() {
    this.page = 1;
    this.announcmentGrid();



  }



}
