import { Component, Prop, Vue } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";

const ActionToolbarProps = Vue.extend({
  props: {
    atWindowRoot: {
      type: Boolean,
      default: true
    },
    atLastTab: {
      type: Boolean,
      default: true
    },
    eventBus: {
      type: Object,
      default: () => {
        return null;
      }
    }
  }
});

@Component
export default class ActionToolbar extends ActionToolbarProps {
  dynamicWindowService: DynamicWindowService = null;
  gridView = true;
  authorities: Set<string> = accountStore.authorities;
  private editing: boolean = false;

  get isEditing() {
    return this.editing;
  }

  get keymap() {
    return {
      'alt+g': this.switchView,
      'alt+up': this.goToParentTab,
      'alt+down': this.goToChildTab,
      'alt+r': this.refreshData,
      'alt+f': this.openSearchWindow,
      'alt+n': this.addRecord,
      'alt+c': this.copyRecord,
      'alt+s': this.saveRecord,
      'alt+del': this.deleteRecord,
      'esc': this.cancelOperation
    };
  }

  created() {
    this.eventBus.$on('inline-editing', this.onInlineEditing);
    this.eventBus.$on('record-saved', this.onSaveSuccess);
  }

  beforeDestroy() {
    this.eventBus.$off('inline-editing', this.onInlineEditing);
    this.eventBus.$off('record-saved', this.onSaveSuccess);
  }

  private onInlineEditing(active: boolean) {
    this.editing = active;
  }

  private onSaveSuccess() {
    this.editing = false;
  }

  public refreshData() {
    this.eventBus.$emit('refresh-data');
  }

  public openSearchWindow() {
    this.eventBus.$emit('open-search-window');
  }

  public addRecord() {
    this.editing = true;
    this.eventBus.$emit('add-record');
  }

  public copyRecord() {
    this.editing = true;
    this.eventBus.$emit('copy-record');
  }

  public saveRecord() {
    this.eventBus.$emit('save-record');
  }

  public deleteRecord() {
    this.eventBus.$emit('delete-record');
  }

  public switchView() {
    this.toggleView(!this.gridView);
  }

  public goToParentTab() {
    this.eventBus.$emit('tab-navigate', -1);
  }

  public goToChildTab() {
    this.eventBus.$emit('tab-navigate', 1);
  }

  public cancelOperation() {
    this.editing = false;
    this.eventBus.$emit('cancel-operation');
  }

  /**
   * Toggles between grid and details mode.
   * @param gridView Is grid mode.
   */
  private toggleView(gridView: boolean) {
    this.gridView = gridView;
    this.$emit('toggle-view', gridView);
  }
}