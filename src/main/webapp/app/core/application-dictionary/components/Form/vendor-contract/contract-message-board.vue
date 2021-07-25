<template>
  <div class="contract-message-board">
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
          <el-form-item label="Contract">
            <el-input
              v-model="data.name"
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
          <el-form-item label="Vendor">
            <el-input
              v-model="data.vendorName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="">
            <el-button
              size="mini"
              @click="openChatForm"
            >
              New Post
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

<script lang="ts" src="./contract-message-board.component.ts"></script>

<style lang="scss">
.compact {
  .contract-message-board {
    .el-table--mini {
      th, td {
        height: 35px;
      }
    }
  }
}

.contract-message-board {
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
