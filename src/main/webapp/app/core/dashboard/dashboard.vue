<template>
    <div>
        <draggable v-model="dashboardItems" 
            group="dashboard" 
            v-bind='dragOptions'
            @start="drag=true" 
            @end="drag=false">
            <transition-group type="transition">
                <div
                v-for="item in dashboardItems"
                :key="item.id"
                >
                    <watch-list
                        v-if="item.paDashboardItem.type === 'WATCHLIST'"
                        ref="widget"
                        :id="item.paDashboardItem.adWatchListId + ''"
                        :name="item.paDashboardItem.adWatchListName"
                    />
                </div>
            </transition-group>
        </draggable>

        <!--
        <div class="md-layout md-gutter">
            <div class="md-layout-item">
                <accordion id="Accordion-ChartStyle1" title="CHART 1" v-bind:expanded="false" animation="leftToRight">
                    <div style="padding: 0px 20px">
                        <div class="md-layout">
                            <div class="md-layout-item">
                                <md-card md-with-hover class="bg-pattern">
                                    <line-chart id="LineChart-Test" title="Total Product Purchase"></line-chart>
                                </md-card>
                            </div>
                        </div> 
                        <div class="md-layout">
                            <div class="md-layout-item">
                                <md-card md-with-hover class="bg-pattern">
                                    <bar-chart id="BarChart-Test" title="Total Product Purchase"></bar-chart>
                                </md-card>
                            </div>
                        </div> 
                    </div>
                </accordion>
            </div>
            <div class="md-layout-item">
                <accordion id="Accordion-ChartStyle2" title="CHART 2" v-bind:expanded="false" animation="rightToLeft">
                    <div style="padding: 0px 20px">
                        <div class="md-layout">
                            <div class="md-layout-item">
                                <md-card md-with-hover class="bg-pattern">
                                    <pie-chart id="PieChart-Test"></pie-chart>
                                </md-card>
                            </div>
                        </div> 
                        <div class="md-layout">
                            <div class="md-layout-item">
                                <md-card md-with-hover class="bg-pattern">
                                    <pie-chart id="DoughnutChart-Test" chartType="doughnut"></pie-chart>
                                </md-card>
                            </div>
                        </div> 
                    </div>
                </accordion>
            </div>
        </div>
        
        <kpi-admin></kpi-admin> -->
    </div>
</template>

<style lang="scss" scoped>
.bg-pattern {
    background-image: url("data:image/svg+xml,%3Csvg width='40' height='40' viewBox='0 0 40 40' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M20 20.5V18H0v-2h20v-2H0v-2h20v-2H0V8h20V6H0V4h20V2H0V0h22v20h2V0h2v20h2V0h2v20h2V0h2v20h2V0h2v20h2v2H20v-1.5zM0 20h2v20H0V20zm4 0h2v20H4V20zm4 0h2v20H8V20zm4 0h2v20h-2V20zm4 0h2v20h-2V20zm4 4h20v2H20v-2zm0 4h20v2H20v-2zm0 4h20v2H20v-2zm0 4h20v2H20v-2z' fill='%23cccccc' fill-opacity='0.2' fill-rule='evenodd'/%3E%3C/svg%3E");
    background-position: center center;
}

.md-title {
    font-size: 16px;
    opacity: 0.6;
    letter-spacing: 0;
    line-height: 49px;
}

.md-title-chart {
    font-size: 45px;
    opacity: 1;
    color: white;
    letter-spacing: 0;
    line-height: 49px;
}

.md-layout {
    margin-top: 20px;
    margin-bottom: 20px;
}

.icon-media {
    width: 43px;
    margin-top: 26px;
    margin-left: 23px;
}

.card-icon {
    font-size: 40px !important;
    color: white;
    margin-top: 29px;
    margin-left: 30px;
}

.md-subhead {
    opacity: 0.9;
    font-size: 35px;
    letter-spacing: 0.15em;
    line-height: 21px;
}

.chart-size {
    height: 352px;
    width: 500px;
}

body {
    background: #35A9DB;
    font-family: roboto;
    text-align: center;
    color: #fff;
}

</style>

<style>
    .ghost {
        opacity: 0.5;
        background: #c8ebfb;
    }
</style>


<script lang="ts" src="./dashboard.component.ts"></script>
