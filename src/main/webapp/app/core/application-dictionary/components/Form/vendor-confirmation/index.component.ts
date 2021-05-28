import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
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
  documentAction = null;
  selectedRow: any = {};
  showConfirmationForm = false;
  submitting = false;

  contract = {
    contractNo: 0,
    startDate: '2021-03-31',
    endDate: '2021-03-31',
    remark: null,
    reason: null
  };

  vendorConfirmations = []
  vendorConfirmation: any[] = [];
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
    this.commonService('/api/m-vendor-confirmations').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true'
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    })
    .then(res => {
      this.vendorConfirmations = res.data;
    });
    this.commonService(null)
      .retrieveReferenceLists('vendorConfirmation')
      .then(res => {
        this.vendorConfirmation = res.map(item => ({ key: item.value, value: item.name }));
      });
  }

  mounted() {
    this.setRow(this.vendorConfirmations[0]);
  }

  accept() {
    this.documentAction = 'Accept';
    this.showConfirmationForm = true;
  }

  closeDetail() {
    this.index = true;
  }

  revise() {
    this.documentAction = 'Need Revision';
    this.showConfirmationForm = true;
  }

  submit() {
    this.submitting = true;
    setTimeout(() => {
      this.submitting = false;
      this.showConfirmationForm = false;
    }, 2000);
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid)?.setCurrentRow(record);
  }

  viewDetail(row: any) {
    this.selectedRow = row;
    this.commonService('/api/m-vendor-confirmation-contracts').retrieve({
      criteriaQuery: this.updateCriteria([
        ''
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    })
    this.index = false;
  }

  formatConfirmationStatus(value: string) {
    if ('P'===value) return 'Need Confirmation';
    return this.vendorConfirmation.find(status => status.key === value)?.value;
  }
}
