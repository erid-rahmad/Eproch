<template>
  <div class="score-detail">
    <el-form
      ref="mainForm"
      label-position="left"
      label-width="200px"
      :model="mainForm"
      size="mini"
    >
      <el-row
        :gutter="columnSpacing"
        style="margin-top: 16px"
      >
        <el-col :span="24">
          <el-form-item label="Vendor Name">
            <el-input
              v-model="mainForm.vendorName"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="Total Score">
            <el-input
              v-model="mainForm.totalScore"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-table
        border
        :data="tableData"
        highlight-current-row
        size="mini"
        stripe
        style="margin-top: 16px; width: 100%"
      >
        <el-table-column
          label="No"
          width="50"
        >
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          label="Criteria"
          prop="criteria"
          min-width="150"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="Sub Criteria"
          prop="subCriteria"
          min-width="150"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="Percentage"
          prop="percentage"
          min-width="100"
        ></el-table-column>
        <el-table-column
          label="Score"
          prop="score"
          min-width="100"
        ></el-table-column>
      </el-table>
    </el-form>
  </div>
</template>

<script lang="ts" src="./score-detail.component.ts"></script>

<style lang="scss">
.compact .score-detail .el-table--mini td {
  height: 35px;
}
</style>
