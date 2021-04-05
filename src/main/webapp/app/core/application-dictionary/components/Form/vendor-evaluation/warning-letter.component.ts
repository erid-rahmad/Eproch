import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import WarningLetterDetail from './warning-letter-detail.vue';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';

const WarningLetterProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    WarningLetterDetail,
    DocumentActionButton,
    DocumentActionConfirm
  }
})
export default class WarningLetter extends mixins(AccessLevelMixin, WarningLetterProp) {

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

  warningLetters = [
    {
      documentTypeId: null,
      documentTypeName: null,
      reportDate: '2021-03-31T00:00:00.000Z',
      vendorName: 'Ingram Micro Indonesia',
      businessCategory: 'Automotive Vehicle',
      subCategory: 'Car',
      startDate: '2021-03-31T00:00:00.000Z',
      endDate: '2021-04-14T00:00:00.000Z',
      warningType: 'Admonition',
      location: null,
      message: null,
      requestor: 'Admin',
      documentAction: 'SMT',
      documentStatus: 'DRF',
      status: 'Open'
    },
    {
      documentTypeId: null,
      documentTypeName: null,
      reportDate: '2021-02-01T00:00:00.000Z',
      vendorName: 'Westcon International Indonesia',
      businessCategory: 'Automotive Vehicle',
      subCategory: 'Car',
      startDate: '2021-01-01T00:00:00.000Z',
      endDate: '2021-01-20T00:00:00.000Z',
      warningType: 'Admonition',
      location: null,
      message: 'First warning!',
      requestor: 'Admin',
      documentAction: 'CLS',
      documentStatus: 'CLS',
      status: 'Close'
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
    this.retrieveDocumentType('Warning Letter');
  }

  mounted() {
    this.setRow(this.warningLetters[0]);
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
          this.warningLetters = this.warningLetters.map(item => {
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
