<template>
    <div>
        <div class="md-layout md-gutter" v-if="type == 'CHART'" style="margin: 10px 1px;">
            <div :id="'LayoutItem-' + id + '-' + index" 
                class="md-layout-item"
                v-for="(item, index) in chartItems"
                    :key="item.chartData"
                style="padding: 0 10px !important;"
            >
                <md-card v-loading="item.isLoading" md-with-hover class="bg-pattern" style="width: 100%; border-radius: 6px; padding: 10px 12px;">
                    <line-chart :id="'LineChart-' + item.id + '-' + index" 
                        v-if="item.serviceName == 'line'"
                        :title="item.name" 
                        :value="JSON.parse(JSON.stringify(item.chartData))" 
                        :position="item.icon"
                        :colors="listColor.map((x) => { return x.staticColor; })"
                    />
                    <bar-chart :id="'BarChart-' + item.id + '-' + index" 
                        v-if="item.serviceName == 'bar' || item.serviceName == 'horizontalBar'"
                        :title="item.name" 
                        :chartType="item.serviceName"
                        :value="JSON.parse(JSON.stringify(item.chartData))" 
                        :position="item.icon"
                        :colors="listColor.map((x) => { return x.staticColor; })"
                    />
                    <!--<pie-chart :id="'PieChart-' + item.id + '-' + index" 
                        :title="item.name" 
                        :chartType="item.serviceName"
                        :value="JSON.parse(JSON.stringify(item.chartData))" 
                        :position="item.icon"
                        :colors="listColor.map((x) => { return x.staticColor; })"
                    />-->
                </md-card>
            </div>
        </div>
        <div class=" md-layout md-gutter" v-if="type == 'CUSTOM'" style="margin: 10px 1px;">
            <div :id="'LayoutItem-' + id + '-' + index" 
                class="md-layout-item"
                v-for="(item, index) in gridItems"
                    :key="item.id"
                style="padding: 0 10px !important; max-width: 100%;"
            >
                <md-card v-loading="item.isLoading" md-with-hover class="bg-pattern" style="width: 100%; border-radius: 6px; padding: 10px 12px;">
                    <el-table stripe border
                        highlight-current-row
                        size="mini"
                        ref="table"
                        :data="JSON.parse(JSON.stringify(item.gridData))"
                        :style="'width: 100%; cursor: pointer; border-radius: 5px; box-shadow: 0px 0px 3px ' + item.accentColor + ' !important;'"
                        cell-style="color: #8898aa !important; font-size: 13px; font-weight: bold; "
                        :header-cell-style="'color: white !important; background:'+ item.accentColor +' !important; '"
                    >
                        <!--<el-table-column min-width="20" type="index" align="center" label=""></el-table-column>-->
                        <el-table-column
                            v-for="(columnItem, columnIdx) in item.serviceName.split('##')"
                            :key="columnItem"
                            :label="columnItem"
                            :prop="columnItem.replace(/ /g, '')"
                            :resizable="true"
                            :min-width="item.websocketEndpoint.split('##')[columnIdx]"
                        >
                        </el-table-column>
                    </el-table>
                </md-card>
            </div>
        </div>
    </div>

  <!-- <div>

      <div class="md-layout md-gutter">
          <div class="md-layout-item">
              <md-card md-with-hover style="
                    background: white;
                    margin-right: 20%;
                    margin-left: 4%;
                    height: 235px;
                    border-radius: 7px;
                    top: 3%;
                    position: relative;
                    box-shadow: 0px 12px 20px -10px;"
              >
                  <echart style="padding-left: 1px;padding-right: 1px"></echart>
              </md-card>
              <md-card md-with-hover style="
                    top: 1%;
                    height: 264px;
                    position: unset;
                    border-radius: 6px;
                    margin-top: -197px;
                    background-color: #ffa726;">
                  <md-card-content style="padding: 23px"></md-card-content>
                  <md-card-header-text align="right" style="margin-right: 14px;">
                      <div class="md-title-chart">This</div>
                      <div class="md-title-chart">Valid</div>
                      <div class="md-title-chart">Data</div>
                  </md-card-header-text>

                  <div class="md-card-actions md-alignment-left" style="
                        margin: 43px 11px 7px;
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
          <div class="md-layout-item">
              <md-card md-with-hover style="
                    background: white;
                    margin-right: 20%;
                    margin-left: 4%;
                    height: 235px;
                    border-radius: 7px;
                    top: 3%;
                    position: relative;
                    box-shadow: 0px 12px 20px -10px;"
              >
                                 <circular-color-bar style="padding: 1px"></circular-color-bar>-->
                  <!--<echartpie style="padding: 1px"></echartpie>
              </md-card>
              <md-card md-with-hover style="
                    top: 1%;

                    height: 264px;
                    position: unset;
                    border-radius: 6px;
                    margin-top: -197px;
                    background-color: #26c6da;">
                  <md-card-content style="padding: 23px"></md-card-content>

                  <md-card-header-text align="right" style="margin-right: 14px;">
                      <div class="md-title-chart">This</div>
                      <div class="md-title-chart">Confirm</div>
                      <div class="md-title-chart">Status</div>
                  </md-card-header-text>

                  <div class="md-card-actions md-alignment-left" style="
                        margin: 43px 11px 7px;
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
      </div>-->

