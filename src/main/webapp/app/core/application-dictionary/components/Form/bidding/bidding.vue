<template>
  <div class="app-container bidding-list">
    <div v-if="index">
      <el-row class="header">
        <el-col :span="24">

          <el-button
            class="button"
            icon="el-icon-plus"
            size="mini"
            type="primary"
            @click="onCreateClicked"
          />

          <el-button
            class="button"
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="onDeleteClicked"
          />

          <!-- <el-button
            class="button"
            size="mini"
            type="primary"
            @click="onExportClicked"
          >
            <svg-icon name="icomoo/199-upload2"></svg-icon> Export
          </el-button> -->

        </el-col>
      </el-row>

      <el-table
        v-loading="processing"
        ref="gridData"
        border
        :data="gridData"
        :default-sort="gridSchema.defaultSort"
        :empty-text="gridSchema.emptyText"
        highlight-current-row
        size="mini"
        stripe
        style="width: 100%; height: 100%"
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
          fixed
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
          prop="documentStatus"
          sortable
        >
          <template slot-scope="{ row }">
            {{ formatDocumentStatus(row.documentStatus) }}
          </template>
        </el-table-column>

        <el-table-column
          label="Joined Vendor"
          min-width="140"
        >
          <template slot-scope="{ row }">
            <el-button
              class="button"
              size="mini"
              style="width: 100%"
              @click="viewJoinVendor(row)"
            >
              <svg-icon name="icomoo/115-users"></svg-icon> {{ randomVendorCount }}
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

    <div v-else>
      <step-form
        ref="biddingForm"
        :edit-mode="editMode"
        :data="selectedRow"
        :step-index="stepIndex"
        @close="onFormClosed"
      />
    </div>

    <el-dialog
      width="30%"
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
          min-width="50"
        >
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>

        <el-table-column
          label="Vendor"
          min-width="150"
          sortable
          prop="name"
        ></el-table-column>

        <el-table-column
          label="Address"
          min-width="100"
          show-overflow-tooltip=""
          sortable
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
.compact .bidding-list .el-dialog .el-table.vendor-list {
  td {
    height: 35px;
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

.header{
  .button {
    margin-bottom: 5px;
  }
}

.form-input {
  width: 100%;
}
</style>
