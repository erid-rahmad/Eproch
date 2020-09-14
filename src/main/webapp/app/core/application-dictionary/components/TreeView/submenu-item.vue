<template>
  <el-submenu :index="`${model.id}`">
    <template slot="title">
      <span>{{ printTitle(model) }}</span>
    </template>
    <drop-list
      mode="cut"
      no-animations
      :items="model[listKey]"
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
        <div class="feedback" :key="`feedback-${data.id}`"/>
      </template>
      <template v-slot:reordering-feedback="{item}">
        <div class="reordering-feedback" :key="`reordering-feedback-${item.id}`"/>
      </template>
    </drop-list>
  </el-submenu>
</template>
<script lang="ts" src="./submenu-item.component.ts"></script>
