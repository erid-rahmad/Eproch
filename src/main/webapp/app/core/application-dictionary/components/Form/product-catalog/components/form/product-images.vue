<template>
  <div>
    <el-upload
      v-model="productImages"
      :action="uploadUrl"
      :accept="accept"
      :before-upload="onBeforeUpload"
      :file-list="fileList"
      :limit="limit"
      list-type="picture-card"
      :headers="uploadHeaders"
      :on-preview="handlePreview"
      :on-exceed="handleExceed"
      :on-remove="handleRemove"
      :on-error="onUploadError"
      :on-success="onUploadSuccess"
    >
      <em slot="default" class="el-icon-plus"></em>
    </el-upload>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./product-images.component.ts">
</script>
