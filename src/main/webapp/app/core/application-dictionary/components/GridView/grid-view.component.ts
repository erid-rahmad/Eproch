import settings from '@/settings';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { IRegisterTabParameter, WindowStoreModule as windowStore } from '@/shared/config/store/window-store';
import { ADColumnType } from '@/shared/model/ad-column.model';
import { IADField } from '@/shared/model/ad-field.model';
import { formatJson } from '@/utils';
import { exportJson2Excel } from '@/utils/excel';
import { normalizeField } from '@/utils/form';
import { hasReferenceList, isActiveStatusField, isAttachmentField, isBooleanField, isDateField, isDateTimeField, isNewRecord, isNumericField, isPasswordField, isStringField, isTableDirectLink, hasPrecision } from '@/utils/validate';
import schema from 'async-validator';
import { format, parseISO } from 'date-fns';
import { ElPagination } from 'element-ui/types/pagination';
import { ElTable } from 'element-ui/types/table';
import { cloneDeep, debounce, isEqual, kebabCase } from 'lodash';
import pluralize from "pluralize";
import { Component, Mixins, Vue, Watch } from 'vue-property-decorator';
import { mapActions } from 'vuex';
import CalloutMixin from '../../mixins/CalloutMixin';
import AddressEditor from "../AddressEditor/address-editor.vue";
import ContextVariableAccessor from "../ContextVariableAccessor";
import PasswordEditor from "../PasswordEditor/password-editor.vue";

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

    accessLevel: String,

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
  components: {
    AddressEditor,
    PasswordEditor
  },
  methods: {
    isNewRecord,
    isStringField,
    isPasswordField,
    isNumericField,
    isDateField,
    isDateTimeField,
    isBooleanField,
    hasPrecision,
    hasReferenceList,
    isTableDirectLink,
    isAttachmentField,
    isActivatorSwitch: isActiveStatusField,
    ...mapActions({
      registerTabState: 'windowStore/registerTab'
    })
  }
})
export default class GridView extends Mixins(ContextVariableAccessor, CalloutMixin, GridViewProps) {
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
  processing = false;
  gridData: Array<any> = [];

  // The displayed fields.
  gridFields: IADField[] = [];

  // Multiple row selection.
  selectedRows: any[] = [];
  filterVal: any = [];

  // Dialog
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
  private originalRecord: any = {};
  private currentRecord: any = {};
  private editing: boolean = false;
  private newRecord: boolean = false;

  private currentRowIndex = 0;
  private validationSchema: any = {};
  private dynamicValidationSchema: any = {};

  // Update the table referenced dropdown list.
  private tableDirectTimestamp: number = Date.now();

  private referenceItemsMap = new Map();
  private activeTableDirectField: any = null;

  // Store the validation rule's queries.
  private referenceFilterQueries: Map<number, string> = new Map();

  // Error messages.
  private errors = new Map();
  private errorTimestamp = Date.now();

  private fields: any[] = [];
  private baseApiUrl: string = '';
  private filterQuery: string = '';
  private filterQueryTmp: string = '';
  private parentId: number = 0;
  private heightUpdateTimestamp = 0;
  private tableHeight = 100;

  private gridResizeObserver;
  private debouncedHeightResizer: (height: number) => void;

  private registerTabState!: (options: IRegisterTabParameter) => Promise<void>;

  get isVendor() {
    return accountStore.userDetails.vendor && this.accessLevel === 'CLN_VND';
  }

  get referenceListItems() {
    return (field: IADField): any[] => {
      if (this.currentRecord === null || field == null) {
        return [];
      }
      return this.referenceItemsMap.get(field.adColumn.name);
    }
  }

  // TODO Find another alternatives than using tableDirectTimestamp.
  get tableDirectReferences() {
    return (field: IADField): any[] => {
      if (this.currentRecord === null || field == null) {
        return [];
      }
      const key = `${this.currentRecord.id}-${field.adColumn.name}`;
      return this.tableDirectTimestamp
        && this.referenceItemsMap.get(key);
    }
  }

  // TODO Consider the performance.
  get displayed() {
    return (row: any, field: IADField) =>
      this.evaluateDisplayLogic({
        defaultTabId: this.tabName,
        field,
        record: row
      });
  }

