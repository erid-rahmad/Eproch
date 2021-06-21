<template>
  <el-form
    v-loading="loading"
    ref="contractInfoForm"
    class="contract-info"
    :disabled="readOnly"
    :label-position="formSettings.labelPosition"
    :label-width="formSettings.labelWidth"
    :model="contract"
    :rules="validationSchema"
    :size="formSettings.size"
  >
    <el-row :gutter="gutterSize">
      <el-col
        :xs="24"
        :sm="20"
        :md="16"
        :lg="12"
        :xl="8"
      >
        <el-form-item label="Contract No.">
          <el-input
            v-model="contract.documentNo"
            ref="documentNo"
            clearable
            :disabled="editMode && !!contract.documentNo"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="Title"
          prop="name"
        >
          <el-input
            v-model="contract.name"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="Purpose">
          <el-radio-group v-model="contract.purpose">
            <el-radio label="P">Project</el-radio>
            <el-radio label="N">Non Project</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="contract.forPriceConfirmation">
            For Price Confirmation
          </el-checkbox>
        </el-form-item>
        <el-form-item
          label="PiC"
          prop="picUserId"
        >
          <ad-input-lookup
            v-model="contract.picUserId"
            placeholder="Select PiC"
            table-name="ad_user"
            :label-fields="['userLogin']"
            :query="['employee.equals=true']"
          ></ad-input-lookup>
        </el-form-item>
        <el-form-item
          label="Department"
          prop="costCenterId"
        >
          <ad-input-lookup
            v-model="contract.costCenterId"
            placeholder="Select Department"
            table-name="c_cost_center"
          ></ad-input-lookup>
        </el-form-item>
        <el-form-item
          label="Vendor Name"
          prop="vendorId"
        >
          <ad-input-lookup
            v-model="contract.vendorId"
            placeholder="Select Vendor"
            lookup-by-field="name"
            table-name="c_vendor"
          ></ad-input-lookup>
        </el-form-item>
        <el-form-item
          label="Start Date"
          prop="startDate"
        >
          <el-date-picker
            v-model="contract.startDate"
            clearable
            :format="dateDisplayFormat"
            type="date"
            :value-format="dateValueFormat"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="Expiration Date">
          <el-date-picker
            v-model="contract.expirationDate"
            clearable
            :format="dateDisplayFormat"
            type="date"
            :value-format="dateValueFormat"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="Evaluation Type">
          <ad-input-lookup
            v-model="contract.vendorEvaluationId"
            placeholder="Select Evaluation Type"
            table-name="c_vendor_evaluation"
          ></ad-input-lookup>
        </el-form-item>
        <el-form-item label="Evaluation Period">
          <ad-input-list
            v-model="contract.evaluationPeriod"
            placeholder="Select Evaluation Period"
            reference-key="vendorEvaluationPeriod"
          ></ad-input-list>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts" src="./contract-info.component.ts"></script>
