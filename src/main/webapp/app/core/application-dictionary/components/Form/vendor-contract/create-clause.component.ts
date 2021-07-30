import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import {Component, Inject, Mixins, Vue} from "vue-property-decorator";
import draggable from 'vuedraggable'
import Sortable from 'sortablejs';
import AccountService from '@/account/account.service';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import settings from '@/settings';
import AdInputLookup from "@/shared/components/AdInput/ad-input-lookup.vue";
// @ts-ignore
import {ContainerMixin, ElementMixin} from 'vue-slicksort';

const baseApiClauseLine = 'api/c-clause-lines';
const baseApiContractDocument = 'api/m-contract-clause-documents';


const SortableList = {
  mixins: [ContainerMixin],
  template: `
    <ul class="list">
    <slot/>
    </ul>
  `
};

const SortableItem = {
  mixins: [ElementMixin],
  props: ['item'],
  template: `
    <li class="list-item">{{ item }}</li>
  `
};


const ContractInfoProps = Vue.extend({
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
    draggable,
     AdInputLookup,
  }
})
export default class ContractDocument extends Mixins(AccessLevelMixin, ContractInfoProps) {


  clauses: any[] = [{
    title: '',
    clause: '',
    clauseLine: '',
    list: [],
  },
  ];

  x = 0;
  loading: boolean = false;
  documentFormVisible: boolean = false;
  contractTextVisible: boolean = false;
  contractText = '';
  documents: any[] = [];
  clausesOption: any[] = [];
  Title = '';
  addModuleDialog = false
  removeModuleDialog = false
  clickModule = "recommended"
  dataini = ''
  @Inject('accountService')
  private accountService: () => AccountService;
  @Inject('dynamicWindowService')
  private commonService: (baseApiUrl: string) => DynamicWindowService;

  get formSettings() {
    return settings.form;
  }

  get readOnly() {
    return this.data.documentStatus && this.data.documentStatus !== 'DRF';
  }

  addClause() {
    this.x += 1;
    let clause = {
      action: false,
      clause: '',
      clauseLine: '',
      list: [],
    }
    this.clauses.push(clause);
  }

  arrange(data){
    this.loading=true;
    this.loading=false;

    return  data.action ? data.action=false:data.action=true;

  }

  mounted() {
  }

  // checkMove(e) {
  //   return !this.clauses[e.draggedContext.futureIndex].fixed
  // }

  created() {
  }

  move(row) {
    function arraymove(arr, fromIndex, toIndex) {
      var element = arr[fromIndex];
      arr.splice(fromIndex, 1);
      arr.splice(toIndex, 0, element);
    }
  }

  addSubClause(row) {
    this.x += 1;
    let satu =
      {no: this.x, clauseLine: ""}
    row.list.push(satu)
  }

  retrieveClauseLine(row) {
    this.commonService(baseApiClauseLine)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
          `clauseId.equals=${row.clause}`,
        ])
      })
      .then(res => {
        return this.clausesOption = res.data;
      })
      .catch(err => {
        console.log('Failed to retrieveClauseLine. %O', err);
        this.$message.error('Failed to retrieveClauseLine');
      })
      .finally(() => this.loading = false);
  }

  async saveDocument() {
    let text ='';
    await this.clauses.forEach( value => {
       value.list.forEach(data=>{
        let paragraph = '\n' + data.clauseLine + '\n';
        text =text+paragraph;
      })
    })
    let data:any={}
    data.contract=text
    data.title=this.Title
    this.$emit('save', data)
  }

  close(){
    this.$emit('close',null)
  }
}
