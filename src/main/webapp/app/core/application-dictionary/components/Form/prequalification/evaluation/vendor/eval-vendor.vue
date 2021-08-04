<template>
  <div class="app-container card-view eval-vendor">
    <div class="toolbar">
      <el-button
        v-if="isVendor && submissionPage"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="onFormClosed"
      >
        Close
      </el-button>

      <el-divider
        v-if="!mainPage"
        direction="vertical"
      ></el-divider>
    </div>

    <div class="card">
      <template v-if="mainPage">
        <el-table
          v-loading="loading"
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
        >
          <el-table-column width="50" label="No">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>

          <el-table-column
            label="Prequalification No"
            min-width="120"
            prop="prequalificationNo"
            sortable
          ></el-table-column>

          <el-table-column
            label="Title"
            min-width="200"
            prop="prequalificationName"
            show-overflow-tooltip
            sortable
          ></el-table-column>

          <el-table-column
            label="Prequalification Type"
            min-width="150"
            prop="prequalificationType"
            sortable
          >
            <template slot-scope="{ row }">
              {{row.prequalificationType == 'O'? 'Announcement' : row.prequalificationType == 'C' ? 'Invitation' : row.prequalificationType}}
            </template>
          </el-table-column>

          <!--
          <el-table-column
            label="Bidding Schedule"
            min-width="140"
          >
            <template slot-scope="{ row }">
              <el-button
                class="button"
                size="mini"
                style="width: 100%"
                @click="viewSchedule(row)"
              >
                <svg-icon name="icomoo/084-calendar"></svg-icon> View Schedule
              </el-button>
            </template>
          </el-table-column>
          -->

          <el-table-column
            label="Prequalification Status"
            min-width="140"
            sortable
          >
            <template slot-scope="{ row }">
              {{ printBiddingStatus(row.prequalificationStatus) }}
            </template>
          </el-table-column>

          <el-table-column
            v-if="isVendor"
            label="Evaluation Status"
            min-width="170"
            prop="passFail"
            sortable
          >
          </el-table-column>

          <el-table-column
            v-if="!isVendor"
            label="Submission Start Date"
            min-width="170"
            sortable
          >
            <template slot-scope="{ row }">
              {{ row.startDate | formatDate(false) }}
            </template>
          </el-table-column>

          <el-table-column
            v-if="!isVendor"
            label="Submission End Date"
            min-width="170"
            sortable
          >
            <template slot-scope="{ row }">
              {{ row.endDate | formatDate(false) }}
            </template>
          </el-table-column>

          <el-table-column
            v-if="!isVendor"
            label="Vendors"
            min-width="170"
            prop="numOfParticipants"
            sortable
          ></el-table-column>

          <el-table-column
            fixed="right"
            min-width="140"
            label="Action"
          >
            <template slot-scope="{ row }">
              <el-button
                class="button"
                size="mini"
                type="primary"
                @click="viewDetails(row)"
              >
                <!-- v-if="isStarted(row)" -->
                <svg-icon name="link"></svg-icon> Actions
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
      <evaluation-form
        v-else-if="submissionPage"
        ref="submissionForm"
        :data="selectedRow"
      ></evaluation-form>
    </div>
  </div>
</template>
<script lang="ts" src="./eval-vendor.component.ts"></script>
<style lang="scss">
.compact .eval-vendor {
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
.bidding-schedule-dialog {
  .el-dialog__body {
    padding-bottom: 20px;
  }
}
</style>
