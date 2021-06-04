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
        prop="vendor"
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
