<template>
  <div class="app-container vendor-blacklist">
    <div class="toolbar">
      <el-button v-if="index" 
        class="button" 
        icon="el-icon-plus" 
        size="mini" 
        type="primary"
        @click="onCreateClicked">
        Add New
      </el-button>
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
        @click="onSaveClicked"
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
    Vendor Blacklist
    <div v-if="index">
      <el-table
        ref="mainGrid"
        v-loading="loading"
        border
        :data="blacklist"
        highlight-current-row
        size="mini"
        stripe
        style="width: 100%"
        @current-change="onCurrentRowChanged"
        @selection-change="onSelectionChanged"
      >
        <el-table-column align="center" fixed type="selection" width="48"/>
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
          sortable
        >
          <template slot-scope="{ row }">
            {{ printBlacklistType(row.type) }}
          </template>
        </el-table-column>
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
              v-if="row.attachmentId"
              class="btn-attachment"
              icon="el-icon-download"
              size="mini"
              :title="row.fileName"
              type="primary"
              @click="downloadFile(row)"
            >
              {{ row.fileName }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="Status"
          min-width="100"
          sortable
        >
          <template slot-scope="{ row }">
            {{ printStatus(row.documentStatus) }}
          </template>
        </el-table-column>
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
      <el-pagination
        ref="pagination"
        :current-page.sync="page"
        :page-size="itemsPerPage"
        :page-sizes="[10, 20, 50, 100]"
        :total="queryCount"
        background
        layout="sizes, prev, pager, next"
        small
        @size-change="changePageSize"
      ></el-pagination>
    </div>
    <vendor-blacklist-detail
      v-else
      :data="selectedRow"
    ></vendor-blacklist-detail>

    <document-action-confirm
      :action="selectedDocumentAction"
      :data="selectedRow"
      :visible.sync="showDocumentActionConfirm"
      @confirmed="confirmed"
    ></document-action-confirm>
  </div>
</template>
<script lang="ts" src="./vendor-blacklist.component.ts"></script>

<style lang="scss" scoped>
.vendor-blacklist {
  /*
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;
  */
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
