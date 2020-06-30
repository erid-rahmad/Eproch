import { Component, Vue, Inject } from 'vue-property-decorator';
import { Drag, DropList } from "vue-easy-dnd";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

const SubmenuItemProps = Vue.extend({
  props: {
    baseApiUrl: String,
    model: {
      type: Object,
      default: () => {
        return null;
      }
    },
    list: {
      type: Array,
      default: () => {
        return [];
      }
    },
    listKey: {
      type: String,
      default: 'items'
    }
  }
});

@Component({
  components: {
    Drag,
    DropList
  }
})
export default class SubmenuItem extends SubmenuItemProps {
  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;
  
  onInsert(event: any) {
    let item = event.data;
    item.parentMenuId = this.model.id;
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
  
  printTitle({name, translationKey}) {
    const key = `route.${translationKey || name}`;
    return this.$te(key) ? this.$t(key) : name;
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
