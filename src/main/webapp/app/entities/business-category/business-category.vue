<template>
    <div class="app-container">
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.businessCategory.home.title')" id="business-category-heading">Business Categories</span>
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
                    :title="$t('opusWebApp.businessCategory.home.createLabel')"
                    @click="add()"
                />
            </div>
            <el-table
                ref="mainTable"
                highlight-current-row
                max-height="512px"
                style="width: 100%"
                :data="businessCategories"
                :default-sort="{prop: 'name', order: 'ascending'}"
                :empty-text="$t('opusWebApp.businessCategory.home.notFound')"
                @current-change="changeClassificationSelection"
                @sort-change="changeOrder"
            >
                <el-table-column
                    prop="name"
                    sortable
                    :label="$t('opusWebApp.businessCategory.name')"
                />
                <el-table-column
                    prop="description"
                    sortable
                    :label="$t('opusWebApp.businessCategory.description')"
                />
                <el-table-column
                    prop="parentCategoryName"
                    sortable
                    :label="$t('opusWebApp.businessCategory.parentCategory')"
                />
                <el-table-column
                    label="Operations"
                    width="200"
                >
                    <template slot-scope="scope">
                        <el-button
                            @click="openDetails(scope.row)"
                            type="text"
                            size="mini"
                            icon="el-icon-info"
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
            <el-tabs type="card">
                <el-tab-pane>
                    <span slot="label"><i class="el-icon-suitcase"></i> {{ $t('opusWebApp.businessCategory.home.categoryTitle') }}</span>
                    Business Categories
                </el-tab-pane>
                <el-tab-pane>
                    <span slot="label"><i class="el-icon-suitcase-1"></i> {{ $t('opusWebApp.businessCategory.home.subCategoryTitle') }}</span>
                    Business Sub-categories
                </el-tab-pane>
            </el-tabs>
        </div>
        <el-dialog
            width="30%"
            :title="$t('entity.delete.title')"
            :visible="showDeleteDialog"
        >
            <span>{{ $t('opusWebApp.businessCategory.delete.question', {'id': removeId}) }}</span>
            <div slot="footer">
                <el-button @click="closeDialog()">{{ $t('entity.action.cancel') }}</el-button>
                <el-button type="danger" @click="removeBusinessCategory()">{{ $t('entity.action.delete') }}</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./business-category.component.ts">
</script>
