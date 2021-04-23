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
    this.pushService('http://localhost:8080/api/c-announcements')
      .create(this.Announcment);
  }


  mounted() {
    console.log("mail from child", this.emailFromChild);
    this.Announcment.description = "<p><br>Kepada Bapak/Ibu Pimpinan <br>#VendorName <br>Hal: Undangan #TenderName <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>";
    this.Announcment.adOrganizationId = 1;
    this.Announcment.biddingId = 1957651;
    this.Announcment.attachmentId = 16502;


    this.pushAnnouncement();
    
  }

  beforeDestroy() {
    this.editor.destroy()
  }

  back() {
    this.$emit("back")
      ;
  }
}