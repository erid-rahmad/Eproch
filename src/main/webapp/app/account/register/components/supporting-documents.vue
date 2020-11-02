<template>
    <div>
        <el-divider content-position="left"><h4>{{ $t('register.document.mandatory.title') }}</h4></el-divider>
        <p>
            <el-alert
                :title="$t('register.document.mandatory.description')"
                :type="errors.type"
                :closable="false"
                show-icon>
            </el-alert>
        </p>
        <div class="table-container">
            <!--
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    v-if="shouldFillMandatoryDocuments"
                    @click.native.prevent="addDocument('mainDocuments')">
                    {{ $t('entity.action.add') }}
                </el-button>
            </div>
            -->
            <!--<p v-if="hasErrors" :text="errors.mainDocuments.message" class="error">All mandatory documents must be uploaded</p>-->
            
            <el-table
                ref="mainDocuments"
                max-height="250"
                style="width: 100%"
                :rules="rules"
                :data="mainDocuments"
            >
                <el-table-column
                    prop="typeName"
                    :label="$t('register.document.form.documentType')"
                />
                <el-table-column
                    prop="documentNo"
                    :label="$t('register.document.form.documentNo')"
                    show-overflow-tooltip
                />
                <el-table-column
                    prop="expirationDate"
                    :label="$t('register.document.form.expirationDate')"
                />
                <el-table-column
                    prop="file.name"
                    :label="$t('register.document.form.file')"
                    show-overflow-tooltip
                >
                    <template scope="props">
                        <el-link 
                            target="_blank"
                            v-if="props.row.file" 
                            :href="'' + props.row.file.response.downloadUri+''" 
                            :underline="false">
                            {{ props.row.file.response.attachment.fileName }}
                        </el-link>
                    </template>
                </el-table-column>
                <el-table-column
                    fixed="right"
                    align="center"
                    :label="$t('register.form.operation')"
                    width="128">
                    <template slot-scope="scope">
                        <el-button
                            @click="edit(scope.row, 'mainDocuments', scope.$index)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"/>
                        <!--
                        <el-button
                            @click="prepareRemove('mainDocuments', scope.$index)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"/>
                            -->
                    </template>
                </el-table-column>
            </el-table>
            
        </div>
        <el-divider content-position="left"><h4>{{ $t('register.document.additional.title') }}</h4></el-divider>
        <p>
            <el-alert
                :title="$t('register.document.additional.description')"
                type="info"
                :closable="false"
                show-icon>
            </el-alert>
        </p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button 
                    type="primary" 
                    icon="el-icon-plus" 
                    @click.native.prevent="addDocument('additionalDocuments')">
                    {{ $t('entity.action.add') }}
                </el-button>
            </div>
            <el-table
                ref="additionalDocuments"
                max-height="250"
                style="width: 100%"
                :data="additionalDocuments"
            >
                <el-table-column
                    prop="typeName"
                    :label="$t('register.document.form.documentType')"
                />
                <el-table-column
                    prop="documentNo"
                    :label="$t('register.document.form.documentNo')"
                    show-overflow-tooltip
                />
                <el-table-column
                    prop="expirationDate"
                    :label="$t('register.document.form.expirationDate')"
                />
                <el-table-column
                    prop="file.name"
                    :label="$t('register.document.form.file')"
                    show-overflow-tooltip
                >
                    <template scope="props">
                        <el-link 
                            target="_blank"
                            v-if="props.row.file" 
                            :href="'' + props.row.file.response.downloadUri+''" 
                            :underline="false">
                            {{ props.row.file.response.attachment.fileName }}
                        </el-link>
                    </template>
                </el-table-column>
                <el-table-column
                    fixed="right"
                    align="center"
                    :label="$t('register.form.operation')"
                    width="128">
                    <template slot-scope="scope">
                        <el-button
                            @click="edit(scope.row, 'additionalDocuments', scope.$index)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            @click="prepareRemove('additionalDocuments', scope.$index)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"
                        />
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
            :title="$t('register.form.document[\'title.edit\']')"
            :visible.sync="editDialogVisible"
            @open="getLoopingReleatedDocumentType(editingForm === 'mainDocuments', 2)"
            @opened="getAllDocumentType(editingForm === 'mainDocuments', 2)"
            @closed="hideDialog">
            <el-row :gutter="16">
                <el-col :span="24" :offset="0">
                    <supporting-documents-update
                        ref="dialogBody"
                        :doc="doc"
                        :event-bus="eventBus"
                        :mandatory="editingForm === 'mainDocuments'"
                    />
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="hideDialog" icon="el-icon-close">{{ $t('entity.action.cancel') }}</el-button>
                <el-button :loading="loading" type="primary" @click="saveDocument" icon="el-icon-check">{{ $t('entity.action.save') }}</el-button>
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