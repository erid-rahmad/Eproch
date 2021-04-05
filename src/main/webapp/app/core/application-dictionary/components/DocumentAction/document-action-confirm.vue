<template>
  <el-dialog
    class="document-action-confirm"
    :title="title"
    :visible="visible"
    :width="width"
    @close="onClosed"
  >
    <template>
      <p>Are you sure you want to {{ action.name | lowercase }} {{ data.documentTypeName | lowercase }}?</p>
      <template v-if="action.value === 'RJC'">
        <p>Please type the reason:</p>
        <el-input
          v-model="action.message"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4}"
        ></el-input>
      </template>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="onClosed"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          size="mini"
          :type="action.value === 'RJC' ? 'danger' : 'primary'"
          @click="applyDocumentAction"
        >
          {{ action.name }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script lang="ts" src="./document-action-confirm.component.ts"></script>