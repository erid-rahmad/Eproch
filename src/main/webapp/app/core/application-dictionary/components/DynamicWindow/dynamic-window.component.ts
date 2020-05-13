import { Component, Vue, ProvideReactive, Inject, Watch } from 'vue-property-decorator'
import { Splitpanes, Pane } from 'splitpanes'
import 'splitpanes/dist/splitpanes.css'
import ActionToolbar from '../ActionToolbar/action-toolbar.vue'
import DetailView from "../DetailView/detail-view.vue";
import GridView from "../GridView/grid-view.vue";

import DynamicWindowService from './dynamic-window.service'
import ADTabService from '@/entities/ad-tab/ad-tab.service';
import { IADTab } from '@/shared/model/ad-tab.model';
import buildCriteriaQueryString from '@/shared/filter/filters';
import ADColumnService from '@/entities/ad-column/ad-column.service';

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
  public windowId: number = 0;
  public title: string = null;
  public gridView = true;
  public mainTab;
  public childTabs = [];
  public currentTab: string = '';
  public mainTabBaseApiUrl: string = null;
  public parentId: number = 0;

  @Inject('aDTabService')
  private aDTabService: () => ADTabService;

  @Inject('aDColumnService')
  private aDColumnService: () => ADColumnService;

  @Watch('currentTab')
  currentTabChanged(tabName) {
    console.log('tab changed to %s', tabName);
  }

  created() {
    this.windowId = this.$route.meta.windowId;
    this.title = this.$t(`route.${this.$route.meta.title}`).toString();
    this.retrieveTabs(null);
  }

  mainTabFields() {
    return this.mainTab?.adfields || [];
  }

  get hasChildTabs() {
    return this.childTabs.length > 0;
  }

  public prepareNew() {
    
  }

  public switchView(value: boolean) {
    this.gridView = value;
  }

  public updateHeight() {
    (<any>this.$refs.mainGrid).syncHeight();

    if (this.hasChildTabs) {
      (<Array<any>>this.$refs.lineGrid).forEach(grid => {
        grid.syncHeight();
      });
    }
  }

  private async buildChildTabFilterQuery(tab: any, index: number, parentId: number): Promise<void> {
    // Transient fields.
    tab.foreignColumnName = await this.getForeignColumn(tab.adTableId);
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
    if (this.childTabs.length === 0) {
      this.retrieveTabs(this.mainTab.id, (tab, index) => {
        this.buildChildTabFilterQuery(tab, index, parent.id);
      });
    } else {
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
  private retrieveTabs(parentTabId: number, callback?: (tab: IADTab, index: number) => void): void {
    let params = {
      'adWindowId.equals': this.windowId,
    };

    if (parentTabId) {
      params['parentTabId.equals'] = parentTabId;
    } else {
      params['parentTabId.specified'] = false;
    }

    this.aDTabService()
      .retrieveWithFilter(params)
      .then((res) => {
        const tabs = res.data;
        for (let [index, tab] of tabs.entries()) {
          if (parentTabId) {
            // Child always has parent.
            if (callback) callback(tab, index);
            if (index === 0) {
              this.currentTab = '' + index;
            }
            this.childTabs.push(tab);
          } else {
            this.mainTab = tab;
            this.mainTabBaseApiUrl = tab.targetEndpoint;
          }
        }
      });
  }

  private async getForeignColumn(tableId: number) {
    const response = await this.aDColumnService()
      .retrieveWithFilter({
        'foreignKey.equals': true,
        'importedTable.equals': this.mainTab.adTableName,
        'adTableId.equals': tableId
      });

    return response?.data[0]?.name;
  }
}