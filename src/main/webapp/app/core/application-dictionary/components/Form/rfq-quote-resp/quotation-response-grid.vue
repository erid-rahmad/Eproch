<template>
    <div class="app-container card-view prequalification-announcement">
        <div class="toolbar">
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
                :disabled="readonly"
            >
                Save as Draft
            </el-button>
            <el-button
                v-if="page === 3"
                size="mini"
                type="primary"
                @click="publish"
                :disabled="readonly"
            >
                Join RFQ
            </el-button>
        </div>
        <div class="card">
            <div v-if="page === 1">
                <el-table
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
                            {{ ((gridPage-1)*itemsPerPage) + row.$index + 1 }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="Rfq No."
                        prop="quotationNo"
                        show-overflow-tooltip
                        min-width="100"
                    ></el-table-column>
                    <el-table-column
                        label="Description"
                        prop="description"
                        show-overflow-tooltip
                        min-width="200"
                    ></el-table-column>
                    <el-table-column
                        label="Required Date"
                        prop="dateRequired"
                        show-overflow-tooltip
                        min-width="100"
                    ></el-table-column>
                    <el-table-column
                        label="RFQ status"
                        show-overflow-tooltip
                        min-width="100"
                    >
                        <template slot-scope="{row}">
                            {{formatStatus(row.dateRequired)}}
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="Joined ?"
                        show-overflow-tooltip
                        min-width="100"
                    >
                        <template slot-scope="{row}">
                            {{row.subDocStatus==='SMT'?'Yes':'No'}}
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="Modified Date"
                        show-overflow-tooltip
                        min-width="100"
                    >
                        <template slot-scope="{row}">
                            {{row.lastModifiedDate | formatDate}}
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="Modified By"
                        show-overflow-tooltip
                        prop="lastModifiedBy"
                        min-width="100"
                    >
                    </el-table-column>
                    <el-table-column width="100">
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
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination
                    ref="pagination"
                    :current-page.sync="gridPage"
                    :page-size="itemsPerPage"
                    :page-sizes="[10, 20, 50, 100]"
                    :total="queryCount"
                    background
                    layout="sizes, prev, pager, next"
                    small
                    @size-change="changePageSize"
                />
            </div>
            <quotation-proposal
                ref="proposal"
                v-else-if="page === 3"
                :data="selectedRow"
                @readOnly="readOnly"
            ></quotation-proposal>
        </div>
    </div>
</template>

<script lang="ts" src="./quotation-response-grid.component.ts">
</script>

<style lang="scss">
.compact .prequalification-announcement {
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
