<template>
  <div class="grid-view">
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
      @select="changeMultipleRowSelection"
      @selection-change="changeRowSelection"
      @row-dblclick="activateInlineEditing"
    >
      <el-table-column
        fixed
        type="selection"
        align="center"
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
        :show-overflow-tooltip="!editing"
        sortable="custom"
      >
        <template slot-scope="scope">
          <el-switch
            v-if="isActiveStatusField(scope.column)"
            :ref="scope.column.property"
            v-model="scope.row[scope.column.property]"
            :disabled="!scope.row.editing || isReadonly(scope.row, field)"
          />
          <el-checkbox
            v-else-if="isBooleanField(field)"
            :ref="scope.column.property"
            v-model="scope.row[scope.column.property]"
            :disabled="!scope.row.editing || isReadonly(scope.row, field)"
          />
          <span v-else-if="!scope.row.editing">
            {{ getFieldValue(scope.row, field) }}
          </span>
          <!-- <el-input
            v-else-if="isTableDirectLink(field)"
            v-model="scope.row[scope.column.property]"
            size="small"
            clearable
          >
            <i
              slot="prefix"
              class="el-input__icon el-icon-search"
              @click="openSearchWindow"
            />
          </el-input> -->
          <el-tooltip
            v-else
            :disabled="!hasError(field)"
            :content="getErrorMessage(field)"
          >
            <el-select
              v-if="isTableDirectLink(field)"
              :ref="scope.column.property"
              v-model="scope.row[scope.column.property]"
              :remote="true"
              :remote-method="fetchTableDirectData"
              size="small"
              clearable
              filterable
              :disabled="isReadonly(scope.row, field)"
              @focus="setTableDirectReference(field)"
            >
              <el-option
                v-for="item in tableDirectReferences(field)"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
            <el-select
              v-else-if="hasReferenceList(field)"
              :ref="scope.column.property"
              v-model="scope.row[scope.column.property]"
              size="small"
              clearable
              filterable
              :disabled="isReadonly(scope.row, field)"
            >
              <el-option
                v-for="item in referenceListItems(field)"
                :key="item.value"
                :label="item.name"
                :value="item.value"
              />
            </el-select>
            <el-input
              v-else-if="isStringField(field)"
              :ref="scope.column.property"
              v-model="scope.row[scope.column.property]"
              size="small"
              clearable
              :disabled="isReadonly(scope.row, field)"
            />
            <el-input-number
              v-else-if="isNumericField(field)"
              :ref="scope.column.property"
              v-model="scope.row[scope.column.property]"
              controls-position="right"
              size="small"
              :min="getMinValue(field)"
              :max="getMaxValue(field)"
              :disabled="isReadonly(scope.row, field)"
            />
          </el-tooltip>
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
      :current-page.sync="page"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="itemsPerPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="queryCount"
    />

    <el-dialog
        width="35%"
        :visible.sync="showDeleteDialog"
        :title="$t('entity.delete.title')"
    >
      <template>
        <span>Are you sure to delete the selected records?</span>
        <div slot="footer">
          <el-button 
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-delete" 
            type="danger" 
            @click="actionDelete()"
          >
            {{ $t('entity.action.delete') }}
          </el-button>
          <el-button 
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close" 
            @click="closeDialog()"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts" src="./grid-view.component.ts"></script>

<style>
.grid-view .el-table .is-error .el-input__inner {
  border-color: #ff4949;
}
</style>
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
