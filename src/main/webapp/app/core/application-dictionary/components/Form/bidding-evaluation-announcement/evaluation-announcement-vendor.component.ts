import AlertMixin from '@/shared/alert/alert.mixin';
import Component, {mixins} from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import announcementDetail from "./components/details-announcement-vendor.vue"


@Component({
  components: {
    announcementDetail,
   

  }
})   
export default class Catalog extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
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

  back() {
    this.index = true;
  }



}
