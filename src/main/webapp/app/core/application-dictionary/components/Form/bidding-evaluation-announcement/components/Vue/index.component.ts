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
      content: '<br>Nomor :081<br>Klasifikasi :penting<br>Perihal:Pengumuman Hasil Evaluasi Sampil I <br>Nusu Dua 06 Desember-2019<br><br>Kepada:<br>Yth Pada Perserta Pengadaan Barang/Jasa<br>PEngadaan A',
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