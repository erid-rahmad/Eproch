import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import Schema from "async-validator";
import { ElTable } from 'element-ui/types/table';
import AdInputLookup from '@/shared/components/AdInput/ad-input-lookup.vue';

const baseApiAuctionItem = 'api/m-auction-items';
const baseApiAuctionSubmissionItem = 'api/m-auction-submission-items';

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

@Component({
  components: {
    AdInputLookup
  }
})
export default class AuctionItem extends Mixins(AccessLevelMixin, AuctionItemProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  editMode: boolean = false;
  newRecord: boolean = false;
  loadingItems: boolean = false;
  deleteConfirmationVisible: boolean = false;

  auction: any = {};
  selectedRow: any = {};

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
  selectedItems: any[] = [];

  watchedItemIds: Set<number> = new Set();


  /**
   * Products are dynamically loaded each time
   * a user is filtering it by code/name.
   */
  products: any[] = [];

  bidImprovementUnitOptions: any[] = [];
  tieBidsRuleOptions: any[] = [];

  gutterSize: number = 24;

  get isInvitation() {
    return this.auction.documentTypeName === 'Auction Invitation';
  }

  get formSettings() {
    return settings.form;
  }

  get isStarted() {
    return this.selectedRow.documentStatus === 'STR';
  }

  get readOnly() {
    if (this.isInvitation) {
      return true;
    }

    return this.auction.documentStatus && this.auction.documentStatus !== 'DRF';
  }

  onAddClicked() {
    if (this.editMode) {
      return false;
    }
    
    this.items.splice(0, 0, {
      editing: true,
      adOrganizationId: AccountStoreModule.organizationInfo.id,
      auctionId: this.auction.id,
      productId: null,
      uomId: null,
      quantity: 0,
      ceilingPrice: 0,
      amount: 0,
      bidDecrement: null,
      protectFrontBuffer: null,
      protectBackBuffer: null
    });

    this.editMode = true;
    this.newRecord = true;
    this.selectedRow = this.items[0];
  }

  onDeleteClicked(row: any) {
    this.tmpItem = row;
    this.deleteConfirmationVisible = true;
  }

  onEditCanceled(index: number) {
    this.editMode = false;

    if (this.newRecord) {
      this.items.splice(0, 1);
      this.newRecord = false;
      this.selectedRow = this.items[0];
    } else {
      this.items.splice(index, 1, this.tmpItem);
      this.setRow(this.tmpItem);
    }
  }

  onEditClicked(row) {
    this.selectedRow = row;
    
    this.tmpItem = {...row};
    this.editMode = true;
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

  /**
   * Handles the items that is selected by vendor.
   * @param items The selected items.
   */
  onSelectionChanged(items: any[]) {
    this.selectedItems = items;
    this.$emit('items-selected', items);
  }

  onCeilingPriceChange(row: any, price: number) {
    row.amount = row.quantity * price;
  }

  onQuantityChange(row: any, quantity: number) {
    row.amount = quantity * row.ceilingPrice;
  }

  onCurrentRowChanged(row: any) {
    if (!this.editMode) {
      this.selectedRow = row;
    }
  }

  async created() {
    this.auction = {...this.data};

    if (this.isInvitation) {
      await this.retrieveWatchedItems(this.data.auctionId, this.vendorInfo.id);
    }

    this.retrieveItems(this.isInvitation ? this.data.auctionId : this.data.id);
    this.retrieveBidImprovementUnitOptions();
    this.retrieveTieBidsRuleOptions();
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

  printBidImprovementUnit(value: string) {
    return this.bidImprovementUnitOptions.find(item => item.value === value)?.name || value;
  }

  printTieBidsRule(value: string) {
    return this.tieBidsRuleOptions.find(item => item.value === value)?.name || value;
  }

  private retrieveWatchedItems(auctionId: number, vendorId) {
    this.loadingItems = true;
    return new Promise(resolve => {
      this.commonService(baseApiAuctionSubmissionItem)
        .retrieve({
          criteriaQuery: [
            `active.equals=true`,
            `auctionId.equals=${auctionId}`,
            `vendorId.equals=${vendorId}`
          ],
          paginationQuery: {
            page: 0,
            size: 500,
            sort: ['id']
          }
        })
        .then(res => {
          res.data.forEach(item => {
            this.watchedItemIds.add(item.auctionItemId);
          });
          resolve(true);
        })
        .catch(err => {
          console.error('Failed to get the item watch list', err);
          this.$message.error('Failed to get the item watch list');
          resolve(false);
        })
        .finally(() => this.loadingItems = false);
    })
  }

  private retrieveItems(auctionId: number) {
    this.loadingItems = true;
    this.commonService(baseApiAuctionItem)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 500,
          sort: ['id']
        }
      })
      .then(res => {
        this.items = res.data.map(item => {
          item.editing = false;

          return item;
        });

        if (this.isInvitation) {
          this.$nextTick(() => {
            this.items.forEach(item => {
              (<ElTable>this.$refs.itemGrid)
                .toggleRowSelection(item, this.watchedItemIds.has(item.id));
            });
          });
        } else if (this.items.length) {
          this.setRow(this.items[0]);
        }
      })
      .finally(() => this.loadingItems = false);
  }

  private retrieveBidImprovementUnitOptions() {
    this.commonService(null)
      .retrieveReferenceLists('bidImprovementUnitOptions')
      .then(res => this.bidImprovementUnitOptions = res);
  }

  private retrieveTieBidsRuleOptions() {
    this.commonService(null)
      .retrieveReferenceLists('tieBidsRuleOptions')
      .then(res => this.tieBidsRuleOptions = res);
  }

  private setRow(record: any) {
    (<ElTable>this.$refs.itemGrid).setCurrentRow(record);
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
        this.editMode = false;
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