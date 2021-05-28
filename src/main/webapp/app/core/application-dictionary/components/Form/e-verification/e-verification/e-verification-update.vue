<template>
  <div class="app-container verification-update">

    <el-row class="header">
      <el-col :span="24">

        <el-button
          v-if="editable"
          v-loading.fullscreen.lock="fullscreenLoading"
          class="button"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          icon="el-icon-check"
          @click="updateEVerification"
        />

        <el-button
          class="button"
          style="margin-left: 0px;"
          size="mini"
          type="danger"
          icon="el-icon-close"
          @click="closeEVerificationUpdate"
        />

        <el-button
          v-if="editable"
          class="button"
          size="mini"
          type="primary"
          icon="el-icon-plus"
          @click="displayMatchPo(1)"
        >
          Add
        </el-button>

        <el-button
          v-if="editable"
          class="button"
          style="margin-left: 0px;"
          size="mini"
          type="primary"
          icon="el-icon-plus"
          @click="displayMatchPo(2)"
        >
          Add by Receipt No.
        </el-button>

      </el-col>
    </el-row>

    <el-row class="filter" :gutter="24">
      <el-form
        ref="eVerificationUpdate"
        label-position="left"
        label-width="170px"
        :model="header"
        :rules="validationRules"
        size="mini"
      >
        <el-col :span="8">
          <el-form-item label="Invoice No." prop="invoiceNo" required>
            <el-input
              v-model="header.invoiceNo"
              :disabled="!editable"
              class="form-input"
              clearable
            ></el-input>
          </el-form-item>
            <el-form-item label="Invoice Date" prop="invoiceDate" required>
                <el-date-picker
                    v-model="header.invoiceDate"
                    class="form-input"
                    clearable
                    :disabled="!editable"
                    type="date"
                    :format="dateDisplayFormat"
                    :value-format="dateValueFormat"
                    placeholder="Pick a date" />
            </el-form-item>
            <el-form-item label="Currency" prop="currencyId" required>
                <el-select
                    v-model="header.currencyId"
                    :disabled="!editable"
                    class="form-input"
                    clearable
                    filterable
                    placeholder="Currency"
                >
                    <el-option
                        v-for="item in currencyOptions"
                        :key="item.key"
                        :label="item.value"
                        :value="item.key"
                    />
                </el-select>
            </el-form-item>
            <el-form-item
                v-if="!editable"
                label="Verification No"
                prop="documentNo"
            >
                <el-input class="form-input" disabled v-model="header.documentNo"/>
            </el-form-item>
            <el-form-item label="Verification Date" prop="dateTrx">
                <el-date-picker
                    v-model="header.dateTrx"
                    class="form-input"
                    clearable
                    :disabled="!editable"
                    :format="dateDisplayFormat"
                    type="date"
                    :value-format="dateValueFormat"
                    placeholder="Pick a date"
                />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="Tax Invoice No." prop="taxInvoice" :required="header.taxable" >
                <el-input
                    v-model="header.taxInvoice"
                    v-cleave="taxInvoicePattern"
                    ref="taxInvoice"
                    class="form-input"
                    :disabled="!editable"
                    placeholder="___.___-__.________"
                    @change="checkVerification"
                ></el-input>
            </el-form-item>
            <el-form-item label="Tax Invoice Date" prop="taxDate" :required="header.taxable">
                <el-date-picker
                    class="form-input"
                    clearable
                    v-model="header.taxDate"
                    type="date"
                    :disabled="!editable"
                    :format="dateDisplayFormat"
                    :value-format="dateValueFormat"
                    placeholder="Pick a date" />
            </el-form-item>
            <el-form-item label="NPWP" prop="vendorName">
                <el-input class="form-input" disabled clearable v-model="header.vendorName"/>
            </el-form-item>
            <el-form-item v-if="!editable" label="Status" prop="documentStatus">
                <el-input class="form-input" disabled clearable :value="formatDocumentStatus(header.documentStatus)"/>
            </el-form-item>
            <el-form-item v-if="!editable" label="Status Date">
                <el-date-picker
                    class="form-input"
                    clearable disabled
                    :value="dateStatus(header.documentStatus)"
                    type="date"
                    :format="dateDisplayFormat"
                    :value-format="dateValueFormat"
                    placeholder="Pick a date"
                />
            </el-form-item>
        </el-col>
        <el-col :span="8">
            <el-form-item label="Taxable Amount" prop="totalLines">
                <el-input
                    class="form-input"
                    disabled
                    v-model="header.totalLines"
                    v-inputmask="{'alias': 'currency'}"
                />
            </el-form-item>
            <el-form-item label="PPN" prop="taxAmount">
                <el-input
                    class="form-input"
                    disabled
                    v-model="header.taxAmount"
                    v-inputmask="{'alias': 'currency'}"
                />
            </el-form-item>
            <el-form-item label="Total Amount" prop="grandTotal">
                <el-input
                    class="form-input"
                    disabled
                    v-model="header.grandTotal"
                    v-inputmask="{'alias': 'currency'}"
                />
            </el-form-item>
        </el-col>
      </el-form>

    </el-row>

        <el-row class="main" ref="tableWrapper">
            <el-col :span="24">
                <el-table
                    v-loading="processing"
                    highlight-current-row
                    border stripe
                    :data="gridData"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :max-height="gridSchema.maxHeight"
                    :row-class-name="rowClassName"
                    size="mini"
                    @sort-change="changeOrder"
                >

                    <el-table-column
                        v-if="editable"
                        align="center"
                        fixed
                        width="55"
                    >
                        <template slot-scope="{ row, $index }">
                            <el-button
                                type="danger"
                                size="mini"
                                icon="el-icon-close"
                                plain
                                @click="removeRow(row, $index)"
                                :title="$t('entity.action.delete')"
                            />
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
                        prop="receiveNo"
                        label="Receipt No."
                    >
                        <template slot-scope="{ row }">
                            {{ row.receiptNo }}
                        </template>
                    </el-table-column>
                    <el-table-column
                        min-width="120"
                        sortable
                        prop="deliveryNo"
                        label="Delivery No."
                    />
                    <el-table-column
                        min-width="250"
                        prop="mProductName"
                        label="Item Description"
                        show-overflow-tooltip
                    />
                    <el-table-column
                        min-width="100"
                        prop="cUomName"
                        label="UoM"
                    />
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
                        min-width="150"
                        label="Total Amount"
                        align="right"
                    >
                        <template slot-scope="{ row }">
                            {{ row.totalAmount | formatCurrency }}
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination
                    ref="pagination, sizes, prev, pager, next"
                    background
                    :current-page.sync="page"
                    layout="total"
                    :page-size="itemsPerPage"
                    small
                    :total="queryCount"
                ></el-pagination>
            </el-col>
        </el-row>

        <el-dialog
            :width="modeFilterMatchPo.width"
            :visible.sync="dialogMatchPoVisible"
            :title="modeFilterMatchPo.titleModal"
            @opened="onMatchPoOpen"
        >

            <el-row :gutter="16">
                <el-col :span="24" :offset="0">
                    <match-po
                        ref="matchPo"
                        :mode-filter-match-po="modeFilterMatchPo"
                        @get-match-po="addAllLines"
                    />
                </el-col>
            </el-row>

            <div v-if="modeFilterMatchPo.mode === 1" slot="footer">
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    icon="el-icon-check"
                    type="primary"
                    @click="addSelectedLines"
                >
                    Add
                </el-button>
                <el-button
                    style="margin-left: 0px;"
                    size="mini"
                    icon="el-icon-close"
                    @click="dialogMatchPoVisible = false"
                >
                    {{ $t('entity.action.cancel') }}
                </el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./e-verification-update.component.ts">
</script>

<style lang="scss">
.compact .verification-update {
    padding: 0px;

    .el-checkbox.is-bordered.el-checkbox--mini {
        border: none;
        height: 26px;
        line-height: 26px;
        margin-bottom: 0;
        padding: 0;
    }
    .el-table--mini {
        th, td {
            height: 35px;
        }
    }
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

    .el-table .el-table__body,
    .el-table--striped .el-table__body {

        .danger-row td,
        tr.el-table__row--striped.danger-row td {
            background: #ffc1c1;
        }

        .warning-row td,
        tr.el-table__row--striped.warning-row td {
            background: #fff2cd;
        }
    }
}

.button {
    margin-bottom: 5px;
}
</style>
