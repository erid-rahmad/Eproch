<template>
  <div class="app-container card-view contract-team-grid">
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
          :label="quickSearchActive ? null : 'Contract No.'"
          min-width="150"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.contractNo"
              size="mini"
              placeholder="Contract No."
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.contractNo}}
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
              v-model="search.contractName"
              size="mini"
              placeholder="Title"
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.contractName}}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Contract Type'"
          min-width="150"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.contractTypeName"
              size="mini"
              placeholder="Contract Type"
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.contractTypeName}}
          </template>
        </el-table-column>
        <el-table-column
          :label="quickSearchActive ? null : 'Vendor Name'"
          min-width="150"
          :sortable="!quickSearchActive"
        >
          <template slot="header">
            <el-input
              v-if="quickSearchActive"
              v-model="search.vendorName"
              size="mini"
              placeholder="Vendor Name"
            ></el-input>
          </template>
          <template slot-scope="{ row }">
            {{row.vendorName}}
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
          <template slot-scope="{ row }">
            {{ row.status==='U'?'Un-arranged':row.status==='A'?'Arranged':row.status }}
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
            <div v-if="row.contractId!=null">
              <el-button
                size="mini"
                title="View Contract Detail"
                type="primary"
                @click="viewContractDetail(row)"
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

      <contract-team
        ref="contractTeam"
        v-else-if="isDetailPage"
        :data="selectedRow"
        :from-grid="true"
      ></contract-team>
<!--
      <contract-requisition
        v-else-if="isContractRequisitionPage"
        :data="selectedRow"
      ></contract-requisition>

      <contract-form
        v-else-if="isContractFormPage"
        :data="selectedRow"
      ></contract-form>
-->
    </div>
  </div>
</template>
<script lang="ts" src="./contract-team-grid.component.ts"></script>

<style lang="scss" scoped>
.contract-team-grid {
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
