import { RegisterTabParameter } from '@/shared/config/store/window-store';
import { ADColumnType } from '@/shared/model/ad-column.model';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { formatJson } from '@/utils';
import { exportJson2Excel } from '@/utils/excel';
import { nullifyField } from '@/utils/form';
import schema from 'async-validator';
import { ElPagination } from 'element-ui/types/pagination';
import { ElTable } from 'element-ui/types/table';
import { debounce, isEqual, kebabCase } from 'lodash';
import pluralize from "pluralize";
import { Component, Inject, Mixins, Vue, Watch } from 'vue-property-decorator';
import { mapActions } from 'vuex';
import ContextVariableAccessor from "../ContextVariableAccessor";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

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
    active: {
      type: Boolean,
      default: true
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

  gridSchema = {
    defaultSort: {},
    emptyText: 'No Records Found'
  };
  selectedRowNo = 1;
  itemsPerPage = 10;
  queryCount: number = null;
  page = 1;
  previousPage = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;
  isFetching = false;
  gridData: Array<any> = [];

  // The displayed fields.
  gridFields: Array<any> = [];

  isSaving: boolean = false;

  // Multiple row selection.
  selectedRows: any[] = [];
  filterVal: any = [];

  //Dialog
  showDeleteDialog: boolean = false;
  showExportDialog: boolean = false;
  checkCurrentRow: boolean = false;
  buttonExport: boolean = false;

  //download file excel
  filename:string = '';
  autoWidth:boolean = true;
  bookType:string = '';

  formExport = {
    name: "",
    type: ""
  }
  chooseBookType = [
    { type: 'csv' },
    { type: 'xls' },
    { type: 'xlsx' },
  ]

  // Inline editing mode needs these data.
  originalRecord: any = {};
  currentRecord: any = {};
  private currentRowIndex = 0;
  private validationSchema: any = {};
  private editing = false;
  private tableDirectTimestamp: number = Date.now();
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
  private filterQueryTmp: string = '';
  private parentId: number = 0;
  private heightUpdateTimestamp = 0;
  private tableHeight = 100;

  private gridResizeObserver;
  private debouncedHeightResizer;

  private registerTabState!: (options: RegisterTabParameter) => Promise<void>;

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
    const { name, adfields, targetEndpoint, filterQuery, parentId, promoted, validationSchema } = this.tab;
    return { name, adfields, targetEndpoint, filterQuery, parentId, promoted, validationSchema };
  }

  /**
   * Sort the fields upon update.
   * @param fields The updated AdField list.
   */
  @Watch('fields')
  onFieldsChange(fields: any[]) {
    if (! fields) {
      return;
    }

    this.referenceItemsMap.clear();
    this.gridFields = fields
      .filter(field => field.showInGrid)
      .sort((prevItem, nextItem) => {
        const prevSequence = prevItem.detailSequence || 0;
        const nextSequence = nextItem.detailSequence || 0;
        return prevSequence - nextSequence;
      });
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
    this.validationSchema = tab.validationSchema;

    if (!this.lazyLoad || this.parentId) {
      this.retrieveAllRecords();
    }
  }

  @Watch('page')
  onPageChange(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  // Start of lifecycle events.
  created() {
    this.onFieldsChange(this.fields);
    this.toolbarEventBus?.$on('export-record', this.exportRecord);
    this.debouncedHeightResizer = debounce(this.resizeTableHeight, 200);
  }

  mounted() {
    // AP-194 Fix Element UI's el-table layout
    this.gridResizeObserver = new ResizeObserver(entries => {
      entries.forEach(entry => {
        this.debouncedHeightResizer(entry.contentRect.height);
      });
    });
    this.gridResizeObserver.observe(this.$refs.tableWrapper);
  }

  beforeDestroy() {
    this.gridResizeObserver.unobserve(this.$refs.tableWrapper);
    this.toolbarEventBus?.$off('export-record', this.exportRecord);
  }
  // End of lifecycle events.

  private resizeTableHeight(height) {
    const paginationHeight = (<ElPagination>this.$refs.pagination).$el.clientHeight;
    console.log('Resizing height to ', height - paginationHeight);
    this.tableHeight = height - paginationHeight;
  }

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

  public setSelectedRecordNo(no: number) {
    const {pageNo, rowNo} = this.getPageAndRowNo(this.itemsPerPage, no);
    if (this.mainTab) {
      this.currentRowIndex = rowNo - 1;
      this.setRow(this.gridData[this.currentRowIndex]);
    }
    this.page = pageNo;
  }

  /**
   * Handle single row selection
   * @param row The current selected row.
   */
  public async onCurrentRowChanged(row: any) {
    if (this.newRecord) {
      return;
    }

    if (this.editing) {
      const noChanges = isEqual(this.originalRecord, this.currentRecord);

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

    this.updateReferenceQueries(this.gridFields);
    this.currentRecord = row;
    this.selectedRowNo = this.gridData.indexOf(row) + 1;
    if (this.mainTab) {
      const recordNo = this.getRecordNo(this.page, this.itemsPerPage, this.selectedRowNo);
      this.$emit('current-row-change', {
        data: row,
        recordNo
      });
    }
  }

  /**
   * Handle multiple rows selection.
   * @param rows The selected rows
   */
  public onSelectionChanged(value: any) {
    this.selectedRows = value;
  }

  public handleMultipleDataToJson(params: any) {
    this.gridFields.forEach((r, i)=>{
      this.filterVal.push(this.gridFields[i].adColumn.name);
    })

  }

  public deleteRecords(): void {
    const multiple = this.selectedRows.length > 0;

    if (! multiple) {
      (<ElTable>this.$refs.grid).toggleRowSelection(this.currentRecord);
    }
    Promise.allSettled(this.selectedRows.map((row: any) => {
      return this.dynamicWindowService(this.baseApiUrl).delete(row.id);
    })).then((results) => {
      const deletedCount = results.filter(res => res.status === 'fulfilled').length
      const message = this.$t(`opusWebApp.applicationDictionary.recordsDeleted`, {
        tabName: this.tabName,
        count: deletedCount
      });

      this.retrieveAllRecords();
      this.$notify({
        title: 'Success',
        message: message.toString(),
        type: 'success',
        duration: 3000
      });
    }).finally(() => {
      this.selectedRows = [];
      this.$emit('rows-deleted');
    });
  }

  public handleCheckCurrentRow(){
    if(this.checkCurrentRow == true){
      if(this.selectedRows.length >= 1){
        this.buttonExport = false;
      }else{
        this.buttonExport = true;
        this.$notify({
          title: 'Warning',
          message: "Please select at least one item",
          type: 'warning',
          duration: 3000
        });
      }
    }else{
      this.buttonExport = false;
    }
  }

  public actionExport(): void {
    this.bookType = this.formExport.type;
    this.filename = this.formExport.name;

    let tHeaderTmp, tDataTemp;
    let tHeader: Array<any> = [];
    let filterVal: Array<any> = [];
    var service: any = '';
    var data: any = '';

    for (let i = 0; i < this.fields.length; i++) {
      tHeaderTmp = this.fields[i].name;
      tDataTemp = this.fields[i].adColumn.name;

      tHeader.push(tHeaderTmp);
      filterVal.push(tDataTemp);
    }

    if (this.checkCurrentRow == true) {
      if (this.selectedRows.length >= 1) {
        for (let i = 0; i < this.selectedRows.length; i++) {
          data = formatJson(filterVal, this.selectedRows);
        }
      }
    } else {
      service = this.gridData;
      data = formatJson(filterVal, service);
    }
    exportJson2Excel(tHeader, data, this.filename !== '' ? this.filename : 'excel-list', undefined, undefined, this.autoWidth, this.bookType !== '' ? this.bookType : "csv");
  }

  public closeDialog(): void {
    this.showExportDialog = false;
  }

  /**
   * Update the validation rule for a referenced field. Triggered once the records
   * has been loaded.
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

  private exportRecord(data?: any) {
    if (! this.mainTab && data?.tabId !== this.tabId)
      return;
      
    this.showExportDialog = true;
  }

  public setTableDirectReference(field: any): void {
    this.activeTableDirectField = field;
  }

  public cancelOperation() {
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
      if ( ! isEqual(this.originalRecord, this.currentRecord)) {
        for (let key in this.currentRecord) {
          this.currentRecord[key] = this.originalRecord[key];
        }
      }
      this.editing = false;
      this.$set(this.currentRecord, 'editing', this.editing);
    }
  }

  public editRecord() {
    this.activateInlineEditing(this.currentRecord);
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
    this.$emit('inline-editing');
  }

  private getRecordNo = (page: number, size: number, row: number) => (page - 1) * size + row;

  private getPageAndRowNo = (size: number, recordNo: number) => {
    return {
      rowNo: recordNo % size || size,
      pageNo: Math.floor(((recordNo - 1) / size) + 1)
    };
  }

  public clearFilterQueryTmp() {
    this.filterQueryTmp = '';
  }

  public filterRecord(query: string) {
    if (!query) {
      this.filterQuery = this.filterQueryTmp || this.tab.nativeFilterQuery;
      this.filterQueryTmp = '';
    } else {
      if (this.filterQueryTmp === '') {
        this.filterQueryTmp = this.filterQuery;
      }

      this.filterQuery = this.filterQueryTmp ? `${this.filterQueryTmp}&${query}` : query;
    }
    this.retrieveAllRecords();
  }

  private reset() {
    this.$emit('quit-edit-mode')
    this.editing = false;
    if (this.currentRecord !== null) {
      this.$set(this.currentRecord, 'editing', this.editing);
    }
  }

  /**
   * Triggered when user is going to add a new record.
   */
  public async addBlankRow() {
    let record = { id: 0, editing: true };
    await this.prepareForm(record);
    if (this.parentId) {
      record[this.tab.foreignColumnName] = this.parentId;
    }
    await this.initRelationships(record);
    this.originalRecord = {...record};
    this.gridData.splice(0, 0, record);
    this.$emit('inline-editing', true);
    this.$nextTick(() => {
      this.setRow(record);
      this.newRecord = true;
      this.editing = record.editing;
    });
  }

  public copyRow() {
    const currentIndex = this.gridData.indexOf(this.currentRecord);
    let record = {...this.currentRecord};
    record.id = 0;
    this.gridData.splice(currentIndex + 1, 0, record);
    this.$nextTick(() => {
      this.setRow(record);
      this.activateInlineEditing(record);
      this.newRecord = true;
    });
  }

  private async prepareForm(row: any, newRecord: boolean = true) {
    for (const field of this.fields) {
      const column = field.adColumn;

      if (newRecord) {
        let defaultValue = field.defaultValue || column.defaultValue;

        if (defaultValue?.includes('{select:', 0)) {
          defaultValue = await this.retrieveRemoteValue(defaultValue);
        } else {
          // TODO Set the default value based on the field/column definition. 
          defaultValue = this.getContext(field.defaultValue || column.defaultValue);
        }

        if (! defaultValue) {
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

  private async retrieveRemoteValue(options: string) {
    let params = options.substring(1, options.length - 1)
      .split(/\s*?,\s*?/)
      .map(item => {
        const param = item.trim().split(/\s*?:\s*?/);
        const result = {};
        result[param[0]] = param[1].trim();
        return result;
      });

    const config = Object.assign({}, ...params);
    const resourceName = pluralize.plural(kebabCase(config.from));
    const api = `/api/${resourceName}`;
    const response = await this.dynamicWindowService(api)
      .retrieve({criteriaQuery: config.where});

    if (response.data.length) {
      return Promise.resolve(response.data[0][config.select]);
    }

    return Promise.resolve(null);
  }

  public beforeSave() {
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

    this.gridFields.forEach(field => {
      nullifyField(record, field);
    });

    validator.validate(record, (errors: any[]) => {
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
      this.$emit('record-saved');
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
          // Initialize editing prop to allow inline edit mode reactivity.
          item.editing = false;
          return item;
        });

        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;
        this.$emit('total-count-changed', this.queryCount);

        // TODO Skip selecting the first row when the user is navigating
        // back to the previous page by using the record-navigator. 
        if (this.gridData.length) {
          const rowExists = this.gridData[this.currentRowIndex] !== void 0;
          this.setRow(this.gridData[rowExists ? this.currentRowIndex : 0]);
        }
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.isFetching = false;
      });
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder.prop;
    this.reverse = propOrder.order === 'ascending';
    const {propOrder: property, reverse} = this;
    this.$emit('order-changed', { property, reverse });
    this.transition();
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
          .set(`${this.currentRecord.id}-${field.adColumn.name}`, res.data);
        this.tableDirectTimestamp = Date.now();
      });
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
          const linkedFieldValue = row[column.name];
          const criteriaQuery: string[] = linkedFieldValue ? [`id.equals=${linkedFieldValue}`] : [];

          // Append additional query from the cached validation rule query, if any.
          if (filterQuery) {
            criteriaQuery.push(filterQuery);
          }

          const res = await this.dynamicWindowService(api).retrieve({ criteriaQuery });
          this.referenceItemsMap.set(`${row.id}-${column.name}`, res.data);
        }
      }
    }
  }

  public isFixed(field: any): boolean | string {
    //if (field.adColumn.name === 'active') {
      //return 'right';
    //}
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
    return field?.adReference?.adreferenceLists || field.adColumn.adReference?.adreferenceLists;
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
