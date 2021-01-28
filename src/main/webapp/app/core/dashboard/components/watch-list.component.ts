import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { IAdWatchListItem, AdWatchListActionType } from '@/shared/model/ad-watch-list-item.model';
import { IAdWatchList } from '@/shared/model/ad-watch-list.model';
import VueCountTo from "vue-count-to";
import { Component, Inject, Vue, Watch } from 'vue-property-decorator';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";

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
  cards: IAdWatchListItem[] = [];

  get isCard() {
    return this.type === DisplayType.Card;
  }

  get isList() {
    return this.type === DisplayType.List;
  }

  get type() {
    return this.items.length > 4 ? DisplayType.List : DisplayType.Card;
  }

  @Watch('items', { deep: true })
  onItemsChanged(items: IAdWatchListItem[]) {
    console.log('watch list items updated. items count:', items.length);
    items.forEach(item => {
      this.retrieveWatchListCounter(item);
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
      const filterQuery = buildCriteriaQueryString([card.filterQuery, 'active.equals=true']);
      sessionStorage.setItem(`filterQuery__${card.actionUrl}?t=${timestamp}`, filterQuery);

      this.$router.push({
        path: card.actionUrl,
        query: {
          t: `${timestamp}`
        }
      });
    }
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
      });
  }

  private retrieveWatchListCounter(item: IAdWatchListItem) {
    this.commonService(item.restApiEndpoint)
      .count([
        'active.equals=true',
        item.filterQuery || null,
        accountStore.userDetails.vendor ? `vendorId.equals=${accountStore.userDetails.cVendorId}` : null
      ])
      .then(count => {
        const card = {...item};
        card.count = count;
        this.cards.push(card);
      })
  }
}