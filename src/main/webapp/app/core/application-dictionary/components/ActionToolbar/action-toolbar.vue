<template>
  <div class="action-toolbar">
    <el-button-group>
      <el-button type="primary" icon="el-icon-plus" @click="onClickNew">New</el-button>
      <el-button type="primary" icon="el-icon-document-copy" @click="onClickCopy">Copy</el-button>
    </el-button-group>
    <el-button-group>
      <el-button type="primary" icon="el-icon-check" @click="onClickSave">Save</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="onClickDelete">Delete</el-button>
    </el-button-group>
    <el-button-group>
      <el-button type="primary" icon="el-icon-s-grid" @click="switchView" :plain="!gridView"/>
    </el-button-group>
  </div>
</template>
<script lang="ts" src="./action-toolbar.component.ts"></script>
<style lang="scss" scoped>
  .el-button-group + .el-button-group {
    padding-left: 8px;
  }
</style>
