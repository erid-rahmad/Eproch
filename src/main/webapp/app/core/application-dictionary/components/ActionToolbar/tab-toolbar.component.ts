import { Component, Prop, Vue } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";

const TabToolbarProps = Vue.extend({
  props: {
    tabName: String,
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

  get isEditing() {
    return this.editing;
  }

  get keymap() {
    return {
      'shift+alt+n': this.addRecord,
      'shift+alt+c': this.copyRecord,
      'shift+alt+s': this.saveRecord,
      'shift+alt+del': this.deleteRecord,
      'esc': this.cancelOperation
    };
  }

  created() {
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
    this.editing = true;
    this.eventBus?.$emit('add-record');
  }

  public copyRecord() {
    this.editing = true;
    this.eventBus?.$emit('copy-record');
  }

  public saveRecord() {
    this.eventBus?.$emit('save-record');
  }

  public deleteRecord() {
    this.eventBus?.$emit('delete-record');
  }

  public cancelOperation() {
    this.editing = false;
    this.eventBus?.$emit('cancel-operation');
  }
}