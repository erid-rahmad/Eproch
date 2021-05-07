import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AdministrationProposal from '../event/bidding-submission/administration-proposal.vue';
import SubmissionForm from "../event/bidding-submission/submission-form.vue";
import PriceProposal from '../event/bidding-submission/price-proposal.vue';
import TechnicalProposal from '../event/bidding-submission/technical-proposal.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const enum SubmissionPage {
  MAIN = 'main',
  SUBMISSION = 'submission',
  ADMINISTRATION_PROPOSAL = 'administration_proposal',
  PRICE_PROPOSAL = 'price_proposal',
  TECHNICAL_PROPOSAL = 'technical_proposal'
}

const baseApiSubmission = 'api/m-bidding-submissions';

@Component({
  components: {
    SubmissionForm,
    AdministrationProposal,
    TechnicalProposal,
    PriceProposal
  }
})
export default class RegisteredBiddingList extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loading: boolean = false;
  formType: string = null;

  gridData: any[] = [
    {
      biddingNo: 'BD-00001',
      biddingName: 'Pengadaan Kendaraan Operasional',
      biddingTypeName: 'Tender Goods',
      biddingStatus: 'In Progress',
      biddingScheduleId: 317004,
      status: 'Submitted',
    },

    {
      biddingNo: 'BD-00003',
      biddingName: 'Pengadaan Office Equipment',
      biddingTypeName: 'Tender Goods',
      biddingStatus: 'In Progress',
      biddingScheduleId: 317004,
      status: 'Draft',
    },
    {
      biddingNo: 'BD-00004',
      biddingName: 'Pengadaan Kendaraan Jabatan',
      biddingTypeName: 'Tender Goods',
      biddingStatus: 'In Progress',
      biddingScheduleId: 317004,
      status: 'Draft',
    }
  ];

  selectedRow: any = {};
  
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

  section: SubmissionPage = SubmissionPage.MAIN;

  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;

  get mainPage() {
    return this.section === SubmissionPage.MAIN;
  }

  get submissionPage() {
    return this.section === SubmissionPage.SUBMISSION;
  }

  get administrationProposalPage() {
    return this.section === SubmissionPage.ADMINISTRATION_PROPOSAL;
  }

  get priceProposalPage() {
    return this.section === SubmissionPage.PRICE_PROPOSAL;
  }

  get technicalProposalPage() {
    return this.section === SubmissionPage.TECHNICAL_PROPOSAL;
  }

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get showAdministrationProposal() {
    return ['S1', 'S2', 'S3'].includes(this.formType);
  }

  get showTechnicalProposal() {
    return ['S1', 'S2', 'S3'].includes(this.formType);
  }

  get showPriceProposal() {
    return ['S1', 'S4'].includes(this.formType);
  }

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onFormClosed() {
    this.section = SubmissionPage.MAIN;
  }

  onSubmissionFormLoaded(data: any) {
    this.formType = data.formType;
  }

  created() {
    if (!this.isVendor) {
      this.section = SubmissionPage.SUBMISSION;
    }
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    const {propOrder: property, reverse} = this;
    this.$emit('order-changed', { property, reverse });
    this.transition();
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.page!=1){
      this.page = 0;
    }
    this.retrieveAllRecords();
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  private loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  private transition(): void {
    this.retrieveAllRecords();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  private retrieveAllRecords(): void {
    this.loading = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.commonService(baseApiSubmission)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;

        if (this.gridData.length) {
          this.setRow(this.gridData[0]);
        }
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.loading = false;
      });
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  closeProposalPage() {
    this.section = SubmissionPage.SUBMISSION;
  }

  openAdministrationProposal() {
    this.section = SubmissionPage.ADMINISTRATION_PROPOSAL;
  }

  openTechnicalProposal() {
    this.section = SubmissionPage.TECHNICAL_PROPOSAL;
  }

  openPriceProposal() {
    this.section = SubmissionPage.PRICE_PROPOSAL;
  }

  viewSchedule(row: any) {
    this.selectedRow = row;
  }

  viewDetails(row: any) {
    this.selectedRow = row;
    this.section = SubmissionPage.SUBMISSION;
  }

}