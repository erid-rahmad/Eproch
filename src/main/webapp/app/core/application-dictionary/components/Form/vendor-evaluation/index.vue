<template>
  <div class="app-container card-view vendor-evaluation">
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
        v-if="index"
        icon="el-icon-plus"
        size="mini"
        type="primary"
        @click="viewDetail()"
      ></el-button>

      <el-button
        v-if="!index && isDraft"
        icon="el-icon-check"
        :loading="loading"
        size="mini"
        type="primary"
        @click="onSaveClicked"
      >
        Save
      </el-button>
    </div>

    <div class="card">

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
          label="Contract No."
          min-width="100"
          prop="contractNo"
          sortable
        ></el-table-column>
        <el-table-column
          label="Contract Title"
          min-width="150"
          prop="contractName"
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
          prop="score"
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
        ref="evaluationForm"
        :data="selectedRow"
        :loading.sync="loading"
      ></vendor-evaluation-detail>
    </div>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
<style lang="scss" scoped>
.vendor-evaluation {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  .toolbar {
    padding: 4px 16px 0;
  }

}
.compact .el-table tbody .el-button {
  margin: 5px 0;
}
</style>
