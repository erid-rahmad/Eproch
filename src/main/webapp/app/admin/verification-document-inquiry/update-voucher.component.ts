import { mixins } from 'vue-class-component';
import Vue from 'vue'
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";

const UpdateVoucherProps = Vue.extend({
  props: {
    setVerificationNo: {
      type: String,
      default: () => ""
    },
  }
})

@Component
export default class UpdateVoucherComponent extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, UpdateVoucherProps) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 412,
    height: 310
  };
  private itemsPerPage = 10;
  private queryCount: number = null;
  private page = 1;
  private previousPage = 1;
  private propOrder = 'id';
  private reverse = false;
  private totalItems = 0;

  private voucher: any = {};

  created(){
    this.voucher.verificationNo = this.setVerificationNo;
  }

  private setVoucher(){
    this.getVoucher();
  }

  public getVoucher(){
    this.$emit('get-form-voucher', this.voucher);
  }

}
