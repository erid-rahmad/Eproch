import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DetailAnnouncement from './details-announcement.vue';

@Component({
  components: {
    DetailAnnouncement,
  }
})
export default class BiddingInvitationResponse extends Mixins(AccessLevelMixin) {




}
