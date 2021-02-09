<template>
  <div class="app-container">
    <el-form
      ref="form"
      :model="catalog"
      label-width="128px"
    >
      <el-form-item
        label="JSON File"
        prop="file"
        required
      >
        <el-upload
          ref="uploader"
          v-model="catalog.file"
          action="/api/m-product-catalogs/marketplace/import"
          accept="application/json"
          :auto-upload="false"
          :headers="uploadHeaders"
          :limit="1"
          :before-upload="cacheFile"
          :on-error="onUploadError"
          :on-success="onUploadSuccess"
        >
          <el-button
            slot="trigger"
            icon="el-icon-search"
            size="mini"
            type="primary"
          >
            Select File
          </el-button>
          <el-button
            style="margin-left: 10px;"
            size="mini"
            type="success"
            @click="uploadFile"
          >
            Import
          </el-button>
          <span
            slot="tip"
            class="el-upload__tip"
            style="margin-left: 10px;"
          >
            Maximum file size is 5mb
          </span>
        </el-upload>
      </el-form-item>
    </el-form>
    <strong>Preview</strong>
    <ul>
      <li
        v-for="(item, i) in catalog.preview"
        :key="`${item.merchant.id}_${item.name}`"
      >
        {{ `${i + 1} - ${item.name}` }}
      </li>
    </ul>
  </div>
</template>
<script lang="ts" src="./bhinneka-catalog-importer.component.ts"></script>
