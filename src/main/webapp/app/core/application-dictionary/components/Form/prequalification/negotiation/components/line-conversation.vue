<template>
  <div class="bidding-negotiation-line-conversation">
    <el-form
      ref="line"
      label-position="left"
      label-width="200px"
      :model="line"
      size="mini"
    >
      <el-row
        :gutter="24"
        style="margin-top: 16px"
      >
        <el-col
          :xs="24"
          :sm="12"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Vendor">
            <el-input
              v-model="line.vendorName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="" v-if="chatHistory.length">
            <el-button
              size="mini"
              @click="viewEvalDetail"
            >
              View Detail
            </el-button>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="12"
          :lg="12"
          :xl="8"
        >
          <el-form-item>
            &nbsp;
          </el-form-item>
          <el-form-item label="" v-if="(line.negotiationStatus!='agreed'&&line.negotiationStatus!='disagreed')">
            <el-button
              size="mini"
              @click="openChatForm"
            >
              Add New
            </el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row
        :gutter="24"
        style="margin-top: 16px"
      >
      
      <el-skeleton :loading="loading" animated :count="3">
        <template slot="template">
          <div class="card" style="border: solid; border-width: thin;">
            <el-skeleton-item variant="h3" style="width: 30%"/>
            <el-skeleton-item variant="text"/>
            <el-skeleton-item variant="text" style="width: 65%"/>
          </div>
        </template>
        <template>
          <div class="form-wrapper card-view app-container" v-if="chatHistory.length" style="background:#FFFFFF">
            <div class="card" v-for="(c,index) in chatHistory" :key="index" style="border: solid; border-width: thin;">
              <h4>{{c.vendorText?line.vendorName:"Buyer"}}
                <el-button
                  class="btn-attachment"
                  icon="el-icon-download"
                  size="mini"
                  type="primary"
                  v-if="c.attachmentId"
                  @click="downloadAttachment(c)"
                >
                </el-button>
              </h4>
              <p>{{c.vendorText?c.vendorText:c.buyerText}}</p>
            </div>
          </div>
          <div class="form-wrapper" v-else style="text-align: center;">
            <p>Tidak ada percakapan</p>
          </div>
        </template>
      </el-skeleton>
      </el-row>
    </el-form>

    <el-dialog :visible.sync="showDetail" title="Method Detail" width="50%">
      <el-form label-position="left" label-width="128px" :loading="loadingDetails" 
      :before-close="closeEvalDetail">
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
                      <el-button v-if="!subCriteria.attachmentName && isVendor" size="mini" type="primary"
                        @click="openAttachmentForm(subCriteria)">
                          <svg-icon name="icomoo/206-attachment">
                          </svg-icon>
                          Attachment
                      </el-button>
                      <el-button v-if="subCriteria.attachmentName" icon="el-icon-view" size="mini" type="primary"
                        @click="handleDownload(subCriteria.attachmentUrl)">{{ subCriteria.attachmentName }}
                      </el-button>
                      <el-button v-if="subCriteria.attachmentName && isVendor" icon="el-icon-close" size="mini" type="primary"
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
                          <el-input v-model="row.answer" disabled size="mini"></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column label="Evaluate" min-width="150">
                        <template slot-scope="{ row }">
                          <el-select v-model="row.passFail" placeholder="Pass Fail">
                            <el-option v-for="item in passfail" :key="item.value" :label="item.label" :value="item.value"/>
                          </el-select>
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
      <div slot="footer">
        <el-button
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          @click="closeEvalDetail"
        >
          <svg-icon name="icomoo/273-checkmark"></svg-icon> Save
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="80%"
      :visible.sync="showChatForm"
      :before-close="clearForm"
    >
      <el-form
        ref="chatForm"
        :rules="chatFormValidationSchema"
        label-position="left"
        label-width="96px"
        :model="chatForm"
        size="mini"
      >
        <el-row
          :gutter="24"
          style="margin-top: 16px"
        >
          <el-col
            :xs="24"
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Bidding Title">
              <el-input
                v-model="line.biddingTitle"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Attachment">
              <el-upload
                ref="contractFile"
                v-model="chatForm.attachment"
                :action="action"
                class="upload-demo"
                :limit="limit"
                :multiple="false"
                :accept="accept"
                :file-list="fileList"
                :before-upload="handleBeforeUpload"
                :on-change="onUploadChange"
                :on-preview="handlePreview"
                :on-exceed="handleExceed"
                :on-remove="handleRemove"
                :on-error="onUploadError"
                :on-success="onUploadSuccess"
              >
                <el-button
                  size="mini"
                  type="primary"
                >
                  Select Document
                </el-button>
                <div
                  slot="tip"
                  class="el-upload__tip"
                >
                  pdf/jpg/png files with a size less than 2mb
                </div>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="text">
          <el-input
            v-model="chatForm.text"
            :rows="7"
            type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item prop="publishCheck">
          <el-checkbox
            v-model="chatForm.publishToEmail"
          > Publish to Email?</el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="clearForm"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          :loading="submitting"
          size="mini"
          type="primary"
          @click="submitForm"
        >
          Submit
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./line-conversation.component.ts"></script>

<style lang="scss">
.compact {
  .bidding-negotiation-line-conversation {
    .el-table--mini {
      th, td {
        height: 35px;
      }
    }
  }
}

.bidding-negotiation-line-conversation {
  .form-wrapper {
    .el-scrollbar__wrap {
      overflow-x: hidden;
      padding: 15px;
    }
  }
}

p {
  font-size: 14px;
}
</style>
