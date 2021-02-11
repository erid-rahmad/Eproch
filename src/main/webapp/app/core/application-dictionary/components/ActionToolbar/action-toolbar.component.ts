import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { Component, Vue, Watch, Inject } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { ADWindowType } from '@/shared/model/ad-window.model';
import { ElDropdown } from 'element-ui/types/dropdown';

const ActionToolbarProps = Vue.extend({
  props: {
    windowType: String,
    windowName: String,
    approved: Boolean,
    documentTypeId: Number,
    nextDocumentAction: String,
    documentType: {
      type: Object,
      default: () => {
        return {};
      }
    },
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

  @Inject('dynamicWindowService')
  protected dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  gridView = true;
  authorities: Set<string> = accountStore.authorities;
  documentActions: Array<any> = [];
  private editing: boolean = false;
  private fullPath: string = '';

  runProcess(triggerDef: any) {
    this.$emit('run-trigger', triggerDef);
  }

  private get activeWindow() {
    return this.fullPath === this.$route.fullPath;
  }

  get defaultDocumentAction() {
    if (this.approved) {
      return 'Approved';
    }
    const docAction = this.documentActions.find(action => action.value === this.nextDocumentAction);
    return docAction?.name || this.nextDocumentAction;
  }

  get isEditing() {
    return this.editing;
  }

  get hasDocumentActions() {
    return this.transactionWindow && this.documentActions.length;
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
      'alt+z': this.cancelOperation,
      'alt+i': this.importRecord,
      'alt+e': this.exportRecord
    };
  }

  public get editMode() {
    return this.editing;
  }

  public set editMode(editing) {
    this.editing = editing;
  }

  @Watch('approved')
  onApprovedChange(approved: boolean) {
    this.$nextTick(() => {
      (<ElDropdown>this.$refs.docActionButton)?.$el
        ?.querySelectorAll('button')
        .forEach(button => {
          button.disabled = approved;
          button.classList[approved ? 'add' : 'remove']('is-disabled');
        });
    });
  }

  @Watch('editing')
  onEditingChange(editing: boolean) {
    this.$emit('edit-mode-change', editing);
  }

  @Watch('documentTypeId')
  onDocumentTypeIdChange(id: number) {
    if (this.windowType === ADWindowType.TRANSACTION) {
      this.retrieveDocumentActions(id);
    }
  }

  created() {
    this.fullPath = this.$route.fullPath;
    this.eventBus.$on('record-saved', this.onSaveSuccess);
  }

  beforeDestroy() {
    this.eventBus.$off('record-saved', this.onSaveSuccess);
  }

  public isActionDisabled(action) {
    return this.approved && (action.value === 'RJC' || action.value === 'APV')
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
    const docAction = this.documentActions.find(action => action.value === this.nextDocumentAction);
    this.applyDocumentAction(docAction);
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

  public importRecord() {
    if (this.activeWindow && !this.editing) {
      this.$emit('import');
    }
  }

  public exportRecord() {
    if (this.activeWindow && !this.editing) {
      this.$emit('export');
    }
  }

  public printRecord() {
    console.log(this.windowType);
    if (!this.activeWindow) return;
    this.eventBus.$emit('print-record', {
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

  private retrieveDocumentActions(docTypeId: number) {
    if (accountStore.grantedDocActions.has(docTypeId)) {
      const referenceListIds = [...accountStore.grantedDocActions.get(docTypeId)];

      this.dynamicWindowService('/api/ad-reference-lists')
      .retrieve({
        criteriaQuery: referenceListIds.map(id => `id.in=${id}`)
      })
      .then(res => {
        this.documentActions = res.data
      });
    }
  }
}
