import { Component, Prop, Vue } from "vue-property-decorator";
import DynamicWindowService from '../DynamicWindow/dynamic-window.service';
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";

@Component
export default class ActionToolbar extends Vue {
  dynamicWindowService: DynamicWindowService = null;
  gridView = true;
  authorities: Set<string> = accountStore.authorities;

  created() {

  }

  beforeDestroy() {

  }

  public onClickNew() {
    this.toggleView(false);
  }

  public onClickCopy() {

  }

  public onClickSave() {

  }

  public onClickDelete() {

  }

  public switchView() {
    this.toggleView(!this.gridView);
  }

  private toggleView(gridView: boolean) {
    this.gridView = gridView;
    this.$emit('toggle-view', gridView);
  }
}