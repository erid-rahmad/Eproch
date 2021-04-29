import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

import AddAnnouncementForm from './components/add-announcement.vue';
import DetailsAnnouncementForm from './components/details-announcement.vue';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';


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


  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

  gridData1 = [
    {
      documentNo: 'BD-00001',
      name: 'pengadaan kendaraan operasional',
      biddingTypeName: 'Tender Goods',
      documentStatus: 'In Progress',
      lastModifiedDate: '2021-03-26  3:30:16 ',
      lastModifiedBy: 'admintender',
      status: true,
      action: 'submit',
      join: '3'
    },

    {
      documentNo: 'BD-00003',
      name: 'pengadaan Office equepment',
      biddingTypeName: 'Tender Goods',
      documentStatus: 'Terminate',
      lastModifiedDate: '2021-03-26  3:30:16  ',
      lastModifiedBy: 'admintender',
      status: true,
      action: 'submit',
      join: '4'
    },
    {
      documentNo: 'BD-00004',
      name: 'pengadaan kendaraan jabatan',
      biddingTypeName: 'Tender Goods',
      documentStatus: 'Not Started',
      lastModifiedDate: '2021-03-26  3:30:16 ',
      lastModifiedBy: 'admintender',
      status: false,
      action: 'register ',
      join: '3'
    }
  ];







  index: boolean = true;
  page: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;
  private moreinfo: any = {};

  ///##########################################################################################################

  mounted() {
    this.announcmentGrid();
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
