import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import ContractForm from './contract-form.vue';
import ContractRequisition from './contract-requisition.vue';
import EvaluationTeamDetail from './evaluation-team-detail.vue';

const EvaluationTeamProp = Vue.extend({
  props: {
    approval: Boolean
  }
});

enum EvaluationTeamPage {
  INDEX, DETAIL, CONTRACT_RFQ, CONTRACT_DETAIL
}

@Component({
  components: {
    ContractForm,
    ContractRequisition,
    EvaluationTeamDetail
  }
})
export default class EvaluationTeam extends mixins(AccessLevelMixin, EvaluationTeamProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  quickSearchActive: boolean = false;
  page: EvaluationTeamPage = EvaluationTeamPage.INDEX;
  selectedRow: any = {};

  search = {
    biddingNo: null,
    biddingTitle: null,
    biddingTypeName: null,
    eventTypeName: null,
    costCenterName: null,
    status: null
  };

  evaluationTeams = [
    {
      biddingNo: 'BN 00001',
      biddingTitle: 'Pengadaan Kendaraan Operasional',
      biddingTypeName: 'Tender Goods',
      eventTypeName: 'Satu Tahap',
      costCenterName: 'Marketing',
      lastModifiedDate: '2021-03-21T05:20:26.039164Z',
      status: 'Arranged',
      members: []
    },
    {
      biddingNo: 'BN 00002',
      biddingTitle: 'Pengadaan Office Supply',
      biddingTypeName: 'Tender Goods',
      eventTypeName: 'Satu Tahap',
      costCenterName: 'General Affair',
      lastModifiedDate: '2021-03-23T05:20:26.039164Z',
      status: 'Arranged',
      members: []
    },
    {
      biddingNo: 'BN 00003',
      biddingTitle: 'Pengadaan Kendaraan Marketing',
      biddingTypeName: 'Tender Goods',
      eventTypeName: 'Satu Tahap',
      costCenterName: 'Marketing',
      lastModifiedDate: null,
      status: 'Un-arranged',
      members: []
    }
  ];

  get isMainPage() {
    return this.page === EvaluationTeamPage.INDEX;
  }

  get isDetailPage() {
    return this.page === EvaluationTeamPage.DETAIL;
  }

  get isContractRequisitionPage() {
    return this.page === EvaluationTeamPage.CONTRACT_RFQ;
  }

  get isContractFormPage() {
    return this.page === EvaluationTeamPage.CONTRACT_DETAIL;
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onQuickSearchClose() {
    this.quickSearchActive = false;
  }

  onQuickSearchOpen() {
    this.quickSearchActive = true;
  }

  onSearchApply() {
    console.log('Search: %O', this.search);
  }

  mounted() {
    this.setRow(this.evaluationTeams[0]);
  }

  backToMainPage() {
    this.page = EvaluationTeamPage.INDEX;
  }

  private setRow(record: any) {
    this.$nextTick(() => {
      (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
    });
  }

  viewDetail(row: any) {
    this.selectedRow = row;
    this.page = EvaluationTeamPage.DETAIL;
  }

  viewContractRfq(row: any) {
    this.selectedRow = row;
    this.page = EvaluationTeamPage.CONTRACT_RFQ;
  }

  viewContractDocument(row: any) {
    this.selectedRow = row;
    this.page = EvaluationTeamPage.CONTRACT_DETAIL;
  }
}
