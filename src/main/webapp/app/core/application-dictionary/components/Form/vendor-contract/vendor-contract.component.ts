import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import { Component, Inject, Mixins, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import ContractDetail from './contract-detail.vue';
import {log} from "util";

const enum ContractPage {
  MAIN = 'main',
  DETAIL = 'detail',
}

const baseApiContract = 'api/m-contracts';

@Component({
  components: {
    ContractDetail,
  }
})
export default class VendorContract extends Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  deleteConfirmationVisible: boolean = false;
  terminationConfirmationVisible: boolean = false;
  loading: boolean = false;
  ApprovePA=false;
  RejectPA=false;
  SubmitPA=false;
  detailTabName: string = 'INF';

  gridData: any[] = [];

  selectedRow: any = {};

  section: ContractPage = ContractPage.MAIN;

  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;

  docStatuses: any[] = [];

  get isCanceled() {
    return this.selectedRow.documentStatus === 'CNL';
  }

  get isDraft() {
    return ! this.selectedRow.id || this.selectedRow.documentStatus === 'DRF';
  }

  get isSubmit() {
    return  this.selectedRow.documentStatus === 'DRF';
  }

  get isAction() {
    return  this.selectedRow.documentStatus === 'SMT';
  }

  get mainPage() {
    return this.section === ContractPage.MAIN;
  }

  get detailPage() {
    return this.section === ContractPage.DETAIL;
  }

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onCloseClicked() {
    this.section = ContractPage.MAIN;
    this.detailTabName = 'INF';
    this.transition();
  }

  onDeleteClicked() {
    this.deleteConfirmationVisible = true;
  }

  onSaveClicked() {
    (<any>this.$refs.detailPage).save();
    }

  ApproveClicked(){
    (<any>this.$refs.detailPage).approve();
    this.ApprovePA=false;
  }

  RejectClicked(){
    (<any>this.$refs.detailPage).reject();
    this.RejectPA=false;
  }

  SubmitClicked() {
    (<any>this.$refs.detailPage).submit();
    this.SubmitPA=false;
  }

  onTerminateClicked() {
    this.terminationConfirmationVisible = true;
  }

  created() {
    const query = this.$route.query;
    this.retrieveDocStatuses();

    console.log(query);

    if(query.id) {
      this.commonService(baseApiContract)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `id.equals=${query.id}`
        ]),
        paginationQuery: {
          page: 0,
          size: 10,
          sort: ['id']
        }
      })
      .then(res => {
        console.log(res);
        this.selectedRow = res.data[0];
        this.section = ContractPage.DETAIL;
      })
    } else { 
      this.transition();
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

  deleteRecord() {
    this.commonService(baseApiContract)
      .delete(this.selectedRow.id)
      .then(() => {
        this.$message.success(`Auction ${this.selectedRow.documentNo} has been deleted successfully`);
        this.transition();
        this.deleteConfirmationVisible = false;
      })
      .catch(err => {
        console.error('Failed to delete the contract', err);
        this.$message.error(`Failed to delete contract ${this.selectedRow.documentNo}`);
      });
  }

  terminateContract(){
    this.selectedRow.documentAction = 'TRM';
    this.commonService(baseApiContract)
      .update(this.selectedRow)
      .then(() => {
        this.$message.success(`Contract ${this.selectedRow.documentNo} has been terminated.`);
        this.terminationConfirmationVisible = false;
        this.retrieveAllRecords();
      }).catch((err)=>{
        console.log(err);
        this.$message.success(`Unable to terminate bidding ${this.selectedRow.documentNo}.`);
      });
  }

  printDocStatus(status: string) {
    return this.docStatuses.find(item => item.value === status)?.name || status;
  }

  private retrieveDocStatuses() {
    this.commonService(null).retrieveReferenceLists('docStatus')
      .then(res => this.docStatuses = res)
      .catch(err => this.$message.warning('Failed to get document statuses'));
  }

  private retrieveAllRecords(): void {
    this.loading = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.commonService(baseApiContract)
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
        this.$message.error(err.detail || err.message);
      })
      .finally(() => this.loading = false);
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetails(row: any) {
    if ( ! row) {
      this.selectedRow = {
        name: null,
        description: null,
        documentNo: null,
        adOrganizationId: AccountStoreModule.organizationInfo.id,
        currencyId: null,
        costCenterId: null,
        ownerUserId: null
      };
    } else {
      this.selectedRow = row;
    }

    this.section = ContractPage.DETAIL;
  }
}
