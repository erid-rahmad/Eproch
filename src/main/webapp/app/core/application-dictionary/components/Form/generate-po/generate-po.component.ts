import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Inputmask from 'inputmask';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

@Component
export default class GeneratePo extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 450,
    height: 450
  };

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  private baseApiUrl = "/api/m-requisitions";
  private baseApiUrlLines = "/api/m-requisition-lines";
  private baseApiUrlVendor = "/api/c-vendors";
  private baseApiUrlWarehouse = "/api/c-warehouses";
  private baseApiUrlCurrency = "/api/c-currencies";

  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private keyReferenceTop: string = "top";

  private filterQuery: string = '';
  private filterQueryFormRecord: string = '';
  private processing: boolean = false;
  private fullscreenLoading: boolean = false;
  public dialogConfirmationVisible: boolean = false;

  public gridData: any[] = [];
  public selectVendor: any[] = [];
  public selectWarehouse: any[] = [];
  public selectTop: any[] = [];
  public selectCurrency: any[] = [];

  private pr = {
    form: {
      id: "",
      warehouseId: "",
      top: "",
      supplierId: "",
      documentDate: "",
      currencyId: ""
    },
    line: [],
  }

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  mounted(): void {
    this.filterQueryFormRecord = "";
    this.retrieveFormRecords(this.baseApiUrlVendor, 'vendor');
    this.retrieveFormRecords(this.baseApiUrlWarehouse, 'warehouse');
    this.retrieveFormRecords(this.baseApiUrlCurrency, 'currency');

    this.retrieveGetReferences(this.keyReferenceTop);
  }

  searchPurchaseRequisition(){
    if((this.pr.form.id == null)||(this.pr.form.id == "")){
      this.$notify({
        title: 'Warning',
        dangerouslyUseHTMLString: true,
        message: 'Please fill form PR No',
        type: 'warning'
      });

    }else{
      this.fullscreenLoading = true;
      this.filterQuery = "id.equals="+this.pr.form.id;
      this.searchPrNo();
    }
  }

  private searchPrNo(): void {
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
          //if(res.data[0].verificationStatus == "SMT"){
            res.data.map((item: any) => {
              this.$set(this.pr, 'form', item);
              return item;
            });

            //console.log(res.data);

            this.filterQuery = "";
            this.filterQuery = "requisitionId.equals="+this.pr.form.id;
            this.searchPrNoLine();
          /*}else{
            this.$notify({
              title: 'Warning',
              dangerouslyUseHTMLString: true,
              message: 'Status Verification is '+this.formatDocumentStatus(res.data[0].verificationStatus),
              type: 'warning'
            });
          }*/

        }

      }).catch(err => {
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

  private searchPrNoLine(): void {
    if ( ! this.baseApiUrlLines) {
      return;
    }

    this.dynamicWindowService(this.baseApiUrlLines)
      .retrieve({
        criteriaQuery: this.filterQuery
      })
      .then(res => {

        //this.dialogInvoiceVerificationVisible = true;

        this.gridData = res.data.map((item: any) => {
          //item.payStat = "A";
          return item;
        });

        console.log(this.pr);

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
    //this.retrieveAllRecords();
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

  public transition(): void {
    //this.retrieveAllRecords();
  }

  public clear(): void {
    this.page = 1;
    //this.retrieveAllRecords();
  }

  public onSelectionChanged(value: any) {
    this.pr.line = value;
    console.log(value);
  }





  public retrieveFormRecords(baseApi, type): void {
    this.dynamicWindowService(baseApi)
      .retrieve({
        criteriaQuery: this.filterQueryFormRecord,
      })
      .then(res => {
        console.log(res);
        let listData = res.data.map(item => {
            return{
                key: item.id,
                value: item.name,
                code: item.code
            };
        });

        if(type == 'vendor'){
          this.selectVendor = listData;
        } else if(type == 'warehouse'){
          this.selectWarehouse = listData;
        } else if(type == 'currency'){
          this.selectCurrency = listData;
        }

      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
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

        if(param[0].value == this.keyReferenceTop){
          this.selectTop = referenceList;
        }
    });
  }

  private generatePurchaseOrder(){
    console.log(this.pr);
  }

}
