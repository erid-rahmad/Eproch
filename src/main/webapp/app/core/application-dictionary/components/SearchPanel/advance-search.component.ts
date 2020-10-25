import { IADField } from '@/shared/model/ad-field.model';
import { hasReferenceList, isActiveStatusField, isBooleanField, isDateField, isDateTimeField, isNumericField, isStringField, isTableDirectLink } from '@/utils/validate';
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

@Component({
  methods: {
    isStringField: isStringField,
    isNumericField: isNumericField,
    isDateField: isDateField,
    isDateTimeField: isDateTimeField,
    isBooleanField: isBooleanField,
    isActivatorSwitch: isActiveStatusField,
    hasReferenceList: hasReferenceList,
    isTableDirectLink: isTableDirectLink
  }
})
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
  private activeTableDirectField: any = null;

  get fieldName() {
    return (field: IADField) => field.virtualColumnName || field.adColumn.name;
  }

  get fieldType() {
    return (field: IADField) => field.type || field.adColumn.type;
  }

  @Watch('fields')
  onFieldsChange(fields: Array<IADField>) {
    this.gridFields = fields.filter(field => field.showInGrid);
    this.row = {};
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

  public removeFilterAdvance(index: number) {
    this.gridData.splice(index, 1);
  }

  public handleSelectionChangeFilterAdvance(value: any) {
    this.selectedRows = value;
  }

  public filterAdvance() {
    if (this.selectedRows.length) {
      let query = '';
      
        for (let row of this.selectedRows) {
          if (row.column && row.query) {
            const field = row.column as IADField;
            const fieldName = field.virtualColumnName || field.adColumn.name;
            const filter = `${fieldName}.${row.query}=${row.queryValue}`;
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
      const fieldName = field.virtualColumnName || field.adColumn.name;
      if (this.row[fieldName] !== void 0)
        this.$set(this.row, fieldName, '');
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

  columnOptionType(row: any, index: number) {
    const field = row.column as IADField;
    const fieldType = field.type || field.adColumn.type;

    this.gridSchema.changeColumnOptionType = fieldType;
    this.gridSchema.changeQueryValue = field.name;
    this.$set(this.operatorMap, index, this.query.get(fieldType));
  }

  getColumnValue(column) {
    const {id, name, type} = column;
    return {id, name, type};
  }

  public hasReferenceList!: (field: IADField) => boolean;
  public isTableDirectLink!: (field: IADField) => boolean;
  public isStringField!: (field: IADField) => boolean;
  public isNumericField!: (field: IADField) => boolean;
  public isDateField!: (field: IADField) => boolean;
  public isDateTimeField!: (field: IADField) => boolean;
  public isBooleanField!: (field: IADField) => boolean;
  public isActivatorSwitch!: (field: IADField) => boolean;

}
