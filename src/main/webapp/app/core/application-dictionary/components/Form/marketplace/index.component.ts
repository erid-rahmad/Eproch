import { Component, Vue } from 'vue-property-decorator';
import ProductCatalogList from "./product-catalog-list.vue";
import ProductDetail from "./marketplace-detail.vue";
import { IMProductCatalog, MProductCatalog } from '@/shared/model/m-product-catalog.model';
import ShoppingCart from "./shopping-cart.vue";
import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";

export enum View {
  Catalog = 1,
  Detail = 2,
  Cart = 3,
}

@Component({
  components: {
    'marketplace-catalog': ProductCatalogList,
    ProductDetail,
    ShoppingCart
  }
})
export default class MarketplaceMain extends Vue {
  private activeView: View = View.Catalog;
  origin: View = View.Catalog;
  selectedItem: IMProductCatalog = new MProductCatalog();

  get isProductCatalogView() {
    return !this.isShoppingCart && this.activeView === View.Catalog;
  }

  get isProductDetailView() {
    return !this.isShoppingCart && this.activeView === View.Detail;
  }

  get isShoppingCart() {
    return marketplaceStore.isShoppingCart;
  }

  created() {
    console.log('query string:', this.$route.query);
  }

  onProductDetailClosed(origin: View) {
    this.activeView = origin;
  }

  onShoppingCartClosed() {
    this.activeView = View.Catalog;
  }

  showProductDetail({item, origin}) {
    this.origin = origin;
    this.selectedItem = item;
    this.activeView = View.Detail;
  }
}