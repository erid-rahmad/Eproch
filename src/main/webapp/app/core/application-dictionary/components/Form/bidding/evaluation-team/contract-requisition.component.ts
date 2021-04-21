import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject } from 'vue-property-decorator';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const ContractRequisitionProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
  }
});

@Component
export default class ContractRequisition extends Mixins(AccessLevelMixin, ContractRequisitionProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  mainForm = {
    requisitionNo: 'PR-21030031',
    biddingNo: 'BN-00001',
    biddingTitle: 'Pengadaan Kendaraan Operasional',
    pic: 'Admin Tender',
    documents: []
  }

  documents = [
    {
      name: 'Contract Requisition',
      active: true,
      remark: null
    },
    {
      name: 'Scope of Work',
      active: true,
      remark: null
    },
    {
      name: 'Data Sheet',
      active: true,
      remark: null
    },
    {
      name: 'Terms of Reference',
      active: true,
      remark: null
    },
    {
      name: 'Instruction to Bid',
      active: true,
      remark: null
    },
    {
      name: 'Responsibility Matrix',
      active: true,
      remark: null
    },
    {
      name: 'Weighting Matrix',
      active: true,
      remark: null
    }
  ];

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 320
  };

  downloadAttachment(row: any) {
    console.log('Downloading document');
  }
}