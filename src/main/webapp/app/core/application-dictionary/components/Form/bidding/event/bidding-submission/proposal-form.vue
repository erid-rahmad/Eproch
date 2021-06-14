<template>
    <div class="proposal-form">
        <h3 style="margin-top: 0">{{ data.evaluation }} Proposal</h3>
        <el-form
            ref="mainForm"
            label-position="left"
            label-width="200px"
            size="mini"
        >
            <el-row
                v-for="(criteria, index) in evaluationMethodCriteria"
                :key="criteria.id"
                :class="`criteria-${index}`"
                class="criteria-section"
                v-loading="loading"
            >
                <el-col :span="24">
                    <el-row>
                        <el-col :span="8">
                            <el-form-item class="criteria-label" label="Criteria">
                                {{ criteria.biddingCriteriaName }}
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row
                        v-for="(subCriteria, subIndex) in criteria.evalMethodSubCriteriaList"
                        :key="subCriteria.id"
                        :class="`sub-${subIndex}`"
                        class="sub-criteria-section"
                    >
                        <el-col :span="24">
                            <el-row>
                                <el-col
                                    :md="12"
                                    :sm="24"
                                    :xl="8"
                                    :xs="24"
                                >
                                    <el-form-item label="Sub Criteria">
                                        {{ subCriteria.biddingSubCriteriaName }}
                                    </el-form-item>
                                </el-col>

                            </el-row>
                            <el-row
                                v-for="(biddingSubCriteria, subsubindex) in subCriteria.biddingSubCriteriaDTO"
                                :key="biddingSubCriteria.id"
                                :class="`sub-${subsubindex}`"
                                class="sub-sub-criteria-section"
                            >

                                <el-col style="text-align: right;padding-right: 40px;padding-bottom: 5px">

                                    <el-button v-if="!biddingSubCriteria.attachmentName && isVendor" :disabled="disabled" size="mini" type="primary"
                                               @click="OpenAttachmentForm(biddingSubCriteria)">
                                        <svg-icon name="icomoo/206-attachment">
                                        </svg-icon>
                                        Attachment
                                    </el-button>
                                    <el-button v-if="biddingSubCriteria.attachmentName" icon="el-icon-view" size="mini" type="primary"
                                               @click="handlePreview">{{ biddingSubCriteria.attachmentName }}
                                    </el-button>
                                    <el-button v-if="biddingSubCriteria.attachmentName && isVendor" :disabled="disabled" icon="el-icon-close" size="mini" type="primary"
                                               @click="cancelAttachment(biddingSubCriteria)">
                                    </el-button>
                                </el-col>
                                <el-table
                                    :data="biddingSubCriteria.criteriaLineDTO"
                                    border
                                    class="question-list"
                                    highlight-current-row
                                    size="mini"
                                >
                                    <el-table-column
                                        label="No."
                                        width="50"
                                    >
                                        <template slot-scope="{ $index }">
                                            {{ $index + 1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                        label="Question"
                                        min-width="320"
                                        prop="name"
                                        show-overflow-tooltip
                                        sortable
                                    ></el-table-column>
                                    <el-table-column
                                        label="Requirement"
                                        prop="requirement"
                                        width="150"
                                    ></el-table-column>

                                    <el-table-column
                                        label="Answer"
                                        width="320"
                                    >
                                        <template slot-scope="{ row }">
                                            <el-input
                                                v-model="row.answer"
                                                :disabled="disabled"
                                                :readonly="!isVendor"
                                                size="mini"
                                            ></el-input>
                                        </template>
                                    </el-table-column>

                                    <el-table-column
                                        v-if="!isVendor"
                                        label="Document"
                                        width="100"
                                    >
                                        <template slot-scope="{ row }">
                                            <el-checkbox
                                                v-model="row.documentEvaluation"
                                                size="mini"
                                            ></el-checkbox>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>
        <el-dialog :show-close="false" :visible.sync="attachmentFormVisible" title="Add Attachment">
            <el-upload
                ref="docUpload"
                :accept="accept"
                :action="action"
                :before-upload="handleBeforeUpload"
                :headers="uploadHeaders"
                :on-error="onUploadError"
                :on-exceed="handleExceed"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="onUploadSuccess"
                :file-list="fileList"
                auto-upload
            >
                <el-button slot="trigger" icon="el-icon-search" type="primary">
                     SelectFile
                </el-button>
                <span slot="tip" class="el-upload__tip" style="margin-left: 10px">
          Files with a size less than 5Mb
        </span>
            </el-upload>

            <div slot="footer">
                <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;"
                           @click="attachmentFormVisible = false">
                    {{ $t('entity.action.cancel') }}
                </el-button>
                <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary"
                           @click="saveAttachment">
                    {{ $t('entity.action.save') }}
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./proposal-form.component.ts"></script>
<style lang="scss">
.proposal-form {
    .criteria-label {
        font-weight: 700;
    }

    .sub-criteria-section {
        label.el-form-item__label {
            font-weight: 600;
        }
    }
}

.compact .proposal-form {
    .el-table--mini {
        td,
        th {
            height: 35px;
        }
    }
}
</style>
<style lang="scss" scoped>
.proposal-form {
    .criteria-section {
        &:not(.criteria-0),
        > .el-col > .sub-criteria-section:not(.sub-0) {
            margin-top: 10px;
        }
    }
}
</style>
