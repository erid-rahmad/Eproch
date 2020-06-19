import Component from 'vue-class-component';
import { Vue, Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import ContextVariableAccessor from '../ContextVariableAccessor';
import { getValidatorType, isStringField, isNumericField, isDateField, isDateTimeField, isBooleanField, isActiveStatusField, hasReferenceList, isTableDirectLink } from '@/utils/validate';
import { ADColumnType } from '@/shared/model/ad-column.model';
import { debounce, kebabCase, isEmpty, isEqual } from 'lodash';
import pluralize from 'pluralize';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { ElForm } from 'element-ui/types/form';
import { mapActions } from 'vuex';
import { RegisterTabParameter } from '@/shared/config/store/window-store';

const DetailViewProps = Vue.extend({
  props: {
    tab: {
      type: Object,
      default: () => {
        return null;
      }
    },
    active: Boolean,
    page: {
      type: Number,
      default: 1
    },
    toolbarEventBus: {
      type: Object,
      default: () => {
        return null;
      }
    },
    searchPanelEventBus: {
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
    isTableDirectLink: isTableDirectLink,
    ...mapActions({
      registerTabState: 'windowStore/registerTab'
    })
  }
})
export default class DetailView extends Mixins(ContextVariableAccessor, DetailViewProps) {

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  private isLoading: boolean = false;
  private queryCount: number = null;
  private propOrder = 'id';
  private reverse = false;
  private totalItems = 0;
  private tabName: string = '';
  private fields: any[] = [];

  // The displayed fields.
  private formFields: any[] = [];

  private baseApiUrl: string = '';
  private filterQuery: string = '';
  private filterQueryTmp: string;
  private parentId: number = 0;

  private referenceItemsUpdateTimestamp: number = Date.now();
  private referenceItemsMap = new Map();
  private activeTableDirectField: any = null;
  private referenceFilterQueries: Map<number, string> = new Map();
  
  private originalData: {} = null;
  private model: any = {};
  private validationSchema: any = {};
  private rows: any[] = [];
  private modelLocked: boolean = false;
  private showDeleteDialog: boolean = false;

  private debouncedCheckEditMode: (record: object, prevRecord: object) => void;
  private debouncedUpdatePage: () => void;

  get isEditing() {
    return !this.modelLocked && this.originalData !== null;
  }

  get observableTabProperties() {
    const { name, adfields, targetEndpoint, filterQuery, parentId, promoted } = this.tab;
    return { name, adfields, targetEndpoint, filterQuery, parentId, promoted };
  }

  get referenceListItems() {
    return (field: any): any[] => {
      return this.referenceItemsUpdateTimestamp && this.referenceItemsMap.get(field.adColumn.name) || [];
    }
  }

  private get sort(): any[] {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  @Watch('model', { deep: true })
  onDataChange(record: object, prevRecord: object) {
    this.debouncedCheckEditMode(record, prevRecord);
  }

  @Watch('observableTabProperties')
  onObservableTabPropertiesChange(tab: any) {
    this.tabName = tab.name;
    this.fields = tab.adfields;
    this.baseApiUrl = tab.targetEndpoint;
    this.filterQuery = tab.filterQuery;
    this.parentId = tab.parentId || 0;

    this.retrieveAllRecords(this.sendCurrentPageChangeEvent);
  }

  @Watch('page')
  onPageChange(page: number) {
    this.debouncedUpdatePage();
  }

  @Watch('fields')
  onFieldsChange(fields: any[]) {
    this.referenceItemsMap.clear();
    this.formFields = fields
      .filter(field => !this.isActivatorSwitch(field) && field.showInDetail)
      .sort((a, b) => {
        if (a.detailSequence !== null)
          return b.detailSequence !== null ? a.detailSequence - b.detailSequence : -1;

        return b.detailSequence !== null ? 1 : -1;
      });

    let rows = [];
    let row: any;
    let columns: any[];
    let columnNo: number;
    let previousColumnNo = 1;
    
    for (let field of this.formFields) {
      columnNo = field.columnNo || 1;
      const column = field.adColumn;
      const newRow = !field.columnNo || columnNo <= previousColumnNo;

      if (field.showInDetail) {
        if (newRow) {
          columns = [];
          row = {
            columns: columns
          };
          rows.push(row);
        }
        columns.push({
          name: column.name,
          span: field.columnSpan || 12,
          field
        });
        previousColumnNo = field.columnNo || 1;
      }
    };

    // Prepare the form validation schema.
    let validationSchema = {};

    for (let field of this.fields) {
      const column = field.adColumn;
      
      validationSchema[column.name] = {
        type: getValidatorType(column.type),
        required: column.mandatory
      }

      if (column.formatPattern) {
        validationSchema[column.name].pattern = column.formatPattern;
      }

      if (column.minLength) {
        validationSchema[column.name].min = column.minLength;
      }

      if (column.maxLength) {
        validationSchema[column.name].max = column.maxLength;
      }

      if (column.minValue) {
        validationSchema[column.name].min = column.minValue;
      }

      if (column.maxValue) {
        validationSchema[column.name].max = column.maxValue;
      }
    }

    this.rows = rows;
    this.validationSchema = validationSchema;
  }

  // Start of lifecycle events.
  created() {
    this.searchPanelEventBus.$on('filter-updated', this.filterRecord);
    this.toolbarEventBus.$on('add-record', this.newRecord);
    this.toolbarEventBus.$on('copy-record', this.copyRecord);
    this.toolbarEventBus.$on('save-record', this.beforeSave);
    this.toolbarEventBus.$on('cancel-operation', this.cancelOperation);
    this.toolbarEventBus.$on('delete-record', this.deleteRecord);
    this.toolbarEventBus.$on('refresh-data', this.refreshData);
    this.debouncedUpdatePage = debounce(this.updatePage, 500);
    this.debouncedCheckEditMode = debounce(this.checkEditMode, 500);
    this.onObservableTabPropertiesChange(this.observableTabProperties);
  }

  beforeDestroyed() {
    this.searchPanelEventBus.$off('filter-updated', this.filterRecord);
    this.toolbarEventBus.$off('add-record', this.newRecord);
    this.toolbarEventBus.$off('copy-record', this.copyRecord);
    this.toolbarEventBus.$off('save-record', this.beforeSave);
    this.toolbarEventBus.$off('cancel-operation', this.cancelOperation);
    this.toolbarEventBus.$off('delete-record', this.deleteRecord);
    this.toolbarEventBus.$off('refresh-data', this.refreshData);
  }

  private reset() {
    this.originalData = null;
    this.retrieveAllRecords(this.sendCurrentPageChangeEvent);
    this.toolbarEventBus.$emit('inline-editing', false);
  }

  private filterRecord(query: string) {
    if (!query) {
      this.filterQuery = this.filterQueryTmp || this.filterQuery;
      this.filterQueryTmp = null;
    } else {
      if (this.filterQueryTmp === null) {
        this.filterQueryTmp = this.filterQuery;
      }
      this.filterQuery = `${this.filterQueryTmp}&${query}`;
    }
  }

  private newRecord(data: any) {
    if (data.isGridView) return;
    
    const form = (<ElForm>this.$refs.mainForm);
    
    if (!isEmpty(this.model)) {
      this.originalData = {...this.model};
    }

    let record = { id: 0 };
    if (this.parentId) {
      record[this.tab.foreignColumnName] = this.parentId;
    }
    this.prepareForm(record);
    this.modelLocked = false;
    this.model = record;
    this.initRelationships(this.model);
    this.$nextTick(() => {
      form.clearValidate();
    });
  }

  private copyRecord(data: any) {
    if (data.isGridView) return;
    this.originalData = {...this.model};
    this.modelLocked = false;
    this.model.id = 0;
  }

  private beforeSave(data: any) {
    if (!data.isGridView)
      this.save(this.reset);
  }
  
  private cancelOperation() {
    if (this.originalData) {
      this.modelLocked = true;
      this.model = {...this.originalData};
      this.originalData = null;
    }
  }

  private deleteRecord(data: any) {
    if (data.isGridView) return;
    this.showDeleteDialog = true;
  }

  public closeDeleteDialog(): void {
    this.showDeleteDialog = false;
  }

  public doDelete() {
    this.dynamicWindowService(this.baseApiUrl)
      .delete(this.model.id)
      .then(() => {
        const message = this.$t(`opusWebApp.applicationDictionary.deleted`, {
          tabName: this.tabName,
          param: this.model.id
        });
  
        this.retrieveAllRecords(this.sendCurrentPageChangeEvent);
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        });
      })
      .finally(() => {
        this.closeDeleteDialog();
      })
  }

  private refreshData(data: any) {
    if (!data.isGridView)
      this.retrieveAllRecords(this.sendCurrentPageChangeEvent);
  }

  private checkEditMode(record: object, prevRecord: object): void {
    if (this.isEditing) {
      if (isEqual(this.originalData, record)) {
        // this.originalData = null;
        this.toolbarEventBus.$emit('inline-editing', false);
      } else {
        this.toolbarEventBus.$emit('inline-editing', true);
      }
    } else if (this.modelLocked) {
      this.modelLocked = false;
    }
  }

  private updatePage(): void {
    if (this.originalData) {
      if (!isEqual(this.originalData, this.model)) {
        this.save(() => {
          this.originalData = null;
          this.retrieveAllRecords(this.sendCurrentPageChangeEvent);
        });
        return;
      } else {
        this.originalData = null;
      }
    }

    this.retrieveAllRecords(this.sendCurrentPageChangeEvent);
  }

  /**
   * Send an event to trigger child tab reload.
   * @param record The active page record
   */
  private sendCurrentPageChangeEvent(record: any) {
    this.$emit('current-page-change', {
      data: record,
      recordNo: this.page
    });
  }

  private prepareForm(row: any, newRecord: boolean = true) {
    for (const field of this.fields) {
      const column = field.adColumn;

      if (newRecord) {
        // TODO Set the default value based on the field/column definition. 
        let defaultValue = this.getContext(field.defaultValue || column.defaultValue);

        if (!defaultValue) {
          if (column.name === 'active') {
            defaultValue = true;
          } else if (this.isDateField(field) || this.isDateTimeField(field)) {
            defaultValue = new Date().toISOString();
          } else if (this.isBooleanField(field)) {
            defaultValue = false;
          } else if (this.isNumericField(field)) {
            defaultValue = null;
          } else if (this.isStringField(field)) {
            defaultValue = '';
          }
        }
        row[column.name] = defaultValue;
      }
    }
  }

  /**
   * Update the validation rule for a specific field. Triggered when the window
   * context is just updated.
   * @param field 
   */
  private updateReferenceQueries(field: any) {
    if (this.hasReferenceList(field) || this.isTableDirectLink(field)) {
      // Parse the validation rule which is used to filter the reference key records.
      const column = field.adColumn;
      const validationRule = field.adValidationRule || column.adValidationRule;
      const referenceFilter = this.parseContextVariable(validationRule?.query);
      this.referenceFilterQueries.set(field.id, referenceFilter);
    }
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

    await this.registerTabState({
      path: this.$route.fullPath,
      tabId: this.tabName,
      data: this.model
    });

    for (let field of this.formFields) {
      const column = field.adColumn;

      if (field.adReferenceId || column.adReferenceId) {
        this.updateReferenceQueries(field);
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

    if (updated)
      this.referenceItemsUpdateTimestamp = Date.now();
  }

  private retrieveAllRecords(callback: (record: any) => void): void {
    this.isLoading = true;
    const paginationQuery = {
      page: this.page - 1,
      size: 1,
      sort: this.sort
    };

    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: this.filterQuery,
        paginationQuery
      })
      .then(res => {
        // TODO Create new record with empty model if data is empty.
        if (res.data.length) {
          this.modelLocked = true;
          this.model = res.data[0];
          this.originalData = {...this.model};
          this.initRelationships(this.model);
        } else {
          this.newRecord({ isGridView: false });
        }
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        this.$emit('total-count-changed', this.queryCount);
        callback && callback(this.model);
      })
      .catch((err) => {
        const message = `Failed getting the record. ${err}`;
        this.$notify({
          title: 'Error',
          message: message.toString(),
          type: 'error',
          duration: 3000
        });
      })
      .finally(() => {
        this.isLoading = false;
      });
  }

  private save(callback?: (record: any) => void) {
    const {
      editing,
      createdBy,
      createdDate,
      lastModifiedBy,
      lastModifiedDate,
      ...record
    } = this.model;

    (<ElForm>this.$refs.mainForm).validate(valid => {
      if (valid)
        this.saveRecord(record, callback);
    });
  }

  private saveRecord(record: any, callback?: (record: any) => void) {
    const update: boolean = record.id > 0;

    if (!update) {
      delete record.id;
    }

    this.isLoading = true;
    const service = this.dynamicWindowService(this.baseApiUrl);
    const saveState = update ? service.update(record) : service.create(record);
    saveState.then(data => {
      callback && callback(data);
      !callback && this.retrieveAllRecords(this.sendCurrentPageChangeEvent);
      const message = this.$t(`opusWebApp.applicationDictionary.${update ? 'updated' : 'created'}`, {
        tabName: this.tabName,
        param: data.id
      });
      this.toolbarEventBus.$emit('record-saved');
      this.$notify({
        title: 'Success',
        message: message.toString(),
        type: 'success',
        duration: 3000
      });
    })
    .catch((err) => {
      const message = `Error saving the record`;
      this.$notify({
        title: 'Error',
        message: message.toString(),
        type: 'error',
        duration: 3000
      });
    })
    .finally(() => {
      this.isLoading = false;
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
    const newRecord: boolean = !this.model.id;
    return !this.tab.writable || !field.writable || (!newRecord && !field.adColumn.updatable);
  }

  public registerTabState!: (options: RegisterTabParameter) => Promise<void>
  public isStringField!: (field: any) => boolean;
  public isNumericField!: (field: any) => boolean;
  public isDateField!: (field: any) => boolean;
  public isDateTimeField!: (field: any) => boolean;
  public isBooleanField!: (field: any) => boolean;
  public isActivatorSwitch!: (field: any) => boolean;
  public hasReferenceList!: (field: any) => boolean;
  public isTableDirectLink!: (field: any) => boolean;

}