<template>
  <div class="app-container card-view msg-board-grid">
    <div class="toolbar">
      <el-button v-if="!isMainPage" icon="el-icon-close" size="mini" type="danger" @click="onCloseClicked"> Close </el-button>
    </div>
    <div class="card">
      <div v-if="isMainPage">
        <el-table
          ref="mainGrid"
          v-loading="loading"
          :data="gridData"
          border
          highlight-current-row
          size="mini"
          stripe
          @current-change="onCurrentRowChanged"
          @sort-change="changeOrder"
        >
          <el-table-column fixed label="No" width="50">
            <template v-slot="row">
              {{ row.$index + 1 }}
            </template>
          </el-table-column>

          <el-table-column label="Contract No" min-width="120" prop="documentNo" sortable></el-table-column>

          <el-table-column label="Title" min-width="200" prop="name" show-overflow-tooltip sortable></el-table-column>

          <el-table-column label="Type" min-width="150" prop="documentTypeName" sortable></el-table-column>

          <el-table-column label="Price Confirmation" min-width="200" sortable>
            <template v-slot="{ row }">
              <el-checkbox v-model="row.forPriceConfirmation" disabled></el-checkbox>
            </template>
          </el-table-column>

          <el-table-column label="Start Date" min-width="150" sortable>
            <template v-slot="{ row }">
              {{ row.startDate | formatDate(true) }}
            </template>
          </el-table-column>

          <el-table-column label="Expiration Date" min-width="150" sortable>
            <template v-slot="{ row }">
              {{ row.expirationDate | formatDate(true) }}
            </template>
          </el-table-column>

          <el-table-column fixed="right" label="Action" width="180">
            <template v-slot="{ row }">
              <el-button size="mini" type="primary" @click="viewDetails(row)">
                <svg-icon name="link"></svg-icon>
                View
              </el-button>
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

      <contract-message-board ref="contractMessageBoard" v-else :data="selectedRow"></contract-message-board>
    </div>
  </div>
</template>
<script lang="ts" src="./msg-board-grid.component.ts"></script>

<style lang="scss" scoped>
.msg-board-grid {
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
