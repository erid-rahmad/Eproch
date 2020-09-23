import { nullifyField } from '@/utils/form';
import { hasReferenceList, isActiveStatusField, isBooleanField, isDateField, isDateTimeField, isNumericField, isStringField, isTableDirectLink } from '@/utils/validate';
import { ElForm } from 'element-ui/types/form';
import { kebabCase, debounce } from 'lodash';
import pluralize from 'pluralize';
import Component from 'vue-class-component';
import { Inject, Mixins, Vue, Watch } from 'vue-property-decorator';
import ContextVariableAccessor from '../ContextVariableAccessor';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { IADField } from '@/shared/model/ad-field.model';

const DetailViewProps = Vue.extend({
  props: {
    tab: {
      type: Object,
      default: () => {
        return null;
      }
    },
    record: {
      type: Object,
      default: () => {
        return {};
      }
    },
    active: Boolean,
    toolbarEventBus: {
      type: Object,
      default: () => {
        return null;
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
export default class DetailView extends Mixins(ContextVariableAccessor, DetailViewProps) {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  isLoading: boolean = false;
  model: any = {};

  // The displayed fields.
  private formFields: any[] = [];

  private referenceItemsUpdateTimestamp: number = Date.now();
  private referenceItemsMap = new Map();
  private activeTableDirectField: any = null;
  private referenceFilterQueries: Map<number, string> = new Map();
  private originalRecord = null;
  private editing = false;
  private debouncedInitRelationships;

  validationSchema: any = {};
  rows: any[] = [];

  get observableTabProperties() {
    const { adfields, validationSchema } = this.tab;
    return { adfields, validationSchema };
  }

  get referenceListItems() {
    return (field: any): any[] => {
      return this.referenceItemsUpdateTimestamp && this.referenceItemsMap.get(field.adColumn.name) || [];
    }
  }

  @Watch('observableTabProperties')
  onObservableTabPropertiesChange({adfields, validationSchema}) {
    if (! adfields) {
      return;
    }
    
    this.validationSchema = validationSchema;
    this.referenceItemsMap.clear();
    this.formFields = adfields
      .filter((field: IADField) => field.showInDetail)
      .sort((prevItem: IADField, nextItem: IADField) => {
        const prevSequence = prevItem.detailSequence || 0;
        const nextSequence = nextItem.detailSequence || 0;
        return prevSequence - nextSequence;
      });

    this.rows = this.buildLayout(this.formFields);
    this.debouncedInitRelationships(this.record);
  }
  
  @Watch('record')
  onRecordChanged(record: any) {
    if (record) {
      this.model = record;
      this.updateReferenceQueries(this.formFields);
      this.exitEditMode();
    }
  }

  private buildLayout(fields: any[]) {
    const rows = [];
    let columns: any[];
    let previousColumnNo = 1;
    
    for (let field of fields) {
      const columnNo = field.columnNo || 1;
      const column = field.adColumn;
      const newRow = !field.columnNo || columnNo <= previousColumnNo;

      if (newRow) {
        columns = [];
        rows.push({
          columns: columns
        });
      }

      columns.push({
        name: column.name,
        span: field.columnSpan || 12,
        field
      });

      previousColumnNo = field.columnNo || 1;
    }

    return rows;
  }

  // Start of lifecycle events.
  created() {
    this.debouncedInitRelationships = debounce(this.initRelationships, 500);
    this.onObservableTabPropertiesChange(this.observableTabProperties);
  }

  onFormChanged() {
    if (! this.editing) {
      this.originalRecord = {...this.record};
      this.editing = true;
      this.$emit('edit-mode-change', this.editing);
    }
  }

  public showLabel(field: any) {
    return (!isBooleanField(field) && field.showLabel) ? field.name : '';
  }

  public exitEditMode() {
    this.editing = false;
  }

  /**
   * Update the validation rule for a specific field. Triggered when the window
   * context is just updated.
   * @param field 
   */
  private updateReferenceQueries(fields: any[]) {
    fields
      .filter(field => this.hasReferenceList(field) || this.isTableDirectLink(field))
      .forEach(field => {
        // Parse the validation rule which is used to filter the reference key records.
        const column = field.adColumn;
        const validationRule = field.adValidationRule || column.adValidationRule;

        if (validationRule) {
          const referenceFilter = this.getContext(validationRule?.query);
          this.referenceFilterQueries.set(field.id, <string>referenceFilter);
        }
      });
  }

  public setTableDirectReference(field: any): void {
    this.activeTableDirectField = field;
  }

  /**
   * This method is invoked each time user typing in el-select component
   * with remote attribute enabled.
   * @param query 
   */
  public fetchTableDirectData(query: string) {
    const field = this.activeTableDirectField;
    const column = field.adColumn;
    const filterQuery = this.referenceFilterQueries.get(field.id);
    const resourceName = pluralize.plural(
      kebabCase(column.importedTable)
    );
    const api = `/api/${resourceName}`;
    const criteriaQuery: string[] = query ? [`name.contains=${query}`] : [];

    // Append additional query from the cached validation rule query, if any.
    if (filterQuery) {
      criteriaQuery.push(filterQuery);
    }

    this.dynamicWindowService(api)
      .retrieve({ criteriaQuery })
      .then(res => {
        this.referenceItemsMap
          .set(field.adColumn.name, res.data);
        this.referenceItemsUpdateTimestamp = Date.now();
      });
  }

  private async initRelationships(row: any) {
    let updated = false;
    this.isLoading = true;

    for (let field of this.formFields) {
      const column = field.adColumn;

      if (field.adReferenceId || column.adReferenceId) {
        const filterQuery = this.referenceFilterQueries.get(field.id);

        // Get the item list of Reference Key[LIST].
        if (this.hasReferenceList(field) && this.referenceItemsMap.get(column.name) === void 0) {
          const referenceId = field.adReferenceId || field.adColumn.adReferenceId;
          const res = await this.dynamicWindowService('/api/ad-reference-lists').retrieve({
            criteriaQuery: [
              `adReferenceId.equals=${referenceId}`,
              filterQuery
            ]
          });
          this.referenceItemsMap.set(column.name, res.data);
          updated = true;
        }

        // Get the item list of Reference Key[DATATYPE|table|direct].
        else if (this.isTableDirectLink(field)) {
          const resourceName = pluralize.plural(kebabCase(column.importedTable));
          const api = `/api/${resourceName}`;
          const linkedFieldValue = row[column.name];
          const criteriaQuery: string[] = linkedFieldValue ? [`id.equals=${linkedFieldValue}`] : [];

          // Append additional query from the cached validation rule query, if any.
          if (filterQuery) {
            criteriaQuery.push(filterQuery);
          }

          const res = await this.dynamicWindowService(api).retrieve({ criteriaQuery });
          this.referenceItemsMap.set(column.name, res.data);
          updated = true;
        }
      }
    }

    this.isLoading = false;
    if (updated)
      this.referenceItemsUpdateTimestamp = Date.now();
  }

  public beforeSave() {
    const {
      editing,
      createdBy,
      createdDate,
      lastModifiedBy,
      lastModifiedDate,
      ...record
    } = this.record;

    this.formFields.forEach(field => {
      nullifyField(record, field);
    });

    (<ElForm>this.$refs.mainForm).validate(valid => {
      if (valid)
        this.$emit('form-validated');
    });
  }

  public getMinLength(field: any) {
    return field.adColumn.minLength || -Infinity;
  }

  public getMaxLength(field: any) {
    return field.adColumn.maxLength || Infinity;
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

  public isReadonly(field: any): boolean {
    const newRecord: boolean = !this.record?.id;
    return !this.tab.writable || !field.writable || (!newRecord && !field.adColumn.updatable);
  }

  public isStringField!: (field: any) => boolean;
  public isNumericField!: (field: any) => boolean;
  public isDateField!: (field: any) => boolean;
  public isDateTimeField!: (field: any) => boolean;
  public isBooleanField!: (field: any) => boolean;
  public isActivatorSwitch!: (field: any) => boolean;
  public hasReferenceList!: (field: any) => boolean;
  public isTableDirectLink!: (field: any) => boolean;

}