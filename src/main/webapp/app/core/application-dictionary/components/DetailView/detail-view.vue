<template>
  <div class="detail-view">
    <el-form
      ref="mainForm"
      :model="model"
      :rules="dynamicValidationSchema"
      label-width="200px"
      label-position="left"
      size="mini"
    >
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
            v-show="displayed(col.field)"
            :class="col.name"
            :label="showLabel(col.field)"
            :prop="col.name"
          >
            <el-switch
              v-if="isActivatorSwitch(col.field)"
              v-model="model.active"
              active-text="Active"
              inactive-color="#ff4949"
              class="switch"
              @change="value => onInputChanged(col.field, value)"
            />
            <el-checkbox
              v-else-if="isBooleanField(col.field)"
              v-model="model[col.name]"
              :label="col.field.name"
              :disabled="isReadonly(col.field)"
              class="checkbox"
              @change="value => onInputChanged(col.field, value)"
            />
            <password-editor
              v-else-if="isPasswordField(col.field)"
              :disabled="isNewRecord(model)"
              :user-id="model.userId"
            />
            <address-editor
              v-else-if="isAddressField(col.field)"
              v-model="model[col.name]"
              @input="value => onInputChanged(col.field, value)"
            />
            <el-select
              v-else-if="isTableDirectLink(col.field)"
              v-model="model[col.name]"
              v-loading="isLoading"
              :remote="true"
              :remote-method="fetchTableDirectData"
              clearable
              filterable
              :disabled="isReadonly(col.field)"
              class="selectRemote"
              @change="value => onInputChanged(col.field, value)"
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
              v-loading="isLoading"
              clearable
              filterable
              :disabled="isReadonly(col.field)"
              class="select"
              @change="value => onInputChanged(col.field, value)"
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
              class="input"
              :show-password="isPasswordField(col.field)"
              @change="value => onInputChanged(col.field, value)"
            />
            <el-input-number
              v-else-if="isNumericField(col.field)"
              v-model="model[col.name]"
              controls-position="right"
              :min="getMinValue(col.field)"
              :max="getMaxValue(col.field)"
              :disabled="isReadonly(col.field)"
              class="numeric"
              @change="value => onInputChanged(col.field, value)"
            />
            <el-date-picker
              v-else-if="isDateField(col.field)"
              v-model="model[col.name]"
              size="mini"
              class="date"
              :type="datePickerType(col.field)"
              :format="dateDisplayFormat(col.field)"
              :value-format="dateValueFormat(col.field)"
              :disabled="isReadonly(col.field)"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./detail-view.component.ts"></script>
<style lang="scss">
.detail-view {
  padding: 8px 24px;
}
.el-form-item--mini.el-form-item{
  margin-bottom: 0px;

  .switch, .checkbox, .selectRemote, .select, .input, .numeric, .date{
    width: 100%;
  }
}

</style>
