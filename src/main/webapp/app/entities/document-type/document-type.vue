<template>
    <div class="app-container">
        <h2 id="page-heading">
            <span v-text="$t('opusWebApp.documentType.home.title')" id="document-type-heading">Document Types</span>
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
                    :title="$t('opusWebApp.documentType.home.createLabel')"
                    @click="add()"
                />
            </div>
            <el-table
                ref="mainTable"
                max-height="512px"
                style="width: 100%"
                @sort-change="changeOrder"
                :data="documentTypes"
                :default-sort="{prop: 'name', order: 'ascending'}"
                :empty-text="$t('opusWebApp.documentType.home.notFound')"
            >
                <el-table-column
                    fixed
                    prop="name"
                    sortable
                    :label="$t('opusWebApp.documentType.name')"
                />
                <el-table-column
                    prop="description"
                    sortable
                    :label="$t('opusWebApp.documentType.description')"
                />
                <el-table-column
                    prop="hasExpirationDate"
                    :label="$t('opusWebApp.documentType.hasExpirationDate')"
                />
                <el-table-column
                    prop="mandatoryBusinessCategories"
                    :label="$t('opusWebApp.documentType.mandatoryBusinessCategories')"
                />
                <el-table-column
                    prop="additionalBusinessCategories"
                    :label="$t('opusWebApp.documentType.additionalBusinessCategories')"
                />
                <el-table-column
                    prop="mandatoryForCompany"
                    :label="$t('opusWebApp.documentType.mandatoryForCompany')"
                />
                <el-table-column
                    prop="mandatoryForProfessional"
                    :label="$t('opusWebApp.documentType.mandatoryForProfessional')"
                />
                <el-table-column
                    prop="additionalForCompany"
                    :label="$t('opusWebApp.documentType.additionalForCompany')"
                />
                <el-table-column
                    prop="mandatoryForProfessional"
                    :label="$t('opusWebApp.documentType.mandatoryForProfessional')"
                />
                <el-table-column
                    fixed="right"
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
        </div>
        <el-dialog
            width="30%"
            :title="$t('entity.delete.title')"
            :visible="showDeleteDialog"
        >
            <span>{{ $t('opusWebApp.documentType.delete.question', {'id': removeId}) }}</span>
            <div slot="footer">
                <el-button @click="closeDialog()">{{ $t('entity.action.cancel') }}</el-button>
                <el-button type="danger" @click="removeDocumentType()">{{ $t('entity.action.delete') }}</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./document-type.component.ts">
</script>
