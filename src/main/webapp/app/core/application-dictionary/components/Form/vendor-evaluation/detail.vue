<template>
  <div class="vendor-evaluation-detail card-view">
    <el-form
      v-loading="loading"
      ref="mainForm"
      label-position="left"
      label-width="150px"
      :model="evaluation"
      :rules="validationSchema"
      size="mini"
    >
      <el-row :gutter="24">
        <el-col :span="8">
          <el-form-item
            label="Evaluation No."
            prop="evaluationNo"
          >
            <el-input
              v-model="evaluation.documentNo"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Reviewer"
            prop="reviewer"
          >
            <ad-input-lookup
              v-model="evaluation.reviewerUserId"
              placeholder="Select Reviewer"
              table-name="ad_user"
              :label-fields="['userLogin']"
              :query="['employee.equals=true']"
            ></ad-input-lookup>
          </el-form-item>

          <el-form-item
            label="Vendor Name"
            prop="vendorId"
          >
            <ad-input-lookup
              v-model="evaluation.vendorId"
              placeholder="Select Vendor"
              lookup-by-field="name"
              table-name="c_vendor"
            ></ad-input-lookup>
          </el-form-item>

          <el-form-item
            label="Contract No."
            prop="contractId"
          >
            <ad-input-lookup
              v-model="evaluation.contractId"
              placeholder="Select Contract"
              lookup-by-field="name"
              table-name="m_contracts"
              @change="onContractIdChanged"
            ></ad-input-lookup>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="Evaluation Type"
            prop="evaluationType"
          >
            <el-input
              v-model="evaluation.evaluationTypeName"
              class="form-input"
              clearable
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Evaluation Period"
            prop="evaluationPeriod"
          >
            <ad-input-list
              v-model="evaluation.contractEvaluationPeriod"
              disabled
              placeholder="Select Evaluation Period"
              reference-key="vendorEvaluationPeriod"
            ></ad-input-list>
          </el-form-item>
          <el-form-item
            label="Evaluation Date"
            prop="evaluationDate"
          >
            <el-date-picker
              v-model="evaluation.evaluationDate"
              class="form-input"
              clearable
              :format="dateDisplayFormat"
              placeholder="Pick a date"
              size="mini"
              style="width: 100%"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="Total Score"
            prop="score"
          >
            <el-input-number
              v-model="evaluation.score"
              class="form-input"
              clearable
              controls-position="right"
              disabled
              :precision="2"
            ></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-table
      ref="lines"
      border
      class="evaluation-line-table"
      :data="lines"
      highlight-current-row
      size="mini"
      stripe
    >

      <el-table-column
        label="Question Category"
        min-width="80"
        prop="cQuestionCategoryName"
        show-overflow-tooltip
      ></el-table-column>

      <el-table-column
        label="Question"
        min-width="180"

        prop="question"
        show-overflow-tooltip
      ></el-table-column>

      <el-table-column
        label="Score %"
        min-width="250"

      >
        <template slot-scope="{ row }">
          <el-slider v-model="row.score" :marks="marks" @change="onRateChanged"></el-slider>
        </template>
      </el-table-column>

      <el-table-column
        label="Remark"
        min-width="150"
      >
        <template slot-scope="{ row }">
          <el-input
            v-model="row.remark"
            class="remark"
            clearable
            :maxlength="255"
            size="mini"
          ></el-input>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts" src="./detail.component.ts"></script>

<style lang="scss" scoped>
.evaluation-line-table .el-input.remark {
  margin: 4px 0;
}
</style>