<!--      <div class="md-layout md-gutter">-->
<!--          <div class="md-layout-item">-->
<!--              <md-card md-with-hover style="-->
<!--                    background: white;-->
<!--                    margin-right: 4%;-->
<!--                    margin-left: 4%;-->
<!--                    height: 235px;-->
<!--                    border-radius: 7px;-->
<!--                    top: 3%;-->
<!--                    position: relative;-->
<!--                    box-shadow: 0px 12px 20px -10px;"-->
<!--              >-->
<!--                  <rateOfContract style="padding-left: 1px;padding-right: 1px"></rateOfContract>-->
<!--              </md-card>-->
<!--              <md-card md-with-hover style="-->
<!--                    top: 1%;-->
<!--                    height: 264px;-->
<!--                    position: unset;-->
<!--                    border-radius: 6px;-->
<!--                    margin-top: -197px;-->
<!--                    background-color: #ff5252;">-->
<!--                  <md-card-content style="padding: 89px;"></md-card-content>-->
<!--                  <md-card-header-text align="right" style="margin-right: 14px;">-->
<!--                      <div class="md-title"></div>-->
<!--                      <div class="md-subhead">50</div>-->
<!--                  </md-card-header-text>-->
<!--                  <div class="md-card-actions md-alignment-left" style="-->
<!--                        margin: 37px 11px 7px;-->
<!--                        font-size: 12px;-->
<!--                        opacity: 0.7;-->
<!--                        padding: 4px 0 0 0;-->
<!--                        border-top: 1px solid #d0d0d0;">-->
<!--                      <div class="stats">-->
<!--                          <i class="el-icon-refresh-right"-->
<!--                             style="font-size: 20px!important;"></i>-->
<!--                          <i style="position: relative;-->
<!--                                      top: -3px;"-->
<!--                          >Just Updated</i>-->
<!--                      </div>-->
<!--                  </div>-->
<!--              </md-card>-->
<!--          </div>-->
<!--          <div class="md-layout-item">-->
<!--              <md-card md-with-hover style="-->
<!--                    background: white;-->
<!--                    margin-right: 4%;-->
<!--                    margin-left: 4%;-->
<!--                    height: 235px;-->
<!--                    border-radius: 7px;-->
<!--                    top: 3%;-->
<!--                    position: relative;-->
<!--                    box-shadow: 0px 12px 20px -10px;"-->
<!--              >-->
<!--                  <lineupdate style="padding: 1px"></lineupdate>-->
<!--              </md-card>-->
<!--              <md-card md-with-hover style="-->
<!--                    top: 1%;-->
<!--                    height: 264px;-->
<!--                    position: unset;-->
<!--                    border-radius: 6px;-->
<!--                    margin-top: -197px;-->
<!--                    background-color: #ffa726;">-->
<!--                  <md-card-content style="padding: 89px;"></md-card-content>-->
<!--                  <md-card-header-text align="right" style="margin-right: 14px;">-->
<!--                      <div class="md-title"></div>-->
<!--                      <div class="md-subhead">50</div>-->
<!--                  </md-card-header-text>-->
<!--                  <div class="md-card-actions md-alignment-left" style="-->
<!--                        margin: 37px 11px 7px;-->
<!--                        font-size: 12px;-->
<!--                        opacity: 0.7;-->
<!--                        padding: 4px 0 0 0;-->
<!--                        border-top: 1px solid #d0d0d0;">-->
<!--                      <div class="stats">-->
<!--                          <i class="el-icon-refresh-right"-->
<!--                             style="font-size: 20px!important;"></i>-->
<!--                          <i style="position: relative;-->
<!--                                      top: -3px;"-->
<!--                          >Just Updated</i>-->
<!--                      </div>-->
<!--                  </div>-->
<!--              </md-card>-->
<!--          </div>-->
<!--          <div class="md-layout-item">-->
<!--              <md-card md-with-hover style="-->
<!--                    background: white;-->
<!--                    margin-right: 4%;-->
<!--                    margin-left: 4%;-->
<!--                    height: 235px;-->
<!--                    border-radius: 7px;-->
<!--                    top: 3%;-->
<!--                    position: relative;-->
<!--                    box-shadow: 0px 12px 20px -10px;"-->
<!--              >-->
<!--                  <lineexample style="padding: 1px"></lineexample>-->
<!--              </md-card>-->
<!--              <md-card md-with-hover style="-->
<!--                    top: 1%;-->
<!--                    height: 264px;-->
<!--                    position: unset;-->
<!--                    border-radius: 6px;-->
<!--                    margin-top: -197px;-->
<!--                    background-color: #26c6da;">-->
<!--                  <md-card-content style="padding: 89px;"></md-card-content>-->
<!--                  <md-card-header-text align="right" style="margin-right: 14px;">-->
<!--                      <div class="md-title"></div>-->
<!--                      <div class="md-subhead">50</div>-->
<!--                  </md-card-header-text>-->
<!--                  <div class="md-card-actions md-alignment-left" style="-->
<!--                        margin: 37px 11px 7px;-->
<!--                        font-size: 12px;-->
<!--                        opacity: 0.7;-->
<!--                        padding: 4px 0 0 0;-->
<!--                        border-top: 1px solid #d0d0d0;">-->
<!--                      <div class="stats">-->
<!--                          <i class="el-icon-refresh-right"-->
<!--                             style="font-size: 20px!important;"></i>-->
<!--                          <i style="position: relative;-->
<!--                                      top: -3px;"-->
<!--                          >Just Updated</i>-->
<!--                      </div>-->
<!--                  </div>-->
<!--              </md-card>-->
<!--          </div>-->
<!--      </div>-->

      <!--<div class=" md-layout md-gutter" style="padding-top: 25px">
          <div class="md-layout-item">
              <md-card md-with-hover style="
                    background: #26c6da;
                              background: rgb(38, 198, 218);
                                width: 90%;
                                height: 85px;
                                margin: 0px;
                                margin-bottom: 16px;
                                border-radius: 6px;
                                top: -5%;
                                left: 5%;
                                position: relative;
                            box-shadow: 0px 12px 20px -10px;">
                  <md-card-media>
                      <div class="md-title-chart"
                           style="padding-top: 16px;
                                    padding-left: 30px;
                                    font-size: 31px;">
                          <i class="el-icon-mobile" style="color: white;
                                            margin-right: 20px;
                                             font-size: 51px !important;
                                            ;">

                          </i>
                          <i>TOP Vendor Purchase Amount</i>

                      </div>
                  </md-card-media>

              </md-card>
              <md-card md-with-hover style="
                            top: 3%;
                            padding-top: 50px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                  <md-card-header-text align="right" style="
                        margin-right: 14px;
                        margin-left: 14px;">
                      <template >
                          <el-table
                              :data="dataPO"
                              height="250"
                          >
                              <el-table-column
                                  label="Code"
                                  width="180px"
                                  prop="warehouseId"
                              >
                              </el-table-column>
                              <el-table-column
                                  label="Vendor Name"
                                  width="280px"
                                  prop="vendorName"
                              >
                              </el-table-column>
                              <el-table-column
                                  label="Total"
                                  width="280px"
                              >
                                  <template slot-scope="{row}">
                                     {{ row.grandTotal | formatCurre  }} 
                                  </template>
                              </el-table-column>

                          </el-table>
                      </template>
                  </md-card-header-text>

                  <div class="md-card-actions md-alignment-left" style="
                        margin: 37px 11px 7px;
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
          <div class="md-layout-item">
              <md-card md-with-hover style="
                    background: #26c6da;
                              background: rgb(38, 198, 218);
                                width: 90%;
                                height: 85px;
                                margin: 0px;
                                margin-bottom: 16px;
                                border-radius: 6px;
                                top: -5%;
                                left: 5%;
                                position: relative;
                            box-shadow: 0px 12px 20px -10px;">
                  <md-card-media>
                      <div class="md-title-chart"
                           style="padding-top: 16px;
                                    padding-left: 30px;
                                    font-size: 31px;">
                          <i class="el-icon-mobile" style="color: white;
                                            margin-right: 20px;
                                             font-size: 51px !important;
                                            ;">

                          </i>
                          <i>My Document</i>

                      </div>
                  </md-card-media>

              </md-card>
              <md-card md-with-hover style="
                            top: 3%;
                            padding-top: 50px;
                            position: unset;
                            border-radius: 6px;
                            margin-top: -76px;">

                  <md-card-header-text align="right" style="
                        margin-right: 14px;
                        margin-left: 14px;">
                      <template>
                          <el-table
                              :data="dataMyDocument"
                              height="250"

                          >
                              <el-table-column
                                  label="Document No"
                                  prop="documentNo"
                              >
                              </el-table-column>
                              <el-table-column
                                  label="Title"
                                  prop="title"
                              >
                              </el-table-column>
                              <el-table-column
                                  label="Status"

                              ><template slot-scope="{row}">{{printStatus(row.status)}}</template>
                              </el-table-column>

                              <el-table-column
                                  label="Date"
                                  width="180px"
                              >
                                  <template slot-scope="{row}">
                                      {{ row.date | formatDate }}
                                  </template>
                              </el-table-column>

                          </el-table>
                      </template>
                  </md-card-header-text>

                  <div class="md-card-actions md-alignment-left" style="
                        margin: 37px 11px 7px;
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
      </div>-->

