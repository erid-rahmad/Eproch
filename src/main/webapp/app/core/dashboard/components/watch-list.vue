
<template>
    <!--<accordion :id="'Accordion-' + id" :title="name" v-bind:expanded="true" animation="bottomToTop" style="margin-bottom: 20px;">
        <div style="padding: 5px 15px 10px;"> -->
            <!-- LIST 
            <div class="md-layout md-gutter" style="margin: 0px 1px;">
                <div class="md-layout-item"
                    v-for="item in items"
                    :key="item.id"
                    @click="onCardClicked(item);"
                >
                    <div class="md-layout-item">
                        <md-list class="wl-custom-list">
                            <md-list-item>
                                <div>
                                    <span class="wl-badge" :style="'background:'+ item.accentColor +'!important;'">{{item.count ? item.count : '0'}}</span>
                                    <span>{{item.name}}</span>
                                </div>
                            </md-list-item>
                        </md-list>
                    </div>
                </div>
            </div> -->

            <!-- CARD NO BG COLOR -->
            <div class=" md-layout md-gutter" style="margin: 10px 1px;">
                <div :id="'LayoutItem-' + id + '-' + index" :class="'md-layout-item ' + getWidthClass('Accordion-' + id)"  
                    v-for="(item, index) in items"
                        :key="item.id"
                    style="margin: 5px 0 !important;"
                    @click="onCardClicked(item);"
                >
                    <md-card md-with-hover class="bg-pattern" style="width: 100%; border-radius: 6px; padding: 10px 12px;">
                        <md-card-header style="padding: 0; margin: 0;">
                            <md-card-header-text align="left">
                                <div class="wl-caption">{{ item.name }}</div>
                                <div class="wl-count-wrapper" :style="'background:'+ item.accentColor +' !important;'">
                                    <div class="wl-title" style="font-size: 14px !important; text-align: center; color: #FFF;">{{ item.count ? item.count : '0' }}</div>
                                </div>
                            </md-card-header-text>
                            <!-- <md-card-media style="height: auto; flex: 0.2; margin-left: 5px !important;">
                                <div class="wl-icon-wrapper" :style="'background:'+ item.accentColor +' !important;'">
                                    <svg-icon :name="item.icon" class="wl-icons"></svg-icon>
                                </div>
                            </md-card-media> -->
                        </md-card-header>
                    </md-card>
                </div>
            </div>

        <!-- CARD BG COLOR
        <div class=" md-layout md-gutter" style="margin: 10px 1px;">
            <div class="md-layout-item" 
                v-for="item in items"
                    :key="item.id"
                @click="onCardClicked(data.vendorAdata)"
            >
                <md-card md-with-hover :style="'width:100%; border-radius: 6px; background: '+ item.accentColor +' !important;'">
                    <div class="bg-pattern" style="padding: 10px; width: 100%; height: 100%;">
                        <md-card-header style="padding: 0;">
                                <md-card-header-text align="left">
                                    <div class="wl-caption" style="color: #FFF;">{{item.name}}</div>
                                </md-card-header-text>
                            </md-card-header>
                            <md-card-content style="padding: 0px 6px;">
                                <div style="width: 100%; overflow: hidden; font-size: 28px; font-weight: 600; color: #FFF;">
                                    <div style="float: left;">
                                        <svg-icon :name="item.icon" class="wl-icons-2"></svg-icon>
                                    </div>
                                    <div style="float: right;">{{ item.count ? item.count : '0' }}</div>
                                </div>
                            </md-card-content>
                    </div>
                </md-card>
            </div>
        </div> -->

        <!-- CAROUSEL
        <el-carousel class="wl-carousel" indicator-position="none" direction="horizontal" :autoplay="true" trigger="click" arrow="always">
            <el-carousel-item class="wl-carousel-item">
                <div class=" md-layout md-gutter">
                    <div class="md-layout-item" @click="onCardClicked(data.vendorAdata)">
                        <md-card md-with-hover style="border-radius: 6px; background: linear-gradient(45deg, #FF5370, #ff869a)!important;">
                            <div class="bg-pattern" style="padding: 4px 8px;">
                                <md-card-header style="padding: 12px 8px 0px;">
                                        <md-card-header-text align="left">
                                            <div class="wl-caption" style="color: #FFF;">New Vendor</div>
                                        </md-card-header-text>
                                    </md-card-header>
                                    <md-card-content style="padding: 0px 6px 16px;">
                                        <div style="width: 100%; overflow: hidden; font-size: 28px; font-weight: 600; color: #FFF;">
                                            <div style="float: left;">
                                                <i class="el-icon-circle-plus-outline" style="color: white"></i>
                                            </div>
                                            <div style="float: right;">{{ dasbordItem.vendorNew }}</div>
                                        </div>
                                    </md-card-content>
                                    <md-card-actions class="md-alignment-left" style="border-top: 1px solid #FFF;">
                                        <div style="width: 100%; overflow: hidden; color: #FFF;">
                                            <span style="float: left; color: #FFF;">
                                                Total Vendor
                                            </span>
                                            <span style="float: right; font-weight: 600;">102</span>
                                        </div>
                                    </md-card-actions>
                            </div>
                        </md-card>
                    </div>
                    <div class="md-layout-item"  @click="onCardClicked(data.vendorAdata)">
                        <md-card md-with-hover style="border-radius: 6px; padding: 4px 8px; background: linear-gradient(45deg, #FFB64D, #ffcb80)">
                            <md-card-header style="padding: 12px 8px 0px;">
                                <md-card-header-text align="left">
                                    <div class="wl-caption" style="color: #FFF;">Vendor Activation</div>
                                </md-card-header-text>
                            </md-card-header>
                            <md-card-content style="padding: 0px 6px 16px;">
                                <div style="width: 100%; overflow: hidden; font-size: 28px; font-weight: 600; color: #FFF;">
                                    <div style="float: left;">
                                        <i class="el-icon-s-claim" style="color: white"></i>
                                    </div>
                                    <div style="float: right;">{{ data.vendorA ? data.vendorA : '56' }}</div>
                                </div>
                            </md-card-content>
                            <md-card-actions class="md-alignment-left" style="border-top: 1px solid #FFF;">
                                <div style="width: 100%; overflow: hidden; color: #FFF;">
                                    <span style="color: #FFF!important; font-weight: 600; margin-right: 0.5rem !important;">
                                        <i class="el-icon-bottom" style="font-weight: 600;"></i>
                                        5.29%
                                    </span>
                                    <span style="white-space: nowrap !important;">Since Last Month</span>
                                </div>
                            </md-card-actions>
                        </md-card>
                    </div>
                    <div class="md-layout-item"  @click="onCardClicked(data.vendorAdata)">
                        <md-card md-with-hover style="border-radius: 6px; padding: 4px 8px; background: linear-gradient(45deg, #2ed8b6, #59e0c5)">
                            <md-card-header style="padding: 12px 8px 0px;">
                                <md-card-header-text align="left">
                                    <div class="wl-caption" style="color: #FFF;">Vendor Request</div>
                                </md-card-header-text>
                            </md-card-header>
                            <md-card-content style="padding: 0px 6px 16px;">
                                <div style="width: 100%; overflow: hidden; font-size: 28px; font-weight: 600; color: #FFF;">
                                    <div style="float: left;">
                                        <i class="el-icon-s-opportunity" style="color: white"></i>
                                    </div>
                                    <div style="float: right;">{{ data.vendorR ? data.vendorR : '27' }}</div>
                                </div>
                            </md-card-content>
                            <md-card-actions class="md-alignment-left" style="border-top: 1px solid #FFF;">
                                <div style="width: 100%; overflow: hidden; color: #FFF;">
                                    <span style="font-weight: 600; margin-right: 0.5rem !important;">
                                        <i class="el-icon-top" style="font-weight: 600;"></i>
                                        3.14%
                                    </span>
                                    <span style="white-space: nowrap !important;">Since Last Month</span>
                                </div>
                            </md-card-actions>
                        </md-card>
                    </div>
                    <div class="md-layout-item"  @click="onCardClicked(data.vendorAdata)">
                        <md-card md-with-hover style="border-radius: 6px; background: linear-gradient(45deg, #4099ff, #73b4ff);">
                            <div class="bg-pattern" style="padding: 4px 8px;">
                                <md-card-header style="padding: 12px 8px 0px;">
                                    <md-card-header-text align="left">
                                        <div class="wl-caption" style="color: #FFF;">Submitted Invoice</div>
                                    </md-card-header-text>
                                </md-card-header>
                                <md-card-content style="padding: 0px 6px 16px;">
                                    <div style="width: 100%; overflow: hidden; font-size: 28px; font-weight: 600; color: #FFF;">
                                        <div style="float: left;">
                                            <i class="el-icon-s-flag" style="color: white"></i>
                                        </div>
                                        <div style="float: right;">{{ data.submitInv ? data.submitInv : '6' }}</div>
                                    </div>
                                </md-card-content>
                                <md-card-actions class="md-alignment-left" style="border-top: 1px solid #FFF;">
                                    <div style="width: 100%; overflow: hidden; color: #FFF;">
                                        <span style="float: left; color: #FFF;">
                                            This Month
                                        </span>
                                        <span style="float: right; font-weight: 600;">53</span>
                                    </div>
                                </md-card-actions>
                            </div>
                        </md-card>
                    </div>
                </div>
            </el-carousel-item>
        </el-carousel> -->
        
        <!-- <el-table
        v-if="isList"
        :data="items"
        :show-header="false"
        stripe
        style="width: 256px"
        >
            <el-table-column
                prop="count"
                width="64"
            />
            <el-table-column class="list-item">
                <template slot-scope="{row}">
                <div @click="onCardClicked(row)">
                    {{row.name}}
                </div>
                </template>
            </el-table-column>
        </el-table> -->
       <!-- </div>
    </accordion> -->
