import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';
import { ElForm } from 'element-ui/types/form';
import Vue from 'vue';
import Component from 'vue-class-component';
import { Inject, Mixins, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import { ElSelect } from 'element-ui/types/select';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';

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
export default class SubitemEditor extends Mixins(AccessLevelMixin, SubitemEditorProp) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingProducts = false;
  loadingLines = false;

  mainForm: Record<string, any>;

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

  productOptions = [];
  uomOptions: any[] = [];

  @Watch('itemDetail.subItem')
  onItemDetailChanged(subItem: any) {
    console.log('subItem');
    if (subItem) {
      this.mainForm = subItem;
      this.retrieveLines(this.mainForm.id);
    } else {
      this.mainForm = {
        id: null,
        adOrganizationId: null,
        totalAmount: 0,
        productId: null,
        mBiddingSubItemLines: []
      };
    }
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
    this.calculateTotalAmount();
  }

  /**
   * Action to be performed upon quantity change event.
   * Update the quantityBalance by subtracting quantity and quantityOrdered.
   * @param row Current row being edited.
   * @param value The quantity.
   */
  onQuantityChange(row: any, quantity: number) {
    row.amount = quantity * row.price;
    this.calculateTotalAmount();
  }

  created() {
    this.onItemDetailChanged(this.itemDetail.subItem);

    if (!this.itemDetail.subItem) {
      this.mainForm.adOrganizationId = this.adOrganizationId;
    }

    this.retrieveUom();
    this.searchProduct();
  }

  private calculateTotalAmount() {
    let totalAmount = this.mainForm.mBiddingSubItemLines
      .map((line: any): number => line.amount || 0)
      .reduce((prev, next) => prev + next, 0);

    this.$set(this.mainForm, 'totalAmount', totalAmount);
  }

  private retrieveLines(subItemId: number) {
    this.loadingLines = true;
    this.commonService('/api/m-bidding-sub-item-lines')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `biddingSubItemId.equals=${subItemId}`
        ]),
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['id']
        }
      })
      .then(res => {
        this.$set(this.mainForm, 'mBiddingSubItemLines', res.data);
      })
      .catch(err => {
        console.log('Failed to get sub-item lines. %O', err);
        this.$message.error('Failed to get sub-item lines');
      })
      .finally(() => {
        this.loadingLines = false;
      })
  }

  private retrieveUom() {
    this.commonService('/api/c-unit-of-measures')
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true'
        ]),
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

  reset() {
    (<ElForm>this.$refs.mainForm).resetFields();
    (<ElSelect>this.$refs.subItemProduct).focus();
  }

  save() {
    if (!this.mainForm.mBiddingSubItemLines.length) {
      this.$message.error('Please add at lease one sub-item line');
      this.$emit('error');
      return;
    }

    (<ElForm>this.$refs.mainForm).validate((passed, errors) => {
      if (passed) {
        this.$emit('saved', {
          itemIndex: this.itemIndex,
          subItem: {...this.mainForm}
        });
      } else {
        console.log('Error saving sub-item. %O', errors);
        this.$emit('error');
      }
    })
  }

  searchProduct(key?: string) {
    const criteriaQuery = this.updateCriteria([
      'active.equals=true'
    ]);

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
