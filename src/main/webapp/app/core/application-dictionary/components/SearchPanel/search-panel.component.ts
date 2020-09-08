import { Component, Vue, Watch } from 'vue-property-decorator';
import BasicSearch from './basic-search.vue';
import AdvanceSearch from './advance-search.vue';
import { IADField } from '@/shared/model/ad-field.model';

const SearchPanelProps = Vue.extend({
  props: {
    fields: {
      type: Array,
      default: () => {
        return [];
      }
    }
  }
});

@Component({
  components: {
    BasicSearch,
    AdvanceSearch
  }
})
export default class SearchPanel extends SearchPanelProps {

  filterQuery: string = '';
  currentTab: string = 'basic';

  public onSearch(params: any) {
    this.filterQuery = params;
    this.$emit('submit', this.filterQuery);
  }

  public onClear() {
    this.$emit('clear');
  }

  public onClose(){
    this.$emit('close');
  }
}