</template>

<script lang="ts" src="./watch-list.component.ts"></script>

<style>
    .wl-custom-list {
        padding: 3px;
        background-color: transparent !important;
    }
    
    .wl-custom-list > .md-list-item {
        cursor: pointer;
        border: 1px solid rgba(0,0,0,0.12);
        border-radius: 5px;
        background-color: transparent !important;
        margin-bottom: 8px;
        padding: 1px;
    } 

    .wl-custom-list > .md-list-item > .md-list-item-container > .md-list-item-content{
        min-height: 32px;
        font-size: 14px !important;
        background-color: #FFF;
    }

    .wl-custom-list > .md-list-item:hover{
        padding: 0px;
        background-color: rgba(0,0,0,0.12) !important;
        border-color: rgba(0,0,0,0.24);
    }

    .wl-custom-list > .md-list-item > .md-list-item-container > .md-list-item-content:hover{
        background-color: rgba(0,0,0,0.12) !important;
    }

    .wl-badge {
        padding: 3px 12px;
        border-radius: 10px;
        margin-right: 5px;
        background-color: #8898aa;
        font-size: 13px !important;
        color: #FFF;
    }

    .wl-icon-wrapper {
        padding: 6px;
        width: 32px;
        height: 32px;
        border-radius: 50%; 
        background-color: #8898aa;
    }

    .wl-count-wrapper {
        display: inline-block;
        padding: 2px 15px;
        border-radius: 10px; 
        background-color: #8898aa;
    }

    .bg-pattern {
        background-image: url("data:image/svg+xml,%3Csvg width='40' height='40' viewBox='0 0 40 40' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M20 20.5V18H0v-2h20v-2H0v-2h20v-2H0V8h20V6H0V4h20V2H0V0h22v20h2V0h2v20h2V0h2v20h2V0h2v20h2V0h2v20h2v2H20v-1.5zM0 20h2v20H0V20zm4 0h2v20H4V20zm4 0h2v20H8V20zm4 0h2v20h-2V20zm4 0h2v20h-2V20zm4 4h20v2H20v-2zm0 4h20v2H20v-2zm0 4h20v2H20v-2zm0 4h20v2H20v-2z' fill='%23cccccc' fill-opacity='0.2' fill-rule='evenodd'/%3E%3C/svg%3E");
        background-position: center center;
    }

    .wl-icons {
        width: 100% !important;
        height: 100% !important;
        fill: #FFF;
        color: #FFF;
    }
