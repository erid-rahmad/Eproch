import QuotationProposal from './quotation-proposal.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { Component, Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

@Component({
  components: {
    QuotationProposal,
  }
})
export default class QuotationResponseGrid extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  newRecord: boolean = false;
  currentDate: Date = new Date();

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

  readonly: boolean = false;
  selectedRow: any = {};

  get isVendor(){
    return accountStore.isVendor;
  }

  onFormClosed() {
    this.page = 1;
    this.newRecord = false;
    this.readOnly(false);
    this.retrieveHeaders();
  }

  created() {
    this.retrieveHeaders();
  }

  private retrieveHeaders() {
    this.loading = true;
    if(this.isVendor){
      this.commonService('/api/m-quote-suppliers')
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
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.gridPage!=1){
      this.gridPage = 0;
    }
    this.retrieveHeaders();
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.retrieveHeaders();
    }
  }

  public transition(): void {
    this.retrieveHeaders();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveHeaders();
  }

  @Watch('gridPage')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  addAnnouncement() {
    this.newRecord = true;
    this.page = 3;
  }

  viewDetails(row: any) {
    this.page = 3;
    this.selectedRow = row;
  }

  publish() {
    (<any>this.$refs.proposal).openPublishDialog();
  }

  saveAsDraft() {
    (<any>this.$refs.proposal).save("DRF");
  }

  formatStatus(dateRequired){
    return new Date() >= new Date(dateRequired) ? 'Finished': 'In Progress'
  }

  readOnly(boolean: boolean){
    this.readonly = boolean;
  }
}
