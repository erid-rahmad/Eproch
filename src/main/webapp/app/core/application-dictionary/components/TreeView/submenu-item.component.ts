import { Component, Vue } from 'vue-property-decorator';
import { Drag, DropList } from "vue-easy-dnd";

const SubmenuItemProps = Vue.extend({
  props: {
    model: {
      type: Object,
      default: () => {
        return null;
      }
    },
    list: {
      type: Array,
      default: () => {
        return [];
      }
    },
    listKey: {
      type: String,
      default: 'items'
    }
  }
});

@Component({
  components: {
    Drag,
    DropList
  }
})
export default class SubmenuItem extends SubmenuItemProps {
  onInsert(event: any) {
    let item = event.data;
    item.parentMenuId = this.model.id;
    
    this.list.splice(event.index, 0, event.data);

    // Update the sequence.
    this.list.forEach((item: any, index: number) => {
      item.sequence = index + 1;
    });
  }

  onReorder(event: any) {
    event.apply(this.list);
  }

  remove(item: any) {
    let index = this.list.indexOf(item);
    this.list.splice(index, 1);
  }
  
  printTitle({name, translationKey}) {
    const key = `route.${translationKey || name}`;
    return this.$te(key) ? this.$t(key) : name;
  }
}
