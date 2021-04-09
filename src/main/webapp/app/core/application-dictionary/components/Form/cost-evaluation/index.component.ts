import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import CostEvaluationDetail from './components/detail.vue';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';

const CostEvaluationProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    CostEvaluationDetail,
    DocumentActionButton,
    DocumentActionConfirm
  }
})
export default class CostEvaluation extends mixins(AccessLevelMixin, CostEvaluationProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  index = true;
  selectedRow: any = {};
  selectedDocumentAction: any = {};
  showDocumentActionConfirm = false;

  documentStatuses = {
    APV: 'Approved',
    DRF: 'Draft',
    RJC: 'Rejected',
    RVS: 'Revised',
    SMT: 'Submitted',
  }

  costEvaluations = [
    {
      documentNo: 'CE-00001',
      biddingNo: 'BN-00001',
      biddingTitle: 'Pengadaan Kendaraan Operasional',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender 3',
      costCenterName: 'Marketing',
      requisitionName: '100001',
      documentAction: 'APV',
      documentStatus: 'SMT',
      documentTypeId: null,
      documentTypeName: null,
      approved: false,
      processed: false,
      lastModifiedDate: '2021-03-01 14:00',
      lastModifiedBy: 'admin'
    },
    {
      documentNo: 'CE-00002',
      biddingNo: 'BN-00002',
      biddingTitle: 'Pengadaan Office Supply',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      requisitionName: '100002',
      documentAction: 'APV',
      documentStatus: 'APV',
      documentTypeId: null,
      documentTypeName: null,
      approved: true,
      processed: true,
      lastModifiedDate: '2021-03-14 10:44',
      lastModifiedBy: 'admin'
    },
    {
      documentNo: 'CE-00003',
      biddingNo: 'BD-00003',
      biddingTitle: 'Pengadaan Kendaraan Marketing',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      requisitionName: '100003',
      documentAction: 'SMT',
      documentStatus: 'DRF',
      documentTypeId: null,
      documentTypeName: null,
      approved: false,
      processed: false,
      lastModifiedDate: '2021-03-03 14:30',
      lastModifiedBy: 'admin'
    },
    {
      documentNo: 'CE-00004',
      biddingNo: 'BD-00004',
      biddingTitle: 'Pengadaan Kendaraan Jabatan',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      requisitionName: '1000014',
      documentAction: 'SMT',
      documentStatus: 'DRF',
      documentTypeId: null,
      documentTypeName: null,
      approved: false,
      processed: false,
      lastModifiedDate: '2021-03-03 14:30',
      lastModifiedBy: 'admin'
    },
    {
      documentNo: 'CE-00005',
      biddingNo: 'BD-00005',
      biddingTitle: 'Pengadaan Office Equipment',
      biddingTypeName: 'Tender Goods',
      currencyName: 'IDR',
      picName: 'Admin Tender',
      costCenterName: 'Marketing',
      requisitionName: '100003',
      documentAction: 'SMT',
      documentStatus: 'RVS',
      documentTypeId: null,
      documentTypeName: null,
      approved: false,
      processed: false,
      lastModifiedDate: '2021-03-01 11:17',
      lastModifiedBy: 'admin'
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

  created() {
    this.retrieveDocumentType('Cost Evaluation');
  }

  mounted() {
    this.setRow(this.costEvaluations[0]);
  }

  closeDetail() {
    this.index = true;
  }

  printStatus(status: string) {
    return this.documentStatuses[status];
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
          this.costEvaluations = this.costEvaluations.map(item => {
            item.documentTypeId = res.data[0].id;
            item.documentTypeName = res.data[0].name;
            return item;
          });
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
}
