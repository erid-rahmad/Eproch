<template>
  <div
    v-loading="generating"
    class="app-container"
  >
    <el-row>
      <el-col
        :span="24"
        style="padding: 4px"
      >
        <el-button
          class="button"
          icon="el-icon-check"
          size="mini"
          type="primary"
          @click="generateQuotation"
        >
          Create Quotation
        </el-button>
      </el-col>
    </el-row>
    Generate Quotation
    <el-form
      ref="filterForm"
      label-position="left"
      label-width="150px"
      :model="filter"
      size="mini"
    >
      <el-row :gutter="rowGutter">
        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-form-item label="PR Number">
            <el-input
              ref="requisitionNo"
              v-model="filter.requisitionNo"
              v-loading="loadingPr"
              clearable
              placeholder="Please Enter PR No"
              @change="retrievePurchaseRequisitionLines"
              @clear="retrievePurchaseRequisitionLines()"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-form-item label="Supplier">
            <el-select
              v-model="filter.vendorId"
              class="form-input"
              clearable
              filterable
              placeholder="Select"
              @change="onVendorChange"
            >
              <el-option
                v-for="item in vendorOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-button
            icon="el-icon-search"
            :loading="loadingPrLines"
            size="mini"
            type="primary"
            @click="retrievePurchaseRequisitionLines(filter.requisitionNo, filter.vendorId)"
          >
            Search
          </el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-divider></el-divider>

    <el-form
      ref="mainForm"
      label-position="left"
      label-width="150px"
      :model="form"
      :rules="mainFormValidationSchema"
      size="mini"
    >
      <el-row
        class="header"
        :gutter="rowGutter"
      >
        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-form-item
            label="Organization"
            prop="adOrganizationId"
          >
            <el-select
              v-model="form.adOrganizationId"
              class="form-input"
              clearable
              filterable
              placeholder="Select Organization"
              @change="retrievePurchaseRequisitionLines"
            >
              <el-option
                v-for="item in organizationOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-form-item
            label="Business Classification"
            prop="businessClassificationId"
          >
            <el-select
              v-model="form.businessClassificationId"
              class="form-input"
              clearable
              filterable
              placeholder="Select Classification"
            >
              <el-option
                v-for="item in classificationOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <!--
        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-form-item
            label="PR Type"
            prop="prType"
          >
            <ad-input-lookup
              v-model="form.documentTypeId"
              placeholder="Select Type"
              table-name="c_document_type"
              :label-fields="['name']"
              :query="['name.contains=Requisition']"
            ></ad-input-lookup>
          </el-form-item>
        </el-col>
        -->
      </el-row>

      <el-row
        class="header"
        :gutter="rowGutter"
      >
        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-form-item
            label="Warehouse"
            prop="warehouseId"
          >
            <el-select
              v-model="form.warehouseId"
              class="form-input"
              clearable
              filterable
              placeholder="Select Warehouse"
            >
              <el-option
                v-for="item in warehouseOptions"
                :key="item.key"
                :label="item.code"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col
          :md="8"
          :sm="12"
          :xs="24"
        >
          <el-form-item
            label="Currency"
            prop="currencyId"
          >
            <el-select
              v-model="form.currencyId"
              class="form-input"
              clearable
              filterable
              placeholder="Select"
            >
              <el-option
                v-for="item in currencyOptions"
                :key="item.key"
                :label="item.code"
                :value="item.key"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table
      v-loading="loadingPrLines"
      ref="mainTable"
      border
      :data="gridData"
      :default-sort="gridSchema.defaultSort"
      :empty-text="gridSchema.emptyText"
      :height="gridSchema.height"
      highlight-current-row
      stripe
      size="mini"
      style="width: 100%"
      @sort-change="changeOrder"
      @selection-change="onSelectionChanged"
    >

      <el-table-column
        align="center"
        fixed
        type="selection"
        width="48"
      ></el-table-column>

      <el-table-column
        label="PR No"
        min-width="100"
        prop="requisitionName"
        sortable
      ></el-table-column>

      <el-table-column
        label="PR Type"
        min-width="200"
        prop="requisitionType"
        show-overflow-tooltip
        sortable
      ></el-table-column>

      <el-table-column
        label="Supplier"
        min-width="200"
        prop="vendorName"
        show-overflow-tooltip
        sortable
      ></el-table-column>

      <el-table-column
        label="Line No"
        min-width="100"
        prop="lineNo"
        show-overflow-tooltip
        sortable
      ></el-table-column>

      <el-table-column
        label="Request Date"
        min-width="150"
        prop="dateRequired"
        sortable
      ></el-table-column>

      <el-table-column
        label="Business Category"
        min-width="150"
        prop="businessCategory"
        sortable
      ></el-table-column>

      <el-table-column
        label="Product"
        min-width="150"
        prop="productName"
        sortable
      ></el-table-column>

      <el-table-column
        min-width="100"
        sortable
        prop="quantityBalance"
        label="Qty Open"
      ></el-table-column>

      <el-table-column
        min-width="100"
        sortable
        prop="uomName"
        label="UoM"
      ></el-table-column>

      <el-table-column
        min-width="150"
        sortable
        label="Unit Price"
      >
        <template slot-scope="{ row }">
          {{ row.unitPrice | formatCurrency }}
        </template>
      </el-table-column>

      <el-table-column
        min-width="150"
        sortable
        label="Amount Open"
      >
        <template slot-scope="{ row }">
          {{ row.orderAmount | formatCurrency }}
        </template>
      </el-table-column>

      <el-table-column
        label="Release Qty"
        min-width="152"
        prop="quantityOrdered"
        sortable
      >
        <template slot-scope="{ row, $index }">
          <el-input-number
            v-model="row.quantityOrdered"
            clearable
            controls-position="right"
            :max="row.quantity"
            :min="0"
            size="mini"
            @change="value => onQuantityOrderedChanged(row, $index, value)"
          ></el-input-number>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      width="70%"
      :visible.sync="submit"
      title="Confirmation Request"
      >
      <el-form label-position="left" label-width="150px" :rules="reqFormValidationSchema" :model="requestForm" ref="reqForm">
        <el-form-item label="Title" prop="title">
          <el-input v-model="requestForm.title" class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="Ordered Amount">
          <el-input v-model="requestForm.totalAmount" disabled class="form-input" v-inputmask="{'alias': 'currency'}" ></el-input>
        </el-form-item>
        <el-form-item label="Selection Method" prop="selectionMethod">
          <el-select v-model="requestForm.selectionMethod" :disabled="readOnly" placeholder="Selection Method">
            <el-option v-for="item in pm" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-row>
          <el-col span="12">
            <el-form-item label="Date Trx" prop="dateTrx">
              <el-date-picker
                v-model="requestForm.dateTrx"
                :format="dateDisplayFormat"
                size="mini"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col span="12">
            <el-form-item label="Date Required" prop="dateRequired">
              <el-date-picker
                v-model="requestForm.dateRequired"
                :format="dateDisplayFormat"
                size="mini"
                type="date"
                :value-format="dateValueFormat"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="Description" prop="description">
          <el-input
            v-model="requestForm.description"
            :rows="7"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          icon="el-icon-check"
          size="mini"
          style="margin-left: 0px;"
          type="primary"
          @click="finalSubmitCheck()"
        >
          Confirm
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="Confirm Request"
      width="30%"
      :visible.sync="finalSubmit"
    >
      <template>
        <span>Are you sure to Submit the selected record?</span>
        <div slot="footer">
          <el-button
            icon="el-icon-close"
            size="mini"
            style="margin-left: 0px;"
            @click="finalSubmit = false"
          >
            Cancel
          </el-button>
          <el-button
            icon="el-icon-check"
            size="mini"
            style="margin-left: 0px;"
            type="primary"
            @click="generate"
          >
            Yes
          </el-button>
        </div>
      </template>
    </el-dialog>
    <!-- <el-pagination
      ref="pagination"
      background
      :current-page.sync="page"
      layout="sizes, prev, pager, next"
      mini
      :page-sizes="[10, 20, 50, 100]"
      :page-size="itemsPerPage"
      :total="queryCount"
      @size-change="changePageSize"
    > -->

  </div>
</template>

<script lang="ts" src="./generate-rfq.component.ts">
</script>

<style lang="scss">
.el-table__fixed, .el-table__fixed-right {
  box-shadow: none;
}
</style>

<style lang="scss" scoped>
.el-divider--horizontal {
  margin: 12px 0;
}

.form-input {
  width: 100%;
}

.main {
  padding: 0px;
}

.button {
  margin-bottom: 5px;
}
</style>
