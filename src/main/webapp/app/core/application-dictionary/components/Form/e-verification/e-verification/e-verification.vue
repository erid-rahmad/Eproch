<template>
    <div class="app-container e-verification">
        <div v-if="index">
            <el-row class="header">
                <el-col :span="24">
                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-check"
                        @click="showDialogConfirmation('update')"
                    />
                    
                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-search"
                        @click.native.prevent="verificationFilter"
                    />

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        @click="addEVerification()"
                        icon="el-icon-plus"
                    />

                    <el-button
                        class="button"
                        size="mini"
                        type="danger"
                        icon="el-icon-delete"
                        :disabled="disabledButton"
                        @click="showDialogConfirmation('cancel')"
                    />

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-position"
                        :disabled="disabledButton"
                        @click="showDialogConfirmation('submit')"
                    >
                        Submit
                    </el-button>

                    <el-button
                        class="button"
                        size="mini"
                        type="primary"
                        icon="el-icon-printer"
                        @click="showDialogConfirmation('print')"
                    >
                        Print
                    </el-button>

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-printer"
                        @click="showDialogConfirmation('printSummary')"
                    >
                        Print (Summary)
                    </el-button>

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-download"
                    >
                        Export
                    </el-button>
                </el-col>
            </el-row>

            <el-row class="filter" :gutter="24">
                <el-form ref="form" label-position="left" label-width="170px" size="mini">
                    <el-col :span="8">
                        <el-form-item label="Verification No." prop="documentNo">
                            <el-input class="form-input" clearable v-model="filter.documentNo"/>
                        </el-form-item>
                        <el-form-item label="Invoice No." prop="invoiceNo">
                            <el-input class="form-input" clearable v-model="filter.invoiceNo"></el-input>
                        </el-form-item>
                        <el-form-item label="Tax Invoice No." prop="taxInvoiceNo">
                            <el-input
                                v-model="filter.taxInvoiceNo"
                                v-inputmask="{'mask': '999-99.99999999'}"
                                class="form-input"
                                clearable
                                placeholder="___-__.________"
                            />
                        </el-form-item>
                        <el-form-item label="Status" prop="documentStatus">
                            <el-select class="form-input" clearable filterable v-model="filter.documentStatus" placeholder="Status" >
                                <el-option
                                    v-for="item in documentStatuses"
                                    :key="item.key"
                                    :label="item.label"
                                    :value="item.key" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Verification Date - From" prop="verificationDateFrom">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.verificationDateFrom"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date"/>
                        </el-form-item>
                        <el-form-item label="Invoice Date - From" prop="invoiceDateFrom">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.invoiceDateFrom"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date" />
                        </el-form-item>
                        <el-form-item label="Tax Invoice Date - From" prop="taxInvoiceDateFrom">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.taxInvoiceDateFrom"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="Verification Date - To" prop="verificationDateTo">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.verificationDateTo"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date" />
                        </el-form-item>
                        <el-form-item label="Invoice Date - To" prop="invoiceDateTo">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.invoiceDateTo"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date" />
                        </el-form-item>
                        <el-form-item label="Tax Invoice Date - To" prop="taxInvoiceDateTo">
                            <el-date-picker
                                class="form-input"
                                clearable
                                v-model="filter.taxInvoiceDateTo"
                                type="date"
                                :format="dateDisplayFormat"
                                :value-format="dateValueFormat"
                                placeholder="Pick a date" />
                        </el-form-item>
                    </el-col>
                </el-form>

            </el-row>

            <el-row class="main" ref="tableWrapper">
                <el-col :span="24">
                    <el-table
                        ref="mainTable"
                        v-loading="processing"
                        highlight-current-row
                        border stripe
                        size="mini"
                        style="width: 100%; height: 100%"
                        :height="gridSchema.height"
                        :default-sort="gridSchema.defaultSort"
                        :empty-text="gridSchema.emptyText"
                        :data="gridData"
                        :row-class-name="rowClassName"
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
                            min-width="140"
                            sortable
                            prop="documentNo"
                            label="Verification No"/>
                        <el-table-column
                            min-width="140"
                            sortable
                            prop="dateTrx"
                            label="Verification Date"
                        />
                        <el-table-column
                            min-width="100"
                            prop="documentStatus"
                            label="Status"
                            sortable
                        >
                            <template slot-scope="{ row }">
                                {{ formatDocumentStatus(row.documentStatus) }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="160"
                            prop="invoiceNo"
                            sortable
                            label="Invoice No"/>
                        <el-table-column
                            min-width="120"
                            prop="invoiceDate"
                            sortable
                            label="Invoice Date"/>
                        <el-table-column
                            min-width="140"
                            prop="taxInvoice"
                            sortable
                            label="Tax Invoice"
                        >
                            <template slot-scope="{ row }">
                                {{ row.taxInvoice | facade('###-##.########') }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="100"
                            prop="taxDate"
                            sortable
                            label="Date Tax"/>
                        <el-table-column
                            min-width="100"
                            prop="currencyName"
                            sortable
                            label="Currency"/>
                        <el-table-column
                            min-width="250"
                            prop="description"
                            sortable
                            label="Notes"/>
                        <el-table-column
                            min-width="150"
                            label="Tax Amount"
                            prop="taxAmount"
                            sortable
                            align="right"
                        >
                            <template slot-scope="{ row }">
                                {{ row.taxAmount | formatCurrency }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="150"
                            label="Total Amount"
                            prop="totalLines"
                            sortable
                            align="right"
                        >
                            <template slot-scope="{ row }">
                                {{ row.totalLines | formatCurrency }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="150"
                            label="Grand Total"
                            prop="grandTotal"
                            sortable
                            align="right"
                        >
                            <template slot-scope="{ row }">
                                {{ row.grandTotal | formatCurrency }}
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
                        small
                        :total="queryCount"
                        @size-change="changePageSize"
                    />
                </el-col>
            </el-row>
        </div>

        <!-- =========================================================================== -->

        <div v-else>

            <el-row :gutter="0">
                <el-col :span="24" :offset="0">
                    <e-verification-update
                        ref="formUpdate"
                        :form-update="selectedRow"
                        :doc-status="documentStatuses"
                        @close-e-verification-update="closeEVerificationUpdate"
                    />
                </el-col>
            </el-row>

        </div>

        <el-dialog
            width="30%"
            :visible.sync="confirmDocStatusUpdate"
            :title="dialogTitle">

            <template>
                <span>{{ dialogMessage }}</span>
                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        :type="dialogType"
                        @click="updateDocumentStatus"
                    >
                        {{ dialogButton }}
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="confirmDocStatusUpdate = false"
                    >
                        No
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./e-verification.component.ts">
</script>

<style lang="scss">
.e-verification {
    .el-pagination .el-input--mini .el-input__inner {
        height: 22px;
    }
}
.el-table__fixed, .el-table__fixed-right {
    box-shadow: none;
}

.el-table .cell.no-ellipsis {
    text-overflow: inherit;
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
