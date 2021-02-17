<template>
  <div class="import-wizard">
    <el-row :gutter="24">
      <el-col
        :xs="12"
        :sm="8"
        :xl="6"
      >
        <el-form
          ref="mainForm"
          :model="csv"
          label-position="left"
          label-width="128px"
          size="mini"
        >
          <el-form-item
            class="field-table-name"
            label="Table Name"
            prop="tableName"
          >
            <el-input
              v-model="csv.tableName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            class="field-delimiter"
            label="Delimiter"
            prop="delimiter"
          >
            <el-select v-model="csv.delimiter">
              <el-option
                v-for="item in delimiterOptions"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            class="field-max-rows"
            label="Max. Rows"
            prop="maxRows"
          >
            <el-input-number
              v-model="csv.maxRows"
              controls-position="right"
              :min="-1"
            ></el-input-number>
          </el-form-item>
          <el-form-item
            class="field-batch-size"
            label="Batch Size"
            prop="matchSize"
          >
            <el-input-number
              v-model="csv.batchSize"
              controls-position="right"
              :min="1"
            ></el-input-number>
          </el-form-item>
          <el-form-item
            class="field-insert-only"
            prop="insertOnly"
            title="No need to lookup for the existing record"
          >
            <el-checkbox v-model="csv.insertOnly">
              Insert Only
            </el-checkbox>
          </el-form-item>
        </el-form>
        <upload-excel
          ref="inputFile"
          v-loading="loadingColumnDefinitions"
          :on-success="onFileSet"
          :before-upload="beforeUpload"
        ></upload-excel>
      </el-col>
      <el-col
        class="column-definition"
        :xs="12"
        :sm="16"
        :xl="18"
      >
        <el-row class="list-header">
          <el-col
            class="column-name"
            :span="18"
          >
            Target Column
          </el-col>
          <el-col
            class="column-type"
            :span="6"
          >
            Type
          </el-col>
        </el-row>
        <el-scrollbar wrap-class="scrollbar-wrapper">
          <drop-container
            group-name="columnDef"
            :get-child-payload="getListPayload"
            :should-accept-drop="shouldListAcceptDrop"
            @drag-start="dragging = true"
            @drag-end="dragging = false"
            @drop="onDropToList"
          >
            <draggable-item
              v-for="item in columnDefinitions"
              :key="item.id"
              :tag="{value: 'el-row'}"
            >
              <el-col
                class="column-name"
                :span="18"
              >
                <span v-html="item.label"></span>
              </el-col>
              <el-col
                class="column-type"
                :span="6"
              >
                {{ item.type }}
              </el-col>
            </draggable-item>
          </drop-container>
        </el-scrollbar>
      </el-col>
    </el-row>
    <el-table
      class="table-preview"
      :data="tableData"
      border
      highlight-current-row
      style="width: 100%;margin-top:8px;"
    >
      <el-table-column
        v-for="header in tableHeaders"
        :key="header.id"
        :class-name="header.highlight ? 'highlight' : ''"
        :prop="header.name"
        min-width="300"
      >
        <template slot="header" slot-scope="{ $index }">
          <drop-container
            :class="`table-header-${$index}`"
            group-name="tableHeader"
            :get-child-payload="(targetIndex) => getHeaderPayload($index, targetIndex)"
            :should-accept-drop="shouldHeaderAcceptDrop"
            @drop="onDropToHeader($index, $event)"
            @drag-start="dragging = true"
            @drag-enter="highlightCell($index)"
            @drag-leave="highlightCell($index, false)"
          >
            <draggable-item
              v-for="targetColumn in header.targetColumns"
              :key="targetColumn.id"
              :tag="{value: 'div', props: {class: 'target-column'}}"
            >
              <span v-html="targetColumn.label"></span>
            </draggable-item>
          </drop-container>
          <div class="source-column">{{ header.name }}</div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
<style lang="scss">
.smooth-dnd-no-user-select {
  .import-wizard {
    .column-definition,
    .table-preview {
      .smooth-dnd-ghost {
        background: #fafafa;
        box-shadow: 0 1px 4px rgba(30, 30, 30, .3);
        width: 300px !important;

        .column-type {
          display: none;
        }
      }
    }

    .table-preview {
      .highlight {
        .cell {
          background: #eee;
        }
      }

      .smooth-dnd-ghost {
        padding-left: 8px;
        padding-right: 8px;
      }
    }
  }
}
.import-wizard {
  .el-form--label-top {
    .el-form-item--mini.el-form-item {
      margin-bottom: 10px;
    }

    .el-form-item__label {
      margin-bottom: 0;
      padding-bottom: 0;
    }
  }

  .el-form {
    .el-input-number--mini {
      width: 100%;
    }

    .el-select {
      width: 100%;
    }
  }

  .drop {
    line-height: 74px;
    margin: 16px 0 8px;
    font-size: 18px;
    padding: 0 16px;
  }

  .column-definition {
    .column-name {
      .linked-table {
        font-weight: 600;
      }
    }

    .column-type {
      border-right: 1px solid #ccc;
    }

    .column-name,
    .column-type {
      border-left: 1px solid #ccc;
      padding: 2px 4px;
    }

    .el-row {
      border-bottom: 1px solid #ccc;
    }

    .list-header {
      border-bottom: 2px solid #ccc;
      border-top: 1px solid #ccc;

      .column-name,
      .column-type {
        font-weight: 600;
      }
    }

    .scrollbar-wrapper {
      border-bottom: 1px solid #ccc;
      height: 274px;
    }
  }

  .table-preview {
    .target-column {
      color: #aaa;
      font-weight: 600;

      span {
        color: #333;
        font-style: italic;
      }
    }
  }
}
</style>
