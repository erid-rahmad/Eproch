<template>
  <div class="submission-form">
    <h3 style="margin-top: 0">Technical Proposal</h3>
    <el-form ref="mainForm" label-position="left" label-width="200px" size="mini" v-loading="loading">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" :xl="8">
          <el-form-item label="Title" prop="name" required>
            <el-input v-model="data.prequalificationName" disabled class="form-input"></el-input>
          </el-form-item>
          <el-form-item label="Prequistion No" prop="documentNo">
            <el-input v-model="data.prequalificationNo" disabled class="form-input"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item class="criteria-label" label="Status">
        {{ proposalStatus }}
      </el-form-item>
      <el-row v-for="(line, index) in evaluationMethodCriteria" :key="line.id" :class="`line-${index}`" class="criteria-section">
        <el-row v-for="(criteria, index) in line.criteria" :key="criteria.id" :class="`criteria2-${index}`" class="criteria-section">
          <el-col :span="24">
            <el-form-item class="criteria-label" label="Criteria">
              {{ criteria.biddingCriteriaName }}
            </el-form-item>
            <el-row
              v-for="(subCriteria, subIndex) in criteria.subCriteria"
              :key="subCriteria.id"
              :class="`sub-${subIndex}`"
              class="sub-criteria-section"
            >
              <el-col :span="24">
                <el-row>
                  <el-col :span="12">
                    <div class="grid-content bg-purple">
                      <el-form-item label="Sub Criteria">
                        {{ subCriteria.biddingSubCriteriaName }}
                      </el-form-item>
                    </div>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col style="text-align: right;padding-right: 40px;padding-bottom: 5px">
                    <el-button v-if="!subCriteria.attachmentName && isVendor" :disabled="disabled" size="mini" type="primary"
                      @click="openAttachmentForm(subCriteria)">
                        <svg-icon name="icomoo/206-attachment">
                        </svg-icon>
                        Attachment
                    </el-button>
                    <el-button v-if="subCriteria.attachmentName" icon="el-icon-view" size="mini" type="primary"
                      @click="handleDownload(subCriteria.attachmentUrl)">{{ subCriteria.attachmentName }}
                    </el-button>
                    <el-button v-if="subCriteria.attachmentName && isVendor" :disabled="disabled" icon="el-icon-close" size="mini" type="primary"
                      @click="cancelAttachment(subCriteria)">
                    </el-button>
                  </el-col>
                  <el-table :data="subCriteria.questions" border class="question-list" highlight-current-row size="mini">
                    <el-table-column label="No." width="50">
                      <template slot-scope="{ $index }">
                        {{ $index + 1 }}
                      </template>
                    </el-table-column>
                    <el-table-column label="Question" min-width="320" prop="name" show-overflow-tooltip></el-table-column>
                    <el-table-column label="Requirement" prop="requirement" width="150"> </el-table-column>
                    <el-table-column label="Answer" width="320">
                      <template slot-scope="{ row }">
                        <el-input v-model="row.answer" :disabled="disabled" :readonly="!isVendor" size="mini"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column v-if="!isVendor" label="Document" width="100">
                      <template slot-scope="{ row }">
                        <el-checkbox :disabled="readOnlyChecklist" v-model="row.documentEvaluation" size="mini"></el-checkbox>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-divider />
                </el-row>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
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
        <el-button slot="trigger" icon="el-icon-search" type="primary"> Select File </el-button>
        <span slot="tip" class="el-upload__tip" style="margin-left: 10px"> Files with a size less than 5Mb </span>
      </el-upload>

      <div slot="footer">
        <el-button icon="el-icon-close" size="mini" style="margin-left: 0px" @click="attachmentFormVisible = false">
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button icon="el-icon-check" size="mini" style="margin-left: 0px" type="primary" @click="saveAttachment">
          {{ $t('entity.action.save') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./submission-form.component.ts"></script>
<style lang="scss">
.submission-form {
  .criteria-label {
    font-weight: 700;
  }

  .sub-criteria-section {
    label.el-form-item__label {
      font-weight: 600;
    }
  }
}

.compact .submission-form {
  .el-table--mini {
    td,
    th {
      height: 35px;
    }
  }
}
</style>
<style lang="scss" scoped>
.submission-form {
  .criteria-section {
    &:not(.criteria-0),
    > .el-col > .sub-criteria-section:not(.sub-0) {
      margin-top: 10px;
    }
  }
}
</style>
