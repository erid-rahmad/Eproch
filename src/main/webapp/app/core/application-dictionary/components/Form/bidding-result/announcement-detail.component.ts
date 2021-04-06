import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Mixins, Vue } from 'vue-property-decorator';

const BiddingResultAnnouncementDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class BiddingResultAnnouncementDetail extends Mixins(AccessLevelMixin, BiddingResultAnnouncementDetailProp) {
}