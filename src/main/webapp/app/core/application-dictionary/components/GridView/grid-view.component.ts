import { Component, Vue, Watch, Inject, Mixins } from 'vue-property-decorator';
import ContextVariableAccessor from "../ContextVariableAccessor";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';
import { ADColumnType } from '@/shared/model/ad-column.model';
import schema from 'async-validator';
import { getValidatorType } from '@/utils/validate';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { kebabCase } from "lodash";
import pluralize from "pluralize";
import _ from 'lodash';
import { mapActions } from 'vuex';
import { RegisterTabParameter } from '@/shared/config/store/window-store';

const GridViewProps = Vue.extend({
  props: {
    mainTab: {
      type: Boolean,
      default: false
    },
    tab: {
      type: Object,
      default: () => {
        return null;
      }
    },

    /**
     * The identifier of a child tab.
     */
    tabId: String,

    /**
     * Record will be loaded once the parentId changed.
     */
    lazyLoad: Boolean,
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
    ...mapActions({
      registerTabState: 'windowStore/registerTab'
    })
  }
})
export default class GridView extends Mixins(ContextVariableAccessor, GridViewProps) {
  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  height: string | number = 'auto';
  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found'
  };
  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;
  isFetching = false;
  gridData: Array<any> = [];
  gridFields: Array<any> = [];
  isSaving: boolean = false;

  // Multiple row selection.
  selectedRows: any = [];
  multipleSelectedRowCheckbox: any = [];
  multipleSelectedRow: Array<any> = [];
  filterVal: any = [];

  //Dialog
  showDeleteDialog: boolean = false;

  // Inline editing mode needs these data.
  originalRecord: any = {};
  currentRecord: any = {};
  validationSchema: any = {};
  editing = false;
  tableDirectTimestamp: number = Date.now();
  private newRecord: boolean = false;
  private referenceItemsMap = new Map();
  private activeTableDirectField: any = null;
  private referenceFilterQueries: Map<number, string> = new Map();

  // Error messages.
  private errors = new Map();
  private errorTimestamp = Date.now();

  private tabName: string = '';
  private fields: any[] = [];
  private baseApiUrl: string = '';
  private filterQuery: string = '';
  private parentId: number = 0;
  private getId: any = null;

  private registerTabState!: (options: RegisterTabParameter) => Promise<void>

  get referenceListItems() {
    return (field: any): any[] => {
      if (this.currentRecord === null || field == null) {
        return [];
      }
      return this.referenceItemsMap.get(field.adColumn.name);
    }
  }

  // TODO Find another alternatives than using tableDirectTimestamp.
  get tableDirectReferences() {
    return (field: any): any[] => {
      if (this.currentRecord === null || field == null) {
        return [];
      }
      const key = `${this.currentRecord.id}-${field.adColumn.name}`;
      return this.tableDirectTimestamp
        && this.referenceItemsMap.get(key);
    }
  }

  get observableTabProperties() {
    const { name, adfields, targetEndpoint, filterQuery, parentId, promoted } = this.tab;
    return { name, adfields, targetEndpoint, filterQuery, parentId, promoted };
  }

  @Watch('observableTabProperties')
  onObservableTabPropertiesChange(tab: any) {
    // Reset the pagination.
    this.page = 1;
    this.propOrder = 'id';
    this.reverse = false;

    this.tabName = tab.name;
    this.fields = tab.adfields;
    this.baseApiUrl = tab.targetEndpoint;
    this.filterQuery = tab.filterQuery;
    this.parentId = tab.parentId || 0;

    if (this.lazyLoad && this.parentId) {
      this.retrieveAllRecords();
    } else if (!this.lazyLoad) {
      this.retrieveAllRecords();
    }
  }

  @Watch('fields')
  onFieldsChange(fields: any[]) {
    this.referenceItemsMap.clear();
    this.gridFields = fields
      .filter(field => field.showInGrid)
      .sort((a, b) => {
        if (a.gridSequence !== null) {
          return b.gridSequence !== null ? a.gridSequence - b.gridSequence : -1;
        }

        return b.gridSequence !== null ? 1 : -1;
      });

    // Prepare the form validation schema.
    this.validationSchema = {};
    for (let field of this.fields) {
      const column = field.adColumn;
      this.validationSchema[column.name] = {
        type: getValidatorType(column.type),
        required: column.mandatory
      }
    }
  }

  // Start of lifecycle events.
  created() {
    this.onFieldsChange(this.fields);
    this.searchPanelEventBus?.$on('filter-updated', this.filterRecord);
    
    this.toolbarEventBus?.$on('add-record', this.addBlankRow);
    this.toolbarEventBus?.$on('copy-record', this.copyRow);
    this.toolbarEventBus?.$on('save-record', this.beforeSave);
    this.toolbarEventBus?.$on('cancel-operation', this.cancelOperation);
    this.toolbarEventBus?.$on('delete-record', this.deleteRow);
    this.toolbarEventBus?.$on('refresh-data', this.refreshData);
  }

  beforeDestroy() {
    this.searchPanelEventBus?.$off('filter-updated', this.filterRecord);

    this.toolbarEventBus?.$off('add-record', this.addBlankRow);
    this.toolbarEventBus?.$off('copy-record', this.copyRow);
    this.toolbarEventBus?.$off('save-record', this.beforeSave);
    this.toolbarEventBus?.$off('cancel-operation', this.cancelOperation);
    this.toolbarEventBus?.$off('delete-record', this.deleteRow);
    this.toolbarEventBus?.$off('refresh-data', this.refreshData);
  }
  // End of lifecycle events.

  public hasError(field: any) {
    return this.errorTimestamp && this.errors.has(field.adColumn.name);
  }

  public getErrorMessage(field: any) {
    return this.errorTimestamp && this.errors.get(field.adColumn.name);
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  public async changeCurrentRow(row: any) {
    if (this.newRecord) {
      return;
    }

    if (this.editing) {
      const noChanges = _.isEqual(this.originalRecord, this.currentRecord);

      if (noChanges) {
        this.reset();
      } else {
        this.save(this.reset);
      }    
    }

    await this.registerTabState({
      path: this.$route.fullPath,
      tabId: this.tabName,
      data: row
    });

    this.gridFields.forEach(field => {
      this.updateReferenceQueries(field);
    })

    this.currentRecord = row;
    this.$emit('current-row-change', row);
  }

  public changeRowSelection(rows: Array<any>) {
    this.selectedRows = rows;
    this.$emit('row-selection-change', rows);
  }

  public changeMultipleRowSelection(value: any, o: any) {
    this.multipleSelectedRow = value;
  }
  
  public handleMultipleDataToJson(params: any) {
    this.gridFields.forEach((r, i)=>{
      this.filterVal.push(this.gridFields[i].adColumn.name);
    })

    this.getId = params;
  }

  public actionDelete(): void {
    Promise.allSettled(this.multipleSelectedRow.map((row: any) => {
      return this.dynamicWindowService(this.baseApiUrl).delete(row.id);
    })).then((results) => {
      const deletedCount = results.filter(res => res.status === 'fulfilled').length
      const message = this.$t(`opusWebApp.applicationDictionary.recordsDeleted`, {
        tabName: this.tabName,
        count: deletedCount
      });

      this.retrieveAllRecords();
      this.toolbarEventBus.$emit('record-saved');
      this.$notify({
        title: 'Success',
        message: message.toString(),
        type: 'success',
        duration: 3000
      });
    }).finally(() => {
      this.multipleSelectedRow = [];
      this.closeDialog();
    });
  }

  public closeDialog(): void {
    this.showDeleteDialog = false;
  }

  /**
   * Update the validation rule for a specific field. Triggered when the window
   * context is just updated.
   * @param field 
   */
  private updateReferenceQueries(field: any) {
    if (this.isTableDirectLink(field)) {
      // Parse the validation rule which is used to filter the reference key records.
      const column = field.adColumn;
      const validationRule = field.adValidationRule || column.adValidationRule;
      const referenceFilter = this.parseContextVariable(validationRule?.query);
      this.referenceFilterQueries.set(field.id, referenceFilter);
    }
  }

  private deleteRow(data?: any) {
    if (!this.mainTab && data?.tabId !== this.tabId)
      return;

    if (this.multipleSelectedRow.length) {
        this.handleMultipleDataToJson(this.multipleSelectedRow);
        this.showDeleteDialog = true;
    } else {
      this.$notify({
        title: 'Warning',
        message: "Please select at least one item",
        type: 'warning',
        duration: 3000
      });
    }
  }

  private refreshData() {
    if (this.mainTab) this.clear();
  }

  public setTableDirectReference(field: any): void {
    this.activeTableDirectField = field;
  }

  public cancelOperation(data?: any) {
    if (!this.mainTab && data?.tabId !== this.tabId)
      return;

    // New blank row and duplicate row are considered new record.
    if (this.newRecord) {
      const index = this.gridData.indexOf(this.currentRecord);
      this.newRecord = false;
      this.currentRecord = null;
      this.editing = false;
      if (this.gridData.length > 1) {
        this.setRow(this.gridData[index > 0 ? index - 1 : 1]);
      }
      this.$nextTick(() => {
        this.gridData.splice(index, 1);
      })
    } else if (this.editing) {
      if (!_.isEqual(this.originalRecord, this.currentRecord)) {
        for (let key in this.currentRecord) {
          this.currentRecord[key] = this.originalRecord[key];
        }
      }
      this.editing = false;
      this.$set(this.currentRecord, 'editing', this.editing);
    }
  }

  /**
   * Toggled on by double clicking a row.
   * @param row Current selected row.
   */
  public async activateInlineEditing(row: any) {
    if (this.newRecord)
      return;

    if (this.editing && row === this.currentRecord)
      return;

    this.originalRecord = {...this.currentRecord};
    await this.initRelationships(row);
    row.editing = !row.editing;
    this.originalRecord.editing = row.editing;
    this.editing = row.editing;
    this.toolbarEventBus.$emit('inline-editing', true);
  }

  private filterRecord(query) {
    //console.log('Filter query: %O', query);
    this.filterQuery = query;
    this.retrieveAllRecords();
  }

  private reset() {
    this.toolbarEventBus.$emit('inline-editing', false);
    this.editing = false;
    if (this.currentRecord !== null) {
      this.$set(this.currentRecord, 'editing', this.editing);
    }
  }

  /**
   * Triggered when user is going to add a new record.
   */
  private async addBlankRow(data?: any) {
    if (!this.mainTab && data?.tabId !== this.tabId)
      return;

    let record = {
      id: 0
    };
    if (this.parentId) {
      record[this.tab.foreignColumnName] = this.parentId;
    }
    this.prepareForm(record);
    // await this.initRelationships(record);
    this.gridData.splice(0, 0, record);
    this.$nextTick(() => {
      this.setRow(record);
      this.activateInlineEditing(record);
      this.newRecord = true;
    });
  }

  private async copyRow(data?: any) {
    if (!this.mainTab && data?.tabId !== this.tabId)
      return;

    const currentIndex = this.gridData.indexOf(this.currentRecord);
    let record = {...this.currentRecord};
    record.id = 0;
    this.prepareForm(record, false);
    // await this.initRelationships(record);
    this.gridData.splice(currentIndex + 1, 0, record);
    this.$nextTick(() => {
      this.setRow(record);
      this.activateInlineEditing(record);
      this.newRecord = true;
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

  private beforeSave(data?: any) {
    if (this.mainTab || data?.tabId === this.tabId)
      this.save(this.reset);
  }

  private save(callback?: (record?: any) => void) {
    const {
      editing,
      createdBy,
      createdDate,
      lastModifiedBy,
      lastModifiedDate,
      ...record
    } = this.currentRecord;
    const validator = new schema(this.validationSchema);

    validator.validate(record, (errors: any[], fields: any) => {
      if (errors) {
        for (let error of errors) {
          this.errors.set(error.field, error.message);
          (<any> this.$refs[error.field][0])?.$el.classList.add('is-error');
        }
        this.errorTimestamp = Date.now();
      } else {
        for (let field in record) {
          this.errors.delete(field);
          if (this.$refs[field])
            (<any> this.$refs[field][0])?.$el.classList.remove('is-error');
        }
        
        this.isSaving = true;
        this.saveRecord(record, callback);
      }
    });
  }

  private saveRecord(record: any, callback?: (record?: any) => void) {
    const update: boolean = record.id > 0;

    if (!update) {
      delete record.id;
    }

    const service = this.dynamicWindowService(this.baseApiUrl);
    const saveState = update ? service.update(record) : service.create(record);
    saveState.then(data => {
      this.retrieveAllRecords();
      callback && callback(data);
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
      console.error('Error saving the record! %O', err);
      const message = `Error saving the record`;
      this.$notify({
        title: 'Error',
        message: message.toString(),
        type: 'error',
        duration: 3000
      });
    })
    .finally(() => {
      this.newRecord = false;
      this.editing = false;
      this.isSaving = false;
    });
  }

  public retrieveAllRecords(): void {
    this.isFetching = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: this.filterQuery,
        paginationQuery
      })
      .then(res => {
        this.gridData = res.data.map((item: any) => {
          item.editing = false;
          return item;
        });

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        if (this.gridData.length) {
          this.setRow(this.gridData[0]);
        }
      }).
      catch((err) => {
        console.error('Failed getting the record. %O', err);
      })
      .finally(() => {
        this.isFetching = false;
      });
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    this.transition();
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public changePageSize(size: number) {
    this.itemsPerPage = size;
    this.retrieveAllRecords();
  }

  /**
   * This method is invoked each time user typing in el-select component
   * with remote attribute enabled.
   * @param query 
   */
  public fetchTableDirectData(query: string) {
    const field = this.activeTableDirectField;
    if (query.trim() !== '') {
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
          this.referenceItemsMap
            .set(`${this.currentRecord.id}-${field.adColumn.name}`, res.data);
          this.tableDirectTimestamp = Date.now();
        });
    }
  }

  public syncHeight() {
    const grid = (<ElTable>this.$refs.grid).$el;
    this.height = grid.clientHeight;
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.grid).setCurrentRow(record);
  }

  private transition(): void {
    this.retrieveAllRecords();
  }

  private sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  private async initRelationships(row: any) {
    for (let field of this.gridFields) {
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
          this.referenceItemsMap.set(`${column.name}`, res.data);
        }

        // Get the item list of Reference Key[DATATYPE|table|direct].
        else if (this.isTableDirectLink(field)) {
          const resourceName = pluralize.plural(kebabCase(column.importedTable));
          const api = `/api/${resourceName}`;
          let criteriaQuery: string[] = row[column.name] == null ? [] : [`id.equals=${row[column.name]}`];

          if (filterQuery) {
            criteriaQuery.push(filterQuery);
          }

          // console.log('update tableDirectReferenceMap for field: %s', field.name);
          const res = await this.dynamicWindowService(api).retrieve({ criteriaQuery });
          this.referenceItemsMap.set(`${row.id}-${column.name}`, res.data);
        }
      }
    }
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
    console.log('getReferenceList field: %O', field);
    return field?.adReference?.adreferenceLists || field.adColumn.adReference.adreferenceLists;
  }

  public hasReferenceList(field: any) {
    return field.adReference?.referenceType === ADReferenceType.LIST
      || field.adColumn.adReference?.referenceType === ADReferenceType.LIST;
  }

  public isTableDirectLink(field: any): boolean {
    const column = field.adColumn;
    const reference = field.adReference || column.adReference;
    return column.foreignKey && (reference?.value === 'direct' || reference?.value === 'table');
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
    if (this.isTableDirectLink(field)) {
      const propName = field.adColumn.name.replace(/Id$/, 'Name');
      return row[propName] || row[field.adColumn.name];
    }
    return row[field.adColumn.name];
  }

  public isActiveStatusField(column: any) {
    return column.property === 'active';
  }
}
