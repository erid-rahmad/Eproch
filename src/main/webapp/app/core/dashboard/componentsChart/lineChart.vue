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
  name: 'LineChart',
  props: {
    id: String,
    title: String,
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

    return {
      chartData: {
        type: "line",
        data: {
          labels: [/*"April 2021", "May 2021", "June 2021", "July 2021"*/],
          datasets: [/*
            {
              label: "Hand Sanitizer",
              fill: false,
              data: [853, 1239, 2938, 1085],
              backgroundColor: '#FF5370',
              borderColor: "#FF5370",
              borderWidth: 2
            },
            {
              label: "Surgical Mask",
              fill: false,
              data: [1834, 2837, 4392, 2562],
              backgroundColor: "#4099ff",
              borderColor: "#4099ff",
              borderWidth: 2
            }*/
          ]
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
          layout: {
            padding: {
              top: 0,
              right: 0,
              bottom: 10,
              left: 0
            }
          },
          title: {
            display: ( this.title != null && this.title != ""),
            text: this.title,
            fontSize: 18,
            padding: 5
          },
          legend: {
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
                  var label = data.datasets[tooltipItem.datasetIndex].label || '';

                  if (label) {
                      label += ': ';
                  }

                  if(typeof(tooltipItem.yLabel) == 'number')
                    label += tooltipItem.yLabel.toFixed(2).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
                  else
                    return label += tooltipItem.yLabel;
                  
                  return label;
              }
            }
          },
          scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                  fontStyle: 'bold',
                  callback: function(value, index, values) {
                    if(typeof(value) == 'number')
                          return value.toFixed(0).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
                        else
                          return value;
                  }
                }
              }
            ],
            xAxes: [
              {
                ticks: {
                  beginAtZero: true,
                  fontStyle: 'bold',
                  callback: function(value, index, values) {
                    if(typeof(value) == 'number')
                          return value.toFixed(0).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
                        else
                          return value;
                  }
                }
              }
            ]
          }
        }
      }
    }
  },
  mounted(){
    let dataset = [];

    if(this.value.length > 0) {
      let listColor = this.colors;
      let labels = this.groupBy(this.value, 'xAxisLabel');
      this.chartData.data.labels = Object.keys(labels);

      let legends = this.groupBy(this.value, 'legendLabel');
      Object.keys(legends).forEach((x, index) => {
        let color = this.popRandom(listColor);
        let data = {
          label: x,
          fill: false,
          data: Object.values(legends)[index].map(y => parseFloat(y.dataValue)), //.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') ),
          backgroundColor: color,
          borderColor: color,
          borderWidth: 2
        }

        dataset.push(data);
      });
    }

    console.log(dataset);
    this.chartData.data.datasets = dataset;
    const ctx = document.getElementById(this.id);
    new Chart(ctx, this.chartData);
  }
}
</script>