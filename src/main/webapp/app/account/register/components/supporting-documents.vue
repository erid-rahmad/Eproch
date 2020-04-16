<template>
    <div>
        <h4>{{ $t('register.document.mandatory.title') }}</h4>
        <p>{{ $t('register.document.mandatory.description') }}</p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    circle
                    v-if="shouldFillMandatoryDocuments"
                    @click.native.prevent="addDocument('mainDocuments')"
                />
            </div>
            <p v-if="hasErrors" :text="errors.mainDocuments.message" class="error">All mandatory documents must be uploaded</p>
            <el-table
                ref="mainDocuments"
                max-height="250"
                style="width: 100%"
                :data="mainDocuments"
            >
                <el-table-column
                    prop="typeName"
                    label="Document Type"
                />
                <el-table-column
                    prop="documentNo"
                    label="Document No."
                />
                <el-table-column
                    prop="expirationDate | formatDate"
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
                max-height="250"
                style="width: 100%"
                :data="additionalDocuments"
            >
                <el-table-column
                    prop="typeName"
                    label="Document Type"
                />
                <el-table-column
                    prop="documentNo"
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
        >
            <el-row :gutter="16">
                <el-col :span="18" :offset="3">
                    <supporting-documents-update
                        ref="dialogBody"
                        :event-bus="eventBus"
                        :mandatory="editingForm === 'mainDocuments'"
                    />
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="hideDialog">Cancel</el-button>
                <el-button :loading="loading" type="primary" @click="saveDocument">Save</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./supporting-documents.component.ts"></script>

<style lang="scss" scoped>
.error {
    background: none;
    color: #ff4949;
    font-size: 12px;
    line-height: 1;
}
</style>
