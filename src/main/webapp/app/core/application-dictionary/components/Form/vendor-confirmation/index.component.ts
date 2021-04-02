import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
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

  index = true;
  documentAction = null;
  selectedRow: any = {};
  showConfirmationForm = false;
  submitting = false;

  contract = {
    contractNo: 112001,
    startDate: '2021-03-31',
    endDate: '2021-03-31',
    remark: null,
    reason: null
  };

  vendorConfirmations = [
    {
      biddingNo: 'BN-00001',
      biddingTitle: 'Pengadaan Kendaraan Operasional',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      selectedWinner: 1,
      amount: 29000000000,
      confirmationStatus: 'Need Confirmation'
    },
    {
      biddingNo: 'BN-11011',
      biddingTitle: 'Office Supply Bid',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      selectedWinner: 1,
      amount: 500000000,
      confirmationStatus: 'Rejected'
    },
  ];

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
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
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetail(row: any) {
    this.selectedRow = row;
    this.index = false;
  }
}
