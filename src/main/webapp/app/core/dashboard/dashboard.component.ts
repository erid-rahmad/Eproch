import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import WatchList from "@/core/dashboard/components/watch-list.vue";
import { AccountStoreModule as accountStore } from "@/shared/config/store/account-store";
import { IPaDashboardPreference } from '@/shared/model/pa-dashboard-preference.model';
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
import DashboardService from './dashboard.service';
import { debounce } from 'lodash';
import draggable from 'vuedraggable';

import circularColorBar from './componentsChart/circularColorBar.vue';
import liveData from './componentsChart/liveData.vue';
import lineexample from './componentsChart/simpleLine.vue';
import lineupdate from './componentsChart/methodUpdate.vue';
import echart from './componentsChart/echart-bar.vue';
import echartpie from './componentsChart/echart-pie.vue';
import LineChart from './componentsChart/lineChart.vue';
import BarChart from './componentsChart/barChart.vue';
import PieChart from './componentsChart/pieChart.vue';
import Accordion from './components/accordion.vue';
import kpiAdmin from './components/kpi-admin.vue';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

const baseApiVendor ='api/c-vendors';
const baseApiTopVendor ='api/pa-dashboards/topVendorPurchase';
const baseApiDocument ='api/pa-dashboards/mydocument';
const baseApiEvaluation ='api/m-vendor-evaluations';

@Component({
  components: {
    WatchList,circularColorBar,liveData,lineexample,lineupdate,echart,echartpie,kpiAdmin,
    Accordion, LineChart, BarChart, PieChart, draggable
  }
})
export default class DashBoard extends  Mixins(AccessLevelMixin) {
  

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private dashboardService: DashboardService;
  private debouncedRefresh: Function;
  private dataPO:any=[];
  private dataMyDocument:any=[];
  private dataEvaluasi:any=[];

  dashboardItems: IPaDashboardPreference[] = [];
  totalRows: Number[] = [];
  
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
    this.retrievePO();
    this.dashboardService = new DashboardService(this);
    this.debouncedRefresh = debounce(this.refresh, 5000);

    if (this.dashboards.length) {
      this.switchDashboard(this.dashboards[0].key);
    }
  }

  data() {
    return {
      dragOptions:{
        animation: 200,
        group: "description",
        disabled: false,
        ghostClass: 'ghost'
      },
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
      }]
    }
  }
  
  mounted() {
    this.dashboardService.connect();
    this.dashboardService.subscribe();
    this.dashboardService.receive().subscribe(() => {
      this.debouncedRefresh();
    });
  }

  documentStatuses = {
    APV: 'Approved',
    DRF: 'Draft',
    RJC: 'Rejected',
    RVS: 'Revised',
    SMT: 'Submitted',
  }
  printStatus(status: string) {
    return this.documentStatuses[status];
  }

  beforeDestroy() {
    this.dashboardService.unsubscribe();
  }

  filterColumn(arr: IPaDashboardPreference[], row: number)
  {
    return arr.filter(x => { return x.rowNo == row});
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
          this.totalRows = this.dashboardItems.map(item => item.rowNo).filter((value, index, self) => self.indexOf(value) === index);
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

  retrievePO(){
    this.commonService(baseApiTopVendor)
      .retrieve({
        criteriaQuery: this.updateCriteria([

        ]),
        paginationQuery: {
          page: 0,
          size: 10,
          sort: ['id']
        }
      })
      .then(async res => {

        function compare(a, b) {
          // Use toUpperCase() to ignore character casing
          const bandA = a.grandTotal;
          const bandB = b.grandTotal;
          let comparison = 0;
          if (bandA < bandB) {
            comparison = 1;
          } else if (bandA > bandB) {
            comparison = -1;
          }
          return comparison;
        }
        await res.data.sort(compare);
        this.dataPO = res.data;

      })
      .finally(()=>this.retrieveMyDocument());
  }

  retrieveMyDocument(){
    this.commonService(baseApiDocument)
      .retrieve({
        criteriaQuery: this.updateCriteria([
          'active.equals=true',
        ]),
        paginationQuery: {
          page: 0,
          size: 10,
          sort: ['date']
        }
      })
      .then(async res => {
        const myDocument = res.data;
        function compare(a, b) {
          // Use toUpperCase() to ignore character casing
          const bandA = a.date;
          const bandB = b.date;
          let comparison = 0;
          if (bandA < bandB) {
            comparison = 1;
          } else if (bandA > bandB) {
            comparison = -1;
          }
          return comparison;
        }
        await myDocument.sort(compare);
        this.dataMyDocument=myDocument;
      })
      .finally();
  }

  public refresh() {
    const widgets: any[] = <any[]>this.$refs.widget;
    widgets?.forEach((widget, index) => {
      console.log('Attempt to refresh widget#%d', index);
      widget.refresh();
    });
  }

  checkMove(e) {
    window.console.log(e.draggedContext);
  }

  groupBy(array, key){
    const result = {};
    array.forEach(item => {
      if (!result[item[key]]){
        result[item[key]] = [];
      }
      result[item[key]].push(item);
    })

    return result;
  }
}

