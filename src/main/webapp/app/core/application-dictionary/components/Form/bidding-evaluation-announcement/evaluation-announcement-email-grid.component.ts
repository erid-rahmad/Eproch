import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

import AddAnnouncementForm from './components/add-announcement.vue';




@Component({
  components: {
    AddAnnouncementForm,
  

  }
})  
export default class EventAnnouncement extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  
  ScheduleListVisible = false;
  dialogTableVisible11 = false;
  viewemail = false;
  editor = null;
  tableData1= [{   
    name: 'WESTCON INTERNATIONAL INDONESIA',
    status: 'pass'
  }, 
  {  
    name: 'SISTECH KHARISMA',
    status: 'fail'
    }, 
    {
      name: 'INGRAM MICRO INDONESIA',
      status: 'fail'
    }, 
  ];
  winerTable= [{
    email: 'westcon@gmail.com',
    name: 'WESTCON INTERNATIONAL INDONESIA',
    status: 'lolos'
  }, 
];


  
  multipleSelection = [];
  tableData= [{
    date: '2016-05-03',
    name: 'WESTCON INTERNATIONAL INDONESIA',
    status: 'failed'
  }, {
      date: '2016-05-02',
      name: 'SISTECH KHARISMA',
      status: 'failed'
    }
    , {
      date: '2016-05-02',
      name: 'INGRAM MICRO INDONESIA',
      status: 'failed'
    }
  ];

  index: boolean = true;
  pages: number = 1;
  moreinfoview: boolean = false;
  step: boolean = false;

  viewBidding(row){
    console.log(row);
  }

  viewSchedule(row){
    console.log(row);
  }
 
  back() { 
    this.pages = 1;
  }

  backtomain() {
    this.$emit("back")
      ;
  }



}
