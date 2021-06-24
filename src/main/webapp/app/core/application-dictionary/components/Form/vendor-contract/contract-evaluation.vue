<template>
  <div class="contract-evaluation">
    <el-table
      v-loading="loading"
      ref="evaluations"
      highlight-current-row
      border
      stripe
      size="mini"
      :data="evaluations"
    >
      <el-table-column
        width="50" label="No.">
        <template v-slot="{ $index }">
          {{ $index + 1 }}
        </template>
      </el-table-column>

      <el-table-column
        label="Evaluation No."
        min-width="200"
        prop="documentNo"
        show-overflow-tooltip
        sortable
      ></el-table-column>

      <el-table-column
        label="Status"
        show-overflow-tooltip
        sortable
        prop="documentStatus"
        width="150"
      ></el-table-column>

      <el-table-column
        label="Total Score"
        prop="score"
        show-overflow-tooltip
        sortable
        width="150"
      ></el-table-column>

      <el-table-column
        align="center"
        fixed="right"
        width="156"
      >
        <template slot="header">&nbsp;</template>
        <template v-slot="{ row }">
          <el-button
            icon="el-icon-search"
            size="mini"
            type="primary"
            @click="viewDetails(row)"
          >View</el-button>
        </template>
      </el-table-column>
    </el-table>
      <el-dialog
          :visible.sync="vendorEvaluationDetail"

          width="80%">
      <el-form class="card"
               ref="form"
               label-width="180px"
               label-position="left"
               align="left"
               size="mini">
          <el-row :gutter="24">
              <el-col :span="12">
          <el-form-item label="Document No">
              {{selectRow.documentNo}}
          </el-form-item>
          <el-form-item label="Document Status">
              {{selectRow.documentStatus}}
          </el-form-item>
          <el-form-item label="Contract No">
              {{selectRow.contractNo}}
          </el-form-item>
          <el-form-item label="Contract Name">
              {{selectRow.contractName}}
          </el-form-item>
              </el-col>
              <el-col :span="12">
          <el-form-item label="Evaluation Type Name">
              {{selectRow.evaluationTypeName}}
          </el-form-item>
          <el-form-item label="Reviewer User Name">
              {{selectRow.reviewerUserName}}
          </el-form-item>
          <el-form-item label="Vendor Name">
              {{selectRow.vendorName}}
          </el-form-item>
          <el-form-item label="Score">
              {{selectRow.score}}
          </el-form-item>
              </el-col>
          </el-row>
      </el-form>
      </el-dialog>
  </div>
</template>
<script lang="ts" src="./contract-evaluation.component.ts"></script>

<style lang="scss" scoped>
.el-dialog{ background-color: #67C23A }
</style>
