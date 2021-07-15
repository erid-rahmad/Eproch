<template>
    <div>
         <!-- LAYOUT DRAG & DROP -- RAYMOND
             <grid-layout :layout.sync="layout"
            :col-num="12"
            :row-height="30"
            :is-draggable="true"
            :is-resizable="false"
            :responsive="false"
            :vertical-compact="true"
            :use-css-transforms="true"
        >
            <grid-item v-for="item in layout"
                        :key="item.i"
                        :static="item.static"
                        :x="item.x"
                        :y="item.y"
                        :w="item.w"
                        :h="item.h"
                        :i="item.i"
                        @click="test"
                        @container-resized="containerResizedEvent"
            >
                <accordion :id="'Accordion-WatchList'+item.i" title="WATCH LIST" v-bind:expanded="true" animation="bottomToTop">
                    <div
                        v-for="item in dashboardItems"
                        :key="item.id"
                    >
                        <watch-list
                            v-if="item.paDashboardItem.type === 'WATCHLIST'"
                            ref="widget"
                            :name="item.paDashboardItem.adWatchListName"
                        />
                    </div>
                </accordion>
            </grid-item>
        </grid-layout> -->
        
        <div
            v-for="item in dashboardItems"
            :key="item.id"
        >
            <watch-list
                v-if="item.paDashboardItem.type === 'WATCHLIST'"
                ref="widget"
                :id="item.paDashboardItem.adWatchListId"
                :name="item.paDashboardItem.adWatchListName"
            />
        </div>

        <!--<div class="md-layout md-gutter">
            <div class="md-layout-item">
                <accordion id="Accordion-ChartStyle1" title="CHART 1" v-bind:expanded="false" animation="leftToRight">
                    <div style="padding: 0px 20px">
                        <div class="md-layout">
                            <div class="md-layout-item">
                                <md-card md-with-hover class="bg-pattern">
                                    <line-chart></line-chart>
                                </md-card>
                            </div>
                        </div> 
                        <div class="md-layout">
                            <div class="md-layout-item">
                                <md-card md-with-hover class="bg-pattern">
                                    <bar-chart></bar-chart>
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
                                    <pie-chart id="PieChart"></pie-chart>
                                </md-card>
                            </div>
                        </div> 
                        <div class="md-layout">
                            <div class="md-layout-item">
                                <md-card md-with-hover class="bg-pattern">
                                    <pie-chart id="DoughnutChart" chartType="doughnut"></pie-chart>
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

<style scoped>
    .vue-grid-layout {
        background: #eee;
    }

    /*
    .vue-grid-item:not(.vue-grid-placeholder) {
        background: #ccc;
        border: 1px solid black;
    }
    .vue-grid-item .resizing {
        opacity: 0.9;
    }
    .vue-grid-item .static {
        background: #cce;
    }
    .vue-grid-item .text {
        font-size: 24px;
        text-align: center;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
        height: 100%;
        width: 100%;
    }
    .vue-grid-item .no-drag {
        height: 100%;
        width: 100%;
    }
    .vue-grid-item .minMax {
        font-size: 12px;
    }
    .vue-grid-item .add {
        cursor: pointer;
    }*/
    .vue-draggable-handle {
        position: absolute;
        width: 20px;
        height: 20px;
        top: 0;
        left: 0;
        background: url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='10' height='10'><circle cx='5' cy='5' r='5' fill='#999999'/></svg>") no-repeat;
        background-position: bottom right;
        padding: 0 8px 8px 0;
        background-repeat: no-repeat;
        background-origin: content-box;
        box-sizing: border-box;
        cursor: pointer;
    }
    .layoutJSON {
        background: #ddd;
        border: 1px solid black;
        margin-top: 10px;
        padding: 10px;
    }
    .columns {
        -moz-columns: 120px;
        -webkit-columns: 120px;
        columns: 120px;
    }
</style>


<script lang="ts" src="./dashboard.component.ts"></script>
