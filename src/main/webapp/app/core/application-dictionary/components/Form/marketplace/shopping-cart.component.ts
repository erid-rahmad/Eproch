import { MarketplaceStoreModule as marketplaceStore } from "@/shared/config/store/marketplace-store";
import { ICGallery } from '@/shared/model/c-gallery.model';
import { IMProductCatalog } from '@/shared/model/m-product-catalog.model';
import { ElForm } from 'element-ui/types/form';
import { Component, Vue, Watch, Inject } from 'vue-property-decorator';
import { View } from './index.component';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

@Component
export default class ShoppingCart extends Vue {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  checked = false;
  subtotal = 0;
  createPrConfirmationVisible = false;

  costCenterOptions = [];
  currencyOptions = [];
  warehouseOptions = [];

  purchaseRequisitionForm = {
    costCenterId: null,
    currencyId: null,
    documentAction: 'DRF',
    documentStatus: 'DRF',
    warehouseId: null,
    mRequisitionLines: []
  };

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
        if (typeof prev === 'number') {
          return prev + (next.quantity * next.product.price)
        }
        return (prev.quantity * prev.product.price) + (next.quantity * next.product.price);
      });
    }
  }

  created() {
    this.retrieveOptions('/api/c-cost-centers', 'costCenterOptions');
    this.retrieveOptions('/api/c-currencies', 'currencyOptions');
    this.retrieveOptions('/api/c-warehouses', 'warehouseOptions');
  }

  handleChangeQty(value) {
    console.log(value)
  }

  continueShopping() {
    marketplaceStore.setShoppingCartView(false);
    this.$emit('closed');
  }

  createPurchaseRequisition() {
    (<ElForm>this.$refs.purchaseRequisitionForm).validate((passed, error) => {
      if (passed) {
        this.buildPurchaseRequisitionData();
        this.commonService('/api/m-requisitions')
          .create(this.purchaseRequisitionForm)
          .then(res => {
            this.$message.success(`Document #${res.documentNo} has been created successfully`);
            marketplaceStore.clearCart();
            this.createPrConfirmationVisible = false;
          })
          .catch(err => {
            console.log('Failed creating purchase request. %O', err);
            this.$message.error('Failed creating the purchase request');
          });
      } else {
        console.log('error. %O', error);
        this.$message.error('Failed to create the purchase requisition document');
      }
    })
    
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

  getImg(gallery: ICGallery){
    if (!gallery) {
      return null;
    }
    const img = gallery.cGalleryItems[0].cAttachment;
    return `/api/c-attachments/download/${img.id}-${img.fileName}`;
  }

  private buildPurchaseRequisitionData() {
    const items = [];

    for (const group of this.cart) {
      const vendorId = group.vendorId;

      for (const item of group.items) {
        items.push({
          vendorId,
          quantity: item.quantity,
          quantityBalance: item.quantity,
          quantityOrdered: 0,
          unitPrice: item.product.price,
          requisitionAmount: item.quantity * item.product.price,
          remark: item.note,
          productId: item.product.mProductId,
          uomId: item.product.cUomId
        });
      }
    }

    this.purchaseRequisitionForm.documentAction = 'DRF';
    this.purchaseRequisitionForm.documentAction = 'DRF';
    this.purchaseRequisitionForm.mRequisitionLines = items;
  }

  private retrieveOptions(url: string, model: string) {
    this.commonService(url)
      .retrieve({
        criteriaQuery: [
          'active.equals=true'
        ],
        paginationQuery: {
          page: 0,
          size: 100,
          sort: ['name']
        }
      })
      .then(res => {
        this[model] = res.data.map(item => {
          return {
            key: item.id,
            label: item.code || item.name
          };
        });
      });
  }

}
