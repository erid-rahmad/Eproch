<template>
    <canvas :id="id"></canvas>
</template>

<script>
import Chart from 'chart.js';

export default {
  name: 'PieChart',
  props: {
    id: {
        type: String,
        default: 'PieChart'
    },
    chartType: {
        type: String,
        default: 'pie'
    }
  },
  data(){
    return {
      chartData: {
        type: this.chartType, // pie doughnut
        data: {
            labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
            datasets: [{
                label: "Population (millions)",
                backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
                data: [2478,5267,734,784,433]
            }]
        },
        options: {
          responsive: true,
          lineTension: 2,
          layout: {
            padding: {
              top: 0,
              right: 10,
              bottom: 10,
              left: 10
            }
          },
          title: {
            display: true,
            text: 'Predicted world population (millions) in 2050',
            fontSize: 20,
            padding: 15
          },
          legend: {
            position: 'top',
            labels:{
              boxWidth: 15,
              fontStyle: 'bold'
            }
          }
        }
      }
    }
  },
  mounted(){
    const ctx = document.getElementById(this.id);
    new Chart(ctx, this.chartData);
  }
}
</script>