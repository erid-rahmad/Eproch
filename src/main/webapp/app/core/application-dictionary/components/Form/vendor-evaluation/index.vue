<template>
  <div class="app-container vendor-evaluation">
    <div class="toolbar">
      <el-button
        v-if="!index"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="closeDetail"
      >
        Close
      </el-button>

      <el-button
        v-if="!index && isDraft"
        icon="el-icon-check"
        size="mini"
        type="primary"
      >
        Save
      </el-button>
    </div>

    <el-table
      v-if="index"
      ref="mainGrid"
      border
      :data="vendorEvaluations"
      highlight-current-row
      size="mini"
      stripe
      style="width: 100%"
      @current-change="onCurrentRowChanged"
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
        label="Aggreement No."
        min-width="100"
        prop="aggreementNo"
        sortable
      ></el-table-column>
      <el-table-column
        label="Aggrement Title"
        min-width="150"
        prop="aggreementTitle"
        show-overflow-tooltip
        sortable
      ></el-table-column>
      <el-table-column
        label="Evaluation Date"
        min-width="100"
        sortable
      >
        <template slot-scope="{ row }">
          {{ row.evaluationDate | formatDate(true) }}
        </template>
      </el-table-column>
      <el-table-column
        label="Total Score"
        min-width="100"
        prop="totalScore"
        sortable
      ></el-table-column>
      <el-table-column
        label="Status"
        min-width="100"
        sortable
      >
        <template slot-scope="{ row }">
          {{ printStatus(row.documentStatus) }}
        </template>
      </el-table-column>
      <el-table-column min-width="100">
        <template slot="header">
          &nbsp;
        </template>
        <template slot-scope="{ row }">
          <el-button
            icon="el-icon-edit"
            size="mini"
            type="primary"
            @click="viewDetail(row)"
          >
            {{ row.documentStatus === 'APV' ? 'View' : 'Edit' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <vendor-evaluation-detail
      v-else
      :data="selectedRow"
    ></vendor-evaluation-detail>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>

<style lang="scss" scoped>
.vendor-evaluation {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  .toolbar {
    padding: 4px;
  }

}
.compact .el-table tbody .el-button {
  margin: 5px 0;
}
</style>
