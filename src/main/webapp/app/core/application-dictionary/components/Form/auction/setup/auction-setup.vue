<template>
  <el-tabs
    v-loading="loadingTabs"
    v-model="activeTab"
    class="auction-setup"
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
      ></component>
    </el-tab-pane>
  </el-tabs>
</template>
<script lang="ts" src="./auction-setup.component.ts"></script>
