<template>
  <div>
  <el-form ref="negoForm" label-position="left" label-width="96px" size="mini">
    <el-row :gutter="24" style="margin-top: 16px">
      <el-col :span="8">
        <el-form-item label="Requestor">
          <el-input v-model="mainForm.createdBy" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="Department">
          <el-input disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="Document Date">
          <el-date-picker
            v-model="mainForm.dateTrx"
            :format="dateDisplayFormat"
            size="mini"
            type="date"
            :value-format="dateValueFormat"
            disabled
          ></el-date-picker>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="24" style="margin-top: 16px">
      <el-col :span="8">
        <el-form-item label="RFQ No.">
          <el-input v-model="mainForm.quotationNo" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="Cost Center">
          <el-input v-model="mainForm.costCenterName" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="Date Required">
          <el-date-picker
            v-model="mainForm.dateRequired"
            :format="dateDisplayFormat"
            size="mini"
            type="date"
            :value-format="dateValueFormat"
            disabled
          ></el-date-picker>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="24" style="margin-top: 16px">
      <el-col :span="8">
        <el-form-item label="Currency">
          <el-input v-model="mainForm.currencyCode" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="Delivery To">
          <el-input v-model="mainForm.warehouseCode" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="24" style="margin-top: 16px">
      <el-col :span="8">
        &nbsp;
      </el-col>
      <el-col :span="8">
        <el-form-item label="Description">
          <el-input v-model="mainForm.description" :rows="4" type="textarea" disabled></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="RFQ Status">
          <el-input v-model="mainForm.status" disabled></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-table
      ref="mainGrid"
      v-loading="loading"
      :data="lineData"
      :empty-text="gridSchema.emptyText"
      border
      highlight-current-row
      size="mini"
      stripe
    >
      <el-table-column label="No" width="50">
        <template slot-scope="row">
          {{ row.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="Product" prop="productName" show-overflow-tooltip min-width="150"></el-table-column>
      <el-table-column label="Line No" prop="lineNo" show-overflow-tooltip min-width="75"></el-table-column>
      <el-table-column label="Short Description" prop="remark" show-overflow-tooltip min-width="150"></el-table-column>
      <el-table-column label="Qty" prop="releaseQty" show-overflow-tooltip min-width="75"></el-table-column>
      <el-table-column label="UoM" prop="uomName" show-overflow-tooltip min-width="75"></el-table-column>
      <el-table-column label="Business Category" prop="businessCategoryName" show-overflow-tooltip min-width="100"></el-table-column>
      <el-table-column label="Unit Price" show-overflow-tooltip min-width="100">
        <template slot-scope="{row}">
          {{row.unitPrice | formatCurrency}}
        </template>
      </el-table-column>
      <el-table-column label="Amount" show-overflow-tooltip min-width="100">
        <template slot-scope="{row}">
          {{row.orderAmount | formatCurrency}}
        </template>
      </el-table-column>
      <el-table-column label="Submission Date" show-overflow-tooltip min-width="100">
        <template slot-scope="{ row }">
          {{ row.submissionDate | formatDate }}
        </template>
      </el-table-column>
      <el-table-column label="Submission Price / Unit" width="150">
        <template slot-scope="{ row }">
          <el-input v-model="row.submissionPrice" v-inputmask="{ alias: 'currency' }" :disabled="readonly" v-on:change="updateTotal(row)"> </el-input>
        </template>
      </el-table-column>
      <el-table-column label="Submission Total Amount" show-overflow-tooltip min-width="100">
        <template slot-scope="{row}">
          {{row.totalSubmissionPrice | formatCurrency}}
        </template>
      </el-table-column>
    </el-table>
  </el-form>

    <el-dialog
      title="Confirm Request"
      width="30%"
      :visible.sync="publishDialog"
    >
      <template>
        <span>Are you sure to Submit proposal and Join the RFQ?</span>
        <div slot="footer">
          <el-button
            icon="el-icon-close"
            size="mini"
            style="margin-left: 0px;"
            @click="publishDialog = false"
          >
            No
          </el-button>
          <el-button
            icon="el-icon-check"
            size="mini"
            style="margin-left: 0px;"
            type="primary"
            @click="save('SMT')"
          >
            Yes
          </el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts" src="./quotation-proposal.component.ts">
</script>

<style lang="scss">
.compact .prequalification-announcement {
  .el-table--mini {
    th,
    td {
      height: 35px;
    }
  }

  .toolbar {
    padding: 4px 16px 0;

    .el-button + .el-button {
      margin-left: 0;
    }
  }
}
</style>
