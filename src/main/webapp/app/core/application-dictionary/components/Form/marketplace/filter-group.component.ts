import { Component, Vue, Watch } from 'vue-property-decorator';
import { ElTree } from 'element-ui/types/tree';

const FilterGroupProps = Vue.extend({
  props: {
    /**
     * For type: range, there are two format types available: number & date
     */
    format: String,

    listItems: {
      type: Array,
      default: () => {
        return [];
      }
    },
    
    /**
     * Allow/disallow multiple selections.
     */
    multiple: Boolean,

    /**
     * The field name.
     */
    name: String,

    /**
     * The number or date pattern to use when using format.
     */
    pattern: String,

    /**
     * Title to be displayed as the group heading.
     */
    title: String,

    treeItems: {
      type: Array,
      default: () => {
        return []
      }
    },

    /**
     * Available types: list, dropdown, range.
     */
    type: {
      type: String,
      default: () => {
        return 'list'
      }
    },
    fields: {
      type: Array,
      default: () => {
        return [];
      }
    }
  }
})

@Component({
  components: {}
})
export default class FilterGroup extends FilterGroupProps {
  checkedItems: string[] = [];
  
  defaultTreeProps = {
    label: 'label',
    children: 'children'
  };

  range = {
    min: null,
    max: null
  };

  @Watch('range', { deep: true })
  onRangeChanged(range) {
    this.$emit('changed', range);
  }

  onCheckedItemsChanged(items: any[]) {
    this.$emit('changed', items);
  }

  onTreeChanged() {
    this.$emit('changed', (<ElTree<any, any>>this.$refs.tree).getCheckedNodes(true));
  }
}