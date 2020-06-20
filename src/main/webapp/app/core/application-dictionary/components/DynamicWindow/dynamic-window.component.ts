import { Component, Vue, Inject, Watch, Mixins } from 'vue-property-decorator';
import ContextVariableAccessor from "../ContextVariableAccessor";
import { Splitpanes, Pane } from 'splitpanes';
import 'splitpanes/dist/splitpanes.css';
import ActionToolbar from '../ActionToolbar/action-toolbar.vue';
import TabToolbar from "../ActionToolbar/tab-toolbar.vue";
import DetailView from "../DetailView/detail-view.vue";
import GridView from "../GridView/grid-view.vue";
import SearchPanel from "../SearchPanel/search-panel.vue";

import ADTabService from '@/entities/ad-tab/ad-tab.service';
import { IADTab, ADTab } from '@/shared/model/ad-tab.model';
import buildCriteriaQueryString from '@/shared/filter/filters';
import ADColumnService from '@/entities/ad-column/ad-column.service';
import { TagsViewStoreModule as tagsViewStore } from "@/shared/config/store/tags-view-store";
import _ from 'lodash';
import { mapActions } from 'vuex';
import { ElPagination } from 'element-ui/types/pagination';

@Component({
  components: {
    Splitpanes,
    Pane,
    ActionToolbar,
    TabToolbar,
    DetailView,
    GridView,
    SearchPanel
  },

  methods: {
    ...mapActions({
      removeWindowState: 'windowStore/removeWindow'
    })
  }
})
export default class DynamicWindow extends Mixins(ContextVariableAccessor) {
  @Inject('aDTabService')
  private aDTabService: () => ADTabService;

  @Inject('aDColumnService')
  private aDColumnService: () => ADColumnService;

  public windowId: number = 0;
  public title: string = null;
  public gridView = true;
  public childTabs = [];
  public currentTab: string = '';

  // Event buses.
  private mainToolbarEventBus = new Vue();
  private secondaryToolbarEventBus = new Vue();
  private searchPanelEventBus = new Vue();
  
  private searchPanelActive: boolean = false;
  private fullPath: string = '';
  private unwatchStore: Function;

  private totalRecords: number = 0;
  private currentRecordNo: number = 1;
  isEditing: boolean = false;

  removeWindowState!: (path: string) => Promise<void>;

  /**
   * Stack of the main tab. The last item in the stack
   * is considered as main tab.
   */
  public tabStack: any[] = [];

  /**
   * Prevents load the same child tabs multiple times.
   */
  private loadingChildTabs = false;

  // Start of computed properties.
  get mainTab() {
    if (this.tabStack.length === 0) {
      return new ADTab();
    }
    return this.tabStack[this.tabStack.length - 1];
  }

  get hasChildTabs() {
    return this.childTabs.length > 0;
  }
  // End of computed properties.

  @Watch('currentTab')
  currentTabChanged(tabName) {
  }

  created() {
    this.fullPath = this.$route.fullPath;
    this.windowId = this.$route.meta.windowId;
    this.title = this.$t(`route.${this.$route.meta.title}`).toString();
    this.unwatchStore = this.$store.watch(
      (state) => state.tagsViewStore.deletedViews,
      (views: string) => {
        if (views.includes(this.fullPath)) {
          this.$destroy();
        }
      }
    );

    this.mainToolbarEventBus.$on('tab-navigate', this.navigateTab);
    this.mainToolbarEventBus.$on('open-search-window', this.openSearchPanel);
    this.mainToolbarEventBus.$on('cancel-operation', this.cancelOperation);
    this.searchPanelEventBus.$on('close-search-window', this.cancelOperation);

    this.retrieveTabs(null);
  }

  beforeDestroy() {
    this.mainToolbarEventBus.$off('tab-navigate', this.navigateTab);
    this.mainToolbarEventBus.$off('open-search-window', this.openSearchPanel);
    this.mainToolbarEventBus.$off('cancel-operation', this.cancelOperation);
    this.searchPanelEventBus.$off('close-search-window', this.cancelOperation);
    this.removeWindowState(this.fullPath);
    this.unwatchStore();
  }
  // End of lifecycle events.

  // Start of reactive methods.
  public mainTabName() {
    return this.mainTab?.name;
  }

  public mainTabFields() {
    return this.mainTab?.adfields || [];
  }

  public mainTabFilterQuery() {
    return this.mainTab?.filterQuery;
  }

  public parentRecordId() {
    return this.mainTab?.parentId || 0;
  }
  // End of reactive methods.

  /**
   * Switch between grid and detail view of the main tab.
   * @param value Either true for grid view or false for detail view.
   */
  public switchView(options: any) {
    this.gridView = options.gridView;
  }

  public onCurrentRecordChange(no: number) {
    (<any>this.$refs.mainGrid)?.setSelectedRecordNo(no);
  }

  public onEditModeChange(isEditing: boolean) {
    this.isEditing = isEditing;
  }

  public onTotalCountChange(count: number) {
    this.totalRecords = count;
  }

