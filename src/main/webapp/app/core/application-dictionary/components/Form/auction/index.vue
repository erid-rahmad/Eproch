<template>
  <div class="app-container card-view auction">
    <div class="toolbar">
      <el-button
        v-if="mainPage"
        icon="el-icon-plus"
        size="mini"
        type="primary"
        @click="viewSetup()"
      ></el-button>

      <el-button
        v-if="mainPage"
        :disabled="selectedRow.documentStatus !== 'DRF'"
        icon="el-icon-delete"
        size="mini"
        title="Delete"
        type="danger"
        @click="onDeleteClicked"
      ></el-button>

      <el-button
        v-if="!mainPage"
        icon="el-icon-close"
        size="mini"
        type="danger"
        @click="onCloseClicked"
      >
        Close
      </el-button>

      <el-button
        v-if="setupPage && isDraft && setupTabName !== 'SUM'"
        size="mini"
        type="primary"
        @click="onFormSaved"
      >
        <svg-icon name="icomoo/273-checkmark"></svg-icon> Save
      </el-button>

      <el-button
        v-else-if="setupPage && isDraft && setupTabName === 'SUM'"
        :disabled="publishing"
        size="mini"
        type="primary"
        @click="onPublishClicked"
      >
        <svg-icon name="guide"></svg-icon> Publish
      </el-button>

      <el-dropdown
        v-if="!isVendor && !isDraft && submissionPage"
        size="mini"
        style="margin-left: 5px"
        @command="runAction"
      >
        <el-button
          size="mini"
          type="primary"
        >
          <svg-icon name="icomoo/149-cog"></svg-icon> Actions 
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
            label="Start Date"
            min-width="150"
            sortable
          >
            <template slot-scope="{ row }">
              {{ row.ruleStartDate | formatDate }}
            </template>
          </el-table-column>

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
                :disabled="row.documentStatus === 'DRF'"
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

      <auction-setup
        v-else-if="setupPage"
        ref="setupPage"
        :data.sync="selectedRow"
        :tab.sync="setupTabName"
      ></auction-setup>

      <bid-submission
        v-else
        ref="bidSubmission"
        :data="selectedRow"
      ></bid-submission>

    </div>

    <el-dialog
      width="30%"
      :visible.sync="deleteConfirmationVisible"
      :title="$t('entity.delete.title')"
    >
      <template>
        <span>Are you sure to delete the selected record?</span>
        <div slot="footer">
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-close"
            @click="deleteConfirmationVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
          <el-button
            style="margin-left: 0px;"
            size="mini"
            icon="el-icon-delete"
            type="danger"
            @click="deleteRecord"
          >
            {{ $t('entity.action.delete') }}
          </el-button>
        </div>
      </template>
    </el-dialog>
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