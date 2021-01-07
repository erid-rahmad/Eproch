import { Component, Vue } from 'vue-property-decorator';

const QuickSearchProps = Vue.extend({
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
  components: {}
})
export default class QuickSearch extends QuickSearchProps {

}
