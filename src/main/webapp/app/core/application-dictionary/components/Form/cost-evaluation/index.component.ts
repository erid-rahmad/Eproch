import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import Vue from 'vue';
import Component, { mixins } from 'vue-class-component';

const CostEvaluationDetailProp = Vue.extend({
  props: {
    approval: Boolean
  }
})

@Component
export default class CostEvaluationDetail extends mixins(AccessLevelMixin, CostEvaluationDetailProp) {

  costEvaluations = [
    {
      documentNo: 'CE-21030001',
      biddingNo: 'BD-21030010',
      biddingTitle: 'Tender Kendaraan Operasional',
      biddingTypeName: 'Bidding #T001',
      documentStatus: 'Submitted',
      lastModifiedDate: '2021-03-20 09:42',
      lastModifiedBy: 'admin'
    },
    {
      documentNo: 'CE-21030002',
      biddingNo: 'BD-21030011',
      biddingTitle: 'Tender Ruang Meeting',
      biddingTypeName: 'Bidding #T002',
      documentStatus: 'Draft',
      lastModifiedDate: '2021-03-14 10:44',
      lastModifiedBy: 'admin'
    },
    {
      documentNo: 'CE-21030003',
      biddingNo: 'BD-21030009',
      biddingTitle: 'Tender Stationary',
      biddingTypeName: 'Bidding #T001',
      documentStatus: 'Draft',
      lastModifiedDate: '2021-03-03 14:30',
      lastModifiedBy: 'admin'
    },
    {
      documentNo: 'CE-21030004',
      biddingNo: 'BD-21030005',
      biddingTitle: 'Tender Taman Burung',
      biddingTypeName: 'Bidding #T001',
      documentStatus: 'Revised',
      lastModifiedDate: '2021-03-01 11:17',
      lastModifiedBy: 'admin'
    }
  ];
}
