import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import Schema from "async-validator";

const baseApiAuctionItem = 'api/m-auction-items';
const baseApiProduct = 'api/c-products';
const baseApiUom = 'api/c-unit-of-measures';

const AuctionItemProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
})

@Component
export default class AuctionItem extends Mixins(AccessLevelMixin, AuctionItemProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  editMode: boolean = false;
  newRecord: boolean = false;
  loadingItems: boolean = false;
  deleteConfirmationVisible: boolean = false;

  auction: any = {};

  /**
   * Store the original record before editing or deleting.
   */
  tmpItem: any = {};

  auctionItemValidationSchema = {
    productId: {
      required: true,
      message: 'Product is required'
    },
    uomId: {
      required: true,
      message: 'UoM is required'
    },
    quantity: {
      required: true,
      message: 'Quantity is required'
    },
    ceilingPrice: {
      required: true,
      message: 'Ceiling Price is required'
    }
  };

  items: any[] = [];

  /**
   * Products are dynamically loaded each time
   * a user is filtering it by code/name.
   */
  products: any[] = [];

  /**
   * UoMs are dynamically loaded each time
   * a user is filtering it by code/name.
   */
  uoms: any[] = [];

  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.auction.documentStatus && this.auction.documentStatus !== 'DRF';
  }

  onAddClicked() {
    this.retrieveUoms();
    this.retrieveProducts();

    this.items.splice(0, 0, {
      editing: true,
      adOrganizationId: AccountStoreModule.organizationInfo.id,
      auctionId: this.auction.id,
      productId: null,
      uomId: null,
      quantity: 0,
      ceilingPrice: 0,
      amount: 0
    });

    this.newRecord = true;
  }

  onDeleteClicked(row: any) {
    this.tmpItem = row;
    this.deleteConfirmationVisible = true;
  }

  onEditCanceled(index: number) {
    if (this.newRecord) {
      this.items.splice(0, 1);
      this.newRecord = false;
    } else {
      this.items.splice(index, 1, this.tmpItem);
    }
  }

  onEditClicked(row) {
    this.retrieveUoms(row.uomId);
    this.retrieveProducts(row.productId);
    
    this.tmpItem = {...row};
    row.editing = true;
  }

  onSaveClicked(row) {
    const validator = new Schema(this.auctionItemValidationSchema);
    validator.validate(row, (errors: any[]) => {
      if (errors) {
        for (const error of errors) {
          row[`${error.field}Error`] = true;
          row[`${error.field}Message`] = error.message;
        }
        this.$message.error(errors[0].message);
      } else {
        row.proposedPriceError = false;
        row.proposedPriceMessage = null;
        row.deliveryDateError = false;
        row.deliveryDateMessage = null;
        this.saveItem(row)
      }
    })
  }

  onCeilingPriceChange(row: any, price: number) {
    row.amount = row.quantity * price;
  }

  onQuantityChange(row: any, quantity: number) {
    row.amount = quantity * row.ceilingPrice;
  }

  created() {
    this.auction = {...this.data};
    this.retrieveItems(this.data.id);
  }

  deleteRecord() {
    this.commonService(baseApiAuctionItem)
      .delete(this.tmpItem.id)
      .then(() => {
        this.$message.success('Item has been deleted successfully');
        this.retrieveItems(this.data.id);
        this.tmpItem = {};
      })
      .catch(err => {
        console.error('Failed to delete item', err);
        this.$message.error('Failed to delete item');
      })
      .finally(() => this.deleteConfirmationVisible = false)
  }

  printProductName(productId?: number) {
    if (!productId) {
      return null;
    }

    return this.products.find(product => product.id === productId)?.name || productId;
  }

  private retrieveItems(auctionId: number) {
    this.loadingItems = true;
    this.commonService(baseApiAuctionItem)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ]
      })
      .then(res => {
        this.items = res.data.map(item => {
          item.editing = false;
          return item;
        })
      })
      .finally(() => this.loadingItems = false);
  }

  retrieveProducts(query?: string | number) {
    let baseQuery = [ 'active.equals=true' ];

    if (!!query) {
      baseQuery = [
        ...baseQuery,
        ...[ typeof query === 'string' ? `name.contains=${query}` : `id.equals=${query}` ]
      ];
    }

    this.commonService(baseApiProduct)
      .retrieve({
        criteriaQuery: this.updateCriteria(baseQuery),
        paginationQuery: {
          page: 0,
          size: 20,
          sort: ['id,desc']
        }
      })
      .then(res => this.products = res.data);
  }

  retrieveUoms(query?: string | number) {
    let baseQuery = [ 'active.equals=true' ];

    if (!!query) {
      baseQuery = [
        ...baseQuery,
        ...[ typeof query === 'string' ? `code.contains=${query}` : `id.equals=${query}` ]
      ];
    }

    this.commonService(baseApiUom)
      .retrieve({
        criteriaQuery: this.updateCriteria(baseQuery),
        paginationQuery: {
          page: 0,
          size: 20,
          sort: ['code']
        }
      })
      .then(res => this.uoms = res.data);
  }

  public save() {
    // No save implementation in Auction Item tab.
  }

  private saveItem(data: any) {
    const newRecord = !data.id;

    this.loadingItems = true;
    this.commonService(baseApiAuctionItem)
      [newRecord ? 'create' : 'update'](data)
      .then(res => {
        this.$message.success(`Item has been ${newRecord ? 'added' : 'updated'} successfully`);
        this.newRecord = false;
        this.tmpItem = {};
        this.retrieveItems(this.auction.id);
      })
      .catch(err => {
        console.error('Failed to save the item', err);
        this.$message.error('Failed to save the item');
        this.loadingItems = false;
      });
  }
}