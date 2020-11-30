import { mixins } from 'vue-class-component';
import Vue from 'vue'
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import ContextVariableAccessor from "../../../ContextVariableAccessor";

@Component
export default class TaxInfoComponent extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
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

  private processing = false;
  private taxInfo: any = {};

  created(){

  }

  private setTaxInfo(){
    this.getTaxInfo();
  }

  public getTaxInfo(){
    this.$emit('get-form-tax-info', this.taxInfo);
  }

}
