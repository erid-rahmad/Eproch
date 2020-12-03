import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import InvoiceVerificationDocumentApproval from './invoice-verification-document-approval.vue';

@Component({
  components: {
    InvoiceVerificationDocumentApproval
  }
})
export default class InvoiceVerification extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
    height: 420
  };

  private baseApiUrl = "/api/m-verifications";
  private baseApiUrlLine = "/api/m-verification-lines";
  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private keyReference: string = "docStatus";
  public documentStatuses: any[] = [];

  private eVerification = {
    form: {
      id: "",
      verificationStatus: "",
      dateApprove: "",
      dateReject: "",
      dateAcct: "",
      dueDate: "",
      description: "",
    },
    line: [],
    taxInfo: {},
    remove: [],
  }

  private filterForm: any = {};
  private filterQuery: string = "";
  private fullscreenLoading: boolean = false;

  private dialogInvoiceVerificationVisible: boolean = false;
  private dialogTitle: string = "";

  created(){
    this.retrieveGetReferences(this.keyReference);
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
      this.filterQuery = "verificationNo.equals="+this.filterForm.verificationNo;
      this.searchVerification();
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
          if(res.data[0].verificationStatus == "SMT"){
            res.data.map((item: any) => {
              this.$set(this.eVerification, 'form', item);
              return item;
            });

            this.filterQuery = "";
            this.filterQuery = "verificationId.equals="+this.eVerification.form.id;
            this.searchVerificationLine();
          }else{
            this.$notify({
              title: 'Warning',
              dangerouslyUseHTMLString: true,
              message: 'Status Verification is '+this.formatDocumentStatus(res.data[0].verificationStatus),
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

        this.$set(this.eVerification, 'line', res.data.map((item: any) => {
          item.payStat = "A";
          return item;
        }));

        console.log(this.eVerification);

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

  actionSubmit(action: string) {
    let message: string;
    const approval = action === 'APV';

    if (!this.eVerification.form.dateAcct) {
      message = "Please input GL Date";
      this.notifWarning(message);
    } else if (!this.eVerification.form.dueDate) {
      message = "Please input Due Date";
      this.notifWarning(message);
    } else if (!this.eVerification.form.description) {
      message = "Please input Notes";
      this.notifWarning(message);
    } else {
      let now = new Date();
      const offset = now.getTimezoneOffset();

      now = new Date(now.getTime() - (offset * 60 * 1000));
      const dateTrx = now.toISOString().split('T')[0];

      this.eVerification.form.verificationStatus = action;
      this.eVerification.form[approval ? 'dateApprove' : 'dateReject'] = dateTrx;
      this.fullscreenLoading = true;
      this.submit();
    }
  }

  private notifWarning(message: string){
    this.$notify({
      title: 'Warning',
      message: message,
      type: 'warning',
      duration: 3000
    });
  }

  submit() {

    this.dynamicWindowService(this.baseApiUrl + "/submit")
      .update(this.eVerification)
      .then(() => {

        this.$notify({
          title: 'Success',
          dangerouslyUseHTMLString: true,
          message: 'E-Verification form updated.',
          type: 'success'
        });

        this.dialogInvoiceVerificationVisible = false;

      }).catch(error => {
        this.$notify({
          title: 'Error',
          dangerouslyUseHTMLString: true,
          message: error,
          type: 'error',
          duration: 3000
        });
      }).finally(() => {
        this.fullscreenLoading = false;
      });

  }

  private retrieveGetReferences(param: string) {
    this.dynamicWindowService(this.baseApiUrlReference)
    .retrieve({
      criteriaQuery: [`value.contains=`+param]
    })
    .then(res => {
        let references = res.data.map(item => {
            return{
                id: item.id,
                value: item.value,
                name: item.name
            };
        });
        this.retrieveGetReferenceLists(references);
    });
  }

  private retrieveGetReferenceLists(param: any) {
    this.dynamicWindowService(this.baseApiUrlReferenceList)
    .retrieve({
      criteriaQuery: [`adReferenceId.equals=`+param[0].id]
    })
    .then(res => {
        let referenceList = res.data.map(item => {
            return{
                key: item.value,
                value: item.name
            };
        });

        if(param[0].value == this.keyReference){
          this.documentStatuses = referenceList;
        }
    });
  }

  formatDocumentStatus(value: string) {
    return this.documentStatuses.find(status => status.key === value)?.value;
  }

}
