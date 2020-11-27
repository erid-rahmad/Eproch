import settings from '@/settings';
import { IRegisterTabParameter, WindowStoreModule as windowStore } from '@/shared/config/store/window-store';
import { ADColumnType } from '@/shared/model/ad-column.model';
import { IADField } from '@/shared/model/ad-field.model';
import { normalizeField } from '@/utils/form';
import { hasReferenceList, isActiveStatusField, isBooleanField, isDateField, isDateTimeField, isNumericField, isPasswordField, isStringField, isTableDirectLink } from '@/utils/validate';
import { ElForm } from 'element-ui/types/form';
import { cloneDeep, debounce, kebabCase } from 'lodash';
import pluralize from 'pluralize';
import Component from 'vue-class-component';
import { Mixins, Vue, Watch } from 'vue-property-decorator';
import { mapActions } from 'vuex';
import CalloutMixin from '../../mixins/CalloutMixin';
import AddressEditor from "../AddressEditor/address-editor.vue";
import ContextVariableAccessor from '../ContextVariableAccessor';

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
  components: {
    AddressEditor
  },
  methods: {
    isStringField: isStringField,
    isPasswordField: isPasswordField,
    isNumericField: isNumericField,
    isDateField: isDateField,
    isDateTimeField: isDateTimeField,
    isBooleanField: isBooleanField,
    isActivatorSwitch: isActiveStatusField,
    hasReferenceList: hasReferenceList,
    isTableDirectLink: isTableDirectLink,
    ...mapActions({
      registerTabState: 'windowStore/registerTab'
    })
  }
})
export default class DetailView extends Mixins(ContextVariableAccessor, CalloutMixin, DetailViewProps) {
  isLoading: boolean = false;
  model: any = {};

  // The displayed fields.
  private formFields: IADField[] = [];

  private referenceItemsUpdateTimestamp: number = Date.now();
  private referenceItemsMap = new Map();
  private activeTableDirectField: any = null;
  private referenceFilterQueries: Map<number, string> = new Map();
  private originalRecord = null;
  private editing = false;
  private debouncedInitRelationships;

  private validationSchema: any = {};
  private dynamicValidationSchema: any = {};
  rows: any[] = [];

  private registerTabState!: (options: IRegisterTabParameter) => Promise<void>;

  // TODO Consider the performance.
  get displayed() {
    return (field: IADField) =>
      this.evaluateDisplayLogic({
        defaultTabId: this.tabName,
        field
      });
  }

  get observableTabProperties() {
    const { name, adfields, validationSchema } = this.tab;
    return { name, adfields, validationSchema };
  }

  get referenceListItems() {
    return (field: IADField): any[] => {
      return this.referenceItemsUpdateTimestamp && this.referenceItemsMap.get(field.adColumn.name) || [];
    }
  }

  get datePickerType() {
    return (field: IADField): string => {
      const type = field.type || field.adColumn.type;
      return type === ADColumnType.LOCAL_DATE ? 'date' : 'datetime';
    }
  }

  get dateDisplayFormat() {
    return (field: IADField): string => {
      const type = field.type || field.adColumn.type;
      return type === ADColumnType.LOCAL_DATE ? settings.dateDisplayFormat : settings.dateTimeDisplayFormat;
    }
  }

  get dateValueFormat() {
    return (field: IADField): string => {
      const type = field.type || field.adColumn.type;
      return type === ADColumnType.LOCAL_DATE ? settings.dateValueFormat : settings.dateTimeValueFormat;
    }
  }

  @Watch('observableTabProperties')
  async onObservableTabPropertiesChange({name, adfields, validationSchema}) {
    if (! adfields) {
      return;
    }
    
    this.tabName = name;
    this.validationSchema = validationSchema;
    this.dynamicValidationSchema = cloneDeep(this.validationSchema);
    this.referenceItemsMap.clear();
    this.formFields = (<IADField[]>adfields)
      .filter((field) => field.showInDetail)
      .sort((prevItem, nextItem) => {
        const prevSequence = prevItem.detailSequence || 0;
        const nextSequence = nextItem.detailSequence || 0;
        return prevSequence - nextSequence;
      });

    const logicallyMandatoryFields = this.formFields
      .filter(field => !!field.mandatoryLogic || !!field.adColumn?.mandatoryLogic);

    await windowStore.addMandatoryFields({
      path: this.$route.fullPath,
      tabId: this.tabName,
      fields: Object.assign({}, ...logicallyMandatoryFields.map(field => ({[field.virtualColumnName || field.adColumn.name]: field})))
    });

    this.reloadValidationSchema();
    this.rows = this.buildLayout(this.formFields);
  }
  
  @Watch('record')
  onRecordChanged(record: any) {
    if (record) {
      this.model = record;
      this.originalRecord = cloneDeep(record);
      this.updateReferenceQueries(this.formFields);
      this.reloadValidationSchema();
      this.debouncedInitRelationships(record);
      if (this.editing) {
        this.exitEditMode();
      }
    }
  }

