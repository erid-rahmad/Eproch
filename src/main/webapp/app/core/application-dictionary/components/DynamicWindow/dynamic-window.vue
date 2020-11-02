<template>
  <div class="app-container">
    <el-row
      class="window-control-bar"
      :gutter="0"
    >
      <el-col :span="12">
        <action-toolbar
          ref="mainToolbar"
          :approved="approved"
          :at-window-root="tabStack.length <= 1"
          :at-last-tab="childTabs.length === 0"
          :buttons="mainTab.toolbarButtons"
          :document-type-id="documentTypeId"
          :event-bus="mainToolbarEventBus"
          :next-document-action="nextDocumentAction"
          :record-count="totalRecords"
          :window-type="windowType"
          @add-record="onAddRecord"
          @apply-document-action="onApplyDocumentAction"
          @cancel="onActionCanceled"
          @copy="onCopyRecord"
          @delete="showDeleteConfirmation"
          @edit-mode-change="onEditModeChanged"
          @open-search-panel="openSearchPanel"
          @refresh="refreshWindow"
          @run-trigger="runTrigger"
          @save="onRecordSave"
          @tab-navigate="onTabNavigated"
          @toggle-view="switchView"
        />
        <el-breadcrumb
          class="tab-navigation-breadcrumb"
          separator-class="el-icon-arrow-right"
          @click.native="onNavigationBreadcrumbClicked"
        >
          <transition-group name="breadcrumb">
            <el-breadcrumb-item
              v-for="(tab, index) in tabStack"
              :key="tab.id"
              :class="{ 'link-enabled': index < tabStack.length }"
              :data-id="tab.id"
              :data-index="index"
            >
              {{ tab.name }}
            </el-breadcrumb-item>
          </transition-group>
        </el-breadcrumb>
      </el-col>
      <el-col :span="12">
        <el-pagination
          ref="toolbarPagination"
          layout="prev, jumper, total, next"
          class="record-navigator"
          :class="{'invisible': isEditing}"
          :current-page.sync="currentRecordNo"
          :page-size="1"
          small
          :total="totalRecords"
          @current-change="onCurrentRecordChange"
        />
      </el-col>
    </el-row>
    <div
      class="window-content"
      :class="{'detailed-view': !gridView}"
    >
      <splitpanes
        class="opus-theme"
      >
        <pane
          v-if="treeView"
          ref="treePane"
          min-size="10"
          max-size="30"
          size="20"
        >
          <tree-view
            ref="treeView"
            :tab="mainTab"
          />
        </pane>
        <pane>
          <splitpanes
            horizontal
            style="height: 100%"
          >
            <pane
              ref="mainPane"
              class="main-pane"
            >
              <grid-view
                v-show="gridView"
                ref="mainGrid"
                :access-level="accessLevel"
                :tab="mainTab"
                :toolbar-event-bus="mainToolbarEventBus"
                @current-row-change="onMainRecordChange"
                @inline-editing="$refs.mainToolbar.activateInlineEditing()"
                @quit-edit-mode="quitEditMode"
                @record-saved="reloadTreeView"
                @rows-deleted="hideDeleteConfirmation"
                @total-count-changed="onTotalCountChange"
                main-tab
              />
              <detail-view
                v-show="!gridView"
                ref="mainForm"
                :tab="mainTab"
                :record="currentRecord"
                :toolbar-event-bus="mainToolbarEventBus"
                @edit-mode-change="$refs.mainGrid.editRecord()"
                @form-validated="$refs.mainGrid.beforeSave()"
              />
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
                  v-for="(tab, index) in subTabs"
                  :key="tab.id"
                  ref="tabPane"
                  :name="'' + index"
                >
                  <span slot="label">
                    <em v-if="tab.icon" :class="`${tab.icon}`"></em>{{ tab.name }}
                  </span>
                  <tab-toolbar
                    ref="lineToolbar"
                    :disabled="isEditing"
                    :event-bus="secondaryToolbarEventBus"
                    :tab-id="'' + index"
                    @add-record="onAddRecord"
                    @cancel="onActionCanceled"
                    @copy="onCopyRecord"
                    @delete="showDeleteConfirmation"
                    @save="onRecordSave"
                  />
                  <grid-view
                    ref="lineGrid"
                    :tab="tab"
                    :tab-id="'' + index"
                    :toolbar-event-bus="secondaryToolbarEventBus"
                    lazy-load
                    @inline-editing="$refs.lineToolbar[index].activateInlineEditing()"
                    @rows-deleted="hideDeleteConfirmation"
                    @total-count-changed="count => {$refs.lineToolbar[index].recordCount = count}"
                  />
                </el-tab-pane>
              </el-tabs>
            </pane>
          </splitpanes>
        </pane>
      </splitpanes>
      <search-panel
        :visible.sync="searchPanelActive"
        :event-bus="searchPanelEventBus"
        :fields="mainTab.adfields"
        @submit="applyFilter"
        @clear="clearFilter"
        @close="closeSearchPanel"
      />
      <trigger-parameter-form
        ref="triggerForm"
        :data="triggerModel"
        :current-record="currentRecord"
        :fields="mainTab.adfields"
        :tab-name="mainTab.name"
        @process-completed="onProcessCompleted"
      />
      <el-dialog
        width="30%"
        :visible.sync="deleteConfirmationVisible"
        :title="$t('entity.delete.title')"
      >
        <template>
          <span>Are you sure to delete the selected record(s)?</span>
          <div slot="footer">
            <el-button
              style="margin-left: 0px;"
              size="mini"
              icon="el-icon-delete" 
              type="danger" 
              @click="deleteRecords"
            >
              {{ $t('entity.action.delete') }}
            </el-button>
            <el-button
              style="margin-left: 0px;"
              size="mini"
              icon="el-icon-close" 
              @click="deleteConfirmationVisible = false"
            >
              {{ $t('entity.action.cancel') }}
            </el-button>
          </div>
        </template>
      </el-dialog>
      <el-dialog
        width="20%"
        :visible.sync="docActionPopupVisible"
        :title="`${docAction.name} Document`"
      >
        <template>
          <p>Are you sure you want to {{ docAction.name }} the document?</p>
          <p v-if="docAction.value === 'RJC'">Please type the reason:</p>
          <el-input
            v-if="docAction.value === 'RJC'"
            v-model="docAction.message"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
          />
          <div slot="footer">
            <el-button
              style="margin-left: 0px;"
              size="mini"
              icon="el-icon-check" 
              :type="docAction.value === 'RJC' ? 'danger' : 'primary'" 
              @click="applyDocumentAction"
            >
              {{ docAction.name }}
            </el-button>
            <el-button
              style="margin-left: 0px;"
              size="mini"
              icon="el-icon-close" 
              @click="docActionPopupVisible = false"
            >
              {{ $t('entity.action.cancel') }}
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script lang="ts" src="./dynamic-window.component.ts"></script>

