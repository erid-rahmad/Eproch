import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
import echarts from 'echarts';
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';

const VendorEvaluationDetailProp = Vue.extend({
  props: {
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  }
});

@Component
export default class VendorEvaluationDetail extends Mixins(AccessLevelMixin, VendorEvaluationDetailProp) {
  period: any = '1y'
  analis: any = {}
  
  periodSelections=[
    {value: '3m', label: '3 Months'},
    {value: '6m', label: '6 Months'},
    {value: '1y', label: '1 Year'},
    {value: '2y', label: '2 Years'},
  ]
  
  @Inject('dynamicWindowService')
  protected commonService: (baseApiUrl: string) => DynamicWindowService;

  private chart:any;

  created(){
    console.log(this.data);
    this.refreshContent();
  }

  refreshContent(){
    this.commonService(`/api/m-vendor-performance-report/detail`).retrieve({
      criteriaQuery: this.updateCriteria([
        `vendorId=${this.data.vendorId}`,
        `period=${this.period}`
      ]),
    })
    .then(
      (res)=>{
        console.log(res.data);
        this.analis = res.data;

        this.chart = echarts.init(document.getElementById("echarts"));
        let contractPerformance:any[] = [];

        this.analis.performanceProjectAnalysis.forEach(element => {
          contractPerformance.push([element.documentNo, element.totalScore])
        });

        this.chart.setOption({
          dataset: {
            source: contractPerformance
          },
          grid: {containLabel: true},
          xAxis: {type: 'category'},
          yAxis: {name: 'Contract Score (%)'},
          series: [
            {
              type: 'bar',
              encode: {
                x: 'documentNo',
                y: 'totalScore'
              }
            }
          ]
        })
      })
    .catch(
      (res)=>{
        this.$message.error("Failed to fetch data.");
        console.log(res);
      }
    )
  }
}