  get observableTabProperties() {
    const { name, adfields, targetEndpoint, filterQuery, parentId, promoted, validationSchema } = this.tab;
    return { name, adfields, targetEndpoint, filterQuery, parentId, promoted, validationSchema };
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

  /**
   * Sort the fields upon update.
   * @param fields The updated AdField list.
   */
  @Watch('fields')
  async onFieldsChange(fields: IADField[]) {
    if (! fields) {
      return;
    }

    this.referenceItemsMap.clear();
    this.gridFields = fields
      .filter(field => field.showInGrid)
      .sort((prevItem, nextItem) => {
        const prevSequence = prevItem.gridSequence || 0;
        const nextSequence = nextItem.gridSequence || 0;
        return prevSequence - nextSequence;
      });

    const logicallyMandatoryFields = this.gridFields
      .filter(field => !!field.mandatoryLogic || !!field.adColumn?.mandatoryLogic);

    await windowStore.addMandatoryFields({
      path: this.$route.fullPath,
      tabId: this.tabName,
      fields: Object.assign({}, ...logicallyMandatoryFields.map(field => ({[field.virtualColumnName || field.adColumn.name]: field})))
    });

    this.reloadValidationSchema();
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
    this.dynamicValidationSchema = cloneDeep(this.validationSchema);

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

  // Start of the component's lifecycle events.
  created() {
    this.toolbarEventBus?.$on('export-record', this.exportRecord);
    this.toolbarEventBus?.$on('print-record', this.printRecord);
    this.debouncedHeightResizer = debounce(this.resizeTableHeight, 300);
    this.onObservableTabPropertiesChange(this.observableTabProperties);
  }

  mounted() {
    // AP-194 Fix Element UI's el-table layout
    this.gridResizeObserver = new ResizeObserver(entries => {
      entries.forEach(entry => {
        this.debouncedHeightResizer(entry.contentRect.height);
      });
    });
    this.gridResizeObserver.observe(<Element> this.$refs.tableWrapper);
  }

  beforeDestroy() {
    this.gridResizeObserver.unobserve(<Element> this.$refs.tableWrapper);
    this.toolbarEventBus?.$off('export-record', this.exportRecord);
    this.toolbarEventBus?.$off('print-record', this.printRecord);
  }
  // End of lifecycle events.

  private resizeTableHeight(height: number) {
    const paginationHeight = (<ElPagination>this.$refs.pagination).$el.clientHeight;
    this.tableHeight = height - paginationHeight;
  }

  public hasError(field: IADField) {
    const fieldName = field.virtualColumnName || field.adColumn.name;
    return this.errorTimestamp && this.errors.has(fieldName);
  }

  public getErrorMessage(field: IADField) {
    const fieldName = field.virtualColumnName || field.adColumn.name;
    return this.errorTimestamp && this.errors.get(fieldName);
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
    this.originalRecord = cloneDeep(row);
    this.selectedRowNo = this.gridData.indexOf(row) + 1;

    // Update the record-navigator.
    if (this.mainTab) {
      const recordNo = this.getRecordNo(this.page, this.itemsPerPage, this.selectedRowNo);
      this.$emit('current-row-change', {
        data: row,
        recordNo
      });
    }
  }

  /**
   * Invoked once the field value changed. It should checks the following steps:
   * 1. Update the respective tab state.
   * 2. Update the validation rules for any fields that depends on this field.
   * 3. Check for any tabs and fields that are conditionally displayed and read-only.
   * 3. Execute callouts.
   * @param field The updated field.
   * @param value The new value.
   */
  public onInputChanged(field: IADField, value: any) {
    this.registerTabState({
      path: this.$route.fullPath,
      tabId: this.tabName,
      data: this.currentRecord
    })
    .then(() => {
      if (this.hasReferenceList(field) || this.isTableDirectLink(field)) {
        this.updateReferenceQueries(this.gridFields);
        this.initRelationships(this.currentRecord);
      }

      this.reloadValidationSchema();
      this.executeCallout(field, (val, target, type) => {
        if (type === 'RESET') {
          this.$set(this.currentRecord, target, null);
        } else if (type === 'PROCESS') {
          this.$set(this.currentRecord, target, val);
        }
      });

      // Update the record-navigator.
      if (this.mainTab) {
        const recordNo = this.getRecordNo(this.page, this.itemsPerPage, this.selectedRowNo);
        this.$emit('current-row-change', {
          data: this.currentRecord,
          recordNo
        });
      }
    });
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

  // TODO Should be able to process deletion of multiple rows in one transaction.
  public deleteRecords(): void {
    const multiple = this.selectedRows.length > 0;

    if (! multiple) {
      (<ElTable>this.$refs.grid).toggleRowSelection(this.currentRecord);
    }
    Promise.allSettled(this.selectedRows.map((row: any) => {
      return this.dynamicWindowService(this.baseApiUrl).delete(row.id);
    })).then((results) => {
      const deletedCount = results.filter(res => res.status === 'fulfilled').length

      if (deletedCount) {
        const message = this.$t(`opusWebApp.applicationDictionary.recordsDeleted`, {
          tabName: this.tabName,
          count: deletedCount
        });

        this.retrieveAllRecords();
        this.$message.success(message.toString());
      } else {
        const rejectedCount = results.filter(res => res.status === 'rejected').length;
        if (rejectedCount) {
          this.$message.error(this.$t('opusWebApp.applicationDictionary.deleteFailed').toString());
        }
      }
    })
    .finally(() => {
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
   * Update the validation rule for a referenced field. Triggered once the records
   * has been loaded.
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

      // Avoid exception when creating a new record, which will invoke
      // the backend API with a bad request.
      if (referenceFilter.includes('=null')) {
        referenceFilter = null;
      }

      if (referenceFilter) {
        this.referenceFilterQueries.set(field.id, referenceFilter);
      }
    }
  }

  private exportRecord(data?: any) {
    if (! this.mainTab && data?.tabId !== this.tabId)
      return;

    this.showExportDialog = true;
  }

  private printRecord(data?: any) {
    if (! this.mainTab && data?.tabId !== this.tabId)
      return;

    console.log(this.currentRecord);
    window.open(`/api/m-purchase-orders/report/${this.currentRecord.id}`, '_blank');
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
    this.dynamicValidationSchema = cloneDeep(this.validationSchema);
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
    this.originalRecord = cloneDeep(record);
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
    let record = cloneDeep(this.currentRecord);
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
      const fieldName = field.virtualColumnName || column.name;

      if (newRecord) {
        let defaultValue = field.defaultValue || column?.defaultValue;
        const options = {
          defaultTabId: this.tabName,
          text: defaultValue
        };

        if (defaultValue?.includes('{select:', 0)) {
          // TODO Don't use async-await.
          defaultValue = await this.retrieveRemoteValue(options);
        } else {
          defaultValue = this.getContext(options);
        }

        if (defaultValue === null) {
          if (fieldName === 'active') {
            defaultValue = true;
          } else if (this.isDateField(field) || this.isDateTimeField(field)) {
            defaultValue = null;
          } else if (this.isBooleanField(field)) {
            defaultValue = false;
          } else if (this.isNumericField(field)) {
            defaultValue = null;
          } else if (this.isStringField(field)) {
            defaultValue = null;
          }
        } else {
          if (this.isNumericField(field)) {
            defaultValue = parseFloat(defaultValue);
          }
        }
        row[fieldName] = defaultValue;
      }
    }
  }

  public beforeSave() {
    this.save(this.reset);
  }

  private save(callback?: (record?: any) => void) {
    const {
      editing,
      createdDate,
      lastModifiedBy,
      lastModifiedDate,
      ...record
    } = this.currentRecord;
    const validator = new schema(this.dynamicValidationSchema);

    this.gridFields.forEach(field => {
      normalizeField(record, field);
    });

    validator.validate(record, (errors: any[]) => {
      if (errors) {
        for (let error of errors) {
          this.errors.set(error.field, error.message);
          (<any> this.$refs[error.field][0])?.$el.classList.add('is-error');
        }
        this.$message.error(errors[0].message);
        this.errorTimestamp = Date.now();
      } else {
        for (let field in record) {
          this.errors.delete(field);
          if (this.$refs[field])
            (<any> this.$refs[field][0])?.$el.classList.remove('is-error');
        }

        this.saveRecord(record, callback);
      }
    });
  }

  private saveRecord(record: any, callback?: (record?: any) => void) {
    const update: boolean = record.id > 0;

    this.processing = true;
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
      this.$message.success(message.toString());
    })
    .catch(err => {
      console.error('Error saving the record! %O', err);
      const message = this.$t('opusWebApp.applicationDictionary.saveFailed');
      this.$message.error(message.toString());
    })
    .finally(() => {
      this.dynamicValidationSchema = cloneDeep(this.validationSchema);
      this.newRecord = false;
      this.editing = false;
      this.processing = false;
    });
  }

  public retrieveAllRecords(): void {
    if ( ! this.baseApiUrl) {
      return;
    }

    this.processing = true;
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
        this.processing = false;
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
    const keyField = column.importedTable === 'ad_user' ? 'userLogin' : 'name';
    const criteriaQuery: string[] = query ? [`${keyField}.contains=${query}`] : [];

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

  // TODO Use common function to be used by both grid and detail view.
  private async initRelationships(row: any) {
    let updated = false;
    for (let field of this.gridFields) {
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
          this.referenceItemsMap.set(`${column.name}`, res.data);
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
          this.referenceItemsMap.set(`${row.id}-${column.name}`, res.data);
          updated = true;
        }
      }
    }

    if (updated) {
      this.tableDirectTimestamp = Date.now();
    }
  }

