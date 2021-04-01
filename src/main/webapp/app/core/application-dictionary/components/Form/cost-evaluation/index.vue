<template>
  <div class="app-container cost-evaluation">
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

      <document-action-button
        v-show="index || selectedRow.documentStatus === 'SMT'"
        :approved="selectedRow.approved"
        :document-type-id="documentTypeId"
        :next-action="defaultDocumentAction"
        size="mini"
        window-type="TRANSACTION"
        @change="onDocumentActionChanged"
      ></document-action-button>
    </div>

    <el-table
      v-if="index"
      ref="mainGrid"
      border
      :data="costEvaluations"
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
        sortable
      >
        <template slot-scope="{ row }">
          {{ printStatus(row.documentStatus) }}
        </template>
      </el-table-column>
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

    <cost-evaluation-detail
      v-else
      :approval="selectedRow.documentStatus === 'SMT'"
      :data="selectedRow"
    ></cost-evaluation-detail>

    <document-action-confirm
      :action="selectedDocumentAction"
      :data="selectedRow"
      :visible.sync="showDocumentActionConfirm"
    ></document-action-confirm>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>

<style lang="scss" scoped>
.cost-evaluation {
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
