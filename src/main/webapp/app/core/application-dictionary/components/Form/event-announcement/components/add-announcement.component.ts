import { Component, Vue } from "vue-property-decorator";
import { Editor, EditorContent } from '@tiptap/vue-2'
import { defaultExtensions } from '@tiptap/starter-kit'
import MenuItem from './MenuItem.vue'
import tiptap from './Vue/index.vue'


@Component({
  components: {
    tiptap,
    



  }
})  


export default class AddAnnouncementForm extends EditorContent {
  
  dialogTableVisible = false;
  dialogTableVisible11 = false;

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

  mounted() {
    this.editor = new Editor({
      extensions: [
        ...defaultExtensions(),
      ],
      content: 'Tes',
    })
  }

  beforeDestroy() {
    this.editor.destroy()
  }

  back() {
    this.$emit("back")
      ;
  }
}