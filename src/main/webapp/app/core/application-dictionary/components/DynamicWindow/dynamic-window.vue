<template>
  <div class="app-container">
    <h2>{{ title }}</h2>
    <action-toolbar
      @toggle-view="switchView"
      @new-record="prepareNew"
    />
    <splitpanes
      class="default-theme"
      horizontal
      style="height: calc(100% - 96px)"
      @ready="updateHeight"
      @resize="updateHeight"
      @pane-maximize="updateHeight">
      <pane ref="mainPane">
        <transition name="fade" mode="out-in">
          <keep-alive>
            <grid-view
              ref="mainGrid"
              v-if="gridView"
              :base-api-url="mainTabBaseApiUrl"
              :fields="mainTabFields()"
              @current-row-change="loadChildTab"
            />
            <detail-view v-else/>
          </keep-alive>
        </transition>
      </pane>
      <pane
        ref="linePane"
        size="30"
        style="position: relative"
        v-if="hasChildTabs">
        <el-tabs class="tab-container" type="border-card">
          <el-tab-pane v-for="tab in childTabs" :key="tab.id">
            <span slot="label">
              <i :class="`el-icon-${tab.icon}`" v-if="tab.icon"> </i>{{ tab.name }}
            </span>
            <grid-view
              ref="lineGrid"
              :base-api-url="tab.targetEndpoint"
              :fields="tab.adfields"
              :filter-query="tab.filterQuery"
            />
          </el-tab-pane>
        </el-tabs>
      </pane>
    </splitpanes>
  </div>
</template>

<script lang="ts" src="./dynamic-window.component.ts"></script>

<style lang="scss">
.el-tabs--border-card > .el-tabs__content {
  height: calc(100% - 30px);
}
</style>
<style lang="scss" scoped>
.action-toolbar {
  padding-bottom: 20px;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity .25s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.tab-container {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}
.splitpanes {
  background: none;

  &__pane {
    background: none !important;

    .el-tabs__content .el-tab-pane {
      height: 100%;
    }
  }

  &__splitter {
    background-color: #ccc;position: relative;

    &::before {
      content: '';
      position: absolute;
      left: 0;
      top: 0;
      transition: opacity 0.4s;
      background-color: rgba(255, 0, 0, 0.3);
      opacity: 0;
      z-index: 1;
    }

    &:hover::before {
      opacity: 1;
    }
  }
}
.splitpanes--vertical > .splitpanes__splitter::before {
  left: -10px;
  right: -10px;
  height: 100%;
}
.splitpanes--horizontal > .splitpanes__splitter::before {
  top: -10px;
  bottom: -10px;
  width: 100%;
}
</style>