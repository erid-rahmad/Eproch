<template>
  <div class="bidding-negotiation-line">
    <div class="toolbar">
      <el-button
        v-if="!innerIndex"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="closeLine"
      >
        Close
      </el-button>
    </div>
    <div v-if="innerIndex">
      <el-scrollbar class="form-wrapper">
        <el-form
          ref="negotiation"
          label-position="left"
          label-width="200px"
          :model="negotiation"
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
                  v-model="negotiation.biddingNo"
                  disabled
                ></el-input>
              </el-form-item>
              <el-form-item label="Bidding Title">
                <el-input
                  v-model="negotiation.biddingTitle"
                  disabled
                ></el-input>
              </el-form-item>
              <el-form-item label="Bidding Type">
                <el-input
                  v-model="negotiation.biddingType"
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
                <el-date-picker
                  v-model="negotiation.startDate"
                  disabled
                  :format="dateDisplayFormat"
                  size="mini"
                  type="datetime"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="End Date">
                <el-date-picker
                  v-model="negotiation.endDate"
                  disabled
                  :format="dateDisplayFormat"
                  size="mini"
                  type="datetime"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-scrollbar>
      <div v-if="!isVendor">
        Agreed vendors
        <el-table border :data="agreed" size="mini" v-loading="loading">
          <el-table-column min-width="100" label="No">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column property="vendorName" label="Vendor" min-width="300"></el-table-column>
          <el-table-column label="Proposed Price" min-width="300">
            <template slot-scope="{row}">
              {{ row.proposedPrice | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column label="Negotiation Price" min-width="300">
            <template slot-scope="{row}">
              {{ row.negotiationPrice | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column min-width="160" label="Action">
          <template slot-scope="{ row }">
            <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="viewNegotiationWindow(row)">
              Negotiate
            </el-button>
          </template>
        </el-table-column>
        </el-table>
      </div>
      Negotiation in progress
      <el-table border :data="inProgress" size="mini" v-loading="loading">
        <el-table-column min-width="50" label="No">
          <template slot-scope="row">
            {{ row.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column property="vendorName" label="Vendor" width="400"></el-table-column>
        <el-table-column label="Proposed Price" min-width="300">
          <template slot-scope="{row}">
            {{ row.proposedPrice | formatCurrency }}
          </template>
        </el-table-column>
        <el-table-column label="Negotiation Price" min-width="300">
          <template slot-scope="{row}">
            {{ row.negotiationPrice | formatCurrency }}
          </template>
        </el-table-column>
        <el-table-column min-width="160" label="Action">
          <template slot-scope="{ row }">
            <div v-if="allowNegotiation">
              <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="viewNegotiationWindow(row)">
                Negotiate
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="!isVendor">
        Disagreed vendors
        <el-table border :data="disagreed" size="mini" v-loading="loading">
          <el-table-column min-width="100" label="No">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column property="vendorName" label="Vendor" min-width="400"></el-table-column>
          <el-table-column label="Proposed Price" min-width="400">
            <template slot-scope="{row}">
              {{ row.proposedPrice | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column label="Negotiation Price" min-width="400">
            <template slot-scope="{row}">
              {{ row.negotiationPrice | formatCurrency }}
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="!isVendor">
        Negotiation not started
        <el-table border :data="notStarted" size="mini" v-loading="loading">
          <el-table-column min-width="100" label="No">
            <template slot-scope="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column property="vendorName" label="Vendor" min-width="400"></el-table-column>
          <el-table-column label="Proposed Price" min-width="400">
            <template slot-scope="{row}">
              {{ row.proposedPrice | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column min-width="400" label="Action">
            <template slot-scope="{ row }">
              <div v-if="allowNegotiation">
                <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="viewNegotiationWindow(row)">
                  Negotiate
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <template v-else>
      <bidding-negotiation-line-conversation
        :data="selectedRow"
      ></bidding-negotiation-line-conversation>
    </template>
  </div>
</template>

<script lang="ts" src="./negotiation-line.component.ts"></script>

<style lang="scss">
.compact {
  .bidding-negotiation-line {
    .el-table--mini {
      th, td {
        height: 35px;
      }
    }

    .el-table{
      margin-top: 5px;
      margin-bottom: 10px;
    }
  }
}

.bidding-negotiation-line {
  .form-wrapper {
    .el-scrollbar__wrap {
      overflow-x: hidden;
      padding: 15px;
    }
  }
}
</style>
