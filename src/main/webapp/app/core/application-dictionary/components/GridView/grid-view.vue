<template>
  <div ref="tableWrapper" class="grid-view">
    <el-table
      v-loading="isFetching"
      ref="grid"
      :data="gridData"
      :height="tableHeight"
      size="mini"
      style="width: 100%"
      highlight-current-row
      border
      stripe
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      @current-change="onCurrentRowChanged"
      @sort-change="changeOrder"
      @selection-change="onSelectionChanged"
      @row-dblclick="activateInlineEditing"
    >
      <el-table-column
        v-show="!editing"
        fixed
        type="selection"
        align="center"
        width="48"
      >
      </el-table-column>
      <el-table-column
        fixed
        width="48"
      >
        <template slot-scope="scope">
          <el-link
            @click="activateInlineEditing(scope.row)"
            type="primary"
            size="mini"
            icon="el-icon-edit"
            plain
            :title="$t('entity.action.edit')"
          />
        </template>
      </el-table-column>
      <el-table-column
        v-for="field in gridFields"
        :key="field.id"
        :fixed="isFixed(field)"
        :prop="field.adColumn.name"
        :label="field.name"
        :width="getFieldWidth(field)"
        min-width="128"
        show-overflow-tooltip
        :sortable="editing ? false: 'custom'"
      >
        <template slot-scope="scope">
          <el-switch
            v-if="isActiveStatusField(field)"
            :ref="scope.column.property"
            v-model="scope.row[scope.column.property]"
            :class="scope.column.property"
            :disabled="!scope.row.editing || isReadonly(scope.row, field)"
            @change="value => onInputChanged(field, value)"
          />
          <el-checkbox
            v-else-if="isBooleanField(field)"
            :ref="scope.column.property"
            v-model="scope.row[scope.column.property]"
            :class="scope.column.property"
            :disabled="!scope.row.editing || isReadonly(scope.row, field)"
            @change="value => onInputChanged(field, value)"
          />
          <span v-else-if="!scope.row.editing">
            {{ getFieldValue(scope.row, field) }}
          </span>
          <el-tooltip
            v-else
            :disabled="!hasError(field)"
            :content="getErrorMessage(field)"
          >
            <el-select
              v-if="isTableDirectLink(field)"
              :ref="scope.column.property"
              v-model="scope.row[scope.column.property]"
              :class="scope.column.property"
              :remote="true"
              :remote-method="fetchTableDirectData"
              size="mini"
              clearable
              filterable
              :disabled="isReadonly(scope.row, field)"
              @change="value => onInputChanged(field, value)"
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
              :class="scope.column.property"
              size="mini"
              clearable
              filterable
              :disabled="isReadonly(scope.row, field)"
              @change="value => onInputChanged(field, value)"
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
              :class="scope.column.property"
              size="mini"
              clearable
              :disabled="isReadonly(scope.row, field)"
              @change="value => onInputChanged(field, value)"
            />
            <el-input-number
              v-else-if="isNumericField(field)"
              :ref="scope.column.property"
              v-model="scope.row[scope.column.property]"
              :class="scope.column.property"
              controls-position="right"
              size="mini"
              :min="getMinValue(field)"
              :max="getMaxValue(field)"
              :disabled="isReadonly(scope.row, field)"
              @change="value => onInputChanged(field, value)"
            />
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      ref="pagination"
      background
      :current-page.sync="page"
      layout="sizes, prev, pager, next"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="itemsPerPage"
      small
      :total="queryCount"
      @size-change="changePageSize"
    />

    <el-dialog
        width="35%"
        :visible.sync="showExportDialog"
        title="Export"
    >
        <template>
            <el-form label-width="100px" size="mini">
                
                <el-form-item label="Name">
                    <el-input 
                        v-model="formExport.name"
                        placeholder="Name" 
                        style="width: 200px;"
                        size="mini" />
                </el-form-item>

                <el-form-item label="Files of Type:">
                    <el-select
                        size="mini"
                        v-model="formExport.type"
                        placeholder="Select Type Excel">
                        <el-option
                            v-for="bookType in chooseBookType"
                            :key="bookType.type"
                            :label="bookType.type"
                            :value="bookType.type"
                        />
                    </el-select>
                </el-form-item>

                <el-form-item >
                    <el-checkbox
                        v-model="checkCurrentRow"
                        @change="handleCheckCurrentRow"
                        align="center"
                        label="Export current row only?"/>
                </el-form-item>
                
            </el-form>
            <div slot="footer">
                <el-button 
                    style="margin-left: 0px;"
                    size="mini"
                    type="primary" 
                    icon="el-icon-download"
                    title="Export"
                    v-bind:disabled="buttonExport"
                    @click="actionExport">
                    Export
                </el-button>
                <el-button 
                    icon="el-icon-close" 
                    size="mini"
                    type="default" 
                    @click="closeDialog()">
                    {{ $t('entity.action.cancel') }}
                </el-button>
            </div>
        </template>
        
    </el-dialog>

  </div>
</template>

<script lang="ts" src="./grid-view.component.ts"></script>

<style lang="scss">
.el-table__fixed, .el-table__fixed-right{
  box-shadow: none;
}
.grid-view {

  .el-table {
    
    .is-error .el-input__inner {
      border-color: #ff4949;
    }

    label.el-checkbox {
      margin: 4px 0;
    }
  }

  .el-pagination {
    background: #fff;
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 5;

    .el-input--mini .el-input__inner {
      height: 22px;
    }
  }
}
</style>
