import { mixins } from 'vue-class-component';
import Vue from 'vue'
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import { ElForm } from 'element-ui/types/form';

const UpdateVoucherProps = Vue.extend({
  props: {
    setVerification: {
      type: Object,
      default: () => {}
    },
  }
})

@Component
export default class UpdateVoucherComponent extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, UpdateVoucherProps) {
  rules = {
    invoiceAp: [
      { required: true, message: 'Please fill Voucher No'},
    ],
    docType: [
      { required: true, message: 'Please fill Doc Type'},
    ]
  };

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 412,
    height: 310
  };

  private baseApiUrlEVerification = "/api/m-verifications";
  fillVoucherNo: boolean = false;
  private fullscreenLoading: boolean = false;

  private voucher: any = {
    invoiceAp: "",
    docType: ""
  };

  get getVerification(){
    if(this.setVerification.invoiceAp) {
      this.fillVoucherNo = true;
      this.voucher.invoiceAp = this.setVerification.invoiceAp;
      this.voucher.docType = this.setVerification.docType;
    } else {
      this.fillVoucherNo = false;
      this.voucher.invoiceAp = "";
      this.voucher.docType = "";
    }
    return this.setVerification;
  }

  confirmUpdate(){
    (this.$refs.form as ElForm).validate((passed, errors) => {
      if (passed) {
        this.fullscreenLoading = true;
        this.onUpdateVoucherApplied();
      } else {
        return false;
      }
    })
  }

  private onUpdateVoucherApplied(){

    this.setVerification.invoiceAp = this.voucher.invoiceAp;
    this.setVerification.docType = this.voucher.docType;

    this.dynamicWindowService(this.baseApiUrlEVerification)
      .update(this.setVerification)
      .then(() => {

        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: 'Update Voucher No updated.',
          type: 'success'
        });

        this.closeDialog();

      }).catch(error => {
        this.$notify({
          title: 'Error',
          message: error,
          type: 'error',
        });
      }).finally(() => {
        this.fullscreenLoading = false;
      });

  }

  closeDialog(){
    this.$emit('close-dialog');
  }

}
