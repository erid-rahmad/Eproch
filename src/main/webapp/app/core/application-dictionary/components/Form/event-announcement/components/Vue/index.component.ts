import { Editor, EditorContent } from '@tiptap/vue-2'
import { defaultExtensions } from '@tiptap/starter-kit'
import { Component, Inject,Watch,Vue } from "vue-property-decorator";


  @Component({
    components: {
      EditorContent,   
    }
  })
  export default class AddAnnouncementForm extends Vue {  
    
    editor = new Editor({
      extensions: [
        ...defaultExtensions(),
        
      ],
      content: `<p><br>Kepada Bapak/Ibu Pimpinan <br>#VendorName <br>Hal: Undangan #TenderName <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>`,
      
    })

  mounted() {
    this.$emit('email', this.editor.getHTML())
  }
    
  beforeDestroy() {
    this.editor.destroy()
  }
    

    submit() {
      console.log("this submit", this.editor.getHTML())
      this.$emit('email', this.editor.getHTML())
    }

    @Watch('editor')
    updateemail(pick: any) {
      this.$emit('email', this.editor.getHTML())
    }
}
