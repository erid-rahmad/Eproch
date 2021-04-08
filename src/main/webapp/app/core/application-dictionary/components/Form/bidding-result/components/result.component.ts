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
    costEvaluationNo: 'CE-00001',
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
      vendorName: 'WESTCON INTERNATIONAL INDONESIA',
      proposedPrice: 29450000000,
      priceGap: '0.4%',
      totalScore: '9'
    },
    {
      vendorName: 'SISTECH KHARISMA',
      proposedPrice: 29310000000,
      priceGap: '0.88%',
      totalScore: '14'
    },
    {
      vendorName: 'INGRAM MICRO INDONESIA',
      proposedPrice: 29400000000,
      priceGap: '0.57%',
      totalScore: '10'
    }
  ];

  manual = true;
  showScoreDetail = false;
  showGeneratePoDialog = false;

  close() {
    this.$emit('close');
  }
}
