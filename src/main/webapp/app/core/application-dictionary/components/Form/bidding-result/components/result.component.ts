import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import ScoreDetail from "./score-detail.vue";

const BiddingResultProp = Vue.extend({
  props: {
    // approval: Boolean
  }
})

@Component({
  components: {
    ScoreDetail
  }
})
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
    documentNo: null,
    biddingNo: 'BN-00001',
    biddingEvaluationNo: 'BE-00101',
    biddingTitle: 'Pengadaan Kendaraan Operasional',
    biddingType: 'Tender Goods',
    picName: 'Admin Tender',
    costCenterName: 'Marketing',
    requisitionName: 'PR-0025',
    selectedVendors: [],
    remark: null,
    galleryId: null
  };

  tableData = [
    {
      vendorName: 'SISTECH KHARISMA',
      proposedPrice: 29000000000,
      priceGap: '3%',
      totalScore: '30'
    },
    {
      vendorName: 'INGRAM MICRO INDONESIA',
      proposedPrice: 29200000000,
      priceGap: '5%',
      totalScore: '22'
    },
    {
      vendorName: 'WESTCON INTERNATIONAL INDONESIA',
      proposedPrice: 29100000000,
      priceGap: '4%',
      totalScore: '24'
    }
  ];

  manual = true;
  showScoreDetail = false;
  showGeneratePoDialog = false;

  close() {
    this.$emit('close');
  }
}
