<template>
  <el-select
    v-loading="loading"
    v-model="reference"
    :class="classNames"
    clearable
    :disabled="disabled"
    filterable
    :placeholder="placeholder"
    :size="size"
    @change="onChanged"
  >
    <el-option
      v-for="item in referenceList"
      :key="item.id"
      :label="item.name"
      :value="item.value"
    ></el-option>
  </el-select>
</template>
<script lang="ts" src="./ad-input-list.component.ts"></script>