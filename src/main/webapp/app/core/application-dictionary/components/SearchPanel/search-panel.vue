<template>
  <div class="search-panel">
    <el-tabs v-model="currentTab">
      <el-tab-pane label="Basic" name="basic">
        <basic-search 
          :fields="fields" 
          @submit="onSearch" 
          @clear="onClear"
          @close="onClose"
        />
      </el-tab-pane>
      <el-tab-pane label="Advanced" name="advance">
        <advance-search 
          :fields="fields"
          @submit="onSearch" 
          @clear="onClear"
          @close="onClose"
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script lang="ts" src="./search-panel.component.ts"></script>
<style lang="scss">
.search-panel {
  background: #fff;
  position: absolute;
  bottom: -92px;
  left: 0;
  right: 0;
  top: 0;
  display: none;
  z-index: 10;

  &[visible] {
    display: block;
  }
}

.close-panel{
  position: absolute;
  right: 0;
  top: 0;
  z-index: 10;
}
</style>
