import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';

const ConfirmationFormProp = Vue.extend({
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
export default class ConfirmationForm extends mixins(AccessLevelMixin, ConfirmationFormProp) {

  columnSpacing = 24;
  showHistory = false;
  showConfirmationForm = false;

  mainForm = {};

  contract = {
    contractNo: 112001,
    startDate: '2021-03-31',
    endDate: '2021-03-31',
    remark: null
  };

  history = [
    {
      contractNo: '112001',
      lastModifiedDate: '2021-03-31',
      status: 'Accepted',
      reason: 'Sudah sesuai'
    },
    {
      contractNo: '112001',
      lastModifiedDate: '2021-03-28',
      status: 'Need Revision',
      reason: 'Mohon konfirmasi'
    }
  ]

  confirmations = [
    {
      vendorName: 'Sistech Kharisma',
      amount: 29000000000,
      quantity: 170,
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
    console.log('component confirmation-form created');
    this.mainForm = {...this.data};
  }

  beforeDestroy() {
    console.log('before destroy component confirmation-form');
  }

  viewDetail(row: any) {
    this.selectedConfirmation = row;
    this.showHistory = true;
  }

  openConfirmationForm(_row: any) {
    this.showConfirmationForm = true;
  }
}
