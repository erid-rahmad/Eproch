import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';

const BiddingResultProp = Vue.extend({
  props: {
    // approval: Boolean
  }
})

@Component
export default class BiddingResult extends mixins(AccessLevelMixin, BiddingResultProp) {

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
    selectedVendors: [],
    remark: null,
    galleryId: null
  };

  tableData = [
    {
      productName: 'HONDA 2015',
      quantity: '50',
      uomName: 'EA',
      vendor1PriceGap: '0',
      vendor1TotalScore: '90',
      vendor2PriceGap: '50000000',
      vendor2TotalScore: '100',
    },
    {
      productName: 'HONDA CIVIC 2017',
      quantity: '30',
      uomName: 'EA',
      vendor1PriceGap: '45000000',
      vendor1TotalScore: '100',
      vendor2PriceGap: '30000000',
      vendor2TotalScore: '95',
    },
    {
      productName: 'HONDA 2020',
      quantity: '100',
      uomName: 'EA',
      vendor1PriceGap: '160000000',
      vendor1TotalScore: '85',
      vendor2PriceGap: '220000000',
      vendor2TotalScore: '100',
    }
  ];

  manual = true;

  close() {
    this.$emit('close');
  }
}
