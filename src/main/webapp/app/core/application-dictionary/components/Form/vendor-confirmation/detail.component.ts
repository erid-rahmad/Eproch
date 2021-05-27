import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

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
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;
  
  columnSpacing = 24;
  showDetail = false;
  showConfirmationForm = false;
  showPoForm = false;
  showHistory = false;

  mainForm:any = {};

  contract = {
    contractNo: 112001,
    startDate: '2021-03-31',
    endDate: '2021-03-31',
    remark: null
  };

  confirmations = [
    {
      vendorName: 'Supplier 3',
      amount: 29310000000,
      quantity: 180,
      documentStatus: 'A',
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
  ];

  selectedConfirmation = {};
  vendorConfirmation: any[] = [];

  created() {
    console.log('component detail created');
    this.mainForm = {...this.data};

    this.commonService(null)
      .retrieveReferenceLists('vendorConfirmation')
      .then(res => {
        this.vendorConfirmation = res.map(item => ({ key: item.value, value: item.name }));
      });
    
    /*
    this.commonService('/api/m-vendor-confirmation-lines').retrieve({
      criteriaQuery: this.updateCriteria([
        'active.equals=true',
        `vendorConfirmationId.equals=${this.mainForm.id}`
      ]),
      paginationQuery: {
        page: 0,
        size: 10000,
        sort: ['id']
      }
    }).then(res=>{console.log(res)});
    */
  }

  formatConfirmationStatus(value: string) {
    return this.vendorConfirmation.find(status => status.key === value)?.value;
  }

  beforeDestroy() {
    console.log('before destroy component detail');
  }

  viewDetail(row: any) {
    this.selectedConfirmation = row;
    this.showDetail = true;
  }

  viewHistory(row: any) {
    this.selectedConfirmation = row;
    this.showHistory = true;
  }

  openConfirmationForm(_row: any) {
    this.showConfirmationForm = true;
  }

  generatePo(_row: any) {
    this.showPoForm = true;
  }
}
