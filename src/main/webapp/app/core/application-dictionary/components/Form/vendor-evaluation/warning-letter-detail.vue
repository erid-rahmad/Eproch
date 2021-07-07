<template>
  <div class="warning-letter-detail">
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="200px"
      :model="mainForm"
      size="mini"
    >
      <el-row :gutter="columnSpacing">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Vendor Name">
            <el-select
              v-model="mainForm.vendorId"
              class="form-input"
              clearable
              filterable
              :disabled="readOnly"
              placeholder="Select"
            >
              <el-option
                v-for="item in vendorOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Report Date">
            <el-date-picker
              v-model="mainForm.reportDate"
              clearable
              :disabled="readOnly"
              :format="dateDisplayFormat"
              placeholder="Pick a date"
              size="mini"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="Warning Type">
            <el-select
              v-model="mainForm.warningType"
              :disabled="readOnly"
            >
              <el-option
                v-for="item in warningTypes"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Start Date">
            <el-date-picker
              v-model="mainForm.startDate"
              clearable
              :disabled="readOnly"
              :format="dateDisplayFormat"
              placeholder="Pick a date"
              size="mini"
              style="width: 100%"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="End Date">
            <el-date-picker
              v-model="mainForm.endDate"
              clearable
              :disabled="readOnly"
              :format="dateDisplayFormat"
              placeholder="Pick a date"
              size="mini"
              style="width: 100%"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="Location">
            <el-input
              v-model="mainForm.location"
              :disabled="readOnly"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="columnSpacing">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Warning Message">
            <el-input
              v-model="mainForm.warning"
              clearable
              :disabled="readOnly"
              :rows="7"
              type="textarea"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./warning-letter-detail.component.ts"></script>
<style lang="scss">
.warning-letter-detail {

}
</style>
