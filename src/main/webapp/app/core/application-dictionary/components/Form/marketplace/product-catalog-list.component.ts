import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Component, Inject, Vue } from 'vue-property-decorator';
import { mapActions } from 'vuex';
import FilterGroup from "./filter-group.vue";
import QuickSearch from "./quick-search.vue";

@Component({
  components: {
    FilterGroup,
    QuickSearch
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
  sortableFields: Record<string, string>[] = [
    { name: 'lastModifiedDate,desc', label: 'Newest' },
    { name: 'price,asc', label: 'Lowest Price' },
    { name: 'price,desc', label: 'Highest Price' }
  ];

  get rows() {
    return this.buildLayout(marketplaceStore.products);
  }

  created() {
    this.fullPath = this.$route.fullPath;
    this.retrieveProductCatalog();
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

  onItemSelected(item: IMProductCatalog) {
    this.$emit('item-selected', item);
  }

  onShoppingCartCanceled() {}

  onShoppingCartProcessed() {}

  getThumbnailPreview(catalog: IMProductCatalog) {
    const gallery = catalog.cGallery;
    for (const item of gallery.cGalleryItems) {
      if (item.preview) {
        return item.cAttachment.imageMedium;
      }
    }

    return gallery.cGalleryItems[0]?.cAttachment?.imageMedium;
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

    return rows;
  }

  private retrieveProductCatalog() {
    this.processing = true;

    marketplaceStore.initCatalog()
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