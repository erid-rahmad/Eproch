<template>
  <div class="app-container card-view vendor-contract">
    <div class="toolbar">
      <el-button
        v-if="mainPage"
        icon="el-icon-plus"
        size="mini"
        type="primary"
        @click="viewDetails()"
      ></el-button>

      <el-button
        v-if="mainPage"
        :disabled="selectedRow.documentStatus !== 'DRF'"
        icon="el-icon-delete"
        size="mini"
        title="Delete"
        type="danger"
        @click="onDeleteClicked"
      ></el-button>

      <el-button
        v-if="detailPage"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="onCloseClicked"
      >
        Close
      </el-button>

      <el-button
        v-if="detailPage && isDraft && detailTabName === 'INF'"
        size="mini"
        type="primary"
        @click="onSaveClicked"
      >
        <svg-icon name="icomoo/273-checkmark"></svg-icon> Save
      </el-button>

      <el-button
        :disabled="isDraft"
        size="mini"
        type="danger"
        @click="onTerminateClicked"
      >
        <svg-icon name="icomoo/273-checkmark"></svg-icon> Terminate
      </el-button>
    </div>

    <div class="card">
      <template v-if="mainPage">
        <el-table
          v-loading="loading"
          ref="mainGrid"
          border
          :data="gridData"
          highlight-current-row
          size="mini"
          stripe
          @current-change="onCurrentRowChanged"
          @sort-change="changeOrder"
        >
          <el-table-column
            fixed
            label="No"
            width="50"
          >
            <template v-slot="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>

          <el-table-column
            label="Contract No"
            min-width="120"
            prop="documentNo"
            sortable
          ></el-table-column>

          <el-table-column
            label="Title"
            min-width="200"
            prop="name"
            show-overflow-tooltip
            sortable
          ></el-table-column>

          <el-table-column
            label="Type"
            min-width="150"
            prop="documentTypeName"
            sortable
          ></el-table-column>

          <el-table-column
            label="Price Confirmation"
            min-width="200"
            sortable
          >
            <template v-slot="{ row }">
              <el-checkbox
                v-model="row.forPriceConfirmation"
                disabled
              ></el-checkbox>
            </template>
          </el-table-column>

          <el-table-column
            label="Vendor Name"
            width="200"
            prop="vendorName"
            show-overflow-tooltip
            sortable
          ></el-table-column>

          <el-table-column
            label="Start Date"
            min-width="150"
            sortable
          >
            <template v-slot="{ row }">
              {{ row.startDate | formatDate(true) }}
            </template>
          </el-table-column>

          <el-table-column
            label="Expiration Date"
            min-width="150"
            sortable
          >
            <template v-slot="{ row }">
              {{ row.expirationDate | formatDate(true) }}
            </template>
          </el-table-column>

          <el-table-column
            label="Modified Date"
            min-width="150"
            sortable
          >
            <template v-slot="{ row }">
              {{ row.lastModifiedDate | formatDate }}
            </template>
          </el-table-column>

          <el-table-column
            label="Modified by"
            min-width="150"
            prop="lastModifiedBy"
            sortable
          ></el-table-column>

          <el-table-column
            fixed="right"
            width="180"
            label="Action"
          >
            <template v-slot="{ row }">
              <el-button
                size="mini"
                type="primary"
                @click="viewDetails(row)"
              >
                <svg-icon name="link"></svg-icon> View
              </el-button>
            </template>
          </el-table-column>

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
      </template>

      <contract-detail
        v-else-if="detailPage"
        ref="detailPage"
        :data.sync="selectedRow"
        :tab.sync="detailTabName"
      ></contract-detail>
    </div>

    <el-dialog
      :visible.sync="terminationConfirmationVisible"
      title="Terminate Contract"
      width="30%"
    >
        <p>Are you sure you want to terminate the contract?</p>
        <div slot="footer">
          <el-button
            icon="el-icon-close"
            size="mini"
            style="margin-left: 0px;"
            @click="terminationConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            size="mini"
            style="margin-left: 0px;"
            type="danger"
            @click="terminateContract"
          >
            <svg-icon name="icomoo/183-switch"></svg-icon>
            Terminate
          </el-button>
        </div>
    </el-dialog>

    <el-dialog
      width="30%"
      :visible.sync="deleteConfirmationVisible"
      :title="$t('entity.delete.title')"
    >
      <template>
        <span>Are you sure to delete the selected record?</span>
        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close"
            @click="deleteConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-delete"
            type="danger"
            @click="deleteRecord"
          >
            {{ $t('entity.action.delete') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./vendor-contract.component.ts"></script>
<style lang="scss">
.compact .vendor-contract {

  .el-table--mini {

    th,
    td {
        height: 35px;
    }
  }

  .toolbar {
    padding: 4px 16px 0;

    .el-button + .el-button {
      margin-left: 8px;
    }
  }
}
</style>