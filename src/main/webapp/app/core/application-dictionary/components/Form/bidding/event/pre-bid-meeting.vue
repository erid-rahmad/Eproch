<template>
  <div class="app-container">
    <el-row type="flex" justify="center">
      <el-col :span="6">
        {{ biddingSchedule.eventTypeLineName }}
      </el-col>
    </el-row>
    <el-row type="flex" justify="center">
      <el-col :span="6">
        {{ biddingSchedule.biddingNo }}
      </el-col>
    </el-row>
    <el-row type="flex" justify="center">
      <el-col :span="6">
        {{ biddingSchedule.biddingTitle }}
      </el-col>
    </el-row>
    <el-row type="flex" justify="center">
      <el-col :span="6">
        <el-upload
          ref="docUpload"
          v-model="attachment"
          :accept="uploadAccept"
          :action="uploadEndpoint"
          auto-upload
          :headers="uploadHeaders"
          :limit="1"
          :on-error="onUploadError"
          :on-remove="onUploadRemove"
          :on-success="onUploadSuccess"
        >
          <el-button
            icon="el-icon-search"
            size="mini"
            slot="trigger"
            type="primary"
          >
            Select File
          </el-button>
          <span
            class="el-upload__tip"
            style="margin-left: 10px"
            slot="tip"
          >
            Files with a size less than 5Mb
          </span>
        </el-upload>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./pre-bid-meeting.component.ts"></script>