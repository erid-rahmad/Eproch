<template>
    <div class="app-container verification-document-inquiry">
        <div v-if="index">
            <el-row class="header">
                <el-col :span="24">

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-check"
                        @click="showDialogConfirmation('detail')" />
                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-search"
                        @click.native.prevent="verificationFilter"/>

                    <el-button
                        class="button"
                        size="mini"
                        type="primary"
                        icon="el-icon-printer"
                        @click="showDialogConfirmation('print')">
                        Print
                    </el-button>
                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-printer"
                        @click="showDialogConfirmation('printSummary')">
                        Print (Summary)
                    </el-button>
                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-printer"
                        @click="showDialogConfirmation('printVerificationReceipt')">
                        Verification Receipt Print
                    </el-button>

                    <el-button
                        class="button"
                        size="mini"
                        type="primary"
                        icon="el-icon-edit"
                        @click="showDialogConfirmation('update')">
                        Update Voucher
                    </el-button>

                    <el-button
                        class="button"
                        style="margin-left: 0px;"
                        size="mini"
                        type="primary"
                        icon="el-icon-download">
                        Export
                    </el-button>
                </el-col>
            </el-row>

            <el-row class="filter">
                <el-form ref="form"  label-width="170px" size="mini">
                    <el-col :span="8">
                        <el-form-item label="Verification No." prop="verificationNo">
                            <el-input class="form-input" clearable v-model="filter.verificationNo"/>
                        </el-form-item>
                        <el-form-item label="Invoice No." prop="invoiceNo">
                            <el-input class="form-input" clearable v-model="filter.invoiceNo"></el-input>
                        </el-form-item>
                        <el-form-item label="Tax Invoice No." prop="taxInvoiceNo">
                            <el-input class="form-input" clearable v-model="filter.taxInvoiceNo"></el-input>
                        </el-form-item>
                        <el-form-item label="Vendor" prop="vendorName">
                            <el-select class="form-input" clearable filterable v-model="filter.vendorName" >
                                <el-option
                                    v-for="item in vendorOptions"
                                    :key="item.key"
                                    :label="item.value"
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
                        <el-form-item label="Status" prop="verificationStatus">
                            <el-select class="form-input" clearable filterable v-model="filter.verificationStatus" placeholder="Status" >
                                <el-option
                                    v-for="item in documentStatusOptions"
                                    :key="item.key"
                                    :label="item.value"
                                    :value="item.key" />
                            </el-select>
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
                        <el-form-item label="Payment Status" prop="payStatus">
                            <el-select class="form-input" clearable filterable v-model="filter.payStatus" placeholder="Status" >
                                <el-option
                                    v-for="item in paymentStatusOptions"
                                    :key="item.key"
                                    :label="item.value"
                                    :value="item.key" />
                            </el-select>
                        </el-form-item>

                    </el-col>
                </el-form>

            </el-row>

            <el-row class="main" ref="tableWrapper">
                <el-col :span="24">
                    <el-table
                        v-loading="processing"
                        ref="mainTable"
                        highlight-current-row
                        border stripe
                        size="mini"
                        style="width: 100%; height: 100%"
                        :height="gridSchema.height"
                        :max-height="gridSchema.maxHeight"
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
                            min-width="250"
                            sortable
                            prop="vendorName"
                            label="Vendor"/>
                        <el-table-column
                            min-width="150"
                            sortable
                            prop="verificationNo"
                            label="Verification No"/>
                        <el-table-column
                            min-width="150"
                            sortable
                            prop="verificationDate"
                            label="Verification Date"/>
                        <el-table-column
                            min-width="100"
                            sortable
                            label="Status">
                            <template slot-scope="{ row }">
                                {{ formatDocumentStatus(row.verificationStatus) }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="250"
                            sortable
                            prop="description"
                            label="Notes"/>
                        <el-table-column
                            min-width="140"
                            sortable
                            label="Payment Status"
                        >
                            <template slot-scope="{ row }">
                                {{ formatPaymentStatus(row.payStatus) }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="150"
                            sortable
                            prop="dueDate"
                            label="Payment Schd."/>
                        <el-table-column
                            min-width="140"
                            sortable
                            prop="payDate"
                            label="Actual Pay Date"/>
                        <el-table-column
                            min-width="160"
                            sortable
                            prop="invoiceNo"
                            label="Invoice No"/>
                        <el-table-column
                            min-width="120"
                            sortable
                            prop="invoiceDate"
                            label="Invoice Date"/>
                        <el-table-column
                            min-width="120"
                            sortable
                            prop="dateAcct"
                            label="GL Date"/>

                        <el-table-column
                            min-width="140"
                            sortable
                            label="Tax Invoice No.">
                            <template slot-scope="{ row }">
                                {{ row.taxInvoice | facade('###-##.########') }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="130"
                            sortable
                            prop="taxDate"
                            label="Tax Inv. Date"/>
                        <el-table-column
                            min-width="100"
                            sortable
                            prop="currencyName"
                            label="Currency"/>

                        <el-table-column
                            min-width="150"
                            sortable
                            label="Taxable Amount"
                            align="right">
                            <template slot-scope="{ row }">
                                {{ row.totalLines | formatCurrency }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="150"
                            sortable
                            label="PPN"
                            align="right">
                            <template slot-scope="{ row }">
                                {{ row.taxAmount | formatCurrency }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="150"
                            sortable
                            label="PPH"
                            align="right">
                            <template slot-scope="{ row }">
                                {{ row.withholdingAmt | formatCurrency }}
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="150"
                            label="Total Amount"
                            align="right">
                            <template slot-scope="{ row }">
                                {{ row.totalAmount | formatCurrency }}
                            </template>
                        </el-table-column>

                        <el-table-column
                            min-width="130"
                            sortable
                            prop="invoiceAp"
                            label="Voucher No"/>
                        <el-table-column
                            min-width="130"
                            sortable
                            prop="docType"
                            label="Doc. Type"/>
                        <el-table-column
                            min-width="170"
                            sortable
                            prop="adOrganizationName"
                            label="Doc. Comp."/>

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
                        @size-change="changePageSize"/>
                </el-col>
            </el-row>
        </div>

        <!-- =========================================================================== -->

        <div v-else>

            <el-row :gutter="0">
                <el-col :span="24" :offset="0">
                    <detail-verification-document
                        ref="form"
                        :detail-verification="selectedRow"
                        @close-detail-verification="closeDetailVerification"
                    />
                </el-col>
            </el-row>

        </div>

        <el-dialog
            width="50%"
            :visible.sync="dialogConfirmationVisible"
            title="Update Voucher Match No.">

            <template>

                <el-row :gutter="16">
                    <el-col :span="24" :offset="0">
                        <update-voucher
                            ref="updateVoucher"
                            :set-verification="selectedRow"
                            @close-dialog="closeDetailVerification"
                        />
                    </el-col>
                </el-row>

            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./verification-document-inquiry.component.ts">
</script>

<style lang="scss">
.el-table__fixed, .el-table__fixed-right{
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

    .el-table .danger-row {
        background: oldlace;
    }
}

.button {
    margin-bottom: 5px;
}
</style>
