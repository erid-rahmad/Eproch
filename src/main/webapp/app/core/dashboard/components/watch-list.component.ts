import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { IAdWatchListItem, AdWatchListActionType } from '@/shared/model/ad-watch-list-item.model';
import { IAdWatchList } from '@/shared/model/ad-watch-list.model';
import VueCountTo from "vue-count-to";
import { Component, Inject, Vue, Watch } from 'vue-property-decorator';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";

enum DisplayType {
  Card = 'card',
  List = 'list'
}

const WatchListProps = Vue.extend({
  props: {
    name: String,
    title: String
  }
})

@Component({
  components: {
    'count-to': VueCountTo
  }
})
export default class WatchList extends WatchListProps {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  items: IAdWatchListItem[] = [];

  get isCard() {
    return this.type === DisplayType.Card;
  }

  get isList() {
    return this.type === DisplayType.List;
  }

  get type() {
    return /*this.items.length > 4 ? DisplayType.List : */DisplayType.Card;
  }

  @Watch('items', { deep: true })
  onItemsChanged(items: IAdWatchListItem[]) {
    items.forEach((item, index) => {
      this.retrieveWatchListCounter(item, index);
    });
  }

  created() {
    this.retrieveWatchListItems(this.name);
  }

  async onCardClicked(card: IAdWatchListItem) {
    if (! card.count) {
      return;
    }
    
    if (card.actionType === AdWatchListActionType.MENU) {
      card.actionUrl = await this.commonService('/api/ad-menus/full-path').find(card.adMenu.id);
    }

    if (card.actionUrl) {
      const timestamp = Date.now();
      windowStore.setWatchlistQuery(card.filterQuery)
        .then(() => {
          this.$router.push({
            path: card.actionUrl,
            query: {
              t: `${timestamp}`
            }
          });
        });
    }
  }

  public refresh() {
    this.onItemsChanged(this.items);
  }

  private retrieveWatchListItems(name: string) {
    this.commonService('/api/ad-watch-lists')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `name.equals=${name}`
        ],
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        if (res.data.length) {
          const watchList: IAdWatchList = res.data[0];
          this.items = watchList.adWatchListItems;
        }
      })
      .catch(err => {
        console.log('Failed to get the watch list.', err);
        this.$message({
          message: 'Failed to get the watch list',
          type: 'warning'
        });
      });
  }

  private retrieveWatchListCounter(item: IAdWatchListItem, index?: number) {
    this.commonService(item.restApiEndpoint)
      .count([
        item.filterQuery || null,
        accountStore.userDetails.vendor ? `vendorId.equals=${accountStore.userDetails.cVendorId}` : null
      ])
      .then(count => {
        this.$set(this.items[index], 'count', count);
      });
  }
}