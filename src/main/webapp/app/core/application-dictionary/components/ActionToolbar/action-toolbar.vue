<template>
  <div class="action-toolbar" v-hotkey="keymap">
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-s-grid"
        title="Toggle Grid"
        @click="switchView"
        :plain="!gridView"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-arrow-up"
        title="Go to Parent Tab"
        :disabled="atWindowRoot"
        @click="goToParentTab"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-arrow-down"
        title="Go to Child Tab"
        :disabled="atLastTab"
        @click="goToChildTab"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-refresh"
        title="Refresh"
        @click="refreshData"
      />
      <el-button 
        type="primary"
        size="small"
        icon="el-icon-search"
        title="Search"
        @click="openSearchWindow"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        title="New"
        :disabled="isEditing"
        @click="addRecord"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-document-copy"
        title="Copy"
        :disabled="isEditing"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-check"
        title="Save"
        :disabled="!isEditing"
        @click="saveRecord"
      />
      <el-button
        type="danger"
        size="small"
        icon="el-icon-delete"
        title="Delete"
        :disabled="isEditing"
        @click="deleteRecord"
      />
    </el-button-group>
  </div>
</template>
<script lang="ts" src="./action-toolbar.component.ts"></script>
<style lang="scss" scoped>
  .el-button-group + .el-button-group {
    padding-left: 8px;
  }
</style>
