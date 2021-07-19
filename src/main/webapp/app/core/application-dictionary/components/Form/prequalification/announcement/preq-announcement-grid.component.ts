import AnnouncementForm from './announcement-form.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { Component, Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import BiddingInvitationResponse from './details-announcement.vue';

@Component({
  components: {
    AnnouncementForm,
    BiddingInvitationResponse,
  }
})
export default class EventAnnouncement extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  newRecord: boolean = false;

  gridData: any = [];
  moreinfo: any = {};

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records',
    maxHeight: 500,
    height: 500
  };

  // for paging
  page: number = 1;
  public gridPage = 1;
  public itemsPerPage = 10;
  public queryCount: number = null;
  public previousPage = 1;
  public reverse = false;
  public totalItems = 0;

  selectedRow: any = {};

  onFormClosed() {
    this.page = 1;
    this.newRecord = false;
    this.retrieveAnnouncements();
  }

  created() {
    this.retrieveAnnouncements();
  }

  private retrieveAnnouncements() {
    this.loading = true;
    this.commonService('/api/m-prequal-announcements')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'

        ]),
        paginationQuery: {
          page: this.gridPage-1,
          size: this.itemsPerPage,
          sort: ['id']
        }
      })
      .then(res => {
        this.gridData = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
      })
      .finally(() => this.loading = false);
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.gridPage!=1){
      this.gridPage = 0;
    }
    this.retrieveAnnouncements();
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.retrieveAnnouncements();
    }
  }

  public transition(): void {
    this.retrieveAnnouncements();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAnnouncements();
  }

  @Watch('gridPage')
  onPageChange(page: number) {
    this.loadPage(page);
  }


  viewBidding(row){
    console.log(row);
  }

  viewSchedule(row){
    console.log(row);
  }
  
  moreInfo(row) {
    this.page = 2;
    this.moreinfo = row;
  }

  addAnnouncement() {
    this.newRecord = true;
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