<!--      <div class=" md-layout md-gutter" style="padding-top: 25px">-->
<!--          <div class="md-layout-item">-->
<!--              <md-card md-with-hover style="-->
<!--                    background: #26c6da;-->
<!--                              background: rgb(38, 198, 218);-->
<!--                                width: 90%;-->
<!--                                height: 85px;-->
<!--                                margin: 0px;-->
<!--                                margin-bottom: 16px;-->
<!--                                border-radius: 6px;-->
<!--                                top: -5%;-->
<!--                                left: 5%;-->
<!--                                position: relative;-->
<!--                            box-shadow: 0px 12px 20px -10px;">-->
<!--                  <md-card-media>-->
<!--                      <div class="md-title-chart"-->
<!--                           style="padding-top: 16px;-->
<!--                                    padding-left: 30px;-->
<!--                                    font-size: 31px;">-->
<!--                          <i class="el-icon-mobile" style="color: white;-->
<!--                                            margin-right: 20px;-->
<!--                                             font-size: 51px !important;-->
<!--                                            ;">-->

<!--                          </i>-->
<!--                          <i>Vendor Quality Rating</i>-->

<!--                      </div>-->
<!--                  </md-card-media>-->

<!--              </md-card>-->
<!--              <md-card md-with-hover style="-->
<!--                            top: 3%;-->
<!--                            padding-top: 50px;-->
<!--                            position: unset;-->
<!--                            border-radius: 6px;-->
<!--                            margin-top: -76px;">-->

