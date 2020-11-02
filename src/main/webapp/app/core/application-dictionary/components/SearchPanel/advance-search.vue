<template>
  <div class="advance-search">

    <el-button
      icon="el-icon-plus"
      plain
      size="mini"
      style="margin-left: 0px;"
      title="Add"
      type="primary"
      @click="addFilterAdvance"
    >
      Add
    </el-button>

    <el-button
      icon="el-icon-search"
      size="mini"
      style="margin-left: 0px;"
      title="Execute"
      type="primary"
      @click="filterAdvance"
    >
      Execute
    </el-button>

    <el-button
      icon="el-icon-refresh"
      plain
      size="mini"
      style="margin-left: 0px;"
      title="Clear"
      type="warning"
      @click="clear"
    >
      Clear
    </el-button>

    <el-button 
      style="margin-left: 0px;"
      size="mini"
      type="default" 
      icon="el-icon-close"
      @click="close"
      title="Close"
    />

    <el-table
      v-loading="isFetching"
      ref="grid"
      :data="gridData"
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      fit
      size="small"
      style="width: 100%"
      @selection-change="handleSelectionChangeFilterAdvance"
    >
      <el-table-column
        fixed
        type="selection"
        align="center"
        width="42">
      </el-table-column>

      <el-table-column
          prop="column"
          align="center"
          label="Column"
      >
        <template slot-scope="{row, $index}">
          <el-select
            filterable
            size="mini"
            v-model="row.column"
            value-key="id"
            placeholder="Select Column"
            @change="columnOptionType(row, $index)"
          >
            <el-option
              v-for="field in gridFields"
              :key="fieldName(field)"
              :label="field.name"
              :value="field"
            >
              <span style="float: left">{{ field.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 8px">{{ fieldType(field) }}</span>
            </el-option>
          </el-select>
        </template>
      </el-table-column>

      <el-table-column
        prop="query"
        align="center"
        label="Query"
      >
        <template slot-scope="{row, $index}">
          <el-select
            filterable
            size="mini"
            v-model="row.query"
            placeholder="Select Query"
          >
            <el-option
              v-for="queryOption in operators($index)"
              :key="queryOption.code"
              :label="queryOption.value"
              :value="queryOption"
            />
          </el-select>
        </template>
      </el-table-column>

      <el-table-column
        prop="queryValue"
        align="center"
        label="Query Value"
      >
        <template slot-scope="{row}">
          <el-select
            v-if="isBooleanField(row.column)"
            v-model="row.queryValue"
            size="small"
            filterable
          >
            <el-option
              v-for="item in booleanOption"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>

          <el-input
            v-else
            class="edit-input"
            size="mini"
            v-model="row.queryValue"
            clearable
          />
        </template>
      </el-table-column>

      <el-table-column fixed="left" width="66">
        <template slot-scope="{$index}">
          <el-button
            style="margin-left: 0px;"
            type="danger"
            size="mini"
            icon="el-icon-close"
            plain
            :title="$t('entity.action.delete')"
            @click="removeFilterAdvance($index)"
          />
        </template>
      </el-table-column>
    </el-table>
    
  </div>
</template>
<script lang="ts" src="./advance-search.component.ts"></script>
