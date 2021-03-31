<template>
  <el-dropdown
    v-if="hasActions"
    ref="mainButton"
    class="document-action-button"
    :size="size"
    split-button
    :type="approved ? 'success' : 'primary'"
    @click="applyNextDocumentAction"
    @command="applyDocumentAction"
  >
    {{ defaultDocumentAction }}
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item
        v-for="action in actions"
        :key="action.id"
        :command="action"
        :title="action.name"
      >
        {{ action.name }}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>
<script lang="ts" src="./document-action-button.component.ts"></script>
