<template>
  <div class="app-container card-view">
    <div class="form-toolbar">
      <el-button icon="el-icon-close" size="mini" type="danger" @click="viewDetail = false" v-if="viewDetail"> Close </el-button>
      <el-button size="mini" type="primary" @click="showContract()" v-if="viewDetail" :disabled="generatingContract"> Generate Contract </el-button>
    </div>
    <div class="card" v-if="!viewDetail">
      <el-form ref="filterForm" label-position="left" label-width="200px" :model="filter" size="mini">
        <!--
        <el-row :gutter="columnSpacing">
          <el-col :xs="24" :sm="24" :lg="10" :xl="8">
            <el-form-item label="Business Category">
              <el-select v-model="filter.categoryId" clearable filterable placeholder="Search Category">
                <el-option v-for="item in categories" :key="item.key" :label="item.value" :value="item.key"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="10" :xl="8">
            <el-form-item label="Vendor">
              <el-select v-model="filter.vendorId" clearable filterable placeholder="Search Vendor">
                <el-option v-for="item in vendors" :key="item.key" :label="item.value" :value="item.key"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="4" :xl="8">
            <el-button icon="el-icon-search" size="mini" type="primary" v-if="!viewDetail" @click="refreshContent()"> Search </el-button>
          </el-col>
        </el-row>
        -->
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
          <el-table-column label="No" width="50">
            <template slot-scope="{ $index }">
              {{ $index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="Quotation No" min-width="150" prop="documentNo" show-overflow-tooltip sortable></el-table-column>
          <el-table-column label="Title" min-width="200" prop="title" show-overflow-tooltip sortable></el-table-column>
          <el-table-column label="Date Required" min-width="150" show-overflow-tooltip>
            <template slot-scope="{ row }">
              {{ formatTimeRemaining(row) }}
            </template>
          </el-table-column>
          <el-table-column label="Type" min-width="170" sortable>
            <template slot-scope="{ row }">
              {{
                row.selectionMethod == 'A' || row.selectionMethod == 'S'
                  ? 'RFQ'
                  : row.selectionMethod == 'P'
                  ? 'Direct Purchase'
                  : row.selectionMethod == 'T'
                  ? 'Tender'
                  : row.selectionMethod
              }}
            </template>
          </el-table-column>
          <el-table-column label="Joined Vendor" min-width="170">
            <template slot-scope="{ row }">
              <el-button class="button" size="mini" style="width: 100%" @click="viewJoinVendor(row.quotationId)">
                <svg-icon name="icomoo/115-users"></svg-icon>
                {{ row.joinedVendorCount }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column min-width="100">
            <template slot-scope="{ row }">
              <el-button icon="el-icon-search" size="mini" type="primary" @click="viewDetailFunc(row)"> View </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </div>
    <div class="card" v-if="viewDetail">
      <rfq-detail ref="rfqDetail" :data="selectedRow" @generatingContract="toggleContract"></rfq-detail>
    </div>

    <el-dialog :visible.sync="showJoinedVendors" class="joined-vendor-dialog" title="Joined Vendors" width="70%">
      <el-table v-loading="loadingJoinedVendors" :data="joinedVendors" border class="vendor-list" highlight-current-row size="mini">
        <el-table-column label="No." width="50">
          <template slot-scope="{ $index }">
            {{ $index + 1 }}
          </template>
        </el-table-column>

        <el-table-column label="Vendor" min-width="150" prop="vendorName" show-overflow-tooltip sortable></el-table-column>

        <el-table-column label="Address" min-width="200" prop="location" show-overflow-tooltip></el-table-column>
      </el-table>
      <div slot="footer">
        <el-button icon="el-icon-close" size="mini" @click="showJoinedVendors = false"> Close </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script lang="ts" src="./rfq-report.component.ts"></script>

<style lang="scss">
.rfq-report {
  .el-table tbody td {
    height: 35px;
  }
}
</style>

<style lang="scss" scoped>
.toolbar {
  padding: 5px;
  margin-left: 10px;
}
</style>
