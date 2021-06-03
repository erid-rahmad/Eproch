import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';

const baseApiAuctionRule = 'api/m-auction-rules';

const AuctionRuleProps = Vue.extend({
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
export default class AuctionRule extends Mixins(AccessLevelMixin, AuctionRuleProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingItems: boolean = false;

  auction: any = {};

  items: any[] = [];

  gutterSize: number = 24;

  get formSettings() {
    return settings.form;
  }

  created() {
    this.auction = {...this.data};
    // this.retrieveItems(this.data.id);
  }

  private retrieveItems(auctionId: number) {
    this.loadingItems = true;
    this.commonService(baseApiAuctionRule)
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `auctionId.equals=${auctionId}`
        ]
      })
      .then(res => this.items = res.data)
      .finally(() => this.loadingItems = false);
  }

  public save() {
    console.log('Saving auction items...');
  }
}