<template>
    <div class="app-container card-view bidding-announcement">
        <div class="toolbar">
            <el-button
                v-if="page === 1"
                icon="el-icon-plus"
                size="mini"
                type="primary"
                @click="addAnnouncement"
            >
                Add
            </el-button>
            <el-button
                v-if="page > 1"
                icon="el-icon-close"
                size="mini"
                type="danger"
                @click="onFormClosed"
            >
                Close
            </el-button>
            <el-button
                v-if="page === 3"
                size="mini"
                type="primary"
                @click="saveAsDraft"
            >
                Save as Draft
            </el-button>
            <el-button
                v-if="page === 3"
                size="mini"
                type="primary"
                @click="openRecipientList"
            >
                Publish
            </el-button>
        </div>
        <div class="card">
            <el-table
                v-if="page === 1"
                ref="mainGrid"
                v-loading="loading"
                :data="gridData"
                :default-sort="gridSchema.defaultSort"
                :empty-text="gridSchema.emptyText"
                border
                highlight-current-row
                size="mini"
                stripe
            >
                <el-table-column label="No" width="50">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column
                    label="Bidding No"
                    prop="biddingDocNo"
                    show-overflow-tooltip
                    sortable
                    width="200"
                ></el-table-column>
                <el-table-column
                    label="Title"
                    prop="biddingName"
                    show-overflow-tooltip
                    sortable
                    width="250"
                ></el-table-column>
                <el-table-column width="200">
                    <template slot="header">
                        &nbsp;
                    </template>
                    <template slot-scope="{ row }">
                        <el-button
                            icon="el-icon-search"
                            size="mini"
                            type="primary"
                            @click="viewDetails(row)"
                        >
                            View
                        </el-button>
                        <el-button
                            size="mini"
                            type="primary"
                            @click="moreInfo(row)"
                        >
                            <svg-icon name="icomoo/269-info"></svg-icon> Invitation
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <bidding-invitation-response
                v-if="page === 2"
                :moreinfo="moreinfo"
                @back="back"
            ></bidding-invitation-response>

            <announcement-form
                v-else-if="page === 3"
                ref="announcementForm"
                :new-record="newRecord"
                :schedule-id="selectedRow.biddingScheduleId"
            ></announcement-form>
        </div>
    </div>
</template>

<script lang="ts" src="./event-announcement.component.ts">
</script>

<style lang="scss">
.compact .bidding-announcement {
    .el-table--mini {

        th,
        td {
            height: 35px;
        }
    }

    .toolbar {
        padding: 4px 16px 0;

        .el-button + .el-button {
            margin-left: 0;
        }
    }
}

</style>
