<template>
  <div class="app-container card-view auction-invitation">
    <div class="toolbar">
      <el-button
        v-if="!mainPage"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="onCloseClicked"
      >
        Close
      </el-button>

      <el-button-group style="margin-left: 5px">
        <el-button
          v-if="reviewPage && isDraft && setupTabName === 'PRQ'"
          size="mini"
          type="primary"
          @click="onAcceptClicked"
        >
          <svg-icon name="icomoo/273-checkmark"></svg-icon> Accept
        </el-button>
        <el-button
          v-if="reviewPage && isDraft && setupTabName === 'PRQ'"
          size="mini"
          type="danger"
          @click="onDeclineClicked"
        >
          <svg-icon name="icomoo/272-cross"></svg-icon> Decline
        </el-button>
      </el-button-group>
    </div>

    <div class="card">
      <template v-if="mainPage">
        <el-table
          v-loading="loading"
          ref="mainGrid"
          border
          :data="gridData"
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
            label="Auction No"
            min-width="120"
            prop="auctionDocumentNo"
            sortable
          ></el-table-column>

          <el-table-column
            label="Title"
            min-width="200"
            prop="auctionName"
            show-overflow-tooltip
            sortable
          ></el-table-column>

          <el-table-column
            label="Start Date"
            min-width="150"
            sortable
          >
            <template slot-scope="{ row }">
              {{ row.auctionRuleStartDate | formatDate }}
            </template>
          </el-table-column>

          <el-table-column
            label="Auction Status"
            min-width="140"
            sortable
          >
            <template slot-scope="{ row }">
              {{ printDocStatus(row.auctionDocumentStatus) }}
            </template>
          </el-table-column>

          <el-table-column
            fixed="right"
            width="180"
            label="Action"
          >
            <template slot-scope="{ row }">
              <el-button
                v-if="!isVendor"
                class="button"
                size="mini"
                type="primary"
                @click="viewSetup(row)"
              >
                <svg-icon name="icomoo/149-cog"></svg-icon> Config
              </el-button>

              <el-button
                class="button"
                size="mini"
                type="primary"
                @click="viewDetails(row)"
              >
                <svg-icon name="link"></svg-icon> View
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

      <auction-invitation-detail
        v-else-if="reviewPage"
        ref="reviewPage"
        :data.sync="auctionData"
        :tab.sync="setupTabName"
      ></auction-invitation-detail>

    </div>

    <el-dialog
      width="40%"
      :visible.sync="confirmationVisible"
      :title="`${selectedAction} Invitation`"
    >
      <template>
        <p>Are you sure you want to {{ selectedAction }} the invitation?</p>
        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close"
            @click="confirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-check"
            :type="documentAction === 'DCL' ? 'danger' : 'primary'"
          >
            {{ selectedAction }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
<style lang="scss">
.compact .auction-invitation {

  .el-table--mini {

    th,
    td {
        height: 35px;
    }
  }

  .toolbar {
    padding: 4px 16px 0;
  }
}
</style>