import Editor from './Editor.vue'
import { Component, Inject,Watch,Vue } from "vue-property-decorator";


  // @Component({
  //   components: {
  //     Editor,   
  //   }
  // })
  export default class AddAnnouncementForm extends Vue {  

    private content = '<p><br>Kepada Bapak/Ibu Pimpinan <br>#VendorName <br>Hal: Undangan #TenderName <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>';
   
  mounted() {
    this.$emit('email',this.content)
  }
    
  submit() {
    console.log("this submit", this.content)
    this.$emit('email', this.content)
  }
    
  @Watch('content')
  onDataChanged(data: any) {
    console.log('Value:', data);

  }
    
    tes(data: any) {
      console.log(data);
      return data;
      
      
    }
    
}