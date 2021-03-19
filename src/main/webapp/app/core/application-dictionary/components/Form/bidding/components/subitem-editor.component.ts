import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';

const SubitemEditorProp = Vue.extend({
  props: {
    itemIndex: Number,
    itemDetail: {
      type: Object,
      default: () => {}
    },
  }
});

@Component
export default class SubitemEditor extends SubitemEditorProp {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private adOrganizationId: number;

  loadingProducts = false;

  mainForm = {
    id: null,
    totalAmount: 0,
    productId: null,
    mBiddingSubItemLines: []
  }

  validationSchema = {
    productId: {
      required: true,
      message: 'Sub Item is required'
    }
  }

  gridSchema = {
    emptyText: 'No lines in this sub item',
    maxHeight: 256
  };

  private organizationCriteria = [
    'adOrganizationId.in=1'
  ];

  productOptions = [];
  uomOptions: any[] = [];

  /**
   * Action to be performed upon quantity change event.
   * Update the quantityBalance by subtracting quantity and quantityOrdered.
   * @param row Current row being edited.
   * @param value The quantity.
   */
  onQuantityChange(row: any, quantity: number) {
    console.log('qty changed. row: %O, qty: %s', row, quantity);
    row.amount = quantity * row.price;
    this.calculateTotalAmount(row);
  }

  /**
   * Action to be performed upon price change event.
   * Update the quantityBalance by subtracting quantity and quantityOrdered.
   * @param row Current row being edited.
   * @param value The price.
   */
  onPriceChange(row: any, price: string) {
    row.price = parseFloat(price);
    row.amount = row.price * row.quantity;
    this.calculateTotalAmount(row);
  }

  created() {
    console.log('subitem form created. line: %O, index: %s', this.itemDetail, this.itemIndex);
    this.adOrganizationId = accountStore.organizationInfo.id;
    this.organizationCriteria.push(`adOrganizationId.in=${this.adOrganizationId}`);
    this.retrieveUom();
    this.searchProduct();
  }

  beforeDestroy() {
    console.log('About to destroy subitem form');
  }

  private calculateTotalAmount(row: any) {
    let totalAmount: number;

    if (this.mainForm.mBiddingSubItemLines.length > 1) {
      totalAmount = this.mainForm.mBiddingSubItemLines.reduce((prev, next) => {
        console.log('prev: %O, next: %O', prev, next);
        if (typeof prev === 'number') {
          return prev + next.amount
        }
        return prev.amount + next.amount;
      });
    } else {
      totalAmount = row.amount;
    }

    console.log('totalAmount:', totalAmount);
    this.$set(this.mainForm, 'totalAmount', totalAmount);
  }

  private retrieveUom() {
    this.commonService('/api/c-unit-of-measures')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          ...this.organizationCriteria
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['name']
        }
      })
      .then(res => {
        this.uomOptions = res.data;
      });
  }

  addSubItem() {
    this.mainForm.mBiddingSubItemLines.push({
      adOrganizationId: this.adOrganizationId,
      quantity: 1,
      price: 0,
      amount: 0,
      productId: null,
      uomId: null
    });
  }

  removeSubItem(index: number) {
    this.mainForm.mBiddingSubItemLines.splice(index, 1);
  }

  save() {
    if (!this.mainForm.mBiddingSubItemLines.length) {
      this.$message.error('Please add at lease one sub-item line');
      this.$emit('error');
      return;
    }

    console.log('validating mainForm: %O', this.mainForm);
    (<ElForm>this.$refs.mainForm).validate((passed, errors) => {
      if (passed) {
        console.log('Save sub-item');
        this.mainForm.id = 1;
        this.$emit('saved', {
          itemIndex: this.itemIndex,
          subItem: this.mainForm
        });
      } else {
        console.log('Error saving sub-item. %O', errors);
        this.$emit('error');
      }
    })
  }

  searchProduct(key?: string) {
    const criteriaQuery = [
      'active.equals=true',
      ...this.organizationCriteria
    ];

    if (!!key) {
      criteriaQuery.push(`name.contains=${key}`);
    }

    this.loadingProducts = true;
    this.commonService('/api/c-products')
      .retrieve({
        criteriaQuery,
        paginationQuery: {
          page: 0,
          size: 20,
          sort: ['name']
        }
      })
      .then(res => {
        this.productOptions = res.data;
      })
      .catch(err => {
        console.log('Load product failed. %O', err);
        this.$message.error('Failed to load the product list');
      })
      .finally(() => {
        this.loadingProducts = false;
      });
  }

}
