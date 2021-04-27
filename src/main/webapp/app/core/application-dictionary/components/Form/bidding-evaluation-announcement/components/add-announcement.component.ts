import { Component, Inject } from "vue-property-decorator";
import { Editor, EditorContent } from '@tiptap/vue-2'
import { defaultExtensions } from '@tiptap/starter-kit'
import MenuItem from './MenuItem.vue'
import tiptap from './Vue/index.vue'
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

@Component({
  components: {
    tiptap,
  }
})
export default class AddAnnouncementForm extends mixins (Vue2Filters.mixin, AlertMixin,EditorContent) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('dynamicWindowService')
  private pushService: (baseApiUrl: string) => DynamicWindowService;

  dialogTableVisible = false;
  dialogTableVisible11 = false;

  public emailFromChild: any = {};
  public Announcment: any = {};
  

  sizeForm= {
    name: '',
    region: '',
    date1: '',
    date2: '',
    delivery: false,
    type: [],
    resource: '',
    desc: ''
      
  };


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
  tableData1= [{
    email: 'westcon@gmail.com',
    name: 'WESTCON INTERNATIONAL INDONESIA',
    status: 'lolos'
  }, 
];
  multipleSelection= [];
  eventschedule = [        
    {
      no: 'Hena',
      event: 'Hena',
      start: 'Manager',
      end: 'hena@yahoo.com',
    },
    {
      no: 'agung',
      event: 'agung',
      start: 'Manager',
      end: 'agung@yahoo.com',
    }, {
      no: 'rahmi',
      event: 'rahmi',
      start: 'Manager',
      end: 'rahmi@yahoo.com',
    }];
  
  editor = null;
  private pushAnnouncement() {
    this.pushService('api/c-announcements')
      .create(this.Announcment);
  }


  mounted() {
    console.log("mail from child", this.emailFromChild);
    this.Announcment.description = "<br>Nomor :081<br>Klasifikasi :penting<br>Perihal:Pengumuman Hasil Evaluasi Sampil I <br>Nusu Dua 06 Desember-2019<br><br>Kepada:<br>Yth Pada Perserta Pengadaan Barang/Jasa<br>PEngadaan A";
    this.Announcment.adOrganizationId = 1;
    this.Announcment.biddingId = 1957651;
    this.Announcment.attachmentId = 16502;    
  }

 
  
  handleSelectionChange(val) {
      this.multipleSelection = val;
  }
  

  beforeDestroy() {
    this.editor.destroy()
  }

  back() {
    this.$emit("back")
      ;
  }


}