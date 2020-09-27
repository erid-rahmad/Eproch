<template>
  <div class="action-toolbar" v-hotkey="keymap">
    <el-button-group>
      <el-button
        v-show="!isEditing"
        icon="el-icon-plus"
        size="small"
        title="New (alt + a)"
        type="primary"
        @click="addRecord"
      />
      <el-button
        v-show="recordCount && !isEditing"
        icon="el-icon-document-copy"
        size="small"
        title="Copy (alt + d)"
        type="primary"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        v-show="isEditing"
        icon="el-icon-check"
        size="small"
        title="Save (alt + s)"
        type="primary"
        @click="saveRecord"
      />
      <el-button
        v-show="isEditing"
        icon="el-icon-close"
        size="small"
        title="Cancel (alt + z)"
        @click="cancelOperation"
      />
      <el-button
        v-show="recordCount && !isEditing"
        icon="el-icon-delete"
        size="small"
        title="Delete (alt + del)"
        type="danger"
        @click="deleteRecord"
      />
    </el-button-group>
    <el-button-group v-show="!isEditing">
      <el-button
        icon="el-icon-refresh"
        size="small"
        title="Refresh (alt + r)"
        type="primary"
        @click="refreshData"
      />
      <el-button
        icon="el-icon-search"
        size="small"
        title="Search (alt + f)"
        type="primary"
        @click="openSearchWindow"
      />
      <el-button 
        icon="el-icon-download"
        size="small"
        title="Export"
        type="primary"
        @click="exportRecord"
      />
    </el-button-group>
    <el-dropdown
      v-if="buttons.length"
      v-show="!isEditing"
      size="small"
      @command="runProcess"
    >
      <el-button
        size="small"
        type="primary"
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
        icon="el-icon-s-grid"
        :plain="!gridView"
        size="small"
        title="Toggle Layout (alt + g)"
        type="primary"
        @click="switchView"
      />
      <el-button
        v-show="!isEditing"
        :disabled="atWindowRoot"
        icon="el-icon-arrow-up"
        size="small"
        title="Go to Parent Tab (alt + up)"
        type="primary"
        @click="goToParentTab"
      />
      <el-button
        v-show="!isEditing"
        :disabled="atLastTab"
        icon="el-icon-arrow-down"
        size="small"
        title="Go to Child Tab (alt + down)"
        type="primary"
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
