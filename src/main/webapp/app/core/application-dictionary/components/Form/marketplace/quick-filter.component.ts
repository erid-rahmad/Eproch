import { Component, Vue } from 'vue-property-decorator';

const QuickFilterProps = Vue.extend({
  props: {
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
export default class QuickFilter extends QuickFilterProps {

}