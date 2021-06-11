<template>
  <el-select
    v-loading="loading"
    v-model="key"
    :class="classNames"
    clearable
    :disabled="disabled"
    filterable
    remote
    :remote-method="fetchRecords"
    :size="size"
    :placeholder="placeholder"
    @change="onChanged"
    @clear="fetchRecords"
  >
    <el-option
      v-for="item in lookupItems"
      :key="item.id"
      :label="printLabel(item)"
      :value="item.id"
    >
      <slot v-bind:item="item"></slot>
    </el-option>
  </el-select>
</template>
<script lang="ts" src="./ad-input-lookup.component.ts"></script>
