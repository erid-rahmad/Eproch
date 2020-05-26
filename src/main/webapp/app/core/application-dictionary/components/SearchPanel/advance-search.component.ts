import { Component, Vue } from 'vue-property-decorator'

const AdvanceSearchProps = Vue.extend({
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

@Component
export default class AdvanceSearch extends AdvanceSearchProps {

}
