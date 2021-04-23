import { Editor, EditorContent } from '@tiptap/vue-2'
import { defaultExtensions } from '@tiptap/starter-kit'

import { Component, Vue } from "vue-property-decorator";

  @Component({
    components: {
      EditorContent,
        
    }
  })  
export default class AddAnnouncementForm extends Vue {
  

    editor = null;



    
  mounted() {
    this.editor = new Editor({
      extensions: [
        ...defaultExtensions(),
      ],
      content: '<p><br>Kepada Bapak/Ibu Pimpinan <br>#VendorName <br>Hal: Undangan #TenderName <br>Dengan hormat </p><p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut. Silahkan Bapak/Ibu melakukan login di login.com untuk mendaftar pada bidding tersebut. Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p><p>Hormat Kami<br>Berca.co.id</p>',
    })

    this.$emit('email', this.editor)
  }

  beforeDestroy() {
    this.editor.destroy()
    }
    
    sent() {       
      this.editor.chain().focus().redo().run()
      console.log(this.editor.options.content)

    }
}