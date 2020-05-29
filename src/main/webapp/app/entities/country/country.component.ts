import { mixins } from 'vue-class-component';
import { Select } from 'element-ui';
import { Component, Inject, Watch, Vue } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { formatJson } from '@/utils';
import { exportJson2Excel } from '@/utils/excel';
import AlertMixin from '@/shared/alert/alert.mixin';

import CountryService from './country.service';
import { ICountry } from '@/shared/model/country.model';

import CurrencyService from '../currency/currency.service';
import { ICurrency } from '@/shared/model/currency.model';

import RegionService from '../region/region.service';
import { IRegion } from '@/shared/model/region.model';

import CityService from '../city/city.service';
import { ICity } from '@/shared/model/city.model';

import { ElTable } from 'element-ui/types/table';
import { CrudEventBus } from '@/core/event/crud-event-bus';

@Component({
  name: 'CountryList',
})
export default class Country extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('countryService') private countryService: () => CountryService;
  public countries = [];

  @Inject('currencyService') private currencyService: () => CurrencyService;
  public currencies: ICurrency[] = [];

  @Inject('regionService') private regionService: () => RegionService;
  public regions: IRegion[] = [];

  @Inject('cityService') private cityService: () => CityService;
  public cities: ICity[] = [];

  public showDeleteDialog: boolean = false;
  public showVisibleColumn: boolean = false;
  public showExport: boolean = false;
  public showImport: boolean = false;
  public showFilterRecord: boolean = false;
  public checkedActive: boolean = true;
  public checkSelected: boolean = true;
  public buttonDisableExport: boolean = true;
  public buttonDisableImport: boolean = true;
  
  //visible column
  public showName: boolean = true;
  public showCode: boolean = true;
  public showCurrency: boolean = true;
  public showActive: boolean = true;

  //pagination
  public getId: number = null;
  public queryCount: number = null;
  public itemsPerPage = 10;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'name';
  public reverse = true;
  public totalItems = 0;

  public isFetching = false;

  //new
  public multipleSelectionCheckbox = [];
  public multipleSelection = [];
  public multipleSelectionFilterAdvance = [];
  public tempMultipleSelectionFilterAdvance = '';
  public tempMultipleSelectionFilterAdvanceColumn = '';
  public tempMultipleSelectionFilterAdvanceQuery = '';
  public tempMultipleSelectionFilterAdvanceQueryValue = '';
  public listLoading = true;
  public isSaving = false;

  //export file excel
  public isLoading = false;
  public filename = 'Country List';
  public autoWidth = true;
  public bookType = '';
  public importHeaders = {}

  public formExport = {
    id: undefined,
    name: undefined,
    type: undefined
  }

  public tabChild = [];
  public multipleTab = [
    { id:'1', key:'region', name:'Region'},
    { id:'3', key:'translation', name:'Translation'},
    //{ id:'3', key:'countryGroup', name:'Country Group'},
  ];

  public chooseBookTypeExport = [
    { id: '1', type: 'csv', name: "CSV" },
    { id: '2', type: 'xlsx', name: "XLSX" },
    { id: '3', type: 'xls', name: "XLS" },
  ]

  public chooseBookTypeImport = [
    { id: '1', type: '.csv', name: "CSV" },
    { id: '2', type: '.xls', name: "XLS" },
  ]

  public selectTabToExport = [
    { id: '1', entity: 'region', name: "Region" },
  ]

  /*public formImport = {
    bookType: undefined,
  }*/

  //filter query
  public changeColumnOptionType = '';
  public filterQuery = '';
  public filterQueryCode = '';
  public filterQueryName = '';
  public filterQueryCurrency = '';
  public listQuery = {
    code: undefined,
    name: undefined,
    currencyId: undefined
  }

  //sample itemListQueryAdvance
  public itemListQueryAdvance= [{
    column: '', 
    query: '', 
    queryValue: ''
  }]
  public addFilterAdvance(): void {
    this.itemListQueryAdvance.push({ 
      column: '', 
      query: '', 
      queryValue: '' 
    });
  }
  public removeFilterAdvance(item, index) {
    this.itemListQueryAdvance.splice(index, 1);
  }

  public activeTabsSearch = {
    active: 'first'
  }

  public column = [
    { id: '1', code: 'code', value: 'Code', type: 'String' },
    { id: '2', code: 'name', value: 'Name', type: 'String' },
    { id: '3', code: 'currencyId', value: 'Currency Id', type: 'Integer' },
    { id: '4', code: 'currencyName', value: 'Currency Name', type: 'String' },
  ]

  public query = [
    { id: '1', code: 'equals', value: 'Equals', type: 'Integer' },
    { id: '2', code: 'notEquals', value: 'Not Equals', type: 'Integer' },
    { id: '3', code: 'greaterThan', value: 'Greater Than', type: 'Integer' },
    { id: '4', code: 'greaterThanOrEqual', value: 'Greater Than Or Equal', type: 'Integer' },
    { id: '5', code: 'lessThan', value: 'Less Than', type: 'Integer' },
    { id: '6', code: 'lessThanOrEqual', value: 'Less Than Or Equal', type: 'Integer' },
    
    { id: '7', code: 'contains', value: 'Contains', type: 'String' },
    { id: '8', code: 'doesNotContain', value: 'Does Not Contain', type: 'String' },
    { id: '9', code: 'equals', value: 'Equals', type: 'String' },
    { id: '10', code: 'notEquals', value: 'Not Equals', type: 'String' },

    { id: '11', code: 'specified', value: 'Specified', type: 'bool' },

    { id: '12', code: 'in', value: 'In', type: 'array' },
    { id: '13', code: 'sort', value: 'Sort', type: 'array' },
  ]

  created() {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    this.importHeaders['Authorization'] = `Bearer ${token}`;
  }

  public mounted(): void {
    this.clear();
    CrudEventBus.$on('country-update-success', this.clear);
    this.initRelationships();
  }

  public clear(): void {
    this.page = 1;
    this.filterQuery = '';
    this.filterQueryCode = '';
    this.filterQueryName = '';
    this.filterQueryCurrency = '';
    this.tempMultipleSelectionFilterAdvance = '';
    this.multipleSelection = [];
    this.listQuery.code = '';
    this.listQuery.name = '';
    this.listQuery.currencyId = '';
    this.retrieveAndClearSelectionRow();
  }

  public retrieveAndClearSelectionRow(): void{
    this.multipleSelectionCheckbox.splice(0);
    this.retrieveAllCountries();
  }

  public handleClickTabSearch(tab, event) {
    //console.log(tab);
  }

  columnOptionType(key: any) {
    this.changeColumnOptionType = key.type;
  }
  
  public handleSelectionChangeFilterAdvance(value: any) {
    this.multipleSelectionFilterAdvance = value;
  }

  public executeFilterAdvance(): void{
    
    if(this.multipleSelectionFilterAdvance.length){
      this.tempMultipleSelectionFilterAdvance = '';
      for (let i = 0; i < this.multipleSelectionFilterAdvance.length; i++) {

        this.tempMultipleSelectionFilterAdvanceColumn = this.multipleSelectionFilterAdvance[i].column.code;
        this.tempMultipleSelectionFilterAdvanceQuery = this.multipleSelectionFilterAdvance[i].query.code;
        this.tempMultipleSelectionFilterAdvanceQueryValue = this.multipleSelectionFilterAdvance[i].queryValue;
        
        this.tempMultipleSelectionFilterAdvance += `&${this.tempMultipleSelectionFilterAdvanceColumn}.${this.tempMultipleSelectionFilterAdvanceQuery}=${this.tempMultipleSelectionFilterAdvanceQueryValue}`;
      }
      this.filterQuery = '';
      this.filterQuery = this.tempMultipleSelectionFilterAdvance;
      this.retrieveAndClearSelectionRow();

    }else{
      this.$notify({
        title: 'Warning',
        message: "Please check filter row",
        type: 'warning',
        duration: 3000
      });
    }
  }

  public handleBasicFilter() {
      if(this.listQuery.code){
        this.filterQueryCode = `&code.contains=${this.listQuery.code}`;
      }else{
        this.filterQueryCode = '';
      }

      if(this.listQuery.name){
        this.filterQueryName = `&name.contains=${this.listQuery.name}`;
      }else{
        this.filterQueryName = '';
      }

      if(this.listQuery.currencyId){
        this.filterQueryCurrency = `&currencyId.equals=${this.listQuery.currencyId}`;
      }else{
        this.filterQueryCurrency = '';
      }

      this.filterQuery = this.filterQueryCode + this.filterQueryName + this.filterQueryCurrency;
      this.retrieveAndClearSelectionRow();

  }

  get operators(){
    return this.query.filter(query=>query.type===this.changeColumnOptionType);
  }
  
  public retrieveAllCountries(): void {
    this.listLoading = true;
    this.isFetching = true;
    this.isSaving = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.countryService()
      .retrieve(
        paginationQuery, this.filterQuery
      )
      .then(
        res => {
          //this.countries = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          
          this.countries = res.data.map((v: any) => {
            this.$set(v, 'edit', false)
            v.originalName = v.name
            return v
          })

          setTimeout(() => {
            this.listLoading = false;
            this.isFetching = false;
            this.isSaving = false;
          }, 0.5 * 1000)
      })
      .catch(()=>{
        const message = "Error, ........";
        this.$notify({
          title: 'Error',
          message: message.toString(),
          type: 'error',
          duration: 3000
        });

        this.listLoading = false;
        this.isFetching = false;
        this.isSaving = false;
        
      });
  }

  public prepareRemove(instance: ICountry): void {
    this.getId = instance.id;
    this.showDeleteDialog = true;
  }

  public handleSelection(params: string){
    
    if (this.multipleSelection.length) {

      if(params === 'remove'){
        this.handleMultipleDataToJson(this.multipleSelection)
        this.showDeleteDialog = true;

      }else if(params === 'duplicate'){

        if(this.multipleSelection.length == 1){
          this.countries.push({ 
            name: this.multipleSelection[0].name, 
            code: this.multipleSelection[0].code, 
            currencyId: this.multipleSelection[0].currencyId,
            currencyName: this.multipleSelection[0].currencyName,
            edit: true,
            duplicate: true
          })

        }else{
          this.$notify({
            title: 'Warning',
            message: "Please select at one item only",
            type: 'warning',
            duration: 3000
          });

        }
      }
      
    }else{

      this.$notify({
        title: 'Warning',
        message: "Please select at least one item",
        type: 'warning',
        duration: 3000
      });
    }
    
  }

  public handleMultipleDataToJson(params: any) {
      const filterVal = ['id', 'code', 'name', 'currencyCode'];
      const data = formatJson(filterVal, params);

      this.getId = data;
      //this.showDeleteDialog = true;
  }

  public removeCountry(): void {
    for (let i = 0; i < this.multipleSelection.length; i++) {
      //console.log(this.removeId[i][0])
      this.countryService()
        .delete(this.getId[i][0])
        .then(() => {
          const message = this.$t('opusWebApp.country.deleted', { param: this.getId[i][0] });
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
          this.getId[i] = null;
          this.clear();
          this.closeDialog();

        })
        .catch(()=>{
          const message = "Error, already used in region or city/ ........";
          this.$notify({
            title: 'Error',
            message: message.toString(),
            type: 'error',
            duration: 3000
          });
          this.closeDialog();

        });
        //error because code country used region & city
    }
  }

  public async handleExport() {
    this.bookType = this.formExport.type;
    this.filename = this.formExport.name;
    this.isLoading = true;

    const tHeader = ['Id', 'Code', 'Name', 'Currency', 'Currency Name'];
    const filterVal = ['id', 'code', 'name', 'currencyCode', 'currencyName'];
    var countries: any = '';
    var data: any = '';

    if(this.checkSelected == true){
      //selected export row
      if(this.multipleSelection.length >= 1){
        for (let i = 0; i < this.multipleSelection.length; i++) {
          countries = this.countries[i];
          data = formatJson(filterVal, this.multipleSelection);
        }
      }

    }else{
      //export all
      const res = await this.countryService().retrieve();
      // this.query.filter(query=>query.type===this.changeColumnOptionType);
      countries = res.data;
      data = formatJson(filterVal, countries);
    }

    
    console.log(this.tabChild);
    console.log(data);
    console.log(this.regions);
    exportJson2Excel(tHeader, data, this.filename !== '' ? this.filename : undefined, undefined, undefined, this.autoWidth, this.bookType);
    this.isLoading = false;
  }

  public handleExportSelection(){
    if(this.checkSelected == true){

      if(this.multipleSelection.length >= 1){
        this.buttonDisableExport = false;
        
      }else{
        this.buttonDisableExport = true;
        this.$notify({
          title: 'Warning',
          message: "Please select at least one item",
          type: 'warning',
          duration: 3000
        });

      }

    }else{
      this.buttonDisableExport = false

    }

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

  public handleSizeChange(size: number) {
    this.itemsPerPage = size;
    this.retrieveAndClearSelectionRow();
  }

  public handleSelectionChangeAll(value: any) {
    //this.multipleSelectionCheckbox.splice(0);
    //console.log(value.length);
    this.multipleSelection = value;
    if(value.length === 0){
      this.buttonDisableExport = true;
    }else{
      this.buttonDisableExport = false;
    }
  }

  public handleSelectionChange(value: any, o: any) {

    this.multipleSelection = value;
    if(value.length === 0){
      this.buttonDisableExport = true;
    }else{
      this.buttonDisableExport = false;
    }

    this.countries.forEach((r,i) => {
      //highlight selection
      if(r.id === o.id) {
        if(this.multipleSelectionCheckbox.indexOf(i) === -1) {
          this.multipleSelectionCheckbox.push(i)
        } else {
          this.multipleSelectionCheckbox.splice(this.multipleSelectionCheckbox.indexOf(i), 1)
        } 
      }
    })

  }
  
  rowClassName({row, rowIndex}) {
    //highlight all
    return (<any>this.$refs.tableCheck).selection.find(element => element.id == row.id) ? 'warning-row' : '';
  }

  /*tableRowClassName({row, rowIndex}) {
    let color = ''
    this.multipleSelectionCheckbox.forEach((r,i) => {
      if (rowIndex === r) {
        color =  'warning-row';
      }
    })
    return color;
  }*/

  public transition(): void {
    this.retrieveAndClearSelectionRow();
  }
/*
  //select single row
  public changeClassificationSelection(currentRow: ICountry) {
    console.log('Selected classification#%d', currentRow.id);
  }
*/
  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    this.transition();
  }

  public closeDialog(): void {
    this.showFilterRecord = false;
    this.showDeleteDialog = false;
    this.showVisibleColumn = false;
    this.showExport = false;
    this.showImport = false;
  }

  public openDetails(instance: ICountry) {
    this.$router.push(`/country/${instance.id}/view`);
  }

  public add() {
    this.$router.push('/country/new');
  }

  public edit(instance: ICountry) {
    this.$router.push(`/country/${instance.id}/edit`);
  }

  public confirmEdit(row: any) {
    row.edit = false
    row.originalName = row.name

    if (row.id) {
      this.countryService()
        .update(row)
        .then(param => {
          this.isSaving = false;
          const message = this.$t('opusWebApp.country.updated', { param: param.id });
          this.clear();
          //CrudEventBus.$emit('country-update-success')
          this.$notify({
            title: 'Success',
            message: message.toString(),
            type: 'success',
            duration: 3000
          });
        })
        .catch((e)=>{
          const message = `${e}`;
          this.$notify({
            title: 'Error',
            message: message.toString(),
            type: 'error',
            duration: 3000
          });
          this.isSaving = false;
        });
    }

  }

  public cancelEdit(row: any) {
    row.name = row.originalName
    row.edit = false
    /*this.$message({
      message: 'The title has been restored to the original value',
      type: 'warning'
    })*/
  }

  public cancelDuplicate(item, index){
    this.countries.splice(this.countries.findIndex(e => e === index), 1)
  }

  public confirmDuplicate(row: any){

    this.countryService()
      .create(row)
      .then(param => {
        this.isSaving = false;
        const message = this.$t('opusWebApp.country.created', { param: param.id });
        this.clear();
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        });
      })
      .catch(()=>{
        const message = "Error, data already exist/ .........";
        this.$notify({
          title: 'Error',
          message: message.toString(),
          type: 'error',
          duration: 3000
        });
        this.isSaving = false;
      });

  }

  public checkActive(row: any) {
    console.log(row.id);
    //this.checkedActive = !this.checkedActive;
  }

  public initRelationships(): void {
    this.currencyService()
      .retrieve()
      .then(res => {
        this.currencies = res.data;
      });
    this.regionService()
      .retrieve()
      .then(res => {
        this.regions = res.data;
      });
    this.cityService()
      .retrieve()
      .then(res => {
        this.cities = res.data;
      });
  }

  //show modal
  public handleModalVisible(param: string) {
    if(param === 'export'){
      this.showExport = true;
      
    }else if(param === 'import'){
      this.showImport = true;

    }else if(param === 'visibleColumn'){
      this.showVisibleColumn = true;

    }else if(param === 'filterRecord'){
      this.showFilterRecord = true;

    }
  }

  //attribute import csv
  changeBookType(key: any){
    if(key){
      this.buttonDisableImport = false;
    }
  }
  submitImport() {
    (<any>this.$refs.import).submit();

  }
  handleExceed(files, fileList) {
    //this.$message.warning(`The limit is 1, you selected ${files.length} files this time, add up to ${files.length + fileList.length} totally`);
    const message = "The limit is 1 file";
    this.$notify({
      title: 'Warning',
      message: message.toString(),
      type: 'warning',
      duration: 3000
    });
  }
  beforeImport(file) {
    const isCSV = file.type === 'application/vnd.ms-excel';
    if (!isCSV) {
      const message = "Document must be CSV format!";
      this.$notify({
        title: 'Error',
        message: message.toString(),
        type: 'error',
        duration: 3000
      });
    }

    return isCSV;
  }
  successImport(res, file, fileList){
    const message = "Import data success";
    this.$notify({
      title: 'Success',
      message: message.toString(),
      type: 'success',
      duration: 3000
    });
    this.clear();
  }
  errorImport(err, file, fileList){
    const message = "Import data error";
    this.$notify({
      title: 'Error',
      message: message.toString(),
      type: 'error',
      duration: 3000
    });
  }

}
