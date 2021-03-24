<template>
  <div class="action-toolbar" v-hotkey="keymap">
    <el-button-group>
      <el-button
        v-show="!isEditing"
        icon="el-icon-plus"
        size="mini"
        title="New (alt + a)"
        type="primary"
        @click="addRecord"
      />
      <el-button
        v-show="recordCount && !isEditing"
        icon="el-icon-document-copy"
        size="mini"
        title="Copy (alt + d)"
        type="primary"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        v-show="isEditing"
        icon="el-icon-check"
        size="mini"
        title="Save (alt + s)"
        type="primary"
        @click="saveRecord"
      />
      <el-button
        v-show="isEditing"
        icon="el-icon-close"
        size="mini"
        title="Cancel (alt + z)"
        @click="cancelOperation"
      />
      <el-button
        v-show="recordCount && !isEditing"
        icon="el-icon-delete"
        size="mini"
        title="Delete (alt + del)"
        type="danger"
        @click="deleteRecord"
      />
    </el-button-group>
    <el-button-group v-show="!isEditing">
      <el-button
        icon="el-icon-refresh"
        size="mini"
        title="Refresh (alt + r)"
        type="primary"
        @click="refreshData"
      />
      <el-button
        icon="el-icon-search"
        size="mini"
        title="Search (alt + f)"
        type="primary"
        @click="openSearchWindow"
      />
      <el-button
        icon="el-icon-printer"
        size="mini"
        title="Print"
        type="primary"
        v-if="windowName == 'Purchase Order'"
        @click="printRecord"
      />
    </el-button-group>
    <el-dropdown
      v-if="buttons.length"
      v-show="!isEditing"
      size="mini"
      @command="runProcess"
    >
      <el-button
        size="mini"
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
        size="mini"
        title="Toggle Layout (alt + g)"
        type="primary"
        @click="switchView"
      />
      <el-button
        v-show="!isEditing"
        :disabled="atWindowRoot"
        icon="el-icon-arrow-up"
        size="mini"
        title="Go to Parent Tab (alt + up)"
        type="primary"
        @click="goToParentTab"
      />
      <el-button
        v-show="!isEditing"
        :disabled="atLastTab"
        icon="el-icon-arrow-down"
        size="mini"
        title="Go to Child Tab (alt + down)"
        type="primary"
        @click="goToChildTab"
      />
    </el-button-group>
    <el-dropdown
      v-show="!isEditing && hasDocumentActions"
      ref="docActionButton"
      size="mini"
      split-button
      :type="approved ? 'success' : 'primary'"
      @click="applyNextDocumentAction"
      @command="applyDocumentAction"
    >
      {{ defaultDocumentAction }}
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item
          v-for="action in documentActions"
          :key="action.id"
          :command="action"
          :title="action.name"
        >
          {{ action.name }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-button-group>
      <el-button
        v-show="!isEditing"
        size="mini"
        title="Import (alt + i)"
        type="primary"
        @click="importRecord"
      >
        <svg-icon name="icomoo/198-download2"></svg-icon> Import
      </el-button>
      <el-button
        v-show="!isEditing"
        size="mini"
        title="Export (alt + e)"
        type="primary"
        @click="exportRecord"
      >
        <svg-icon name="icomoo/199-upload2"></svg-icon> Export
      </el-button>
    </el-button-group>
  </div>
</template>
<script lang="ts" src="./action-toolbar.component.ts"></script>
<style lang="scss">
  .compact {
    .el-button {
      font-size: 12px;
    }
  }
  .el-button-group + .el-button-group,
  .el-button-group + .el-dropdown,
  .el-dropdown + .el-button-group {
    padding-left: 8px;
  }
</style>
