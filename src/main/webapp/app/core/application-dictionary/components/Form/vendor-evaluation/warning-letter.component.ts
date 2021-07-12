import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import WarningLetterDetail from './warning-letter-detail.vue';
import { Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';
import { AccountStoreModule } from '@/shared/config/store/account-store';

const WarningLetterProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    WarningLetterDetail,
    DocumentActionButton,
    DocumentActionConfirm
  }
})
export default class WarningLetter extends mixins(AccessLevelMixin, WarningLetterProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  index = true;
  loading = false;
  selectedRow: any = {};
  selectedRows: any[] = [];
  selectedDocumentAction: any = {};
  showDocumentActionConfirm = false;

  docTypeId = 0;
  docTypeName = '';

  warningTypes = [
    {
      id: 1,
      name: 'Admonition',
      value: 'A'
    },
    {
      id: 2,
      name: 'Admonition 1',
      value: 'A1'
    },
    {
      id: 3,
      name: 'Admonition 2',
      value: 'A2'
    }
  ];

  documentStatuses = [];

  warningLetters = [];

  // for paging
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public reverse = false;
  public totalItems = 0;

  get defaultDocumentAction() {
    return this.selectedRow.documentAction || 'DRF';
  }

  get documentApproved() {
    return this.selectedRow.processed && this.selectedRow.approved || false;
  }

  get documentTypeId() {
    return this.selectedRow.documentTypeId;
  }

  get isDraft() {
    return this.selectedRow.documentStatus === 'DRF' || this.selectedRow.documentStatus === 'RVS';
  }

  onDocumentActionChanged(action: any) {
    this.selectedDocumentAction = action;
    this.showDocumentActionConfirm = true;
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  onSelectionChanged(value: any) {
    this.selectedRows = value;
    this.$emit('selectedRows', this.selectedRows);
  }

  created() {
    this.refreshHeader();
    this.commonService(null)
      .retrieveReferenceLists('docStatus')
      .then(res => {
        this.documentStatuses = res.map(item => 
          ({
            key: item.value,
            label: item.name
          })
        );
      });
    this.retrieveDocumentType('Warning Letter');
  }

  refreshHeader(){
    this.loading = true;
    this.commonService("/api/m-warning-letters").retrieve(
      {
        criteriaQuery: this.updateCriteria([
        'active.equals=true']),
        paginationQuery: {
          page: this.page-1,
          size: this.itemsPerPage,
          sort: ['id']
        }
      }).then((res)=>{
        this.warningLetters = res.data;
        console.log(this.warningLetters);

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

  @Watch('page')
  onPageChange(page: number) {
    this.loadPage(page);
  }


  mounted() {
    this.setRow(this.warningLetters[0]);
  }

  closeDetail() {
    this.index = true;
    this.refreshHeader();
  }

  printStatus(value: string) {
    return this.documentStatuses.find(status => status.key === value)?.label;
  }

  private retrieveDocumentType(name: string) {
    this.commonService('/api/c-document-types')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `name.equals=${name}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1,
          sort: []
        }
      })
      .then(res => {
        if (res.data.length) {
          this.warningLetters = this.warningLetters.map(item => {
            item.documentTypeId = res.data[0].id;
            item.documentTypeName = res.data[0].name;
            return item;
          });
          this.docTypeId = res.data[0].id;
          this.docTypeName = res.data[0].name;
        }
      });
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetail(row: any) {
    console.log('selected row:', row);
    this.selectedRow = row;
    this.index = false;
  }

  onCreateClicked(){
    const empty = {
      documentTypeId: this.docTypeId,
      documentTypeName: this.docTypeName,
      reportDate: '',
      vendorName: '',
      vendorId: null,
      businessCategoryId: null,
      subCategoryId: null,
      startDate: '',
      endDate: '',
      warningType: '',
      location: '',
      warning: '',
      documentAction: 'SMT',
      documentStatus: 'DRF',
      dateTrx: new Date(),
      adOrganizationId: AccountStoreModule.organizationInfo.id,
      status: ''
    }
    this.selectedRow = empty;
    this.index = false;
  }

  formatWarningType(value: string) {
    return this.warningTypes.find(status => status.value === value)?.name || value;
  }

  onSaveClicked(){
    this.commonService('/api/m-warning-letters')[this.selectedRow.id?'update':'create'](this.selectedRow).
      then((res)=>{
        this.$message.success(`Warning letter ${this.selectedRow.id?'updated':'created'}.`)
        this.selectedRow = res;
      }).catch((res)=>{
        this.$message.error(`Error during ${this.selectedRow.id?'updating':'creating'} a warning letter`)
      });
  }

  confirmed(action: any){
    console.log(action);
    if(this.selectedRow.id) this.selectedRows.push(this.selectedRows);
    
    if(this.selectedRows.length){
      this.selectedRows.forEach((row)=>{
        row.documentAction = action.value;
        row.documentStatus = action.value;
        this.commonService('/api/m-warning-letters').update(row).
        then((res)=>{
          console.log(`${action.name} Warning Letter ${row.documentNo} success.`)
        }).catch((res)=>{
          console.log(`${action.name} Warning Letter ${row.documentNo} failed.`)
        });
      })
      this.$message.success(`${action.name} ${this.selectedRows.length} Warning Letter(s) success.`);
      this.showDocumentActionConfirm = false;
      this.selectedRows = [];
      this.refreshHeader();
    } else {
      this.selectedRow.documentAction = action.value;
      this.selectedRow.documentStatus = action.value;
      this.commonService('/api/m-warning-letters').update(this.selectedRow).
      then((res)=>{
        this.selectedRow = res;
        this.$message.success(`${action.name} Warning Letter ${this.selectedRow.documentNo} success.`);
        this.showDocumentActionConfirm = false;
        this.refreshHeader();
      }).catch((res)=>{
        this.$message.error(`${action.name} Warning Letter ${this.selectedRow.documentNo} failed.`)
      });
    }
  }
}
