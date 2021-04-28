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
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles'
  }, {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles'
  }, {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles'
  }, {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles'
  }, {
    date: '2016-05-08',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles'
  }, {
    date: '2016-05-06',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles'
  }, {
    date: '2016-05-07',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles'
    }];
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

  // private retrieveCostCenter() {
  //   this.commonService('/api/c-cost-centers')
  //     .retrieve({
  //       criteriaQuery:([
       
  //       ]),
  //       paginationQuery: {
  //         page: 0,
  //         size: 1000,
  //         sort: ['name']
  //       }
  //     })
  //     .then(res => {
  //       this.costCenterOptions = res.data;
  //     });
  // }

  private pushAnnouncement() {
    this.pushService('api/c-announcements')
      .create(this.Announcment);
  }


  mounted() {
    console.log("mail from child", this.emailFromChild);
    this.Announcment.description = "<p><br>Kepada Bapak/Ibu Pimpinan <br>#VendorName <br>Hal: Undangan #TenderName <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>";
    this.Announcment.adOrganizationId = 1;
    this.Announcment.biddingId = 1957651;
    this.Announcment.attachmentId = 16502;


    // this.pushAnnouncement();
    
  }

  beforeDestroy() {
    this.editor.destroy()
  }

  back() {
    this.$emit("back")
      ;
  }


}