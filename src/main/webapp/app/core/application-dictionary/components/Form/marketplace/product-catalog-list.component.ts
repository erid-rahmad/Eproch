import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { IFilterCriteria, MarketplaceStoreModule as marketplaceStore } from '@/shared/config/store/marketplace-store';
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Component, Inject, Vue } from 'vue-property-decorator';
import { mapActions } from 'vuex';
import FilterGroup from './filter-group.vue';
import { View } from './index.component';

@Component({
  components: {
    FilterGroup
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

  private filteredBrands: string[] = [];
  private filteredCategories: Record<string, any> = {};
  private filteredPriceRange: Record<string, number> = {};

  brands = [];
  products = [];

  page = 1;
  propOrder = null;
  reverse = false;
  totalItems = 0;
  processing = false;

  filterCriteria: IFilterCriteria = {
    brands: [],
    categories: [],
    name: null,
    price: {}
  };

  sortableFields: Record<string, string>[] = [
    { name: 'newest', label: 'Newest' },
    { name: 'lowestPrice', label: 'Lowest Price' },
    { name: 'highestPrice', label: 'Highest Price' }
  ];

  get categories() {
    return [{
      label: 'Furniture',
      children: [{
        label: 'Office & Commercial Furniture',
        children: [{
          label: 'Office Chair'
        }]
      }]
    }, {
      label: 'Health, Medicine & Labs',
      children: [{
        label: 'Lab Instruments & Supplies',
        children: [{
          label: 'Lab Tools & Accessories'
        }]
      }]
    }, {
      label: 'Office & Stationary',
      children: [{
        label: 'Stationary',
        children: [{
          label: 'Alat Tulis'
        }]
      }, {
        label: 'Printer & Scanner',
        children: [{
          label: 'Printer Bisnis'
        }]
      }]
    }, {
      label: 'Tools & Measurement',
      children: [{
        label: 'Weight Measuring Instruments',
        children: [{
          label: 'Digital Scale'
        }]
      }, {
        label: 'Environmental Quality Measuring Instruments',
        children: [{
          label: 'Tachometer'
        }]
      }, {
        label: 'Hand Tools',
        children: [{
          label: 'Forming tools'
        }, {
          label: 'Holding & Clamping tools'
        }]
      }]
    }, {
      label: 'Safety & Security Equipments',
      children: [{
        label: 'Home Security',
        children: [{
          label: 'Akses Door'
        }]
      }]
    }, {
      label: 'Transportation And Automative',
      children: [{
        label: 'Vehicle Parts & Systems',
        children: [{
          label: 'Tires and Tire Tubes'
        }, {
          label: 'Seal'
        }]
      }]
    }];
  }

  get rows() {
    return this.buildLayout(this.products);
  }

  created() {
    this.retrieveBrands();
    this.retrieveAllProducts();
    this.fullPath = this.$route.fullPath;
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

  onFilterBrandChanged(query: string[]) {
    this.filterCriteria.brands = query;
  }

  onFilterCategoryChanged(query: any[]) {
    this.filterCriteria.categories = query.map(q => q.label);
  }

  onFilterPriceChanged({min, max}) {
    this.filterCriteria.price.min = min;
    this.filterCriteria.price.max = max;
  }

  onItemSelected(item: IMProductCatalog) {
    this.$emit('item-selected', {
      item,
      origin: View.Catalog
    });
  }

  onShoppingCartCanceled() {}

  onShoppingCartProcessed() {}

  onSorted(value: string) {
    if (value) {
      marketplaceStore.applySorting(value);
    }
  }

  applyFilter() {
    marketplaceStore.applyCatalogFilter(this.filterCriteria);
  }

  getThumbnailPreview(catalog: IMProductCatalog) {
    const gallery = catalog.cGallery;
    for (const item of gallery.cGalleryItems) {
      if (item.preview) {
        return `/api/c-attachments/download/${item.cAttachment.id}-${item.cAttachment.fileName}`;
      }
    }

    return `/api/c-attachments/download/${gallery.cGalleryItems[0]?.cAttachment?.id}-${gallery.cGalleryItems[0]?.cAttachment?.fileName}`;
  }

  private buildLayout(items: IMProductCatalog[]) {
    // const maxCols = 6;
    const rows = [];
    let columns: IMProductCatalog[] = [];
    let colNo = 1;
    for (let item of items) {
      const newRow = colNo === 1;

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

  retrieveBrands(): void {
    this.commonService('/api/m-brands')
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['name']
        }
      })
      .then(res => {
        this.brands = res.data.map((brand: any) => brand.name);
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message.error(err.detail || err.message);
      });
  }

  retrieveAllProducts(): void {
    this.commonService('/api/m-product-catalogs')
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ]
      })
      .then(res => {
          this.products = res.data;
      })
      .catch(err => {
        console.error('Failed getting the record. %O', err);
        this.$message.error(err.detail || err.message);
      });
  }
}
