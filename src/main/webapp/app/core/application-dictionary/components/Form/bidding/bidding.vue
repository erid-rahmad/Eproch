<template>
  <div class="app-container card-view bidding-process">
    <div class="toolbar">
      <el-button
        v-if="!index"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="onFormClosed"
      >
        Close
      </el-button>
      <el-button
        v-if="index"
        class="button"
        icon="el-icon-plus"
        size="mini"
        type="primary"
        @click="onCreateClicked"
      ></el-button>

      <document-action-button
        v-show="index || editMode"
        :approved="documentApproved"
        :document-type-id="documentTypeId"
        :next-action="defaultDocumentAction"
        size="mini"
        window-type="TRANSACTION"
        @change="onDocumentActionChanged"
      ></document-action-button>
    </div>

    <div
      v-if="index"
      class="card"
    >
      <el-table
        v-loading="processing"
        ref="mainGrid"
        border
        :data="gridData"
        :default-sort="gridSchema.defaultSort"
        :empty-text="gridSchema.emptyText"
        highlight-current-row
        size="mini"
        stripe
        @current-change="onCurrentRowChanged"
        @sort-change="changeOrder"
        @selection-change="onSelectionChanged"
      >

        <el-table-column
          align="center"
          fixed
          type="selection"
          width="48"
        />

        <el-table-column
          fixed="right"
          width="200"
        >
          <template slot-scope="{ row }">
            <el-button
              icon="el-icon-search"
              size="mini"
              title="View"
              type="primary"
              :underline="false"
              @click="viewBidding(row)"
            >View</el-button>
            <el-button
              size="mini"
              title="Terminate Bidding"
              type="danger"
              :underline="false"
              @click="showTerminationDialog = true"
            >
              <svg-icon name="icomoo/183-switch"></svg-icon> Terminate
            </el-button>
          </template>
        </el-table-column>

        <el-table-column
          label="Bidding No."
          min-width="150"
          sortable
          prop="documentNo"
        ></el-table-column>

        <el-table-column
          label="Title"
          min-width="140"
          prop="name"
          show-overflow-tooltip
          sortable
        ></el-table-column>

        <el-table-column
          label="Bidding Type"
          min-width="130"
          prop="biddingTypeName"
          show-overflow-tooltip
          sortable
        ></el-table-column>

        <el-table-column
          label="Bidding Schedule"
          min-width="140"
        >
          <template slot-scope="{ row }">
            <el-button
              class="button"
              size="mini"
              style="width: 100%"
              @click="viewBidding(row, 1)"
            >
              <svg-icon name="icomoo/084-calendar"></svg-icon> View Schedule
            </el-button>
          </template>
        </el-table-column>

        <el-table-column
          label="Bidding Status"
          min-width="140"
          prop="biddingStatus"
          sortable
        ></el-table-column>

        <el-table-column
          label="Joined Vendor"
          min-width="140"
        >
          <template slot-scope="{ row }">
            <el-button
              class="button"
              size="mini"
              style="width: 100%"
              @click="viewJoinVendor"
            >
              <svg-icon name="icomoo/115-users"></svg-icon> {{ row.vendorCount }}
            </el-button>
          </template>
        </el-table-column>

        <el-table-column
          label="Modified Date"
          min-width="150"
          prop="lastModifiedDate"
          sortable
        >
          <template slot-scope="{ row }">
            {{ row.lastModifiedDate | formatDate }}
          </template>
        </el-table-column>

        <el-table-column
          label="Modified By"
          prop="lastModifiedBy"
          min-width="150"
          sortable
        ></el-table-column>

      </el-table>
      <el-pagination
        ref="pagination"
        background
        layout="sizes, prev, pager, next"
        small
        :current-page.sync="page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="itemsPerPage"
        :total="queryCount"
        @size-change="changePageSize"
      ></el-pagination>
    </div>

    <step-form
      v-else
      class="card"
      ref="biddingForm"
      :edit-mode="editMode"
      :data="selectedRow"
      :step-index="stepIndex"
    ></step-form>

    <document-action-confirm
      :action="selectedDocumentAction"
      :data="selectedRow"
      :visible.sync="showDocumentActionConfirm"
    ></document-action-confirm>

    <el-dialog
      class="joined-vendor-dialog"
      width="40%"
      :visible.sync="showJoinedVendors"
      title="Joined Vendors"
    >
      <el-table
        border
        class="vendor-list"
        :data="joinedVendors"
        highlight-current-row
        size="mini"
      >
        <el-table-column
          label="No."
          width="50"
        >
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>

        <el-table-column
          label="Vendor"
          min-width="150"
          show-overflow-tooltip
          sortable
          prop="name"
        ></el-table-column>

        <el-table-column
          label="Address"
          min-width="200"
          show-overflow-tooltip
          prop="location"
        ></el-table-column>

      </el-table>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="showJoinedVendors = false"
        >
          Close
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="30%"
      :visible.sync="showTerminationDialog"
      title="Terminate Bidding Confirmation"
    >
      <p>Are you sure you want to terminate the bidding process?</p>
      <p>Please type the reason:</p>
      <el-input
        v-model="terminationReason"
        type="textarea"
        :autosize="{ minRows: 2, maxRows: 4}"
      ></el-input>
      <div slot="footer">
        <el-button
          style="margin-left: 0px;"
          size="mini"
          icon="el-icon-close"
          @click="showTerminationDialog = false"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          style="margin-left: 0px;"
          size="mini"
          type="danger"
          @click="showTerminationDialog = false"
        >
          <svg-icon name="icomoo/183-switch"></svg-icon> Terminate
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./bidding.component.ts"></script>

<style lang="scss">
.compact .bidding-process {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  .joined-vendor-dialog .el-table.vendor-list {
    td {
      height: 35px;
    }
  }
}

.el-table__fixed, .el-table__fixed-right{
  box-shadow: none;
}

.main {
  padding: 0px;

  .button {
    width: 100%;
  }
}

.toolbar {
  padding: 4px 16px;
}

.form-input {
  width: 100%;
}
</style>
