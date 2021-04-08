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
          event: 'Bidding Explanation Meeting',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
        {
          no: '6',
          event: 'Bidding Review',
          start: '02/10/2021 14:00',
          end: '02/20/2021 14:00',
        },
       
        
      ]
    }
  };


}
