import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AuctionContent from './auction-content.vue';
import AuctionInfo from './auction-info.vue';
import AuctionItem from './auction-item.vue';
import AuctionParticipant from './auction-participant.vue';
import AuctionRule from './auction-rule.vue';
import AuctionSummary from './auction-summary.vue';
import AuctionTeam from './auction-team.vue';

const tabPaneComponent = new Map<string, string>([
  ['CTN', 'auction-content'],
  ['INF', 'auction-info'],
  ['ITM', 'auction-item'],
  ['PCP', 'auction-participant'],
  ['RUL', 'auction-rule'],
  ['SUM', 'auction-summary'],
  ['TEM', 'auction-team'],
])

const AuctionSetupProps = Vue.extend({
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
    AuctionContent,
    AuctionInfo,
    AuctionItem,
    AuctionParticipant,
    AuctionRule,
    AuctionSummary,
    AuctionTeam,
  }
})
export default class AuctionSetup extends Mixins(AccessLevelMixin, AuctionSetupProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingTabs: boolean = false;

  activeTab: string = 'INF';
  tabs: any[] = [];

  get activeComponent() {
    return tabPaneComponent.get(this.activeTab);
  }

  get isTabInfo() {
    return this.activeTab === 'INF';
  }

  onTabSaved(data: any) {
    if (this.isTabInfo) {
      this.$emit('update:data', data);
      this.tabs = this.tabs.map(tab => {
        const item = {...tab};
        item.disabled = false;
        return item;
      });
    }
  }

  created() {
    this.retrieveTabs();
  }

  private retrieveTabs() {
    this.loadingTabs = true;
    this.commonService(null)
      .retrieveReferenceLists('auctionSetupTabs', {
        page: 0,
        size: 10,
        sort: ['id']
      })
      .then(res => {
        this.tabs = res.map((item, index) => {
          return {
            id: item.id,
            name: item.name,
            value: item.value,
            disabled: index > 0 && ! this.data.id ? true : false
          };
        })
      })
      .finally(() => this.loadingTabs = false);
  }

  save() {
    (<any>this.$refs[this.activeTab][0]).save();
  }
}