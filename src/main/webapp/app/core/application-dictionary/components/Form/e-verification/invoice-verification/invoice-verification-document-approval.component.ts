import settings from '@/settings';
import { mixins } from 'vue-class-component';
import Vue from 'vue'
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import AlertMixin from '@/shared/alert/alert.mixin';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
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
    maxHeight: 480,
    height: 320
  };
  rules = {

  }

  private processing = false;

  private gridData: Array<any> = [];
  public payStat: any = [];
  private filter: any = {};

  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private keyPayStat: string = "payStat";

  private dialogTaxInfoVisible: boolean = false;
  private dialogTitle: string = "";

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get formHeader() {
    return this.dataVerificationAndLines.form;
  }

  get lines() {
    return this.dataVerificationAndLines.lines;
  }

  created(){
    this.retrieveGetReferences(this.keyPayStat);
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
