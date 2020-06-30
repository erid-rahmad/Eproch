<template>
  <div
    v-loading="loading"
    class="tree-view"
  >
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
  </div>
</template>
<script lang="ts" src="./tree-view.component.ts"></script>
<style lang="scss">
.tree-view .el-submenu__title, .tree-view .el-menu-item {
  font-size: 12px;
  line-height: 32px;
  height: 32px;
  border-bottom: 1px solid #eee;
}
.tree-view .el-submenu.is-active .el-submenu__title {
  border-bottom-color: #ccc;
  color: #333 !important;
  font-weight: 600;
}
</style>
