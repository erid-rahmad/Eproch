<template>
  <div class="search-panel">
    <el-tabs
      v-model="currentTab"
      @tab-click="onTabClick"
    >
      <el-tab-pane label="Basic" name="basic">
        <basic-search 
          :event-bus="eventBus" 
          :fields="fields" 
          @submit-basic-search="onBasicSearch" 
          @clear-search="onClear" />
      </el-tab-pane>
      <el-tab-pane label="Advanced" name="advance">
        <advance-search 
          :event-bus="eventBus" 
          :fields="fields"
          @submit-advance-search="onAdvanceSearch" 
          @clear-search="onClear" />
      </el-tab-pane>
    </el-tabs>

    <el-button 
      @click="onClosePanel"
      type="primary"
      circle
      plain 
      class="close-panel"
      icon="el-icon-close"/>

  </div>
</template>
<script lang="ts" src="./search-panel.component.ts"></script>
<style lang="scss">
.search-panel {
  background: #fff;
  position: absolute;
  bottom: -52px;
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
