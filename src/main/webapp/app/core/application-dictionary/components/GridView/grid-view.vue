<template>
  <div
    class="grid-view"
  >
    <el-table
      ref="grid"
      highlight-current-row
      :height="height"
      size="small"
      style="width: 100%"
      :data="gridData"
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      @current-change="changeCurrentRow"
      @sort-change="changeOrder"
      @selection-change="changeRowSelection"
    >
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        v-for="field in gridFields"
        :key="field.id"
        :prop="field.name"
        :label="field.name"
        sortable
      />
      <el-table-column fixed="right" width="96">
        <template slot-scope="scope">
          <el-button
            @click="edit(scope.row)"
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
