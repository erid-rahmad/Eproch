import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import VendorBlacklistDetail from './vendor-blacklist-detail.vue';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';

const VendorBlacklistProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    VendorBlacklistDetail,
    DocumentActionButton,
    DocumentActionConfirm
  }
})
export default class VendorBlacklist extends mixins(AccessLevelMixin, VendorBlacklistProp) {

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

  documentStatuses = [];

  blacklist = [];

  // for paging
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public reverse = false;
  public totalItems = 0;  

  blacklistTypes = [
    {
      id: 1,
      name: 'Blacklist',
      value: 'B'
    },
    {
      id: 2,
      name: 'Whitelist',
      value: 'W'
    }
  ];

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
    this.retrieveDocumentType('Vendor Blacklist');
  }

  refreshHeader(){
    this.loading = true;
    this.commonService("/api/m-blacklists").retrieve(
      {
        criteriaQuery: this.updateCriteria([
        'active.equals=true']),
        paginationQuery: {
          page: this.page-1,
          size: this.itemsPerPage,
          sort: ['id']
        }
      }).then((res)=>{
        this.blacklist = res.data;
        console.log(this.blacklist);

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
    this.setRow(this.blacklist[0]);
  }

  closeDetail() {
    this.index = true;
    this.refreshHeader();
  }

  printStatus(value: string) {
    return this.documentStatuses.find(status => status.key === value)?.label;
  }

  printBlacklistType(value: string) {
    return this.blacklistTypes.find(status => status.value === value)?.name;
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
          this.blacklist = this.blacklist.map(item => {
            item.documentTypeId = res.data[0].id;
            item.documentTypeName = res.data[0].name;
            return item;
          });
        }
        this.docTypeId = res.data[0].id;
        this.docTypeName = res.data[0].name;
      });
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetail(row: any) {
    this.selectedRow = row;
    this.index = false;
  }

  onCreateClicked(){
    const empty = {
      documentTypeId: this.docTypeId,
      documentTypeName: this.docTypeName,
      approvalDate: null,
      vendorId: null,
      vendorName: null,
      blacklistedPersonalCount: 0,
      notes: null,
      attachment: null,
      type: null,
      adOrganizationId: AccountStoreModule.organizationInfo.id,
      createdBy: AccountStoreModule.account.login,
      documentAction: 'SMT',
      documentStatus: 'DRF',
      status: '',
      reportDate: new Date(),
      dateTrx: new Date(),
      lines: [],
      deleteLineIds: []
    }

    this.selectedRow = empty;
    this.index = false;
  }

  onSaveClicked(){
    if (!this.selectedRow.attachmentId) {
      this.$message.warning("File harus diupload");
      return;
    }

    if (!this.selectedRow.vendorId) {
      this.$message.warning("Tolong pilih vendor");
      return;
    }

    this.commonService('/api/m-blacklists')[this.selectedRow.id?'update':'create'](this.selectedRow).
      then((res)=>{
        this.$message.success(`Blacklist ${this.selectedRow.id?'updated':'created'}.`)
        this.selectedRow.id = res.id;
      }).catch((res)=>{
        this.$message.error(`Error during ${this.selectedRow.id?'updating':'creating'} a blacklist`)
      });
  }

  confirmed(action: any){
    console.log(action);
    
    if(this.selectedRow.id) this.selectedRows.push(this.selectedRows);

    if(this.selectedRows.length){
      this.selectedRows.forEach((row)=>{
        row.documentAction = action.value;
        row.documentStatus = action.value;
        this.commonService('/api/m-blacklists').update(row).
        then((res)=>{
          console.log(`${action.name} Blacklist ${row.documentNo} success.`)
        }).catch((res)=>{
          console.log(`${action.name} Blacklist ${row.documentNo} failed.`)
        });
      })
      this.$message.success(`${action.name} ${this.selectedRows.length} Blacklist(s) success.`);
      this.showDocumentActionConfirm = false;
      this.selectedRows = [];
      this.refreshHeader();
    } else {
      this.selectedRow.documentAction = action.value;
      this.selectedRow.documentStatus = action.value;
      this.commonService('/api/m-blacklists').update(this.selectedRow).
      then((res)=>{
        this.selectedRow = res;
        this.$message.success(`${action.name} Blacklist ${this.selectedRow.documentNo} success.`);
        this.showDocumentActionConfirm = false;
        this.refreshHeader();
      }).catch((res)=>{
        this.$message.error(`${action.name} Blacklist ${this.selectedRow.documentNo} failed.`)
      });
    }
  }

  downloadFile(row){
    window.open(row.downloadUrl, '_blank');
  }
}
