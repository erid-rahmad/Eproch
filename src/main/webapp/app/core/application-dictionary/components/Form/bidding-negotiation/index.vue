<template>
  <div class="app-container bidding-negotiation">
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
    </div>
    <el-table
      ref="mainGrid"
      v-if="index && displayTable"
      border
      :data="biddingNegotiations"
      highlight-current-row
      size="mini"
      stripe
      style="width: 100%"
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
        label="Bidding No."
        min-width="100"
        prop="biddingNo"
        sortable
      ></el-table-column>
      <el-table-column
        label="Title"
        min-width="200"
        prop="biddingTitle"
        sortable
      ></el-table-column>
      <el-table-column
        label="Bidding Type"
        min-width="100"
        prop="biddingType"
        sortable
      ></el-table-column>
      <el-table-column
        label="Event Type"
        min-width="100"
        prop="eventType"
        sortable
      ></el-table-column>
      <el-table-column
        label="Bidding Schedule"
        min-width="150"
      >
        <template slot-scope="{ row }">
          <el-button
            icon="el-icon-search"
            size="mini"
            type="primary"
            @click="viewSchedule(row)"
          >
            View Schedule
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="Bidding Status"
        min-width="100"
        sortable
      >
        <template slot-scope="{ row }">{{formatBiddingStatus(row.biddingStatus)}}</template>
      </el-table-column>
      <el-table-column
        label="Evaluation Status"
        min-width="100"
        prop="evaluationStatus"
      ></el-table-column>
      <el-table-column
        label="Vendor"
        min-width="100"
        prop="vendorCount"
        v-if="!isVendor"
      ></el-table-column>
      <el-table-column label="Action" min-width="100">
        <template slot-scope="{ row }">
          <el-button
            icon="el-icon-search"
            size="mini"
            type="primary"
            @click="viewDetail(row)"
          >
            View
          </el-button>
          <el-button
            icon="el-icon-search"
            size="mini"
            type="primary"
            v-if="row.vendorCount==row.finishedCount"
            @click="viewSummary(row)"
          >
            Summary
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <template v-else>
      <bidding-negotiation-line
        :data="selectedRow"
        :outerIndex="index"
      ></bidding-negotiation-line>
    </template>

    <el-dialog title="Bidding Schedule" :visible.sync="showSchedule" width="90%" :before-close="closeSchedule">
      <el-table border :data="biddingSchedule" size="mini">
        <el-table-column width="60" label="No">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column property="eventTypeLineName" label="Event" width="200" show-overflow-tooltip></el-table-column>
        <el-table-column property="startDate" label="Start Date" min-width="200" show-overflow-tooltip></el-table-column>
        <el-table-column property="endDate" label="Finish Date" min-width="200" show-overflow-tooltip></el-table-column>
        <el-table-column min-width="100" label="Action">
          <template slot-scope="{ row }">
            <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="viewSchedule2(row)">
              View
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog title="Negotiation Summary" :visible.sync="showSummary" width="90%" :before-close="clearSummary">
      <el-form
          ref="negotiation"
          label-position="left"
          label-width="200px"
          :model="selectedRow"
          size="mini"
        >
          <el-row
            :gutter="24"
            style="margin-top: 16px"
          >
            <el-col
              :xs="24"
              :sm="12"
              :lg="12"
              :xl="8"
            >
              <el-form-item label="Bidding Number">
                <el-input
                  v-model="selectedRow.biddingNo"
                  disabled
                ></el-input>
              </el-form-item>
              <el-form-item label="Bidding Title">
                <el-input
                  v-model="selectedRow.biddingTitle"
                  disabled
                ></el-input>
              </el-form-item>
              <el-form-item label="Bidding Type">
                <el-input
                  v-model="selectedRow.biddingType"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col
              :xs="24"
              :sm="12"
              :lg="12"
              :xl="8"
            >
              <el-form-item label="Start Date">
                <el-input
                  v-model="selectedRow.startDate"
                  disabled
                ></el-input>
              </el-form-item>
              <el-form-item label="End Date">
                <el-input
                  v-model="selectedRow.endDate"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      <el-table border :data="negoSummary" size="mini">
        <el-table-column width="60" label="No">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column property="vendorName" label="Vendor" min-width="200" show-overflow-tooltip></el-table-column>
        <el-table-column label="Status" min-width="200">
          <template slot-scope="{row}">
            <el-checkbox
              v-model="row.checkmark"
            >{{row.negotiationStatus}}</el-checkbox>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer">
        <el-button
          icon="el-icon-close"
          size="mini"
          @click="clearSummary"
        >
          {{ $t('entity.action.cancel') }}
        </el-button>
        <el-button
          icon="el-icon-check"
          size="mini"
          type="primary"
          @click="clearSummary"
        >
          Submit
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" src="./index.component.ts"></script>

<style lang="scss" scoped>
.bidding-negotiation {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;
}
.compact .el-table tbody .el-button {
  margin: 5px 0;
}
</style>
