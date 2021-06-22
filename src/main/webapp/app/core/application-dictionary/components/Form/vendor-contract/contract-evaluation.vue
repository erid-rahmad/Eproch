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
        width="150"
      ></el-table-column>

      <el-table-column
        label="Total Score"
        prop="totalScore"
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
  </div>
</template>
<script lang="ts" src="./contract-evaluation.component.ts"></script>
