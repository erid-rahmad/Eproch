import ADColumnService from '@/entities/ad-column/ad-column.service';
import ADTabService from '@/entities/ad-tab/ad-tab.service';
import ADWindowService from '@/entities/ad-window/ad-window.service';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { ADTab, IADTab } from '@/shared/model/ad-tab.model';
import { getValidatorType } from '@/utils/validate';
import _, { debounce } from 'lodash';
import { Pane, Splitpanes } from 'splitpanes';
import 'splitpanes/dist/splitpanes.css';
import { Component, Inject, Mixins, Vue, Watch } from 'vue-property-decorator';
import { mapActions } from 'vuex';
import ActionToolbar from '../ActionToolbar/action-toolbar.vue';
import TabToolbar from "../ActionToolbar/tab-toolbar.vue";
import ContextVariableAccessor from "../ContextVariableAccessor";
import DetailView from "../DetailView/detail-view.vue";
import GridView from "../GridView/grid-view.vue";
import SearchPanel from "../SearchPanel/search-panel.vue";
import TreeView from '../TreeView/tree-view.vue';
import TriggerParameterForm from "../TriggerParameterForm/trigger-parameter-form.vue";
import DynamicWindowService from './dynamic-window.service';


@Component({
  components: {
    Splitpanes,
    Pane,
    ActionToolbar,
    TabToolbar,
    DetailView,
    GridView,
    TreeView,
    SearchPanel,
    TriggerParameterForm
  },

  methods: {
    ...mapActions({
      removeWindowState: 'windowStore/removeWindow'
    })
  }
})
export default class DynamicWindow extends Mixins(ContextVariableAccessor) {
  @Inject('aDWindowService')
  private aDWindowService: () => ADWindowService;
  
  @Inject('aDTabService')
  private aDTabService: () => ADTabService;

  @Inject('aDColumnService')
  private aDColumnService: () => ADColumnService;

  @Inject('dynamicWindowService')
  private dynamicWindowService: (baseApiUrl: string) => DynamicWindowService;

  private removeWindowState!: (path: string) => Promise<void>;
  private debouncedUpdateChildTabs: (data: any) => void;

  public windowId: number = 0;
  private windowType = null;
  public gridView = true;
  public treeView = false;
  public childTabs = [];
  public currentTab: string = '';
  public previousTab: string = '';
  public triggerModel: any = {};

  // Event buses.
  mainToolbarEventBus = new Vue();
  secondaryToolbarEventBus = new Vue();
  private searchPanelEventBus = new Vue();
  private searchPanelActive: boolean = false;
  private fullPath: string = '';
  private unwatchStore: Function;

  totalRecords: number = 0;
  currentRecord: any = {};
  currentRecordNo: number = 1;
  isEditing: boolean = false;
  deleteConfirmationVisible: boolean = false;
  deleteOptions: any = null;

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
  get firstTab() {
    return this.tabStack[0] || new ADTab();
  }

  get mainTab() {
    if (this.tabStack.length === 0) {
      return new ADTab();
    }
    return this.tabStack[this.tabStack.length - 1];
  }

  get hasChildTabs() {
    return this.childTabs.length > 0;
  }

  get title() {
    const text: string = this.$route.meta.title;
    const translationKey = `route.${text}`;
    return this.$te(translationKey) ? this.$t(translationKey) : text;
  }
  // End of computed properties.

  @Watch('$route')
  onRouteChanged(route: any) {
    if (route.fullPath === this.$route.fullPath) {
      console.log('Update window %s', route.meta.title);
    }
  }

  @Watch('mainTab')
  mainTabChanged(tab) {
    if (!tab.toolbarButtons) {
      this.retrieveButtons(tab);
    }
  }

  created() {
    this.fullPath = this.$route.fullPath;
    this.windowId = this.$route.meta.windowId;
    this.unwatchStore = this.$store.watch(
      (state) => state.tagsViewStore.deletedViews,
      (views: string) => {
        if (views.includes(this.fullPath)) {
          this.$destroy();
        }
      }
    );

    this.debouncedUpdateChildTabs = debounce(this.updateChildTabs, 500);
    
    this.retrieveWindowDetail()
      .then((res) => {
        this.windowType = res.type;
        this.treeView = res.treeView;
        this.retrieveTabs(null);
      });
  }

