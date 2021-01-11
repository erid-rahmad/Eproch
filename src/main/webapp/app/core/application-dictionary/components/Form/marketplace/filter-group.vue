<template>
  <div class="filter-group">
    <div class="title">{{ title }}</div>
    <el-checkbox-group
      v-if="type === 'list'"
      v-model="checkedItems"
      @change="onCheckedItemsChanged"
    >
        <el-checkbox
          v-for="item in listItems"
          :key="item"
          :label="item"
        >{{ item }}</el-checkbox>
    </el-checkbox-group>
    <el-form
      v-if="type === 'range'"
      v-model="range"
    >
      <el-form-item>
        <el-input
          v-model="range.min"
          placeholder="Min"
          size="mini"
        />
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="range.max"
          placeholder="Max"
          size="mini"
        />
      </el-form-item>
    </el-form>
  </div>
</template>
<script lang="ts" src="./filter-group.component.ts"></script>
<style lang="scss" scoped>
.filter-group {
  .title {
    font-size: 14px;
    font-weight: 600;
  }

  .el-checkbox {
    display: block;
  }

  .el-form-item {
    margin-bottom: 0;
  }
}
</style>
