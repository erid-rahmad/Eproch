import Component from 'vue-class-component';
import { Vue, Watch, Inject } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ElTable } from 'element-ui/types/table';
import { IADField } from '@/shared/model/ad-field.model';
import { ADColumnType } from '@/shared/model/ad-column.model';
import { ActionToolbarEventBus } from '../ActionToolbar/action-toolbar-event-bus';

const GridViewProps = Vue.extend({
  props: {
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
      default: () => []
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
    this.gridFields = fields.filter(field => field.showInGrid);
  }

  created() {
    this.onFieldsChange(this.fields);
    this.onBaseApiUrlChange(this.baseApiUrl);
    ActionToolbarEventBus.$on('add-record', this.addBlankRow);
  }

  beforeDestroy() {
    ActionToolbarEventBus.$off('add-record', this.addBlankRow);
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  changeCurrentRow(row: any) {
    this.$emit('current-row-change', row);
  }

  changeRowSelection(rows: Array<any>) {
    this.$emit('row-selection-change', rows);
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
            this.$set(item, 'editing', false);
            return item;
          });

          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
          if (this.gridData.length) {
            let row = this.gridData[0];
            (<ElTable>this.$refs.grid).setCurrentRow(row);
          }
        },
        err => {
          this.isFetching = false;
        }
      );
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

  public activateInlineEditing(row: any) {
    row.editing = !row.editing;
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

  public getFieldValue(row: any, prop: string) {
    return row[prop];
  }

  public isActiveStatusField(column: any) {
    return column.property === 'active';
  }

  private addBlankRow() {
    console.log('gridFields: %O', this.gridFields);
    /* for (const field of this.gridFields) {
      console.log('prop: %s', field.adColumn.name);
    } */
  }
}
