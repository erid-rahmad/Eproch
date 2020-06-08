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
  private fullPath: string = '';

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
      'alt+z': this.cancelOperation
    };
  }

  created() {
    this.fullPath = this.$route.fullPath;
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
    if (!this.activeWindow) return;
    this.eventBus.$emit('refresh-data');
  }

  public openSearchWindow() {
    if (!this.activeWindow) return;
    this.eventBus.$emit('open-search-window');
  }

  public addRecord() {
    if (!this.activeWindow) return;
    this.editing = true;
    this.eventBus.$emit('add-record');
  }

  public copyRecord() {
    if (!this.activeWindow) return;
    this.editing = true;
    this.eventBus.$emit('copy-record');
  }

  public saveRecord() {
    if (!this.activeWindow) return;
    this.eventBus.$emit('save-record');
  }

  public deleteRecord() {
    if (!this.activeWindow) return;
    this.eventBus.$emit('delete-record');
  }

  public switchView() {
    this.toggleView(!this.gridView);
  }

  public goToParentTab() {
    if (!this.activeWindow) return;
    this.eventBus.$emit('tab-navigate', {
      direction: -1
    });
  }

  public goToChildTab() {
    if (!this.activeWindow) return;
    this.eventBus.$emit('tab-navigate', {
      direction: 1
    });
  }

  public cancelOperation() {
    if (!this.activeWindow) return;
    this.editing = false;
    this.eventBus.$emit('cancel-operation');
  }

  /**
   * Toggles between grid and details mode.
   * @param gridView Is grid mode.
   */
  private toggleView(gridView: boolean) {
    if (!this.activeWindow) return;
    this.gridView = gridView;
    this.$emit('toggle-view', {
      gridView
    });
  }

  private get activeWindow() {
    return this.fullPath === this.$route.fullPath;
  }
}