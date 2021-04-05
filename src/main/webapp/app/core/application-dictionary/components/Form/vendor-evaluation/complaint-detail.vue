<template>
  <div class="complaint-detail">
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
              :disabled="readOnly"
            >
              <el-option
                v-for="item in vendors"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Report Date">
            <el-date-picker
              v-model="mainForm.dateTrx"
              clearable
              :disabled="readOnly"
              :format="dateDisplayFormat"
              placeholder="Pick a date"
              size="mini"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="Department">
            <el-select
              v-model="mainForm.costCenterId"
              :disabled="readOnly"
            >
              <el-option
                v-for="item in costCenters"
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
          <el-form-item label="Requestor">
            <el-input
              v-model="mainForm.requestor"
              :disabled="readOnly"
            ></el-input>
          </el-form-item>
          <el-form-item label="Contract No.">
            <el-input
              v-model="mainForm.contractNo"
              :disabled="readOnly"
            ></el-input>
          </el-form-item>
          <el-form-item label="Type">
            <el-input
              v-model="mainForm.type"
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
          <el-form-item label="Message">
            <el-input
              v-model="mainForm.message"
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
<script lang="ts" src="./complaint-detail.component.ts"></script>
<style lang="scss">
.complaint-detail {
  padding: 4px 8px;
}
</style>
