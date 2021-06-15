<template>
  <div class="auction-rule">
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
            <el-input
              v-model="auction.documentNo"
              clearable
            ></el-input>
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
          <el-form-item label="Title">
            <el-input
              v-model="auction.name"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
    <el-form
      v-loading="loadingRule"
      ref="auctionRuleForm"
      :disabled="readOnly"
      :label-position="formSettings.labelPosition"
      :label-width="formSettings.labelWidth"
      :model="auctionRule"
      :rules="auctionRuleValidationSchema"
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
            <el-select
              v-model="auctionRule.bidPrevPeriod"
              ref="bidPrevPeriod"
              clearable
              filterable
            >
              <el-option
                v-for="item in bidPrevPeriodOptions"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
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
            <el-date-picker
              v-model="auctionRule.startDate"
              clearable
              :format="dateTimeDisplayFormat"
              size="mini"
              type="datetime"
            ></el-date-picker>
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
            <el-input-number
              v-model="auctionRule.firstLotRunTime"
              controls-position="right"
              :min="0"
              size="mini"
            ></el-input-number>
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
            <el-input-number
              v-model="auctionRule.bidRankOvertime"
              controls-position="right"
              :min="0"
              size="mini"
            ></el-input-number>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="20"
          :md="16"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Overtime Period (Minutes)">
            <el-input-number
              v-model="auctionRule.overtimePeriod"
              controls-position="right"
              :min="0"
              size="mini"
            ></el-input-number>
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
          <el-form-item label="Start Overtime Threshold (Minutes)">
            <el-input-number
              v-model="auctionRule.startOvertimeWithin"
              controls-position="right"
              :min="0"
              size="mini"
            ></el-input-number>
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
          <el-form-item label="Est. Award Date">
            <el-date-picker
              v-model="auctionRule.estAwardDate"
              clearable
              :format="dateDisplayFormat"
              size="mini"
              type="date"
              :value-format="dateValueFormat"
            ></el-date-picker>
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
            <el-select
              v-model="auctionRule.bidImprovementUnit"
              clearable
              filterable
            >
              <el-option
                v-for="item in bidImprovementUnitOptions"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
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
            <el-select
              v-model="auctionRule.tieBidsRule"
              clearable
              filterable
            >
              <el-option
                v-for="item in tieBidsRuleOptions"
                :key="item.id"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
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
            <el-checkbox v-model="auctionRule.showLeader">
              Show Leading Bid To All Participants
            </el-checkbox>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script lang="ts" src="./auction-rule.component.ts"></script>
