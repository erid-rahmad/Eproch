import { Component, Vue } from 'vue-property-decorator';
import ProductCatalogList from "./product-catalog-list.vue";
import ProductDetail from "./marketplace-detail.vue";
import { IMProductCatalog, MProductCatalog } from '@/shared/model/m-product-catalog.model';

@Component({
  components: {
    'marketplace-catalog': ProductCatalogList,
    'item-detail': ProductDetail
  }
})
export default class MarketplaceMain extends Vue {
  browseCatalog: boolean = true;
  selectedItem: IMProductCatalog = new MProductCatalog();

  showProductDetail(item: IMProductCatalog) {
    this.selectedItem = item;
    this.browseCatalog = false;
  }
}