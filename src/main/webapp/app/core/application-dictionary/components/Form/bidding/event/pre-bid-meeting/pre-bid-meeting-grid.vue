<template>
    <div class="app-container card-view pre-bid-meeting">
        <div class="toolbar">
            <el-button
                v-if="!index"
                icon="el-icon-close"
                size="mini"
                type="danger"
                @click="closeDetail"
            >
                Close
            </el-button>
        </div>
        <div class="card" v-if="index">
            <el-table v-loading="loading"
                  :data="preBidMeetingGrid"
                  border
                  size="mini"
                  style="width: 100%">

                <el-table-column align="center" label="No" min-width="40">

                    <template slot-scope="row">
                        {{ (page-1)*itemsPerPage + row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column align="center" label="Title" min-width="180" prop="biddingName" show-overflow-tooltip
                                sortable>
                </el-table-column>
                <el-table-column align="center" label="Bidding No" min-width="150" prop="biddingNo" show-overflow-tooltip
                                sortable>
                </el-table-column>
                <el-table-column align="center" label="Bidding Type" min-width="180" prop="biddingType" show-overflow-tooltip
                                sortable>
                </el-table-column>
                <el-table-column align="center" label="Bidding Status" min-width="120" prop="biddingStatus" show-overflow-tooltip
                                sortable>
                    <template slot-scope="{ row }">
                        {{ formatBiddingStatus(row.biddingStatus) }}
                    </template>
                </el-table-column>
                <el-table-column align="center" label="Modified Date" min-width="120" show-overflow-tooltip sortable>
                    <template slot-scope="{row}">
                        {{ row.lastModifiedDate | formatDate }}
                    </template>
                </el-table-column>
                <el-table-column align="center" label="Modified By" min-width="120" prop="lastModifiedBy" show-overflow-tooltip
                                sortable>
                </el-table-column>
                <el-table-column label="Action" align="center"  min-width="70">
                    <template slot-scope="{ row }">
                        <el-button  icon="el el-download-alt" :underline="false" size="mini" type="primary"
                        @click="openDetail(row)"
                        >
                            Action
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                ref="pagination"
                :current-page.sync="page"
                :page-size="itemsPerPage"
                :page-sizes="[10, 20, 50, 100]"
                :total="queryCount"
                background
                layout="sizes, prev, pager, next"
                small
                @size-change="changePageSize"/>
        </div>
        <div v-else>
            <pre-bid-meeting
                :scheduleId="selectedRow.biddingScheduleId">
            </pre-bid-meeting>
        </div>
    </div>
</template>
<script lang="ts" src="./pre-bid-meeting-grid.component.ts"></script>
