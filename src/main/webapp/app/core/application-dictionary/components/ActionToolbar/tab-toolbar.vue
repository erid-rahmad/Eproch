<template>
  <div class="tab-toolbar" v-hotkey="keymap">
    <el-button-group v-show="allowInsert">
      <el-button
        v-show="!disabled && !isEditing"
        type="primary"
        size="mini"
        icon="el-icon-plus"
        title="New (shift + alt + a)"
        @click="addRecord"
      ></el-button>
      <el-button
        v-show="recordCount && !isEditing"
        type="primary"
        size="mini"
        icon="el-icon-document-copy"
        title="Copy (shift + alt + d)"
        :disabled="disabled"
        @click="copyRecord"
      ></el-button>
    </el-button-group>
    <el-button-group>
      <el-button
        v-show="allowInsert && !disabled && isEditing"
        type="primary"
        size="mini"
        icon="el-icon-check"
        title="Save (shift + alt + s)"
        @click="saveRecord"
      ></el-button>
      <el-button
        v-show="!disabled && isEditing"
        icon="el-icon-close"
        size="mini"
        title="Cancel (shift + alt + z)"
        @click="cancelOperation"
      ></el-button>
      <el-button
        v-show="allowDelete && recordCount && !isEditing"
        :disabled="disabled"
        icon="el-icon-delete"
        size="mini"
        title="Delete (shift + alt + del)"
        type="danger"
        @click="deleteRecord"
      ></el-button>
    </el-button-group>
  </div>
</template>
<script lang="ts" src="./tab-toolbar.component.ts"></script>
