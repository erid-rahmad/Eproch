import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";

@Component
export default class MarketplaceCart extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {

  checked: boolean = false;

  data = [
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
  ]

  totalCount = "2";
  subTotal = "Rp 11.348.000";

  img = [
    {
      src: "https://static.bmdstatic.com/pk/product/medium/5a6587b8bc0a7.jpg"
    }
  ]

  created() {
    console.log("tes")
  }

  handleChangeQty(value) {
    console.log(value)
  }

  continueShopping(){
    console.log("tes continue shopping")
  }

  getDetail(){
    this.$emit("get-detail");
  }

};

