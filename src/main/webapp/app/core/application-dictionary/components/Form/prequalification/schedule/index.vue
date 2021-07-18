<template>
    <div class="app-container card-view prequalification-schedule">
        <div class="toolbar">
            <el-button v-if="!index" icon="el-icon-close" size="mini" type="danger" @click="onFormClosed">
                Close
            </el-button>
        </div>
        <div v-if="index" class="card">
            <el-table
                ref="mainGrid"
                v-loading="processing"
                :data="gridData"
                :default-sort="gridSchema.defaultSort"
                :empty-text="gridSchema.emptyText"
                border
                highlight-current-row
                size="mini"
                stripe
                @current-change="onCurrentRowChanged"
                @sort-change="changeOrder"
                @selection-change="onSelectionChanged"
            >
                <el-table-column align="center" label="No." fixed width="48">
                    <template slot-scope="{ $index }">
                        {{ $index + 1 }}
                    </template>
                </el-table-column>

                <el-table-column align="center" fixed="right" width="200">
                    <template slot-scope="{ row }">
                        <el-button :underline="false" icon="el-icon-search" size="mini" title="View" type="primary"
                                   @click="viewPreq(row)">
                            Action
                        </el-button>
                    </template>
                </el-table-column>

                <el-table-column label="Prequalification No." min-width="150" prop="documentNo" sortable></el-table-column>

                <el-table-column label="Title" min-width="140" prop="name" show-overflow-tooltip
                                 sortable></el-table-column>

                <el-table-column label="Prequalification Event" min-width="130" prop="preqEventName" show-overflow-tooltip
                                 sortable></el-table-column>

                <el-table-column label="Prequalification Method" min-width="130" prop="preqMethodName" show-overflow-tooltip
                                 sortable></el-table-column>
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
        </div>

        <prequalification-schedule
            v-else
            ref="schedule"
            :data="selectedRow"
            class="card"
        ></prequalification-schedule>
    </div>
</template>

<script lang="ts" src="./index.component.ts"></script>

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

.toolbar {
    padding: 4px 16px;
}

.form-input {
    width: 100%;
}
</style>
