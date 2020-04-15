<template>
    <div class="app-container">
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.currency.home.title')" id="business-category-heading">Currency</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    circle
                    :title="$t('opusWebApp.currency.home.createLabel')"
                    @click="add()"
                />
            </div>
            <el-table
                ref="mainTable"
                highlight-current-row
                max-height="512px"
                style="width: 100%"
                @sort-change="changeOrder"
                :data="currencies"
                :default-sort="{prop: 'name', order: 'ascending'}"
                :empty-text="$t('opusWebApp.currency.home.notFound')"
                @current-change="changeClassificationSelection"
            >
                <el-table-column
                    prop="code"
                    sortable
                    :label="$t('opusWebApp.currency.code')"
                />
                <el-table-column
                    prop="symbol"
                    sortable
                    :label="$t('opusWebApp.currency.symbol')"
                />
                <el-table-column
                    prop="name"
                    sortable
                    :label="$t('opusWebApp.currency.name')"
                />
                <el-table-column
                    label="Operations"
                    width="200"
                >
                    <template slot-scope="scope">
                        <el-button
                            @click="openDetails(scope.row)"
                            type="warning"
                            size="mini"
                            icon="el-icon-info"
                            plain
                            :title="$t('entity.action.view')"
                        />
                        <el-button
                            @click="edit(scope.row)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            @click="prepareRemove(scope.row)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"
                        />
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="loadPage"
                :current-page.sync="page"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="itemsPerPage"
                layout="total, sizes, prev, pager, next, jumper"
                :total="queryCount">
            </el-pagination>
        </div>
        <el-dialog
            width="30%"
            :title="$t('entity.delete.title')"
            :visible="showDeleteDialog"
        >
            <span>{{ $t('opusWebApp.currency.delete.question', {'id': removeId}) }}</span>
            <div slot="footer">
                <el-button icon="el-icon-close" @click="closeDialog()">{{ $t('entity.action.cancel') }}</el-button>
                <el-button icon="el-icon-delete" type="danger" @click="removeCurrency()">{{ $t('entity.action.delete') }}</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./currency.component.ts">
</script>
