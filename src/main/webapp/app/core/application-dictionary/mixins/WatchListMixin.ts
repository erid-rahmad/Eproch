import { Component, Vue } from 'vue-property-decorator';
import { WindowStoreModule as windowStore } from "@/shared/config/store/window-store";

@Component
export default class WatchListMixin extends Vue {

  protected getWatchListQuery() {
    const tmpFilterQuery = windowStore.watchlistQuery;
    
    if (tmpFilterQuery) {
      windowStore.removeWatchlistQuery();
    }
    return tmpFilterQuery;
  }
}