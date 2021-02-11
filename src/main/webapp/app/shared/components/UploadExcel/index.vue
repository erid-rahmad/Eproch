<template>
  <div>
    <input
      ref="excel-upload-input"
      class="excel-upload-input"
      type="file"
      accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, .xlsx, .xls, .csv"
      @change="handleClick"
    >
    <div
      class="drop"
      @drop="handleDrop"
      @dragover="handleDragover"
      @dragenter="handleDragover"
    >
      Drop excel file here or
      <el-button
        :loading="loading"
        style="margin-left:16px;"
        size="mini"
        type="primary"
        @click="handleUpload"
      >
        Browse
      </el-button>
    </div>
  </div>
</template>

<script lang="ts" src="./index.component.ts"></script>

<style lang="scss" scoped>
.excel-upload-input {
  display: none;
  z-index: -9999;
}

.drop {
  border: 2px dashed #bbb;
  line-height: 96px;
  margin: 0 auto;
  font-size: 24px;
  border-radius: 5px;
  text-align: center;
  color: #bbb;
  position: relative;
}
</style>
