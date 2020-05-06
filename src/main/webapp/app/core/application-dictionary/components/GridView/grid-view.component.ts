import Component from 'vue-class-component';
import { Vue, InjectReactive, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ElPagination } from 'element-ui/types/pagination';
import { ElTable } from 'element-ui/types/table';

const GridViewProps = Vue.extend({
  props: {
    baseApiUrl: String,
    filterQuery: String,
    orderQuery: String,
    fields: {
      type: Array,
      default: () => []
    }
  }
});

@Component
export default class GridView extends GridViewProps {
  @InjectReactive()
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

  get gridFields() {
    return this.fields.filter(field => field.showInGrid)
  }

  @Watch('baseApiUrl')
  onBaseApiUrlChange(url: string) {
    if (url) {
      this.retrieveAllRecords();
    }
  }

  created() {
    this.onBaseApiUrlChange(this.baseApiUrl);
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  changeCurrentRow(row: any) {
    console.log('Current row changed. value: %O', row)
    this.$emit('current-row-change', row)
  }

  changeRowSelection(rows: Array<any>) {
    console.log('Selected rows: %O', rows);
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
          this.gridData = res.data;
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
}