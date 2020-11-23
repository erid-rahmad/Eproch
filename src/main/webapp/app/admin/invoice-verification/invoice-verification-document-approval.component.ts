import { mixins } from 'vue-class-component';
import Vue from 'vue'
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import ContextVariableAccessor from "../../core/application-dictionary/components/ContextVariableAccessor";
import TaxInfo from './tax-info.vue';

const VerificationDocApprProps = Vue.extend({
  props: {
    dataVerificationAndLines: {
      type: Object,
      default: () => {}
    },
  }
})

@Component({
  components: {
    TaxInfo
  }
})
export default class InvoiceVerificationDocumentApproval extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, VerificationDocApprProps) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 250,
    height: 210
  };

  private itemsPerPage = 10;
  private queryCount: number = null;
  private page = 1;
  private previousPage = 1;
  private propOrder = 'id';
  private reverse = false;
  private totalItems = 0;

  private processing = false;

  private gridData: Array<any> = [];
  public payStat: any = {};
  private filter: any = {};

  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private keyPayStat: string = "payStat";

  private dialogTaxInfoVisible: boolean = false;
  private dialogTitle: string = "";


  get formHeader(){
    this.gridData = this.dataVerificationAndLines.lines;
    return this.dataVerificationAndLines.form;
  };

  created(){
    this.retrieveGetReferences(this.keyPayStat);
  }

  mounted() {

  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    const {propOrder: property, reverse} = this;
    this.$emit('order-changed', { property, reverse });
    this.transition();
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    if(this.page!=1){
      this.page = 0;
    }
    //this.retrieveAllRecordMatchPos();
  }

  public transition(): void {
    //this.retrieveAllRecordMatchPos();
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  @Watch('page')
  onPageChange(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public closeDialog(): void {
    //(<any>this.$refs.removeEntity).hide();
  }

  private displayTaxInfo(){
    this.dialogTaxInfoVisible = true;
    this.dialogTitle = "Tax Info Review";
  }

  public dataTaxInfo(data?: any){
    this.dataVerificationAndLines.taxInfo = data;
  }

  private getTaxInfo() {
    console.log(this.dataVerificationAndLines);
    this.dialogTaxInfoVisible = false;
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

        if(param[0].value == this.keyPayStat){
          this.payStat = referenceList;
        }
    });
  }

}
