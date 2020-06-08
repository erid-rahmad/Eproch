import { Component, Prop, Vue } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";

const TabToolbarProps = Vue.extend({
  props: {
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
  private editing: boolean = false;
  private fullPath: string = '';

  get isEditing() {
    return this.editing;
  }

  // TODO Handles accidental/multiple key invocation.
  get keymap() {
    return {
      'shift+alt+n': this.addRecord,
      'shift+alt+c': this.copyRecord,
      'shift+alt+s': this.saveRecord,
      'shift+alt+del': this.deleteRecord,
      'shift+alt+z': this.cancelOperation
    };
  }

  created() {
    this.fullPath = this.$route.fullPath;
    this.eventBus?.$on('inline-editing', this.onInlineEditing);
    this.eventBus?.$on('record-saved', this.onSaveSuccess);
  }

  beforeDestroy() {
    this.eventBus?.$off('inline-editing', this.onInlineEditing);
    this.eventBus?.$off('record-saved', this.onSaveSuccess);
  }

  private onInlineEditing(active: boolean) {
    this.editing = active;
  }

  private onSaveSuccess() {
    this.editing = false;
  }

  public addRecord() {
    if (!this.activeWindow) return;
    this.editing = true;
    this.eventBus?.$emit('add-record', {
      tabId: this.tabId
    });
  }

  public copyRecord() {
    if (!this.activeWindow) return;
    this.editing = true;
    this.eventBus?.$emit('copy-record', {
      tabId: this.tabId
    });
  }

  public saveRecord() {
    if (!this.activeWindow) return;
    this.eventBus?.$emit('save-record', {
      tabId: this.tabId
    });
  }

  public deleteRecord() {
    if (!this.activeWindow) return;
    this.eventBus?.$emit('delete-record', {
      tabId: this.tabId
    });
  }

  public cancelOperation() {
    if (!this.activeWindow) return;
    this.editing = false;
    this.eventBus?.$emit('cancel-operation', {
      tabId: this.tabId
    });
  }

  private get activeWindow() {
    return this.fullPath === this.$route.fullPath;
  }
}