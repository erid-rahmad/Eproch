<template>
  <div class="rfq-report">
    <!--
    <el-row :gutter="10">
      <el-col :span="24">
        <div class="card">
          <el-form label-position="left" label-width="220px" size="mini">
            <el-form-item label="Period">
              <el-col :span="8">
                <el-select v-model="period" @change="refreshContent()" clearable filterable placeholder="Period" width="280">
                  <el-option v-for="item in periodSelections" :key="item.value" :label="item.label" :value="item.value"></el-option>
                </el-select>
              </el-col>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
    -->
    <el-row :gutter="10">
      <el-form label-position="left" label-width="130px" size="mini">
        <el-col :span="8">
          <el-form-item label="Title">
            <el-input v-model="data.title" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="Time Remaining">
            <el-input v-model="data.timeRemaining" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <el-row :gutter="10"
      ><!-- form section -->
      <el-col :span="8">
        <div class="card table-data">
          <el-divider content-position="left">
            <h4>Joined Vendors</h4>
          </el-divider>
          <el-table v-loading="processing" :data="joinedSuppliers" border size="mini" stripe
            highlight-current-row @current-change="onCurrentRowChanged" @selection-change="onSelectionChanged">
            <el-table-column align="center" fixed type="selection" width="48"/>
            <el-table-column label="Symbol" min-width="75"> 
              <template slot-scope="{$index}">
                <div v-bind:style="{ backgroundColor: colors[$index], height: '30px', width: '50px' }">
                </div>
              </template>
            </el-table-column>
            <el-table-column label="Ranking" min-width="100" show-overflow-tooltip> 
              <template slot-scope="{ $index }">
                {{ $index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="Supplier" min-width="150" prop="vendorName" show-overflow-tooltip> </el-table-column>
            <el-table-column label="Quote" min-width="100" prop="quoteSupplierId" show-overflow-tooltip> </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="card table-data">
          <el-divider content-position="left">
            <h4>Best Quotes By Time</h4>
          </el-divider>
          <canvas :id="chartId2" width="100%" height="75%"></canvas>
          <!--
            <el-table v-loading="processing" :data="joinedSuppliers" border highlight-current-row size="mini" stripe>
            <el-table-column label="Supplier" min-width="150" prop="vendorName" show-overflow-tooltip> </el-table-column>
            <el-table-column label="Total" min-width="150" show-overflow-tooltip>
              <template slot-scope="{ row }">
                {{ row.submissionGrandTotal | formatCurrency }}
              </template>
            </el-table-column>
            <el-table-column label="Submission Date" min-width="150" prop="dateSubmitted" show-overflow-tooltip> </el-table-column>
          </el-table>
          -->
        </div>
      </el-col>
      <el-col :span="8">
        <div class="card table-data">
          <el-divider content-position="left">
            <h4>Best Saves by Supplier</h4>
          </el-divider>
          <canvas :id="chartId" width="100%" height="75%"></canvas>
          <!--
          <el-table v-loading="processing" :data="joinedSuppliers" border highlight-current-row size="mini" stripe>
            <el-table-column label="Supplier" min-width="150" prop="vendorName" show-overflow-tooltip> </el-table-column>
            <el-table-column label="Saved Amount" min-width="150" show-overflow-tooltip>
              <template slot-scope="{ row }">
                {{ -(row.submissionGrandTotal - data.grandTotal) | formatCurrency }}
              </template>
            </el-table-column>
            <el-table-column label="Submission Date" min-width="150" prop="dateSubmitted" show-overflow-tooltip> </el-table-column>
          </el-table>
          -->
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" src="./rfq-detail.component.ts"></script>

<style lang="scss" scoped>
.evaluation-line-table .el-input.remark {
  margin: 4px 0;
}

.table-data {
  height: 400px;
}
</style>
