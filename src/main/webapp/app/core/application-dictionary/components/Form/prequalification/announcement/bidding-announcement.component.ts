import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { mixins } from 'vue-class-component';
import { Component } from 'vue-property-decorator';
import AnnouncementForm from './announcement-form.vue';



@Component({
  components: {
    AnnouncementForm,
  }
})
export default class BiddingAnnouncement extends mixins(AccessLevelMixin) {

  openRecipientList() {
    (<any>this.$refs.announcementForm).openRecipientList();
  }

  saveAsDraft() {
    (<any>this.$refs.announcementForm).saveAsDraft();
  }
}
