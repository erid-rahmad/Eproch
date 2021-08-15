<template>
  <div class="bid-submission">
    <el-container>
      <el-aside
        class="auction-info"
        width="320px"
      >
        <ul>
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
                  :class="getItemClassNames(item)"
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
                v-if="isVendor"
                class="bid-rank"
                effect="dark"
                :type="rankType"
              >
                My Bid Rank: {{ rank }}
              </el-tag>
              <template v-else>&nbsp;</template>
            </el-col>
            <el-col
              :xs="24"
              :sm="12"
            >
              <countdown-timer
                v-if="auctionItems.length"
                ref="countdownTimer"
                align-right
                :auto-start="false"
                :duration="auction.remainingTime"
                :paused="isPaused"
                :to-date="auction.estEndDate"
              ></countdown-timer>
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
                <template v-if="leadingBidPrice === null">&nbsp;</template>
                <el-form-item
                  v-else
                  label="Leading Bid"
                >
                  {{ auction.currencyName }} {{ leadingBidPrice | formatCurrency }}
                </el-form-item>
              </el-col>
              <el-col
                :xs="24"
                :sm="18"
                :lg="12"
                :xl="8"
              >
                <el-form-item label="Decrement">
                  <el-button
                    :disabled="!isVendor || (!isOngoing && selectedItem.auctionStatus !== 'STR')"
                    size="mini"
                    title="Quick Bid"
                    plain
                    @click="confirmResponse(selectedItem.bidDecrement)"
                  >
                    <svg-icon
                      v-if="isVendor"
                      name="tick"
                    ></svg-icon> {{ selectedItem.bidDecrement | formatCurrency }}
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
            <el-table
              v-loading="loadingAuctionItems"
              ref="mainGrid"
              border
              :data="selectedItemGrid"
              highlight-current-row
              size="mini"
              style="margin-bottom: 8px;margin-top: 8px"
            >
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
                label="Ceiling Price"
                min-width="150"
              >
                <template v-slot="{ row }">
                  {{ row.ceilingPrice | formatCurrency }}
                </template>
              </el-table-column>

              <el-table-column
                v-if="isVendor"
                label="Ext. Price"
                min-width="150"
              >
                <template v-slot="{ row }">
                  {{ row.bidPrice | formatCurrency }}
                </template>
              </el-table-column>

              <el-table-column
                label="Total"
                min-width="200"
              >
                <template v-slot="{ row }">
                  {{ row.amount | formatCurrency }}
                </template>
              </el-table-column>
            </el-table>

            <el-row :gutter="gutterSize">
              <el-col
                :xs="24"
                :sm="24"
                :lg="12"
                :xl="8"
              >
                <el-form-item
                  v-if="isVendor"
                  label="Decrement Bid"
                >
                  <el-input-number
                    v-model="bidForm.amount"
                    :disabled="!isOngoing && selectedItem.auctionStatus !== 'STR'"
                    :min="0"
                    size="mini"
                    :step="selectedItem.bidDecrement || 1"
                    step-strictly
                    @change="onBidDecrementChanged"
                  ></el-input-number>
                  <el-button
                    :disabled="!isOngoing && selectedItem.auctionStatus !== 'STR'"
                    size="mini"
                    type="primary"
                    @click="confirmResponse(bidForm.amount)"
                  >
                    <svg-icon name="tick"></svg-icon> Submit Bid
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>

            <el-divider content-position="left">
              Event Log
            </el-divider>
            <el-table
              v-loading="loadingEventLog"
              ref="logsGrid"
              border
              :data="eventLog"
              :fit="false"
              :max-height="320"
              size="mini"
              style="margin-top: 8px"
              :span-method="spanEventLogColumns"
            >
              <el-table-column
                label="Participant"
                show-overflow-tooltip
                min-width="200"
              >
                <template v-slot="{ row }">
                  {{ ['FIN', 'STR', 'STP', 'PAS'].includes(row.action) ? printStatus(row.action) : row.vendorName }}
                </template>
              </el-table-column>

              <el-table-column
                label="Price"
                min-width="200"
              >
                <template v-slot="{ row }">
                  <span v-if="row.action === 'BID'">
                    {{ row.price | formatCurrency }}
                  </span>
                </template>
              </el-table-column>

              <el-table-column
                label="Submission Time"
                width="200"
              >
                <template v-slot="{ row }">
                  {{ row.dateTrx | formatDateTime }}
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </el-main>
      </el-container>
    </el-container>
    <el-dialog
      width="40%"
      :visible.sync="bidConfirmationVisible"
      title=""
    >
      <template>
        <p>Are you sure to submit the response?</p>
        <div slot="footer">
          <el-button
            size="mini"
            icon="el-icon-close"
            @click="bidConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-check"
            type="primary"
            @click="submitBid"
          >
            Submit
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./bid-submission.component.ts"></script>
<style lang="scss">
.bid-submission {
  .auction-info {
    .item-list {
      .el-radio {

        .el-radio__label {
          color: #c0c0c0;
        }

        &.is-watched .el-radio__label {
          color: #1f2d3d;
        }

        &.is-ongoing .el-radio__input .el-radio__inner {
          animation: blink 2s infinite;

          @media (prefers-reduced-motion: reduce) {
            animation: none;
          }
        }
      }
    }
  }
}

@keyframes blink {
  0%, 100% {
    border-color: #ff5252;
    background: #ff5252;
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

  .el-header,
  .el-main {
    padding-right: 0;
  }
}
</style>