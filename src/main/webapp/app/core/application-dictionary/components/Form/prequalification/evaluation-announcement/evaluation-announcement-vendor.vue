<template>
    <div class="app-container card-view bidding-process">
        <el-row v-if="index"  class="card">
            <el-table :data="biddingResults"
                      border
                      size="mini" style="width: 100%">
                <el-table-column label="No" align="center" min-width="50">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Prequalification Number" align="center" prop="prequalificationNo" sortable min-width="150">
                </el-table-column>
                <el-table-column label="Title" min-width="150" prop="prequalificationName" sortable>
                </el-table-column>
                <el-table-column min-width="150" sortable align="center" label="Prequalification Type">
                            <template slot-scope="{ row }">
                                {{ row.type == 'O' ? 'Announcement' : 'Invitation' }}
                            </template>
                        </el-table-column>
                <el-table-column min-width="150" label="Prequalification Schedule">
                    <template slot-scope="{ row }">
                        <el-button class="button" size="mini" style="width: 100%" @click="viewSchedule(row)">
                            <svg-icon name="icomoo/084-calendar"></svg-icon> View Schedule
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="Bidding Status" align="center" min-width="150"  sortable>
                    <template slot-scope="{ row }">
                        {{ formatBiddingStatus(row.prequalificationStatus) }}
                    </template>
                </el-table-column>
                <el-table-column label="Action" align="center" min-width="100" sortable>
                    <template slot-scope="{ row }">
                        <el-button class="btn-attachment" size="mini" type="primary" @click="view(row)">
                            Action
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
        <el-dialog :visible.sync="ScheduleListVisible" title="Bidding Schedule" width="90%">
            <el-table :data="BiddingSchedule" border size="mini">
                <el-table-column label="No" width="60">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Event" property="event" show-overflow-tooltip width="200"></el-table-column>
                <el-table-column label="Start Date" min-width="200" property="startdate"
                                 show-overflow-tooltip></el-table-column>
                <el-table-column label="Finish Date" min-width="200" property="finisdate"
                                 show-overflow-tooltip></el-table-column>
                <el-table-column label="Action" min-width="100">
                    <template slot-scope="{ row }">
                        <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary"
                                   @click="index=false;ScheduleListVisible=false">
                            Action
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
        <el-row v-if="!index" class="main">
            <announcementDetail :pickRow="pickRow" @back="back"></announcementDetail>
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
