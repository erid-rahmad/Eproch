<template>
    <div class="app-container card-view bidding-process">
        <div></div>
        <div></div>
        <el-row v-if="index" class="main" ref="tableWrapper">
            <el-col :span="24">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <keep-alive>
                        <el-col :span="24">
                            <el-table :data="biddingResults" size="mini" style="width: 100%">
                                <el-table-column min-width="50" label="No">
                                    <template slot-scope="row">
                                        {{ row.$index+1 }}
                                    </template>
                                </el-table-column>
                                <el-table-column prop="biddingNo" label="Bidding Number" sortable width="100">
                                </el-table-column>
                                <el-table-column prop="biddingName" label="Title" sortable min-width="210">
                                </el-table-column>
                                <el-table-column prop="biddingType" label="Bidding Type" sortable min-width="210">
                                </el-table-column>
                                <el-table-column prop="3" label="Bidding Schedule" sortable min-width="150">
                                    <template slot-scope="{ row }">
                                        <el-button class="btn-attachment" icon="el-icon-search" size="mini" type="primary" @click="ScheduleListVisible=true">
                                            View
                                        </el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="biddingStatus" label="Bidding Status" sortable min-width="210">
                                </el-table-column>
                                <el-table-column  label="Action" sortable min-width="180">
                                    <template slot-scope="{ row }">
                                        <el-button class="btn-attachment" size="mini" type="primary" @click="view(row)">
                                            Action
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-col>
                    </keep-alive>
                </el-tabs>
            </el-col>
        </el-row>
        <el-dialog title="Bidding Schedule" :visible.sync="ScheduleListVisible" width="90%">
            <el-table border :data="BiddingSchedule" size="mini">
                <el-table-column width="60" label="No">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column property="event" label="Event" width="200" show-overflow-tooltip></el-table-column>
                <el-table-column property="startdate" label="Start Date" min-width="200" show-overflow-tooltip></el-table-column>
                <el-table-column property="finisdate" label="Finish Date" min-width="200" show-overflow-tooltip></el-table-column>
                <el-table-column min-width="100" label="Action">
                    <template slot-scope="{ row }">
                        <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="index=false;ScheduleListVisible=false">
                            Action
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-row v-if="!index" class="main">
            <announcementDetail @back="back" :pickRow="pickRow" ></announcementDetail>
        </el-row>

    </div>
</template>

<script lang="ts" src="./evaluation-announcement-vendor.component.ts">
</script>

<style lang="scss">
    .el-tabs__header {
        margin: 0px;
    }

    .el-table__fixed {
        box-shadow: none;
    }

    .main {
        padding: 0px;
    }



    .form-input {
        width: 100%;
    }

</style>
