<template>
    <canvas id="bar-chart"></canvas>
</template>

<script>
import Chart from 'chart.js';

export default {
  name: 'BarChart',
  data(){
    return {
      chartData: {
        type: "bar", //bar / horizontalBar
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
              top: 10,
              right: 10,
              bottom: 10,
              left: 10
            }
          },
          title: {
            display: true,
            text: 'Total Product Purchase',
            fontSize: 20,
            padding: 5
          },
          legend: {
            position: 'top',
            align: 'end',
            labels:{
              boxWidth: 15,
              fontStyle: 'bold',
              padding: 10
            }
          },
          scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                  padding: 20
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
    const ctx = document.getElementById('bar-chart');
    new Chart(ctx, this.chartData);
  }
}
</script>