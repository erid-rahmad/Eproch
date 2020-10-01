import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { Component, Vue } from "vue-property-decorator";

const TabToolbarProps = Vue.extend({
  props: {
    disabled: Boolean,
    tabId: String,
    eventBus: {
      type: Object,
      default: () => {
        return null;
      }
    }
  }
});

@Component
export default class TabToolbar extends TabToolbarProps {
  authorities: Set<string> = accountStore.authorities;
  recordCount = 0;
  private editing: boolean = false;
  private fullPath: string = '';

  get isEditing() {
    return this.editing;
  }

  // TODO Handles accidental/multiple key invocation.
  get keymap() {
    return {
      'shift+alt+a': this.addRecord,
      'shift+alt+d': this.copyRecord,
      'shift+alt+s': this.saveRecord,
      'shift+alt+del': this.deleteRecord,
      'shift+alt+z': this.cancelOperation
    };
  }

  created() {
    this.fullPath = this.$route.fullPath;
    this.eventBus?.$on('record-saved', this.onSaveSuccess);
  }

  beforeDestroy() {
    this.eventBus?.$off('record-saved', this.onSaveSuccess);
  }

  public activateInlineEditing() {
    this.editing = true;
  }

  private onSaveSuccess() {
    this.editing = false;
  }

  public addRecord() {
    if (! this.activeWindow || this.editing) return;
    this.$emit('add-record', { tabId: this.tabId });
  }

  public copyRecord() {
    if (!this.activeWindow || this.editing) return;
    this.$emit('copy', {tabId: this.tabId});
  }

  public saveRecord() {
    if (this.activeWindow && this.editing) {
      this.$emit('save', {tabId: this.tabId});
    }
  }

  public deleteRecord() {
    if (!this.activeWindow) return;
    this.$emit('delete', {
      isGridView: true,
      tabId: this.tabId
    });
  }

  public cancelOperation() {
    if (this.activeWindow) {
      this.editing = false;
      this.$emit('cancel', {tabId: this.tabId});
    }
  }

  private get activeWindow() {
    return this.fullPath === this.$route.fullPath;
  }
}