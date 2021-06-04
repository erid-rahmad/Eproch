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
              @click="viewNegoDetail"
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
          </el-form-item>
          <el-form-item label="">
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
      <el-table border :data="chatHistory" size="mini">
        <el-table-column width="100" label="No">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column label="From" width="200">
          <template slot-scope="{row}">
            <div v-if="row.vendorText">Vendor</div>
            <div v-else>Buyer</div>
          </template>
        </el-table-column>
        <el-table-column label="Content" width="750">
          <template slot-scope="{row}">
            <div v-if="row.vendorText">{{row.vendorText}}</div>
            <div v-else>{{row.buyerText}}</div>
          </template>
        </el-table-column>
        <el-table-column label="Attachment" width="400">
          <template slot-scope="{row}">
            <el-button
              class="btn-attachment"
              icon="el-icon-download"
              size="mini"
              type="primary"
              v-if="row.attachmentId"
            >
              Download
            </el-button>
          </template>
        </el-table-column>
      </el-table>
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
                v-model="line.attachment"
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
</style>
