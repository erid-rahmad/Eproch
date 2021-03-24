<template>
    <div class="app-container">
        <div v-if="index">
           <el-row class="main">
                <el-col :span="24">
                    <el-table
                        v-loading="processing"
                        ref="gridData"
                        highlight-current-row
                        border stripe
                        size="mini"
                        style="width: 100%; height: 100%"                     
                        :data="gridData1"
                        @sort-change="changeOrder"
                        @selection-change="onSelectionChanged">

                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>

                        <el-table-column
                            min-width="50"
                            sortable
                            prop="documentNo"
                            label="Bidding No"/>

                        <el-table-column
                            min-width="100"
                            sortable
                            prop="name"
                            label="Title"/>

                        <el-table-column
                            min-width="50"
                            sortable
                            prop="biddingTypeName"
                            label="Bidding Type"/>

                        <el-table-column
                            min-width="60"
                            label="Bidding Schedule">
                            <template slot-scope="{ row }">
                                <el-button
                                    class="button"
                                    icon="el-icon-search"
                                    size="mini"
                                    type="primary"
                                    @click="view(row, 2)">
                                    View
                                </el-button>
                            </template>
                        </el-table-column>

                        <el-table-column
                            min-width="60"
                            prop="documentStatus"
                            label="Bidding Status"
                            sortable>                           
                        </el-table-column>

                        <el-table-column
                            min-width="60"
                            label="Joined Vendor">
                            <template slot-scope="{ row }">
                                <el-button
                                    class="button"
                                    icon="el-icon-search"
                                    size="mini"
                                    type="primary"
                                    @click="viewJoinVendor(row)">
                                    3
                                </el-button>
                            </template>
                        </el-table-column>

                        <el-table-column
                            min-width="60"
                            sortable
                            prop="lastModifiedDate"
                            label="Modified Date"/>

                        <el-table-column
                            min-width="60"
                            sortable
                            prop="lastModifiedBy"
                            label="Modified By"/>
                        
                        <el-table-column
                            min-width="60"
                        >
                            <template slot="header">
                                &nbsp;
                            </template>
                            <template slot-scope="{ row }">
                                <el-button
                                    class="button"
                                    icon="el-icon-search"
                                    size="mini"
                                    type="primary"
                                    @click="view(row)">
                                    View

                                    
                                </el-button>
                            </template>
                        </el-table-column>

                    </el-table>
                    <el-pagination
                        ref="pagination"
                        background
                        layout="sizes, prev, pager, next"
                        small
                        :current-page.sync="page"
                        :page-sizes="[10, 20, 50, 100]"
                        :page-size="itemsPerPage"
                        :total="queryCount"
                        @size-change="changePageSize"/>
                </el-col>
            </el-row>
        </div>

            <!-- =========================================================================== -->

        <div v-else>
            <step-form
                :biddingrow = "rowsa"
                @back="close"/>
                
        </div>

        <el-dialog
            title="Joined Vendors"
            :visible.sync="vendorListVisible"
        >
            <el-table
                border
                :data="joinedVendors"
                size="mini"
            >
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

    .el-table__fixed, .el-table__fixed-right{
        box-shadow: none;
    }

    .main {
        padding: 0px;

        .button {
            width: 100%;
        }
    }

    .header{
        .button {
            margin-bottom: 5px;
        }
    }

    .form-input {
        width: 100%;
    }

</style>
