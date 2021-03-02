import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Component, Vue, Watch } from 'vue-property-decorator';
import { View } from './index.component';

@Component
export default class ShoppingCart extends Vue {

  checked: boolean = false;
  subtotal: number = 0;

  get cart() {
    return marketplaceStore.cart;
  }

  get grandTotal() {
    return marketplaceStore.cartGrandTotal;
  }

  get totalCount() {
    return marketplaceStore.cartItemCount;
  }

  @Watch('cart', { deep: true })
  calculateSubtotal(cart: any[]) {
    const items: any[] = [];

    for (const group of cart) {
      for (const item of group.items) {
        items.push(item);
      }
    }

    if (!items.length) {
      this.subtotal = 0;
    } else if (items.length === 1) {
      const item = items[0];
      this.subtotal = item.quantity * item.product.price;
    } else {
      this.subtotal = items.reduce((prev, next) => {
        console.log('next:', next);
        if (typeof prev === 'number') {
          return prev + (next.quantity * next.product.price)
        }
        return (prev.quantity * prev.product.price) + (next.quantity * next.product.price);
      });
    }
  }

  handleChangeQty(value) {
    console.log(value)
  }

  continueShopping() {
    marketplaceStore.setShoppingCartView(false);
    this.$emit('closed');
  }

  getDetail(productCatalog: IMProductCatalog) {
    marketplaceStore.setShoppingCartView(false);
    this.$emit('item-selected', {
      item: productCatalog,
      origin: View.Cart
    });
  }

  removeAll() {
    marketplaceStore.clearCart();
  }

  removeItem(item: any) {
    marketplaceStore.removeFromCart(item);
  }

  getImg(img){
    return `/api/c-attachments/download/${img.id}-${img.fileName}`;
  }

}
