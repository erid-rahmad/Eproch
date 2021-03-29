<template>
  <div class="app-container vendor-evaluation">
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="150px"
      :model="data"
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
              v-model="data.evaluationNo"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Reviewer"
            prop="reviewer"
          >
            <el-input
              v-model="data.reviewer"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>

          <el-form-item
            label="Vendor Name"
            prop="vendorName"
          >
            <el-input
              v-model="data.vendorName"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>

          <el-form-item
            label="Aggreement No."
            prop="aggreementNo"
          >
            <el-input
              ref="aggreementNo"
              v-model="data.aggreementNo"
              clearable
              placeholder="Please Enter Aggreement No."
            >
              <el-button
                icon="el-icon-search"
                slot="append"
              >
                Search
              </el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="Evaluation Type"
            prop="type"
          >
            <el-input
              v-model="data.type"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Evaluation Period"
            prop="period"
          >
            <el-input
              v-model="data.period"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item
            label="Evaluation Date"
            prop="date"
          >
            <el-date-picker
              v-model="data.date"
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
            prop="totalScore"
          >
            <el-input
              v-model="data.totalScore"
              class="form-input"
              clearable
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row>
      <el-col
        :xs="24"
        :sm="12"
      >
        <el-table
          ref="evaluationLines"
          border
          class="evaluation-line-table"
          :data="data.evaluationLines"
          highlight-current-row
          :max-height="gridSchema.maxHeight"
          size="mini"
          stripe
        >

          <el-table-column
            label="Question Category"
            width="150"
            prop="questionCategory"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column
            label="Question"
            width="200"
            prop="question"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column
            label="Score"
            width="150"
          >
            <template slot-scope="{ row }">
              <el-rate
                v-model="row.rate"
                :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
              ></el-rate>
            </template>
          </el-table-column>

          <el-table-column
            label="Remark"
            min-width="200"
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
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>

<style lang="scss" scoped>
.evaluation-line-table .el-input.remark {
  margin: 4px 0;
}
</style>
