<template>
  <div class="filter-group">
    <div class="title">{{ title }}</div>
    <el-scrollbar
      v-if="type === 'list'"
      wrap-class="scrollbar-wrapper"
    >
      <el-checkbox-group
        v-model="checkedItems"
        @change="onCheckedItemsChanged"
      >
          <el-checkbox
            v-for="item in listItems"
            :key="item"
            :label="item"
          >{{ item }}</el-checkbox>
      </el-checkbox-group>
    </el-scrollbar>
    <el-form
      v-if="type === 'range'"
      v-model="range"
    >
      <el-form-item>
        <el-input
          v-model="range.min"
          v-inputmask="{'alias': 'currency'}"
          placeholder="Min"
          size="mini"
        />
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="range.max"
          v-inputmask="{'alias': 'currency'}"
          placeholder="Max"
          size="mini"
        />
      </el-form-item>
    </el-form>
    <el-scrollbar
      v-if="type === 'tree'"
      wrap-class="scrollbar-wrapper"
    >
      <el-tree
        ref="tree"
        :data="treeItems"
        node-key="id"
        :props="defaultTreeProps"
        show-checkbox
        @check-change="onTreeChanged"
      />
    </el-scrollbar>
  </div>
</template>
<script lang="ts" src="./filter-group.component.ts"></script>
<style lang="scss">
.filter-group {
  .el-checkbox {
    display: block;
  }

  .el-form-item {
    margin-bottom: 0;
  }

  .scrollbar-wrapper {
    max-height: 256px;
  }

  .title {
    font-size: 14px;
    font-weight: 600;
  }
}
</style>
