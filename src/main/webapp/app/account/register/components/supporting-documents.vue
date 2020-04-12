<template>
    <div>
        <h4>{{ $t('register.document.mandatory.title') }}</h4>
        <p>{{ $t('register.document.mandatory.description') }}</p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button type="primary" icon="el-icon-plus" circle @click.native.prevent="addDocument('mainDocuments')" />
            </div>
            <el-table
                ref="mainDocuments"
                :data="mainDocuments"
                height="250"
                style="width: 100%">
                <el-table-column
                    prop="docType"
                    label="Document Type"
                />
                <el-table-column
                    prop="docNo"
                    label="Document No."
                />
                <el-table-column
                    prop="expirationDate"
                    label="Expiration Date"
                />
                <el-table-column
                    prop="file"
                    label="File"
                />
            </el-table>
        </div>
        <h4>{{ $t('register.document.additional.title') }}</h4>
        <p>{{ $t('register.document.additional.description') }}</p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button type="primary" icon="el-icon-plus" circle @click.native.prevent="addDocument('additionalDocuments')" />
            </div>
            <el-table
                ref="additionalDocuments"
                :data="additionalDocuments"
                height="250"
                style="width: 100%">
                <el-table-column
                    prop="docType"
                    label="Document Type"
                />
                <el-table-column
                    prop="docNo"
                    label="Document No."
                />
                <el-table-column
                    prop="expirationDate"
                    label="Expiration Date"
                />
                <el-table-column
                    prop="file"
                    label="File"
                />
            </el-table>
        </div>
        <el-dialog
            :title="$t('register.form.document[\'title.edit\']')"
            :visible.sync="editDialogVisible"
            @opened="handleDialogOpen"
        >
            <el-row :gutter="16">
                <el-col :span="18" :offset="3">
                    <supporting-documents-form
                        :event-bus="eventBus"
                        :document="document"
                        :mandatory="editingForm === 'mainDocuments'"
                    />
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">Cancel</el-button>
                <el-button :loading="loading" type="primary" @click="saveDocument">Save</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./supporting-documents.component.ts"></script>
