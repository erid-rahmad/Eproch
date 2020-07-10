<template>
  <div class="action-toolbar" v-hotkey="keymap">
    <el-button-group>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-plus"
        title="New (shift + alt + n)"
        :disabled="isEditing"
        @click="addRecord"
      />
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-document-copy"
        title="Copy (shift + alt + c)"
        :disabled="isEditing"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        title="Save (shift + alt + s)"
        :disabled="!isEditing"
        @click="saveRecord"
      />
      <el-button
        size="small"
        icon="el-icon-close"
        title="Cancel (shift + alt + z)"
        :disabled="!isEditing"
        @click="cancelOperation"
      />
      <el-button
        type="danger"
        size="mini"
        icon="el-icon-delete"
        title="Delete (shift + alt + del)"
        :disabled="isEditing"
        @click="deleteRecord"
      />
    </el-button-group>
  </div>
</template>
<script lang="ts" src="./tab-toolbar.component.ts"></script>
