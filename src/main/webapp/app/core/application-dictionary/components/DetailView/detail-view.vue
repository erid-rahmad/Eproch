<template>
  <div class="detail-view">
    <el-form
      ref="mainForm"
      v-loading="isFetching"
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
    <!-- <vue-form-json-schema
      v-if="!isFetching"
      v-model="model"
      :schema="schema"
      :ui-schema="uiSchema"
      :options="options"
      @state-change="onChangeState"
      @validated="onValidated"
    /> -->
  </div>
</template>
<script lang="ts" src="./detail-view.component.ts"></script>
