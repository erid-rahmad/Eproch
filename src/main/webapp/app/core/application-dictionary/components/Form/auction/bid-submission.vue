<template>
  <div class="app-container bid-submission">
    <el-container>
      <el-aside
        class="auction-info"
        width="256px"
      >
        <ul>
          <li>Information</li>
          <li>
            Select Line
            <div class="item-list">
              <el-radio-group
                v-model="selectedItemId"
                @change="onItemSelected"
              >
                <el-radio
                  v-for="item in auctionItems"
                  :key="item.id"
                  :disabled="item.status !== 'P'"
                  :label="item.id"
                >
                  {{ item.productCode }} {{ item.productName }}
                </el-radio>
              </el-radio-group>
            </div>
          </li>
        </ul>
      </el-aside>
      <el-container>
        <el-header>
          <el-row>
            <el-col
              :xs="24"
              :sm="12"
            >
              <el-tag
                class="bid-rank"
                effect="dark"
                :type="rankType"
              >
                My Bid Rank: {{ rank }}
              </el-tag>
            </el-col>
            <el-col
              :xs="24"
              :sm="12"
            >
              <div class="bid-timer">
                <div class="timer-label">Time Remaining</div>
                <div class="timer-value">{{ timeRemaining }}</div>
              </div>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-form
            ref="bidForm"
            label-position="left"
            label-width="200px"
            :model="bidForm"
            size="mini"
          >
            <el-row :gutter="gutterSize">
              <el-col
                :xs="24"
                :sm="18"
                :lg="12"
                :xl="8"
              >
                <el-form-item label="Leading Bid">
                  {{ auction.currencyName }} {{ auction.leadingBid | formatCurrency }}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="Decrement">
                  <el-button
                    size="mini"
                    title="Quick Bid"
                    type="primary"
                    @click="submitBid(auction.decrement)"
                  >
                    <svg-icon name="tick"></svg-icon> {{ auction.decrement | formatCurrency }}
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
            <el-table
              ref="mainGrid"
              border
              :data="selectedItemGrid"
              highlight-current-row
              size="mini"
              style="margin-bottom: 8px;margin-top: 8px"
            >
              <el-table-column width="50" label="No">
                <template slot-scope="row">
                  {{ row.$index + 1 }}
                </template>
              </el-table-column>

              <el-table-column
                label="Item ID"
                width="100"
                prop="productCode"
              ></el-table-column>

              <el-table-column
                label="Title"
                min-width="200"
                prop="productName"
                show-overflow-tooltip
              ></el-table-column>

              <el-table-column
                label="Qty"
                width="100"
                prop="quantity"
              ></el-table-column>

              <el-table-column
                label="UoM"
                width="75"
                prop="uomName"
              ></el-table-column>

              <el-table-column
                label="Price"
                width="200"
              >
                <template slot-scope="{ row }">
                  {{ row.ceilingPrice | formatCurrency }}
                </template>
              </el-table-column>

              <el-table-column
                label="Total"
                width="200"
              >
                <template slot-scope="{ row }">
                  {{ row.amount | formatCurrency }}
                </template>
              </el-table-column>
            </el-table>
            <el-row :gutter="gutterSize">
              <el-col
                :xs="24"
                :sm="18"
                :lg="12"
                :xl="8"
              >
                <el-form-item label="Decrement Bid">
                  <el-input-number
                    v-model="bidForm.amount"
                    :min="auction.decrement"
                    :step="auction.decrement"
                    step-strictly
                  ></el-input-number>
                </el-form-item>
                <el-form-item label="">
                  <el-button
                    size="mini"
                    type="primary"
                    @click="submitBid(bidForm.amount)"
                  >
                    <svg-icon name="tick"></svg-icon> Bid
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
            <el-divider content-position="left">
              Bid Logs
            </el-divider>
            <el-table
              ref="logsGrid"
              border
              :data="bidLogs"
              size="mini"
              style="margin-top: 8px"
            >
              <el-table-column width="50" label="No">
                <template slot-scope="row">
                  {{ row.$index + 1 }}
                </template>
              </el-table-column>

              <el-table-column
                label="Participant"
                width="200"
                prop="vendorName"
                show-overflow-tooltip
              ></el-table-column>

              <el-table-column
                label="Price"
                min-width="200"
              >
                <template slot-scope="{ row }">
                  {{ row.price | formatCurrency }}
                </template>
              </el-table-column>

              <el-table-column
                label="Submission Time"
                width="200"
              >
                <template slot-scope="{ row }">
                  {{ row.dateSubmit | formatDateTime }}
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" src="./bid-submission.component.ts"></script>
<style lang="scss">
.bid-submission {
  .auction-info {
    .item-list {
      .el-radio__input.is-checked .el-radio__inner {
        animation: blink 2s infinite;

        @media (prefers-reduced-motion: reduce) {
          animation: none;
        }
      }
    }
  }
}

@keyframes blink {
  0%, 100% {
    border-color: #db4000;
    background: #db4000;
  }

  50% {
    border-color: #ffba9d;
    background: #ffba9d;
  }
}
</style>
<style lang="scss" scoped>
.bid-submission {
  .auction-info {
    background: #fff;
    border-right: 1px solid #d0d0de;
    padding-left: 0;
  }

  .bid-timer {
    text-align: right;

    .timer-value{
      font-size: 1.5rem;
      font-weight: 600;
    }
  }
}
</style>