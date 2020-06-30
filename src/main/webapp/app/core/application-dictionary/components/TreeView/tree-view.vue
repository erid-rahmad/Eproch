<template>
  <div
    v-loading="loading"
    class="tree-view"
  >
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <drop-list
        tag="el-menu"
        mode="cut"
        no-animations
        :items="list"
        :row="true"
        @insert="onInsert"
        @reorder="onReorder"
      >
        <template v-slot:item="{item}">
          <drag
            :key="item.id"
            tag="tree-item"
            :model="item"
            :data="item"
            :base-api-url="baseApiUrl"
            :list="item[listKey]"
            :list-key="listKey"
            @cut="remove(item)"
          />
        </template>
        <template v-slot:feedback="{data}">
          <div class="feedback" :key="`${data.id}`"/>
        </template>
        <template v-slot:reordering-feedback="{item}">
          <div class="reordering-feedback" :key="`feedback-${item.id}`"/>
        </template>
      </drop-list>
    </el-scrollbar>
  </div>
</template>
<script lang="ts" src="./tree-view.component.ts"></script>
<style lang="scss">
.tree-view {
  height: 100%;

  .el-scrollbar {
    height: 100%;

    .scrollbar-wrapper {
      overflow-x: hidden;
    }
  }

  .el-submenu__title,
  .el-menu-item {
    font-size: 12px;
    line-height: 32px;
    height: 32px;
    border-bottom: 1px solid #eee;
  }

  .el-submenu.is-active .el-submenu__title {
    border-bottom-color: #ccc;
    color: #333 !important;
    font-weight: 600;
  }
}
</style>
