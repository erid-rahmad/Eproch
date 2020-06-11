import { Component, Vue, Watch, Inject } from 'vue-property-decorator'
import { IADField } from '@/shared/model/ad-field.model';
import { getValidatorType } from '@/utils/validate';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { ADColumnType } from '@/shared/model/ad-column.model';
import pluralize from 'pluralize';
import { kebabCase } from 'lodash';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

const BasicSearchProps = Vue.extend({
  props: {
    eventBus: {
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
export default class BasicSearch extends BasicSearchProps {
  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  listFields: Array<any> = [];
  row: any = {};
  rowQuery: any = {};
  rowQueryTemp: any = {};
  currentRecord: any = {};
  tableDirectTimestamp: number = Date.now();
  private newRecord: boolean = false;
  private tableDirectReferenceMap = new Map();
  private activeTableDirectField: any = null;

  @Watch('fields')
  onFieldsChange(fields: Array<IADField>) {
    this.tableDirectReferenceMap.clear();
    
    this.listFields = fields.filter(field => field.showInGrid);
    this.row = {};
  }
  // Start of lifecycle events.
  created() {
    this.onFieldsChange(this.fields);
  }




  public filterBasic(){
    this.rowQueryTemp = "";
    for (let field of this.fields) {
      const column = field.adColumn;
      
      let queryName = this.row[column.name];
      let queryNameParam = column.name;
      let queryTypeParam = column.type;
      
      if(queryName){
        if(queryTypeParam === "STRING"){
          this.rowQuery = `&${queryNameParam}.contains=${queryName}`;
        }else if((queryTypeParam === "INTEGER")||(queryTypeParam === "BIG_DECIMAL")||(queryTypeParam === "BOOLEAN")){
          this.rowQuery = `&${queryNameParam}.equals=${queryName}`;
        }
        this.rowQueryTemp += this.rowQuery;
      }
    }

    this.$emit('submit-basic-search', this.rowQueryTemp);
    this.eventBus.$emit('close-search-window');
  }

  public clear(){
    for (let field of this.fields) {
      const column = field.adColumn;
      this.row[column.name] = "";
    }
    this.$emit('clear-search', "");
    this.eventBus.$emit('close-search-window');
  }

  public close(){
    this.eventBus.$emit('close-search-window');
  }

  public booleanOption = [
    { value: '', label: '*' },
    { value: 'true', label: 'true' },
    { value: 'false', label: 'false' },
  ]





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

  public isDateTimeField(field: any) {
    return field.adColumn.type === ADColumnType.INSTANT;
  }

  public isBooleanField(field: any) {
    return field.adColumn.type === ADColumnType.BOOLEAN;
  }

  public isActiveStatusField(column: any) {
    return column.adColumn.name === 'active';
  }

  public getqueryValueByColumnBoolean(row: any) {
    return row && row.adColumn.type === "BOOLEAN";
  }
}
