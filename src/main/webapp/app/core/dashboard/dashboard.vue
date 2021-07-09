<template>
    <div>
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
        <kpi-admin></kpi-admin>
    </div>
</template>

<style lang="scss" scoped>


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


<script lang="ts" src="./dashboard.component.ts"></script>
