import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import InvoiceVerificationDocumentApproval from './invoice-verification-document-approval.vue';
import { ElForm } from 'element-ui/types/form';
import { ElInput } from 'element-ui/types/input';

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
      documentStatus: "",
      dateApprove: "",
      dateReject: "",
      dateAcct: "",
      dueDate: "",
      description: "",
    },
    lines: [],
    taxInfo: {},
    removedLines: [],
  }

  private filterForm: any = {};
  private fullscreenLoading: boolean = false;

  private dialogInvoiceVerificationVisible: boolean = false;
  private dialogTitle: string = "";

  created() {
    this.retrieveGetReferences(this.keyReference);
  }

  mounted() {
    (<ElInput>this.$refs.searchField).focus();
  }

  searchInvoiceVerification() {
    (<ElForm>this.$refs.form).validate(valid => {
      if (valid) {
        this.dialogTitle = "Verification Document Approval"
        this.fullscreenLoading = true;
        this.searchVerification(this.filterForm.documentNo);
      }
      return valid;
    });
  }

  private searchVerification(no: string): void {
    if (!this.baseApiUrl) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: [
          `documentNo.equals=${no}`,
          'documentStatus.in=SMT',
          'documentStatus.in=ROP'
        ]
      })
      .then(res => {
        if ( ! res.data.length) {
          this.$message({
            message: 'Record is not found',
            type: 'info'
          });
        } else {
          const item = res.data[0];

          this.$set(this.eVerification, 'form', item);
          this.searchVerificationLine(item.id);
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

  private searchVerificationLine(headerId: number): void {
    if ( ! this.baseApiUrlLine) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlLine)
      .retrieve({
        criteriaQuery: `verificationId.equals=${headerId}`,
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['lineNo']
        }
      })
      .then(res => {
        this.dialogInvoiceVerificationVisible = true;
        this.$set(this.eVerification, 'lines', res.data.map((item: any) => {
          item.payStat = 'A';
          return item;
        }));
      })
      .catch(err => {
        this.$message({
          type: 'error',
          message: err.response?.data?.detail || err.message
        });
      })
      .finally(() => {
        this.fullscreenLoading = false;
      });
  }

  actionSubmit(action: string) {
    let message: string;
    const approval = action === 'APV';
    let submit = false;

    if (approval) {
      if (!this.eVerification.form.dateAcct) {
        message = "Please input GL Date";
        this.showErrorMessage(message);
      } else if (!this.eVerification.form.dueDate) {
        message = "Please input Due Date";
        this.showErrorMessage(message);
      } else {
        this.$set(this.eVerification.form, 'description', null);
        submit = true;
      }
    } else {
      if (!this.eVerification.form.description) {
        message = "Please input Notes";
        this.showErrorMessage(message);
      } else {
        this.$set(this.eVerification.form, 'dateAcct', null);
        this.$set(this.eVerification.form, 'dueDate', null);
        submit = true;
      }
    }

    if (submit) {
      let now = new Date();
      const offset = now.getTimezoneOffset();

      now = new Date(now.getTime() - (offset * 60 * 1000));
      const dateTrx = now.toISOString().split('T')[0];

      this.eVerification.form.documentStatus = action;
      this.eVerification.form[approval ? 'dateApprove' : 'dateReject'] = dateTrx;
      this.fullscreenLoading = true;
      this.submit(approval);
    }
  }

  private showErrorMessage(message: string) {
    this.$message({
      message: message,
      type: 'error'
    });
  }

  submit(approval: boolean) {
    this.dynamicWindowService(this.baseApiUrl + "/update-document")
      .update(this.eVerification)
      .then(() => {
        this.$message({
          message: `Invoice verification has been ${approval ? 'approved' : 'rejected'}`,
          type: 'success'
        });
      })
      .catch(err => {
        this.$message({
          message: err.response?.data?.detail || err.message,
          type: 'error'
        });
      }).finally(() => {
        this.fullscreenLoading = false;
        this.dialogInvoiceVerificationVisible = false;
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
