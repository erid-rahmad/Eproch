import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { AccountStoreModule } from '@/shared/config/store/account-store';
import { intervalToDuration } from 'date-fns';
import dayjs from 'dayjs';
import duration from "dayjs/plugin/duration";
import { isEmpty } from 'lodash';
import { Component, Inject, Mixins, Vue, Watch } from 'vue-property-decorator';
import AuctionService from '../auction.service';

dayjs.extend(duration);

const baseApiAuctionItem = 'api/m-auction-items';

const durationFormats = new Map([
  ['years', ['years', 'months']],
  ['months', ['months', 'days']],
  ['days', ['days', 'hours', 'minutes']],
  ['hours', ['hours', 'minutes', 'seconds']],
])

const BidSubmissionProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class BidSubmission extends Mixins(AccessLevelMixin, BidSubmissionProp) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  private auctionWsService: AuctionService = new AuctionService();

  // These props are used to show the remainingTime.
  private timerId;
  private intervalId;
  private currentDate = new Date();

  gutterSize = 24;

  auction: any = {
    currencyName: 'IDR',
    leadingBid: null,
    decrement: 1000,
    actualEndDate: '2021-05-31T23:59:59'
  };

  auctionItems: any[] = [];

  bidForm: any = {
    amount: this.auction.decrement
  };

  bidLogs: any[] = [];

  selectedItem: any = {};
  selectedItemId: number = null;

  rank: string | number = null;

  loadingAuctionItems: boolean = false;

  timerRefreshInterval: number = 60000;

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

  get selectedItemGrid() {
    return isEmpty(this.selectedItem) ? [] : [ this.selectedItem ];
  }

  get timeRemaining() {
    if (!this.auction.actualEndDate) {
      return '';
    }
/* 
    if (this.currentDate >= new Date(this.auction.actualEndDate)) {
      return 'Event has been ended';
    }
 */
    return dayjs.duration({
      hours: 0,
      minutes: 10,
      seconds: 0,
    }).format('HH:mm:ss');
  }

  @Watch('data')
  onDataChanged(data: any) {
    this.retrieveAuctionItems(data.id);
  }

  onItemSelected(itemId: number) {
    this.selectedItem = this.auctionItems.find(item => item.id === itemId);
  }

  created() {
    this.onDataChanged(this.data);
    this.updateTimer();
  }

  mounted() {
    this.auctionWsService.connect();
    this.auctionWsService.subscribe(this.data.id);
    this.auctionWsService.receive().subscribe(data => {
      console.log('Auction info has been updated.', data);
    });
  }

  beforeDestroy() {
    clearInterval(this.intervalId);
    clearTimeout(this.timerId);
    this.auctionWsService.unsubscribe();
    this.auctionWsService.disconnect();
  }

  private retrieveAuctionItems(auctionId: number) {
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
        this.auctionItems = (res.data as any[]).map((item, idx) => {
          item.status = idx === 0 ? 'P' : 'N';
          return item;
        });
        if (this.auctionItems.length) {
          this.selectedItemId = this.auctionItems[0].id;
          this.onItemSelected(this.selectedItemId);
        }
      });
  }

  submitBid(amount: number) {
    let price: number;

    if (this.auction.leadingBid === null) {
      price = this.selectedItem.ceilingPrice - amount;
    } else {
      price = this.auction.leadingBid - amount;
    }

    this.$set(this.auction, 'leadingBid', price);
    this.rank = 1;

    this.bidLogs.push({
      vendorName: AccountStoreModule.vendorInfo.name,
      price,
      dateSubmit: new Date().toISOString()
    })
  }

  private updateCurrentDate() {
    this.currentDate = new Date();

    const duration = intervalToDuration({
      start: this.currentDate,
      end: new Date(this.auction.actualEndDate)
    });

    let refreshInterval;

    if (duration.months) {
      refreshInterval = 1440000;
    } else if (duration.days) {
      refreshInterval = 60000;
    } else {
      refreshInterval = 1000;
    }

    if (refreshInterval !== this.timerRefreshInterval) {
      this.timerRefreshInterval = refreshInterval;
      this.updateTimer();
    }
  }

  private updateTimer() {
    clearInterval(this.intervalId);
    clearTimeout(this.timerId);
    this.timerId = setTimeout(() => {
      this.intervalId = setInterval(this.updateCurrentDate, this.timerRefreshInterval);
      this.updateCurrentDate();

      if (this.currentDate >= new Date(this.auction.actualEndDate)) {
        clearInterval(this.intervalId);
        clearInterval(this.timerId);
      }
    }, (60 - this.currentDate.getSeconds()) * 1000);
  }
}