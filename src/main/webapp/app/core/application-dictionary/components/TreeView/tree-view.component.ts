import { Vue, Component, Inject, Watch } from 'vue-property-decorator';
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { Drag, DropList } from "vue-easy-dnd";

const TreeViewProps = Vue.extend({
  props: {
    tab: {
      type: Object,
      default: () => {
        return null;
      }
    }
  }
})

@Component({
  components: {
    Drag,
    DropList
  }
})
export default class TreeView extends TreeViewProps {
  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  private baseApiUrl: string = '';
  loading = false;
  list: any[] = [];
  listKey = null;

  get itemChildren() {
    return (item: any) => item[this.listKey];
  }

  get observableTabProperties() {
    const { name, adfields, targetEndpoint, filterQuery, parentId, promoted } = this.tab;
    console.log('name: %s', name);
    return { name, adfields, targetEndpoint, filterQuery, parentId, promoted };
  }

  @Watch('observableTabProperties')
  onObservableTabPropertiesChange(tab: any) {
    console.log('tab changed: %O', tab);
    this.baseApiUrl = tab.targetEndpoint;
    this.retrieveAllRecords();
  }

  created() {
    console.log('tree-view created');
    this.listKey = 'adMenus';
  }

  onInsert(event: any) {
    console.log('tree-view.onInsert(). event: %O', event);
    let item = event.data;
    item.parentMenuId = null;
    this.list.splice(event.index, 0, event.data);
  }

  onReorder(event: any) {
    console.log('tree-view.onReorder(). event: %O', event);
    event.apply(this.list);
  }

  remove(item: any) {
    console.log('tree-view.remove(). event: %O', item);
    let index = this.list.indexOf(item);
    this.list.splice(index, 1);
  }

  public reload() {
    this.retrieveAllRecords();
  }
  
  public retrieveAllRecords(): void {
    this.loading = true;
    this.dynamicWindowService(this.baseApiUrl)
      .retrieve({
        criteriaQuery: 'parentMenuId.specified=false'
      })
      .then(res => {
        this.list = res.data;
      })
      .catch((err) => {
        console.error('Failed getting the record. %O', err);
      })
      .finally(() => {
        this.loading = false;
      });
  }

}