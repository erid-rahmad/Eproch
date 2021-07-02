<template>
  <div class="app-container card-view vendor-confirmation">
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
        v-if="!index && isVendor && selectedRow.status==='P'"
        icon="el-icon-check"
        size="mini"
        type="primary"
        @click="accept"
      >
        Accept
      </el-button>
      <el-button
        v-if="!index && isVendor && selectedRow.status==='P'"
        icon="el-icon-edit"
        size="mini"
        type="warning"
        @click="revise"
      >
        Need Revision
      </el-button>
    </div>

    <div class="card">
      <div v-if="index">
        <el-table
          ref="mainGrid"
          border
          v-loading="loading"
          :data="vendorConfirmations"
          max-height="550"
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
            label="Bidding No."
            min-width="130"
            prop="biddingNo"
            sortable
          ></el-table-column>
          <el-table-column
            label="Bidding Title"
            min-width="200"
            prop="biddingTitle"
            show-overflow-tooltip
            sortable
          ></el-table-column>
          <el-table-column
            v-if="!isVendor"
            label="Bidding Type"
            min-width="150"
            prop="biddingTypeName"
            sortable
          ></el-table-column>
          <el-table-column
            label="Bidding Status"
            min-width="150"
            sortable
          ><template slot-scope="{ row }">{{formatBiddingStatus(row.biddingStatus)}}</template>
          </el-table-column>
          <el-table-column
            label="Amount"
            min-width="150"
            sortable
          >
            <template slot-scope="{ row }">
              {{ row.amount | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            label="Currency"
            min-width="100"
            prop="currencyName"
            sortable
          ></el-table-column>
          <el-table-column
            v-if="isVendor"
            label="Status"
            min-width="100"
          >
            <template slot-scope="{ row }">{{formatConfirmationStatus(row.status)}}</template>
          </el-table-column>
          <el-table-column
            v-else
            label="Selected Winners"
            min-width="130"
            prop="selectedWinners"
          ></el-table-column>
          <el-table-column
            fixed="right"
            min-width="100"
          >
            <template slot="header">
              &nbsp;
            </template>
            <template slot-scope="{ row }">
              <el-button
                icon="el-icon-search"
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
        />
      </div>
      <template v-else>
        <confirmation-form
          v-if="isVendor"
          :data="contract"
        ></confirmation-form>

        <vendor-confirmation-detail
          v-else
          :data="selectedRow"
        ></vendor-confirmation-detail>
      </template>
    </div>

    <el-dialog
      v-if="!index && isVendor"
      width="30%"
      :visible.sync="showConfirmationForm"
      :title="documentAction"
    >
      <el-form
        ref="confirmationForm"
        label-position="left"
        label-width="96px"
        :model="contract"
        size="mini"
      >
        <el-form-item label="Reason">
          <el-input
            v-model="contract.reason"
            :rows="7"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="clearReason"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          :loading="submitting"
          size="mini"
          type="primary"
          @click="submit"
        >
          Submit
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./index.component.ts"></script>

<style lang="scss" scoped>
.vendor-confirmation {
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
