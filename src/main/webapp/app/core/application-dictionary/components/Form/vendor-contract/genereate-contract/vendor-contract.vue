<template>
    <div class="app-container card-view vendor-contract">
        <div class="toolbar">
            <el-button
                v-if="detailPage"
                icon="el-icon-close"
                size="mini"
                type="danger"
                @click="onCloseClicked"
            >
                Close
            </el-button>
            <el-button
                v-if=" detailPage && detailTabName === 'INF'"
                size="mini"
                title="Delete"
                type="primary"
                @click="generatePO"
            >Generate PO</el-button>
        </div>
        <div class="card">
            <template v-if="mainPage">
                <el-table
                    ref="mainGrid"
                    v-loading="loading"
                    :data="gridData"
                    border
                    highlight-current-row
                    size="mini"
                    stripe
                    @current-change="onCurrentRowChanged"
                    @sort-change="changeOrder"
                >
                    <el-table-column
                        fixed
                        label="No"
                        width="50"
                    >
                        <template v-slot="row">
                            {{ row.$index + 1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Contract No"
                        min-width="120"
                        prop="documentNo"
                        sortable
                    ></el-table-column>

                    <el-table-column
                        label="Title"
                        min-width="200"
                        prop="name"
                        show-overflow-tooltip
                        sortable
                    ></el-table-column>

                    <el-table-column
                        label="Type"
                        min-width="150"
                        prop="documentTypeName"
                        sortable
                    ></el-table-column>

                    <el-table-column
                        label="Price Confirmation"
                        min-width="200"
                        sortable
                    >
                        <template v-slot="{ row }">
                            <el-checkbox
                                v-model="row.forPriceConfirmation"
                                disabled
                            ></el-checkbox>
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Vendor Name"
                        prop="vendorName"
                        show-overflow-tooltip
                        sortable
                        width="200"
                    ></el-table-column>

                    <el-table-column
                        label="Start Date"
                        min-width="150"
                        sortable
                    >
                        <template v-slot="{ row }">
                            {{ row.startDate | formatDate(true) }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Expiration Date"
                        min-width="150"
                        sortable
                    >
                        <template v-slot="{ row }">
                            {{ row.expirationDate | formatDate(true) }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Modified Date"
                        min-width="150"
                        sortable
                    >
                        <template v-slot="{ row }">
                            {{ row.lastModifiedDate | formatDate }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        label="Modified by"
                        min-width="150"
                        prop="lastModifiedBy"
                        sortable
                    ></el-table-column>

                    <el-table-column
                        fixed="right"
                        label="Action"
                        width="180"
                    >
                        <template v-slot="{ row }">
                            <el-button
                                size="mini"
                                type="primary"
                                @click="viewDetails(row)"
                            >
                                <svg-icon name="link"></svg-icon>
                                View
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
                    @size-change="changePageSize"
                ></el-pagination>
            </template>
            <contract-info
                v-else-if="detailPage"
                ref="contractInfo"
                :data.sync="selectedRow"
                :tab.sync="detailTabName">
            </contract-info>
        </div>
    </div>
</template>
<script lang="ts" src="./vendor-contract.component.ts"></script>
<style lang="scss">
.compact .vendor-contract {

    .el-table--mini {

        th,
        td {
            height: 35px;
        }
    }

    .toolbar {
        padding: 4px 16px 0;

        .el-button + .el-button {
            margin-left: 8px;
        }
    }
}
</style>
