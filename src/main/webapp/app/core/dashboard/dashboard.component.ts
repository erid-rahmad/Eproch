import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import WatchList from "@/core/dashboard/components/watch-list.vue";
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { IPaDashboardPreference } from '@/shared/model/pa-dashboard-preference.model';
import { Component, Inject, Vue } from 'vue-property-decorator';
import DashboardService from './dashboard.service';
import { debounce } from 'lodash';

import circularColorBar from './componentsChart/circularColorBar.vue';
import liveData from './componentsChart/liveData.vue';
import lineexample from './componentsChart/simpleLine.vue';
import lineupdate from './componentsChart/methodUpdate.vue';
import echart from './componentsChart/echart-bar.vue';

@Component({
  components: {
    WatchList,circularColorBar,liveData,lineexample,lineupdate,echart
  }
})
export default class DashBoard extends Vue {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private dashboardService: DashboardService;
  private debouncedRefresh: Function;

  dashboardItems: IPaDashboardPreference[] = [];

  get dashboards() {
    const list = [];
    for (const [key] of accountStore.grantedDashboards) {
      const keys = key.split('_');

      list.push({
        key,
        id: keys[0],
        name: keys[1]
      });
    }
    return list;
  }

  created() {
    this.dashboardService = new DashboardService(this);
    this.debouncedRefresh = debounce(this.refresh, 5000);

    if (this.dashboards.length) {
      this.switchDashboard(this.dashboards[0].key);
    }
  }

  data() {
    return {
      tableData: [{
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles'
      }, {
        date: '2016-05-02',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles'
      }, {
        date: '2016-05-04',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles'
      }, {
        date: '2016-05-01',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles'
      }, {
        date: '2016-05-08',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles'
      }, {
        date: '2016-05-06',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles'
      }, {
        date: '2016-05-07',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles'
      }],
    }
  }

  mounted() {
    this.dashboardService.connect();
    this.dashboardService.subscribe();
    this.dashboardService.receive().subscribe(() => {
      this.debouncedRefresh();
    });
  }

  beforeDestroy() {
    this.dashboardService.unsubscribe();
  }

  switchDashboard(key: string) {
    if (accountStore.grantedDashboards.has(key)) {
      const items = [...accountStore.grantedDashboards.get(key)];

      this.commonService('/api/pa-dashboard-preferences')
        .retrieve({
          criteriaQuery: [
            'active.equals=true',
            `adUserId.equals=${accountStore.userDetails.id}`,
            ...[...items].map(id => `paDashboardItemId.in=${id}`)
          ],
          paginationQuery: {
            page: 0,
            size: 50,
            sort: ['id']
          }
        })
        .then(res => {
          this.dashboardItems = res.data;
        })
        .catch(err => {
          console.log('Dashboard error', err);
          this.$message({
            message: 'Failed to load dashboard items.',
            type: 'error'
          });
        });
    }
  }

  public refresh() {
    const widgets: any[] = <any[]>this.$refs.widget;
    widgets?.forEach((widget, index) => {
      console.log('Attempt to refresh widget#%d', index);
      widget.refresh();
    });
  }
}
