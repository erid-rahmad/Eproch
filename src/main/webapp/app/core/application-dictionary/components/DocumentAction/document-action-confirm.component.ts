import { Component, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '../../mixins/AccessLevelMixin';
import Vue2Filters from "vue2-filters";

const DocumentActionConfirmProps = Vue.extend({
  props: {
    action: {
      type: Object,
      default: () => {
        return {};
      }
    },

    data: {
      type: Object,
      default: () => {
        return {};
      }
    },

    visible: Boolean,

    width: {
      type: [String, Number],
      default: () => {
        return '30%';
      }
    }
  }
});

@Component
export default class DocumentActionConfirm extends Mixins(AccessLevelMixin, DocumentActionConfirmProps, Vue2Filters) {
  onClosed() {
    this.$emit('update:visible', false);
  }
  applyDocumentAction() {
    this.$emit('confirmed', this.action);
  }
}