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
    title: String,
    type: String,
    listColor: Array
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
  chartItems: IAdWatchListItem[] = [];
  gridItems: IAdWatchListItem[] = [];

  /*@Watch('chartItems', {deep: true})
  @Watch('gridItems', {deep: true})*/
  onItemsChanged(items: IAdWatchListItem[]) {
    items.forEach((item, index) => {
      this.retrieveKpiData(item, index);
    });
  }

  async created() {
    this.retrieveKpiItems(this.name);

   //this.retrievePO();
   //this.retrieveMyDocument();
  }

  refresh(){
    this.onItemsChanged(this.type == 'CHART' ? this.chartItems : this.gridItems);
  }

  retrieveKpiItems(name: string) {
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
        //console.log(res.data[0].adWatchListItems);
        if(this.type == 'CHART'){
          this.chartItems = res.data[0].adWatchListItems;
          this.chartItems.forEach((x, index) => {
            this.$set(this.chartItems[index], 'chartData', []);
            this.$set(this.chartItems[index], 'isLoading', true);
          });
        }
        else {
          this.gridItems = res.data[0].adWatchListItems;
          this.gridItems.forEach((x, index) => {
            this.$set(this.gridItems[index], 'gridData', []);
            this.$set(this.gridItems[index], 'isLoading', true);
            if(!x.accentColor) this.$set(this.gridItems[index], 'accentColor', this.getRandomColor()['staticColor']);
          });
        }

        this.onItemsChanged(this.type == 'CHART' ? this.chartItems : this.gridItems);
      })
      .catch(err => {
        console.log('Failed to get the watch list.', err);
        this.$message({
          message: 'Failed to get the watch list',
          type: 'warning'
        });
      });
  }
  
  private getRandomColor(){
    var randomNumber = Math.floor(Math.random() * this.listColor.length);
    return this.listColor[randomNumber];
  }

  private retrieveKpiData(item: IAdWatchListItem, index?: number) {
    //console.log(item.restApiEndpoint);
    this.commonService(item.restApiEndpoint)
      .retrieve(
        {
          criteriaQuery:  item.filterQuery
        }
      )
      .then(res => {
        //console.log('apiResult: ' + JSON.stringify(res.data));
        if(this.type == 'CHART'){
          /*let arrData = [
            { xAxisLabel: 'Jan 2021', legendLabel: 'CC Jakarta', dataValue: 5299708.30 },
            { xAxisLabel: 'Feb 2021', legendLabel: 'CC Jakarta', dataValue: 1867992.00 },
            { xAxisLabel: 'Mar 2021', legendLabel: 'CC Jakarta', dataValue: 3398798.74 },
            { xAxisLabel: 'Apr 2021', legendLabel: 'CC Jakarta', dataValue: 2798790.96 },
            { xAxisLabel: 'May 2021', legendLabel: 'CC Jakarta', dataValue: 1486886.53 },
            { xAxisLabel: 'Jun 2021', legendLabel: 'CC Jakarta', dataValue: 1789097.26 },
            { xAxisLabel: 'Jan 2021', legendLabel: 'CC Bandung', dataValue: 3982090.50 },
            { xAxisLabel: 'Feb 2021', legendLabel: 'CC Bandung', dataValue: 7868548.21 },
            { xAxisLabel: 'Mar 2021', legendLabel: 'CC Bandung', dataValue: 2356789.32 },
            { xAxisLabel: 'Apr 2021', legendLabel: 'CC Bandung', dataValue: 6782450.85 },
            { xAxisLabel: 'May 2021', legendLabel: 'CC Bandung', dataValue: 4674798.57 },
            { xAxisLabel: 'Jun 2021', legendLabel: 'CC Bandung', dataValue: 3846374.28 },
            { xAxisLabel: 'Jan 2021', legendLabel: 'CC Surabaya', dataValue: 2486589.62 },
            { xAxisLabel: 'Feb 2021', legendLabel: 'CC Surabaya', dataValue: 3432558.29 },
            { xAxisLabel: 'Mar 2021', legendLabel: 'CC Surabaya', dataValue: 4532467.36 },
            { xAxisLabel: 'Apr 2021', legendLabel: 'CC Surabaya', dataValue: 4928902.76 },
            { xAxisLabel: 'May 2021', legendLabel: 'CC Surabaya', dataValue: 7875898.48 },
            { xAxisLabel: 'Jun 2021', legendLabel: 'CC Surabaya', dataValue: 5784789.32 }
          ];
          this.$set(this.chartItems[index], 'chartData', arrData);*/
          this.$set(this.chartItems[index], 'chartData', res.data);
        }
        else {
          let arrData = [];
          let objModel = {};

          item.serviceName.split('##').forEach((x) => {
            objModel[x.replace(/ /g, '')] = null;
          });

          res.data.forEach(x => {
            let objData = {};
            Object.assign(objData, objModel);
            Object.keys(objModel).forEach((y, yIdx) => {
              if(typeof(x[yIdx]) == 'number'){
                objData[y] = x[yIdx].toFixed(((x[yIdx] + '').indexOf('.') > -1) ? 2 : 0).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
              }
              else{
                objData[y] = x[yIdx];
              }
            })

            arrData.push(objData);
          });

          this.$set(this.gridItems[index], 'gridData', arrData);
        }
      })
      .finally(() => {
        if(this.type == 'CHART'){
          this.$set(this.chartItems[index], 'isLoading', false);
        }
        else{
          this.$set(this.gridItems[index], 'isLoading', false);
        }
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
