import { Component, Vue, Watch, Inject } from 'vue-property-decorator'
import { IADField } from '@/shared/model/ad-field.model';
import { getValidatorType } from '@/utils/validate';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { ADColumnType } from '@/shared/model/ad-column.model';
import pluralize from 'pluralize';
import { kebabCase } from 'lodash';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { hasReferenceList, isActiveStatusField, isBooleanField, isDateField, isDateTimeField, isNumericField, isStringField, isTableDirectLink } from '@/utils/validate';

const BasicSearchProps = Vue.extend({
  props: {
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
export default class BasicSearch extends BasicSearchProps {
  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  listFields: Array<any> = [];
  row: any = {};
  currentRecord: any = {};
  tableDirectTimestamp: number = Date.now();
  private newRecord: boolean = false;
  private tableDirectReferenceMap = new Map();
  private activeTableDirectField: any = null;

  get fieldName() {
    return (field: IADField) => field.virtualColumnName || field.adColumn.name;
  }

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

  public filterBasic() {
    let rowQueryTemp = "";
    for (let field of this.fields) {
      const column = field.adColumn;
      const fieldName = field.virtualColumnName || column?.name;
      const queryName = this.row[fieldName];

      if (!queryName)
        continue;

      const fieldType = field.type || column?.type;
      let rowQuery: string;

      if (fieldType === ADColumnType.STRING) {
        rowQuery = `${fieldName}.contains=${queryName}`;
      } else if (fieldType === ADColumnType.INTEGER || fieldType === ADColumnType.LONG || fieldType === ADColumnType.BIG_DECIMAL || fieldType === ADColumnType.BOOLEAN
        || fieldType === ADColumnType.LOCAL_DATE || fieldType === ADColumnType.ZONED_DATE_TIME) {
        rowQuery = `${fieldName}.equals=${queryName}`;
      }

      if (rowQuery)
        rowQueryTemp += rowQueryTemp.length ? `&${rowQuery}` : rowQuery;
    }

    this.$emit('submit', rowQueryTemp);
  }

  public clear() {
    for (let field of this.fields) {
      const fieldName = field.virtualColumnName || field.adColumn;
      if (this.row[fieldName] !== void 0)
        this.$set(this.row, fieldName, '');
    }
    this.$emit('clear');
  }

  public close() {
    this.$emit('close');
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

  public getMinValue(field: IADField) {
    return field.minValue || field.adColumn?.minValue || -Infinity;
  }

  public getMaxValue(field: IADField) {
    return field.maxValue || field.adColumn?.maxValue || Infinity;
  }

  public getReferenceList(field: any) {
    return field.adReference?.adreferenceLists || field.adColumn?.adReference?.adreferenceLists;
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