  /**
   * Whether the field is fix-positioned on the table.
   * If true, the field will be positioned on the left side.
   * Return 'left' or 'right' to explicitly positioning the field on the
   * respective side. Default to false.
   * @param field The field.
   */
  public isFixed(field: IADField): boolean | string {
    // TODO Not implemented yet.
    return false;
  }

  public isReadonly(row: any, field: IADField): boolean {
    const newRecord: boolean = ! row?.id;
    const notUpdatable = ( ! newRecord && ! field.updatable && ! field.adColumn?.updatable);
    const conditionallyReadonly = this.evaluateReadonlyLogic({
      defaultTabId: this.tabName,
      field
    });

    return ! this.tab.writable || ! field.writable || notUpdatable || conditionallyReadonly;
  }

  public getFieldWidth(field: IADField) {
    if (this.isActivatorSwitch(field)) {
      return '96';
    }

    return '256';
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
    return field?.adReference?.adreferenceLists || field.adColumn.adReference?.adreferenceLists;
  }

  public isNewRecord!: (row: any) => boolean;
  public isStringField!: (field: IADField) => boolean;
  public isPasswordField!: (field: IADField) => boolean;
  public isNumericField!: (field: IADField) => boolean;
  public isDateField!: (field: IADField) => boolean;
  public isDateTimeField!: (field: IADField) => boolean;
  public isBooleanField!: (field: IADField) => boolean;
  public isActivatorSwitch!: (field: IADField) => boolean;
  public hasPrecision!: (field: IADField) => boolean;
  public hasReferenceList!: (field: IADField) => boolean;
  public isTableDirectLink!: (field: IADField) => boolean;
  public isAttachmentField!: (field: IADField) => boolean;

