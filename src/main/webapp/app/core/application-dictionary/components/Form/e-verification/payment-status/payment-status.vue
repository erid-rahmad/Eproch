<template>
    <div class="app-container">
                <el-row class="header">
                    <el-col :span="24">

                        <el-button
                            class="button"
                            style="margin-left: 0px;"
                            size="small"
                            type="primary"
                            icon="el-icon-search"
                            @click.native.prevent="verificationFilter"/>

                        <el-button
                            class="button"
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

                            <el-form-item label="Status" prop="verificationStatus">
                                <el-select class="form-input" clearable filterable v-model="filter.verificationStatus" placeholder="Status" >
                                    <el-option
                                        v-for="item in documentStatuses"
                                        :key="item.key"
                                        :label="item.value"
                                        :value="item.key" />
                                </el-select>
                            </el-form-item>

                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="Verification Date" prop="verificationDate">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.verificationDate"
                                    type="date"
                                    :format="dateDisplayFormat"
                                    :value-format="dateValueFormat"
                                    placeholder="Pick a date"/>
                            </el-form-item>

                            <el-form-item label="Invoice Date" prop="invoiceDate">
                                <el-date-picker
                                    class="form-input"
                                    clearable
                                    v-model="filter.invoiceDate"
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
                            :max-height="gridSchema.maxHeight"
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
                                min-width="140"
                                sortable
                                prop="verificationNo"
                                label="Verification No"/>
                            <el-table-column
                                min-width="140"
                                sortable
                                prop="verificationDate"
                                label="Verification Date"/>
                            <el-table-column
                                min-width="120"
                                sortable
                                prop="verificationStatus"
                                label="Status">
                                <template slot-scope="{ row }">
                                    {{ formatDocumentStatus(row.verificationStatus) }}
                                </template>
                            </el-table-column>

                            <el-table-column
                                min-width="120"
                                sortable
                                prop="payStatus"
                                label="Pay Status"/>
                            <el-table-column
                                min-width="130"
                                sortable
                                prop="dueDate"
                                label="Pay Schedule"/>
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
                                min-width="130"
                                sortable
                                prop="invoiceDate"
                                label="Invoice Date"/>
                            <el-table-column
                                min-width="110"
                                sortable
                                prop="currencyName"
                                label="Currency"/>

                            <el-table-column
                                min-width="150"
                                sortable
                                prop="totalLines"
                                label="Taxable Amount"
                                align="right">
                                <template slot-scope="{ row }">
                                    {{ row.totalLines | formatCurrency }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="150"
                                sortable
                                prop="taxAmount"
                                label="PPN"
                                align="right">
                                <template slot-scope="{ row }">
                                    {{ row.taxAmount | formatCurrency }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="150"
                                sortable
                                prop="taxAmount"
                                label="PPH"
                                align="right">
                                <template slot-scope="{ row }">
                                    {{ row.taxAmount | formatCurrency }}
                                </template>
                            </el-table-column>
                            <el-table-column
                                min-width="150"
                                sortable
                                prop="totalAmount"
                                label="Total Amount"
                                align="right">
                                <template slot-scope="{ row }">
                                    {{ row.totalAmount | formatCurrency }}
                                </template>
                            </el-table-column>

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

            <!-- =========================================================================== -->



        <el-dialog
            width="50%"
            :visible.sync="dialogConfirmationVisible"
            title="Update Voucher Match No.">

            <template>

                <el-row :gutter="16">
                    <el-col :span="24" :offset="0">

                    </el-col>
                </el-row>

                <div slot="footer">
                    <el-button
                        style="margin-left: 0px;"
                        size="mini"
                        icon="el-icon-check"
                        type="primary">
                            Save
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

<script lang="ts" src="./payment-status.component.ts">
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
