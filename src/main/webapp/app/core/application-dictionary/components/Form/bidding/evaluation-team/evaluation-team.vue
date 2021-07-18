<template>
  <div class="app-container card-view evaluation-team">
    <div class="toolbar">
      <el-button
        v-if="!isMainPage"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="backToMainPage"
      >
        Close
      </el-button>
      <el-button
        v-if="!isMainPage"
        icon="el-icon-check"
        size="mini"
        style="margin-left: 1px"
        type="primary"
        @click="onSaveClicked"
      >
        Save
      </el-button>
    </div>
    <div class="card">
      <div v-if="isMainPage">
      <el-table
        ref="mainGrid"
        v-loading="loading"
        border
        :data="evaluationTeams"
        highlight-current-row
        size="mini"
        stripe
        style="width: 100%"
        @current-change="onCurrentRowChanged"
      >
        <el-table-column
          label="No"
          width="50"
        >
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Bidding No.'"
          min-width="150"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.biddingNo"
              size="mini"
              placeholder="Bidding No."
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.biddingNo || row.prequalificationNo}}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Title'"
          min-width="150"
          show-overflow-tooltip
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.biddingTitle"
              size="mini"
              placeholder="Title"
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.biddingName || row.prequalificationName}}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Bidding Type'"
          min-width="150"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.biddingTypeName"
              size="mini"
              placeholder="Bidding Type"
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.biddingType || "Prequalification"}}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Event Type'"
          min-width="150"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.eventTypeName"
              size="mini"
              placeholder="Event Type"
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.biddingEventType || row.prequalificationEventType}}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Department'"
          min-width="150"
          prop="costCenterName"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.costCenterName"
              size="mini"
              placeholder="Department"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="Modified Date"
          min-width="150"
          prop="lastModifiedDate"
          sortable
        >
          <template slot-scope="{ row }">
            {{ row.lastModifiedDate | formatDate }}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Status'"
          min-width="100"
          prop="status"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.status"
              size="mini"
              placeholder="Status"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column
          align="right"
          fixed="right"
          min-width="150"
        >
          <template slot="header">
            <el-button
              v-if="!quickSearchActive"
              size="mini"
              title="Quick Search"
              type="primary"
              @click="onQuickSearchOpen"
            >
              <svg-icon name="search"></svg-icon> Search
            </el-button>
            <el-button-group v-else>
              <el-button
                size="mini"
                title="Apply"
                type="primary"
                @click="onSearchApply"
              >
                <svg-icon name="search"></svg-icon> Search
              </el-button>
              <el-button
                icon="el-icon-close"
                size="mini"
                title="Close"
                type="danger"
                @click="onQuickSearchClose"
              ></el-button>
            </el-button-group>
          </template>
          <template slot-scope="{ row }">
            <el-button
              size="mini"
              title="View Detail"
              type="primary"
              @click="viewDetail(row)"
            >
              <svg-icon name="link"></svg-icon>
            </el-button>
            <div v-if="row.biddingId!=null">
              <el-button
                size="mini"
                title="View Contract Requisition"
                type="primary"
                @click="viewContractRfq(row)"
              >
                <svg-icon name="icomoo/039-file-text2"></svg-icon>
              </el-button>
              <el-button
                size="mini"
                title="View Contract Document"
                type="primary"
                @click="viewContractDocument(row)"
              >
                <svg-icon name="skill"></svg-icon>
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        ref="pagination"
        :current-page.sync="gridPage"
        :page-size="itemsPerPage"
        :page-sizes="[10, 20, 50, 100]"
        :total="queryCount"
        background
        layout="sizes, prev, pager, next"
        small
        @size-change="changePageSize"
      ></el-pagination>
      </div>

      <evaluation-team-detail
        v-else-if="isDetailPage"
        :data="selectedRow"
      ></evaluation-team-detail>

      <contract-requisition
        v-else-if="isContractRequisitionPage"
        :data="selectedRow"
      ></contract-requisition>

      <contract-form
        v-else-if="isContractFormPage"
        :data="selectedRow"
      ></contract-form>
    </div>
  </div>
</template>
<script lang="ts" src="./evaluation-team.component.ts"></script>

<style lang="scss" scoped>
.evaluation-team {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  .toolbar {
    padding: 4px;
  }

}
.compact .el-table tbody .el-button {
  margin: 5px 0;
}
</style>
