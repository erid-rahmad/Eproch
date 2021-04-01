import DocumentActionButton from '@/core/application-dictionary/components/DocumentAction/document-action-button.vue';
import DocumentActionConfirm from '@/core/application-dictionary/components/DocumentAction/document-action-confirm.vue';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const CostEvaluationDetailProp = Vue.extend({
  props: {
    approval: Boolean,
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component({
  components: {
    DocumentActionButton,
    DocumentActionConfirm,
  }
})
export default class CostEvaluationDetail extends mixins(AccessLevelMixin, CostEvaluationDetailProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  columnSpacing = 24;

  selectedDocumentAction: any = {};
  showDocumentActionConfirm = false;

  uploadedDocuments = [
    {
      name: 'Document #100.jpeg',
      url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
    },
    {
      name: 'Document #101.jpeg',
      url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
    }
  ];

  breakdown = false;
  mainForm = {};

  tableData = [
    {
      productName: 'HONDA 2015',
      quantity: '50',
      uomName: 'EA',
      mandays: '14',
      evaluationPrice: '238000000',
      evaluationSubTotal: '11900000000',
      vendor1Price: '238000000',
      vendor1SubTotal: '11900000000',
      vendor2Price: '237000000',
      vendor2SubTotal: '11850000000',
      subItem: null,
      subSubItem: null
    },
    {
      productName: 'HONDA CIVIC 2017',
      quantity: '30',
      uomName: 'EA',
      mandays: '21',
      evaluationPrice: '439000000',
      evaluationSubTotal: '13170000000',
      vendor1Price: '437500000',
      vendor1SubTotal: '13125000000',
      vendor2Price: '438000000',
      vendor2SubTotal: '13140000000',
      subItem: null,
      subSubItem: null
    },
    {
      productName: 'HONDA 2020',
      quantity: '100',
      uomName: 'EA',
      mandays: '30',
      evaluationPrice: '45000000',
      evaluationSubTotal: '4500000000',
      vendor1Price: '43400000',
      vendor1SubTotal: '4340000000',
      vendor2Price: '42800000',
      vendor2SubTotal: '4280000000',
      subItem: null,
      subSubItem: null
    }
  ];

  get defaultDocumentAction() {
    return 'APV';
  }

  onDocumentActionChanged(action: any) {
    this.selectedDocumentAction = action;
    this.showDocumentActionConfirm = true;
  }

  created() {
    console.log('created. isApproval: %s, data: %O', this.approval, this.data);
    this.mainForm = {...this.data};
  }
}