  isAddressField(field: IADField) {
    if (field.virtualColumnName) {
      return false;
    }

    const reference = field.adReference || field.adColumn.adReference;
    return reference?.value === 'address';
  }

  /**
   * Print the foreign-keyed field value.
   * @param row The record of the current row iteration.
   * @param field The inspected field of the current field iteration.
   */
  public getFieldValue(row: any, field: IADField) {
    const fieldName = field.virtualColumnName || field.adColumn.name;
    const fieldType = field.type || field.adColumn.type;

    if (this.isAddressField(field) || this.isTableDirectLink(field)) {
      const propName = fieldName.replace(/(UserId|Id)$/, 'Name');
      return row[propName] || row[fieldName];
    } else if (fieldType === ADColumnType.LOCAL_DATE) {
      const value = row[fieldName];
      if (value) {
        return format(parseISO(value), settings.dateDisplayFormat);
      }
    } else if (fieldType === ADColumnType.INSTANT || fieldType === ADColumnType.ZONED_DATE_TIME) {
      const value = row[fieldName];
      if (value) {
        return format(parseISO(value), settings.dateTimeDisplayFormat);
      }
    }
    return row[fieldName];
  }

  public getFileName(row: any, field: IADField) {
    const fieldName = field.adColumn.name;
    const propName = fieldName.replace(/(UserId|Id)$/, 'Name');
    return row[propName] || row[fieldName];
  }

  public downloadAttachment(row: any, field: IADField) {
    const fileName = `${row[field.adColumn.name]}-${this.getFileName(row, field)}`;
    window.open(`/api/c-attachments/download/${fileName}`, '_blank');
  }

}
