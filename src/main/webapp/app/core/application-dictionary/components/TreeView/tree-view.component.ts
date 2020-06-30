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
    let item = event.data;
    item.parentMenuId = null;
    this.list.splice(event.index, 0, event.data);
    this.updateSequence();
  }

  onReorder(event: any) {
    event.apply(this.list);
    this.updateSequence();
  }

  remove(item: any) {
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

  private updateSequence() {
    this.list.forEach((item: any, index: number) => {
      item.sequence = index + 1;
    });
    this.dynamicWindowService(`${this.baseApiUrl}/sequence`)
      .updateList(this.list)
      .then((res) => {
        console.log('Sequence updated. %O', res.data);
        this.$notify({
          title: 'Success',
          message: 'Menu item sequence updated',
          type: 'success',
          duration: 3000
        });
      })
      .catch(err => {
        console.error('Failed updating menu sequence. ', err);
      });

  }

}
