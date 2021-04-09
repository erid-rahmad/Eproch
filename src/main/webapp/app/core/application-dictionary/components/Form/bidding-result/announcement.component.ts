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
      biddingNo: 'BN-00000',
      biddingType: 'Tender Goods',
      biddingTitle: 'Pengadaan Mesin',
      type: 'Open',
      winner: 'WESTCON INTERNATIONAL',
      totalScore: 12,
      contractAmount: 29100000000,
      currencyName: 'IDR',
      status:'Terminate '
    },
    {

      biddingNo: 'BN-00001',
      biddingType: 'Tender Goods',
      biddingTitle: 'Pengadaan Kendaraan Operasional',
      type: 'Invitation',
      winner: 'SISTECH KHARISMA',
      totalScore: 14,
      contractAmount: 29310000000,
      currencyName: 'IDR',
      status:'Winner Selection'
    },
    {
      biddingNo: 'BN-00002',
      biddingType: 'Tender Goods',
      biddingTitle: 'Pengadaan Office Supply',
      type: 'Invitation',
      winner: 'INGRAM MICRO INDONESIA',
      totalScore: 11,
      contractAmount: 29200000000,
      currencyName: 'IDR',
      status:'In Progres'
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
