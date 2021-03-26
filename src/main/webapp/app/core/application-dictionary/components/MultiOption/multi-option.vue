<template>
  <el-checkbox-group
    v-if="checkboxGroup"
    v-model="selectedOptions"
    class="multi-option"
    @change="onOptionChanged"
  >
    <el-checkbox
      v-for="item in options"
      :key="item.id"
      :label="item.value"
    >
      {{ item.name }}
    </el-checkbox>
  </el-checkbox-group>
  <el-select
    v-else
    v-model="selectedOptions"
    class="multi-option"
    collapse-tags
    multiple
    :placeholder="placeholder"
    :size="size"
    @change="onOptionChanged"
  >
    <el-option
      v-for="item in options"
      :key="item.id"
      :label="item.name"
      :value="item.value">
    </el-option>
  </el-select>
</template>
<script lang="ts" src="./multi-option.component.ts"></script>
<style lang="scss" scoped>
.el-checkbox-group.multi-option .el-checkbox {
  display: block;
}
</style>
