import { Editor, EditorContent, EditorMenuBar } from "tiptap";
import { Blockquote, Bold, BulletList, Code, CodeBlock, HardBreak, Heading, History, Italic, Link, ListItem, OrderedList, Strike, TodoItem, TodoList, Underline } from 'tiptap-extensions';
import { Component, Vue, Watch } from "vue-property-decorator";

const HtmlEditorProps = Vue.extend({
  props: {
    size: {
      type: String,
      default: () => {
        return 'small'
      }
    },
    value: String
  }
})

@Component({
  components: {
    EditorContent,
    EditorMenuBar
  }
})
export default class HtmlEditor extends HtmlEditorProps {

  editor: Editor = null;

  @Watch('value')
  onValueChanged(value: string) {
    if (this.editor?.getHTML() !== value) {
      this.editor.setContent(value);
    }
  }
  
  mounted() {
    this.editor = new Editor({
      extensions: [
        new Blockquote(),
        new CodeBlock(),
        new HardBreak(),
        new Heading({ levels: [1, 2, 3] }),
        new BulletList(),
        new OrderedList(),
        new ListItem(),
        new TodoItem(),
        new TodoList(),
        new Bold(),
        new Code(),
        new Italic(),
        new Link(),
        new Strike(),
        new Underline(),
        new History(),
      ],
      content: this.value,
      onUpdate: ({ getHTML }) => {
        const html = getHTML();
        this.$emit('input', html);
        this.$emit('change', html);
      },
    });
  }

  beforeDestroy() {
    this.editor.destroy();
  }

}