import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Component, Inject, Vue } from 'vue-property-decorator';
import FilterGroup from "./filter-group.vue";
import QuickSearch from "./quick-search.vue";
import ShoppingCart from "./shopping-cart.vue";
import { mapActions } from 'vuex';

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
  cGallery?: ICGallery;
}

interface ICGallery {
  cGalleryItems?: ICGalleryItem[];
}

interface ICGalleryItem {
  cAttachment?: ICAttachment;
  sequence?: number;
  preview?: boolean;
}

interface ICAttachment {
  fileName: string;
  imageSmall: string;
  imageMedium: string;
  imageLarge: string;
}

@Component({
  components: {
    FilterGroup,
    QuickSearch,
    ShoppingCart
  },

  methods: {
    ...mapActions({
      removeWindowState: 'windowStore/removeWindow'
    })
  }
})
export default class ProductCatalogList extends Vue {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private removeWindowState!: (path: string) => Promise<void>;

  private catalogApi = '/api/m-product-catalogs/marketplace';
  private data: IMProductCatalog[] = [];
  private unwatchStore: Function;
  private fullPath: string = '';

  page = 1;
  propOrder = 'id';
  reverse = false;
  totalItems = 0;
  processing = false;
  rows: IMProductCatalog[] = [];
  sortableFields: Record<string, string>[] = [
    { name: 'lastModifiedDate,desc', label: 'Newest' },
    { name: 'price,asc', label: 'Lowest Price' },
    { name: 'price,desc', label: 'Highest Price' }
  ];

  created() {
    this.fullPath = this.$route.fullPath;
    this.retrieveProductCatalog(this.buildLayout);
    this.unwatchStore = this.$store.watch(
      (state) => state.tagsViewStore.deletedViews,
      (views: string) => {
        if (views.includes(this.fullPath)) {
          this.$destroy();
        }
      }
    );
  }
  
  beforeDestroy() {
    this.removeWindowState(this.fullPath);
    this.unwatchStore();
  }

  onFilterCategoryChanged(query: string) {
    console.log('Category query:', query);
  }

  onFilterBrandChanged(query: string) {
    console.log('Brand query:', query);
  }
  onFilterPriceChanged() {}

  onQuickSearchCompleted(query) {}

  onShoppingCartCanceled() {}

  onShoppingCartProcessed() {}

  getThumbnailPreview(catalog: IMProductCatalog) {
    const gallery = catalog.cGallery;
    for (const item of gallery.cGalleryItems) {
      if (item.preview) {
        return item.cAttachment.imageMedium;
      }
    }
  }

  private buildLayout(items: IMProductCatalog[]) {
    const maxCols = 6;
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