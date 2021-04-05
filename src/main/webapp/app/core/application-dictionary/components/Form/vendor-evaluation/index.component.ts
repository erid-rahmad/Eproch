import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import VendorEvaluationDetail from './detail.vue';

const VendorEvaluationProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    VendorEvaluationDetail
  }
})
export default class VendorEvaluation extends mixins(AccessLevelMixin, VendorEvaluationProp) {

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

  vendorEvaluations = [
    {
      documentNo: '11011',
      vendorId: 1,
      vendorName: 'Ingram Micro Indonesia',
      reviewer: 'Admin Evaluator',
      aggreementNo: '13334',
      aggreementTitle: 'Pengadaan Kendaraan Operasional',
      evaluationType: 'Vendor Otomotif',
      evaluationPeriod: 'Yearly',
      evaluationDate: '2021-03-31T00:00:00.000Z',
      totalScore: 3.67,
      documentStatus: 'APV',
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

  mounted() {
    this.setRow(this.vendorEvaluations[0]);
  }

  closeDetail() {
    this.index = true;
  }

  printStatus(status: string) {
    return this.documentStatuses[status];
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
