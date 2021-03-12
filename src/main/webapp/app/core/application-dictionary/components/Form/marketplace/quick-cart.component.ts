import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";
import { TagsViewStoreModule as tagsViewStore } from "@/shared/config/store/tags-view-store";
import { Component, Vue, Watch } from 'vue-property-decorator';
import { ICGallery } from '@/shared/model/c-gallery.model';

@Component
export default class QuickCart extends Vue {

  subtotal: number = 0;
  visible: boolean = false;

  get cartItems() {
    const items: any[] = [];

    for (const group of marketplaceStore.cart) {
      for (const item of group.items) {
        items.push(item);
      }
    }

    return items;
  }

  get hasAccess() {
    return true;
  }

  @Watch('cartItems', { deep: true })
  calculateSubtotal(items: any[]) {
    if (!items.length) {
      this.subtotal = 0;
    } else if (items.length === 1) {
      const item = items[0];
      this.subtotal = item.quantity * item.product.price;
    } else {
      this.subtotal = items.reduce((prev, next) => {
        if (typeof prev === 'number') {
          return prev + (next.quantity * next.product.price)
        }
        return (prev.quantity * prev.product.price) + (next.quantity * next.product.price);
      });
    }
  }

  created() {
    marketplaceStore.initCart();
  }

  removeItem(item: any) {
    marketplaceStore.removeFromCart(item);
  }

  showCart() {
    this.visible = false;
    const route = tagsViewStore.visitedViews.find(view => view.path.includes('marketplace/catalogs'));

    marketplaceStore.setShoppingCartView(true);

    if (this.$route.fullPath === route?.fullPath) {
      return;
    }

    if (route) {
      this.$router.push(route);
    } else {
      this.$router.push({
        path: '/marketplace/catalogs',
        query: {
          t: `${Date.now()}`
        }
      });
    }
  }

  getImg(gallery: ICGallery){
    if (!gallery) {
      return null;
    }
    const img = gallery.cGalleryItems[0].cAttachment;
    return `/api/c-attachments/download/${img.id}-${img.fileName}`;
  }
}
