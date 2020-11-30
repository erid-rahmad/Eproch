<template>
    <div class="app-container verification-update">

        <el-row class="header">
            <el-col :span="24">

                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="primary"
                    icon="el-icon-check"
                    v-loading.fullscreen.lock="fullscreenLoading"
                    @click="updateEVerification"/>
                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="danger"
                    icon="el-icon-close"
                    @click="closeEVerificationUpdate"/>

                <el-button
                    class="button"
                    size="small"
                    type="primary"
                    icon="el-icon-plus"
                    @click="displayMatchPo(1)">
                    Add
                </el-button>
                <el-button
                    class="button"
                    style="margin-left: 0px;"
                    size="small"
                    type="primary"
                    icon="el-icon-plus"
                    @click="displayMatchPo(2)">
                    Add by Receipt No.
                </el-button>

            </el-col>
        </el-row>

        <el-row class="filter">
            <el-form ref="eVerificationUpdate" :model="formUpdate" label-width="170px" size="mini" :rules="rules">
                <el-col :span="8">
                    <el-form-item label="Invoice No." prop="invoiceNo" required>
                        <el-input class="form-input" clearable v-model="formUpdate.invoiceNo"/>
                    </el-form-item>
                    <el-form-item label="Invoice Date" prop="invoiceDate" required>
                        <el-date-picker
                            class="form-input"
                            clearable
                            v-model="formUpdate.invoiceDate"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date" />
                    </el-form-item>
                    <el-form-item label="Currency" prop="currencyId" required>
                        <el-select class="form-input" clearable filterable v-model="formUpdate.currencyId" placeholder="Currency" >
                            <el-option
                                v-for="item in currencyOptions"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key" />
                        </el-select>
                    </el-form-item>

                </el-col>
                <el-col :span="8">
                    <el-form-item label="Tax Invoice No." prop="taxInvoice" required>
                        <el-input
                            class="form-input"
                            clearable
                            v-model="formUpdate.taxInvoice"
                            v-inputmask="{'mask': '99.999.999.9-999.999'}"
                            placeholder="__.___.___._-___.___"
                            />
                    </el-form-item>
                    <el-form-item label="Tax Invoice Date" prop="taxDate" required>
                        <el-date-picker
                            class="form-input"
                            clearable
                            v-model="formUpdate.taxDate"
                            type="date"
                            :format="dateDisplayFormat"
                            :value-format="dateValueFormat"
                            placeholder="Pick a date" />
                    </el-form-item>
                    <el-form-item label="NPWP" prop="vendorName">
                        <el-input class="form-input" disabled clearable v-model="formUpdate.vendorName"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="Taxable Amount" prop="totalLines">
                        <el-input
                            class="form-input"
                            disabled
                            v-model="formUpdate.totalLines"
                            v-inputmask="{'alias': 'currency'}"
                        />
                    </el-form-item>
                    <el-form-item label="PPN" prop="taxAmount">
                        <el-input
                            class="form-input"
                            disabled
                            v-model="formUpdate.taxAmount"
                            v-inputmask="{'alias': 'currency'}"
                        />
                    </el-form-item>
                    <el-form-item label="Total Amount" prop="grandTotal">
                        <el-input
                            class="form-input"
                            disabled
                            v-model="formUpdate.grandTotal"
                            v-inputmask="{'alias': 'currency'}"
                        />
                    </el-form-item>
                </el-col>
            </el-form>

        </el-row>

        <el-row class="main grid-view" ref="tableWrapper">
            <el-col :span="24">
                <el-table
                    v-loading="processing"
                    ref="gridData"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%: height: 100%"
                    :height="gridSchema.height"
                    :default-sort="gridSchema.defaultSort"
                    :empty-text="gridSchema.emptyText"
                    :data="gridData"
                    @sort-change="changeOrder">

                    <el-table-column
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
                    background mini
                    layout="sizes, prev, pager, next"
                    :current-page.sync="page"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="itemsPerPage"
                    :total="queryCount"
                    @size-change="changePageSize"/>
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
    .compact .verification-update{
        padding: 0px;
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

    .grid-view {
        .el-table {
            .is-error .el-input__inner {
                border-color: #ff4949;
            }

            label.el-checkbox {
                margin: 4px 0;
            }

            .switch, .checkbox, .selectRemote, .select, .input, .numeric, .date{
                width: 100%;
            }
        }

        .el-pagination {
            background: #fff;
            bottom: 0;
            left: 0;
            right: 0;
            z-index: 5;

            .el-input--mini .el-input__inner {
                height: 22px;
            }
        }
    }
</style>
