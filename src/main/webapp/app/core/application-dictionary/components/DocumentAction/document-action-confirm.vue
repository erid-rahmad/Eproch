<template>
  <el-dialog
    class="document-action-confirm"
    :title="`${action.name} ${data.documentTypeName} ${data.documentNo}`"
    :visible.sync="visible"
    :width="width"
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
          size="mini"
          icon="el-icon-close"
          @click="$emit('update:visible', false)"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          size="mini"
          icon="el-icon-check"
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