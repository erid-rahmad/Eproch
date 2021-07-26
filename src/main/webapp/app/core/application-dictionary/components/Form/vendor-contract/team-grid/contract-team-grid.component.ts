import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject, Watch } from 'vue-property-decorator';
//import ContractForm from './contract-form.vue';
//import ContractRequisition from './contract-requisition.vue';
import ContractTeam from '../contract-team.vue';

const ContractTeamProp = Vue.extend({
  props: {
    approval: Boolean
  }
});

enum ContractTeamPage {
  INDEX, DETAIL//, CONTRACT_RFQ, CONTRACT_DETAIL
}

@Component({
  components: {
    //ContractForm,
    //ContractRequisition,
    ContractTeam
  }
})
export default class ContractTeamGrid extends mixins(AccessLevelMixin, ContractTeamProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  quickSearchActive: boolean = false;
  page: ContractTeamPage = ContractTeamPage.INDEX;
  selectedRow: any = {};

  search = {
    contractNo: null,
    contractName: null,
    contractType: null,
    vendorName: null,
    costCenterName: null,
    status: null
  };

  loading = false;

  // for paging
  public itemsPerPage = 10;
  public queryCount: number = null;
  public gridPage = 1;
  public previousPage = 1;
  public reverse = false;
  public totalItems = 0;  

  evaluationTeams = [];

  private unspecifiedForm = () => {
    this.$message.error('No form specified for the selected event');
  }
  url: any;

  created() {
    this.refreshHeader();
  }

  refreshHeader(){
    this.loading = true;

    this.commonService("/api/m-contract-teams")
    .retrieve(
      {
        criteriaQuery: this.updateCriteria([
        'active.equals=true']),
        paginationQuery: {
          page: this.page-1,
          size: this.itemsPerPage,
          sort: ['id']
        }
      }).then((res)=>{
        this.evaluationTeams = res.data;

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
      }
    ).finally(()=>{this.loading=false})
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.page!=1){
      this.page = 0;
    }
    this.refreshHeader();
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.refreshHeader();
    }
  }

  public transition(): void {
    this.refreshHeader();
  }

  public clear(): void {
    this.page = 1;
    this.refreshHeader();
  }

  @Watch('gridPage')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  get isMainPage() {
    return this.page === ContractTeamPage.INDEX;
  }

  get isDetailPage() {
    return this.page === ContractTeamPage.DETAIL;
  }
/*
  get isContractRequisitionPage() {
    return this.page === ContractTeamPage.CONTRACT_RFQ;
  }

  get isContractFormPage() {
    return this.page === ContractTeamPage.CONTRACT_DETAIL;
  }
*/
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
    this.page = ContractTeamPage.INDEX;
    this.refreshHeader();
  }

  private setRow(record: any) {
    this.$nextTick(() => {
      (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
    });
  }

  viewDetail(row: any) {
    this.selectedRow = row;
    this.page = ContractTeamPage.DETAIL;
  }
  
  async viewContractDetail(row: any) {
    this.selectedRow = row;
    if(!this.url){
      const res = await this.commonService('api/ad-menus')
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `name.equals=Contracts`
          ])
        });

      if (!res.data.length) {
        return this.unspecifiedForm();
      }

      // TODO Cache the URL.
      const menu = res.data[0];
      this.url = await this.commonService('/api/ad-menus/full-path').find(menu.id);

      if (this.url) {
        const timestamp = Date.now();
        this.$router.push({
          path: this.url,
          query: {
            t: `${timestamp}`,
            id: `${this.selectedRow.contractId}`,
            page: `0`
          }
        });
        this.$emit('form-open');
      }
    } else {
      const timestamp = Date.now();
        this.$router.push({
          path: this.url,
          query: {
            t: `${timestamp}`,
            id: `${this.selectedRow.contractId}`,
            page: `0`
          }
        });
        this.$emit('form-open');
    }
  }

  async viewContractDocument(row: any) {
    this.selectedRow = row;
    if(!this.url){
      const res = await this.commonService('api/ad-menus')
        .retrieve({
          criteriaQuery: this.updateCriteria([
            'active.equals=true',
            `name.equals=Contracts`
          ])
        });

      if (!res.data.length) {
        return this.unspecifiedForm();
      }

      // TODO Cache the URL.
      const menu = res.data[0];
      this.url = await this.commonService('/api/ad-menus/full-path').find(menu.id);

      if (this.url) {
        const timestamp = Date.now();
        this.$router.push({
          path: this.url,
          query: {
            t: `${timestamp}`,
            id: `${this.selectedRow.contractId}`,
            page: `1`
          }
        });
        this.$emit('form-open');
      }
    } else {
      const timestamp = Date.now();
        this.$router.push({
          path: this.url,
          query: {
            t: `${timestamp}`,
            id: `${this.selectedRow.contractId}`,
            page: `1`
          }
        });
        this.$emit('form-open');
    }
  }

  onSaveClicked(){
    (<any>this.$refs.contractTeam).save();
  }
}
