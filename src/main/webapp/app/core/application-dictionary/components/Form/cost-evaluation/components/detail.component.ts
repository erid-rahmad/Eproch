import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';

const CostEvaluationDetailProp = Vue.extend({
  props: {
    // approval: Boolean
  }
})

@Component
export default class CostEvaluationDetail extends mixins(AccessLevelMixin, CostEvaluationDetailProp) {

  columnSpacing = 24;

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

  mainForm = {
    documentNo: 'CE-21030001',
    biddingTitle: 'Tender Kendaraan Operasional',
    biddingNo: 'BD-21030010',
    currencyName: 'IDR',
    picName: 'Indah Haryanti',
    costCenterName: 'Departement #021',
    requisitionName: 'PR-21020098',
    galleryId: null
  };

  tableData = [
    {
      productName: 'Truk Pick-up',
      quantity: '1',
      uomName: 'EA',
      mandays: '14',
      evaluationPrice: '123000000',
      evaluationSubTotal: '123000000',
      vendor1Price: '119000000',
      vendor1SubTotal: '119000000',
      vendor2Price: '120000000',
      vendor2SubTotal: '120000000',
    },
    {
      productName: 'Truk Van',
      quantity: '2',
      uomName: 'EA',
      mandays: '21',
      evaluationPrice: '245000000',
      evaluationSubTotal: '490000000',
      vendor1Price: '243000000',
      vendor1SubTotal: '486000000',
      vendor2Price: '245000000',
      vendor2SubTotal: '490000000',
    },
    {
      productName: 'Truk Box Medium',
      quantity: '1',
      uomName: 'EA',
      mandays: '30',
      evaluationPrice: '550000000',
      evaluationSubTotal: '550000000',
      vendor1Price: '525000000',
      vendor1SubTotal: '525000000',
      vendor2Price: '526000000',
      vendor2SubTotal: '526000000',
    }
  ];

  approval = true;

  close() {
    this.$emit('close');
  }
}
