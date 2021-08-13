import DynamicWindowService from '@/core/application-dictionary/components/DynamicWindow/dynamic-window.service';
import { Component, Inject, Vue, Mixins, Watch } from 'vue-property-decorator';

import { IAdWatchListItem } from '@/shared/model/ad-watch-list-item.model';
import circularColorBar from '../componentsChart/circularColorBar.vue';
import liveData from '../componentsChart/liveData.vue';
import lineexample from '../componentsChart/simpleLine.vue';
import lineupdate from '../componentsChart/methodUpdate.vue';
import echart from '../componentsChart/echart-bar.vue';
import echartpie from '../componentsChart/echart-pie.vue';
import rateOfContract from '../componentsChart/rateOfContract.vue';
import LineChart from '../componentsChart/lineChart.vue';
import BarChart from '../componentsChart/barChart.vue';
import PieChart from '../componentsChart/pieChart.vue';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";

const baseApiTopVendor ='api/pa-dashboards/topVendorPurchase';
const baseApiDocument ='api/pa-dashboards/mydocument';

const KPIDashboardProps = Vue.extend({
  props: {
    id: String,
    name: String,
    title: String
  }
})

@Component({
  components: {
    circularColorBar,liveData,lineexample,lineupdate,echart,echartpie,rateOfContract,
    LineChart, BarChart, PieChart
  }
})
export default class KPIDashboard extends  Mixins(AccessLevelMixin, KPIDashboardProps) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private dataPO:any=[];
  private dataMyDocument:any=[];
  items: IAdWatchListItem[] = [];

  listColor=
  [
    //yellow
    {staticColor: '#ecec04', gradientColor: 'linear-gradient(315deg, #fbb034 0%, #ffdd00 74%);'}, 
    //red
    {staticColor: '#f5365c', gradientColor: 'linear-gradient(45deg, rgb(255, 83, 112), rgb(255, 134, 154));'}, 
    //green
    {staticColor: '#2dce89', gradientColor: 'linear-gradient(87deg, rgb(45, 206, 137) 0px, rgb(45, 206, 204) 100%);'}, 
    // blue
    {staticColor: '#11cdef', gradientColor: 'linear-gradient(87deg, rgb(17, 205, 239) 0px, rgb(17, 113, 239) 100%);'}
  ]
  
  @Watch('items', {deep: true})
  async onItemsChanged(items: IAdWatchListItem[]) {
    await items.forEach((item, index) => {
      this.retrieveChartData(item, index);
    });
    /*await items.forEach(item=>{
      this.$set(this.data,item.code, item.count);
      this.$set(this.data,item.code+"data", item);
    })*/
  }


  created() {
    this.retrieveChartItems(this.name);

   //this.retrievePO();
   //this.retrieveMyDocument();
  }

  public refresh() {
    this.onItemsChanged(this.items);

    /*setInterval(() => {
      this.onItemsChanged(this.items);
    }, 60000);*/
  }

  retrieveChartItems(name: string) {
    this.commonService('/api/ad-watch-lists')
      .retrieve({
        criteriaQuery: [
          'active.equals=true',
          `name.equals=${name}`
        ],
        paginationQuery: {
          page: 0,
          size: 1,
          sort: ['id']
        }
      })
      .then(res => {
        this.items = res.data[0].adWatchListItems;
      })
      .catch(err => {
        console.log('Failed to get the watch list.', err);
        this.$message({
          message: 'Failed to get the watch list',
          type: 'warning'
        });
      });
  }

  
  private retrieveChartData(item: IAdWatchListItem, index?: number) {
    this.commonService(item.restApiEndpoint)
      .retrieve()
      .then(res => {
        this.$set(this.items[index], 'chartData', res.data);
      });
  }

  /*documentStatuses = {
    APV: 'Approved',
    DRF: 'Draft',
    RJC: 'Rejected',
    RVS: 'Revised',
    SMT: 'Submitted',
  }
  printStatus(status: string) {
    return this.documentStatuses[status];
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
  }*/

}
