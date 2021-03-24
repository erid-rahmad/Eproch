<template>
  <div class="app-container">
    <el-table
      border
      :data="costEvaluations"
      highlight-current-row
      size="mini"
      stripe
      style="width: 100%"
    >
      <el-table-column
        label="No"
        min-width="50"
      >
        <template slot-scope="{ $index }">
          {{ $index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        label="Cost Evaluation"
        min-width="100"
        prop="documentNo"
        sortable
      ></el-table-column>
      <el-table-column
        label="Biding No."
        min-width="100"
        prop="biddingNo"
        sortable
      ></el-table-column>
      <el-table-column
        label="Bidding Title"
        min-width="200"
        prop="biddingTitle"
        sortable
      ></el-table-column>
      <el-table-column
        label="Bidding Type"
        min-width="100"
        prop="biddingTypeName"
        sortable
      ></el-table-column>
      <el-table-column
        label="Aproval Status"
        min-width="100"
        prop="documentStatus"
        sortable
      ></el-table-column>
      <el-table-column
        label="Modified Date"
        min-width="100"
        prop="lastModifiedDate"
        sortable
      ></el-table-column>
      <el-table-column
        label="Modified By"
        min-width="100"
        prop="lastModifiedBy"
      ></el-table-column>
      <el-table-column min-width="100">
        <template slot="header">
          &nbsp;
        </template>
        <el-button
          icon="el-icon-info"
          size="mini"
          type="primary"
        >
          Action
        </el-button>
      </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>

<style lang="scss" scoped>
.compact .el-table tbody .el-button {
  margin: 5px 0;
}
</style>