<!--                  <md-card-header-text align="right" style="-->
<!--                        margin-right: 14px;-->
<!--                        margin-left: 14px;">-->
<!--                      <template >-->
<!--                          <el-table-->
<!--                              :data="score"-->
<!--                              height="250"-->
<!--                          >-->
<!--                              <el-table-column-->
<!--                                  label="Vendor Name"-->
<!--                                  prop="vendorName"-->
<!--                              >-->
<!--                              </el-table-column>-->
<!--                              <el-table-column-->
<!--                                  label="Ordered"-->
<!--                                  prop="Ordered"-->
<!--                              >-->
<!--                              </el-table-column>-->
<!--                              <el-table-column-->
<!--                                  label="Returned"-->
<!--                                  prop="Returned"-->
<!--                              >-->
<!--                              </el-table-column>-->
<!--                              <el-table-column-->
<!--                                  label="Availability"-->
<!--                                  prop="Availability"-->
<!--                              >-->
<!--                              </el-table-column>-->
<!--                              <el-table-column-->
<!--                                  label="Defect Rate"-->
<!--                                  prop="Defect"-->
<!--                              >-->
<!--                              </el-table-column>-->
<!--                              <el-table-column-->
<!--                                  label="score"-->
<!--                                  width="280px"-->
<!--                                  prop="score"-->
<!--                              >-->
<!--                              </el-table-column>-->

<!--                          </el-table>-->
<!--                      </template>-->
<!--                  </md-card-header-text>-->

<!--                  <div class="md-card-actions md-alignment-left" style="-->
<!--                        margin: 37px 11px 7px;-->
<!--                        font-size: 12px;-->
<!--                        opacity: 0.7;-->
<!--                        padding: 4px 0 0 0;-->
<!--                        border-top: 1px solid #d0d0d0;">-->
<!--                      <div class="stats">-->
<!--                          <i class="el-icon-refresh-right"-->
<!--                             style="font-size: 20px!important;"></i>-->
<!--                          <i style="position: relative;-->
<!--                                      top: -3px;"-->
<!--                          >Just Updated</i>-->
<!--                      </div>-->
<!--                  </div>-->
<!--              </md-card>-->
<!--          </div>-->

<!--      </div>-->

 <!-- </div> -->
</template>

<script lang="ts" src="./kpi-admin.component.ts"></script>
<style lang="scss" scoped>
    .md-list-item:hover{
        padding: 0px;
        background-color: rgba(0,0,0,0.12) !important;
        border-color: rgba(0,0,0,0.24);
    }

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
