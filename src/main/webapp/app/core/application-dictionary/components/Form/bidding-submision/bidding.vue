<template>
    <div class="app-container">

        <div v-if="index">

            <el-row>
                <el-col :span="24">
                    <el-table :data="gridData1" style="width: 100%" size="mini">
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="80" sortable prop="documentNo" label="Bidding No" />

                        <el-table-column min-width="200" sortable prop="name" label="Title" />

                        <el-table-column min-width="100" sortable prop="biddingTypeName" label="Bidding Type" />

                        <el-table-column min-width="100" label="Bidding Schedule">
                            <template slot-scope="{ row }">
                                <el-button class="button" icon="el-icon-search" size="mini" type="primary" @click="viewJoinVendor(row)">
                                    View
                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column min-width="100" prop="documentStatus" label="Bidding Status" sortable>
                        </el-table-column>
                        <el-table-column min-width="100" prop="subStatus" label="Submission Status" sortable>
                        </el-table-column>
                        <el-table-column min-width="100" label="Action">
                            <template slot-scope="{ row }">
                                <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="Action(row)">
                                  Action
                                </el-button>
                            </template>
                        </el-table-column>

                    </el-table>

                </el-col>
            </el-row>
        </div>
        <div v-else>
            <BiddingSubmission :biddingrow="rowsa" @back="close" />
        </div>

        <el-dialog title="Bidding Schedule" :visible.sync="vendorListVisible" width="90%">
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
                        <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="view(row)">
                           Action
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./bidding.component.ts">
</script>

<style lang="scss">
    .el-table__fixed,
    .el-table__fixed-right {
        box-shadow: none;
    }

    .main {
        padding: 0px;

        .button {
            width: 100%;
        }
    }

    .header {
        .button {
            margin-bottom: 5px;
        }
    }

    .form-input {
        width: 100%;
    }

</style>
