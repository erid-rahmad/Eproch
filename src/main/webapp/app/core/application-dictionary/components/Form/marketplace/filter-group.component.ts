import { Component, Vue } from 'vue-property-decorator';

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
  range = {
    min: null,
    max: null
  };

  onCheckedItemsChanged(items: string[]) {
    this.$emit('changed', items.map(item => `mBrandName.in=${item}`).join('&'));
  }
}