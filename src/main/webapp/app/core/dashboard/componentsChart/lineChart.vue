<template>
    <canvas :id="id"></canvas>
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
    return {
      chartData: {
        type: "line",
        data: {
          labels: ["April 2021", "May 2021", "June 2021", "July 2021"],
          datasets: [
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
            },
            {
              label: "Face Shield",
              fill: false,
              data: [256, 568, 1002, 1590],
              backgroundColor: "#2ed8b6",
              borderColor: "#2ed8b6",
              borderWidth: 2
            },
            {
              label: "Disinfectant",
              fill: false,
              data: [157, 589, 1894, 3208],
              backgroundColor: "#FFB64D",
              borderColor: "#FFB64D",
              borderWidth: 2
            }
          ]
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
            fontSize: 20,
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
          },
          scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                  fontStyle: 'bold'
                }
              }
            ],
            xAxes: [
                {
                    ticks: {
                        fontStyle: 'bold'
                    }
                }
            ]
          }
        }
      }
    }
  },
  mounted(){
    let listColor = this.colors;
    let labels = this.groupBy(this.value, 'xAxisLabel');
    this.chartData.data.labels = Object.keys(labels);

    let dataset = [];
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

    this.chartData.data.datasets = dataset;
    const ctx = document.getElementById(this.id);
    new Chart(ctx, this.chartData);
  }
}
</script>