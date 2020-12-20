import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../../../../core/application-dictionary/components/ContextVariableAccessor";
import { ElForm } from 'element-ui/types/form';

@Component
export default class ENofa extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  rules = {
    vendorId: [
      { required: true, message: 'Please select Address ID'},
    ],
    startNo: [
      { required: true, message: 'Please fill Start No'},
      { min: 15, message: 'Length must be 13 digits' }
    ],
    endNo: [
      { required: true, message: 'Please fill End No'},
      { min: 15, message: 'Length must be 13 digits' }
    ],
  };

  private baseApiUrl = "/api/c-tax-invoices";
  private baseApiUrlVendor = "/api/c-vendors";
  private filterQuery: string = '';
  private processing = false;
  private fullscreenLoading = false;

  private form: any = {};
  public filterBy = false;
  public vendorOptions: any = [];
  enofaList: any[] = [];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.retrieveEnofa();
  }

  retrieveEnofa() {
    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['id']
        }
      })
      .then(res => this.enofaList = res.data)
  }

  public retrieveAllVendorRecords(query: any): void {

    this.filterQuery = "&code.equals="+query;
    this.processing = true;

    this.dynamicWindowService(this.baseApiUrlVendor)
      .retrieve({
        criteriaQuery: this.filterQuery,
      })
      .then(res => {
        let referenceList = res.data.map(item => {
          return{
              key: item.id,
              value: item.code,
              name: item.name
          };
        });

        this.vendorOptions = referenceList;
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.processing = false;
      });
  }

  confirmSave(){
    (this.$refs.eNofaForm as ElForm).validate((passed, errors) => {
      if (passed) {
        if(this.form.startNo < this.form.endNo){
          this.fullscreenLoading = true;
          this.checkRange();
        }else{
          this.$notify({
            title: "Warning",
            dangerouslyUseHTMLString: true,
            message: "Start No. must be less than End No.",
            type: "warning",
            duration: 3000
          });
        }

      } else {
        return false;
      }
    })
  }

  private save(){
    this.dynamicWindowService(this.baseApiUrl)
      .create(this.form)
      .then(() => {

        this.$notify({
          title: "Success",
          dangerouslyUseHTMLString: true,
          message: "Success created.",
          type: "success",
          duration: 3000
        });

        this.closeDialog();

      }).catch(error => {
        this.$notify({
          title: "Error",
          dangerouslyUseHTMLString: true,
          message: error,
          type: "error",
          duration: 3000
        });

      }).finally(() => {
        this.fullscreenLoading = false;
        this.form = {};
      });

  }

  public checkRange(): void {
    const length = this.enofaList.length;
    const formStartNo = parseInt(this.form.startNo.replace(/[-.]+/g, ''));
    const formEndNo = parseInt(this.form.endNo.replace(/[-.]+/g, ''));
    let proceed = true;

    for (let i = 0; i < length; i++) {
      const startNo = parseInt(this.enofaList[i].startNo);
      const endNo = parseInt(this.enofaList[i].endNo);

      if (formStartNo <= endNo && formEndNo >= startNo) {
        proceed = false;
        this.$message({
          message: "The entered tax invoice range intersects another entry",
          type: "error"
        });
        break;
      }
    }
    
    if (proceed) {
      this.save();
    }
    this.fullscreenLoading = false;
  }

  closeDialog(){
    this.$emit('close-dialog');
  }

}