  /**
   * grid-view's height need to be updated to maintain its fixed header position.
   */
  public updateHeight() {
    (<any>this.$refs.mainGrid)?.syncHeight();

    if (this.hasChildTabs) {
      (<Array<any>>this.$refs.lineGrid).forEach(grid => {
        grid.syncHeight();
      });
    }
  }

  /**
   * Navigate between parent and child tab.
   * @param value The direction of tab navigation,
   *              negative value to go to parent tab, otherwise go to children tab.
   */
  private navigateTab(options: any) {
    if (options.direction < 0 && this.tabStack.length > 1) {
      this.childTabs = [];
      this.currentTab = '';
      this.tabStack.splice(-1, 1);
    } else {
      if (!this.currentTab.length) return;
      const index = parseInt(this.currentTab);
      const tab = this.$refs.tabPane[index];
      this.handleTabClick(tab);
    }
  }

  private openSearchPanel() {
    this.searchPanelActive = true;
  }

  private cancelOperation() {
    this.searchPanelActive = false;
  }

  public handleTabClick(tab: any) {
    const { id, parentTabId, parentId, parentTableName } = tab.$children[1].tab;
    this.childTabs = [];
    this.currentTab = '';
    this.retrieveTabs(parentTabId, id, async (tab: any, index: number) => {
      tab.promoted = true;
      await this.buildChildTabFilterQuery(tab, index, parentId, parentTableName);
    });
  }

  private async buildChildTabFilterQuery(tab: any, index: number, parentId: number, parentTableName?: string): Promise<void> {
    // Transient fields.
    tab.foreignColumnName = await this.getForeignColumn(tab.adTableId, parentTableName);
    tab.parentId = parentId;

    // Ensure to link the child tab with exactly its parent.
    tab.filterQuery = buildCriteriaQueryString([
      `${tab.foreignColumnName}.equals=${parentId}`,
      tab.filterQuery
    ]);
  }

  /**
   * This method is called once the parent tab record row changed.
   * @param parent parent tab record.
   */
  public async loadChildTab({data, recordNo}) {
    this.currentRecordNo = recordNo;
    if (this.childTabs.length === 0 && !this.loadingChildTabs) {
      this.loadingChildTabs = true;
      this.retrieveTabs(this.mainTab.id, (tab: any, index: number) => {
        this.buildChildTabFilterQuery(tab, index, data?.id);
      });
    } else {
      if (!this.currentTab.length || data === null) {
        return;
      }
      const index = parseInt(this.currentTab);
      let tab = this.childTabs[index];
      tab.parentId = data.id;
      tab.filterQuery = buildCriteriaQueryString([
        `${tab.foreignColumnName}.equals=${data.id}`
      ]);

      // Use Vue.$set to notify array update.
      this.$set(this.childTabs, index, tab);
    }
  }

  /**
   * Retrieve tab(s).
   * @param parentTabId Main/header tab don't have it.
   */
  private retrieveTabs(...args: any[]) {
  /* private retrieveTabs(parentTabId?: number, tabId?: number, callback?: (tab: IADTab, index: number) => void): void { */
    let parentTabId: number;
    let tabId: number;
    let callback: (tab: IADTab, index: number) => void;

    if (args.length === 0) {
      throw new Error('retrieveTabs needs some arguments.');
    }

    parentTabId = args[0];
    if (args[1] !== undefined) {
      if (_.isFunction(args[1])) {
        if (args[2] !== undefined)
          throw new Error('second argument must be a number');

        callback = args[1];
      } else if (_.isFinite(args[1])) {
        tabId = args[1];
      }
    }
    if (args[2] !== undefined) {
      if (!_.isFunction(args[2]))
        throw new Error('third argument must be a function');

      callback = args[2];
    }

    let params = {
      'adWindowId.equals': this.windowId,
    };

    if (parentTabId) {
      params['parentTabId.equals'] = parentTabId;
    } else {
      params['parentTabId.specified'] = false;
    }

    if (tabId) {
      params['id.equals'] = tabId;
    }

    this.aDTabService()
      .retrieveWithFilter(params)
      .then(async (res) => {
        let tabs = res.data;
        for (let [index, tab] of tabs.entries()) {
          if (callback) {
            await callback(tab, index);
          }
          if (parentTabId && !tabId) {
            // Child always has parent.
            if (index === 0) {
              this.currentTab = '' + index;
            }
            this.childTabs.push(tab);
          } else {
            this.tabStack.push(tab);
          }
        }
      })
      .finally(() => {
        this.loadingChildTabs = false;
      });
  }

  /**
   * Retrieve the foreign key column name.
   * @param tableId Identifier of the table to inspect
   * @param parentTableName The imported parent table name or the main tab's table name if empty.
   */
  private async getForeignColumn(tableId: number, parentTableName?: string) {
    const response = await this.aDColumnService()
      .retrieveWithFilter({
        'foreignKey.equals': true,
        'importedTable.equals': parentTableName || this.mainTab?.adTableName,
        'adTableId.equals': tableId
      });

    return response?.data[0]?.name;
  }
}