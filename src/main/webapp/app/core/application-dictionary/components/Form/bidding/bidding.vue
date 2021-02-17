<template>
    <div class="app-container">
        <div v-if="index">
            <el-row class="header">
                <el-col :span="24">

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-plus"
                        @click="onClick('add')"/>

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="danger"
                        icon="el-icon-delete"
                        @click="onClick('remove')"/>

                    <el-button
                        class="button"
                        size="mini"
                        type="primary"
                        icon="el-icon-download"
                        @click="onClick('export')">
                        Export
                    </el-button>

                </el-col>
            </el-row>

            <el-row class="main">
                <el-col :span="24">
                    <el-table
                        v-loading="processing"
                        ref="gridData"
                        highlight-current-row
                        border stripe
                        size="mini"
                        style="width: 100%; height: 100%"
                        :height="gridSchema.height"
                        :max-height="gridSchema.maxHeight"
                        :default-sort="gridSchema.defaultSort"
                        :empty-text="gridSchema.emptyText"
                        :data="gridData"
                        @sort-change="changeOrder"
                        @selection-change="onSelectionChanged">

                        <el-table-column
                            align="center"
                            fixed
                            type="selection"
                            width="48"/>

                        <el-table-column
                            fixed
                            width="48"
                        >
                            <template slot-scope="{ row }">
                                <el-link
                                    type="primary"
                                    size="mini"
                                    icon="el-icon-search"
                                    plain
                                    title="View"
                                    :underline="false"
                                    @click="viewBidding(row)"
                                />
                            </template>
                        </el-table-column>

                        <el-table-column
                            min-width="50"
                            sortable
                            prop="biddingNo"
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
                            sortable
                            label="Bidding Schedule">
                            <template slot-scope="{ row }">
                                <el-button
                                    class="button"
                                    icon="el-icon-search"
                                    size="mini"
                                    type="primary"
                                    @click="viewSchedule(row)">
                                    View
                                </el-button>
                            </template>
                        </el-table-column>

                        <el-table-column
                            min-width="60"
                            prop="documentStatus"
                            label="Bidding Status"
                            sortable>
                            <template slot-scope="{ row }">
                                {{ formatDocumentStatus(row.documentStatus) }}
                            </template>
                        </el-table-column>

                        <el-table-column
                            min-width="60"
                            sortable
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

                    </el-table>
                    <el-pagination
                        ref="pagination"
                        background
                        layout="sizes, prev, pager, next"
                        mini
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
                @back="close"/>
        </div>

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
