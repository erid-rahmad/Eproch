import AlertMixin from '@/shared/alert/alert.mixin';
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { mixins } from 'vue-class-component';
import { Vue, Component, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import DetailDescription from './components/detail-description.vue';
import { MarketplaceStoreModule as marketplaceStore } from '@/shared/config/store/marketplace-store';
import { CGalleryItem } from '@/shared/model/c-gallery-item.model';

const ProductDetailProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component({
  components: {
    DetailDescription
  }
})
export default class ProductDetail extends ProductDetailProps {

  sku = 3319807056;
  rate = 3.7;
  cicilan = "Mulai dari Rp 616.246/bulan.";
  estimate = "Produk Inden";
  storeInformation = "Dijual dan dikirim oleh Bhinneka, DKI Jakarta";
  qty = 1;
  availableStock = 39;
  totalPrice = 0;
  warranty = "3 Years Local Official Distributor Warranty";

  defaultImg: string = null;
  imgListsPreview: string[] = [];

  private galleryItems: CGalleryItem[] = [];
  private activeName = 'desc';
  private tabTitleOptions = [
    { name: 'Description', value: 'desc' },
    { name: 'Product excellence', value: 'excl' },
    { name: 'Specification', value: 'spec' },
  ]

  @Watch('data')
  onDataChanged(data: IMProductCatalog) {
    this.galleryItems = data.cGallery.cGalleryItems;

    this.totalPrice = data.price;

    console.log('data:', data);
    this.defaultImg = this.galleryItems
      .find(item => item.preview)
      .cAttachment.imageLarge;

    this.imgListsPreview = this.galleryItems
      .filter(item => !item.preview)
      .map(item => item.cAttachment.imageSmall);
  }

  handleTabClick(tab, event) {
    this.activeName = tab.name;
  }

  clickImageThumbnail(src: string) {
    this.defaultImg = src;

    let newList = []; // define a new array
    let i = 0; // i is used to take the subscript of the array corresponding to the picture clicked by the user
    let length = this.imgListsPreview.length; // The length of the original array
    // One traversal of some assigns the subscript corresponding to the current url to i
    this.imgListsPreview.some((item, index) => {
      console.log(item)
      console.log(index)
      if (src === item) {
        newList.push(item); // At the same time do the first push operation (url of the picture currently clicked by the user)
        i = index;
      }
    })
    newList.push(...this.imgListsPreview.slice(i + 1, length)); // The url of the picture whose order is after the current picture
    newList.push(...this.imgListsPreview.slice(0, i)); // Press the url of the picture before the current picture
    this.imgListsPreview = newList; // Re-assign
    console.log(this.imgListsPreview);
  }

  handleChangeQty(qty: number) {
    this.totalPrice = this.data.price * qty;
  }

  addToCart() {
    const product = {...this.data};

    marketplaceStore.addToCart({
      product,
      qty: this.qty
    })
  }

  setDetail(){
  }
}
