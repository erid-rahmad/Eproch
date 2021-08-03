import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import { Component, Mixins, Vue, Watch } from 'vue-property-decorator';
import ContractDocument from './contract-document.vue';
import ContractEvaluation from './contract-evaluation.vue';
import ContractInfo from './contract-info.vue';
import Task from './task.vue';
import ContractTeam from './contract-team.vue';
import ContractMessageBoard from './contract-message-board.vue';
import {AccountStoreModule} from "@/shared/config/store/account-store";

const tabPaneComponent = new Map<string, string>([
  ['INF', 'contract-info'],
  ['DOC', 'contract-document'],
  ['EVA', 'contract-evaluation'],
  ['TEM', 'contract-team'],
  ['MSB', 'contract-message-board'],
  ['TSK', 'Task'],
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
    Task,
    ContractDocument,
    ContractTeam,
    ContractMessageBoard
  }
})
export default class ContractDetail extends Mixins(AccessLevelMixin, ContractDetailProps) {

  activeTab: string = 'INF';
  saveBtn:boolean=false;
  publishBtn:boolean=false;

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
    },{
      id: 4,
      name: 'Task',
      value: 'TSK',
    },
    {
      id: 5,
      name: 'Team',
      value: 'TEM',
    },
    {
      id: 6,
      name: 'Message Board',
      value: 'MSB',
    },
    {
      id: 7,
      name: 'History',
      value: 'HRY',
    }
  ];

  get activeComponent() {
    return tabPaneComponent.get(this.activeTab);
  }

  get isTabDocument() {
    if (this.isVendor){
      return this.activeTab === 'TSK'
    }else {return this.activeTab === 'DOC';}
  }

  get isTabInfo() {
    return this.activeTab === 'INF';
  }

  get isVendor(){
    return AccountStoreModule.isVendor;
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

  button(button){

  }

  created() {
    if (this.isVendor){
      this.tabs = [
     {
          id: 4,
          name: 'Task',
          value: 'TSK',
        }]

    }

    this.activeTab = this.tab;
    this.tabs.forEach((tab, index) => {
      tab.disabled = index > 0 && ! this.data.id ? true : false;
    })

    const query = this.$route.query;
    console.log(query);
    if(query.page){
      this.activeTab = this.tabs[+query.page].value;
    }
  }

  save() {
    (<any>this.$refs[this.activeTab][0]).save();
  }

  generatePO() {
    (<any>this.$refs[this.activeTab][0]).generatePoAction();
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