<style lang="scss">
.splitpanes {
  background: #333;
}
.splitpanes__splitter {
  background-color: #ccc;
  position: relative;
}
.splitpanes__splitter:before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  transition: opacity 0.4s;
  background-color: rgba(255, 0, 0, 0.2);
  opacity: 0;
  z-index: 5;
}
.splitpanes__splitter:hover:before {
  opacity: 1;
}
.splitpanes--vertical > .splitpanes__splitter:before {
  left: -8px;
  right: -8px;
  height: 100%;
}
.splitpanes--horizontal > .splitpanes__splitter:before {
  top: -8px;
  bottom: -8px;
  width: 100%;
}
.el-pagination--small .el-pagination__editor.el-input .el-input__inner {
  height: 20px;
}
.el-tabs--border-card > .el-tabs__content {
  height: calc(100% - 30px);
  padding: 0px;
}
.el-tabs__item {
  line-height: 30px;
}
.el-tabs__nav-scroll{
  height: 30px;
}
.el-tabs--top.el-tabs--border-card > .el-tabs__header .el-tabs__item:last-child{
  padding: 0px 10px 0px 10px;
  height: 30px;
}
.record-navigator {
  opacity: 1;
  transition: opacity .25s ease-in-out;
  z-index: 5;

  &.invisible {
    opacity: 0;
  }

  .el-input__inner {
    font-size: 13px;
  }
}
.window-control-bar {
  position: relative;

  .record-navigator {
    position: absolute;
    bottom: 0;
    right: 0;
    padding: 1px;
  }
}
.window-content {
  position: relative;
  height: calc(100% - 72px);

  &.detailed-view .splitpanes__pane.main-pane {
    overflow-y: auto;
  }

  .splitpanes {

    .splitpanes__pane {
      
      .main-pane {
        position: relative;
      }

      .grid-view {
        height: 100%;
      }
    }

    .el-tabs__content {
      overflow: auto;

      .grid-view {
        height: calc(100% - 37px);
      }
    }
  }
}
</style>

<style lang="scss" scoped>
.tab-navigation-breadcrumb {
  line-height: 32px;  
  padding-left: 8px;

  .link-enabled {
    font-weight: 700;
    cursor: pointer;
  }
}
.action-toolbar,
.tab-toolbar {
  padding: 4px;
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

  &.splitpanes--vertical > .splitpanes__splitter::before {
    left: -10px;
    right: -10px;
    height: 100%;
  }

  &.splitpanes--horizontal > .splitpanes__splitter::before {
    top: -10px;
    bottom: -10px;
    width: 100%;
  }

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
</style>