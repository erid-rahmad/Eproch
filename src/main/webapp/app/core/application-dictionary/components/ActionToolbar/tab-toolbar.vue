<template>
  <div class="tab-toolbar" v-hotkey="keymap">
    <el-button-group>
      <el-button
        v-show="!disabled && !isEditing"
        type="primary"
        size="mini"
        icon="el-icon-plus"
        title="New (shift + alt + a)"
        @click="addRecord"
      />
      <el-button
        v-show="recordCount && !isEditing"
        type="primary"
        size="mini"
        icon="el-icon-document-copy"
        title="Copy (shift + alt + d)"
        :disabled="disabled"
        @click="copyRecord"
      />
    </el-button-group>
    <el-button-group>
      <el-button
        v-show="!disabled && isEditing"
        type="primary"
        size="mini"
        icon="el-icon-check"
        title="Save (shift + alt + s)"
        @click="saveRecord"
      />
      <el-button
        v-show="!disabled && isEditing"
        size="small"
        icon="el-icon-close"
        title="Cancel (shift + alt + z)"
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
