<template>
  <div class="app-container card-view auction">
    <div class="toolbar">
      <el-button
        v-if="submissionPage"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="onFormClosed"
      >
        Close
      </el-button>

      <el-dropdown
        v-if="!isVendor && submissionPage && actions.length"
        size="mini"
        style="margin-left: 5px"
        @command="runAction"
      >
        <el-button
          size="mini"
          type="primary"
        >
          <svg-icon name="icomoo/149-cog"></svg-icon> Settings 
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item
            v-for="action in actions"
            :key="action.id"
            :command="action"
            :title="action.tooltip"
          >
            {{ action.name }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <div class="card">
      <template v-if="mainPage">
        <el-table
          v-loading="loading"
          ref="mainGrid"
          border
          :data="gridData"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
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
            prop="documentNo"
            sortable
          ></el-table-column>

          <el-table-column
            label="Title"
            min-width="200"
            prop="name"
            show-overflow-tooltip
            sortable
          ></el-table-column>

          <el-table-column
            label="Auction Status"
            min-width="140"
            sortable
          >
            <template slot-scope="{ row }">
              {{ printDocStatus(row.documentStatus) }}
            </template>
          </el-table-column>

          <el-table-column
            fixed="right"
            min-width="140"
            label="Action"
          >
            <template slot-scope="{ row }">
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

      <bid-submission
        v-else
        ref="bidSubmission"
        :data="selectedRow"
      ></bid-submission>

    </div>
  </div>
</template>
<script lang="ts" src="./index.component.ts"></script>
<style lang="scss">
.compact .auction {

  .el-table--mini {

    th,
    td {
        height: 35px;
    }
  }

  .toolbar {
    padding: 4px 16px 0;

    .el-button + .el-button {
      margin-left: 8px;
    }
  }
}
</style>