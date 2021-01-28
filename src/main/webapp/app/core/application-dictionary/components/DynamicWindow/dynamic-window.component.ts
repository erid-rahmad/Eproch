import ADColumnService from '@/entities/ad-column/ad-column.service';
import ADTabService from '@/entities/ad-tab/ad-tab.service';
import ADWindowService from '@/entities/ad-window/ad-window.service';
import buildCriteriaQueryString from '@/shared/filter/filters';
import { ADTab, IADTab } from '@/shared/model/ad-tab.model';
import { getValidatorType } from '@/utils/validate';
import { debounce, isFinite, isFunction } from 'lodash';
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
import { IADField } from '@/shared/model/ad-field.model';
import { AccountStoreModule as accountStore } from '@/shared/config/store/account-store';

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

  private removeWindowState!: (path: string) => Promise<void>;
  private debouncedUpdateChildTabs: (data: any) => void;

  public windowId: number = 0;
  windowType = null;
  windowName = null;
  accessLevel = null;
  public gridView = true;
  private childTabs: IADTab[] = [];
  public subTabs: IADTab[] = [];
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

  approved: boolean = false;
  documentTypeId: number = 0;
  nextDocumentAction: string = null;
  docAction: any = {};
  docActionMessage: string = null;
  docActionPopupVisible: boolean = false;

  /**
   * Stack of the main tab. The last item in the stack
   * is considered as main tab.
   */
  public tabStack: any[] = [];

  /**
   * Prevents load the same child tabs multiple times.
   */
  private loadingChildTabs = false;

  get isVendor() {
    return accountStore.userDetails.vendor && this.accessLevel === 'CLN_VND';
  }

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
    return this.subTabs.length > 0;
  }

  get title() {
    const text: string = this.$route.meta.title;
    const translationKey = `route.${text}`;
    return this.$te(translationKey) ? this.$t(translationKey) : text;
  }

  get treeView() {
    return this.mainTab.treeView;
  }
  // End of computed properties.

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
      .then(res => {
        this.windowType = res.type;
        this.windowName = res.name;
        this.accessLevel = res.accessLevel;
        this.retrieveTabs(null);
      });

    console.log('User details: %O', accountStore.userDetails);
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

  public applyDocumentAction() {
    const tableId = this.mainTab.adTableId;
    const tableName = this.mainTab.adTableName;
    console.log('Apply docAction[%O]. table[id: %s, name: %s], record: %O', this.docAction, tableId, tableName, this.currentRecord);
    const {
      editing,
      createdBy,
      createdDate,
      lastModifiedBy,
      lastModifiedDate,
      ...record
    } = this.currentRecord;

    record.approvalStatus = this.docAction.value;
    record.approved = true;
    record.processed = true;
    record.documentStatus = this.docAction.value;
    this.dynamicWindowService(this.mainTab.targetEndpoint)
      .applyDocAction(record)
      .then(status => {
        this.refreshWindow();
        this.docActionPopupVisible = false;
        this.$message({
          message: `Document has been successfully ${this.docAction.name.toLowerCase()}ed`,
          type: 'success'
        });
      })
      .catch(err => {
        console.error('Error updating the document status! %O', err);
        const message = `Error updating the document status`;
        this.$message({
          message: message.toString(),
          type: 'error'
        });
      });
  }

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

  public onApplyDocumentAction(action: any) {
    this.docAction = action;
    this.docActionPopupVisible = true;
  }

  public onCopyRecord(data?: any) {
    if (! data?.tabId) {
      (<any>this.$refs.mainGrid).copyRow();
    } else if (data.tabId === this.currentTab) {
      const index = parseInt(data.tabId);
      this.$refs.lineGrid[index].copyRow();
    }
  }

  public onActionCanceled(data?: any) {
    this.closeSearchPanel();
    if (! data?.tabId) {
      (<any>this.$refs.mainGrid).cancelOperation();

      if (! this.gridView) {
        (<any>this.$refs.mainForm).exitEditMode();
      }
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
    this.$message.success('Process has been successfully executed');
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

    if (this.hasChildTabs && this.currentTab) {
      this.$refs.lineGrid[parseInt(this.currentTab)].clear();
    }
  }

  public runTrigger(model: any) {
    this.triggerModel = model;
    (<any> this.$refs.triggerForm).displayed = true;
  }

  public reloadTreeView() {
    if (this.mainTab.treeView) {
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

    if (this.windowType === 'TRANSACTION' && this.tabStack.length === 1) {
      this.approved = data.approved;
      this.documentTypeId = data.documentTypeId;
      this.nextDocumentAction = data.documentAction;
    }

    this.debouncedUpdateChildTabs(data);
  }

  /**
   * Update the children tabs based on the selected header record.
   * @param data parent tab's record.
   */
  private async updateChildTabs(data: any) {
    if (this.childTabs.length === 0 && !this.loadingChildTabs) {
      this.loadingChildTabs = true;
      await this.retrieveTabs(this.mainTab.id, (tab: any, index: number) => {
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

    this.subTabs = this.childTabs.filter(tab => this.evaluateDisplayLogic({
      defaultTabId: tab.name,
      record: data,
      tab
    }));
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
  private retrieveTabs(...args: any[]): Promise<any[]> {
  /* private retrieveTabs(parentTabId?: number, tabId?: number, callback?: (tab: IADTab, index: number) => void): void { */
    let parentTabId: number;
    let tabId: number;
    let callback: (tab: IADTab, index: number) => Promise<void>;

    if (args.length === 0) {
      throw new Error('retrieveTabs needs some arguments.');
    }

    parentTabId = args[0];
    if (args[1] !== undefined) {
      if (isFunction(args[1])) {
        if (args[2] !== undefined)
          throw new Error('second argument must be a number');

        callback = args[1];
      } else if (isFinite(args[1])) {
        tabId = args[1];
      }
    }
    if (args[2] !== undefined) {
      if (! isFunction(args[2]))
        throw new Error('third argument must be a function');

      callback = args[2];
    }

    let params = {
      'adWindowId.equals': this.windowId,
      'active.equals': true
    };

    if (parentTabId) {
      params['parentTabId.equals'] = parentTabId;
    } else {
      params['parentTabId.specified'] = false;
    }

    if (tabId) {
      params['id.equals'] = tabId;
    }

    return new Promise((resolve, reject) => {
      this.aDTabService()
        .retrieveWithFilter(params, {
          page: 0,
          size: 50,
          sort: ['tabSequence,asc', 'name,asc']
        })
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
              const fullPath = this.$route.fullPath;
              const tmpFilterQuery = sessionStorage.getItem(`filterQuery__${fullPath}`);

              tab.nativeFilterQuery = tab.filterQuery;

              if (tmpFilterQuery !== null) {
                sessionStorage.removeItem(`filterQuery__${fullPath}`);
              }

              if (this.isVendor) {
                const vendorIdField = tab.adTableName === 'c_vendor' ? 'id' : 'vendorId';

                tab.filterQuery = buildCriteriaQueryString([
                  `${vendorIdField}.equals=${accountStore.userDetails.cVendorId}`,
                  tmpFilterQuery,
                  tab.nativeFilterQuery // Include current query.
                ]);
              } else {
                tab.filterQuery = buildCriteriaQueryString([
                  tmpFilterQuery, tab.nativeFilterQuery
                ]);
              }

              // Header tab is immediatelly pushed to the tab stack.
              this.tabStack.push(tab);
            }
          }
          return resolve(tabs);
        })
        .catch(err => reject(err))
        .finally(() => {
          this.loadingChildTabs = false;
        });
    });
  }

  private buildValidationSchema(fields: IADField[]) {
    let validationSchema = {};

    for (let field of fields) {
      const column = field.adColumn;
      const fieldName = field.virtualColumnName || column?.name;

      if ( ! fieldName) {
        continue;
      }

      const formatPattern = field.formatPattern || column?.formatPattern;
      const minLength = field.minLength || column?.minLength;
      const maxLength = field.maxLength || column?.maxLength;
      const minValue = field.minValue || column?.minValue;
      const maxValue = field.maxValue || column?.maxValue;

      validationSchema[fieldName] = {
        required: field.mandatory || column?.mandatory
      }

      if ( ! column?.foreignKey) {
        const type = getValidatorType(field.type || column.type);

        if (type !== 'date') {
          validationSchema[fieldName].type = type;
        }
      }

      if (formatPattern) {
        validationSchema[fieldName].pattern = formatPattern;
      }

      if (minLength) {
        validationSchema[fieldName].min = minLength;
      }

      if (maxLength) {
        validationSchema[fieldName].max = maxLength;
      }

      if (minValue) {
        validationSchema[fieldName].min = minValue;
      }

      if (maxValue) {
        validationSchema[fieldName].max = maxValue;
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
