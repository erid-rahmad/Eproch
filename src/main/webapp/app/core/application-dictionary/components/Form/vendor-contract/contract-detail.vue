<template>
  <el-tabs
    v-model="activeTab"
    class="contract-detail"
    type="card"
  >
    <el-tab-pane
      v-for="item in tabs"
      :key="item.id"
      :disabled="item.disabled"
      :label="item.name"
      lazy
      :name="item.value"
    >
      <component
        v-if="item.value === activeTab"
        :ref="item.value"
        :is="activeComponent"
        :key="item.id"
        :data="data"
        @saved="onTabSaved"
        @button="button"
      ></component>
    </el-tab-pane>
  </el-tabs>
</template>
<script lang="ts" src="./contract-detail.component.ts"></script>