  beforeDestroy() {
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

  public onAddRecord(data?: any) {
    if (! data?.tabId) {
      (<any>this.$refs.mainGrid).addBlankRow();
    } else if (data.tabId === this.currentTab) {
      const index = parseInt(data.tabId);
      this.$refs.lineGrid[index].addBlankRow();
    }
  }

  public onCopyRecord(data?: any) {
    console.log('copy record', data);
    if (! data?.tabId) {
      console.log('copy header');
      (<any>this.$refs.mainGrid).copyRow();
    } else if (data.tabId === this.currentTab) {
      console.log('copy line', data.tabId);
      const index = parseInt(data.tabId);
      this.$refs.lineGrid[index].copyRow();
    }
  }

  public onActionCanceled(data?: any) {
    this.closeSearchPanel();
    if (! data?.tabId) {
      (<any>this.$refs.mainGrid).cancelOperation();
      (<any>this.$refs.mainForm).exitEditMode();
    } else if (data.tabId === this.currentTab) {
      const index = parseInt(data.tabId);
      (<any>this.$refs.lineGrid[index]).cancelOperation();
    }
  }

  public onRecordSave(data: any) {
    if (data?.tabId) {
      if (data.tabId === this.currentTab) {
        const index = parseInt(data.tabId);
        (<any>this.$refs.lineGrid[index]).beforeSave();
      }
    } else if (this.gridView) {
      (<any>this.$refs.mainGrid).beforeSave();
    } else {
      (<any>this.$refs.mainForm).beforeSave();
    }
  }

  public onProcessCompleted(response: any) {
    this.refreshWindow();
  }

  public onCurrentRecordChange(no: number) {
    (<any>this.$refs.mainGrid)?.setSelectedRecordNo(no);
  }

  public onEditModeChanged(isEditing: boolean) {
    this.isEditing = isEditing;
  }

  public quitEditMode() {
    (<any>this.$refs.mainToolbar).editMode = false;
  }

  public onTotalCountChange(count: number) {
    this.totalRecords = count;
  }

  public showDeleteConfirmation(options: any) {
    this.deleteConfirmationVisible = true;
    this.deleteOptions = options;
  }

  public hideDeleteConfirmation() {
    this.deleteConfirmationVisible = false;
  }

  public deleteRecords() {
    if (this.deleteOptions.tabId) {
      const index = parseInt(this.deleteOptions.tabId);
      (<any>this.$refs.lineGrid[index]).deleteRecords();
    } else {
      (<any>this.$refs.mainGrid).deleteRecords();
    }
  }

  /**
   * Navigate between parent and child tab.
   * @param value The direction of tab navigation,
   *              negative value to go to parent tab, otherwise go to children tab.
   */
  public onTabNavigated(options: any) {
    (<any>this.$refs.mainGrid).clearFilterQueryTmp();

    if (options.direction < 0 && this.tabStack.length > 1) {
      this.childTabs = [];
      this.currentTab = '';
      this.tabStack.splice(-1, 1);
    } else {
      if (!this.currentTab.length) return;
      // Current selected child tab index.
      const index = parseInt(this.currentTab);
      const tab = this.$refs.tabPane[index];
      this.handleTabClick(tab);
    }
  }

  onNavigationBreadcrumbClicked(e) {
    const target = e.target;
    let index = 0;

    if (target.className === 'el-breadcrumb__inner') {
      const item = target.closest('.el-breadcrumb__item');
      index = parseInt(item.dataset.index);
    } else if (target.className === 'el-breadcrunb__item') {
      index = parseInt(target.dataset.index);
    }

    if (index < this.tabStack.length - 1) {
      const removedChildCount = index + 1;
      (<any>this.$refs.mainGrid).clearFilterQueryTmp();
      this.childTabs = [];
      this.currentTab = '';
      this.tabStack.splice(removedChildCount, this.tabStack.length - removedChildCount);
    }
  }

  public openSearchPanel() {
    this.searchPanelActive = true;
  }

  public closeSearchPanel() {
    this.searchPanelActive = false;
  }

  public refreshWindow() {
    this.reloadTreeView();
    (<any>this.$refs.mainGrid).clear();
  }

  public runTrigger(model: any) {
    this.triggerModel = model;
    (<any> this.$refs.triggerForm).displayed = true;
  }

  public reloadTreeView() {
    if (this.treeView) {
      (<any>this.$refs.treeView).reload();
    }
  }

  public applyFilter(query: string) {
    (<any>this.$refs.mainGrid).filterRecord(query);
    this.closeSearchPanel();
  }

  public clearFilter() {
    this.applyFilter('');
  }

  public handleTabClick(tab: any) {
    if (tab.name !== this.previousTab) {
      this.previousTab = tab.name;
      return;
    }

    // Push the current child tab to the main tab.
    const { id, parentTabId, parentId, parentTableName } = tab.$children[1].tab;
    this.childTabs = [];
    this.currentTab = '';
    this.retrieveTabs(parentTabId, id, async (childTab: any, index: number) => {
      childTab.promoted = true;
      await this.buildChildTabFilterQuery(childTab, index, parentId, parentTableName);
    });
  }

  private async buildChildTabFilterQuery(tab: any, index: number, parentId: number, parentTableName?: string) {
    // Transient fields.
    tab.foreignColumnName = await this.getForeignColumn(tab.adTableId, parentTableName);
    tab.parentId = parentId;

    // Ensure to link the child tab with exactly its parent.
    tab.nativeFilterQuery = tab.filterQuery;
    tab.filterQuery = buildCriteriaQueryString([
      `${tab.foreignColumnName}.equals=${parentId}`,
      tab.nativeFilterQuery // Include current query.
    ]);
  }

  /**
   * This method is called once the parent tab record row changed.
   * @param parent parent tab record.
   */
  public async onMainRecordChange({data, recordNo}) {
    this.currentRecord = data;
    this.currentRecordNo = recordNo;
    this.debouncedUpdateChildTabs(data);
  }

  /**
   * Update the children tabs based on the selected header record.
   * @param data parent tab record.
   */
  private updateChildTabs(data: any) {
    if (this.childTabs.length === 0 && !this.loadingChildTabs) {
      this.loadingChildTabs = true;
      this.retrieveTabs(this.mainTab.id, (tab: any, index: number) => {
        this.buildChildTabFilterQuery(tab, index, data?.id);
      });
    } else {
      if (!this.currentTab.length || data === null) {
        return;
      }
      this.childTabs.forEach((tab, index) => {
        tab.parentId = data.id;

        // Ensure to link each child tab with exactly its parent.
        tab.filterQuery = buildCriteriaQueryString([
          `${tab.foreignColumnName}.equals=${data.id}`,
          tab.nativeFilterQuery
        ]);

        // Use Vue.$set to notify array update.
        this.$set(this.childTabs, index, tab);
      });
    }
  }

  private async retrieveWindowDetail(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.aDWindowService()
        .find(this.windowId)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        })
    });
  }

  /**
   * Retrieve tab(s).
   * @param parentTabId Main/header tab don't have it.
   */
  private retrieveTabs(...args: any[]) {
  /* private retrieveTabs(parentTabId?: number, tabId?: number, callback?: (tab: IADTab, index: number) => void): void { */
    let parentTabId: number;
    let tabId: number;
    let callback: (tab: IADTab, index: number) => Promise<void>;

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
            // Invoke the callback for modifying the tab if necessary.
            await callback(tab, index);
          }

          tab.validationSchema = this.buildValidationSchema(tab.adfields);

          if (parentTabId && !tabId) {
            // Child has always a parent.
            if (index === 0) {
              this.currentTab = '' + index;
              this.previousTab = this.currentTab;
            }
            this.childTabs.push(tab);
          } else {
            // Header tab is immediatelly pushed to the tab stack.
            this.tabStack.push(tab);
          }
        }
      })
      .finally(() => {
        this.loadingChildTabs = false;
      });
  }

  private buildValidationSchema(fields: any[]) {
    let validationSchema = {};

    for (let field of fields) {
      const column = field.adColumn;
      
      validationSchema[column.name] = {
        type: getValidatorType(column.type),
        required: column.mandatory
      }

      if (column.formatPattern) {
        validationSchema[column.name].pattern = column.formatPattern;
      }

      if (column.minLength) {
        validationSchema[column.name].min = column.minLength;
      }

      if (column.maxLength) {
        validationSchema[column.name].max = column.maxLength;
      }

      if (column.minValue) {
        validationSchema[column.name].min = column.minValue;
      }

      if (column.maxValue) {
        validationSchema[column.name].max = column.maxValue;
      }
    }

    return validationSchema;
  }

  private retrieveButtons(tab: any) {
    const tabIndex = this.tabStack.indexOf(tab);

    this.dynamicWindowService('/api/ad-buttons')
      .retrieve({
        criteriaQuery: [
          `adTabId.equals=${tab.id}`,
          'toolbar.equals=true',
          'active.equals=true'
        ]
      })
      .then((res) => {
        tab.toolbarButtons = res.data;
        this.$set(this.tabStack, tabIndex, tab);
      })

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