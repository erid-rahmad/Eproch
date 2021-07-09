
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
import echarts from 'echarts';
import AccessLevelMixin from "@/core/application-dictionary/mixins/AccessLevelMixin";
import DynamicWindowService from "@/core/application-dictionary/components/DynamicWindow/dynamic-window.service";
const baseApiDashbord ='api/m-purchase-orders/dashbordView';

@Component
export default class DashBoard extends  Mixins(AccessLevelMixin) {

  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  contenBar:any=[[]]

  private chart:any;


  async mounted() {

    this.chart = echarts.init(document.getElementById("echarts-1"));
    this.chart.setOption({
      title: {
        text: '世界人口总量',
        subtext: '数据来自网络'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      legend: {
        data: ['2011年', '2012年']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
      },
      yAxis: {
        type: 'category',
        data: ['supplier1', '印尼', '美国', '印度', '中国', '世界人口(万)']
      },
      series: [
        {
          name: '2011年',
          type: 'bar',
          data: [1, 12, 23, 44, 34, 88]
        },

      ]
    })
  }










}
