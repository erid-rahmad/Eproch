import { ADColumnType } from '@/shared/model/ad-column.model';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { hasReferenceList, isActiveStatusField, isBooleanField, isDateField, isDateTimeField, isNumericField, isStringField, isTableDirectLink } from '@/utils/validate';
import { Component, Mixins, Vue } from 'vue-property-decorator';
import ContextVariableAccessor from '../ContextVariableAccessor';

const TriggerParameterFormProps = Vue.extend({
  props: {
    currentRecord: {
      type: Object,
      default: () => {
        return {};
      }
    },
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },
    fields: {
      type: Array,
      default: () => {
        return [];
      }
    },
    tabName: String
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
export default class TriggerParameterForm extends Mixins(ContextVariableAccessor, TriggerParameterFormProps) {

  private visible: boolean = false;
  private activeTableDirectField: any = null;
  private referenceItemsMap = new Map();
  private referenceFilterQueries: Map<number, string> = new Map();
  private referenceItemsUpdateTimestamp: number = Date.now();
  private parameter: any = {};
  processing: boolean = false;

  onOpen() {
    this.data.adTriggerParams.forEach((param: any) => {
      let defaultValue: any = this.getContext({
        defaultTabId: this.tabName,
        text: param.defaultValue,
        record: this.currentRecord
      });

      if ( ! defaultValue) {
        if (this.isDateField(param.type) || this.isDateTimeField(param.type)) {
          defaultValue = new Date().toISOString();
        } else if (this.isBooleanField(param.type)) {
          defaultValue = false;
        } else if (this.isNumericField(param.type)) {
          defaultValue = null;
        } else if (this.isStringField(param.type)) {
          defaultValue = '';
        }
      }
      this.$set(this.parameter, param.value, defaultValue);
    });
  }

  onClose() {
    this.parameter = {};
    this.visible = false;
  }

  runTask() {
    this.processing = true;
    this.dynamicWindowService(`/api/ad-triggers/process/${this.data.value}`)
      .create(this.parameter)
      .then(res => {
        console.log('Process results:', res);
        this.$message.success('Process has been successfully executed');
      })
      .catch(err => {
        this.$message.error(err);
      })
      .finally(() => {
        this.processing = false;
        this.visible = false;
      })
  }

  get referenceListItems() {
    return (field: any): any[] => {
      return this.referenceItemsUpdateTimestamp && this.referenceItemsMap.get(field.id) || [];
    }
  }

  get displayed() {
    return this.visible;
  }

  set displayed(visible) {
    this.visible = visible;
  }

  created() {
    console.log('TriggerParameterForm created');
  }

  beforeDestroy() {
    console.log('TriggerParameterForm before destroy');
  }

  /**
   * Update the validation rule for a specific field. Triggered when the window
   * context is just updated.
   * @param field 
   */
  /* private updateReferenceQueries(field: any) {
    if (this.hasReferenceList(field) || this.isTableDirectLink(field)) {
      // Parse the validation rule which is used to filter the reference key records.
      const column = field.adColumn;
      const validationRule = field.adValidationRule || column.adValidationRule;
      const referenceFilter = this.getContext(validationRule?.query);
      this.referenceFilterQueries.set(field.id, referenceFilter);
    }
  }

  private async initRelationships(row: any) {
    let updated = false;

    for (let field of this.data.adTriggerParams) {
      if (field.adReferenceId) {
        this.updateReferenceQueries(field);
        const filterQuery = this.referenceFilterQueries.get(field.id);

        // Get the item list of Reference Key[LIST].
        if (this.hasReferenceList(field) && this.referenceItemsMap.get(field.id) === void 0) {
          const referenceId = field.adReferenceId;
          const res = await this.dynamicWindowService('/api/ad-reference-lists').retrieve({
            criteriaQuery: [
              `adReferenceId.equals=${referenceId}`,
              filterQuery
            ]
          });
          this.referenceItemsMap.set(field.id, res.data);
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
          this.referenceItemsMap.set(field.id, res.data);
          updated = true;
        }
      }
    }

    if (updated)
      this.referenceItemsUpdateTimestamp = Date.now();
  } */

  public setTableDirectReference(field: any): void {
    this.activeTableDirectField = field;
  }

  public getMinLength(field: any) {
    return field.minLength || -Infinity;
  }

  public getMaxLength(field: any) {
    return field.maxLength || Infinity;
  }

  public getMinValue(field: any) {
    return field.minValue || -Infinity;
  }

  public getMaxValue(field: any) {
    return field.maxValue || Infinity;
  }

  public isTableDirectLink(field: any): boolean {
    const reference = field.adReference;
    return reference?.value === 'direct' || reference?.value === 'table';
  }

  public hasReferenceList(field: any) {
    return field.adReference?.referenceType === ADReferenceType.LIST;
  }

  public isStringField(type: any) {
    return type === ADColumnType.STRING;
  }

  public isNumericField(type: any) {
    return (
      type === ADColumnType.BIG_DECIMAL ||
      type === ADColumnType.DOUBLE ||
      type === ADColumnType.FLOAT ||
      type === ADColumnType.INTEGER ||
      type === ADColumnType.LONG
    );
  }

  public isDateField(type: any) {
    return type === ADColumnType.LOCAL_DATE || type === ADColumnType.ZONED_DATE_TIME;
  }

  public isDateTimeField(type: any) {
    return type === ADColumnType.INSTANT;
  }

  public isBooleanField(type: any) {
    return type === ADColumnType.BOOLEAN;
  }
}