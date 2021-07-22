<template>
  <div class="app-container card-view registered-prequalification-list">
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

      <el-button
        v-if="!mainPage && submissionPage "
        :loading="loading"
        :disabled="readonly"
        size="mini"
        type="primary"
        @click="saveProposal"
      >
        <svg-icon name="icomoo/273-checkmark"></svg-icon> Save
      </el-button>

      <el-button
          v-if="!mainPage && submissionPage "
          :loading="loading"
          size="mini"
          :disabled="readonly"
          type="primary"
          @click="SubmmitProposal"
      >
          <svg-icon name="guide"></svg-icon> Submit
      </el-button>

      <el-divider
        v-if="!mainPage"
        direction="vertical"
      ></el-divider>
      <!--
      <el-button
        v-for="proposal in displayedProposals"
        :key="proposal.id"
        size="mini"
        type="primary"
        @click="openProposalForm(proposal)"
      >
        {{ printEvaluation(proposal.evaluationMethodLineEvaluation) }} Proposal
      </el-button>
      -->
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
            label="Submission Status"
            min-width="170"
            sortable
          >
            <template slot-scope="{ row }">
              {{ printSubmissionStatus(row.documentStatus) }}
            </template>
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
      <submission-form
        v-else-if="submissionPage"
        ref="submissionForm"
        :data="selectedRow"
      ></submission-form>
      <!--
      <component
        @setReadOnly="setReadOnly"
        v-else
        ref="proposalForm"
        :is="proposalComponent"
        :data="selectedProposal"
        :disabled="submitted"
        :loading.sync="loading"
        :schedule="schedule"
        :submission-id="selectedRow.id"
      ></component>

      <el-dialog
        class="bidding-schedule-dialog"
        width="80%"
        :visible.sync="biddingScheduleVisible"
        title="Bidding Schedule"
      >
        <bidding-schedule
          :bidding-id="selectedRow.biddingId"
          :bidding-name="selectedRow.biddingName"
          :bidding-no="selectedRow.biddingNo"
          :submission-id="selectedRow.id"
          @form-open="biddingScheduleVisible = false"
        ></bidding-schedule>
      </el-dialog>
      -->
    </div>
  </div>
</template>
<script lang="ts" src="./registered-prequalification-list.component.ts"></script>
<style lang="scss">
.compact .registered-prequalification-list {
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
