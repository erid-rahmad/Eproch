<template>
    <div>
        <JSCharting style="height: 230px" :options="options"></JSCharting>
    </div>
</template>

<script lang="ts">
import JSCharting, {JSC} from 'jscharting-vue';


export default {
    name: 'exampleChart',
    data() {
        let options: JSC.JSCChartConfig = {
            type: 'line',
            title: { label: { text: 'Chart Tytle' } },
            legend: {
                position: 'inside bottom right'
            },
            xAxis: { scale: { type: 'time' } },
            series: [
                {
                    name: 'Purchases',
                    points: [
                        ['1/1/2020', 29.9],
                        ['1/2/2020', 71.5],
                        ['1/3/2020', 106.4],
                        ['1/6/2020', 129.2],
                        ['1/7/2020', 144.0],
                        ['1/8/2020', 176.0]
                    ]
                }
            ]
        };
        return { options };
    },
    components: {
        JSCharting
    }
};
</script>
