<template>
  <div class="detail-view">
    <el-form
      ref="mainForm"
      v-loading="isLoading"
      :model="model"
      :rules="validationSchema"
      label-width="128px"
      label-position="left"
      size="small"
    >
      <el-row
        v-if="model.active !== void 0"
        :gutter="24"
      >
        <el-col :span="6">
          <el-form-item label="">
            <el-switch
              v-model="model.active"
              active-text="Active"
              inactive-color="#ff4949"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row
        v-for="(row, i) in rows"
        :key="`row_${i}`"
        :gutter="24"
      >
        <el-col
          v-for="(col, j) in row.columns"
          :key="`row_${i}_col_${j}`"
          :span="col.span"
        >
          <el-form-item
            :label="col.field.name"
            :prop="col.name"
          >
            <el-checkbox
              v-if="isBooleanField(col.field)"
              v-model="model[col.name]"
              :label="col.field.name"
              :disabled="isReadonly(col.field)"
            />
            <el-select
              v-else-if="isTableDirectLink(col.field)"
              v-model="model[col.name]"
              :remote="true"
              :remote-method="fetchTableDirectData"
              clearable
              filterable
              :disabled="isReadonly(col.field)"
              @focus="setTableDirectReference(col.field)"
            >
              <el-option
                v-for="item in referenceListItems(col.field)"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
            <el-select
              v-else-if="hasReferenceList(col.field)"
              v-model="model[col.name]"
              clearable
              filterable
              :disabled="isReadonly(col.field)"
            >
              <el-option
                v-for="item in referenceListItems(col.field)"
                :key="item.value"
                :label="item.name"
                :value="item.value"
              />
            </el-select>
            <el-input
              v-else-if="isStringField(col.field)"
              v-model="model[col.name]"
              clearable
              :minlength="getMinLength(col.field)"
              :maxlength="getMaxLength(col.field)"
              :disabled="isReadonly(col.field)"
            />
            <el-input-number
              v-else-if="isNumericField(col.field)"
              v-model="model[col.name]"
              controls-position="right"
              :min="getMinValue(col.field)"
              :max="getMaxValue(col.field)"
              :disabled="isReadonly(col.field)"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-dialog
        width="35%"
        :visible.sync="showDeleteDialog"
        :title="$t('entity.delete.title')"
    >
      <template>
        <span>Are you sure to delete the record?</span>
        <div slot="footer">
          <el-button 
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-delete" 
            type="danger" 
            @click="doDelete"
          >
            {{ $t('entity.action.delete') }}
          </el-button>
          <el-button 
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close" 
            @click="closeDeleteDialog"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./detail-view.component.ts"></script>
<style lang="scss">
.detail-view {
  padding: 8px 24px;
}
</style>
