import { ADColumnType } from '@/shared/model/ad-column.model';
import { IADField } from '@/shared/model/ad-field.model';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { kebabCase } from 'lodash';
import pluralize from 'pluralize';
import { Component, Inject, Vue, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

const AdvanceSearchProps = Vue.extend({
  props: {
    tab: {
      type: Object,
      default: () => {
        return null;
      }
    },
    fields: {
      type: Array,
      default: () => {
        return [];
      }
    }
  }
});

@Component
export default class AdvanceSearch extends AdvanceSearchProps {
  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found',
    changeColumnOptionType: '',
    changeQueryValue: ''
  };
  isFetching = false;
  gridFields: Array<any> = [];
  
  gridData: Array<any> = [];
  row: any = {};
  currentRecord: any = {};
  tableDirectTimestamp: number = Date.now();
  
  // Multiple row selection.
  filterQuery = '';
  selectedRows: Array<any> = [];
  operatorMap: any = {};

  //get form after choose get column
  formInputText:boolean = false;
  formInputNumber:boolean = false;
  formInputCheckbox:boolean = false;
  formInputSwitch:boolean = false;
  formInputSelect:boolean = false;

  editing = false;
  validationSchema: any = {};

  private newRecord: boolean = false;
  private tableDirectReferenceMap = new Map();
  private activeTableDirectField: any = null;

  @Watch('fields')
  onFieldsChange(fields: Array<IADField>) {
    this.tableDirectReferenceMap.clear();
    
    this.gridFields = fields.filter(field => field.showInGrid);
    //console.log(this.gridFields);
    this.row = {};
    /*for (let field of this.gridFields) {
      const column = field.adColumn;
      console.log(field.name);
      console.log(column.name);
      console.log(column.type);

      //console.log(this.$set(this.row, column.name, ""));
      //this.row[column.name] = "";
      //this.$set(this.row, column.name, "");
    }*/
    //console.log(this.row);
    
    this.gridData = [{
      column: '', 
      query: '', 
      queryValue: ''
    }]
    
  }

  // Start of lifecycle events.
  created() {
    this.onFieldsChange(this.fields);
  }

  public addFilterAdvance(){
    this.gridData.push({ 
      column: '', 
      query: '', 
      queryValue: '' 
    });

  }

  public removeFilterAdvance(item, index) {
    this.gridData.splice(index, 1);
  }

  public handleSelectionChangeFilterAdvance(value: any) {
    this.selectedRows = value;
  }

  public filterAdvance() {
    if (this.selectedRows.length) {
      let query = '';
      
        for (let row of this.selectedRows) {
          if ((row.column !== "") && (row.query !== "")) {
            const filter = `${row.column.adColumn.name}.${row.query}=${row.queryValue}`;
            query += query.length ? `&${filter}` : filter;
          } else {
            this.$notify({
              title: 'Warning',
              message: "Please select filter row",
              type: 'warning',
              duration: 3000
            });
            return;
          }
        }

        this.$emit('submit', query);
      
    }else{
      this.$notify({
        title: 'Warning',
        message: "Please select filter row",
        type: 'warning',
        duration: 3000
      });
    }

  }

  public clear() {
    for (let field of this.fields) {
      const column = field.adColumn;
      //this.row[column.name] = "";
      this.$set(this.row, column.name, "");
    }
    this.$emit('clear');

    this.gridData = [{
      column: '', 
      query: '', 
      queryValue: ''
    }]
  }

  public close() {
    this.$emit('close');
  }

  public query = new Map([
    [
      'STRING',
      ['contains', 'doesNotContain', 'equals', 'notEquals']
    ],[
      'BOOLEAN',
      ['equals']
    ],[
      'ARRAY',
      ['in', 'sort']
    ],[
      'INTEGER',
      ['equals', 'notEquals', 'greaterThan', 'greaterThanOrEqual', 'lessThan', 'lessThanOrEqual']
    ],[
      'BIG_DECIMAL',
      ['equals', 'notEquals', 'greaterThan', 'greaterThanOrEqual', 'lessThan', 'lessThanOrEqual']
    ],[
      'DOUBLE',
      ['equals', 'notEquals', 'greaterThan', 'greaterThanOrEqual', 'lessThan', 'lessThanOrEqual']
    ],[
      'LONG',
      ['equals', 'notEquals', 'greaterThan', 'greaterThanOrEqual', 'lessThan', 'lessThanOrEqual']
    ],[
      'FLOAT',
      ['equals', 'notEquals', 'greaterThan', 'greaterThanOrEqual', 'lessThan', 'lessThanOrEqual']
    ],
  ])

  public booleanOption = [
    { value: '', label: '*' },
    { value: 'true', label: 'true' },
    { value: 'false', label: 'false' },
  ]

  get operators(){
    return (index)=>this.operatorMap[index];
  }

  columnOptionType(key: any, index: any) {
    const adCol = key.column.adColumn;
    const col = key.column;
    this.gridSchema.changeColumnOptionType = adCol.type;
    this.gridSchema.changeQueryValue = col.name;

    //this.gridData[index].query = '';
    //this.operatorMap[index] = this.query.get(adCol.type);
    this.$set(this.operatorMap, index, this.query.get(adCol.type));
  }

  getColumnValue(column) {
    const {id, name, type} = column;
    return {id, name, type};
  }

  getqueryValueByColumnBoolean(row: any) {
    //return this.gridSchema.changeColumnOptionType === "BOOLEAN";
    //console.log(row);
    return row.column && row.column.adColumn.type === "BOOLEAN";
  }

  




  public fetchTableDirectData(query: string) {
    const field = this.activeTableDirectField;
    //if (query.trim() !== '') {
      const column = field.adColumn;
      const resourceName = pluralize.plural(
        kebabCase(column.importedTable)
      );
      const api = `/api/${resourceName}`;
      this.dynamicWindowService(api)
        .retrieve({
          criteriaQuery: `name.contains=${query}`
        })
        .then(res => {
          this.tableDirectReferenceMap
            .set(`${this.currentRecord.id}-${field.adColumn.name}`, res.data);
          this.tableDirectTimestamp = Date.now();
        });
    //}
  }

  public isFixed(field: any): boolean | string {
    if (field.adColumn.name === 'active') {
      return 'right';
    }
    return false;
  }

  public isReadonly(row: any, field: any): boolean {
    const newRecord: boolean = !row.id;
    return !this.tab.writable || !field.writable || (!newRecord && !field.adColumn.updatable);
  }

  public getFieldWidth(field: any) {
    if (this.isBooleanField(field) && field.adColumn.name === 'active') {
      return '96';
    }

    return '';
  }

  public getMinValue(field: any) {
    return field.adColumn.minValue || -Infinity;
  }

  public getMaxValue(field: any) {
    return field.adColumn.maxValue || Infinity;
  }

  public getReferenceList(field: any) {
    return field?.adReference?.adreferenceLists || field.adColumn.adReference.adreferenceLists;
  }

  public hasReferenceList(field: any) {
    return field.adReference?.referenceType === ADReferenceType.LIST
      || field.adColumn.adReference?.referenceType === ADReferenceType.LIST;
  }

  public isTableDirectLink(field: any): boolean {
    const column = field.adColumn;
    const reference = field.adReference || column.adReference;
    return column.foreignKey && (reference?.referenceType === ADReferenceType.DATATYPE || reference?.value === 'direct' || reference?.value === 'table');
  }

  public isStringField(field: any) {
    return field.adColumn.type === ADColumnType.STRING;
  }

  public isNumericField(field: any) {
    return (
      field.adColumn.type === ADColumnType.BIG_DECIMAL ||
      field.adColumn.type === ADColumnType.DOUBLE ||
      field.adColumn.type === ADColumnType.FLOAT ||
      field.adColumn.type === ADColumnType.INTEGER ||
      field.adColumn.type === ADColumnType.LONG
    );
  }

  public isDateField(field: any) {
    return field.adColumn.type === ADColumnType.LOCAL_DATE || field.adColumn.type === ADColumnType.ZONED_DATE_TIME;
  }

  public isDateTimeField(field: any) {
    return field.adColumn.type === ADColumnType.INSTANT;
  }

  public isBooleanField(field: any) {
    return field.adColumn.type === ADColumnType.BOOLEAN;
  }

  public getFieldValue(row: any, field: any) {
    //console.log('row:%O,field:%O', row,field)
    if (this.isTableDirectLink(field)) {
      const propName = field.adColumn.name.replace(/Id$/, 'Name');
      return row[propName] || row[field.adColumn.name];
    }
    return row[field.adColumn.name];
  }

  public isActiveStatusField(column: any) {
    return column.property === 'active';
  }

  public setTableDirectReference(field: any): void {
    this.activeTableDirectField = field;
  }

  getTableDirectReferences(field: any): any[] {
    if (this.currentRecord === null || field == null) {
      return [];
    }
    const key = `${this.currentRecord.id}-${field.adColumn.name}`;
    return field && this.tableDirectTimestamp
      && this.tableDirectReferenceMap.get(key);
  }
}
