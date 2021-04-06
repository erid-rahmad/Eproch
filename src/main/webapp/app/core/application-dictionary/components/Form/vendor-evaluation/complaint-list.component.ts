import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import ComplaintDetail from './complaint-detail.vue';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';

const ComplaintListProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    ComplaintDetail,
    DocumentActionButton,
    DocumentActionConfirm
  }
})
export default class ComplaintList extends mixins(AccessLevelMixin, ComplaintListProp) {

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

  complaints = [
    {
      documentTypeId: null,
      documentTypeName: null,
      dateTrx: '2021-03-31T00:00:00.000Z',
      vendorId: 1,
      vendorName: 'INGRAM MICRO INDONESIA',
      businessCategory: 'Automotive Vehicle',
      subCategory: 'Car',
      message: 'Performa buruk',
      contractNo: '1113456',
      costCenterId: 1,
      costCenterName: 'Procurement',
      requestor: 'Admin1',
      type: 'Performance',
      documentAction: 'SMT',
      documentStatus: 'DRF',
      status: 'Drafted'
    },
    {
      documentTypeId: null,
      documentTypeName: null,
      dateTrx: '2021-03-31T00:00:00.000Z',
      vendorId: 2,
      vendorName: 'WESTCON INTERNATIONAL INDONESIA',
      businessCategory: 'Automotive Vehicle',
      subCategory: 'Car',
      message: 'Performa buruk',
      contractNo: '1113456',
      costCenterId: 1,
      costCenterName: 'Procurement',
      requestor: 'Admin1',
      type: 'Performance',
      documentAction: 'CMP',
      documentStatus: 'RVW',
      status: 'Reviewed'
    },
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
    this.retrieveDocumentType('Complaint');
  }

  mounted() {
    this.setRow(this.complaints[0]);
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
          this.complaints = this.complaints.map(item => {
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
