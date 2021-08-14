import {Component, Inject, Mixins, Vue, Watch} from 'vue-property-decorator';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

const AccordionProps = Vue.extend({
  props: {
    id: {
        type: String,
        default: 'accordion'
    },
    title: {
        type: String,
        default: 'title'
    },
    animation: {
        type: String,
        default: 'bottomToTop'
        // validator: prop => ['leftToRight', 'bounceIn', 'bottomToTop'].includes(prop)
    },
    expanded: {
        type: Boolean,
        default: false
    }
  }
})

@Component({
  components: {

  }
})
export default class Accordion extends  Mixins(AccessLevelMixin,AccordionProps) {

    open: Boolean = false;

    expandCollapsePanel(){
      this.open = !this.open;

      let triggerHeight = document.getElementById(this.id + '-trigger').offsetHeight;
      document.getElementById(this.id + '-body').style.height = 'calc(100% - '+ triggerHeight + 'px)';
    }

    created(){
      this.open = this.expanded;
    }

    mounted(){

    }
}
