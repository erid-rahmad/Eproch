import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";

const ENofaProps = Vue.extend({
  props: {

  }
})

@Component
export default class ENofa extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, ENofaProps) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 412,
    height: 310
  };
  private rules: {
    vendorId: [
      { required: true, message: 'Please input Activity name', trigger: 'blur' },
    ],
    startNo: [
      { required: true, message: 'Please select Activity zone', trigger: 'change' }
    ],
    endNo: [
      { required: true, message: 'Please pick a date', trigger: 'change' }
    ],
  }
  private baseApiUrl = "/api/c-vendors";
  private filterQuery: string = '';
  private processing = false;

  private form: any = {};
  public filterBy = false;

  public vendorOptions: any = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  created(){
    this.form.adOrganizationId = 1;
  }

  public retrieveAllVendorRecords(query: any): void {

    this.filterQuery = "&name.contains="+query;
    this.processing = true;

    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: this.filterQuery,
      })
      .then(res => {
        let referenceList = res.data.map(item => {
          return{
              key: item.id,
              value: item.name
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

  private setForm(){
    this.getForm();
  }

  public getForm(){
    this.$emit('get-form', this.form);
  }

}
