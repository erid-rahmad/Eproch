import { Component, Vue } from 'vue-property-decorator';

const ShoppingCartProps = Vue.extend({
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
export default class ShoppingCart extends ShoppingCartProps {

}