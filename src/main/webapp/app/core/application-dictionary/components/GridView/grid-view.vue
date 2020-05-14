<template>
  <div
    class="grid-view"
  >
    <el-table
      v-loading="isFetching"
      ref="grid"
      :data="gridData"
      highlight-current-row
      :height="height"
      size="small"
      style="width: 100%"
      stripe
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      @current-change="changeCurrentRow"
      @sort-change="changeOrder"
      @selection-change="changeRowSelection"
      @row-dblclick="activateInlineEditing"
    >
      <el-table-column
        fixed
        type="selection"
        width="42">
      </el-table-column>
      <el-table-column
        v-for="field in gridFields"
        :key="field.id"
        :fixed="isFixed(field)"
        :prop="field.adColumn.name"
        :label="field.name"
        :width="getFieldWidth(field)"
        min-width="144"
        sortable
      >
        <template slot-scope="scope">
          <el-switch
            v-if="isActiveStatusField(scope.column)"
            v-model="scope.row[scope.column.property]"
            :disabled="!scope.row.editing"
          />
          <el-checkbox
            v-else-if="isBooleanField(field)"
            v-model="scope.row[scope.column.property]"
            :disabled="!scope.row.editing"
          />
          <el-input
            v-else-if="scope.row.editing && isStringField(field)"
            v-model="scope.row[scope.column.property]"
            size="small"
            clearable
          />
          <el-input-number
            v-else-if="scope.row.editing && isNumericField(field)"
            v-model="scope.row[scope.column.property]"
            controls-position="right"
            size="small"
            :min="getMinValue(field)"
            :max="getMaxValue(field)"
          />
          <span v-else>{{ getFieldValue(scope.row, scope.column.property) }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="left" width="66">
        <template slot-scope="scope">
          <el-button
            @click="activateInlineEditing(scope.row)"
            type="primary"
            size="mini"
            icon="el-icon-edit"
            plain
            :title="$t('entity.action.edit')"
          />
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      ref="pagination"
      background
      @size-change="changePageSize"
      @current-change="loadPage"
      :current-page.sync="page"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="itemsPerPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="queryCount"
    />
  </div>
</template>

<script lang="ts" src="./grid-view.component.ts"></script>

<style lang="scss" scoped>
.grid-view {
  display: flex;
  flex-direction: column;
  height: 100%;

  .el-table {
    flex: 1;
  }
}
</style>
