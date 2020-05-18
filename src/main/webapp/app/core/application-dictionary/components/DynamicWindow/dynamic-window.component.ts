import { Component, Vue, ProvideReactive, Inject, Watch } from 'vue-property-decorator'
import { Splitpanes, Pane } from 'splitpanes'
import 'splitpanes/dist/splitpanes.css'
import ActionToolbar from '../ActionToolbar/action-toolbar.vue'
import DetailView from "../DetailView/detail-view.vue";
import GridView from "../GridView/grid-view.vue";

import ADTabService from '@/entities/ad-tab/ad-tab.service';
import { IADTab, ADTab } from '@/shared/model/ad-tab.model';
import buildCriteriaQueryString from '@/shared/filter/filters';
import ADColumnService from '@/entities/ad-column/ad-column.service';
import _ from 'lodash';
import { ActionToolbarEventBus } from '../ActionToolbar/action-toolbar-event-bus';

@Component({
  components: {
    Splitpanes,
    Pane,
    ActionToolbar,
    DetailView,
    GridView
  }
})
export default class DynamicWindow extends Vue {
  @Inject('aDTabService')
  private aDTabService: () => ADTabService;

  @Inject('aDColumnService')
  private aDColumnService: () => ADColumnService;

  public windowId: number = 0;
  public title: string = null;
  public gridView = true;
  public childTabs = [];
  public currentTab: string = '';
  public mainTabBaseApiUrl: string = null;

  /**
   * Stack of the main tab. The last item in the stack
   * is considered main tab.
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

  // Start of lifecycle events.
  created() {
    this.windowId = this.$route.meta.windowId;
    this.title = this.$t(`route.${this.$route.meta.title}`).toString();
    this.retrieveTabs(null);
  }

  mounted() {
    ActionToolbarEventBus.$on('tab-navigate', this.navigateTab);
  }

  beforeDestroy() {
    ActionToolbarEventBus.$off('tab-navigate', this.navigateTab);
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
  public switchView(value: boolean) {
    this.gridView = value;
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
  private navigateTab(value: number) {
    if (value < 0 && this.tabStack.length > 1) {
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

  public handleTabClick(tab: any) {
    const data = tab.$el.dataset;
    const tabId = parseInt(data.tabId);
    const parentTabId = parseInt(data.parentTabId);
    const parentRecordId = parseInt(data.parentRecordId);
    const parentTableName = data.parentTableName;
    this.childTabs = [];
    this.currentTab = '';
    this.mainTabBaseApiUrl = null;
    this.retrieveTabs(parentTabId, tabId, (tab, index) => {
      this.buildChildTabFilterQuery(tab, index, parentRecordId, parentTableName);
    });
  }

  private async buildChildTabFilterQuery(tab: any, index: number, parentId: number, parentTableName?: string): Promise<void> {
    // Transient fields.
    tab.foreignColumnName = await this.getForeignColumn(tab.adTableId, parentTableName);
    tab.parentId = parentId;

    tab.filterQuery = buildCriteriaQueryString([
      `${tab.foreignColumnName}.equals=${parentId}`,
      tab.filterQuery
    ]);
  }

  /**
   * This method is called once the parent tab record row changed.
   * @param parent parent tab record.
   */
  public async loadChildTab(parent: any) {
    if (this.childTabs.length === 0 && !this.loadingChildTabs) {
      this.loadingChildTabs = true;
      this.retrieveTabs(this.mainTab.id, (tab, index) => {
        this.buildChildTabFilterQuery(tab, index, parent.id);
      });
    } else {
      if (!this.currentTab.length) {
        return;
      }
      const index = parseInt(this.currentTab);
      let tab = this.childTabs[index];
      tab.parentId = parent.id;
      tab.filterQuery = buildCriteriaQueryString([
        `${tab.foreignColumnName}.equals=${parent.id}`
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
      .then((res) => {
        const tabs = res.data;
        for (let [index, tab] of tabs.entries()) {
          if (callback) {
            callback(tab, index);
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
        this.loadingChildTabs = false;
      });
  }

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