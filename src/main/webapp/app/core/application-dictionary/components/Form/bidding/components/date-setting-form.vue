<template>
  <el-dialog
    class="date-setting-form"
    width="30%"
    :visible="visible"
    :title="dialogTitle"
    @close="onClose"
    @open="onOpen"
  >
    <el-form
      v-loading="loading"
      ref="dateSettingForm"
      label-position="left"
      label-width="150px"
      :model="formData"
      :rules="validationSchema"
      size="mini"
    >
      <el-form-item label="Event Name">
        <el-input
          :value="title"
          disabled
        ></el-input>
      </el-form-item>
      <el-form-item
        label="Schedule"
        prop="dateRange"
      >
        <el-date-picker
          v-model="formData.dateRange"
          :format="dateDisplayFormat"
          end-placeholder="End Datetime"
          range-separator="To"
          size="mini"
          start-placeholder="Start Datetime"
          type="datetimerange"
          @change="onDateRangeChanged"
        ></el-date-picker>
      </el-form-item>
      <el-form-item
        label="Status"
        prop="status"
      >
        <el-select
          v-model="formData.status"
          clearable
          style="width: 100%"
        >
          <el-option
            v-for="item in eventStatuses"
            :key="item.id"
            :label="item.name"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button
        icon="el-icon-close"
        size="mini"
        @click="onClose"
      >
        {{ $t('entity.action.cancel') }}
      </el-button>
      <el-button
        icon="el-icon-check"
        size="mini"
        style="margin-left: 0px;"
        type="primary"
        @click="save"
      >
        {{ $t('entity.action.save') }}
      </el-button>
    </div>
  </el-dialog>
</template>
<script lang="ts" src="./date-setting-form.component.ts"></script>