</style>

<!-- CAROUSEL CARD -->
<style>
    .wl-carousel,
    .el-carousel__container
    {
        min-height: 160px;
        height: auto;
        overflow-x: hidden;
        overflow-y: auto;
    }

    .wl-carousel-item {
        padding: 0 35px;
    }

    .wl-carousel > .el-carousel__container > .el-carousel__arrow {
        margin-top: 10px;
        width: 30px !important;
        height: 30px !important;
        background-color: rgba(31, 45, 61, 0.24);
    }

    .wl-carousel > .el-carousel__container > .el-carousel__arrow > .el-icon-arrow-left {
        font-size: 14px;
        font-weight: 600;
    }

    .wl-carousel > .el-carousel__container > .el-carousel__arrow--left {
        left: 0px;
    }

    .wl-carousel > .el-carousel__container > .el-carousel__arrow--right {
        right: 0px;
    }

    .wl-caption {
        margin: 0;
        color: #8898aa;
        font-weight: 600;
        font-size: 14px;
        text-transform: none;
        margin-bottom: 4px;
    }

    .wl-title {
        font-weight: 500;
        font-size: 16px;
    }
</style>

<style lang="scss" scoped>

.md-title {
    font-size: 15px;
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
    margin-top: 10px;
    margin-bottom: 10px;
    display: flex;
    flex-flow: row wrap;
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

body{
    background: #35A9DB;
    font-family: roboto;
    text-align: center;
    color: #fff;
}

.md-layout.md-gutter>.md-layout-item {
    padding: 0 10px !important;
    margin: 5px auto;
    display: flex;
}


</style>
