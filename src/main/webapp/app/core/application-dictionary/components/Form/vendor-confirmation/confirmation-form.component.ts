import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

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
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  
  columnSpacing = 24;
  showHistory = false;
  showConfirmationForm = false;

  mainForm:any = {};

  contract = {
    contractNo: 112001,
    startDate: '2021-03-31',
    endDate: '2021-03-31',
    remark: null
  };

  history = [
    {
      confirmationNo: '112001',
      lastModifiedDate: '2021-03-31',
      status: 'Accepted',
      reason: 'Sudah sesuai'
    },
    {
      confirmationNo: '112001',
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
    console.log(this.data);
  }

  beforeDestroy() {
    console.log('before destroy component confirmation-form');
  }

  viewHistory() {
    this.showHistory = true;
    this.commonService('/api/m-vendor-confirmation-responses').retrieve({
      criteriaQuery: this.updateCriteria([
      'active.equals=true',
      `vendorConfirmationLineId.equals=${this.mainForm.vendorConfirmationLineId}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{this.history = res.data});
  }

  openConfirmationForm(_row: any) {
    this.showConfirmationForm = true;
  }

  downloadAttachment(){
    window.open(this.mainForm.downloadUrl, '_blank');
  }
}
