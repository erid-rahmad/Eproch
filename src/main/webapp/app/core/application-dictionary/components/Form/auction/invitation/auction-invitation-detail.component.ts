import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Inject, Mixins, Vue, Watch } from "vue-property-decorator";
import DynamicWindowService from '../../../DynamicWindow/dynamic-window.service';
import AuctionPrerequisite from '../components/auction-prerequisite.vue';
import AuctionContent from '../setup/auction-content.vue';
import AuctionInfo from '../setup/auction-info.vue';
import AuctionItem from '../setup/auction-item.vue';

const tabPaneComponent = new Map<string, string>([
  ['CTN', 'auction-content'],
  ['INF', 'auction-info'],
  ['ITM', 'auction-item'],
  ['PRQ', 'auction-prerequisite'],
])

const AuctionInvitationDetailProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    },

    selectedItems: {
      type: Array,
      default: () => {
        return [];
      }
    },

    tab: {
      type: String,
      default: () => {
        return 'INF';
      }
    }
  }
})

@Component({
  components: {
    AuctionContent,
    AuctionInfo,
    AuctionItem,
    AuctionPrerequisite,
  }
})
export default class AuctionInvitationDetail extends Mixins(AccessLevelMixin, AuctionInvitationDetailProps) {

  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  loadingTabs: boolean = false;

  activeTab: string = 'INF';

  tabs: any[] = [];

  get activeComponent() {
    return tabPaneComponent.get(this.activeTab);
  }

  get documentStatus() {
    const { documentStatus } = this.data;
    return documentStatus;
  }

  @Watch('activeTab')
  onActiveTabChanged(tabName: string) {
    this.$emit('update:tab', tabName);
  }

  @Watch('documentStatus')
  onDocumentStatusChanged(status: string, oldStatus: string) {
    if (status === 'ACC' && oldStatus === 'DRF') {
      this.activeTab = 'ITM';
    }

    this.tabs = this.tabs
      .map(tab => {
        if (tab.value === 'PRQ') {
          tab.hidden = status === 'ACC';
        } else if (tab.value === 'ITM' || tab.value === 'CTN') {
          tab.disabled = ['DRF', 'DCL'].includes(status);
        }

        return tab;
      })
      .filter(tab => ! tab.hidden);
  }

  onItemsSelected(items: any[]) {
    this.$emit('update:selectedItems', items);
  }

  onTabSaved(data: any) {
    this.$emit('update:data', data);
  }

  created() {
    this.activeTab = this.tab;
    
    this.tabs = [
      {
        id: 1000,
        name: 'Information',
        value: 'INF'
      },
      {
        id: 1010,
        name: 'Prerequisites',
        value: 'PRQ',
        hidden: this.data.documentStatus === 'ACC'
      },
      {
        id: 1020,
        name: 'Items',
        value: 'ITM',
        disabled: this.data.documentStatus === 'DRF'
      },
      {
        id: 1030,
        name: 'Content',
        value: 'CTN',
        disabled: this.data.documentStatus === 'DRF'
      },
    ].filter(tab => ! tab.hidden);
  }

  save() {
    (<any>this.$refs[this.activeTab][0]).save();
  }
}