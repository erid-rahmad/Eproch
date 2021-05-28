<template>
  <div class="app-container product-receive-info">
    <el-row class="header">
      <el-col :span="24">
        <el-button
          class="button"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          icon="el-icon-search"
          @click.native.prevent="verificationFilter"
        ></el-button>

        <el-button
          class="button"
          size="mini"
          type="primary"
          icon="el-icon-download"
          @click="exportWizardVisible = true"
        >
          Export
        </el-button>
      </el-col>
    </el-row>

    <el-row class="filter" :gutter="24">
      <el-form
          ref="form"
          label-position="left"
          label-width="170px"
          :model="filter"
          size="mini"
      >
        <el-col :span="8">
          <el-form-item label="Receive No." prop="receiptNo">
            <el-input class="form-input" clearable v-model="filter.receiptNo"/>
          </el-form-item>
          <el-form-item label="Receive Date - From" prop="receiptDateFrom">
            <el-date-picker
              class="form-input"
              clearable
              v-model="filter.receiptDateFrom"
              type="date"
              :format="dateDisplayFormat"
              :value-format="dateValueFormat"
              placeholder="Pick a date"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="PO No." prop="poNo">
            <el-input class="form-input" clearable v-model="filter.poNo"/>
          </el-form-item>
          <el-form-item label="Receive Date - To" prop="receiptDateTo">
            <el-date-picker
              class="form-input"
              clearable
              v-model="filter.receiptDateTo"
              type="date"
              :format="dateDisplayFormat"
              :value-format="dateValueFormat"
              placeholder="Pick a date"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            v-if="isVendor"
            label="Delivery No."
            prop="deliveryNo"
          >
            <el-input
              v-model="filter.deliveryNo"
              class="form-input"
              clearable
            />
          </el-form-item>
          <el-form-item
            v-else
            label="Vendor"
            prop="vendorId"
          >
            <el-select
                v-model="filter.vendorId"
                class="form-input"
                clearable
                filterable
            >
              <el-option
                v-for="item in vendorOptions"
                :key="item.key"
                :label="item.label"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Status" prop="invoiced">
            <el-select
              v-model="filter.invoiced"
              class="form-input"
              clearable
              filterable
              placeholder="Select"
            >
              <el-option
                v-for="item in statusOptions"
                :key="item.key"
                :label="item.label"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-form>

    </el-row>

    <el-row class="main" ref="tableWrapper">
      <el-col :span="24">
        <el-table
          v-loading="processing"
          ref="gridData"
          highlight-current-row
          border stripe
          size="mini"
          style="width: 100%; height: 100%"
          :height="gridSchema.height"
          :max-height="gridSchema.maxHeight"
          :default-sort="gridSchema.defaultSort"
          :empty-text="gridSchema.emptyText"
          :data="gridData"
          @row-click="singleSelection"
          @sort-change="changeOrder"
        >

          <el-table-column
            align="center"
            class-name="no-ellipsis"
            fixed
            width="36"
          >
            <template slot="header"></template>
            <template slot-scope="{ $index }">
              <el-radio class="radio" v-model="radioSelection" :label="$index">&nbsp;</el-radio>
            </template>
          </el-table-column>

          <el-table-column
            min-width="250"
            sortable
            v-if="isVendor==null"
            prop="vendorId"
            label="Vendor"
          >
            <template slot-scope="{ row }">
              {{ row.vendorShortName }}
            </template>
          </el-table-column>

          <el-table-column
            min-width="120"
            sortable
            prop="poNo"
            label="PO No."
          />
          <el-table-column
            min-width="120"
            sortable
            prop="receiptNo"
            label="Receive No."
          />
          <el-table-column
            min-width="120"
            sortable
            prop="receiptDate"
            label="Receive Date"
          />
          <el-table-column
            min-width="120"
            sortable
            prop="deliveryNo"
            label="Delivery No."
          />
          <el-table-column
            min-width="250"
            sortable
            show-overflow-tooltip
            prop="mProductName"
            label="Item Description"
          />
          <el-table-column
            min-width="100"
            sortable
            prop="cUomId"
            label="UoM"
          >
            <template slot-scope="{ row }">
              {{ row.cUomName }}
            </template>
          </el-table-column>
          <el-table-column
            min-width="100"
            sortable
            prop="qty"
            label="Qty"
          />

          <el-table-column
            min-width="150"
            sortable
            prop="priceActual"
            label="Unit Price"
            align="right"
          >
            <template slot-scope="{ row }">
              {{ row.priceActual | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            min-width="150"
            sortable
            prop="totalLines"
            label="Taxable Amount"
            align="right"
          >
            <template slot-scope="{ row }">
                {{ row.totalLines | formatCurrency }}
            </template>
          </el-table-column>
          <el-table-column
            min-width="150"
            sortable
            prop="taxAmount"
            label="PPN"
            align="right"
          >
            <template slot-scope="{ row }">
              {{ row.taxAmount | formatCurrency }}
            </template>
          </el-table-column>

          <el-table-column
            min-width="120"
            sortable
            prop="mMatchType"
            label="Status"
          >
            <template slot-scope="{ row }">
              {{ formatDocumentStatus(row.invoiced) }}
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
        />
      </el-col>
    </el-row>

    <el-dialog
      width="30%"
      :visible.sync="exportWizardVisible"
      title="Export Data"
    >
      <template>
        <el-form
          ref="exportWizardForm"
          :model="exportParameter"
          label-position="left"
          label-width="128px"
          size="mini"
        >
          <el-form-item
            class="field-current-row-only"
            prop="currentRowOnly"
            title="Export all or just the selected row"
          >
            <el-checkbox
              v-model="exportParameter.currentRowOnly"
              @change="checkCurrentRow"
            >
              Current Row Only
            </el-checkbox>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button
            icon="el-icon-check"
            :loading="exportingData"
            size="mini"
            type="primary"
            @click="exportData"
          >
            {{ $t('entity.action.export') }}
          </el-button>
          <el-button
            icon="el-icon-close"
            size="mini"
            @click="exportWizardVisible = false"
          >
            {{ $t('entity.action.cancel') }}
          </el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script lang="ts" src="./product-receive-info.component.ts">
</script>

<style lang="scss">
.product-receive-info {
    .el-pagination .el-input--mini .el-input__inner {
        height: 22px;
    }
}
.el-table__fixed, .el-table__fixed-right{
    box-shadow: none;
}

.header {
    color: #333;
}

.filter {
    .form-input {
        width: 100%;
    }
}

.main {
    padding: 0px;
}

.button {
    margin-bottom: 5px;
}
</style>
