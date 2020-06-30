import { Component, Vue } from 'vue-property-decorator';
import SingleItem from "./single-item.vue";
import SubmenuItem from "./submenu-item.vue";

const TreeItemProps = Vue.extend({
  props: {
    baseApiUrl: String,
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
  name: 'TreeItem',
  components: {
    SingleItem,
    SubmenuItem
  }
})
export default class TreeItem extends TreeItemProps {
  get component() {
    return this.noChildren ? SingleItem : SubmenuItem;
  }

  get children() {
    return (data: any) => data[this.listKey];
  }

  private get noChildren() {
    return !this.model[this.listKey] || !this.model[this.listKey].length;
  }
}