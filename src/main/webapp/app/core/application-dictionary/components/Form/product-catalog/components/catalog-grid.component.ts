import settings from '@/settings';
import AlertMixin from '@/shared/alert/alert.mixin';
import Vue from 'vue';
import { mixins } from 'vue-class-component';
import { Component, Watch } from 'vue-property-decorator';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../../ContextVariableAccessor";
import buildCriteriaQueryString from '@/shared/filter/filters';

const CatalogGridProp = Vue.extend({
  props: {
    status: {
      type: String,
      default: () => ''
    },
  }
})
@Component
export default class CatalogGrid extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor, CatalogGridProp) {

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
  public statusCatalog = "";

  private baseApiUrl = "/api/m-product-catalogs";

  private baseQuery: string = '';
  private processing = false;

  public gridData: Array<any> = [];

  selectedRows: Array<any> = [];
  selectedRow: any = {};

  get dateDisplayFormat() {
    return settings.dateDisplayFormat;
  }

  get dateValueFormat() {
    return settings.dateValueFormat;
  }

  get isVendor() {
    return accountStore.userDetails.vendor;
  }

  created() {
    let filterQuery = [];

    if (this.isVendor) {
      filterQuery.push(`cVendorId.equals=${accountStore.vendorInfo.id}`);
    }

    if (this.status === 'R') {
      filterQuery.push('documentStatus.equals=VOD');
    } else if (this.status === 'B') {
      filterQuery.push('documentStatus.equals=RJC');
    } else if (this.status === 'L') {
      filterQuery = [
        filterQuery,
        ...[
          'active.equals=true',
          'documentStatus.equals=APV'
        ]
      ];
    }

    this.baseQuery = buildCriteriaQueryString(filterQuery);
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
    this.$emit("rows-selected", this.selectedRows);
    console.log(value);
  }

  private close(){
    //this.dialogConfirmationVisible = false;
    this.selectedRows = [];
    this.selectedRow = {};
    this.retrieveAllRecords();
  }

  private editRow(row){
    this.selectedRow = row;
    this.$emit("row-selected", this.selectedRow);
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
        criteriaQuery: this.baseQuery,
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data.map((item: any) => {
          if (item.cGallery != null) {
            if (item.cGallery.cGalleryItems.length) {
              item.imgList = item.cGallery.cGalleryItems;
            }
          }

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

  displayImage(imgList){
    var x = 0;
    var img = "";
    if(imgList){
      for(x; x<imgList.length; x++){
        if(imgList[x].preview){
          img = `/api/c-attachments/download/${imgList[x].cAttachment.id}-${imgList[x].cAttachment.fileName}`;
        }
      }
    }

    return img;
  }

  displayImageList(imgList){
    var x = 0;
    var arr = [];

    if(imgList){
      for(x; x<imgList.length; x++){
        arr.push(`/api/c-attachments/download/${imgList[x].cAttachment.id}-${imgList[x].cAttachment.fileName}`);
      }
    }

    return arr;
  }

}
