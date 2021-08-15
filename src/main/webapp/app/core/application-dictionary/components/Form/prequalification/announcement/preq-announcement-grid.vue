<template>
    <div class="app-container card-view prequalification-announcement">
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
                :disabled="disabled"
            >
                Save as Draft
            </el-button>
            <el-button
                v-if="page === 3"
                size="mini"
                type="primary"
                @click="openRecipientList"
                :disabled="disabled"
            >
                Publish
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
                        label="Prequalification No"
                        prop="preqDocumentNo"
                        show-overflow-tooltip
                        sortable
                        width="200"
                    ></el-table-column>
                    <el-table-column
                        label="Title"
                        prop="prequalificationName"
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
            <announcement-form
                v-else-if="page === 3"
                ref="announcementForm"
                :new-record="newRecord"
                :schedule-id="selectedRow.prequalificationScheduleId"
                :is-prequalification="true"
                @readOnly="readOnly"
            ></announcement-form>
        </div>
    </div>
</template>

<script lang="ts" src="./preq-announcement-grid.component.ts">
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
