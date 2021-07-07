<template>
  <div >
      <template>
          <el-carousel height="200px" direction="horizontal" :autoplay="false">
              <el-carousel-item>
                  <div class=" md-layout md-gutter" style="padding-top: 15px">
                      <div class="md-layout-item"  @click="onCardClicked(data.vendorAdata)">
                          <md-card md-with-hover style="
                    background: linear-gradient(60deg,#66bb6a,#43a047);
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="el-icon-circle-plus card-icon" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">New Vendor</div>
                                  <div class="md-subhead">{{ dasbordItem.vendorNew }}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item" @click="onCardClicked(data.vendorAdata)">
                          <md-card md-with-hover style="
                    background: #ffa726;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-user-solid" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Vendor Activation</div>
                                  <div class="md-subhead">{{data.vendorA}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item" @click="onCardClicked(data.vendorAdata)">
                          <md-card md-with-hover style="
                    background: #ff5252;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-s-opportunity" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Vendor Request</div>
                                  <div class="md-subhead">{{ data.vendorR }}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item"  @click="onCardClicked(data.submitInvdata)">
                          <md-card md-with-hover style="
                    background: #26c6da;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-s-flag" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Submitted Invoice</div>
                                  <div class="md-subhead">{{data.submitInv}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                  </div>
              </el-carousel-item>
              <el-carousel-item>
                  <div class=" md-layout md-gutter" style="padding-top: 15px">
                      <div class="md-layout-item" @click="onCardClicked(data.reversedMRdata)">
                          <md-card md-with-hover style="
                    background: linear-gradient(60deg,#66bb6a,#43a047);
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="el-icon-document-delete card-icon" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Verification Cancelled Receipt</div>
                                  <div class="md-subhead">{{data.reversedMR}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item" @click="onCardClicked(data.ApprovINVdata)">
                          <md-card md-with-hover style="
                    background: #ffa726;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-stopwatch" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Pending Approved Verification</div>
                                  <div class="md-subhead">{{data.ApprovINV}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item" @click="onCardClicked(data.ropInvdata)">
                          <md-card md-with-hover style="
                    background: #ff5252;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-copy-document" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Reopened Invoices</div>
                                  <div class="md-subhead">{{data.ropInv}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item" @click="onCardClicked(data.rejectInvdata)">
                          <md-card md-with-hover style="
                    background: #26c6da;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-s-release" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Rejected Verification</div>
                                  <div class="md-subhead">{{data.rejectInv}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                  </div>
              </el-carousel-item>
              <el-carousel-item>
                  <div class=" md-layout md-gutter" style="padding-top: 15px">
                      <div class="md-layout-item" @click="onCardClicked(data.waitForPaydata)">
                          <md-card md-with-hover style="
                    background: linear-gradient(60deg,#66bb6a,#43a047);
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="el-icon-timer card-icon" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">Waiting Payment</div>
                                  <div class="md-subhead">{{ data.waitForPay }}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item" @click="onCardClicked(data.newOrdersdata)">
                          <md-card md-with-hover style="
                    background: #ffa726;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-s-order" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">New Order</div>
                                  <div class="md-subhead">+{{data.newOrders}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
                      <div class="md-layout-item" @click="onCardClicked(data.vendorRdata)" >
                          <md-card md-with-hover style="
                    background: #ff5252;
                            width: 85px;
                            height: 85px;
                            margin: 0px;
                            border-radius: 6px;
                            top: -5%;
                            left: 5%;
                            position: relative;
                            box-shadow: 0px 12px 20px -10px;"
                          >
                              <md-card-media>
                                  <md-icon class="card-icon el-icon-coordinate" style="color: white"></md-icon>
                              </md-card-media>
                          </md-card>
                          <md-card md-with-hover style="
                            top: 3%;
                            height: 132px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                              <md-card-header-text align="right" style="margin-right: 14px;">
                                  <div class="md-title">New Event</div>
                                  <div class="md-subhead">{{dasbordItem.eventNew}}</div>
                              </md-card-header-text>

                              <div class="md-card-actions md-alignment-left" style="
                        margin: 32px 11px 7px;
                        font-size: 12px;
                        opacity: 0.7;
                        padding: 4px 0 0 0;
                        border-top: 1px solid #d0d0d0;">
                                  <div class="stats">
                                      <i class="el-icon-refresh-right"
                                         style="font-size: 20px!important;"></i>
                                      <i style="position: relative;
                                      top: -3px;"
                                      >Just Updated</i>
                                  </div>
                              </div>
                          </md-card>
                      </div>
<!--                      <div class="md-layout-item">-->
<!--                          <md-card md-with-hover style="-->
<!--                    background: #26c6da;-->
<!--                            width: 85px;-->
<!--                            height: 85px;-->
<!--                            margin: 0px;-->
<!--                            border-radius: 6px;-->
<!--                            top: -5%;-->
<!--                            left: 5%;-->
<!--                            position: relative;-->
<!--                            box-shadow: 0px 12px 20px -10px;"-->
<!--                          >-->
<!--                              <md-card-media>-->
<!--                                  <md-icon class="card-icon el-icon-data-analysis" style="color: white"></md-icon>-->
<!--                              </md-card-media>-->
<!--                          </md-card>-->
<!--                          <md-card md-with-hover style="-->
<!--                            top: 3%;-->
<!--                            height: 132px;-->
<!--                            position: unset;-->
<!--                            border-radius: 6px;-->
<!--                            margin-top: -76px;">-->

<!--                              <md-card-header-text align="right" style="margin-right: 14px;">-->
<!--                                  <div class="md-title">Benefit</div>-->
<!--                                  <div class="md-subhead">$51</div>-->
<!--                              </md-card-header-text>-->

<!--                              <div class="md-card-actions md-alignment-left" style="-->
<!--                        margin: 32px 11px 7px;-->
<!--                        font-size: 12px;-->
<!--                        opacity: 0.7;-->
<!--                        padding: 4px 0 0 0;-->
<!--                        border-top: 1px solid #d0d0d0;">-->
<!--                                  <div class="stats">-->
<!--                                      <i class="el-icon-refresh-right"-->
<!--                                         style="font-size: 20px!important;"></i>-->
<!--                                      <i style="position: relative;-->
<!--                                      top: -3px;"-->
<!--                                      >Just Updated</i>-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                          </md-card>-->
<!--                      </div>-->
                  </div>
              </el-carousel-item>
          </el-carousel>
      </template>

      <el-table
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
    </el-table>
  </div>
</template>
<script lang="ts" src="./watch-list.component.ts"></script>
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

body{
    background: #35A9DB;
    font-family: roboto;
    text-align: center;
    color: #fff;
}


</style>
