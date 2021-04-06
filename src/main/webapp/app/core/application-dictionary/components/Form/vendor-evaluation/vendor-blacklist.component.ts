import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import VendorBlacklistDetail from './vendor-blacklist-detail.vue';

const VendorBlacklistProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    VendorBlacklistDetail,
    DocumentActionButton,
    DocumentActionConfirm
  }
})
export default class VendorBlacklist extends mixins(AccessLevelMixin, VendorBlacklistProp) {

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

  blacklist = [
    {
      documentTypeId: null,
      documentTypeName: null,
      approvalDate: null,
      vendorId: 1,
      vendorName: 'INGRAM MICRO INDONESIA',
      blacklistedPersonalCount: 0,
      notes: null,
      attachment: null,
      type: 'Whitelist',
      documentAction: 'SMT',
      documentStatus: 'DRF',
      approved: false,
      status: 'Draft'
    },
    {
      documentTypeId: null,
      documentTypeName: null,
      approvalDate: '2021-03-31T00:00:00.000Z',
      vendorId: 2,
      vendorName: 'PT. APV',
      blacklistedPersonalCount: 1,
      notes: 'Kinerja buruk',
      attachment: 'Performance Report.pdf',
      type: 'Blacklist',
      documentAction: 'APV',
      documentStatus: 'APV',
      approved: true,
      status: 'Approved'
    },
    {
      documentTypeId: null,
      documentTypeName: null,
      approvalDate: null,
      vendorId: 3,
      vendorName: 'SISTECH KHARISMA',
      blacklistedPersonalCount: 0,
      notes: null,
      attachment: null,
      type: 'Whitelist',
      documentAction: 'APV',
      documentStatus: 'SMT',
      approved: false,
      status: 'Submitted'
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
    this.retrieveDocumentType('Vendor Blacklist');
  }

  mounted() {
    this.setRow(this.blacklist[0]);
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
          this.blacklist = this.blacklist.map(item => {
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
    this.selectedRow = row;
    this.index = false;
  }
}
