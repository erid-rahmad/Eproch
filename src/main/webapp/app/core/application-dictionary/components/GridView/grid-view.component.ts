import Component from 'vue-class-component';
import { Vue, InjectReactive } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ElPagination } from 'element-ui/types/pagination';
import { ElTable } from 'element-ui/types/table';

const GridViewProps = Vue.extend({
  props: {
    baseApiUrl: String,
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

  created() {
    this.retrieveAllRecords();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRecords();
  }

  changeSelection(value: any) {
    console.log('Row selection changed. value: %O', value)
  }

  public retrieveAllRecords(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()
    };
    this.dynamicWindowService(this.baseApiUrl)
      .retrieve(paginationQuery)
      .then(
        res => {
          this.gridData = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
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
    const pagination = (<ElPagination>this.$refs.pagination).$el;
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