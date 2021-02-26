import { MarketplaceStoreModule as marketplaceStore } from '@/shared/config/store/marketplace-store';
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class ComparisonWindow extends Vue {
  private maxItems = 3;

  infoLabels = [
    'Product',
    'Category',
    'Name',
    'Brand',
    'UoM',
    'Weight',
    'Dimension',
    'Price',
    'Short Description'
  ]

  newSearch: string = null;
  searchCriteria: string[] = [];
  defaultImg: any = null;

  get canAddSlot() {
    return this.items.length < this.maxItems;
  }

  get colSpan() {
    const colCount = this.canAddSlot ? this.items.length + 1 : this.items.length;
    return 24 / colCount;
  }

  get items() {
    return marketplaceStore.comparisonList.map(item => {
      const comparison: any = {...item};
      comparison.info = [
        {content: item.mProduct.name},
        {content: `${item.mProduct.productCategoryName} > ${item.mProduct.productSubCategoryName}`},
        {content: item.name},
        {content: item.mBrandName || '-'},
        {content: item.cUomName || '-'},
        {content: item.weight || '-'},
        {content: this.printDimension(item)},
        {content: item.price, filter: 'currency'},
        {content: item.description.replace('div', 'ul'), html: true}
      ];

      return comparison;
    });
  }

  removeItem(item: IMProductCatalog) {
    marketplaceStore.removeFromCompare(item);
  }

  selectItem(item: IMProductCatalog, index?: number) {
    this.newSearch = null;
    if (index === void 0) {
      marketplaceStore.addToCompare(item);
    } else {
      marketplaceStore.updateComparator({ item, index });
    }
  }

  searchItems(input: string, callback: (list: IMProductCatalog[]) => {}) {
    console.log('Search product:', input);
    const items = marketplaceStore.filterProducts({
      brands: [],
      categories: [],
      name: input,
      price: {}
    });
    console.log('found items', items);
    callback(items.map(item => {
      const mappedItem: any = {...item};
      mappedItem.value = item.name;
      return mappedItem;
    }));
  }

  created() {
    console.log('list:', this.items);
  }

  getCategory(item: IMProductCatalog) {
    const product = item.mProduct;
    const category = product.productCategoryName;
    const subcategory = product.productSubCategoryName;
    return `${category} > ${subcategory}`;
  }

  getImage(item: IMProductCatalog) {
    this.defaultImg = item.cGallery.cGalleryItems
      .find(gi => gi.preview)
      .cAttachment;

    this.defaultImg = `/api/c-attachments/download/${this.defaultImg.id}-${this.defaultImg.fileName}`;

    return this.defaultImg;
  }

  getProductName(item: IMProductCatalog) {
    return item.mProduct.name;
  }

  printDimension(item: IMProductCatalog) {
    if (!item.length && !item.width && !item.height) {
      return '-';
    }

    return `${item.length} x ${item.width} x ${item.height}`
  }
}
