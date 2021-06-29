import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Mixins, Vue, Watch } from 'vue-property-decorator';
import ContractDocument from './contract-document.vue';
import ContractEvaluation from './contract-evaluation.vue';
import ContractInfo from './contract-info.vue';

const tabPaneComponent = new Map<string, string>([
  ['INF', 'contract-info'],
  ['DOC', 'contract-document'],
  ['EVA', 'contract-evaluation'],
])

const ContractDetailProps = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
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
    ContractInfo,
    ContractEvaluation,
    ContractDocument
  }
})
export default class ContractDetail extends Mixins(AccessLevelMixin, ContractDetailProps) {

  activeTab: string = 'INF';

  tabs: any[] = [
    {
      id: 1,
      name: 'Information',
      value: 'INF',
    },
    {
      id: 2,
      name: 'Documents',
      value: 'DOC',
    },
    {
      id: 3,
      name: 'Vendor Evaluations',
      value: 'EVA',
    }
  ];

  get activeComponent() {
    return tabPaneComponent.get(this.activeTab);
  }

  get isTabDocument() {
    return this.activeTab === 'DOC';
  }

  get isTabInfo() {
    return this.activeTab === 'INF';
  }

  @Watch('activeTab')
  onActiveTabChanged(tabName: string) {
    this.$emit('update:tab', tabName);
  }

  onTabSaved(data: any) {
    if (this.isTabInfo) {
      this.$emit('update:data', data);

      this.tabs = this.tabs
        .map(tab => {
          const item = {...tab};
          item.disabled = false;
          return item;
        });
    }
  }

  created() {
    this.activeTab = this.tab;
    this.tabs.forEach((tab, index) => {
      tab.disabled = index > 0 && ! this.data.id ? true : false;
    })
  }

  save() {
    (<any>this.$refs[this.activeTab][0]).save();
  }

  submit() {
    (<any>this.$refs[this.activeTab][0]).submit();
  }

  approve() {
    (<any>this.$refs[this.activeTab][0]).approve();
  }

  reject() {
    (<any>this.$refs[this.activeTab][0]).reject();
  }
}
