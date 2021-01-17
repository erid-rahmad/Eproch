import { MarketplaceStoreModule as marketplaceStore } from '@/shared/config/store/marketplace-store';
import { CGalleryItem } from '@/shared/model/c-gallery-item.model';
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Component, Vue, Watch } from 'vue-property-decorator';
import DetailDescription from './components/detail-description.vue';
import { View } from './index.component';

const ProductDetailProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },

    /**
     * Either catalog or cart.
     */
    origin: Number
  }
})

@Component({
  components: {
    DetailDescription
  }
})
export default class ProductDetail extends ProductDetailProps {

  rate = 0;
  storeInformation = "Dijual dan dikirim oleh Bhinneka, DKI Jakarta";
  qty = 1;
  totalPrice = 0;
  warranty = "3 Years Local Official Distributor Warranty";

  defaultImg: string = null;
  imgListsPreview: string[] = [];
  activeTab = 'desc';
  tabs = [];

  private galleryItems: CGalleryItem[] = [];

  get isPreOrder() {
    return this.shipmentEstimation === 'Pre Order';
  }

  get shipmentEstimation() {
    const duration = this.data.preOrderDuration;
    return duration < 7 ? `Shipping within ${duration} - 5 days` : 'Pre Order';
  }

  @Watch('data')
  onDataChanged(data: IMProductCatalog) {
    this.tabs = [{
      id: 'desc',
      name: 'Description',
      content: data.description.replace('div', 'ul')
    }];

    this.galleryItems = data.cGallery.cGalleryItems;
    this.qty = 1;
    this.totalPrice = data.price;

    console.log('data:', data);
    this.defaultImg = this.galleryItems
      .find(item => item.preview)
      .cAttachment.imageLarge;

    this.imgListsPreview = this.galleryItems
      .filter(item => !item.preview)
      .map(item => item.cAttachment.imageSmall);
  }

  handleTabClick(tab) {
    this.activeTab = tab.name;
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
      quantity: this.qty
    })
  }

  goToPreviousPage() {
    if (this.origin === View.Cart) {
      marketplaceStore.setShoppingCartView(true);
    }
    this.$emit('closed', this.origin);
  }
}
