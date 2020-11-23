import { mixins } from 'vue-class-component';
import Vue from 'vue'
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import InvoiceVerificationDocumentApproval from './invoice-verification-document-approval.vue';
import AlertMixin from '@/shared/alert/alert.mixin';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Inputmask from 'inputmask'

Vue.directive('inputmask', {
  bind: function(el, binding) {
    var inputs = el.getElementsByTagName('INPUT')
    var input = inputs[0]
    if (inputs.length > 1) {
      input = inputs[inputs.length - 1]
    }
    // new Inputmask(binding.value).mask(input)
    new Inputmask({
      autoUnmask: true,
    }).mask(input)
  },
})

const InvoiceVerificationProps = Vue.extend({
  props: {

  }
})

@Component({
  components: {
    InvoiceVerificationDocumentApproval
  }
})
export default class InvoiceVerification extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, InvoiceVerificationProps) {
  private index: boolean = true;
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
    height: 420
  };

  private baseApiUrl = "/api/m-verifications";
  private baseApiUrlLine = "/api/m-verification-lines";

  private eVerification = {
    form: {
      id: "",
      verificationStatus: ""
    },
    lines: [],
    taxInfo: {},
  }

  private filterForm: any = {};
  private filterQuery: string = "";
  private fullscreenLoading: boolean = false;

  private dialogInvoiceVerificationVisible: boolean = false;
  private dialogTitle: string = "";

  created(){

  }

  mounted(): void {

  }

  beforeDestroy() {

  }

  private searchInvoiceVerification(){
    if((this.filterForm.verificationNo == null)||(this.filterForm.verificationNo == "")){
      this.$notify({
        title: 'Warning',
        dangerouslyUseHTMLString: true,
        message: 'Please fill form Verification No.',
        type: 'warning'
      });

    }else{
      this.dialogTitle = "Verification Document Approval"
      this.fullscreenLoading = true;

      setTimeout(() => {
        this.filterQuery = "verificationNo.equals="+this.filterForm.verificationNo;
        this.searchVerification();
      }, 2000);
    }
  }

  private searchVerification(): void {
    if ( ! this.baseApiUrl) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: this.filterQuery
      })
      .then(res => {

        if(res.data.length == 0){
          this.$notify({
            title: 'Warning',
            dangerouslyUseHTMLString: true,
            message: 'Data not found',
            type: 'warning'
          });

        }else{
          if(res.data[0].verificationStatus == "Submit"){
            res.data.map((item: any) => {
              this.$set(this.eVerification, 'form', item);
              this.$set(this.eVerification.form, 'glDate', null);
              this.$set(this.eVerification.form, 'dueDate', null);
              return item;
            });

            this.filterQuery = "";
            this.filterQuery = "verificationId.equals="+this.eVerification.form.id;
            this.searchVerificationLine();
          }else{
            this.$notify({
              title: 'Warning',
              dangerouslyUseHTMLString: true,
              message: 'Status Verification '+res.data[0].verificationStatus,
              type: 'warning'
            });
          }

        }

      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

  private searchVerificationLine(): void {
    if ( ! this.baseApiUrlLine) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlLine)
      .retrieve({
        criteriaQuery: this.filterQuery
      })
      .then(res => {

        this.dialogInvoiceVerificationVisible = true;

        this.$set(this.eVerification, 'lines', res.data);

        //console.log(this.eVerification);

      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

  private actionSubmit(buttonSubmit: any){
    console.log(buttonSubmit);
    console.log(this.eVerification);
  }

}
