<template>
    <div class="app-container">
        
        <div v-if="index">
            
            <el-row >
                <el-col :span="24">
                    <el-table :data="gridData1" style="width: 100%" size="mini" >
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>

                        <el-table-column min-width="80" sortable prop="documentNo" label="Bidding No" />

                        <el-table-column min-width="100" sortable prop="name" label="Title" />

                        <el-table-column min-width="100" sortable prop="biddingTypeName" label="Bidding Type" />

                        <el-table-column min-width="100" label="Bidding Schedule">
                            <template slot-scope="{ row }">
                                <el-button class="button" icon="el-icon-search" size="mini" type="primary" @click="view(row, 2)">
                                    View
                                </el-button>
                            </template>
                        </el-table-column>

                        <el-table-column min-width="100" prop="documentStatus" label="Bidding Status" sortable>
                        </el-table-column>


                        <el-table-column min-width="100" label="Joined Vendor">
                            <template slot-scope="{ row }">
                                <el-button class="button" icon="el-icon-search" size="mini" type="primary" @click="viewJoinVendor(row)">
                                    3
                                </el-button>
                            </template>
                        </el-table-column>

                        <el-table-column min-width="100" sortable prop="lastModifiedDate" label="Modified Date" />

                        <el-table-column min-width="100" sortable prop="lastModifiedBy" label="Modified By" />
                        <el-table-column min-width="90" label="Join Status">                       
                            <template slot-scope="{ row }">
                                <el-switch v-model="row.value1" active-text="Yes" size="mini" inactive-text="No">
                                </el-switch>
                            </template>
                        </el-table-column>
                        <el-table-column min-width="60">
                            <template slot="header">
                                &nbsp;
                            </template>
                            <template slot-scope="{ row }">
                                <el-button class="button" icon="el-icon-search" size="mini" type="primary" @click="view(row)">
                                    View
                                </el-button>
                            </template>
                        </el-table-column>

                    </el-table>
                   
                </el-col>
            </el-row>
        </div>

        <!-- =========================================================================== -->

        <div v-else>
            <step-form :biddingrow="rowsa" @back="close" />

        </div>

        <el-dialog title="Joined Vendors" :visible.sync="vendorListVisible">
            <el-table border :data="joinedVendors" size="mini">
                <el-table-column width="50" label="No">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column property="vendorName" label="Vendor Name" width="200" show-overflow-tooltip></el-table-column>
                <el-table-column property="address" label="Address" min-width="200" show-overflow-tooltip></el-table-column>
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
