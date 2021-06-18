<template>
    <div class="app-container card-view">
        <div class="toolbar">
<!--            <el-button v-if="!index" icon="el-icon-close" size="mini" type="danger" @click="close" >-->
<!--                Close-->
<!--            </el-button>-->
<!--            <el-button v-if="!index"  size="mini" type="primary" >-->
<!--                <svg-icon name="icomoo/273-checkmark"></svg-icon>-->
<!--                Save-->
<!--            </el-button>-->

        </div>
        <div v-if="index" class="card">
            <el-table
                ref="mainGrid"
                v-loading="processing"
                :data="gridData"
                border
                highlight-current-row
                size="mini"
                stripe
            >
                <el-table-column align="center" fixed="right" width="100">
                    <template slot-scope="{ row }">
                        <el-button :underline="false" icon="el-icon-search" size="mini" title="View" type="primary"
                                   @click="view(row)"
                        >View
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="Bidding No." min-width="150" prop="documentNo" sortable>
                </el-table-column>
                <el-table-column label="Title" min-width="140" prop="name" show-overflow-tooltip
                                 sortable>
                </el-table-column>

                <el-table-column label="Bidding Type" min-width="130" prop="biddingTypeName" show-overflow-tooltip
                                 sortable>
                </el-table-column>

                <el-table-column label="Bidding Schedule" min-width="140">
                    <template slot-scope="{ row }">
                        <el-button class="button" size="mini" style="width: 100%">
                            <svg-icon name="icomoo/084-calendar"></svg-icon>
                            View Schedule
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="Bidding Status" min-width="140" sortable>
                    <template slot-scope="{ row }">
                        {{ formatBiddingStatus(row.biddingStatus) }}
                    </template>
                </el-table-column>
                <el-table-column label="Joined Vendor" min-width="140">
                    <template slot-scope="{ row }">
                        <el-button class="button" size="mini" style="width: 100%">
                            <svg-icon name="icomoo/115-users"></svg-icon>
                            {{ row.joinedVendorCount }}
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="Modified Date" min-width="150" prop="lastModifiedDate" sortable>
                    <template slot-scope="{ row }">
                        {{ row.lastModifiedDate | formatDate }}
                    </template>
                </el-table-column>
                <el-table-column label="Modified By" min-width="150" prop="lastModifiedBy" sortable></el-table-column>
            </el-table>
        </div>
        <div v-if="!index">
            <bidding-submission :scheduleFromGrid="scheduleFromGrid"></bidding-submission>

        </div>
    </div>
</template>
<script lang="ts" src="./bidding-submission-grid.component.ts">
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
