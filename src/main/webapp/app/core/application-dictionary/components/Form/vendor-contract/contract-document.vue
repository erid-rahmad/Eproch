<template>
  <div class="contract-document">
    <el-table
      v-loading="loading"
      ref="documents"
      highlight-current-row
      border
      stripe
      size="mini"
      :data="documents"
    >
      <el-table-column
        width="50" label="No">
        <template v-slot="{ $index }">
          {{ $index + 1 }}
        </template>
      </el-table-column>

      <el-table-column
        label="Document Name"
        min-width="200"
        prop="name"
        show-overflow-tooltip
      ></el-table-column>

      <el-table-column label="File" min-width="200">
        <template v-slot="{ row }">
          <el-button
            class="btn-attachment"
            icon="el-icon-download"
            size="mini"
            :title="row.attachmentName"
            type="primary"
            @click="downloadAttachment(row)"
          >
            {{ row.attachmentName }}
          </el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" fixed="right" width="56">
        <template slot="header">
          <el-button icon="el-icon-plus" size="mini" type="primary" @click="documentFormVisible = true"></el-button>
        </template>
        <template v-slot="{ row }">
          <el-button v-if="!readOnly" icon="el-icon-delete" size="mini" type="danger" @click="removeDocument(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :show-close="false"
      title="Upload Document"
      :visible.sync="documentFormVisible"
    >
      <template>
        <el-form
          ref="documentForm"
          :label-position="formSettings.labelPosition"
          :label-width="formSettings.labelWidth"
          :model="document"
          :size="formSettings.size"
        >
          <el-form-item label="Information" prop="name" required>
            <el-input v-model="document.name" class="form-input" clearable></el-input>
          </el-form-item>
          <el-form-item label="Attachment" prop="attachment" required>
            <el-upload
              ref="docUpload"
              v-model="document.attachment"
              :accept="accept"
              :action="uploadApi"
              auto-upload
              :headers="uploadHeaders"
              :limit="limit"
              :before-upload="handleBeforeUpload"
              :on-preview="handlePreview"
              :on-exceed="handleExceed"
              :on-remove="handleRemove"
              :on-error="onUploadError"
              :on-success="onUploadSuccess"
            >
              <el-button icon="el-icon-search" slot="trigger" type="primary">
                Select File
              </el-button>
              <span class="el-upload__tip" style="margin-left: 10px" slot="tip">
                Files with a size less than 5Mb
              </span>
            </el-upload>
          </el-form-item>
        </el-form>

        <div slot="footer">
          <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;" @click="documentFormVisible = false">
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary" @click="saveDocument">
            {{ $t('entity.action.save') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./contract-document.component.ts"></script>
<style lang="scss">
.contract-document {
  .btn-attachment {
    width: 100%;
  }
}
</style>
