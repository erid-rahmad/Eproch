import AnnouncementForm from '@/core/application-dictionary/components/Form/bidding/event/bidding-announcement/announcement-form.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { Component, Inject, Mixins } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';



@Component({
  components: {
    AnnouncementForm
  }
})
export default class EventAnnouncement extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  gridData: any = [];

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

  page: number = 1;
  selectedRow: any = {};

  onFormClosed() {
    this.page = 1;
    this.retrieveAnnouncements();
  }

  created() {
    this.retrieveAnnouncements();
  }

  private retrieveAnnouncements() {
    this.loading = true;
    this.commonService('/api/c-announcements')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'

        ]),
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['id']
        }
      })
      .then(res => {
        this.gridData = res.data;
      })
      .finally(() => this.loading = false);
  }
  
  viewBidding(row){
    console.log(row);
  }

  viewSchedule(row){
    console.log(row);
  }

  addAnnouncement() {
    this.page = 3;
  }

  viewDetails(row: any) {
    this.page = 3;
    this.selectedRow = row;
  }

  openRecipientList() {
    (<any>this.$refs.announcementForm).openRecipientList();
  }

  saveAsDraft() {
    (<any>this.$refs.announcementForm).saveAsDraft();
  }

}
