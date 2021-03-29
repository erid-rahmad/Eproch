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
    documentNo: 'BR-21030001',
    biddingTitle: 'Pengadaan Kendaraan Operasional',
    biddingNo: 'BN-00001',
    currencyName: 'IDR',
    picName: 'admintender',
    costCenterName: 'Marketing',
    requisitionName: 'PR-0025',
    biddingTypeName: 'Tender Goods',
    galleryId: null
  };

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
    }
  ];

  approval = true;

  close() {
    this.$emit('close');
  }
}
