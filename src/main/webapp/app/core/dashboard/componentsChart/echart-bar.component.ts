
import { Component, Inject, Vue } from 'vue-property-decorator';
// @ts-ignore
import echarts from 'echarts';
@Component
export default class DashBoard extends Vue {
  private chart:any;
    dataAxis = ['点', '击', '柱', '子', '或', '者', '两', '指', '在', '触', '屏', '上', '滑', '动', '能', '够', '自', '动', '缩', '放'];
   data = [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220];
   yMax = 500;
   dataShadow = [];
   colors = ['#5470C6', '#91CC75', '#EE6666'];
  mounted() {
    this.chart = echarts.init(document.getElementById("echarts"));
    this.chart.setOption({
      color: this.colors,

      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross'
        }
      },
      grid: {
        right: '20%'
      },
      toolbox: {
        feature: {
          dataView: {show: true, readOnly: false},
          restore: {show: true},
          saveAsImage: {show: true}
        }
      },
      legend: {
        data: ['蒸发量', '降水量', '平均温度']
      },
      xAxis: [
        {
          type: 'category',
          axisTick: {
            alignWithLabel: true
          },
          data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        }
      ],
      yAxis: [
        {
          type: 'value',
          name: '蒸发量',
          min: 0,
          max: 250,
          position: 'right',
          axisLine: {
            show: true,
            lineStyle: {
              color: this.colors[0]
            }
          },
          axisLabel: {
            formatter: '{value} ml'
          }
        },
        {
          type: 'value',
          name: '降水量',
          min: 0,
          max: 250,
          position: 'right',
          offset: 80,
          axisLine: {
            show: true,
            lineStyle: {
              color: this.colors[1]
            }
          },
          axisLabel: {
            formatter: '{value} ml'
          }
        },
        {
          type: 'value',
          name: '温度',
          min: 0,
          max: 25,
          position: 'left',
          axisLine: {
            show: true,
            lineStyle: {
              color: this.colors[2]
            }
          },
          axisLabel: {
            formatter: '{value} °C'
          }
        }
      ],
      series: [
        {
          name: '蒸发量',
          type: 'bar',
          data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
        },
        {
          name: '降水量',
          type: 'bar',
          yAxisIndex: 1,
          data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
        },
        {
          name: '平均温度',
          type: 'line',
          yAxisIndex: 2,
          data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
        }
      ]


    })
  }


}
