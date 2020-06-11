<template>
  <div class="app-container">
    <h3>{{ title }}</h3>
    <div class="window-content">
      <action-toolbar
        @toggle-view="switchView"
        :at-window-root="tabStack.length <= 1"
        :at-last-tab="childTabs.length === 0"
        :event-bus="mainToolbarEventBus"
      />
      <splitpanes
        class="default-theme"
        horizontal
        style="height: 100%"
        @ready="updateHeight"
        @resized="updateHeight"
      >
        <pane ref="mainPane">
          <transition name="fade" mode="out-in">
            <keep-alive>
              <grid-view
                v-if="gridView"
                ref="mainGrid"
                :tab="mainTab"
                :search-panel-event-bus="searchPanelEventBus"
                :toolbar-event-bus="mainToolbarEventBus"
                @current-row-change="loadChildTab"
                main-tab
              />
              <detail-view v-else/>
            </keep-alive>
          </transition>
        </pane>
        <pane
          v-if="hasChildTabs"
          ref="linePane"
          size="40"
          class="child-pane"
          style="position: relative"
        >
          <el-tabs
            v-model="currentTab"
            class="tab-container"
            type="border-card"
            @tab-click="handleTabClick"
          >
            <el-tab-pane
              v-for="(tab, index) in childTabs"
              :key="tab.id"
              ref="tabPane"
              :name="'' + index"
            >
              <span slot="label">
                <i :class="`el-icon-${tab.icon}`" v-if="tab.icon"> </i>{{ tab.name }}
              </span>
              <tab-toolbar
                :tab-id="'' + index"
                :event-bus="secondaryToolbarEventBus"
              />
              <grid-view
                ref="lineGrid"
                :tab="tab"
                :tab-id="'' + index"
                :toolbar-event-bus="secondaryToolbarEventBus"
                lazy-load
              />
            </el-tab-pane>
          </el-tabs>
        </pane>
      </splitpanes>
      <search-panel
        :visible.sync="searchPanelActive"
        :event-bus="searchPanelEventBus"
        :fields="mainTab.adfields"
      />
    </div>
  </div>
</template>

<script lang="ts" src="./dynamic-window.component.ts"></script>

<style lang="scss">
.el-tabs--border-card > .el-tabs__content {
  height: calc(100% - 30px);
}
.window-content {
  position: relative;
  height: calc(100% - 96px);

  .splitpanes .el-tabs__content {
    overflow: auto;

    .grid-view {
      height: calc(100% - 48px);
    }
  }
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