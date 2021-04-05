<template>
  <div class="app-container vendor-blacklist">
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
        v-show="index || selectedRow.documentStatus !== 'APV'"
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
      :data="blacklist"
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
        label="Vendor Name"
        min-width="150"
        prop="vendorName"
        show-overflow-tooltip
        sortable
      ></el-table-column>
      <el-table-column
        label="Type"
        min-width="100"
        prop="type"
        sortable
      ></el-table-column>
      <el-table-column
        label="Blacklisted Personal"
        min-width="150"
        prop="blacklistedPersonalCount"
        sortable
      ></el-table-column>
      <el-table-column
        label="Blacklist Information"
        min-width="200"
        prop="notes"
        sortable
      ></el-table-column>
      <el-table-column
        label="Document"
        min-width="150"
      >
        <template slot-scope="{ row }">
          <el-button
            v-if="row.attachment"
            class="btn-attachment"
            icon="el-icon-download"
            size="mini"
            :title="row.attachment"
            type="primary"
          >
                {{ row.attachment }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="Status"
        min-width="100"
        prop="status"
        sortable
      ></el-table-column>
      <el-table-column
        label="Approval Date"
        min-width="150"
      >
        <template slot-scope="{ row }">
          {{ row.approvalDate | formatDate(true) }}
        </template>
      </el-table-column>
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

    <vendor-blacklist-detail
      v-else
      :data="selectedRow"
    ></vendor-blacklist-detail>

    <document-action-confirm
      :action="selectedDocumentAction"
      :data="selectedRow"
      :visible.sync="showDocumentActionConfirm"
    ></document-action-confirm>
  </div>
</template>
<script lang="ts" src="./vendor-blacklist.component.ts"></script>

<style lang="scss" scoped>
.vendor-blacklist {
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
<style lang="scss" scoped>
.vendor-blacklist {
  .btn-attachment {
    width: 100%;
  }
}
</style>
