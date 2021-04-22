import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import settings from '@/settings';
import { Component, Mixins, Vue, Inject } from 'vue-property-decorator';
import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';

const ContractFormProp = Vue.extend({
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
export default class ContractForm extends Mixins(AccessLevelMixin, ContractFormProp) {

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
      name: 'Contract 1',
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