import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import { random } from 'lodash';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import StepForm from "../bidding/steps-form.vue";

@Component({
  components: {
    StepForm,
  }
})
export default class Bidding extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    maxHeight: 500,
    height: 500
  };

  index: boolean = true;

  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public statusCatalog = "";

  private baseApiUrl = "/api/m-biddings";
  private baseApiUrlReference = "/api/ad-references";
  private baseApiUrlReferenceList = "/api/ad-reference-lists";
  private keyReference: string = "docStatus";

  public documentStatuses: any[] = [];

  private filterQuery: string = '';
  private processing = false;

  public gridData: Array<any> = [];

  showJoinedVendors = false;
  editMode: boolean = false;
  stepIndex: number = 0;
  selectedRow: any = null;
  selectedRows: Array<any> = [];

  joinedVendors = [
    {
      name: 'Perum Nurdiyanti Megantara',
      location: ''
    },
    {
      name: 'PT Hidayanto (Persero) Tbk',
      location: ''
    }
  ];

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get randomVendorCount() {
    return random(1, 10);
  }

  created() {
    //console.log(this.status)
  }

  public mounted(): void {
    this.retrieveGetReferences(this.keyReference);
    this.filterQuery = "";
    this.retrieveAllRecords();
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
    this.retrieveAllRecords();
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
    this.retrieveAllRecords();
  }

  public refreshCatalogGrid(){
    this.selectedRows = [];
    this.retrieveAllRecords();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  public onSelectionChanged(value: any) {
    this.selectedRows = value;
    this.$emit("selectedRows", this.selectedRows);
    console.log(value);
  }

  close() {
    //this.dialogConfirmationVisible = false;
    this.index = true;
    this.selectedRows = [];
    this.retrieveAllRecords();
  }

  onClick(key) {
    if (key == 'add') {
      this.editMode = false;
      this.selectedRow = null;
      this.index = false;
    } else {
      console.log(key);
    }
  }

  public retrieveAllRecords(): void {
    if (!this.baseApiUrl) {
      return;
    }

    this.processing = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };

    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: this.filterQuery,
        paginationQuery
      })
      .then(res => {
        console.log(res);

        this.gridData = res.data.map((item: any) => {
          return item;
        });

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        this.$emit('total-count-changed', this.queryCount);

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
        this.selectedRows = [];
      });
  }

  viewBidding(row: any, stepIndex: number = 0) {
    this.stepIndex = stepIndex;
    this.editMode = true;
    this.selectedRow = row;
    this.index = false;
  }

  viewJoinVendor(row: any) {
    this.showJoinedVendors = true;
  }

  formatDocumentStatus(value: string) {
    return this.documentStatuses.find(status => status.key === value)?.value;
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




}
