<template>
  <div class="action-toolbar" v-hotkey="keymap">
    <el-button-group>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-plus"
        title="New (shift + alt + n)"
        :disabled="disabled || isEditing"
        @click="addRecord"
      />
      <el-button
        v-show="recordCount && !isEditing"
        type="primary"
        size="mini"
        icon="el-icon-document-copy"
        title="Copy (shift + alt + c)"
        :disabled="disabled"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        title="Save (shift + alt + s)"
        :disabled="disabled || !isEditing"
        @click="saveRecord"
      />
      <el-button
        size="small"
        icon="el-icon-close"
        title="Cancel (shift + alt + z)"
        :disabled="disabled || !isEditing"
        @click="cancelOperation"
      />
      <el-button
        v-show="recordCount && !isEditing"
        type="danger"
        size="mini"
        icon="el-icon-delete"
        title="Delete (shift + alt + del)"
        :disabled="disabled"
        @click="deleteRecord"
      />
    </el-button-group>
  </div>
</template>
<script lang="ts" src="./tab-toolbar.component.ts"></script>
