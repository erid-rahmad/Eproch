import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { ElTable } from 'element-ui/types/table';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';
import BiddingResultAnnouncementDetail from './announcement-detail.vue';

const BiddingResultAnnouncementProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component({
  components: {
    BiddingResultAnnouncementDetail
  }
})
export default class BiddingResultAnnouncement extends mixins(AccessLevelMixin, BiddingResultAnnouncementProp) {

  index = true;
  selectedRow: any = {};

  announcements = [
    {
      biddingNo: 'BN-0001',
      biddingType: 'Tender Goods',
      biddingTitle: 'Pengadaan Kendaraan Operasional',
      type: 'Invitation',
      winner: 'SISTECH KHARISMA',
      totalScore: 16,
      contractAmount: 29000000000,
      currencyName: 'IDR'
    },
    {
      biddingNo: 'BN-0002',
      biddingType: 'Tender Goods',
      biddingTitle: 'Pengadaan Office Equipment',
      type: 'Invitation',
      winner: 'INGRAM MICRO INDONESIA',
      totalScore: 11,
      contractAmount: 29200000000,
      currencyName: 'IDR'
    },
    {
      biddingNo: 'BN-0003',
      biddingType: 'Tender Goods',
      biddingTitle: 'Pengadaan Mesin',
      type: 'Open',
      winner: 'WESTCON INTERNATIONAL',
      totalScore: 12,
      contractAmount: 29100000000,
      currencyName: 'IDR'
    }
  ];

  onCurrentRowChanged(row: any) {
    this.selectedRow = row;
  }

  mounted() {
    this.setRow(this.announcements[0]);
  }

  closeDetail() {
    this.index = true;
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.mainGrid).setCurrentRow(record);
  }

  viewDetail(row: any) {
    this.selectedRow = row;
    this.index = false;
  }
}
