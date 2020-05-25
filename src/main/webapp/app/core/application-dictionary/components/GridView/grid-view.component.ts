import Component from 'vue-class-component';
import { Vue, Watch, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';
import { IADField } from '@/shared/model/ad-field.model';
import { ADColumnType } from '@/shared/model/ad-column.model';
import { ActionToolbarEventBus } from '../ActionToolbar/action-toolbar-event-bus';
import schema from 'async-validator';
import { getValidatorType } from '@/utils/validate';
import { ADReferenceType } from '@/shared/model/ad-reference.model';
import { kebabCase } from "lodash";
import pluralize from "pluralize";
import { AccountStoreModule } from '@/shared/config/store/account-store';

const GridViewProps = Vue.extend({
  props: {
    mainTab: {
      type: Boolean,
      default: false
    },
    tabName: String,
    baseApiUrl: String,
    filterQuery: String,
    orderQuery: String,

    /**
     * Record will be loaded once the parentId changed.
     */
    lazyLoad: Boolean,
    parentId: {
      type: Number,
      default: 0
    },
    fields: {
      type: Array,
      default: () => {
        return []
      }
    },
    toolbarEventBus: {
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

  // Inline editing mode needs these data.
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

  getTableDirectReferences(field: any): any[] {
    if (field == null) {
      return [];
    }
    const key = `${this.currentRecord.id}-${field.adColumn.name}`;
    return field && this.tableDirectTimestamp
      && this.tableDirectReferenceMap.get(key);
  }

  @Watch('baseApiUrl')
  onBaseApiUrlChange(url: string) {
    // Immediatelly load records if it is orphan tab.
    if (url && !this.lazyLoad) {
      this.retrieveAllRecords();
    }
  }

  @Watch('parentId')
  onParentIdChange(id: any) {
    if (id) {
      this.retrieveAllRecords();
    }
  }

  @Watch('fields')
  onFieldsChange(fields: Array<IADField>) {
    this.tableDirectReferenceMap.clear();
    this.gridFields = fields.filter(field => field.showInGrid);
  }

  // Start of lifecycle events.
  created() {
    this.onFieldsChange(this.fields);
    this.onBaseApiUrlChange(this.baseApiUrl);
    this.toolbarEventBus?.$on('add-record', this.addBlankRow);
    this.toolbarEventBus?.$on('copy-record', this.copyRow);
    this.toolbarEventBus?.$on('save-record', this.saveRecord);
    this.toolbarEventBus?.$on('cancel-operation', this.cancelOperation);
  }

  beforeDestroy() {
    this.toolbarEventBus?.$off('add-record', this.addBlankRow);
    this.toolbarEventBus?.$off('copy-record', this.copyRow);
    this.toolbarEventBus?.$off('save-record', this.saveRecord);
    this.toolbarEventBus?.$off('cancel-operation', this.cancelOperation);
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
      this.toolbarEventBus.$emit('inline-editing', false);
      this.editing = false;
      if (this.currentRecord !== null) {
        this.$set(this.currentRecord, 'editing', this.editing);
      }
    }

    this.currentRecord = row;
    this.$emit('current-row-change', row);
  }

  public changeRowSelection(rows: Array<any>) {
    this.selectedRows = rows;
    this.$emit('row-selection-change', rows);
  }

  public setTableDirectReference(field: any): void {
    this.activeTableDirectField = field;
  }

  public cancelOperation() {
    // Canceling additional row.
    this.editing = false;

    // New blank row and duplicate row are considered new record.
    if (this.newRecord) {
      const index = this.gridData.indexOf(this.currentRecord);
      this.newRecord = false;
      this.currentRecord = null;
      if (this.gridData.length > 1) {
        (<ElTable>this.$refs.grid).setCurrentRow(this.gridData[index > 0 ? index - 1 : 1]);
      }
      this.$nextTick(() => {
        this.gridData.splice(index, 1);
      })
    } else {
      this.currentRecord.editing = this.editing;
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

    this.prepareFormAndValidation(row, false);
    await this.initRelationships(row);
    row.editing = !row.editing;
    this.editing = row.editing;
    this.toolbarEventBus.$emit('inline-editing', true);
  }

  /**
   * Triggered when user is going to add a new record.
   */
  private async addBlankRow() {
    if (this.mainTab) {
      let record = {
        id: 0,
        editing: true
      };
      this.prepareFormAndValidation(record);
      await this.initRelationships(record);
      this.gridData.splice(0, 0, record);
      this.$nextTick(() => {
        (<ElTable>this.$refs.grid).setCurrentRow(record);
        this.newRecord = true;
        this.editing = record.editing;
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
        (<ElTable>this.$refs.grid).setCurrentRow(record);
        this.newRecord = true;
        this.editing = record.editing;
      });
    }
  }

  private prepareFormAndValidation(row: any, newRecord: boolean = true) {
    for (const field of this.fields) {
      const column = field.adColumn;

      if (newRecord) {
        let defaultValue = column.defaultValue?.match(/\{([\#]?[\w]+)\}/);
        console.log('defaultValue: %s', defaultValue);
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
      this.validationRule[column.name] = {
        type: getValidatorType(column.type),
        required: column.mandatory
      }
    }
  }

  private saveRecord() {
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
          console.log('saving record %O', record);
          for (let field in record) {
            if (this.$refs[field])
              (<any> this.$refs[field][0])?.$el.classList.remove('is-error');
          }
          
          this.isSaving = true;
          if (record.id) {
            this.updateRecord(record);
          } else {
            this.createRecord(record);
          }
          this.newRecord = false;
          this.editing = false;
          this.$set(this.currentRecord, 'editing', this.editing);
          this.validationRule = {};
        }
      });
    }
  }

  private createRecord(record: any) {
    this.dynamicWindowService(this.baseApiUrl)
      .create(record)
      .then(param => {
        this.retrieveAllRecords();
        const message = this.$t('opusWebApp.country.created', { param: param.id });
        this.toolbarEventBus.$emit('record-saved');
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        });
      })
      .catch(()=>{
        const message = "Error, data already exist/ .........";
        this.$notify({
          title: 'Error',
          message: message.toString(),
          type: 'error',
          duration: 3000
        });
      })
      .finally(() => {
        this.isSaving = false;
      });
  }

  private updateRecord(record: any) {
    this.dynamicWindowService(this.baseApiUrl)
      .update(record)
      .then(param => {
        this.retrieveAllRecords();
        const message = this.$t('opusWebApp.country.updated', { param: param.id });
        this.toolbarEventBus.$emit('record-saved');
        this.$notify({
          title: 'Success',
          message: message.toString(),
          type: 'success',
          duration: 3000
        });
      })
      .catch(()=>{
        const message = "Error, data already exist/ .........";
        this.$notify({
          title: 'Error',
          message: message.toString(),
          type: 'error',
          duration: 3000
        });
      })
      .finally(() => {
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
      .then(
        res => {
          this.gridData = res.data.map((item: any) => {
            item.editing = false;
            return item;
          });

          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          if (this.gridData.length) {
            (<ElTable>this.$refs.grid).setCurrentRow(this.gridData[0]);
          }
        },
        err => {
          console.error('Failed getting the record. %O', err);
        }
      )
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

  public isFixed(field: any) {
    if (field.adColumn.name === 'active') {
      return 'right';
    }
    return false;
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
    const reference = column.adReference;
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
