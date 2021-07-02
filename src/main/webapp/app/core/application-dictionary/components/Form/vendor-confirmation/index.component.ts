import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import ConfirmationForm from './confirmation-form.vue';
import VendorConfirmationDetail from './detail.vue';

const VendorConfirmationProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    ConfirmationForm,
    VendorConfirmationDetail
  }
})
export default class VendorConfirmation extends mixins(AccessLevelMixin, VendorConfirmationProp) {
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  index = true;
  loading = true;
  documentAction = null;
  isAccept = false;

  selectedRow: any = {};
  showConfirmationForm = false;
  submitting = false;

  contract:any = {
    contractNo: 0,
    startDate: '2021-03-31',
    endDate: '2021-03-31',
    remark: "",
    reason: ""
  };

  vendorConfirmations = []

  vendorConfirmation: any[] = []; 
  biddingStates: any[] = [];

  // for paging
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public reverse = false;
  public totalItems = 0;

/*
    {
      biddingNo: 'BN-00001',
      biddingTitle: 'Pengadaan Kendaraan Operasional',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      selectedWinner: 1,
      amount: 29310000000,
      confirmationStatus: 'Need Confirmation'
    },
    {
      biddingNo: 'BN-00002',
      biddingTitle: 'Pengadaan Office Supply',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      selectedWinner: 1,
      amount: 500000000,
      confirmationStatus: 'Rejected'
    },
  ];
*/
  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  created() {
    const biddingNo = this.$route.query?.doc;
    console.log('biddingNo:', biddingNo);
    if (biddingNo) {
      const row = this.vendorConfirmations.find(data => data.biddingNo === biddingNo);
      if (row) {
        this.viewDetail(row);
      }
    }
    this.refreshHeader();
    this.commonService(null)
      .retrieveReferenceLists('vendorConfirmation')
      .then(res => {
        this.vendorConfirmation = res.map(item => ({ key: item.value, value: item.name }));
      });
    this.commonService(null)
      .retrieveReferenceLists('biddingStatus')
      .then(res => {
        this.biddingStates = res.map(item => ({ key: item.value, value: item.name }));
      });
  }

  refreshHeader(){
    this.loading=true;
    this.commonService('/api/m-vendor-confirmations').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true'
      ]),
      paginationQuery: {
        page: this.page-1,
        size: this.itemsPerPage,
        sort: ['id']
      }
    })
    .then(res => {
      this.vendorConfirmations = res.data;
      this.vendorConfirmations.forEach((elem)=>{
        if(elem.negoAmount) elem.amount = elem.negoAmount;
      })
      this.totalItems = Number(res.headers['x-total-count']);
      this.queryCount = this.totalItems;
    }).finally(()=>{
      this.loading=false;
    });
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
    this.setRow(this.vendorConfirmations[0]);
  }

  accept() {
    this.documentAction = 'Accept';
    this.isAccept = true;
    this.showConfirmationForm = true;
  }

  closeDetail() {
    this.index = true;
    this.refreshHeader();
  }

  revise() {
    this.documentAction = 'Need Revision';
    this.isAccept = false;
    this.showConfirmationForm = true;
  }

  submit() {
    this.submitting = true;
    
    let data:any = {};

    data.adOrganizationId = this.contract.adOrganizationId;
    data.vendorConfirmationLineId = this.contract.vendorConfirmationLineId;
    data.vendorConfirmationContractId = this.selectedRow.latestContractId;
    if(this.isAccept) data.accept = this.contract.reason;
    else data.needRevision = this.contract.reason;

    this.commonService('/api/m-vendor-confirmation-responses').create(data).then(res=>{
      this.submitting = false;
      this.selectedRow.status = this.isAccept?'A':'R'
      this.clearReason();
      this.$message.success("Reason submitted successfully.");
    }).catch(err => {
      console.error('Failed to submit reason.', err);
      this.$message.error(`Failed saving the contract`);
    });
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid)?.setCurrentRow(record);
  }

  viewDetail(row: any) {
    this.selectedRow = row;
    this.commonService(`/api/m-vendor-confirmation-contracts/${row.latestContractId}`).retrieve({
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{
      console.log(res.data);
      this.contract = res.data;
      this.index = false;
    }).catch(reason=>{
      console.log(reason);
      this.contract = {
        confirmationNo: Date.now(),
        contractStartDate: '2021-05-28',
        contractEndDate: '2021-05-28',
        contractDetail: null,
        adOrganizationId: row.adOrganizationId,
        vendorConfirmationLineId: row.id
      };
      this.index = false;
    })
  }

  formatBiddingStatus(value: string) {
    return this.biddingStates.find(status => status.key === value)?.value;
  }

  formatConfirmationStatus(value: string) {
    if ('P'===value) return 'Need Confirmation';
    return this.vendorConfirmation.find(status => status.key === value)?.value;
  }

  clearReason(){
    this.contract.reason = "";
    this.showConfirmationForm = false;
  }
}
