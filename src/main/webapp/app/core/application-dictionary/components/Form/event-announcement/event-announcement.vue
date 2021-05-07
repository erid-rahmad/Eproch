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
                v-loading="loading"
                ref="mainGrid"
                border
                :data="gridData"
                :default-sort="gridSchema.defaultSort"
                :empty-text="gridSchema.emptyText"
                highlight-current-row
                size="mini"
                stripe
            >
                <el-table-column width="50" label="No">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column min-width="80" sortable prop="biddingDocNo" label="Bidding No" />
                <el-table-column min-width="100" sortable prop="biddingName" label="Title" />
                <el-table-column align="center" min-width="20">
                    <template slot="header">
                        &nbsp;
                    </template>
                    <template slot-scope="{ row }">
                        <el-button
                            size="mini"
                            icon="el-icon-search"
                            type="primary"
                            @click="viewDetails(row)"
                        >View</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- <details-announcement-form
                v-if="page === 2"
                @back="back"
                :moreinfo="moreinfo"
            ></details-announcement-form> -->

            <announcement-form
                v-else-if="page === 3"
                ref="announcementForm"
                :schedule-id="selectedRow.biddingScheduleId"
            ></announcement-form>   
        </div>
        <!-- <el-dialog title="Joined Vendors" :visible.sync="vendorListVisible">
            <el-table border :data="joinedVendors" size="mini">
                <el-table-column width="50" label="No">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column property="vendorName" label="Vendor Name" width="200" show-overflow-tooltip></el-table-column>
                <el-table-column property="address" label="Address" min-width="200" show-overflow-tooltip></el-table-column>
            </el-table>
        </el-dialog> -->
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
