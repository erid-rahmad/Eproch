<template>
  <div class="app-container complaint">
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
        style="margin-left: 1px"
        type="primary"
      >
        Save
      </el-button>

      <document-action-button
        v-show="index || selectedRow.documentStatus === 'DRF'"
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
      :data="complaints"
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
        label="Report Date"
        width="130"
        sortable
      >
        <template slot-scope="{ row }">
          {{ row.dateTrx | formatDate(true) }}
        </template>
      </el-table-column>
      <el-table-column
        label="Vendor Name"
        min-width="150"
        prop="vendorName"
        show-overflow-tooltip
        sortable
      ></el-table-column>
      <el-table-column
        label="Business Category"
        min-width="200"
        prop="businessCategory"
        show-overflow-tooltip
        sortable
      ></el-table-column>
      <el-table-column
        label="Sub Category"
        min-width="150"
        prop="subCategory"
        show-overflow-tooltip
        sortable
      ></el-table-column>
      <el-table-column
        label="Notes"
        min-width="200"
        prop="message"
        sortable
      ></el-table-column>
      <el-table-column
        label="Requestor"
        min-width="100"
        prop="requestor"
      ></el-table-column>
      <el-table-column
        label="Status"
        min-width="100"
        prop="status"
        sortable
      ></el-table-column>
      <el-table-column
        fixed="right"
        width="90"
      >
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
            View
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <complaint-detail
      v-else
      :data="selectedRow"
    ></complaint-detail>

    <document-action-confirm
      :action="selectedDocumentAction"
      :data="selectedRow"
      :visible.sync="showDocumentActionConfirm"
    ></document-action-confirm>
  </div>
</template>
<script lang="ts" src="./complaint-list.component.ts"></script>

<style lang="scss" scoped>
.complaint {
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
