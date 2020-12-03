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
      { required: true, message: 'Please select Address ID' },
    ],
    startNo: [
      { required: true, message: 'Please fill Start No' },
      { min: 15, message: 'Length should be 15' }
    ],
    endNo: [
      { required: true, message: 'Please fill End No' },
      { min: 15, message: 'Length should be 15' }
    ],
  }

  private baseApiUrl = "/api/c-tax-invoices";
  private baseApiUrlVendor = "/api/c-vendors";
  private filterQuery: string = '';
  private processing = false;
  private fullscreenLoading = false;

  private form: any = {};
  public filterBy = false;
  public vendorOptions: any = [];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){

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
        console.log(referenceList);

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
        this.fullscreenLoading = true;
        this.save();

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

  closeDialog(){
    this.$emit('close-dialog');
  }

}
