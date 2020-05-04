import { Component, Vue, ProvideReactive, Inject } from 'vue-property-decorator'
import { Splitpanes, Pane } from 'splitpanes'
import 'splitpanes/dist/splitpanes.css'
import ActionToolbar from '../ActionToolbar/action-toolbar.vue'
import DetailView from "../DetailView/detail-view.vue";
import GridView from "../GridView/grid-view.vue";

import DynamicWindowService from './dynamic-window.service'
import ADTabService from '@/entities/ad-tab/ad-tab.service';

@Component({
  components: {
    Splitpanes,
    Pane,
    ActionToolbar,
    DetailView,
    GridView
  },
  inject: []
})
export default class DynamicWindow extends Vue {
  public title: string = null;
  public baseApiUrl = null;
  public gridView = true;

  @ProvideReactive()
  public dynamicWindowService = (baseApiUrl: string) => new DynamicWindowService(baseApiUrl);

  @Inject('aDTabService')
  private aDTabService: () => ADTabService;

  created() {
    this.title = this.$t(`route.${this.$route.meta.title}`).toString();
    this.baseApiUrl = this.$route.meta.baseApiUrl;
  }

  public prepareNew() {
    
  }

  public switchView(value: boolean) {
    this.gridView = value;
  }

  public updateHeight() {
    (<any>this.$refs.mainGrid).syncHeight();
    (<any>this.$refs.lineGrid).syncHeight();
  }

  /**
   * Retrieve header/main tab which has no parent ID.
   */
  private retrieveHeaderTab(): void {
    this.aDTabService()
      .retrieveWithFilter({
        'parentTabId.specified': false
      })
      .then((res) => {
        console.log('response: %O', res);
      });
  }
}