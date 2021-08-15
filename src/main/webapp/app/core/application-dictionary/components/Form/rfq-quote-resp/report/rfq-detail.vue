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
            <el-table-column label="Symbol" min-width="75"> </el-table-column>
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
          <el-table v-loading="processing" :data="joinedSuppliers" border highlight-current-row size="mini" stripe>
            <el-table-column label="Supplier" min-width="150" prop="vendorName" show-overflow-tooltip> </el-table-column>
            <el-table-column label="Total" min-width="150" show-overflow-tooltip>
              <template slot-scope="{ row }">
                {{ row.submissionGrandTotal | formatCurrency }}
              </template>
            </el-table-column>
            <el-table-column label="Submission Date" min-width="150" prop="dateSubmitted" show-overflow-tooltip> </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="card table-data">
          <el-divider content-position="left">
            <h4>Best Saves by Supplier</h4>
          </el-divider>
          <el-table v-loading="processing" :data="joinedSuppliers" border highlight-current-row size="mini" stripe>
            <el-table-column label="Supplier" min-width="150" prop="vendorName" show-overflow-tooltip> </el-table-column>
            <el-table-column label="Saved Amount" min-width="150" show-overflow-tooltip>
              <template slot-scope="{ row }">
                {{ -(row.submissionGrandTotal - data.grandTotal) | formatCurrency }}
              </template>
            </el-table-column>
            <el-table-column label="Submission Date" min-width="150" prop="dateSubmitted" show-overflow-tooltip> </el-table-column>
          </el-table>
        </div>
      </el-col>
      <!--
      <el-col :span="12">
        <div class="card">
          <el-divider content-position="left">
            <h4>Procedure And Pay</h4>
          </el-divider>
          <el-form label-position="left" label-width="220px" size="mini">
            <el-form-item label="PO Spend" prop="referenceNo">
              <el-input ref="PO Spend" v-model="analis.poSpend" clearable disabled placeholder="Please Enter Reference No"> </el-input>
            </el-form-item>
            <el-form-item label="PO Count" prop="referenceNo">
              <el-input ref="PO Count" v-model="analis.poCount" clearable disabled placeholder="Please Enter Reference No"> </el-input>
            </el-form-item>
            <el-form-item label="Invoice Spend" prop="referenceNo">
              <el-input ref="Invoice Spend" v-model="analis.invoiceSpend" clearable disabled placeholder="Please Enter Reference No">
              </el-input>
            </el-form-item>
            <el-form-item label="Invoice Count" prop="referenceNo">
              <el-input ref="Invoice Count" v-model="analis.invoiceCount" clearable disabled placeholder="Please Enter Reference No">
              </el-input>
            </el-form-item>
            <el-form-item label="% Invoice Without Exception" prop="referenceNo">
              <el-input
                ref="% Invoice Without Exception"
                v-model="analis.invoiceWithoutException"
                clearable
                disabled
                placeholder="Please Enter Reference No"
              >
              </el-input>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      -->
    </el-row>
    <!--
    <el-row :gutter="10">
      <el-col :span="12">
        <div class="card table-data">
          <el-divider content-position="left">
            <h4>Active Contract</h4>
          </el-divider>
          <el-table v-loading="processing" :data="analis.activeContractObj" border highlight-current-row size="mini" stripe>
            <el-table-column label="Project Name" min-width="250" prop="name" sortable> </el-table-column>
            <el-table-column label="Effective Date" min-width="140" prop="startDate" show-overflow-tooltip sortable> </el-table-column>
            <el-table-column label="Expired Date" min-width="130" prop="expirationDate" show-overflow-tooltip sortable> </el-table-column>
            <el-table-column label="Contract Amount" min-width="130" prop="price" show-overflow-tooltip sortable> </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="card table-data">
          <el-divider content-position="left">
            <h4>Vendor Performance Project Analysis</h4>
          </el-divider>
          <el-table v-loading="processing" :data="analis.performanceProjectAnalysis" border highlight-current-row size="mini" stripe>
            <el-table-column label="Project Name" min-width="250" prop="name" sortable> </el-table-column>
            <el-table-column label="PIC" min-width="140" prop="pic" show-overflow-tooltip sortable> </el-table-column>
            <el-table-column label="Start Date" min-width="130" prop="startDate" show-overflow-tooltip sortable> </el-table-column>
            <el-table-column label="Total Score" min-width="130" prop="totalScore" show-overflow-tooltip sortable> </el-table-column>
            <el-table-column label="Project Count" min-width="130" show-overflow-tooltip sortable> 1 </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
    -->
    <!--
    <el-row :gutter="10">
      <el-col :span="24">
        <div class="card">
          <canvas :id="chartId" width="100%" height="50"></canvas>
        </div>
      </el-col>
    </el-row>
    -->
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
