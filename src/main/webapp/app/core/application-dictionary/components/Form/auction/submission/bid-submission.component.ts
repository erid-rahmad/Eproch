import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import AdTriggerService from '@/entities/ad-trigger/ad-trigger.service';
import CountdownTimer from "@/shared/components/CountdownTimer/index.vue";
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { isEmpty } from 'lodash';
import { Component, Inject, Mixins, Vue, Watch } from 'vue-property-decorator';
import AuctionService from '../auction.service';

const baseApiAuctionItem = 'api/m-auction-items';
const baseApiAuctionSubmissionItem = 'api/m-auction-submission-items';
const baseApiAuctionEventLog = 'api/m-auction-event-logs';

const auctionStatuses = {
  'FIN': 'Finished',
  'PAS': 'Paused',
  'STR': 'Started',
  'STP': 'Stopped'
};

const BidSubmissionProp = Vue.extend({
  props: {
    auctionStatus: String,
    activeLotId: Number,
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component({
  components: {
    CountdownTimer
  }
})
export default class BidSubmission extends Mixins(AccessLevelMixin, BidSubmissionProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  @Inject('adTriggerService')
  private adTriggerService: () => AdTriggerService;

  private auctionWsService: AuctionService = new AuctionService();

  gutterSize = 24;

  auction: any = {};

  auctionRule: any = {};
  auctionItems: any[] = [];
  submissionItems: any[] = [];

  bidForm: any = {
    amount: 0
  };

  eventLog: any[] = [];
  selectedItemId: number = null;

  rank: string | number = null;
  leadingBidPrice: number = null;
  
  originalPrice: number = null;
  tmpPrice: number = null;

  bidConfirmationVisible: boolean = false;
  loadingAuctionItems: boolean = false;
  loadingEventLog: boolean = false;

  timerRefreshInterval: number = 60000;

  get isOngoing() {
    return this.auctionItems.some(item => item.auctionStatus === 'STR');
  }

  get isPaused() {
    return this.auctionItems.some(item => item.auctionStatus === 'PAS');
  }

  get isVendor() {
    return AccountStoreModule.isVendor;
  }

  get rankType() {
    if (this.rank === 3) {
      return 'danger';
    }

    if (this.rank === 2) {
      return 'warning';
    }

    if (this.rank === 1) {
      return 'success';
    }

    return 'info';
  }

  get selectedItem() {
    return this.auctionItems.find(item => item.id === this.selectedItemId) || {};
  }

  get selectedItemGrid() {
    console.log('Compute selectedItemGrid... data:', this.selectedItem);
    return isEmpty(this.selectedItem) ? [] : [ this.selectedItem ];
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.auction = {...data};
    this.retrieveAuctionItems(data.id);
  }

  onBidDecrementChanged(value: number) {
    const bidPrice = this.selectedItem.ceilingPrice - value;
    this.updateExtendedPrice(this.selectedItem.id, bidPrice);
  }

  onItemSelected(itemId: number) {
    this.$emit('update:activeLotId', this.selectedItem.id);
    this.$emit('update:auctionStatus', this.selectedItem.auctionStatus);

    const decrement = this.selectedItem.bidDecrement || 0;

    this.$set(this.bidForm, 'amount', decrement === null ? 0 : decrement);
    this.retrieveLowestPrice(itemId);
    this.retrieveEventLog(this.auction.id, itemId);
  }

  async created() {
    this.onDataChanged(this.data);
  }

  mounted() {
    this.auctionWsService.connect();
    this.auctionWsService.subscribe(this.data.id);
    this.auctionWsService.receive().subscribe(data => {
      const log = data.log;

      // Update auctionStatus to determine available actions for the auction.
      // This will delegate up to parent component.
      const item = data.lot;
      if (item?.auctionStatus) {
        this.$emit('update:auctionStatus', item.auctionStatus);
      }

      // Update the respective item in the item list.
      const itemIndex = this.auctionItems.findIndex(el => el.id === item.id);
      const currentItem = this.auctionItems[itemIndex];
      item.watched = currentItem.watched;  // Retain the watched property.
      item.bidPrice = currentItem.bidPrice;  // Retain the bidPrice property.
      item.amount = currentItem.quantity * item.bidPrice;
      this.auctionItems.splice(itemIndex, 1, item);
      console.log('Item updated.', this.auctionItems);

      this.auction = data.auction;
      const action: string = log.action;
      const timer: any = this.$refs.countdownTimer;

      if (['TRM', 'PAS', 'FIN'].includes(action)) {
        timer.stop();
      } else if (action === 'STR') {
        timer.start();
      } else if (action === 'BID') {
        // Update the proposed price of the respective lot.
        if (this.leadingBidPrice > data.bid.price) {
          this.updateLowestPrice(item.id, data.bid.price);
        }

        log.username = data.bid.vendorName;
      }

      // Append the new log into eventLog.
      this.eventLog.unshift(log);
    });
  }

  beforeDestroy() {
    this.auctionWsService.unsubscribe();
    this.auctionWsService.disconnect();
  }

  confirmResponse(extendedPrice: number) {
    this.tmpPrice = extendedPrice;
    this.bidConfirmationVisible = true;
  }

  getItemClassNames(item: any) {
    const status = item.auctionStatus;

    return {
      'is-watched': item.watched,
      'is-done': status === 'FIN',
      'is-ongoing': status === 'STR',
      'is-paused': status === 'PAS'
    };
  }

  printStatus(value: string) {
    return auctionStatuses[value];
  }

  private retrieveAuctionItems(auctionId: number) {
    this.loadingAuctionItems = true;
    this.commonService(baseApiAuctionItem)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['id']
        }
      })
      .then(res => {
        this.auctionItems = res.data.map(item => {
          item.lowestPrice = item.ceilingPrice;
          return item;
        });

        if (this.isOngoing) {
          this.$nextTick(() => (this.$refs.countdownTimer as any).start());
        }

        if (this.auctionItems.length) {
          const activeItem = this.auctionItems.find(item => ['STR', 'PAS'].includes(item.auctionStatus) || item.auctionStatus === null);

          this.selectedItemId = activeItem?.id || this.auctionItems[0].id;
          console.log('Auction items retrieved. selected ID:', this.selectedItemId);
          this.onItemSelected(this.selectedItemId);
          
          if (this.isVendor) {
            this.retrieveSubmissionItems(this.auction.id);
          }
        }
      })
      .finally(() => this.loadingAuctionItems = false);
  }

  private retrieveSubmissionItems(auctionId: number) {
    this.loadingAuctionItems = true;
    this.commonService(baseApiAuctionSubmissionItem)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`,
          `vendorId.equals=${this.vendorInfo.id}`
        ],
        paginationQuery: {
          page: 0,
          size: 1000,
          sort: ['id']
        }
      })
      .then(res => {
        this.submissionItems = res.data;

        this.auctionItems = this.auctionItems.map(item => {
          const mappedItem = {...item};
          const watchedItem = this.submissionItems.find(submissionItem => submissionItem.auctionItemId === item.id);

          mappedItem.bidPrice = watchedItem?.price || 0;
          mappedItem.watched = watchedItem || false;

          if (mappedItem.bidPrice) {
            mappedItem.amount = mappedItem.quantity * mappedItem.bidPrice;
          }
          return mappedItem;
        });
        console.log('Extended price applied. items:', this.auctionItems);
      })
      .finally(() => this.loadingAuctionItems = false);
  }

  private retrieveEventLog(auctionId: number, itemId: number) {
    this.loadingEventLog = true;
    this.commonService(baseApiAuctionEventLog)
      .retrieve({
        criteriaQuery: [
          `auctionId.equals=${auctionId}`,
          `auctionItemId.equals=${itemId}`,
        ],
        paginationQuery: {
          page: 0,
          size: 10000,
          sort: ['dateTrx,desc']
        }
      })
      .then(res => this.eventLog = res.data)
      .finally(() => this.loadingEventLog = false);
  }

  private retrieveLowestPrice(itemId: number) {
    this.loadingAuctionItems = true;
    this.commonService(`${baseApiAuctionEventLog}/min-price`)
      .find(itemId)
      .then(res => this.updateLowestPrice(itemId, res?.minPrice))
      .finally(() => this.loadingAuctionItems = false);
  }

  private updateExtendedPrice(itemId: number, extendedPrice: number) {
    if (extendedPrice === null || extendedPrice === undefined) {
      return;
    }

    const currentItemIdx = this.auctionItems.findIndex(item => item.id === itemId);
    const currentItem = this.auctionItems[currentItemIdx];
    currentItem.bidPrice = extendedPrice || 0;
    currentItem.amount = currentItem.quantity * extendedPrice;
    this.auctionItems.splice(currentItemIdx, 1, currentItem);
  }

  private updateLowestPrice(itemId: number, lowestPrice: number) {
    if (lowestPrice === null || lowestPrice === undefined) {
      return;
    }

    this.leadingBidPrice = lowestPrice;
  }

  spanEventLogColumns({ row, column, rowIndex, columnIndex }) {
    if (['STR', 'STP', 'PAS', 'FIN'].includes(row.action)) {
      if (columnIndex === 0) {
        return { rowspan: 1, colspan: 2 };
      } else if (columnIndex === 1) {
        return [0, 0];
      }
    }
  }

  submitBid() {
    const params = {
      action: 'BID',
      auctionId: this.auction.id,
      itemId: this.selectedItem.id,
      price: `${this.selectedItem.ceilingPrice - this.tmpPrice}`
    }

    this.adTriggerService().runService('mAuctionProcessTrigger', params)
      .then(res => {
        this.$message.success('Bid has been successfully submitted');
        this.bidConfirmationVisible = false;
      })
      .catch(err => {
        console.error('Failed to place bid', err);
        this.$message.error('Failed to place a bid for the current lot');
      })
  }
}
