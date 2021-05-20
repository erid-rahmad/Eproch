import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { ElTable } from 'element-ui/types/table';
import { mixins } from 'vue-class-component';
import { Component, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import StepForm from '../bidding/steps-form.vue';

const baseApiUrl = 'api/m-biddings';

@Component({
  components: {
    DocumentActionButton,
    DocumentActionConfirm,
    StepForm
  }
})
export default class BiddingProcess extends mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  dataChanged: boolean = false;

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records',
    maxHeight: 500,
    height: 500
  };

  index: boolean = true;

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public statusCatalog = '';

  processing = false;

  biddingStatuses: any[] = [];
  gridData: any[] = [];

  showJoinedVendors = false;
  showTerminationDialog = false;
  showDocumentActionConfirm = false;
  editMode: boolean = false;
  stepIndex: number = 0;
  selectedRow: any = {};
  selectedRows: any[] = [];
  selectedDocumentAction: any = {};

  terminationReason = null;

  joinedVendors = [
    {
      name: 'INGRAM MICRO INDONESIA',
      location: 'WISMA NUGRAHA SANTANA 9TH FLOOR SUITE#909, JL. JEND. SUDIRMAN KAV.7-8, 10220, JAKARTA PUSAT'
    },
    {
      name: 'SISTECH KHARISMA',
      location: 'JL. JUANDA 38-C, 10120, JAKARTA PUSAT'
    },
    {
      name: 'WESTCON INTERNATIONAL INDONESIA',
      location: 'GEDUNG MD PALACE TOWER 1, LT.5, JL. SETIABUDI SELATAN NO. 7 RT.05 RW.01, SETIABUDI, SETIABUDI, JAKARTA SELATAN'
    }
  ];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get defaultDocumentAction() {
    return this.selectedRow.documentAction || 'DRF';
  }

  get documentApproved() {
    return this.selectedRow.processed && this.selectedRow.approved || false;
  }

  get documentTypeId() {
    return this.selectedRow.documentTypeId;
  }

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }

  onCreateClicked() {
    this.editMode = false;
    this.selectedRow = {};
    this.index = false;
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onDocumentActionChanged(action: any) {
    this.selectedDocumentAction = action;
    this.showDocumentActionConfirm = true;
  }

  onFormClosed() {
    this.index = true;
    this.selectedRows = [];
    this.dataChanged = false;
    this.transition();
  }

  onFormSaved() {
    (<any>this.$refs.biddingForm).saveStep();
  }

  onSelectionChanged(value: any) {
    this.selectedRows = value;
    this.$emit('selectedRows', this.selectedRows);
  }

  onStepChanged(dataChanged: boolean = true) {
    this.dataChanged = dataChanged;
  }

  onStepSaved() {
    this.dataChanged = false;
  }

  created() {
    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        this.biddingStatuses = res.map(item => ({ key: item.value, value: item.name }));
      });

    this.transition();
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

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllRecords();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  formatBiddingStatus(value: string) {
    return this.biddingStatuses.find(status => status.key === value)?.value;
  }

  public retrieveAllRecords(): void {
    this.processing = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.commonService(baseApiUrl)
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
        this.processing = false;
        this.selectedRows = [];
      });
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewBidding(row: any, stepIndex: number = 0) {
    this.stepIndex = stepIndex;
    this.editMode = true;
    this.selectedRow = row;
    this.index = false;
  }

  viewJoinVendor() {
    this.showJoinedVendors = true;
  }
}
