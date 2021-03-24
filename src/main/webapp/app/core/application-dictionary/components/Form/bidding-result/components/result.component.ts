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
    documentNo: 'CE-21030001',
    biddingTitle: 'Tender Kendaraan Operasional',
    biddingNo: 'BD-21030010',
    currencyName: 'IDR',
    picName: 'Indah Haryanti',
    costCenterName: 'Departement #021',
    requisitionName: 'PR-21020098',
    selectedVendors: [],
    remark: null,
    galleryId: null
  };

  tableData = [
    {
      productName: 'Truk Pick-up',
      quantity: '1',
      uomName: 'EA',
      vendor1PriceGap: '4000000',
      vendor1TotalScore: '100',
      vendor2PriceGap: '3000000',
      vendor2TotalScore: '95',
    },
    {
      productName: 'Truk Van',
      quantity: '2',
      uomName: 'EA',
      vendor1PriceGap: '4000000',
      vendor1TotalScore: '100',
      vendor2PriceGap: '0',
      vendor2TotalScore: '50',
    },
    {
      productName: 'Truk Box Medium',
      quantity: '1',
      uomName: 'EA',
      vendor1PriceGap: '25000000',
      vendor1TotalScore: '100',
      vendor2PriceGap: '24000000',
      vendor2TotalScore: '98',
    }
  ];

  manual = true;

  close() {
    this.$emit('close');
  }
}
