import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import axios from 'axios';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

import BiddingSubmission from './bidding-submission.vue';


@Component({
  components: {

    BiddingSubmission,
  }
})  
export default class Bidding extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

  data() {
    return {
      value1: true,
      value2: true,
      
    }
  }

  

  index: boolean = true;

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page :number =1;



  public documentStatuses: any[] = [];

  public gridData: Array<any> = [];
  public gridData1: Array<any> = [];
  public rowsa: any = {};
  
  vendorListVisible = false;
  selectedRows: Array<any> = [];

  mounted() {
    this.ambildata();
  }

  viewBidding(row){
    console.log(row);
  }

  viewSchedule(row){
    console.log(row);
  }

  viewJoinVendor(row){
    this.vendorListVisible = true;
  }

  view(row, stepIndex: number = 0) {
    this.index = false;
    this.rowsa = row;
    console.log("this row", row);
    this.vendorListVisible = false;
    
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
    
  ]

  ambildata() {
     axios
      .get('/api/m-biddings')
      // .then(res => {
      //   this.gridData1 = res.data;
      //   console.log("data tes 11",this.gridData1);
      // }); 
    this.gridData1 = [
      {
        documentNo: 'BD-00001',
        name: 'pengadaan kendaraan operasional',
        biddingTypeName: 'Tender Goods',
        documentStatus: 'In Progress',
        subStatus: 'Submitted',
        lastModifiedDate: '2021-03-26  3:30:16 ',
        lastModifiedBy: 'admintender',
        status: true,
        action: 'submit',
        join:'3'
      },

      {
        documentNo: 'BD-00003',
        name: 'pengadaan Office equepment',
        biddingTypeName: 'Tender Goods',
        documentStatus: 'Terminate',
        subStatus: 'Drafted',
        lastModifiedDate: '2021-03-26  3:30:16  ',
        lastModifiedBy: 'admintender',
        status: true,
        action: 'submit',
        join:'4'
      },
      {
        documentNo: 'BD-00004',
        name: 'pengadaan kendaraan jabatan',
        biddingTypeName: 'Tender Goods',
        documentStatus: 'Not Started',
        subStatus: 'Drafted',
        lastModifiedDate: '2021-03-26  3:30:16 ',
        lastModifiedBy: 'admintender',
        status: false,
        action: 'register ',
        join:'3'
      }
    ]
  }




}
