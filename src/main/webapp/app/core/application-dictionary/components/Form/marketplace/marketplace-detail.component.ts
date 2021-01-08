import AlertMixin from '@/shared/alert/alert.mixin';
import { mixins } from 'vue-class-component';
import { Component } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import ContextVariableAccessor from "../../ContextVariableAccessor";
import DetailDescription from './components/detail-description.vue';
import MarketplaceCart from './marketplace-cart.vue';

@Component({
  components: {
    DetailDescription,
    MarketplaceCart
  }
})
export default class Marketplace extends mixins(Vue2Filters.mixin, AlertMixin, ContextVariableAccessor) {

  page: string = "";

  name = "BENQ Projector  MW612";
  sku = 3319807056;
  rate = 3.7;
  price = "Rp 14.080.000";
  cicilan = "Mulai dari Rp 616.246/bulan.";
  estimate = "Produk Inden";
  storeInformation = "Dijual dan dikirim oleh Bhinneka, DKI Jakarta";
  qty = 1;
  availableStock = 39;
  totalPrice = "Rp 14.080.000";
  warranty = "3 Years Local Official Distributor Warranty";

  defaultImg = 'https://static.bmdstatic.com/pk/product/medium/5a6587b8bc0a7.jpg';
  imgListsPreview = [];
  imgLists = [
    {
      id: "2",
      src: 'https://static.bmdstatic.com/pk/product/medium/5a6587b8bc0a7.jpg',
      alt: "asd123"
    }, {
      id: "3",
      src: 'https://static.bmdstatic.com/pk/product/medium/SANWA-Infrared-Thermometer-IR60i--3318112175-2017719143915.jpg',
      alt: "asd123"
    }, {
      id: "4",
      src: 'https://static.bmdstatic.com/pk/product/medium/5e65a8c02fc4e.jpg',
      alt: "asd123"
    }, {
      id: "5",
      src: 'https://static.bmdstatic.com/pk/product/medium/5f64736994cec.jpg',
      alt: "asd123"
    }
  ]

  private activeName = 'desc';
  private tabTitleOptions = [
    { name: 'Description', value: 'desc' },
    { name: 'Product excellence', value: 'excl' },
    { name: 'Specification', value: 'spec' },
  ]

  handleTabClick(tab, event) {
    this.activeName = tab.name;
  }

  created() {
    this.page = "detail";
    for(var i=0; i < this.imgLists.length; i++){
      this.imgListsPreview.push(this.imgLists[i].src);
    }
  }

/*  clickImageThumbnail(img: string){
    console.log(img);
    this.defaultImg = img;
  }*/

  clickImageThumbnail(src) {
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

  handleChangeQty(value) {
    console.log(value)
  }

  addToCart(){
    this.page = "cart";
  }

  setDetail(){
    this.page = "detail";
  }
}
