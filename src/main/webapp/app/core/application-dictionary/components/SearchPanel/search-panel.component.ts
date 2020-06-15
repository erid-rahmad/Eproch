import { Component, Vue, Watch } from 'vue-property-decorator';
import BasicSearch from './basic-search.vue';
import AdvanceSearch from './advance-search.vue';
import { IADField } from '@/shared/model/ad-field.model';

const SearchPanelProps = Vue.extend({
  props: {
    eventBus: {
      type: Object,
      default: () => {
        return null;
      }
    },
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

  @Watch('fields')
  onFieldsChange(fields: Array<IADField>) {
    //console.log('Fields updated. %O', fields);
  }

  created() {
  }

  beforeDestroy() {
  }

  private onBasicSearch(params: any) {
    // TODO build basic search filter query.
    this.filterQuery = params;
    this.eventBus.$emit('filter-updated', this.filterQuery);
  }

  private onAdvanceSearch(params: any) {
    // TODO build advance search filter query.
    this.filterQuery = params;
    this.eventBus.$emit('filter-updated', this.filterQuery);
  }

  private onClear() {
    this.eventBus.$emit('close-search-window');
    this.eventBus.$emit('filter-updated', "clear");
  }

  private onTabClick() {

  }

  private onClosePanel(){
    this.eventBus.$emit('close-search-window');
  }
}
