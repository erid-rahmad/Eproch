<template>
    <div class="app-container">
            <div v-if="index">
                <el-row class="header">
                    <el-col :span="24">

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            :disabled="disabledButton"
                            icon="el-icon-check"
                            @click="showDialogConfirmation('update')" />
                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            icon="el-icon-search"
                            @click.native.prevent="verificationFilter"/>
                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            @click="addEVerification()"
                            icon="el-icon-plus"/>

                        <el-button
                            class="button"
                            size="small"
                            type="danger"
                            icon="el-icon-delete"
                            :disabled="disabledButton"
                            @click="showDialogConfirmation('void')" />

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            icon="el-icon-position"
                            :disabled="disabledButton"
                            @click="showDialogConfirmation('submit')">
                            Submit
                        </el-button>
                        <el-button
                            class="button"
                            size="small"
                            type="primary"
                            icon="el-icon-printer">
                            Print
                        </el-button>
                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            icon="el-icon-printer">
                            Print (Summary)
                        </el-button>
                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
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
                            <el-form-item label="Status" prop="verificationStatus">
                                <el-select class="form-input" clearable filterable v-model="filter.verificationStatus" placeholder="Status" >
                                    <el-option
                                        v-for="item in statusOptions"
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
                                    format="yyyy/MM/dd"
                                    value-format="yyyy-MM-dd"
                                    placeholder="Pick a date"/>
                            </el-form-item>
                            <el-form-item label="Invoice Date - From" prop="invoiceDateFrom">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.invoiceDateFrom"
                                    type="date"
                                    format="yyyy/MM/dd"
                                    value-format="yyyy-MM-dd"
                                    placeholder="Pick a date" />
                            </el-form-item>
                            <el-form-item label="Tax Invoice Date - From" prop="taxInvoiceDateFrom">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.taxInvoiceDateFrom"
                                    type="date"
                                    format="yyyy/MM/dd"
                                    value-format="yyyy-MM-dd"
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
                                    format="yyyy/MM/dd"
                                    value-format="yyyy-MM-dd"
                                    placeholder="Pick a date" />
                            </el-form-item>
                            <el-form-item label="Invoice Date - To" prop="invoiceDateTo">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.invoiceDateTo"
                                    type="date"
                                    format="yyyy/MM/dd"
                                    value-format="yyyy-MM-dd"
                                    placeholder="Pick a date" />
                            </el-form-item>
                            <el-form-item label="Tax Invoice Date - To" prop="taxInvoiceDateTo">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.taxInvoiceDateTo"
                                    type="date"
                                    format="yyyy/MM/dd"
                                    value-format="yyyy-MM-dd"
                                    placeholder="Pick a date" />
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
                            @row-click="singleSelection"
                            @sort-change="changeOrder">

                            <el-table-column
                                align="center"
                                fixed
                                width="35">
                                <template slot-scope="scope">
                                    <el-radio class="radio" v-model="radioSelection" :label="scope.$index">&nbsp;</el-radio>
                                </template>
                            </el-table-column>

                            <el-table-column
                                min-width="130"
                                prop="verificationNo"
                                label="Verification No"/>
                            <el-table-column
                                min-width="150"
                                prop="verificationDate"
                                label="Verification Date"/>
                            <el-table-column
                                min-width="128"
                                prop="verificationStatus"
                                label="Status"/>
                            <el-table-column
                                min-width="128"
                                prop="invoiceNo"
                                label="Invoice No"/>
                            <el-table-column
                                min-width="128"
                                prop="invoiceDate"
                                label="Invoice Date"/>
                            <el-table-column
                                min-width="128"
                                prop="taxInvoice"
                                label="Tax Invoice"/>
                            <el-table-column
                                min-width="128"
                                prop="taxDate"
                                label="Date Tax"/>
                            <el-table-column
                                min-width="128"
                                prop="currencyName"
                                label="Currency"/>

                            <el-table-column
                                min-width="128"
                                prop="description"
                                label="Notes"/>
                            <el-table-column
                                min-width="128"
                                prop="taxAmount"
                                label="Tax Amount"/>
                            <el-table-column
                                min-width="128"
                                prop="totalLines"
                                label="Total Amount"/>
                            <el-table-column
                                min-width="128"
                                prop="grandTotal"
                                label="Grand Total"/>

                        </el-table>
                        <el-pagination
                            ref="pagination"
                            background
                            layout="sizes, prev, pager, next"
                            mini
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
                        <e-verification-update
                            ref="formUpdate"
                            :form-update="selectedRows"
                            @close-e-verification-update="closeEVerificationUpdate"
                        />
                    </el-col>
                </el-row>

            </div>

        <el-dialog
            width="30%"
            :visible.sync="dialogConfirmationVisible"
            :title="dialogTitle">

            <template>
                <span>{{ dialogMessage }}</span>
                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        :type="dialogType"
                        @click="buttonDialogUpdateRecords">
                            {{ dialogButton }}
                    </el-button>
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-close"
                        @click="dialogConfirmationVisible = false">
                            {{ $t('entity.action.cancel') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script lang="ts" src="./e-verification.component.ts">
</script>

<style lang="scss">
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
