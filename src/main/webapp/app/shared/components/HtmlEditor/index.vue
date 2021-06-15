<template>
  <div class="html-editor">
    <editor-menu-bar
      v-if="!disabled"
      :editor="editor"
      v-slot="{ commands, isActive }"
    >
      <div class="editor-menu-bar">
        <el-button-group>
          <el-button
            :size="size"
            :type="isActive.heading({ level: 1 }) ? 'primary' : null"
            @click="commands.heading({ level: 1 })"
          >
            H1
          </el-button>
          <el-button
            :size="size"
            :type="isActive.heading({ level: 2 }) ? 'primary' : null"
            @click="commands.heading({ level: 2 })"
          >
            H2
          </el-button>
          <el-button
            :size="size"
            :type="isActive.heading({ level: 3 }) ? 'primary' : null"
            @click="commands.heading({ level: 3 })"
          >
            H3
          </el-button>
        </el-button-group>
        <el-button-group>
          <el-button
            :size="size"
            :type="isActive.bold() ? 'primary' : null"
            @click="commands.bold"
          >
            <svg-icon name="icomoo/355-bold"></svg-icon>
          </el-button>
          <el-button
            :size="size"
            :type="isActive.italic() ? 'primary' : null"
            @click="commands.italic"
          >
            <svg-icon name="icomoo/357-italic"></svg-icon>
          </el-button>
          <el-button
            :size="size"
            :type="isActive.underline() ? 'primary' : null"
            @click="commands.underline"
          >
            <svg-icon name="icomoo/356-underline"></svg-icon>
          </el-button>
          <el-button
            :size="size"
            :type="isActive.strike() ? 'primary' : null"
            @click="commands.strike"
          >
            <svg-icon name="icomoo/358-strikethrough"></svg-icon>
          </el-button>
        </el-button-group>
        <el-button-group>
          <el-button
            :size="size"
            :type="isActive.ordered_list() ? 'primary' : null"
            @click="commands.ordered_list"
          >
            <svg-icon name="icomoo/186-list-numbered"></svg-icon>
          </el-button>
          <el-button
            :size="size"
            :type="isActive.bullet_list() ? 'primary' : null"
            @click="commands.bullet_list"
          >
            <svg-icon name="icomoo/187-list"></svg-icon>
          </el-button>
        </el-button-group>
      </div>
    </editor-menu-bar>
    <editor-content :editor="editor"></editor-content>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
<style lang="scss" scoped>
.html-editor {
  border: 1px solid #DCDFE6;
  padding: 16px;
  transition: border .25s;

  &:hover {
    border-color: #C0C4CC;

    .editor-menu-bar {
      border-bottom-color: #C0C4CC;
    }
  }

  .editor-menu-bar {
    border-bottom: 1px solid #DCDFE6;
    margin: -18px -16px 0;
    padding: 4px;
    transition: border .25s;
  }
}
</style>