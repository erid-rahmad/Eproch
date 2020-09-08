import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { Component, Vue, Watch } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';

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
    buttons: {
      type: Array,
      default: () => {
        return [];
      }
    },
    recordCount: Number,
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

  runProcess(triggerDef: any) {
    this.$emit('run-trigger', triggerDef);
  }

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

  public get editMode() {
    return this.editing;
  }

  public set editMode(editing) {
    this.editing = editing;
  }

  @Watch('editing')
  onEditingChange(editing: boolean) {
    this.$emit('edit-mode-change', editing);
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
    this.$emit('refresh', {
      isGridView: this.gridView
    });
  }

  public openSearchWindow() {
    if (!this.activeWindow) return;
    this.$emit('open-search-panel');
  }

  public addRecord() {
    if (!this.activeWindow || this.editing) return;
    this.editing = true;
    this.$emit('add-record', {
      isGridView: this.gridView
    });
    this.eventBus.$emit('add-record', {
      isGridView: this.gridView
    });
  }

  public copyRecord() {
    if (!this.activeWindow || this.editing) return;
    this.editing = true;
    this.$emit('copy');
  }

  public saveRecord() {
    if (!this.activeWindow || !this.editing) return;
    this.$emit('save');
  }

  public deleteRecord() {
    if (!this.activeWindow) return;
    this.$emit('delete', {
      isGridView: this.gridView
    });
  }

  public exportRecord() {
    if (!this.activeWindow) return;
    this.eventBus.$emit('export-record', {
      isGridView: this.gridView
    });
  }

  public switchView() {
    this.toggleView(!this.gridView);
  }

  public goToParentTab() {
    if (!this.activeWindow || this.atWindowRoot || this.editing) return;
    this.$emit('tab-navigate', {
      direction: -1
    });
  }

  public goToChildTab() {
    if (!this.activeWindow || this.atLastTab || this.editing) return;
    this.$emit('tab-navigate', {
      direction: 1
    });
  }

  public cancelOperation() {
    if (!this.activeWindow) return;
    this.editing = false;
    this.$emit('cancel');
  }

  /**
   * Toggles between grid and details mode.
   * @param gridView Is grid mode.
   */
  private toggleView(gridView: boolean) {
    if (! this.activeWindow) return;
    this.gridView = gridView;
    this.$emit('toggle-view', {
      gridView
    });
  }

  private get activeWindow() {
    return this.fullPath === this.$route.fullPath;
  }
}