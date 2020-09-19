<template>
  <div class="action-toolbar" v-hotkey="keymap">
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        title="New (alt + a)"
        :disabled="isEditing"
        @click="addRecord"
      />
      <el-button
        v-show="recordCount && !isEditing"
        type="primary"
        size="small"
        icon="el-icon-document-copy"
        title="Copy (alt + d)"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-check"
        title="Save (alt + s)"
        :disabled="!isEditing"
        @click="saveRecord"
      />
      <el-button
        size="small"
        icon="el-icon-close"
        title="Cancel (alt + z)"
        :disabled="!isEditing"
        @click="cancelOperation"
      />
      <el-button
        v-show="recordCount && !isEditing"
        type="danger"
        size="small"
        icon="el-icon-delete"
        title="Delete (alt + del)"
        @click="deleteRecord"
      />
    </el-button-group>
    <el-button-group v-show="!isEditing">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-refresh"
        title="Refresh (alt + r)"
        @click="refreshData"
      />
      <el-button 
        type="primary"
        size="small"
        icon="el-icon-search"
        title="Search (alt + f)"
        @click="openSearchWindow"
      />
      <el-button 
        type="primary"
        size="small"
        icon="el-icon-download"
        title="Export"
        @click="exportRecord"
      />
    </el-button-group>
    <el-dropdown
      v-if="buttons.length"
      size="small"
      @command="runProcess"
    >
      <el-button
        type="primary"
        size="small"
      >
        <font-awesome-icon :icon="['fas', 'bolt']"/>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item
          v-for="button in buttons"
          :key="button.id"
          :command="button.adTrigger"
          :title="button.tooltip"
        >
          {{ button.name }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-button-group>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-s-grid"
        title="Toggle Layout (alt + g)"
        @click="switchView"
        :plain="!gridView"
      />
      <el-button
        v-show="!isEditing"
        type="primary"
        size="small"
        icon="el-icon-arrow-up"
        title="Go to Parent Tab (alt + up)"
        :disabled="atWindowRoot"
        @click="goToParentTab"
      />
      <el-button
        v-show="!isEditing"
        type="primary"
        size="small"
        icon="el-icon-arrow-down"
        title="Go to Child Tab (alt + down)"
        :disabled="atLastTab"
        @click="goToChildTab"
      />
    </el-button-group>
  </div>
</template>
<script lang="ts" src="./action-toolbar.component.ts"></script>
<style lang="scss" scoped>
  .el-button-group + .el-button-group,
  .el-button-group + .el-dropdown,
  .el-dropdown + .el-button-group {
    padding-left: 8px;
  }
</style>
