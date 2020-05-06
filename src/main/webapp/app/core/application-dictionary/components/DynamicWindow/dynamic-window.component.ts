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
  public mainTabBaseApiUrl: string = null;

  @ProvideReactive()
  public dynamicWindowService = (baseApiUrl: string) => new DynamicWindowService(baseApiUrl);

  @Inject('aDTabService')
  private aDTabService: () => ADTabService;

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

  public loadChildTab(parent) {
    console.log('loadChildTab. parent: %O', parent);
  }

  /**
   * Retrieve header/main tab which has no parent ID.
   */
  private retrieveTabs(parentTabId: number): void {
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
        for (let tab of tabs) {
          if (parentTabId === null) {
            this.mainTab = tab;
            this.mainTabBaseApiUrl = tab.targetEndpoint
            this.retrieveTabs(tab.id)
          } else {
            tab.filterQuery = buildCriteriaQueryString([
              `parentTabId.equals=${this.mainTab.id}`,
              tab.filterQuery
            ])
            this.childTabs.push(tab);
          }
        }
      });
  }
}