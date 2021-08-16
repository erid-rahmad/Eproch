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
        <el-dialog :visible.sync="ScheduleListVisible" title="Bidding Schedule" width="60%">
            <el-form
            disabled
            label-position="left"
            label-width="150px"
            size="mini"
            >
                <el-row :gutter="24">
                    <el-col
                    :xs="24"
                    :sm="24"
                    :lg="12"
                    :xl="8"
                    >
                        <el-form-item label="Title">
                            {{ pickRow.prequalificationName }}
                        </el-form-item>
                        <el-form-item label="Prequalification No">
                            {{ pickRow.prequalificationNo }}
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-row>
                <el-col
                    :xs="24"
                    :sm="24"
                    :xl="18"
                >
                    <el-table
                    v-loading="loading"
                    ref="biddingSchedules"
                    border
                    :data="biddingSchedules"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    highlight-current-row
                    size="mini"
                    stripe
                    style="width: 100%"
                    >
                    <el-table-column
                        label="No"
                        width="50"
                    >
                        <template slot-scope="row">
                        {{ row.$index + 1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Event"
                        min-width="200"
                        prop="eventLineName"
                        show-overflow-tooltip
                    ></el-table-column>

                    <el-table-column
                        width="422"
                        prop="actual"
                        label="Schedule"
                    >
                        <template slot-scope="{ row }">
                        <el-date-picker
                            v-model="row.actual"
                            disabled
                            :format="dateDisplayFormat"
                            end-placeholder="End Datetime"
                            range-separator="To"
                            size="mini"
                            start-placeholder="Start Datetime"
                            type="datetimerange"
                        ></el-date-picker>
                        </template>
                    </el-table-column>

                    <el-table-column
                        fixed="right"
                        label="Action"
                        min-width="200"
                    >
                        <!--
                        <template slot-scope="{ row }">
                            <el-button
                                v-if="row.status && row.status !== 'N'"
                                size="mini"
                                type="primary"
                                @click="viewEvent(row)"
                            >
                                <svg-icon name="link"></svg-icon> View
                            </el-button>
                        </template>
                        -->
                    </el-table-column>

                    </el-table>
                </el-col>
            </el-row>
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
