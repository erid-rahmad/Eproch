import { Component, Prop, Vue } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { ActionToolbarEventBus } from './action-toolbar-event-bus';

const ActionToolbarProps = Vue.extend({
  props: {
    atWindowRoot: {
      type: Boolean,
      default: true
    },
    atLastTab: {
      type: Boolean,
      default: true
    }
  }
});

@Component
export default class ActionToolbar extends ActionToolbarProps {
  dynamicWindowService: DynamicWindowService = null;
  gridView = true;
  authorities: Set<string> = accountStore.authorities;

  created() {

  }

  beforeDestroy() {

  }

  public refreshData() {
    ActionToolbarEventBus.$emit('refresh-data');
  }

  public openSearchWindow() {
    ActionToolbarEventBus.$emit('open-search-window');
  }

  public addRecord() {
    ActionToolbarEventBus.$emit('add-record');
  }

  public copyRecord() {
    ActionToolbarEventBus.$emit('copy-record');
  }

  public saveRecord() {
    ActionToolbarEventBus.$emit('save-record');
  }

  public deleteRecord() {
    ActionToolbarEventBus.$emit('delete-record');
  }

  public switchView() {
    this.toggleView(!this.gridView);
  }

  public goToParentTab() {
    ActionToolbarEventBus.$emit('tab-navigate', -1);
  }

  public goToChildTab() {
    ActionToolbarEventBus.$emit('tab-navigate', 1);
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