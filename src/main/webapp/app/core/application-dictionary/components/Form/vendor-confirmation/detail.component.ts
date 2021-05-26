import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';

const VendorConfirmationDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class VendorConfirmationDetail extends mixins(AccessLevelMixin, VendorConfirmationDetailProp) {

  columnSpacing = 24;
  showDetail = false;
  showConfirmationForm = false;
  showPoForm = false;

  mainForm = {};

  contract = {
    contractNo: 112001,
    startDate: '2021-03-31',
    endDate: '2021-03-31',
    remark: null
  };

  confirmations = [
    {
      vendorName: 'Sistech Kharisma',
      amount: 29310000000,
      quantity: 180,
      documentStatus: 'Draft',
      lines: [
        {
          item: 'Honda 2015',
          quantity: 50,
          unitPrice: 238000000,
          totalLine: 11900000000
        },
        {
          item: 'Honda Civic 2017',
          quantity: 30,
          unitPrice: 439000000,
          totalLine: 13710000000
        },
        {
          item: 'Honda 2020',
          quantity: 100,
          unitPrice: 45000000,
          totalLine: 4500000000
        }
      ]
    }
  ];

  selectedConfirmation = {};

  created() {
    console.log('component detail created');
    this.mainForm = {...this.data};
  }

  beforeDestroy() {
    console.log('before destroy component detail');
  }

  viewDetail(row: any) {
    this.selectedConfirmation = row;
    this.showDetail = true;
  }

  openConfirmationForm(_row: any) {
    this.showConfirmationForm = true;
  }

  generatePo(_row: any) {
    this.showPoForm = true;
  }
}
