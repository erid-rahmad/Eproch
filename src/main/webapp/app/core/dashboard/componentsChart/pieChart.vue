<template>
  <div>
    <canvas :id="id"></canvas>
    <div :id="id+'-none'" style="margin: 30px auto; text-align: center; display: none;">
        <span style="font-size: 1rem; font-weight: bold; color: #8898aa;">No data to display</span>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js';

export default {
  name: 'PieChart',
  props: {
    id: String,
    title: String,
    chartType: {
        type: String,
        default: 'pie'
    },
    position: String,
    value: {type: Array, default: (() => []) },
    colors: {type: Array, default: (() => []) }
  },
  methods: {
    groupBy(array, key){
      const result = {};
      array.forEach(item => {
        if (!result[item[key]]){
          result[item[key]] = [];
        }
        result[item[key]].push(item);
      })

      return result;
    },
    popRandom (array) {
      let i = (Math.random() * array.length) | 0
      return array.splice(i, 1)[0];
    }
  },
  data(){
    let id = this.id;
    let type = this.chartType;

    return {
      chartData: {
        type: this.chartType, // pie doughnut
        data: {
            labels: [/*"Africa", "Asia", "Europe", "Latin America", "North America"*/],
            datasets: [/*{
                label: "Population (millions)",
                backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
                data: [2478,5267,734,784,433]
            }*/]
        },
        plugins: {
          afterDraw: function(chart) {
            let isEmpty = (chart.data.datasets.length == 0);
            document.getElementById(id).style.display = (isEmpty ? 'none' : 'block');
            document.getElementById(id + '-none').style.display = (isEmpty ? 'block' : 'none');
          }
        },
        options: {
          responsive: true,
          lineTension: 2,
          segmentShowStroke: true,
          segmentStrokeWidth : 5,
          layout: {
            padding: {
              top: 0,
              right: 0,
              bottom: 10,
              left: 0
            }
          },
          animation: {
            animateScale: true,
            animateRotate: true
          },
          title: {
            display: ( this.title != null && this.title != ""),
            text: this.title,
            fontSize: 18,
            padding: 5
          },
          legend: {
            display: true,
            position: this.position,
            labels:{
              boxWidth: 15,
              fontSize: 13,
              fontStyle: 'bold'
            }
          },
          tooltips: {
            mode: 'label',
            bodySpacing: 10,
            titleMarginBottom: 10,
            callbacks: {
                label: function(tooltipItem, data) {
                    var label = '';
                    let datasets = data['datasets'];

                    if(datasets.length > 0){
                      if(data['datasets'][tooltipItem['datasetIndex']]['label'] != '')
                        label += '[' + data['datasets'][tooltipItem['datasetIndex']]['label'] + '] ';
                    }

                    label += data['labels'][tooltipItem['index']] || '';

                    if (label) {
                        label += ': ';
                    }

                    let value = data['datasets'][tooltipItem['datasetIndex']]['data'][tooltipItem['index']];
                    if(typeof(value) == 'number')
                      label += value.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
                    else
                      label += value;

                    return label;
                }
            }
          }
        }
      }
    }
  },
  mounted(){
    let dataset = [];
    this.value = this.value ? this.value : [];
    if(this.value.length > 0) {
      let listColor = this.colors;
      let legends = this.groupBy(this.value, 'legendLabel');
      this.chartData.data.labels = Object.keys(legends);

      let listDataSet = this.groupBy(this.value, 'xAxisLabel');

      let usedColor = [];
      Object.keys(legends).forEach((x, index) => {
        usedColor.push(this.popRandom(listColor));
      })

      Object.keys(listDataSet).forEach((x, index) => {
        let data = {
          label: x,
          fill: false,
          data: Object.values(listDataSet)[index].map(y => parseFloat(y.dataValue)), //.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') ),
          backgroundColor: usedColor,
          borderColor: '#FFF',
          borderWidth: 2
        }

        dataset.push(data);
      });
    }

    //console.log('pieChart: ' + JSON.stringify(dataset));
    this.chartData.data.datasets = dataset;
    const ctx = document.getElementById(this.id);
    new Chart(ctx, this.chartData);
  }
}
</script>