import Component from 'vue-class-component';
import { Vue, Watch, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';
import { IADField } from '@/shared/model/ad-field.model';
import { ADColumnType } from '@/shared/model/ad-column.model';
import schema from 'async-validator';
import { getValidatorType } from '@/utils/validate';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { kebabCase } from "lodash";
import pluralize from "pluralize";
import { AccountStoreModule } from '@/shared/config/store/account-store';
import _ from 'lodash';

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
    orderQuery: String,

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

@Component
export default class GridView extends GridViewProps {
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
  multipleSelectedRow: any = [];
  filterVal: any = [];

  //Dialog
  showDeleteDialog: boolean = false;

  // Inline editing mode needs these data.
  originalRecord: any = {};
  currentRecord: any = {};
  validationRule: any = {};
  editing = false;
  tableDirectTimestamp: number = Date.now();
  private newRecord: boolean = false;
  private tableDirectReferenceMap = new Map();
  private activeTableDirectField: any = null;

  // Error messages.
  private errors = new Map();
  private errorTimestamp = Date.now();

  private tabName: string = '';
  private fields: any[] = [];
  private baseApiUrl: string = '';
  private filterQuery: string = '';
  private parentId: number = 0;
  private getId: any = null;

  getTableDirectReferences(field: any): any[] {
    if (this.currentRecord === null || field == null) {
      return [];
    }
    const key = `${this.currentRecord.id}-${field.adColumn.name}`;
    return field && this.tableDirectTimestamp
      && this.tableDirectReferenceMap.get(key);
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
  onFieldsChange(fields: Array<IADField>) {
    this.tableDirectReferenceMap.clear();
    this.gridFields = fields.filter(field => field.showInGrid);
    this.validationRule = {};
    for (let field of this.fields) {
      const column = field.adColumn;
      this.validationRule[column.name] = {
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
    this.toolbarEventBus?.$on('save-record', this.save);
    this.toolbarEventBus?.$on('cancel-operation', this.cancelOperation);
    this.toolbarEventBus?.$on('delete-record', this.deleteRow);
    this.toolbarEventBus?.$on('refresh-data', this.refreshData);
  }

  beforeDestroy() {
    this.searchPanelEventBus?.$off('filter-updated', this.filterRecord);

    this.toolbarEventBus?.$off('add-record', this.addBlankRow);
    this.toolbarEventBus?.$off('copy-record', this.copyRow);
    this.toolbarEventBus?.$off('save-record', this.save);
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

  public changeCurrentRow(row: any) {
    if (this.newRecord) {
      return;
    }

    if (this.editing) {
      const noChanges = _.isEqual(this.originalRecord, this.currentRecord);
      console.log('noChanges: %s', noChanges);
      const reset = () => {
        this.toolbarEventBus.$emit('inline-editing', false);
        this.editing = false;
        if (this.currentRecord !== null) {
          this.$set(this.currentRecord, 'editing', this.editing);
        }
      };

      if (noChanges) {
        reset();
      } else {
        this.save(reset);
      }    
    }

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

  public removeCountry(): void {
    for (let i = 0; i < this.multipleSelectedRow.length; i++) {
      const service = this.dynamicWindowService(this.baseApiUrl);
      const saveState = service.delete(this.getId[i].id);
      saveState.then(data => {
        this.retrieveAllRecords();
        const message = this.$t(`opusWebApp.applicationDictionary.deleted`, {
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

        this.getId[i] = null;
        this.multipleSelectedRow = [];
        this.closeDialog();
      })
    }
  }

  public closeDialog(): void {
    this.showDeleteDialog = false;
  }

  private deleteRow(){
    if (this.multipleSelectedRow.length) {
        this.handleMultipleDataToJson(this.multipleSelectedRow);
        this.showDeleteDialog = true;
    }else{
      this.$notify({
        title: 'Warning',
        message: "Please select at least one item",
        type: 'warning',
        duration: 3000
      });
    }
  }

  private refreshData(){
    this.clear();
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
    console.log('original: %O, current: %O', this.originalRecord, this.currentRecord);
    this.prepareFormAndValidation(row, false);
    await this.initRelationships(row);
    row.editing = !row.editing;
    this.originalRecord.editing = row.editing;
    this.editing = row.editing;
    this.toolbarEventBus.$emit('inline-editing', true);
  }

  private filterRecord(query) {
    console.log('Filter query: %O', query);
  }

  /**
   * Triggered when user is going to add a new record.
   */
  private async addBlankRow() {
    if (this.mainTab) {
      let record = {
        id: 0
      };
      if (this.parentId) {
        record[this.tab.foreignColumnName] = this.parentId;
      }
      this.prepareFormAndValidation(record);
      await this.initRelationships(record);
      this.gridData.splice(0, 0, record);
      this.$nextTick(() => {
        this.setRow(record);
        this.activateInlineEditing(record);
        this.newRecord = true;
      });
    }
  }

  private async copyRow() {
    if (this.mainTab) {
      const currentIndex = this.gridData.indexOf(this.currentRecord);
      let record = {...this.currentRecord};
      record.id = 0;
      record.editing = true;
      this.prepareFormAndValidation(record, false);
      await this.initRelationships(record);
      this.gridData.splice(currentIndex + 1, 0, record);
      this.$nextTick(() => {
        this.setRow(record);
        this.activateInlineEditing(record);
        this.newRecord = true;
      });
    }
  }

  private prepareFormAndValidation(row: any, newRecord: boolean = true) {
    for (const field of this.fields) {
      const column = field.adColumn;

      if (newRecord) {
        // TODO Set the default value based on the field/column definition. 
        let defaultValue = column.defaultValue?.match(/\{([\#]?[\w]+)\}/);
        if (defaultValue) {
          defaultValue = AccountStoreModule.properties.get(defaultValue[1]);
        } else {
          defaultValue = column.defaultValue;
        }

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

  private save(callback?: (record?: any) => void) {
    if (this.mainTab) {
      const {
        editing,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        ...record
      } = this.currentRecord;
      const validator = new schema(this.validationRule);

      validator.validate(record, (errors: any[], fields: any) => {
        if (errors) {
          for (let error of errors) {
            this.errors.set(error.field, error.message);
            this.errorTimestamp = Date.now();
            (<any> this.$refs[error.field][0])?.$el.classList.add('is-error');
          }
        } else {
          for (let field in record) {
            if (this.$refs[field])
              (<any> this.$refs[field][0])?.$el.classList.remove('is-error');
          }
          
          this.isSaving = true;
          this.saveRecord(record, callback);
        }
      });
    }
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
          this.tableDirectReferenceMap
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
    let needUpdate = false;
    for (let field of this.gridFields) {
      const column = field.adColumn;
      const reference = column.adReference;
      if (column.foreignKey) {
        const resourceName = pluralize.plural(kebabCase(column.importedTable));
        const api = `/api/${resourceName}`;

        if (reference?.referenceType === 'DATATYPE' || reference?.value === 'direct' || reference?.value === 'table') {
          let criteriaQuery = row[column.name] == null ? null : `id.equals=${row[column.name]}`;
          const res = await this.dynamicWindowService(api)
            .retrieve({ criteriaQuery });
          this.tableDirectReferenceMap
            .set(`${row.id}-${column.name}`, res.data);
          this.tableDirectTimestamp = Date.now();
          needUpdate = true;
        }
      }
    }

    if (needUpdate)
      this.tableDirectTimestamp = Date.now();
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
    //console.log('row:%O,field:%O', row,field)
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
