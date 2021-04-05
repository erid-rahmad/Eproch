<template>
  <div class="app-container vendor-performance-report">
    <div class="toolbar">
      <el-button
        icon="el-icon-search"
        size="mini"
        type="primary"
      >
        Search
      </el-button>
    </div>
    <el-form
      ref="filterForm"
      label-position="left"
      label-width="200px"
      :model="filter"
      size="mini"
    >
      <el-row :gutter="columnSpacing">
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Business Category">
            <el-select
              v-model="filter.categoryId"
              clearable
              filterable
              placeholder="Search Category"
            >
              <el-option
                v-for="item in categories"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Sub Category">
            <el-select
              v-model="filter.subCategoryId"
              placeholder="Search Sub Category"
            >
              <el-option
                v-for="item in subCategories"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col
          :xs="24"
          :sm="24"
          :lg="12"
          :xl="8"
        >
          <el-form-item label="Vendor">
            <el-select
              v-model="filter.vendorId"
              clearable
              filterable
              placeholder="Search Vendor"
            >
              <el-option
                v-for="item in vendors"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-table
        ref="mainGrid"
        border
        :data="performanceReports"
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
          label="Vendor"
          width="130"
          sortable
        >
          <template slot-scope="{ row }">
            {{ row.dateTrx | formatDate(true) }}
          </template>
        </el-table-column>
        <el-table-column
          label="Vendor Name"
          min-width="150"
          prop="vendorName"
          show-overflow-tooltip
          sortable
        ></el-table-column>
        <el-table-column
          label="Business Category"
          min-width="200"
          prop="businessCategory"
          show-overflow-tooltip
          sortable
        ></el-table-column>
        <el-table-column
          label="Sub Category"
          min-width="150"
          prop="subCategory"
          show-overflow-tooltip
          sortable
        ></el-table-column>
        <el-table-column
          label="Evaluation Score"
          min-width="150"
          prop="evaluationScore"
          sortable
        ></el-table-column>
        <el-table-column
          label="Warning Letter Score"
          min-width="200"
          prop="warningLetterScore"
        ></el-table-column>
        <el-table-column
          label="Rating"
          min-width="100"
          prop="rating"
        ></el-table-column>
      </el-table>
    </el-form>
  </div>
</template>
<script lang="ts" src="./vendor-performance-report.component.ts"></script>

<style lang="scss">
.vendor-performance-report {
  .el-table tbody td {
    height: 35px;
  }
}
</style>

<style lang="scss" scoped>
.vendor-performance-report {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 36px auto;

  .toolbar {
    padding: 4px;
  }
}
</style>
