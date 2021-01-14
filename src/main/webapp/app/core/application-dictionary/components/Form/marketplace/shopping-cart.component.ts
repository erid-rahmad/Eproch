import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { Component, Vue } from 'vue-property-decorator';
import ProductDetail from "./marketplace-detail.vue";

const ShoppingCartProps = Vue.extend({
  props: {
    fields: {
      type: Array,
      default: () => {
        return [];
      }
    }
  }
})

@Component({
  components: {
    'item-detail': ProductDetail
  }
})
export default class ShoppingCart extends ShoppingCartProps {

  checked: boolean = false;
  showDetail: boolean = false;
  selectedItem: IMProductCatalog = {};

  /* data = [
    {
      storeId: 1,
      store: "Bhinneka",
      item: [
        {
          itemId: 1,
          img: "https://static.bmdstatic.com/pk/product/medium/5f59da3f28c6a.jpg",
          name: "FITBIT Sense",
          price: "Rp 5.899.000",
          specialPrice: "",
          totalAmount: "Rp 5.899.000",
          varian: "Color Carbon/Graphite",
          qty: "12"
        }, {
          itemId: 2,
          img: "https://static.bmdstatic.com/pk/product/medium/BROTHER-Printer-HL-L6200DW--SKU07016127-201684103149.jpg",
          name: "BROTHER Printer HL-L6200DW",
          price: "Rp 5.449.000",
          totalAmount: "Rp 5.449.000",
          varian: "",
          qty: "3"
        }
      ]
    },
  ] */

  img = [
    {
      src: "https://static.bmdstatic.com/pk/product/medium/5a6587b8bc0a7.jpg"
    }
  ]

  get cart() {
    console.log('get cart data', marketplaceStore.cart);
    return marketplaceStore.cart;
  }

  get grandTotal() {
    return marketplaceStore.cartGrandTotal;
  }

  get totalCount() {
    return marketplaceStore.cartItemCount;
  }
  
  handleChangeQty(value) {
    console.log(value)
  }

  continueShopping() {
    console.log("tes continue shopping")
  }

  getDetail() {
    this.$emit("get-detail");
  }

}