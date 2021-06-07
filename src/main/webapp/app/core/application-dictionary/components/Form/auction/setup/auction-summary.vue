<template>
  <el-collapse
    v-model="activeGroups"
    class="auction-summary"
    @change="onCollapseChanged"
  >
    <el-collapse-item title="Overview" name="INF">
      <el-form
        disabled
        :label-position="formSettings.labelPosition"
        :label-width="formSettings.labelWidth"
        :model="auction"
        :size="formSettings.size"
      >
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Auction No.">
              <span>{{ auction.documentNo }}</span>
            </el-form-item>
            <el-form-item label="Title">
              <span>{{ auction.name }}</span>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Creation Date">
              <span>{{ auction.createdDate | formatDate(false) }}</span>
            </el-form-item>
            <el-form-item label="Last Modified Date">
              <span>{{ auction.lastModifiedDate | formatDate(false) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Description">
              <span>{{ auction.description }}</span>
            </el-form-item>
            <el-form-item label="Currency">
              <span>{{ auction.currencyName }}</span>
            </el-form-item>
            <el-form-item label="Owner">
              <span>{{ auction.ownerName }}</span>
            </el-form-item>
            <el-form-item label="Department">
              <span>{{ auction.costCenterName }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-collapse-item>
    
    <el-collapse-item title="Team Members" name="TEM">
      <el-table
        v-loading="loadingTeams"
        border
        :data="teams"
        highlight-current-row
        size="mini"
        stripe
      >
        <el-table-column width="50" label="No">
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>

        <el-table-column
          label="Role"
          width="150"
          show-overflow-tooltip
          sortable
        >
          <template slot-scope="{ row }">
            {{ printRoleName(row.role) }}
          </template>
        </el-table-column>

        <el-table-column
          label="User"
          width="200"
          prop="userName"
          show-overflow-tooltip
          sortable
        ></el-table-column>
      </el-table>
    </el-collapse-item>

    <el-collapse-item title="Auction Rules" name="RUL">
      <el-form
        v-loading="loadingRule"
        ref="auctionRuleForm"
        disabled
        :label-position="formSettings.labelPosition"
        :label-width="formSettings.labelWidth"
        :model="auctionRule"
        :size="formSettings.size"
      >
        <el-divider content-position="left">
          <h4>Timing Rules</h4>
        </el-divider>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Place Bid During Preview Period">
              <span>{{ printBidPrevPeriodName(auctionRule.bidPrevPeriod) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Bidding Start Time">
              <span>{{ auctionRule.startDate | formatDate }}</span>
              <el-checkbox
                v-model="auctionRule.reminderStartTime"
                disabled
                style="margin-left: 16px"
              >
                Reminder
              </el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Running Time First Lot">
              <span>{{ auctionRule.firstLotRunTime }} Minutes</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Bid Rank Overtime">
              <span>{{ auctionRule.bidRankOvertime }}</span>
            </el-form-item>
            <el-form-item label="Start Overtime Threshold">
              <span>{{ auctionRule.startOvertimeWithin }} Minutes</span>
            </el-form-item>
            <el-form-item label="Overtime Period">
              <span>{{ auctionRule.overtimePeriod }} Minutes</span>
            </el-form-item>
            <el-form-item label="Est. Award Date">
              <span>{{ auctionRule.estAwardDate | formatDate(true) }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">
          <h4>Action Rules</h4>
        </el-divider>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Improve Bid Amount by">
              <span>{{ printBidImprovementUnitName(auctionRule.bidImprovementUnit) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="Tie Bids Rule">
              <span>{{ printTieBidsRuleName(auctionRule.tieBidsRule) }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">
          <h4>Feedback</h4>
        </el-divider>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="">
              <el-checkbox
                v-model="auctionRule.showResponse"
                disabled
                title="whether the response can be seen by each participant"
              >
                Show Participant Responses
              </el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="gutterSize">
          <el-col
            :xs="24"
            :sm="20"
            :md="16"
            :lg="12"
            :xl="8"
          >
            <el-form-item label="">
              <el-checkbox
                v-model="auctionRule.showLeader"
                disabled
              >
                Show Leading Bid To All Participants
              </el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-collapse-item>

    <el-collapse-item title="Participants" name="PCP">
      <el-table
        v-loading="loadingParticipants"
        border
        :data="participants"
        highlight-current-row
        size="mini"
        stripe
      >
        <el-table-column width="50" label="No">
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>

        <el-table-column
          label="Vendor"
          min-width="250"
          prop="vendorName"
          show-overflow-tooltip
        ></el-table-column>

        <el-table-column
          label="User Contact"
          min-width="200"
          prop="userName"
          show-overflow-tooltip
        ></el-table-column>

        <el-table-column
          label="Business Email"
          min-width="200"
          prop="userEmail"
          show-overflow-tooltip
        ></el-table-column>
      </el-table>
    </el-collapse-item>

    <el-collapse-item title="Items" name="ITM">
      <el-table
        v-loading="loadingItems"
        border
        :data="items"
        highlight-current-row
        size="mini"
        stripe
      >
        <el-table-column width="50" label="No">
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>

        <el-table-column
          label="Code"
          prop="productCode"
          show-overflow-tooltip
          sortable
          width="130"
        ></el-table-column>

        <el-table-column
          label="Name"
          min-width="200"
          prop="productName"
          show-overflow-tooltip
          sortable
        ></el-table-column>

        <el-table-column
          label="Qty"
          width="150"
          prop="quantity"
          sortable
        ></el-table-column>

        <el-table-column
          label="UoM"
          prop="uomName"
          width="100"
          sortable
        ></el-table-column>

        <el-table-column
          align="right"
          label="Price"
          width="200"
          sortable
        >
          <template slot-scope="{ row }">
            {{ row.ceilingPrice | formatCurrency('id') }}
          </template>
        </el-table-column>

        <el-table-column
          align="right"
          label="Total"
          width="200"
          sortable
        >
          <template slot-scope="{ row }">
            {{ row.amount | formatCurrency('id') }}
          </template>
        </el-table-column>
      </el-table>
    </el-collapse-item>

    <el-collapse-item title="Information" name="CTN">
      <div
        v-loading="loadingContent"
        v-html="content.description"
      ></div>
    </el-collapse-item>
  </el-collapse>
</template>
<script lang="ts" src="./auction-summary.component.ts"></script>