  private buildLayout(fields: IADField[]) {
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
        name: field.virtualColumnName || column?.name,
        span: field.columnSpan || 12,
        field
      });

      previousColumnNo = field.columnNo || 1;
    }

    return rows;
  }

  // Start of lifecycle events.
  created() {
    this.debouncedInitRelationships = debounce(this.initRelationships, 300);
    this.onObservableTabPropertiesChange(this.observableTabProperties);
  }

  public onInputChanged(field: IADField, value: any) {
    if (! this.editing) {
      this.editing = true;
      this.$emit('edit-mode-change', this.editing);
    }

    this.registerTabState({
      path: this.$route.fullPath,
      tabId: this.tabName,
      data: this.model
    })
    .then(() => {
      if (this.hasReferenceList(field) || this.isTableDirectLink(field)) {
        this.updateReferenceQueries(this.formFields);
        this.initRelationships(this.model);
      }

      this.reloadValidationSchema();
      this.executeCallout(field, (val, target, type) => {
        if (type === 'RESET') {
          this.$set(this.model, target, null);
        } else if (type === 'PROCESS') {
          this.$set(this.model, target, val);
        }
      })
    });
  }

  public showLabel(field: IADField) {
    return (!isBooleanField(field) && field.showLabel) ? field.name : '';
  }

  public exitEditMode() {
    this.editing = false;
  }

  private reloadValidationSchema() {
    const logicallyMandatoryFields = windowStore.logicallyMandatoryFields(this.$route.fullPath, this.tabName);
    
    for (let name in this.dynamicValidationSchema) {
      const field = logicallyMandatoryFields[name];

      if (field !== void 0 && !field.mandatory && !field.adColumn?.mandatory) {
        let vSchema = this.dynamicValidationSchema[name];

        const shouldMandatory = this.evaluateMandatoryLogic({
          defaultTabId: this.tabName,
          field
        });

        vSchema.required = shouldMandatory;
      }
    }
  }

  /**
   * Update the validation rule for a specific field. Triggered when the window
   * context is just updated.
   * @param field 
   */
  private updateReferenceQueries(fields: IADField[]) {
    fields
      .filter(field => this.hasReferenceList(field) || this.isTableDirectLink(field))
      .forEach(async field => {
        await this.updateReferenceQuery(field);
      });
  }

  private async updateReferenceQuery(field: IADField) {
    // Parse the validation rule which is used to filter the reference key records.
    const column = field.adColumn;
    const validationRule = field.adValidationRule || column?.adValidationRule;

    if (validationRule) {
      let referenceFilter = <string>this.getContext({
        defaultTabId: this.tabName,
        text: validationRule.query
      });
      
      if (referenceFilter?.includes('{select', 0)) {
        referenceFilter = await this.parseValidationQuery({
          defaultTabId: this.tabName,
          text: referenceFilter
        });
      }

      if (referenceFilter) {
        this.referenceFilterQueries.set(field.id, referenceFilter);
      }
    }
  }

  public setTableDirectReference(field: IADField): void {
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

    try {
      for (let field of this.formFields) {
        const column = field.adColumn;

        if (field.adReferenceId || column?.adReferenceId) {
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

      if (updated) {
        this.referenceItemsUpdateTimestamp = Date.now();
      }
    } catch (err) {
      console.log('Failed initializing field relationships.', err);
    } finally {
      this.isLoading = false;
    }
  }

  public beforeSave() {
    const {
      editing,
      createdBy,
      createdDate,
      lastModifiedBy,
      lastModifiedDate,
      ...record
    } = this.model;

    this.formFields.forEach(field => {
      normalizeField(record, field);
    });

    (<ElForm>this.$refs.mainForm).validate(valid => {
      if (valid)
        this.$emit('form-validated');
    });
  }

  public getMinLength(field: IADField) {
    return field.minLength || field.adColumn?.minLength || -Infinity;
  }

  public getMaxLength(field: IADField) {
    return field.maxLength || field.adColumn?.maxLength || Infinity;
  }

  public getMinValue(field: IADField) {
    return field.minValue || field.adColumn?.minValue || -Infinity;
  }

  public getMaxValue(field: IADField) {
    return field.maxValue || field.adColumn?.maxValue || Infinity;
  }

  public getReferenceList(field: IADField) {
    return field?.adReference?.adreferenceLists || field.adColumn?.adReference?.adreferenceLists;
  }

  public isReadonly(field: IADField): boolean {
    const newRecord: boolean = ! this.model?.id;
    const notUpdatable = ( ! newRecord && ! field.updatable && ! field.adColumn?.updatable);
    const conditionallyReadonly = this.evaluateReadonlyLogic({
      defaultTabId: this.tabName,
      field
    });
    
    return ! this.tab.writable || ! field.writable || notUpdatable || conditionallyReadonly;
  }

  public hasReferenceList!: (field: IADField) => boolean;
  public isTableDirectLink!: (field: IADField) => boolean;
  public isStringField!: (field: IADField) => boolean;
  public isPasswordField!: (field: IADField) => boolean;
  public isNumericField!: (field: IADField) => boolean;
  public isDateField!: (field: IADField) => boolean;
  public isDateTimeField!: (field: IADField) => boolean;
  public isBooleanField!: (field: IADField) => boolean;
  public isActivatorSwitch!: (field: IADField) => boolean;

  isAddressField(field: IADField) {
    if (field.virtualColumnName) {
      return false;
    }

    const reference = field.adReference || field.adColumn.adReference;
    return reference?.value === 'address';
  }

}