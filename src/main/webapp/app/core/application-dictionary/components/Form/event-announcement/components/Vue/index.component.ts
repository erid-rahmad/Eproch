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
    private content = '';



    
  mounted() {
    this.editor = new Editor({
      extensions: [
        ...defaultExtensions(),
      ],
      
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