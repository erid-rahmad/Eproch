<template>
    <div class="app-container">

        <el-divider content-position="left"><h4>Event Schedule</h4></el-divider>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table
                    v-loading="processing"
                    ref="biddingSchedule"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                    
                    :data="projectinformation">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="information"
                        label="information"/>

                    <el-table-column
                        min-width="100"
                        prop="atachment"
                        label="atachment">               
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>        
    </div>
</template>

<script lang="ts" src="./bidding-schedule.component.ts"></script>
