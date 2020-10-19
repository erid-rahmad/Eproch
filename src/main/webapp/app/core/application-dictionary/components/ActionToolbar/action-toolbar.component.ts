import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { Component, Vue, Watch } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ADWindowType } from '@/shared/model/ad-window.model';

const ActionToolbarProps = Vue.extend({
  props: {
    windowType: String,
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

  get actions() {
    return [
      {name: 'Approve', value: 'APPROVE'},
      {name: 'Cancel', value: 'CANCEL'},
      {name: 'Reject', value: 'REJECT'}
    ];
  }

  get isEditing() {
    return this.editing;
  }

  get currentDocumentAction() {
    return 'Approve';
  }

  get transactionWindow() {
    return this.windowType === ADWindowType.TRANSACTION;
  }

  get keymap() {
    return {
      'alt+g': this.switchView,
      'alt+up': this.goToParentTab,
      'alt+down': this.goToChildTab,
      'alt+r': this.refreshData,
      'alt+f': this.openSearchWindow,
      'alt+a': this.addRecord,
      'alt+d': this.copyRecord,
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
    this.eventBus.$on('record-saved', this.onSaveSuccess);
  }

  beforeDestroy() {
    this.eventBus.$off('record-saved', this.onSaveSuccess);
  }

  public activateInlineEditing() {
    this.editing = true;
  }

  private onSaveSuccess() {
    this.editing = false;
  }

  public applyDocumentAction(action: object) {
    this.$emit('apply-document-action', action);
  }

  public applyNextDocumentAction() {
    this.applyDocumentAction({name: 'Approve', value: 'APPROVE'});
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
    this.$emit('add-record');
  }

  public copyRecord() {
    if (!this.activeWindow || this.editing) return;
    this.$emit('copy');
  }

  public saveRecord() {
    if (this.activeWindow && this.editing) {
      this.$emit('save', {
        isGridView: this.gridView
      });
    }
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
    if (this.activeWindow) {
      this.editing = false;
      this.$emit('cancel');
    }
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