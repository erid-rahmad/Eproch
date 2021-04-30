<template>
  <div class="app-container">
    <el-form
      v-loading="loading"
      ref="mainForm"
      label-position="left"
      label-width="150px"
      :model="mainForm"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Event Name">
            <el-input
              v-model="mainForm.eventTypeLineName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Bidding No">
            <el-input
              v-model="mainForm.biddingNo"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Bidding Title">
            <el-input
              v-model="mainForm.biddingTitle"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Start Date">
            <el-date-picker
              v-model="mainForm.startDate"
              disabled
              :format="dateDisplayFormat"
              size="mini"
              type="datetime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="End Date">
            <el-date-picker
              v-model="mainForm.endDate"
              disabled
              :format="dateDisplayFormat"
              size="mini"
              type="datetime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="">
            <el-button
              type="primary"
              @click="viewParticipants"
            >
              <svg-icon name="icomoo/115-users"></svg-icon> Participants
            </el-button>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="">
            <el-upload
              v-loading="loadingAttachments"
              ref="docUpload"
              :accept="uploadAccept"
              :action="uploadEndpoint"
              class="attachment"
              drag
              :before-remove="beforeFileRemove"
              :file-list="uploadedFiles"
              :headers="uploadHeaders"
              :on-error="onUploadError"
              :on-preview="onFilePreview"
              :on-remove="onFileRemove"
              :on-success="onUploadSuccess"
              multiple
            >
              <em class="el-icon-upload"></em>
              <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./pre-bid-meeting.component.ts"></script>
<style lang="scss" scoped>
.app-container {
  background: #f0f2f5;

  .el-form {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin: 10px;
    padding: 24px;
  }
}
</style>