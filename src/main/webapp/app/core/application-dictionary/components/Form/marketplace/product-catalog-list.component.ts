import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Component, Inject, Vue } from 'vue-property-decorator';
import QuickFilter from "./quick-filter.vue";
import QuickSearch from "./quick-search.vue";
import ShoppingCart from "./shopping-cart.vue";

interface IMProductCatalog {
  id?: number;
  name?: string;
  description?: string;
  shortDescription?: string;
  height?: number;
  length?: number;
  width?: number;
  weight?: number;
  price?: number;
  thumbnailSmall?: string;
}

@Component({
  components: {
    QuickFilter,
    QuickSearch,
    ShoppingCart
  }
})
export default class ProductCatalogList extends Vue {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private catalogApi = '/api/m-product-catalogs/marketplace';
  private data: IMProductCatalog[] = [];

  page = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;
  processing = false;
  rows: IMProductCatalog[] = [];

  created() {
    this.retrieveProductCatalog(this.buildLayout);
  }

  onQuickSearchCompleted(query) {

  }

  onShoppingCartCanceled() {}

  onShoppingCartProcessed() {}



  private buildLayout(items: IMProductCatalog[]) {
    const maxCols = 4;
    const rows = [];
    let columns: IMProductCatalog[] = [];
    let colNo = 1;
    for (let item of items) {
      const newRow = colNo === 1 || colNo > maxCols;

      if (newRow) {
        colNo = 1;
        columns = [];
        rows.push({
          columns: columns
        });
      }

      columns.push(item);
      ++colNo;
    }

    this.rows = rows;
  }

  private retrieveProductCatalog(callback?: (items: IMProductCatalog[]) => void) {
    this.processing = true;
    this.commonService(this.catalogApi)
      .retrieve({
        criteriaQuery: [],
        paginationQuery: {
          page: this.page - 1,
          size: 10,
          sort: [
            'name'
          ]
        }
      })
      .then(res => {
        this.data = res.data;
        this.totalItems = Number(res.headers['x-total-count']);
        callback && callback(this.data);
      })
      .catch(err => {
        console.error('Failed getting the product catalog. %O', err);
        this.$message({
          type: 'error',
          message: err.detail || err.message
        });
      })
      .finally(() => {
        this.processing = false;
      });
  }
}