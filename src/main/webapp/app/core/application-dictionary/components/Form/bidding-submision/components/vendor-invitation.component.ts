import { ElForm } from 'element-ui/types/form';
import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import Vue2Filters from 'vue2-filters';
import Vue from 'vue';
import Component from 'vue-class-component';
import { buildCascaderOptions } from '@/utils/form';
import ContextVariableAccessor from "../../../ContextVariableAccessor";

const VendorInvitationProp = Vue.extend({
  props: {
    biddingInformation: {
      type: Object,
      default: () => {}
    },
    vendorInvitation: {
      type: Object,
      default: () => {}
    },
  }
})

@Component
export default class VendorInvitation extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, VendorInvitationProp) {

  data() {
    return {
      eventschedule: [
        {
          no: '1',
          event: 'Bidding Announcement',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        {
          no: '2',
          event: 'Vendor Brieving',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        {
          no: '4',
          event: 'Vendor Persentation',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        {
          no: '5',
          event: 'Bidding Submission',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        {
          no: '6',
          event: 'Bidding Evaluation',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        {
          no: '7',
          event: 'Bidding Approval',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        {
          no: '8',
          event: 'Bidding Result',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        
      ]
    }
  };


}
