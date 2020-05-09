import { mixins } from 'vue-class-component';
import { Select } from 'element-ui'
import { Component, Inject, Watch, Vue } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { formatJson } from '@/utils'
import { exportJson2Excel } from '@/utils/excel'
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
  public countries: ICountry[] = [];

  @Inject('currencyService') private currencyService: () => CurrencyService;
  public currencies: ICurrency[] = [];

  @Inject('regionService') private regionService: () => RegionService;
  public regions: IRegion[] = [];

  @Inject('cityService') private cityService: () => CityService;
  public cities: ICity[] = [];

  public showDeleteDialog: boolean = false;
  public showVisibleColumn: boolean = false;
  public showDownloadDoc: boolean = false;
  public showFilterRecord: boolean = false;
  public checkedActive: boolean = true;
  
  public showName: boolean = true;
  public showCode: boolean = true;
  public showCurrency: boolean = true;
  public showActive: boolean = true;

  public getId: number = null;
  public itemsPerPage = 10;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'name';
  public reverse = true;
  public totalItems = 0;

  public isFetching = false;
  //new
  public multipleSelection = []
  public multipleSelectionFilterAdvance = []
  public tempMultipleSelectionFilterAdvance = ''
  public tempMultipleSelectionFilterAdvanceColumn = ''
  public tempMultipleSelectionFilterAdvanceQuery = ''
  public tempMultipleSelectionFilterAdvanceQueryValue = ''
  public listLoading = true;
  public isSaving = false;

  //download file excel
  public downloadLoading = false;
  public filename = 'Country List'
  public autoWidth = true
  public bookType = 'csv'
  public tableKey = 0

  public listTypeBook = {
    id: undefined,
    name: undefined,
    type: undefined
  }
  public chooseBookType = [
    { id: '1', type: 'csv' },
    { id: '2', type: 'xls' },
    { id: '3', type: 'xlsx' },
  ]
  public tableVisibleColumn = [
    { 
      name: this.showName, 
      code: this.showCode, 
      currency: this.showCurrency, 
      active: this.showActive
    },
  ]

  //filter query
  public changeColumnOptionType = ''
  public filterQuery = ''
  public filterQueryCode = ''
  public filterQueryName = ''
  public filterQueryCurrency = ''
  public listQuery = {
    code: undefined,
    name: undefined,
    currencyId: undefined
  }

  //sample itemListQueryAdvance
  public itemListQueryAdvance= [
    { column: '', query: '', queryValue: ''},
  ]
  public addFilterAdvance(): void {
    this.itemListQueryAdvance.push({ column: '', query: '', queryValue: '' })
  }
  public removeFilterAdvance(item, index) {
    this.itemListQueryAdvance.splice(index, 1)
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

  /*private a
  get computeMethod(){
    this.a = this.changeColumnOptionType === this.query[0].type
    return this.a
  }*/

  public mounted(): void {
    this.retrieveAllCountries();
    CrudEventBus.$on('country-update-success', this.clear)
    this.initRelationships()
  }

  public clear(): void {
    this.page = 1;
    this.filterQuery = ''
    this.filterQueryCode = ''
    this.filterQueryName = ''
    this.filterQueryCurrency = ''
    this.tempMultipleSelectionFilterAdvance = ''
    this.listQuery.code = ''
    this.listQuery.name = ''
    this.listQuery.currencyId = ''
    this.retrieveAllCountries();
  }

  public handleClickTabSearch(tab, event) {
    //console.log(tab);
  }

  columnOptionType(key: any) {
    this.changeColumnOptionType = key.type
    console.log(key.type)
  }
  
  public handleSelectionChangeFilterAdvance(value: any) {
    this.multipleSelectionFilterAdvance = value
  }

  public executeFilterAdvance(): void{
    
    if(this.multipleSelectionFilterAdvance.length){
      this.tempMultipleSelectionFilterAdvance = ''
      for (let i = 0; i < this.multipleSelectionFilterAdvance.length; i++) {

        this.tempMultipleSelectionFilterAdvanceColumn = this.multipleSelectionFilterAdvance[i].column.code;
        this.tempMultipleSelectionFilterAdvanceQuery = this.multipleSelectionFilterAdvance[i].query.code;
        this.tempMultipleSelectionFilterAdvanceQueryValue = this.multipleSelectionFilterAdvance[i].queryValue;
        
        this.tempMultipleSelectionFilterAdvance += `&${this.tempMultipleSelectionFilterAdvanceColumn}.${this.tempMultipleSelectionFilterAdvanceQuery}=${this.tempMultipleSelectionFilterAdvanceQueryValue}`;
      }
      this.filterQuery = ''
      this.filterQuery = this.tempMultipleSelectionFilterAdvance
      this.retrieveAllCountries()

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
    //this.listQuery.page = 1
    //if((this.listQuery.search != undefined)&&(this.listQuery.option != '')&&(this.listQuery.option != undefined)){
      if(this.listQuery.code){
        this.filterQueryCode = `&code.contains=${this.listQuery.code}`
      }else{
        this.filterQueryCode = ''
      }
      if(this.listQuery.name){
        this.filterQueryName = `&name.contains=${this.listQuery.name}`
      }else{
        this.filterQueryName = ''
      }
      if(this.listQuery.currencyId){
        this.filterQueryCurrency = `&currencyId.equals=${this.listQuery.currencyId}`
      }else{
        this.filterQueryCurrency = ''
      }
      this.filterQuery = this.filterQueryCode + this.filterQueryName + this.filterQueryCurrency
      this.retrieveAllCountries()
    /*}else{
      this.$notify({
        title: 'Warning',
        message: "Please input filter",
        type: 'warning',
        duration: 3000
      });
    }*/
  }

  public retrieveAllCountries(): void {
    this.listLoading = true
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

      if(params == 'remove'){
        this.handleRemove(this.multipleSelection)

      }else if(params == 'duplicate'){

        if(this.multipleSelection.length == 1){
          //this.duplicate(this.multipleSelection[0])
          this.countries.push({ 
            name: this.multipleSelection[0].name, 
            code: this.multipleSelection[0].code, 
            currencyId: this.multipleSelection[0].currencyId,
          })
          console.log(`${this.multipleSelection[0].name} - oke, dapet value, tapi belum selesai`)

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

  public handleRemove(params: any) {
      const filterVal = ['id', 'code', 'name', 'currencyId', 'currencyCode']
      const data = formatJson(filterVal, params)

      this.getId = data;
      this.showDeleteDialog = true;
  }

  /*
  public handleDuplicate(params: any) {
    const filterVal = ['id', 'code', 'name', 'currencyId', 'currencyCode']
    const data = formatJson(filterVal, params)

    this.getId = data;
    console.log(this.getId[0])
  }
  */

  public removeCountry(): void {
    //console.log(this.multipleSelection.length)
    
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
          this.retrieveAllCountries();
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
          this.getId[i] = null;
          this.retrieveAllCountries();
          this.closeDialog();
        });
        //karena code country sudah digunakan oleh region & city
    }
    this.retrieveAllCountries();
  }

  public handleDownload() {
    //console.log(this.listTypeBook.code);
    this.bookType = this.listTypeBook.type;
    this.filename = this.listTypeBook.name;
    this.downloadLoading = true
    const tHeader = ['Id', 'Code', 'Name', 'Currency']
    const filterVal = ['id', 'code', 'name', 'currencyCode']
    const countries = this.countries
    const data = formatJson(filterVal, countries)
    exportJson2Excel(tHeader, data, this.filename !== '' ? this.filename : undefined, undefined, undefined, this.autoWidth, this.bookType)
    this.downloadLoading = false
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
    this.retrieveAllCountries();
  }

  public handleSelectionChange(value: any) {
    this.multipleSelection = value
  }

  public transition(): void {
    this.retrieveAllCountries();
  }
/*
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
    this.showDeleteDialog = false;
    this.showVisibleColumn = false;
    this.showDownloadDoc = false;
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

    console.log(`${row.id} - ${row.code} - ${row.name} - ${row.currencyId}`)

    if (row.id) {
      this.countryService()
        .update(row)
        .then(param => {
          this.isSaving = false;
          //this.$router.go(-1);
          const message = this.$t('opusWebApp.country.updated', { param: param.id });
          this.retrieveAllCountries();
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

  public checkActive(row: any) {
    console.log(row.id)
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

  public handleModalVisible(param: string) {
    if(param==='downloadDoc'){
      this.showDownloadDoc = true;
    }else if(param==='visibleColumn'){
      this.showVisibleColumn = true;
    }else if(param==='filterRecord'){
      this.showFilterRecord = true
    }
    
  }

}
