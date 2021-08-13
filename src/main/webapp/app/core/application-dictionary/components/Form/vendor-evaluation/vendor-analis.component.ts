import AccessLevelMixin from '@/core/application-dictionary/mixins/AccessLevelMixin';
// import echarts from 'echarts';
import {Component, Inject, Mixins, Vue} from 'vue-property-decorator';
import DynamicWindowService from '../../DynamicWindow/dynamic-window.service';
import Chart from 'chart.js';

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
  private chartConfig: any= {
    type : "bar",
    data : {
      labels: [""],
      datasets : [{
        label: "",
        data: [],
        backgroundColor : '#b0d5ed',
        borderColor : "#FF5370"
      }]
    }
  };

  chartId= "performanceChart";

  private processing: boolean= false;

  created(){
    console.log(this.data);
    this.refreshContent();
    
  }

  mounted(){

    this.$nextTick(()=>{
      this.initChart();
    });
  }

  initChart(){
    try{
      const context= document.getElementById(this.chartId);
      console.log("context : " , context);
      this.chart= new Chart(context, this.chartConfig);
    }catch(e){
      console.log(e);
    }
  }

  refreshContent(){
    this.processing= true;
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

        // this.chart = echarts.init(document.getElementById("echarts"));
        // let contractPerformance:any[] = [];

        let contractPerformanceLabel: any[]= [];
        let contractPerformanceData: any[]= [];

        this.analis.performanceProjectAnalysis.forEach(el => {
          // contractPerformance.push([element.documentNo, element.totalScore])
          // contractPerformance.push({
          //   "documentNo" : el.documentNo,
          //   "totalScore" : el.totalScore
          // })
          contractPerformanceLabel.push(el.documentNo);
          contractPerformanceData.push(el.totalScore);
        });

        if(this.chart){

          console.log("refreshing chart data ");
          this.chartConfig.data.labels= contractPerformanceLabel;
          this.chartConfig.data.datasets[0].data= contractPerformanceData;
          
          this.chart.update();
        }

        this.processing= false;

        // this.chart.setOption({
        //   dataset: {
        //     source: contractPerformance
        //   },
        //   grid: {containLabel: true},
        //   xAxis: {type: 'category'},
        //   yAxis: {name: 'Contract Score (%)'},
        //   series: [
        //     {
        //       type: 'bar',
        //       encode: {
        //         x: 'documentNo',
        //         y: 'totalScore'
        //       }
        //     }
        //   ]
        // })
      })
    .catch(
      (res)=>{
        this.$message.error("Failed to fetch data.");
        console.log(res);
      }
    )
  }
}
