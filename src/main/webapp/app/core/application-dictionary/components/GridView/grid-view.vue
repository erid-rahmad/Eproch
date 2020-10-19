<template>
  <div ref="tableWrapper" class="grid-view">
    <el-table
      v-loading="processing"
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
        align="center"
        fixed
        type="selection"
        width="48"
      >
      </el-table-column>
      <el-table-column
        fixed
        width="48"
      >
        <template slot-scope="{ row }">
          <el-link
            @click="activateInlineEditing(row)"
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
        :label="field.name"
        :prop="field.adColumn.name"
        :width="getFieldWidth(field)"
        min-width="128"
        show-overflow-tooltip
        :sortable="editing ? false: 'custom'"
      >
        <template slot-scope="{ row, column }">
          <el-switch
            v-if="isActivatorSwitch(field)"
            :ref="column.property"
            v-model="row[column.property]"
            :class="column.property"
            class="switch"
            :disabled="!row.editing || isReadonly(row, field)"
            @change="value => onInputChanged(field, value)"
          />
          <el-checkbox
            v-else-if="isBooleanField(field)"
            v-show="displayed(row, field)"
            :ref="column.property"
            v-model="row[column.property]"
            :class="column.property"
            class="checkbox"
            :disabled="!row.editing || isReadonly(row, field)"
            @change="value => onInputChanged(field, value)"
          />
          <span
            v-else-if="!row.editing"
            v-show="displayed(row, field)"
            :class="column.property"
          >
            {{ getFieldValue(row, field) }}
          </span>
          <el-tooltip
            v-else
            :disabled="!hasError(field)"
            :content="getErrorMessage(field)"
          >
            <el-select
              v-if="isTableDirectLink(field)"
              v-show="displayed(row, field)"
              :ref="column.property"
              v-model="row[column.property]"
              :class="column.property"
              class="selectRemote"
              :remote="true"
              :remote-method="fetchTableDirectData"
              size="mini"
              clearable
              filterable
              :disabled="isReadonly(row, field)"
              @change="value => onInputChanged(field, value)"
              @clear="onInputChanged(field)"
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
              v-show="displayed(row, field)"
              :ref="column.property"
              v-model="row[column.property]"
              :class="column.property"
              class="select"
              size="mini"
              clearable
              filterable
              :disabled="isReadonly(row, field)"
              @change="value => onInputChanged(field, value)"
              @clear="onInputChanged(field)"
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
              v-show="displayed(row, field)"
              :ref="column.property"
              v-model="row[column.property]"
              :class="column.property"
              class="input"
              size="mini"
              clearable
              :disabled="isReadonly(row, field)"
              @change="value => onInputChanged(field, value)"
            />
            <el-input-number
              v-else-if="isNumericField(field)"
              v-show="displayed(row, field)"
              :ref="column.property"
              v-model="row[column.property]"
              :class="column.property"
              class="numeric"
              controls-position="right"
              size="mini"
              :min="getMinValue(field)"
              :max="getMaxValue(field)"
              :disabled="isReadonly(row, field)"
              @change="value => onInputChanged(field, value)"
            />
            <el-date-picker
              v-else-if="isDateField(field)"
              :ref="column.property"
              v-model="row[column.property]"
              size="mini"
              :type="datePickerType(field)"
              :format="dateDisplayFormat(field)"
              :value-format="dateValueFormat(field)"
              :disabled="isReadonly(row, field)"
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

    .switch, .checkbox, .selectRemote, .select, .input, .numeric, .date{
      width: 100%;
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
