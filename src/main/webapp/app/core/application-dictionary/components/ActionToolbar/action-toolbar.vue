<template>
  <div class="action-toolbar">
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-s-grid"
        title="Toggle Grid"
        v-shortkey="['alt', 'g']"
        @shortkey="switchView"
        @click="switchView"
        :plain="!gridView"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-arrow-up"
        title="Go to Parent Tab"
        :disabled="atWindowRoot"
        v-shortkey="['alt', 'up']"
        @shortkey="goToParentTab"
        @click="goToParentTab"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-arrow-down"
        title="Go to Child Tab"
        :disabled="atLastTab"
        v-shortkey="['alt', 'down']"
        @shortkey="goToChildTab"
        @click="goToChildTab"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-refresh"
        title="Refresh"
        v-shortkey="['alt', 'r']"
        @shortkey="refreshData"
        @click="refreshData"
      />
      <el-button 
        type="primary"
        size="small"
        icon="el-icon-search"
        title="Search"
        v-shortkey="['alt', 'f']"
        @shortkey="openSearchWindow"
        @click="openSearchWindow"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        title="New"
        v-shortkey="['alt', 'n']"
        @shortkey="addRecord"
        @click="addRecord"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-document-copy"
        title="Copy"
        v-shortkey="['alt', 'c']"
        @shortkey="copyRecord"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-check"
        title="Save"
        v-shortkey="['alt', 's']"
        @shortkey="saveRecord"
        @click="saveRecord"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-delete"
        title="Delete"
        v-shortkey="['alt', 'del']"
        @shortkey="deleteRecord"
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
