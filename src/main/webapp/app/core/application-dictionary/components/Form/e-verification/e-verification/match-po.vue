<template>
    <div class="match-po-lookup">
        <div v-if="modeFilterMatchPo.mode==1">
            <el-row
                class="filter"
                :gutter="24"
                style="margin-top: 0px"
            >
                <el-form
                    ref="form"
                    label-position="left"
                    label-width="170px"
                    size="mini"
                >
                    <el-col :span="8">
                        <el-form-item label="Receipt No." prop="receiptNo">
                            <el-input class="form-input" clearable v-model="filter.receiptNo"/>
                        </el-form-item>
                        <el-form-item label="Receipt Date - From" prop="receiptDateFrom">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.receiptDateFrom"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date" />
                        </el-form-item>

                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="PO No." prop="poNo">
                            <el-input class="form-input" clearable v-model="filter.poNo"></el-input>
                        </el-form-item>
                        <el-form-item label="Receipt Date - To" prop="receiptDateTo">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.receiptDateTo"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Delivery No." prop="deliveryNo">
                            <el-input class="form-input" clearable v-model="filter.deliveryNo"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button
                                class="button"
                                size="mini"
                                type="primary"
                                icon="el-icon-search"
                                @click.native.prevent="matchPoFilter">Find</el-button>
                        </el-form-item>
                    </el-col>
                </el-form>

            </el-row>
            <el-row class="main" ref="tableWrapper">
                <el-col :span="24">
                    <el-table
                        v-loading="processing"
                        ref="gridData"
                        border
                        :data="gridData"
                        :empty-text="gridSchema.emptyText"
                        :height="gridSchema.height"
                        highlight-current-row
                        :max-height="gridSchema.maxHeight"
                        size="mini"
                        stripe
                        style="padding-top: 0px;"
                        @selection-change="onSelectionChanged"
                        @sort-change="changeOrder"
                    >
                        <el-table-column
                            align="center"
                            fixed
                            type="selection"
                        />
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
                            label="Receipt No."
                        />
                        <el-table-column
                            min-width="120"
                            sortable
                            prop="receiptDate"
                            label="Receipt Date"
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
                            prop="mProductName"
                            label="Item Description"
                            show-overflow-tooltip
                        />
                        <el-table-column
                            min-width="100"
                            sortable
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
                            prop="cTaxRate"
                            label="PPN (%)"
                            align="right"
                        >
                          <template slot-scope="{ row }">
                            {{ row.taxAmount | formatCurrency }}
                          </template>
                        </el-table-column>
                        <el-table-column
                            min-width="150"
                            sortable
                            prop="totalAmount"
                            label="Total Amount"
                            align="right"
                        >
                          <template slot-scope="{ row }">
                            {{ row.totalAmount | formatCurrency }}
                          </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        ref="pagination"
                        background
                        layout="total, sizes, prev, pager, next, jumper"
                        small
                        :current-page.sync="page"
                        :page-sizes="[10, 20, 50, 100, 200, 500, 1000]"
                        :page-size="itemsPerPage"
                        :total="queryCount"
                        @size-change="changePageSize"
                    />
                </el-col>
            </el-row>
        </div>
        <div v-else>
            <el-form
                ref="form"
                label-width="100px"
                size="mini"
            >
                    <el-form-item label="Receipt No." prop="filterByReceiptNo">
                        <el-input
                            class="form-input"
                            clearable
                            v-model="filter.filterByReceiptNo"
                            />
                    </el-form-item>
                    <el-form-item>
                        <el-button
                            class="button"
                            size="mini"
                            type="primary"
                            icon="el-icon-search"
                            @click.native.prevent="matchPoFilter">Find</el-button>
                    </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script lang="ts" src="./match-po.component.ts">
</script>

<style lang="scss">
.compact {
    .match-po-lookup {
        .el-table--mini {
            th, td {
                height: 35px;
            }
        }
    }
}
.el-dialog__body{
    padding: 10px 20px;
}
</style>
