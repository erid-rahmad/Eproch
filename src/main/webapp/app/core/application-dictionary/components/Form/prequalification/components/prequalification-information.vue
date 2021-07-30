<template>
  <div class="prequalification-information">
    <el-form
      ref="prequalificationInformation"
      :disabled="readOnly"
      label-position="left"
      label-width="150px"
      :model="preq"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col :xs="24" :sm="12" :lg="8">
          <el-form-item label="Prequalification No" prop="documentNo">
            <el-input v-model="preq.documentNo" class="form-input" clearable :disabled="editMode"></el-input>
          </el-form-item>
          <el-form-item label="Title" prop="name" required>
            <el-input v-model="preq.name" class="form-input" clearable :disabled="editMode"></el-input>
          </el-form-item>
          <el-form-item label="Reference No" prop="referenceNo" required>
            <el-input
              ref="requisitionNo"
              v-model="preq.referenceNo"
              v-loading="loadingReferenceNo"
              clearable
              :disabled="editMode"
              placeholder="Please Enter Reference No"
              @change="retrieveReferencedData"
            >
              <el-button
                v-if="!editMode"
                :loading="loadingReferenceNo"
                icon="el-icon-search"
                slot="append"
                @click="retrieveReferencedData(preq.referenceNo)"
              >
                Search
              </el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="Prequalification Type" prop="type" required>
            <el-select
              v-model="preq.type"
              class="form-input"
              clearable
              filterable
              placeholder="Select Prequalification Type"
              style="width: 100%"
            >
              <el-option v-for="item in biddingTypeOptions" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-form-item label="Announcement Text" prop="announcementText" required>
          <el-input v-model="preq.announcementText" type="textarea" :autosize="{ minRows: 6}"></el-input>
        </el-form-item>
        <el-form-item label="Attachment" prop="attachment" required>
            <el-upload
              ref="docUpload"
              v-model="preq.attachment"
              :accept="accept"
              :action="action"
              auto-upload
              :headers="projectDocUploadHeaders"
              :limit="limit"
              :before-upload="handleBeforeUpload"
              :on-preview="handlePreview"
              :on-exceed="handleExceed"
              :on-remove="handleRemove"
              :on-error="onUploadError"
              :on-success="onUploadSuccess"
              :file-list="fileList"
            >
              <el-button icon="el-icon-search" slot="trigger" type="primary">
                Select File
              </el-button>
              <span class="el-upload__tip" style="margin-left: 10px" slot="tip">
                Files with a size less than 5Mb
              </span>
            </el-upload>
          </el-form-item>
      </el-row>
    </el-form>
  </div>
</template>

<script lang="ts" src="./prequalification-information.component.ts"></script>

<style lang="scss">
.compact .prequalification-information .el-table--mini {
  th,
  td {
    height: 35px;
  }
}
</style>

<style lang="scss" scoped>
.btn-attachment {
  width: 100%;
}
</style>
